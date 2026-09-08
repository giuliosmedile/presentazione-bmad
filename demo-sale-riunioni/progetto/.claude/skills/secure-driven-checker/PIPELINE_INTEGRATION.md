# Integrazione `secure-driven-checker` nel flusso BMad

> **Principio cardine:** `secure-driven-checker` v2 si comporta come uno **step di pipeline a sé**. Non modifica nessuno skill BMad preesistente. L'integrazione è ottenuta invocandolo manualmente in punti precisi del workflow oppure leggendo il report JSON che produce.

## 1. Contratto di output

Ogni esecuzione produce due artefatti in `docs/audit-reports/`:

- `report-YYYYmmDDHHmmss.md` — leggibile umano, in italiano
- `report-YYYYmmDDHHmmss.json` — machine-readable, contratto stabile per chi consuma

Il JSON è ciò che gli altri skill BMad possono leggere senza accoppiamento. Schema chiave (estratto):

```json
{
  "skill_version": "v2",
  "mode": "diff",
  "summary": {
    "status": "CLEAN | VIOLATIONS_FOUND",
    "violations_in_diff": { "BLOCKER": 0, "CRITICAL": 0, "WARNING": 0, "INFO": 0 }
  },
  "violations": [ /* ... */ ],
  "preexisting_violations": [ /* ... */ ],
  "suppressed_violations": [ /* ... */ ],
  "verified_requirement_ids": [ /* ... */ ]
}
```

## 2. Punti di inserimento nella pipeline BMad

Nessuno di questi step modifica i file dei rispettivi skill BMad. L'integrazione è *operativa*: lo developer (o un altro agente BMad invocato nel turno successivo) richiama esplicitamente il checker quando il workflow lo prevede.

### 2.1 Dopo `bmad-dev-story` o `bmad-quick-dev` — pre-commit gate informativo

| Quando | Azione |
| --- | --- |
| Amelia (`bmad-dev-story`) ha chiuso una story, oppure `bmad-quick-dev` ha prodotto una modifica | Invocare `/secure-driven-checker` (modalità `diff` di default) **in un context fresh** prima di committare |
| Output | Report Markdown + JSON in `docs/audit-reports/` |
| Decisione | Lo sviluppatore valuta il report. Se BLOCKER o CRITICAL → correggere e rilanciare |
| Override | Per soppressione localizzata e tracciata: aggiungere un commento inline `// @secure-driven:ignore Rxxx (reason: ...)` sopra la riga |

### 2.2 In congiunzione con `bmad-code-review` — review layer parallelo

`bmad-code-review` è una review adversarial multi-layer (Blind Hunter / Edge Case Hunter / Acceptance Auditor). Il `secure-driven-checker` è il **quarto layer** ortogonale: compliance normativa.

Modello d'uso consigliato:

1. Eseguire `/secure-driven-checker` per primo (modalità `diff` o `pr <base>`).
2. Eseguire `/bmad-code-review` subito dopo, indicandogli che il report di compliance è disponibile in `docs/audit-reports/report-{ts}.{md,json}`.
3. Il code review aggrega le findings in triage. Le violazioni di compliance hanno tracciabilità diretta (requirement_id + linea), e quindi confluiscono in modo naturale nel processo di triage.

### 2.3 Pre-merge / PR review — modalità `pr`

| Quando | Azione |
| --- | --- |
| Si sta per aprire o rivedere una PR | Invocare `/secure-driven-checker pr <base-ref>` (es. `pr main`) |
| Output | Report che copre l'intero diff PR vs base |
| Hook documentale | Allegare il report Markdown alla descrizione della PR (o committarlo se la policy lo prevede) |

### 2.4 Insieme a `bmad-checkpoint-preview` — review umano

`bmad-checkpoint-preview` richiede un riassunto leggibile della modifica. Il **report Markdown** del checker è già il sommario pronto. Suggerimento operativo: lo si invoca per primo, il checkpoint preview riferisce esplicitamente al path del report.

### 2.5 Fine epic — `bmad-retrospective`

Alla chiusura di un epic, leggere tutti i `docs/audit-reports/report-*.json` prodotti durante l'epic e calcolare metriche:

- Numero totale di violazioni rilevate vs risolte
- Requisiti più violati (top 5)
- Trend di severity nel tempo
- Numero di soppressioni inline introdotte (debito tecnico da rivisitare)

Suggerire questo input a `bmad-retrospective` quando lo si invoca.

### 2.6 Mid-sprint — `bmad-correct-course`

Se più audit consecutivi mostrano violazioni BLOCKER sullo stesso requisito in aree distinte → segnale di problema sistemico nel design. Invocare `bmad-correct-course` portando con sé i report JSON come evidenza per il re-baseline.

### 2.7 Documentazione — `bmad-agent-tech-writer` (Paige)

Quando Paige produce documentazione di un'API:

- Aprire `docs/audit-reports/` per consultare i requisiti **verified** sull'area documentata.
- Citare nella documentazione: *"questa API soddisfa i requisiti R407, R408, … delle linee guida configurate per il progetto, come verificato in `report-{ts}.md`."*

### 2.8 Design check pre-implementazione — `bmad-agent-architect` (Winston)

Quando Winston disegna un nuovo endpoint REST/SOAP:

1. Implementare anche solo lo scaffold (controller stub + signature + OpenAPI fragment).
2. Eseguire `/secure-driven-checker full <package-del-nuovo-endpoint>`.
3. Validare che lo scaffold sia già allineato ai requisiti applicabili **prima** di entrare nella story implementativa.

## 3. Modalità di invocazione (cheat sheet)

```text
/secure-driven-checker                            # diff (default): uncommitted + staged
/secure-driven-checker staged                     # solo staged (utile in pre-commit)
/secure-driven-checker full                       # intero progetto
/secure-driven-checker full src/main/java/...     # un singolo package
/secure-driven-checker pr main                    # diff vs main
/secure-driven-checker --reload-guidelines        # invalida cache, ricarica .md
/secure-driven-checker --gate                     # fallisce se sev >= BLOCKER (configurabile)
```

I flag possono essere combinati: `/secure-driven-checker pr main --gate`.

## 4. Soppressioni inline

Sintassi (esempio Java):

```java
// @secure-driven:ignore R413 (reason: endpoint legacy, deprecate 2026-Q3 — vedi TIRRENO-1234)
@PostMapping("/legacy/endpoint")
public ResponseEntity<?> legacyHandler(...) { ... }
```

Regole:

- Una singola soppressione copre la riga successiva fino a 5 righe dopo.
- Più ID separati da spazio: `@secure-driven:ignore R413 R415`.
- Il `(reason: ...)` è **fortemente raccomandato** ed entra nel report nella sezione "Violazioni soppresse".
- Le soppressioni **non sono mai silenziose**: ogni violazione soppressa è elencata nel report con riga, requisito e motivo.

## 5. Cosa NON fa lo skill

- Non riformatta codice, non propone refactoring, non risolve violazioni autonomamente.
- Non blocca mai un workflow se non con `--gate` esplicito.
- Non aggiorna `MEMORY.md` automaticamente; l'utente deve chiederlo esplicitamente se vuole un puntatore persistente al folder dei report.
- Non modifica nessuno skill BMad preesistente. L'integrazione è operativa, non strutturale.

## 6. Estensione futura (non in v2)

- **Hook pre-commit nativo Git:** `git config core.hooksPath .githooks` + uno script `pre-commit` che invoca `/secure-driven-checker staged --gate`. Lasciato fuori dalla v2 per evitare dipendenze su tooling esterno e per restare conforme al vincolo "informativo di default".
- **Trend dashboard:** aggregatore che produce un `compliance-trend.md` letto direttamente dal foglio Excel originale dei requisiti per cross-check.
- **Mapping `applicability` ai requisiti R5xx/R6xx:** la v2 lascia placeholder; richiede una lettura completa del foglio per chiudere il mapping.
