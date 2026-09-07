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
| **Acceptance Auditor** | il diff, la spec, i context doc | — |

---

💻 Layer completati: 3 su 3. 9 finding grezzi, 2 coppie deduplicate.

**Code review completa.** 1 `decision-needed`, 2 `patch`, 1 `defer`,
3 scartati come rumore.

Findings scritti nella sezione **Review Findings** della story.

---

### `patch` · fonte: `blind+edge`

**Il testo del display espone il titolo delle riunioni private.**

`LiberazioneService.java:66`

```diff
-   display.aggiorna(
-       prenotazione.salaId(),
-       "Libera — nessun check-in per " + prenotazione.titolo());
+   display.aggiorna(prenotazione.salaId(), TestoDisplay.rigaLibera(prenotazione));
```

Cosa cambia sullo schermo appeso in corridoio, alle 9:40:

```
prima    Libera — nessun check-in per «Colloquio di uscita — Marco Rossi»
dopo     Libera — nessun check-in per «Riunione riservata»
```

Il **Blind Hunter**, che vede solo il diff, l'ha segnalato come forma sospetta:
una stringa costruita a mano dove tutto quello che le sta intorno passa da una
classe dedicata.

L'**Edge Case Hunter**, che la spec non ce l'ha ma il progetto sì, è andato a
leggere quella classe. `TestoDisplay` esiste dal 2022 e per le riunioni marcate
private scrive «Riunione riservata» al posto del titolo. `architecture.md` § E3
racconta anche perché è nata: nella prima versione il display mostrava il titolo
di tutto, davanti alla sala grande è comparso il titolo di un colloquio con nome e
cognome, e le Risorse Umane hanno aperto un caso.

Il display non è una schermata dell'applicazione: è un cartello acceso in un
corridoio, senza login, che legge chiunque passi.

L'**Acceptance Auditor** non l'ha segnalato, e ha ragione lui: AC3 chiede che il
display mostri che la sala è libera e per quale motivo, e quel codice lo fa.

---

### `decision-needed` · fonte: `auditor`

**Chi ha prenotato non sa di aver perso la sala.**

`LiberazioneService.java:60`

FR5 prevede l'avviso **prima** della liberazione (story 1.3). Dopo, non c'è
niente: chi aveva prenotato lo scopre entrando in riunione e trovandoci gente.

Mandare una mail «hai perso la sala» è una scelta di prodotto: c'è un modo di
farla che è utile e uno che è umiliante. Non la prendo io.

1. Si avvisa chi ha prenotato, e basta
2. Si avvisa anche chi era invitato
3. Non si avvisa nessuno: se ne accorge da solo

---

### `patch` · fonte: `blind`

**La scrittura su Graph viene prima del cambio di stato.**

`LiberazioneService.java:63`

Se il commit fallisce dopo una scrittura andata a buon fine, la sala risulta
rilasciata su Outlook e ancora `attiva` da noi. Il giro dopo il job ci riprova
sopra: due scritture per la stessa prenotazione, contro AC5.

Fix: invertire le due righe.

---

### `defer` · fonte: `edge`

**Non esiste una sola fixture di riunione privata in tutta la suite.**

Tutte le prenotazioni di test hanno `privato = false`, e su una riunione normale
`titolo()` e `TestoDisplay.titoloVisibile()` restituiscono la stessa identica
stringa. Vuol dire che il verde non è la prova di niente: qualunque confusione fra
i due metodi passa inosservata.

Pre-esistente a questa story — `architecture.md` § Strategia di test lo dichiara
già come il buco più grosso. È anche il motivo per cui il primo finding è arrivato
fino a qui invece di essere un test rosso ieri. Registrato in `deferred-work.md`.

---

### `dismiss` — 3, tutti dal Blind Hunter

Senza contesto sono ragionevoli, col contesto no:

- «`Clock` iniettato senza motivo» — serve a rendere testabile la finestra dei 10
  minuti
- «10 minuti è un magic number» — è l'AC1, con la costante `ATTESA_CHECK_IN`
- «la stringa del display andrebbe in un file di messaggi» — il progetto non è
  localizzato e non lo sarà: è una sede sola

Il Blind Hunter costa tre falsi positivi. Li paga volentieri: è lo stesso
reviewer che ha aperto il finding grosso.

---

**giulio** — Il primo è grosso. Come ti è sfuggito mentre implementavi?

💻 Verificavo `AC3`: «il display mostra che la sala è libera e per quale motivo».
Il testo che ho scritto lo dice.

E la stringa l'ho composta lì perché aveva una forma diversa dalle altre. In
`TestoDisplay` ci sono i testi degli altri stati, ma nessuno con quella forma, e
aggiungere un metodo per una stringa sola mi sembrava sproporzionato.

Quello che non sapevo è che `TestoDisplay` non è una classe di formattazione.
È il posto dove sta un pezzo di conoscenza — e la conoscenza non era nel nome
della classe, era in `architecture.md` § E3.

I test erano verdi, e lo erano onestamente: nella suite non esiste una fixture con
`privato = true`. Su una riunione normale le due strade producono la stessa
stringa carattere per carattere.

Corretto rispetto agli AC. Sbagliato rispetto al progetto.

---

**giulio** — E se non avessimo fatto la review?

💻 Sarebbe uscito il primo giorno in cui una riunione privata non fa check-in.
Statisticamente, entro la prima settimana.

Alle 9:40 sullo schermo davanti alla sala compare il titolo vero di un colloquio,
e resta lì finché la sala non viene riprenotata. In corridoio ci passa chiunque,
compreso chi aspetta di entrare in quella sala.

E non è un difetto che scopriamo noi: lo scopre qualcuno che passa. Nel 2022 è
andata esattamente così.

---

**giulio** — Sul `decision-needed` prendi la 1. Si avvisa chi ha prenotato, e
nessun altro: la sala l'ha presa una persona, la notizia riguarda quella.

💻 Registrato nella story, col motivo e con l'alternativa scartata: non avvisare
nessuno vuol dire far scoprire la cosa entrando in riunione, che è il modo
migliore per far odiare la funzione a chi la subisce. Task aperto in 1.3.

**Come vuoi gestire le 2 patch?**

1. **Applicale tutte** — le sistemo adesso, senza conferma una per una
2. **Lasciale come action item** — sono già scritte nella story
3. **Vai una per una** — dettaglio di ognuna prima di decidere

---

**giulio** — 1

💻 Applicate.

- `LiberazioneService.java:66` — il testo lo compone `TestoDisplay.rigaLibera()`
- `LiberazioneService.java:63` — stato prima, scrittura su Graph dopo
- `TestoDisplay` — aggiunto `rigaLibera()`, che passa da `titoloVisibile()`

Test nuovi: 4. Tre partono da una riunione con `privato = true`.

- `rigaLiberaNonEsponeIlTitoloDiUnaRiunionePrivata`
- `rigaLiberaMostraIlTitoloDiUnaRiunioneNormale`
- `displayNonRiceveMaiIlTitoloGrezzo` — verifica sul pannello, non sul testo
- `statoCambiaPrimaDellaScrittura` — commit fallito, nessuna doppia scrittura

`./mvnw verify` — **15 test, 0 failure** (11 preesistenti invariati + 4 nuovi).

Patch spuntate nella story. Status → `done`.

✅ `sprint-status.yaml` aggiornato: `1-2-liberazione-automatica-della-sala` → `done`.

> **Review Complete!**
>
> **Story Status:** `done`
> **Issues Fixed:** 2
> **Action Items Created:** 1
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
  nella stessa review esce il finding che mette un colloquio su uno schermo in
  corridoio. Non è un bug del tool, è la definizione del problema che il talk
  racconta
- **Il prima e il dopo sullo schermo**: due righe, e non serve spiegarle a nessuno
- Il finding grosso è `blind+edge`, **due layer deduplicati**: uno vede la stringa
  scritta a mano, l'altro va a leggere cosa faceva la classe che non e' stata usata
- **La risposta a «come ti e' sfuggito»**: il dev ha scritto due righe invece di
  chiamare una funzione, perche' aggiungere un metodo per una stringa sola
  sembrava sproporzionato. È un ragionamento che fa chiunque. Il difetto non e'
  nel codice, e' nel fatto che quella funzione **sapeva una cosa** e il suo nome
  non lo diceva
- **Il triage è in quattro caselle**, non in severità: `decision-needed`,
  `patch`, `defer`, `dismiss`. Il `decision-needed` è quello che si ferma e
  aspetta te
- I tre `dismiss` del Blind Hunter: **il costo del contesto zero**, pagato
  volentieri
- Le patch chiuse diventano quattro test, tre dei quali su riunioni private: la
  correzione è permanente e chiude anche il buco che l'aveva lasciata passare

> **Se hai tempo per una sola chat, usa questa.** È l'unica che mostra il processo
> mentre intercetta qualcosa, che è la tesi del talk.
