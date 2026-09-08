#!/usr/bin/env python3
"""Costruisce il recap dei test unitari/integrazione a partire dai report Surefire
e dai sorgenti Java.

Due sotto-comandi:

  parse   Legge i report Surefire (target/surefire-reports/*.xml) di UN branch gia'
          compilato, li correla con i @DisplayName trovati nei sorgenti di test, e
          scrive un file JSON intermedio con una riga per test:
          {nome_test, descrizione_test, esito_test, classname}.
          In modalita' diff, --only-files restringe ai soli test contenuti nei file
          indicati (tipicamente i *Test.java toccati dopo un commit/data).

  merge   Fonde piu' JSON intermedi (uno per branch, nell'ordine passato), deduplica
          per nome_test (vince la prima occorrenza = branch elencato prima) e scrive
          il CSV finale  nome_test;descrizione_test;esito_test

Solo stdlib. Pensato per essere invocato dalla skill, non a mano.
"""
import argparse
import csv
import glob
import json
import os
import re
import sys
import xml.etree.ElementTree as ET

# --- estrazione @DisplayName dai sorgenti -----------------------------------

# Un metodo di test = un blocco di annotazioni (@Test, @DisplayName, ...) seguito
# dalla firma. Le annotazioni possono stare in QUALSIASI ordine (in questo progetto
# @Test viene prima di @DisplayName), quindi le raccogliamo tutte e poi ispezioniamo
# il blocco. Catturiamo tutte le annotazioni consecutive che precedono la firma.
_TEST_ANNOT = ("Test", "ParameterizedTest", "RepeatedTest", "TestFactory", "TestTemplate")
_BLOCK_RE = re.compile(
    r"(?P<annots>(?:@\w+(?:\s*\([^)]*\))?\s*)+)"                      # 1+ annotazioni
    r"(?:public|private|protected|final|static|\s)*"                 # modificatori
    r"[\w.<>\[\],?\s]+?\s+"                                          # tipo di ritorno
    r"(?P<name>\w+)\s*\(",                                           # nome metodo
    re.MULTILINE,
)
_DISP_RE = re.compile(r'@DisplayName\(\s*"(?P<disp>(?:[^"\\]|\\.)*)"\s*\)')


def _unescape(s):
    return s.encode("utf-8").decode("unicode_escape") if "\\" in s else s


def build_display_maps(source_dirs):
    """Ritorna (method_to_disp, disp_to_method) scandendo i *Test*.java."""
    method_to_disp = {}
    disp_to_method = {}
    for root in source_dirs:
        for path in glob.glob(os.path.join(root, "**", "*.java"), recursive=True):
            base = os.path.basename(path)
            if not (base.endswith("Test.java") or base.endswith("Tests.java")
                    or base.endswith("IT.java") or "Test" in base):
                continue
            try:
                text = open(path, encoding="utf-8").read()
            except Exception:
                continue
            for m in _BLOCK_RE.finditer(text):
                annots = m.group("annots")
                if not any(("@" + a) in annots for a in _TEST_ANNOT):
                    continue  # non e' un metodo di test
                name = m.group("name")
                dm = _DISP_RE.search(annots)
                if dm:
                    disp = _unescape(dm.group("disp"))
                    method_to_disp[name] = disp
                    disp_to_method[disp] = name
                else:
                    method_to_disp.setdefault(name, name)
    return method_to_disp, disp_to_method


# --- parsing report Surefire ------------------------------------------------

def _norm_case_name(raw):
    """Normalizza il name di <testcase>: toglie '()' e parametri [..]."""
    n = raw.strip()
    n = re.sub(r"\(.*\)$", "", n)          # metodo() -> metodo
    n = re.sub(r"\[.*\]$", "", n).strip()  # parametrizzati metodo[1] -> metodo
    return n


def _simple_class(classname):
    return classname.rsplit(".", 1)[-1] if classname else ""


def parse_reports(reports_dir, method_to_disp, disp_to_method, only_simple_classes=None):
    rows = []
    seen = set()  # (classname, nome) per non duplicare i parametrizzati
    for xml_path in sorted(glob.glob(os.path.join(reports_dir, "TEST-*.xml"))):
        try:
            tree = ET.parse(xml_path)
        except ET.ParseError:
            continue
        for case in tree.iter("testcase"):
            classname = case.get("classname", "")
            if only_simple_classes is not None and _simple_class(classname) not in only_simple_classes:
                continue
            raw = case.get("name", "")
            cand = _norm_case_name(raw)
            # risolvi nome metodo e descrizione
            if cand in method_to_disp:
                nome = cand
            elif cand in disp_to_method:
                nome = disp_to_method[cand]
            else:
                nome = cand  # best effort
            descr = method_to_disp.get(nome, nome)
            key = (classname, nome)
            if key in seen:
                # gia' visto (es. piu' invocazioni parametrizzate): aggiorna esito se fallito
                if _case_failed(case):
                    for r in rows:
                        if r["_key"] == list(key):
                            r["esito_test"] = "Non Passato"
                continue
            seen.add(key)
            rows.append({
                "nome_test": nome,
                "descrizione_test": descr,
                "esito_test": "Non Passato" if _case_failed(case) else "Passato",
                "classname": classname,
                "_key": list(key),
            })
    for r in rows:
        r.pop("_key", None)
    return rows


def _case_failed(case):
    for child in case:
        tag = child.tag.split("}")[-1]
        if tag in ("failure", "error", "skipped"):
            return True
    return False


# --- comandi ----------------------------------------------------------------

def cmd_parse(args):
    method_to_disp, disp_to_method = build_display_maps(args.sources)
    only = None
    if args.only_files:
        only = set()
        for f in args.only_files:
            b = os.path.basename(f)
            if b.endswith(".java"):
                only.add(b[:-5])
    rows = parse_reports(args.reports, method_to_disp, disp_to_method, only)
    json.dump(rows, open(args.out, "w", encoding="utf-8"), ensure_ascii=False, indent=2)
    print(f"[parse] {len(rows)} test -> {args.out}")


def cmd_merge(args):
    merged = []
    seen = set()
    for jpath in args.inputs:  # ordine = priorita' (primo vince)
        for r in json.load(open(jpath, encoding="utf-8")):
            nome = r["nome_test"]
            if nome in seen:
                continue
            seen.add(nome)
            merged.append(r)
    with open(args.out, "w", encoding="utf-8-sig", newline="") as fh:
        w = csv.writer(fh, delimiter=";")
        w.writerow(["nome_test", "descrizione_test", "esito_test"])
        for r in merged:
            w.writerow([r["nome_test"], r["descrizione_test"], r["esito_test"]])
    passati = sum(1 for r in merged if r["esito_test"] == "Passato")
    print(f"[merge] {len(merged)} test unici ({passati} Passato / "
          f"{len(merged)-passati} Non Passato) -> {args.out}")


def main():
    p = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    sub = p.add_subparsers(dest="cmd", required=True)

    pp = sub.add_parser("parse", help="parsa i report Surefire di un branch")
    pp.add_argument("--reports", required=True, help="dir dei report Surefire (TEST-*.xml)")
    pp.add_argument("--sources", nargs="+", required=True, help="root dei sorgenti di test")
    pp.add_argument("--only-files", nargs="*", help="[diff] limita ai test in questi file")
    pp.add_argument("--out", required=True, help="JSON intermedio di output")
    pp.set_defaults(func=cmd_parse)

    pm = sub.add_parser("merge", help="fonde i JSON per branch e scrive il CSV")
    pm.add_argument("--inputs", nargs="+", required=True, help="JSON per branch, in ordine di priorita'")
    pm.add_argument("--out", required=True, help="CSV finale")
    pm.set_defaults(func=cmd_merge)

    args = p.parse_args()
    args.func(args)


if __name__ == "__main__":
    main()
