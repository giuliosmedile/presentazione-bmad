---
name: recap-unit-test
description: >-
  Genera un recap CSV dei test unitari/integrazione del progetto attraverso i
  branch git, eseguendoli per determinarne l'esito. Due modalita': `full` (tutti
  i test) e `diff` (solo i test nei file toccati dopo un certo commit o data).
  Produce un CSV nome_test;descrizione_test;esito_test con esito "Passato"/"Non
  Passato", deduplicando i test ripetuti su piu' branch. Usa questa skill ogni
  volta che l'utente chiede un recap/report/riepilogo dei test, l'elenco dei test
  con il loro esito, un CSV dei test eseguiti, "quali test sono passati", di
  raccogliere i test aggiunti dopo un commit/branch/data, o di consuntivare i test
  fatti su uno o piu' branch. Conversa SEMPRE in italiano.
---

# Recap Unit Test

Produce un **CSV** che consuntiva i test del progetto (Maven + JUnit 5) attraverso i
branch git. Per ogni test riporta nome, descrizione ed esito reale, ottenuto
**eseguendo** la suite. Output:

```
nome_test;descrizione_test;esito_test
```

- `nome_test` — nome del metodo `@Test` (es. `handshake_luogoOggettoGiuridico_roundTrip`)
- `descrizione_test` — valore di `@DisplayName`; se assente, si usa il nome del metodo
- `esito_test` — `Passato` oppure `Non Passato` (fallito, in errore o skippato)

Uno script fa il lavoro deterministico (parsing report + `@DisplayName`, dedup, CSV):
`scripts/test_recap.py`. Tu orchestri: proponi branch e modalita', compili/esegui
per branch, invochi lo script, consegni il CSV. **Parla sempre in italiano con l'utente.**

## Modalita'

- **full** — elenca tutti i test presenti sull'ultimo commit di ogni branch scelto.
- **diff** — elenca solo i test contenuti nei file `*Test.java` **toccati dopo** un
  commit o una data di riferimento (git considera il file modificato/aggiunto). Chiedi
  all'utente il punto di riferimento (SHA o data ISO `YYYY-MM-DD`) se non fornito.

## Regole trasversali

- **Dedup per `nome_test`**: se lo stesso test compare su piu' branch, lo includi una
  sola volta. Vince il branch elencato **per primo** nell'ordine di analisi (di default
  il piu' recente per data di commit). Lo script `merge` applica questa regola.
- **Conversazione in italiano**, sempre.
- Non alterare la working copy dell'utente: vedi "Sicurezza sul checkout".

---

## Workflow

### 1. Analizza lo stato git e proponi

Raccogli il contesto e **proponi** all'utente branch + modalita', poi conferma.

```bash
git rev-parse --abbrev-ref HEAD                 # branch corrente
git status --porcelain                          # working copy pulita?
git branch --sort=-committerdate \
  --format='%(refname:short) | %(committerdate:short)'   # branch per data desc
```

Presenta una proposta concreta, es.: *"Analizzo i 2 branch aggiornati piu' di recente
(`story/16607-…` del 2026-07-01 e `story/17109-…` del 2026-06-30) in modalita' **full**.
Confermi o vuoi cambiare branch/modalita'?"* Includi la scelta della modalita' nella
proposta. Aspetta conferma prima di procedere.

Se la modalita' e' **diff** e manca il riferimento, chiedilo (SHA commit o data).

### 2. Sicurezza sul checkout

Cambiare branch **richiede una working copy pulita**. Se `git status --porcelain` non e'
vuoto, fermati e chiedi all'utente come procedere (stash / commit / annullare) — non
buttare via il suo lavoro. Ricorda il branch di partenza:

```bash
START=$(git rev-parse --abbrev-ref HEAD)
```

A fine lavoro (anche in caso di errore) torna sempre al branch iniziale:
`git checkout "$START"`.

### 3. Per ogni branch, nell'ordine deciso

Elabora i branch nell'ordine di priorita' (il primo vince i duplicati). Per ciascuno:

**a. Checkout dell'ultimo commit**

```bash
git checkout <branch>
```

**b. (Solo modalita' diff) determina i file di test toccati**

```bash
# rispetto a un commit di riferimento:
git diff --name-only <ref-sha> HEAD -- 'src/test/java/**/*Test.java' 'src/test/java/**/*Tests.java'
# oppure rispetto a una data:
git log --since='<YYYY-MM-DD>' --name-only --pretty=format: -- 'src/test/java/**/*Test.java' | sort -u
```

Raccogli i path risultanti: diventeranno `--only-files` per lo script. Se la lista e'
vuota, il branch non aggiunge test in diff: saltalo (informane l'utente).

**c. Esegui i test (produce i report Surefire)**

```bash
mvn -q clean test
```

Usa **`clean`**: cambiando branch, `target/` conserva classi e file `META-INF/services`
del branch precedente. Riusarli mischia artefatti di branch diversi e puo' far fallire il
caricamento del contesto Spring (es. un `FunctionContributor`/service che punta a una
classe non presente su questo branch) — con conseguente cascata di errori che marca "Non
Passato" test in realta' sani. `clean` ricompila da zero il branch corrente ed elimina
questa contaminazione incrociata.

Gli integration test richiedono i **DB attivi** (vedi `application-*` / `docker compose`
del progetto). Attenzione: branch diversi possono richiedere stack diversi (es. uno
PostgreSQL, un altro MySQL) — **l'infra va allineata al branch**, non basta quella lasciata
dal branch precedente. Verifica anche che nessun servizio DB **nativo** occupi la porta
attesa (es. un PostgreSQL installato su Windows sulla 5432): l'app si connetterebbe a
quello sbagliato e Flyway/JPA fallirebbero (tipico: `il ruolo "X" non esiste`), facendo
saltare il contesto Spring e marcando "Non Passato" decine di test in realta' sani. Se il
DB non e' raggiungibile o e' quello sbagliato i test risulteranno "Non Passato": avvisa
l'utente prima di eseguire, cosi' puo' avviare/allineare lo stack. `mvn test`
puo' terminare con exit code diverso da 0 se dei test falliscono — **e' atteso**: i
report XML vengono comunque scritti in `target/surefire-reports/`. Prosegui col parsing.

Se il primo test a caricare il contesto va in errore, Spring supera la "context failure
threshold" e **salta** i successivi con lo stesso contesto marcandoli in errore: un unico
problema d'ambiente puo' quindi gonfiare i "Non Passato". Se vedi molti errori identici,
sospetta l'ambiente (DB spento, `target` sporco, profilo errato) prima di concludere che i
test siano rotti, e segnalalo all'utente.

Per accelerare in modalita' diff puoi restringere l'esecuzione, es.
`mvn -q clean test -Dtest='ClasseA,ClasseB'` con le sole classi toccate, ma **non e'
obbligatorio**: lo script filtra comunque i test in fase di parsing.

**d. Parsa il branch nel JSON intermedio**

```bash
python .claude/skills/recap-unit-test/scripts/test_recap.py parse \
  --reports target/surefire-reports \
  --sources src/test/java \
  --out /tmp/recap_<branch-safe>.json
# in modalita' diff aggiungi:  --only-files <file1> <file2> ...
```

Su Windows/PowerShell anteponi `PYTHONUTF8=1` (o `$env:PYTHONUTF8=1`) per evitare
problemi con i caratteri accentati/frecce nei `@DisplayName`.

### 4. Fondi e genera il CSV

Passa i JSON **nell'ordine di priorita'** (primo = vince i duplicati):

```bash
python .claude/skills/recap-unit-test/scripts/test_recap.py merge \
  --inputs /tmp/recap_branch1.json /tmp/recap_branch2.json ... \
  --out recap_test.csv
```

Lo script scrive l'header, deduplica per `nome_test`, usa `;` come separatore e
codifica UTF-8 con BOM (apribile direttamente in Excel).

### 5. Chiudi

- `git checkout "$START"` per ripristinare il branch iniziale.
- Riassumi all'utente in italiano: percorso del CSV, numero di test unici, quanti
  Passato / Non Passato, ed eventuali branch saltati (diff senza test nuovi).

---

## Note sul parsing (perche' funziona)

- Lo script correla i `<testcase>` dei report Surefire con i `@DisplayName` letti dai
  sorgenti. Surefire a volte scrive nel `name` del testcase il metodo, a volte il
  display name: lo script tenta entrambe le direzioni (metodo↔display) per risolvere
  sempre `nome_test` al nome del metodo.
- I test **parametrizzati** generano piu' `<testcase>` con lo stesso metodo: lo script
  li collassa in un'unica riga, marcata `Non Passato` se **almeno una** invocazione
  fallisce.
- In `@DisplayName` di questo progetto `@Test` precede `@DisplayName`: l'estrazione e'
  indipendente dall'ordine delle annotazioni.

Per dettagli su casi limite ed estensioni vedi `references/dettagli.md`.
