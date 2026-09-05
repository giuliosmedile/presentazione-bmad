# 💻 Amelia — Code review (`CR`)

> Review avversariale su tre layer in parallelo, in sessione separata
> dall'implementazione.
> Artefatti toccati: la sezione **Review Findings** della story,
> `deferred-work.md`, `sprint-status.yaml`.

---

**giulio** — `/bmad-agent-dev`

💻 Ciao giulio, sono Amelia. Ricorda che puoi invocare `bmad-help` in qualunque
momento.

| # | Cod | Descrizione | Skill che parte |
|---|---|---|---|
| 1 | `DS` | Scrivere test e codice della prossima story | `bmad-dev-story` |
| 2 | `QD` | Flusso rapido: chiarisci, pianifica, implementa, rivedi | `bmad-quick-dev` |
| 3 | `QA` | Generare test API ed E2E su feature esistenti | `bmad-qa-generate-e2e-tests` |
| 4 | `CR` | Code review completa su più assi di qualità | `bmad-code-review` |
| 5 | `SP` | Generare o aggiornare lo sprint plan | `bmad-sprint-planning` |
| 6 | `CS` | Preparare una story col contesto per implementarla | `bmad-create-story` |
| 7 | `ER` | Retrospettiva di fine epic | `bmad-retrospective` |

In attesa. Rispondi con un numero, un codice o una descrizione.

---

**giulio** — CR sulla 1.1.

💻 `CR`. Sessione nuova: non riuso il ragionamento con cui l'ho implementata.

Contesto raccolto:

- diff — 7 file, `git diff main...HEAD`
- spec — `1-1-ricerca-e-prenotazione-di-una-sala.md`
- context docs — `project-context.md`, `architecture.md`

Modalità `full` (c'è la spec). Tre reviewer in parallelo, e **ricevono contesto
diverso apposta**:

| Reviewer | Cosa riceve | Cosa non riceve |
|---|---|---|
| **Blind Hunter** | solo il diff | spec, context docs, accesso al progetto |
| **Edge Case Hunter** | il diff, e può leggere il progetto | la spec |
| **Acceptance Auditor** | il diff, la spec, i context docs | — |

---

💻 Layer completati: 3 su 3. 9 finding grezzi, 2 coppie deduplicate.

**Code review completa.** 1 `decision-needed`, 2 `patch`, 1 `defer`,
3 scartati come rumore.

Findings scritti nella sezione **Review Findings** della story.

---

### `decision-needed` · fonte: `auditor`

**AC1 non determina l'ordine a parità di capienza.**

`RicercaDisponibilita.java:41` — `ORDER BY s.capienza ASC`

L'AC1 dice «ordinate per capienza crescente». Con due sale da 8 posti l'ordine è
quello che restituisce Postgres, che non è stabile fra due chiamate identiche.
L'utente che ricarica vede le sale scambiate.

Non lo patcho da solo: il criterio di spareggio è una scelta di prodotto.

1. Nome della sala (alfabetico) — prevedibile, ignora dove sei
2. Piano dell'utente prima — utile, ma serve il piano nel profilo
3. Lasciare indeterminato e togliere «ordinate» dall'AC1

---

### `patch` · fonte: `blind+edge`

**La ricerca enumera per esclusione: `no_show` continua a occupare la sala.**

`RicercaDisponibilita.java:34`

```diff
-   AND p.stato <> 'disdetta'
+   AND p.stato = 'attiva'
```

Il **Blind Hunter**, che vede solo il diff, l'ha segnalato come forma fragile:
un predicato per esclusione resta corretto solo finché nessuno aggiunge uno
stato.

L'**Edge Case Hunter**, che la spec non ce l'ha ma il progetto sì, è andato a
leggere e ha trovato che lo stato aggiunto c'è già: `architecture.md` § D4
elenca `attiva | disdetta | no_show`, e `StatoPrenotazione` lo ha nell'enum.

Conseguenza: una prenotazione decaduta per no-show passa il filtro e **continua a
occupare la sala**. È la funzionalità per cui il progetto esiste, nella query
principale.

L'**Acceptance Auditor** non l'ha segnalato, e ha ragione lui: rispetto agli AC
della 1.1 quel predicato è corretto.

---

### `patch` · fonte: `blind+edge`

**La traduzione dell'errore è troppo larga.**

`PrenotazioneService.java:78`

```java
} catch (DataIntegrityViolationException e) {
    throw new SalaGiaOccupataException(salaId, periodo);
}
```

Qualunque vincolo violato diventa «sala occupata». `architecture.md` § D3 lo
vieta esplicitamente: «non si intercetta genericamente `DataIntegrityViolation`:
si verifica il nome del vincolo».

Fix: controllare `prenotazione_no_overlap`, rilanciare il resto.

---

### `defer` · fonte: `edge`

**`PrenotazioneController` non mappa il timeout del database.**

`PrenotazioneController.java:52` — mappa 409 e 422, ogni altra eccezione esce
come 500 senza corpo. Pre-esistente allo scaffold, non introdotto da questa
story. Registrato in `deferred-work.md`.

---

### `dismiss` — 3, tutti dal Blind Hunter

Senza contesto sono ragionevoli, col contesto no:

- «manca la validazione null su `salaId`» — c'è, `@NotNull` sul DTO a monte
- «la query non ha un indice sul filtro temporale» — `V1__sale_e_prenotazioni.sql`
  crea l'indice GiST col vincolo di esclusione
- «4 ore è un magic number» — è l'AC4, con la costante `DURATA_MASSIMA`

Il Blind Hunter costa tre falsi positivi. Li paga volentieri: è lo stesso
reviewer che ha aperto il finding grosso.

---

**giulio** — Il secondo è grosso. Come ti è sfuggito mentre implementavi?

💻 Verificavo `AC1`: «solo le sale libere». Il predicato lo fa.

I test della 1.1 non seminano `no_show` — quello stato nasce nella 1.2. Con solo
prenotazioni `attiva` nel database i due filtri danno lo stesso risultato.

Corretto rispetto agli AC. Sbagliato rispetto al progetto.

---

**giulio** — E se non avessimo fatto la review?

💻 Il difetto sarebbe emerso nella 1.2 come «la liberazione non funziona».
Avremmo cercato nel job, che era corretto. Il difetto stava nella query della
story precedente, già approvata e chiusa.

---

**giulio** — Sul `decision-needed` prendi l'opzione 1, nome della sala. Non
abbiamo il piano nel profilo e non voglio aprire quel fronte adesso.

💻 Registrato. `ORDER BY s.capienza ASC, s.nome ASC`. L'item passa da
`decision-needed` a `patch`.

**Come vuoi gestire le 3 patch?**

1. **Applicale tutte** — le sistemo adesso, senza conferma una per una
2. **Lasciale come action item** — sono già scritte nella story
3. **Vai una per una** — dettaglio di ognuna prima di decidere

---

**giulio** — 1

💻 Applicate.

- `RicercaDisponibilita.java:34` — `stato = 'attiva'`
- `RicercaDisponibilita.java:41` — spareggio su `s.nome`
- `PrenotazioneService.java:78` — verifica del nome del vincolo

Test nuovi: 3.

- `ricercaEscludeSalaConPrenotazioneNoShow` — semina una `no_show`, verifica che
  la sala risulti libera
- `ordinamentoStabileAParitaDiCapienza` — due chiamate, stesso ordine
- `vincoloDiversoNonDiventaSalaOccupata` — viola un `CHECK`, si aspetta l'errore
  vero

`./mvnw verify` — **19 test, 0 failure** (16 preesistenti invariati + 3 nuovi).

Patch spuntate nella story. Status → `done`.

✅ `sprint-status.yaml` aggiornato: `1-1-ricerca-e-prenotazione` → `done`.

> **Review Complete!**
>
> **Story Status:** `done`
> **Issues Fixed:** 3
> **Action Items Created:** 0
> **Deferred:** 1
> **Dismissed:** 3

**Cosa vuoi fare adesso?**

1. **Prossima story** — `dev-story` prende la prossima `ready-for-dev`
2. **Rifare la review** — dopo aver toccato altro
3. **Chiudere**

---

**giulio** — 3.

---

## Cosa mostrare

- **I tre reviewer ricevono contesto diverso apposta.** È la riga che fa capire
  tutto: l'asimmetria è progettata, non un caso
- **Il verdetto contraddittorio**: l'Acceptance Auditor dice «spec soddisfatta» e
  nella stessa review escono due patch. Non è un bug del tool, è la definizione
  del problema che il talk racconta
- Il finding grosso è `blind+edge`, **due layer deduplicati**: uno vede la forma
  fragile, l'altro va a leggere il progetto e trova che lo stato esiste già
- **Il triage è in quattro caselle**, non in severità: `decision-needed`,
  `patch`, `defer`, `dismiss`. Il `decision-needed` è quello che si ferma e
  aspetta te, e non è un caso che sia l'unico che il tool non tocca da solo
- I tre `dismiss` del Blind Hunter: **il costo del contesto zero**, pagato
  volentieri
- La risposta a «come ti è sfuggito» — spiega perché il checkpoint esiste
- Le patch chiuse diventano tre test: la correzione è permanente

> **Se hai tempo per una sola chat, usa questa.** È l'unica che mostra il processo
> mentre intercetta qualcosa, che è la tesi del talk.
