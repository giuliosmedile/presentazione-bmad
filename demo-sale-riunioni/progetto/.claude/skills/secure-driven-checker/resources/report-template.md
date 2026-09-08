# Secure Driven Design — Audit Report

> Generato da `secure-driven-checker v2` il **{{generated_at_iso}}** (Europe/Rome).

## Esecuzione

| Voce | Valore |
| --- | --- |
| Modalità | `{{mode}}` |
| Scope | `{{target_scope_or_diff_summary}}` |
| Base ref (solo modalità `pr`) | `{{base_ref_or_dash}}` |
| Commit base | `{{commit_base_full_sha}}` |
| Linee guida — file | `{{guidelines_path}}` |
| Linee guida — SHA-256 | `{{guidelines_sha256}}` |
| File analizzati | {{files_analyzed_count}} |
| Comando invocato | `{{invocation_command}}` |

## Esito

**Status:** `{{audit_status}}`  <!-- CLEAN | VIOLATIONS_FOUND -->

| Severity | Conteggio (in-diff) |
| --- | ---: |
| BLOCKER | {{count_blocker}} |
| CRITICAL | {{count_critical}} |
| WARNING | {{count_warning}} |
| INFO | {{count_info}} |
| **Totale in-diff** | **{{count_total}}** |

- Pre-esistenti (fuori dalle modifiche): **{{count_preexisting}}**
- Soppressi via `@secure-driven:ignore`: **{{count_suppressed}}**
- Requisiti verificati almeno una volta: **{{requirements_verified}}** su **{{requirements_indexed_total}}**

---

## Violazioni rilevate

<!-- Inserire una sezione per ogni violazione, raggruppate per severity poi per requirement_id.
     Layout per ciascuna violazione: -->

### [{{severity}}] {{requirement_id}} — {{requirement_title}}

- **File:** `{{file_path}}`
- **Package / Modulo:** `{{package_or_module}}`
- **Riga:** `{{line}}`
- **Snippet:**
  ```{{language}}
  {{snippet}}
  ```
- **Spiegazione:** {{explanation}}
- **Riferimento requisito:** {{area}} / {{ambito}} / {{normativa}}

<!-- ripetere per ogni violazione. Se 0 violazioni in-diff: sostituire questa sezione con:
     "Nessuna violazione rilevata nelle modifiche in scope." -->

---

## Violazioni pre-esistenti (fuori dalle modifiche correnti)

> Queste violazioni sono presenti nel codice ma fuori dalle hunk del diff analizzato. Sono
> riportate per trasparenza ma non contano nel totale in-diff.

<!-- Stesso layout della sezione Violazioni. Omettere la sezione se vuota. -->

---

## Violazioni soppresse

> Soppressioni inline `@secure-driven:ignore` riconosciute. Riportate per audit trail.

| File | Riga | Requirement | Motivo |
| --- | ---: | --- | --- |
| `{{file_path}}` | {{line}} | {{requirement_id}} | {{reason}} |

<!-- Omettere la sezione se vuota. -->

---

## Requisiti verificati

> Elenco dei requisiti effettivamente esercitati durante questa run (almeno un file in scope
> li ha attivati). Garanzia anti-skip silenzioso.

- **{{requirement_id}}** — {{requirement_title}}
- …

---

## Requisiti saltati per file

> Requisiti NON applicabili al singolo file in base a `applicability-rules.yaml`. Mostrate al
> massimo 50 righe; la lista completa è nel JSON sidecar.

| File | Requirement | Motivo |
| --- | --- | --- |
| `{{file_path}}` | {{requirement_id}} | {{reason}} |

---

## Requisiti saltati a livello di metodo (Controller ibridi REST + GraphQL)

> Solo per i Controller in cui coesistono handler REST e GraphQL nello stesso file. Mostra
> quali requisiti REST sono stati correttamente NON applicati ai metodi GraphQL (e viceversa).
> Garanzia anti-skip silenzioso a granularità di metodo.

| File | Metodo | Requirement | Motivo |
| --- | --- | --- | --- |
| `{{file_path}}` | `{{method_name}}` | {{requirement_id}} | {{reason}} |

---

## Riproducibilità

- **Source SHA-256 (full):** `{{guidelines_sha256_full}}`
- **Commit base (full):** `{{commit_base_full_sha}}`
- **Skill version:** `secure-driven-checker v2`
- **JSON sidecar:** `{{report_json_path}}`

---

<!-- END OF REPORT -->
