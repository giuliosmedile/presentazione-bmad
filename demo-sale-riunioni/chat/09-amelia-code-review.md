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

**giulio** — CR sulla 1.2.

💻 `CR`. Sessione nuova: non riuso il ragionamento con cui l'ho implementata.

Contesto raccolto:

- diff — 8 file, `git diff main...HEAD`
- spec — `1-2-liberazione-automatica-della-sala.md`
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

### `patch` · fonte: `blind+edge`

**Il rilascio opera sulla serie, non sull'occorrenza.**

`LiberazioneService.java:57`

```diff
-   outlook.annulla(prenotazione.idEventoGraph());
+   outlook.rilasciaSala(prenotazione);
```

Il **Blind Hunter**, che vede solo il diff, l'ha segnalato come forma sospetta:
un identificativo che arriva da una riga di database e finisce dritto in una
`DELETE`, senza che nessuno guardi di che tipo sia quella riga.

L'**Edge Case Hunter**, che la spec non ce l'ha ma il progetto sì, è andato a
leggere cosa c'è dentro quel campo. `architecture.md` § E4:

| `tipo` | cosa c'è in `id_evento_graph` |
|---|---|
| `SINGOLA` | l'id dell'evento |
| `SERIE` | l'id della serie |
| `OCCORRENZA` | **l'id della serie** |

Stessa cosa detta dallo schema (`V3__sincronizzazione.sql`, indice unico su
`(id_evento_graph, inizio)`) e dal commento in `MappaturaEventoGraph`.

Conseguenza: la prima occorrenza non usata di una serie fa partire una `DELETE`
sull'id della serie. **Sparisce la serie intera** — tutte le occorrenze, passate e
future, dai calendari di tutti i partecipanti. Su Exchange non si annulla.

Le occorrenze sono il 70% delle prenotazioni di questo sistema.

L'**Acceptance Auditor** non l'ha segnalato, e ha ragione lui: rispetto ad AC3 —
«la sala non risulta più occupata su Outlook» — quel codice fa esattamente quello
che c'è scritto.

---

### `decision-needed` · fonte: `edge+auditor`

**Rilasciare la sala fa partire una notifica che sfiora una regola di prodotto.**

`PrenotazioneOutlookClient.java:71`

Togliere la sala da un evento fa mandare a Exchange un messaggio all'organizzatore:
«la sala ha rifiutato l'invito». Non è sopprimibile, non la mandiamo noi.

`project-context.md` § Regole di prodotto: *nessuna comunicazione automatica che
nomini una persona*. Questa non nomina nessuno, ma arriva a una persona sola e
per un motivo che la riguarda.

Non lo decido io: è dove passa il confine di quella regola, e il confine l'hai
tirato tu.

1. Si rilascia, e la notifica si accetta
2. Si marca `no_show` solo da noi, senza toccare Outlook
3. Si rilascia e si prova a sopprimere la notifica (non risulta possibile con i
   permessi che abbiamo)

---

### `patch` · fonte: `blind`

**La scrittura su Graph viene prima del cambio di stato.**

`LiberazioneService.java:55`

Se il commit fallisce dopo una scrittura andata a buon fine, la sala risulta
rilasciata su Outlook e ancora `attiva` da noi. Il giro dopo il job ci riprova
sopra: due scritture per la stessa prenotazione, contro AC4.

Fix: invertire le due righe.

---

### `defer` · fonte: `edge`

**Non esiste una sola fixture di tipo `OCCORRENZA` in tutta la suite.**

Tutte le prenotazioni di test sono singole: le fixture sono del 2022 e le serie
sono arrivate nel 2024. Pre-esistente a questa story — `architecture.md`
§ Strategia di test lo dichiara già come il buco più grosso.

È anche il motivo per cui il primo finding è arrivato fino a qui invece di essere
un test rosso ieri. Registrato in `deferred-work.md`.

---

### `dismiss` — 3, tutti dal Blind Hunter

Senza contesto sono ragionevoli, col contesto no:

- «`Clock` iniettato senza motivo» — serve a rendere testabile la finestra dei 10
  minuti
- «10 minuti è un magic number» — è l'AC1, con la costante `ATTESA_CHECK_IN`
- «manca il controllo di null su `idEventoGraph`» — la colonna è `NOT NULL` dal 2022

Il Blind Hunter costa tre falsi positivi. Li paga volentieri: è lo stesso
reviewer che ha aperto il finding grosso.

---

**giulio** — Il primo è grosso. Come ti è sfuggito mentre implementavi?

💻 Verificavo `AC3`: «la sala non risulta più occupata su Outlook». La `DELETE`
lo ottiene.

E il metodo l'ho riusato perché me lo dice il progetto: `architecture.md` § D2
dice che ogni scrittura verso Graph passa da `PrenotazioneOutlookClient` e che il
client si riusa, non si riscrive. L'ho letto e l'ho fatto.

Quello che non ho letto è il capitolo E4, sessanta righe più su nello stesso
documento, che dice cosa contiene il campo che stavo passando.

I test erano verdi, e lo erano onestamente: nella suite non esiste una fixture di
tipo `OCCORRENZA`. Con sole prenotazioni singole i due percorsi fanno la stessa
cosa.

Corretto rispetto agli AC. Sbagliato rispetto al progetto.

---

**giulio** — E se non avessimo fatto la review?

💻 Sarebbe uscito il martedì mattina. Una serie che nessuno usa più viene
liberata alle 9:40, e insieme all'occorrenza sparisce la ricorrenza: allo
stand-up della settimana dopo non si presenta nessuno, perché l'invito non c'è
più nel calendario di nessuno.

Nessuno lo collegherebbe a noi. La segnalazione arriverebbe come «Outlook ha
cancellato le riunioni», e il codice da cui parte è un job schedulato che gira
di notte su un'altra funzionalità.

E non si torna indietro: gli eventi cancellati su Exchange non si recuperano da
qui.

---

**giulio** — Sul `decision-needed` prendi la 1. La notifica riguarda la sala, non
la persona, e senza quella l'organizzatore scopre di non avere più la sala
entrando in riunione.

💻 Registrato nella story, col motivo e con l'alternativa scartata: la 2
renderebbe la liberazione invisibile a chi cerca una sala dal calendario, cioè
quasi tutti. L'item passa da `decision-needed` a `patch`.

**Come vuoi gestire le 2 patch?**

1. **Applicale tutte** — le sistemo adesso, senza conferma una per una
2. **Lasciale come action item** — sono già scritte nella story
3. **Vai una per una** — dettaglio di ognuna prima di decidere

---

**giulio** — 1

💻 Applicate.

- `LiberazioneService.java:57` — `outlook.rilasciaSala(prenotazione)`
- `LiberazioneService.java:55` — stato prima, scrittura su Graph dopo
- `PrenotazioneOutlookClient` — aggiunto `rilasciaSala()`, che sulle occorrenze
  risolve l'id vero prima di scrivere

Test nuovi: 4, e tutti e quattro partono da una prenotazione di tipo
`OCCORRENZA`.

- `rilascioSuOccorrenzaNonToccaLaSerie` — verifica che la chiamata a Graph non
  usi l'id della serie
- `rilascioSuOccorrenzaRisolveIdIstanza`
- `rilascioSuSingolaRestaInvariato` — la strada vecchia non cambia comportamento
- `statoCambiaPrimaDellaScrittura` — commit fallito, nessuna doppia scrittura

`./mvnw verify` — **15 test, 0 failure** (11 preesistenti invariati + 4 nuovi).

Patch spuntate nella story. Status → `done`.

✅ `sprint-status.yaml` aggiornato: `1-2-liberazione-automatica-della-sala` → `done`.

> **Review Complete!**
>
> **Story Status:** `done`
> **Issues Fixed:** 2
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
  nella stessa review esce il finding che cancella le riunioni di tutti. Non è un
  bug del tool, è la definizione del problema che il talk racconta
- Il finding grosso è `blind+edge`, **due layer deduplicati**: uno vede la forma
  sospetta, l'altro va a leggere il progetto e trova cosa c'e' dentro quel campo
- **La risposta a «come ti e' sfuggito»**: il dev ha riusato un metodo che
  c'era gia', perche' glielo diceva l'architettura. Riusare era la cosa giusta.
  Il difetto non e' nel codice scritto, e' nel contesto mancante
- **Il triage è in quattro caselle**, non in severità: `decision-needed`,
  `patch`, `defer`, `dismiss`. Il `decision-needed` è quello che si ferma e
  aspetta te, e qui si ferma **su una regola che hai scritto tu sei slide prima**
- I tre `dismiss` del Blind Hunter: **il costo del contesto zero**, pagato
  volentieri
- Le patch chiuse diventano quattro test, tutti su occorrenze: la correzione è
  permanente e chiude anche il buco che l'aveva lasciata passare

> **Se hai tempo per una sola chat, usa questa.** È l'unica che mostra il processo
> mentre intercetta qualcosa, che è la tesi del talk.
