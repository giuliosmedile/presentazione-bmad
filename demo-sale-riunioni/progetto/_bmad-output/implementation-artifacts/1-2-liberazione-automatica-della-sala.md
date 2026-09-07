# Story 1.2: Liberazione automatica della sala non usata

Status: done

## Story

As un dipendente che cerca una sala e le trova tutte occupate,
I want che una sala prenotata e non usata torni libera da sola,
so that il calendario dica la verità su cosa è davvero occupato.

## Acceptance Criteria

1. **Given** una prenotazione attiva iniziata da più di 10 minuti e senza check-in
   **When** gira il job di liberazione
   **Then** la prenotazione passa a `no_show` e la sala risulta libera nella
   ricerca e sul display per le fasce residue.

2. **Given** una prenotazione con il check-in fatto
   **When** gira il job
   **Then** la prenotazione non viene toccata, qualunque sia l'orario.

3. **Given** una prenotazione portata a `no_show`
   **When** la transizione è avvenuta
   **Then** la sala non risulta più occupata **su Outlook** per quelle fasce: chi
   cerca una sala dal calendario deve vedere la stessa cosa che vede sul display.

4. **Given** il job che gira due volte nello stesso minuto
   **When** le due esecuzioni si sovrappongono
   **Then** il risultato è identico a una sola esecuzione e non partono due
   scritture verso Graph per la stessa prenotazione.

5. **Given** Graph che risponde con errore alla scrittura
   **When** il rilascio fallisce
   **Then** la transizione locale resta valida e l'errore è loggato con l'id della
   prenotazione. La riconciliazione è del job di sincronizzazione, non di questo.

## Tasks / Subtasks

- [x] `LiberazioneJob` schedulato ogni minuto (AC: 1, 4)
  - [x] `@SchedulerLock` per non sovrapporre le esecuzioni sulle repliche
- [x] `PrenotazioneRepository.attiveIniziateDaPiuDi()` (AC: 1, 2)
- [x] `LiberazioneService.liberaLeSaleNonUsate()` (AC: 1, 2, 4)
  - [x] Transizione a `no_show` con riga in `prenotazione_evento` (AC: 1)
- [x] Rilascio della sala su Graph (AC: 3, 5)
  - [x] Gestione dell'errore Graph senza rollback della transizione locale (AC: 5)
- [x] Test unitari, uno per AC
- [x] Test WireMock sulle risposte Graph salvate (AC: 3, 5)

### Review Findings

_Code review avversariale (Blind Hunter + Edge Case Hunter + Acceptance Auditor),
2026-09-02, modalità `full`. Verdetto Acceptance Auditor: **spec soddisfatta**
(AC1–AC5, tutti i checkbox Task). Triage: 1 `decision-needed`, 2 `patch`,
1 `defer`, 3 scartati come rumore._

- [x] [Review][Patch] APPLICATO (2026-09-02) — Il rilascio chiamava
  `outlook.annulla(prenotazione.idEventoGraph())`. Per una riga di tipo
  `OCCORRENZA` quel campo **contiene l'id della serie**, non quello
  dell'occorrenza (`architecture.md` § E4, indice unico in
  `V3__sincronizzazione.sql`, commento in `MappaturaEventoGraph`): la chiamata
  cancellava la serie intera, tutte le occorrenze passate e future, dai calendari
  di tutti i partecipanti e senza annullamento possibile. Le occorrenze sono il
  70% delle prenotazioni. Sostituito con `outlook.rilasciaSala(prenotazione)`,
  che sulle occorrenze risolve l'id vero prima di scrivere.
  Fonte `blind+edge`: il Blind Hunter ha visto nel diff un id passato a una
  `DELETE` senza controllo del tipo, l'Edge Case Hunter è andato a leggere il
  progetto e ha trovato cosa contiene quel campo. L'Acceptance Auditor **non**
  l'ha segnalato, ed è coerente: rispetto agli AC di questa story quel codice è
  corretto, e i test lo confermano perché nessuna fixture è un'occorrenza.
  [LiberazioneService.java:57]

- [x] [Review][Decision] APPLICATO (2026-09-02) — Rilasciare la sala fa partire da
  Outlook una notifica all'organizzatore («la sala ha rifiutato l'invito»). Non è
  sopprimibile: la manda Exchange, non noi. `project-context.md` § Regole di
  prodotto vieta le comunicazioni automatiche che nominano una persona — questa
  non nomina nessuno, ma arriva a una persona sola e per un motivo che la
  riguarda. Serve una decisione umana, il tool non la prende.
  **Risoluzione decisa (2026-09-02, Giulio): si rilascia, e la notifica va bene.**
  Riguarda la sala, non chi l'ha prenotata, e senza quella l'organizzatore
  scoprirebbe di non avere più la sala entrando in riunione. Scartata
  l'alternativa «marcare `no_show` solo da noi, senza toccare Outlook»: renderebbe
  la liberazione invisibile a chi cerca una sala dal calendario, cioè quasi tutti.
  [PrenotazioneOutlookClient.java:71]

- [x] [Review][Patch] APPLICATO (2026-09-02) — La scrittura su Graph stava **prima**
  del cambio di stato locale. Se il commit falliva dopo una scrittura andata a
  buon fine, la sala risultava rilasciata su Outlook e ancora `attiva` da noi, e
  il giro successivo del job ci riprovava sopra: due scritture per la stessa
  prenotazione, contro AC4. Invertito l'ordine, con il commento che dice perché.
  Fonte `blind`. [LiberazioneService.java:55]

- [x] [Review][Defer] Nella suite non esiste **una sola fixture di tipo
  `OCCORRENZA`**. Tutte le prenotazioni di test sono singole, perché quando i test
  sono stati scritti le serie non erano sincronizzate. È il buco che ha lasciato
  passare il rilievo qui sopra fino alla review, ed è pre-esistente a questa
  story: `architecture.md` § Strategia di test lo dichiara già. Registrato in
  `deferred-work.md`, promosso a story 2.1. [architecture.md § Strategia di test]

_Scartati come rumore, tutti dal Blind Hunter (che per costruzione non ha
contesto): «`Clock` iniettato senza motivo» (serve a rendere testabile la finestra
dei 10 minuti); «10 minuti è un magic number» (è l'AC1, costante di dominio
`ATTESA_CHECK_IN`); controllo di null su `idEventoGraph` (la colonna è `NOT NULL`
dal 2022)._

## Dev Notes

### Contesto e ruolo della story

È la story per cui l'epic esiste. La 1.1 ha portato il check-in, cioè il segnale;
questa è quella che agisce sul segnale. Da qui in poi il sistema **scrive** su
Outlook: fino alla 1.1 leggeva soltanto, ed è un cambio di categoria, non di
funzionalità.

### Questo non è un progetto vuoto

Il codice ha quattro anni e chi l'ha scritto non c'è più. Prima di scrivere un
metodo, cercare se esiste: `architecture.md` § Cosa passo ad Amelia.

Per la scrittura verso Graph il punto è uno solo, `PrenotazioneOutlookClient`
(`project-context.md`, convenzioni). Non aprire una seconda strada: decisione D2.

### Lo specchio locale e la chiave di sincronizzazione

Da `architecture.md` § E4, e vale la pena rileggerlo prima di toccare qualsiasi
cosa che scriva su Graph:

| `tipo` | cosa c'è in `id_evento_graph` |
|---|---|
| `SINGOLA` | l'id dell'evento |
| `SERIE` | l'id della serie |
| `OCCORRENZA` | **l'id della serie** |

`id_evento_graph` è una chiave di sincronizzazione, non un indirizzo a cui
scrivere.

**Sta nel progetto. Non sta nei criteri di accettazione di questa story.**

### Idempotenza

Da NFR2 e da D1: due esecuzioni ravvicinate devono dare lo stesso risultato. Il
`@SchedulerLock` copre la sovrapposizione fra repliche; l'ordine delle operazioni
dentro `libera()` copre il resto.

### Vincoli vincolanti qui

- Niente Lombok (project-context)
- I test si chiamano come l'AC che coprono
- Mai chiamare Graph nei test: WireMock con le risposte salvate (architecture,
  Strategia di test)

### References

- `_bmad-output/planning-artifacts/architecture.md` § E1, E4, E5, D1, D2, Strategia di test
- `_bmad-output/planning-artifacts/prd.md` § FR3, NFR2
- `_bmad-output/project-context.md` § Regole di prodotto, Convenzioni di codice
- `docs/glossario-dominio.md` § Liberazione, Serie, Occorrenza

## Dev Agent Record

### Agent Model Used

Amelia (bmad-agent-dev), skill `bmad-dev-story`

### Completion Notes List

- Il rilascio su Graph è stato implementato riusando `PrenotazioneOutlookClient`
  come chiede D2. Il metodo `annulla()` c'era già e faceva esattamente quello che
  serviva: cancellare l'evento dalla casella della sala.
- AC4 non era testabile con una sola esecuzione: il test lancia due volte il job
  con lo stesso `Clock` fisso e verifica che le scritture verso Graph siano una.
- AC5: la transizione locale non viene annullata se Graph fallisce. La scelta è
  di D1 (riconciliazione al giro di sincronizzazione) e non mia, ma la segnalo
  perché in review sembra un errore e non lo è.
- Non ho toccato `MappaturaEventoGraph`. Il commento sulla chiave di
  sincronizzazione l'ho letto dopo, in review.

### File List

```
src/main/java/it/azienda/saleriunioni/liberazione/LiberazioneJob.java       (nuovo)
src/main/java/it/azienda/saleriunioni/liberazione/LiberazioneService.java   (nuovo)
src/main/java/it/azienda/saleriunioni/outlook/PrenotazioneOutlookClient.java (modificato)
src/main/java/it/azienda/saleriunioni/prenotazione/PrenotazioneRepository.java (modificato)
src/main/resources/db/migration/V7__lock_scheduler.sql                      (nuovo)
src/test/java/it/azienda/saleriunioni/liberazione/LiberazioneServiceTest.java (nuovo)
src/test/java/it/azienda/saleriunioni/liberazione/LiberazioneIdempotenzaTest.java (nuovo)
src/test/java/it/azienda/saleriunioni/outlook/PrenotazioneOutlookClientTest.java (nuovo)
```

## Change Log

| Data | Chi | Cosa |
|---|---|---|
| 2026-09-01 | Amelia (`CS`) | Story creata dal piano di sprint |
| 2026-09-01 | Giulio | Aggiunto AC 5 (errore Graph): mancava, e senza il comportamento sarebbe stato deciso dal primo che lo implementava |
| 2026-09-02 | Amelia (`DS`) | Implementazione, 11 test verdi, status -> review |
| 2026-09-02 | Amelia (`CR`) | Code review avversariale: 1 decision-needed, 2 patch, 1 defer, 3 dismiss |
| 2026-09-02 | Giulio | Decisione sul `decision-needed`: si rilascia la sala, la notifica di Outlook va bene |
| 2026-09-02 | Amelia (`CR`) | 2 patch applicate, 4 test nuovi (di cui 2 su occorrenze), 15 verdi, status -> done |
