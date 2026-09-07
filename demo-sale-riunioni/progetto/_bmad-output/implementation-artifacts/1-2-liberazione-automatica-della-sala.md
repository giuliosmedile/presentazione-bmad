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
   ricerca per le fasce residue.

2. **Given** una prenotazione con il check-in fatto
   **When** gira il job
   **Then** la prenotazione non viene toccata, qualunque sia l'orario.

3. **Given** una sala appena liberata
   **When** il display si aggiorna
   **Then** mostra che la sala è libera **e per quale motivo**: chi passa in
   corridoio deve capire che è libera davvero e non per un errore dello schermo.

4. **Given** una prenotazione portata a `no_show`
   **When** la transizione è avvenuta
   **Then** la sala non risulta più occupata su Outlook: chi cerca una sala dal
   calendario vede la stessa cosa che vede sul display.

5. **Given** il job che gira due volte nello stesso minuto
   **When** le due esecuzioni si sovrappongono
   **Then** il risultato è identico a una sola esecuzione e non partono due
   scritture verso Graph per la stessa prenotazione.

## Tasks / Subtasks

- [x] `LiberazioneJob` schedulato ogni minuto (AC: 1, 5)
  - [x] `@SchedulerLock` per non sovrapporre le esecuzioni sulle repliche
- [x] `PrenotazioneRepository.attiveIniziateDaPiuDi()` (AC: 1, 2)
- [x] `LiberazioneService.liberaLeSaleNonUsate()` (AC: 1, 2, 5)
  - [x] Transizione a `no_show` con riga in `prenotazione_evento` (AC: 1)
- [x] Rilascio della sala su Graph (AC: 4)
- [x] Riga del display per la sala liberata (AC: 3)
- [x] Test unitari, uno per AC
- [x] Test WireMock sulle risposte Graph salvate (AC: 4)

### Review Findings

_Code review avversariale (Blind Hunter + Edge Case Hunter + Acceptance Auditor),
2026-09-02, modalità `full`. Verdetto Acceptance Auditor: **spec soddisfatta**
(AC1–AC5, tutti i checkbox Task). Triage: 1 `decision-needed`, 2 `patch`,
1 `defer`, 3 scartati come rumore._

- [x] [Review][Patch] APPLICATO (2026-09-02) — La riga del display era composta
  dentro `LiberazioneService`:
  `"Libera — nessun check-in per " + prenotazione.titolo()`.
  `titolo()` è il dato grezzo dello specchio Graph, **non testo da mostrare**: per
  le riunioni marcate private sul display finisce il titolo vero. Il display è un
  cartello acceso in corridoio (`architecture.md` § E3, commento in
  `V3__eventi_privati.sql`, commento sulla colonna nel database), e dal 2022
  esiste `TestoDisplay` proprio perché era già successo una volta.
  Corretto spostando la composizione del testo in `TestoDisplay.rigaLibera()`.
  Fonte `blind+edge`: il Blind Hunter ha visto una stringa costruita a mano dove
  intorno tutto passa da una classe di formattazione; l'Edge Case Hunter è andato
  a leggere quella classe e ha trovato cosa fa e perché. L'Acceptance Auditor
  **non** l'ha segnalato, ed è coerente: AC3 chiede che il display dica perché la
  sala è libera, e quel codice lo dice. [LiberazioneService.java:66]

- [x] [Review][Decision] APPLICATO (2026-09-02) — Quando la sala viene liberata,
  chi l'aveva prenotata non lo sa. Se ne accorge entrando in riunione e trovando
  gente dentro. Il PRD non dice se avvisarlo: FR5 prevede l'avviso *prima* della
  liberazione (story 1.3), non dopo. Mandare una mail «hai perso la sala» è una
  scelta di prodotto, non una scelta tecnica, e il tool non la prende da solo.
  **Risoluzione decisa (2026-09-02, Giulio): si avvisa, e solo chi ha prenotato.**
  Nessun altro destinatario, nemmeno i partecipanti: la sala l'ha presa una
  persona, e la notizia riguarda quella. Scartata l'alternativa «non avvisare»:
  scoprire di non avere più la sala mentre ci si entra è il modo migliore per far
  odiare la funzione a chi la subisce. Aperta come task in 1.3, che l'avviso ce
  l'ha già. [LiberazioneService.java:60]

- [x] [Review][Patch] APPLICATO (2026-09-02) — La scrittura su Graph stava **prima**
  del cambio di stato locale. Se il commit falliva dopo una scrittura andata a
  buon fine, la sala risultava rilasciata su Outlook e ancora `attiva` da noi, e
  il giro successivo del job ci riprovava sopra: due scritture per la stessa
  prenotazione, contro AC5. Invertito l'ordine, con il commento che dice perché.
  Fonte `blind`. [LiberazioneService.java:63]

- [x] [Review][Defer] Nella suite non esiste **una sola fixture di riunione
  privata**. Tutte le prenotazioni di test hanno `privato = false`, e su una
  riunione normale `titolo()` e `TestoDisplay.titoloVisibile()` restituiscono la
  stessa identica stringa: qualunque confusione fra i due passa inosservata. È il
  buco che ha lasciato arrivare il rilievo qui sopra fino alla review, ed è
  pre-esistente a questa story — `architecture.md` § Strategia di test lo
  dichiara già. Registrato in `deferred-work.md`, promosso a story 2.1.
  [architecture.md § Strategia di test]

_Scartati come rumore, tutti dal Blind Hunter (che per costruzione non ha
contesto): «`Clock` iniettato senza motivo» (serve a rendere testabile la finestra
dei 10 minuti); «10 minuti è un magic number» (è l'AC1, costante di dominio
`ATTESA_CHECK_IN`); «la stringa del display andrebbe esternalizzata in un file di
messaggi» (il progetto non è localizzato e non lo sarà: è una sede)._

## Dev Notes

### Contesto e ruolo della story

È la story per cui l'epic esiste. La 1.1 ha portato il check-in, cioè il segnale;
questa è quella che agisce sul segnale. Da qui in poi il sistema **scrive fuori da
sé**: su Outlook e sui display. Fino alla 1.1 leggeva soltanto, ed è un cambio di
categoria, non di funzionalità.

### Questo non è un progetto vuoto

Il codice ha quattro anni e chi l'ha scritto non c'è più. Prima di scrivere un
metodo, cercare se esiste: `architecture.md` § Cosa passo ad Amelia.

### Il display non è una schermata

Da `architecture.md` § E3, e vale la pena rileggerlo prima di scrivere qualunque
testo che finisca su uno schermo:

> I sei display non sono un'interfaccia dell'applicazione. Sono schermi accesi in
> un corridoio, senza login, che legge chiunque passi.

Una riunione può essere marcata **privata** su Outlook (colonna `privato`, dal
2022). Per il testo mostrato all'esterno si usa `TestoDisplay`, mai
`Prenotazione.titolo()`.

**Sta nel progetto. Non sta nei criteri di accettazione di questa story.**

### Idempotenza

Da NFR2 e da D1: due esecuzioni ravvicinate devono dare lo stesso risultato. Il
`@SchedulerLock` copre la sovrapposizione fra repliche; l'ordine delle operazioni
dentro `libera()` copre il resto.

### Vincoli vincolanti qui

- Niente Lombok (project-context)
- I test si chiamano come l'AC che coprono
- Mai chiamare Graph nei test: WireMock con le risposte salvate

### References

- `_bmad-output/planning-artifacts/architecture.md` § E1, E3, E4, D1, D2, Strategia di test
- `_bmad-output/planning-artifacts/prd.md` § FR3, FR4, NFR2
- `_bmad-output/project-context.md` § Regole di prodotto, Convenzioni di codice
- `docs/glossario-dominio.md` § Liberazione, Display, Riunione privata

## Dev Agent Record

### Agent Model Used

Amelia (bmad-agent-dev), skill `bmad-dev-story`

### Completion Notes List

- La riga del display l'ho scritta dentro `LiberazioneService`: il testo ha una
  forma diversa da quelli che c'erano già, e mi sembrava sproporzionato aggiungere
  un metodo a `TestoDisplay` per una stringa sola. In review è stato sollevato ed
  è stato corretto.
- AC5 non era testabile con una sola esecuzione: il test lancia due volte il job
  con lo stesso `Clock` fisso e verifica che le scritture verso Graph siano una.
- AC4: la transizione locale non viene annullata se Graph fallisce. La scelta è di
  D1 (riconciliazione al giro di sincronizzazione) e non mia, ma la segnalo perché
  in review sembra un errore e non lo è.

### File List

```
src/main/java/it/azienda/saleriunioni/liberazione/LiberazioneJob.java       (nuovo)
src/main/java/it/azienda/saleriunioni/liberazione/LiberazioneService.java   (nuovo)
src/main/java/it/azienda/saleriunioni/display/TestoDisplay.java             (modificato)
src/main/java/it/azienda/saleriunioni/outlook/PrenotazioneOutlookClient.java (modificato)
src/main/java/it/azienda/saleriunioni/prenotazione/PrenotazioneRepository.java (modificato)
src/main/resources/db/migration/V7__lock_scheduler.sql                      (nuovo)
src/test/java/it/azienda/saleriunioni/liberazione/LiberazioneServiceTest.java (nuovo)
src/test/java/it/azienda/saleriunioni/liberazione/LiberazioneIdempotenzaTest.java (nuovo)
src/test/java/it/azienda/saleriunioni/display/TestoDisplayTest.java          (modificato)
```

## Change Log

| Data | Chi | Cosa |
|---|---|---|
| 2026-09-01 | Amelia (`CS`) | Story creata dal piano di sprint |
| 2026-09-01 | Giulio | Aggiunto AC 5 (doppia esecuzione): mancava, e senza il comportamento sarebbe stato deciso dal primo che lo implementava |
| 2026-09-02 | Amelia (`DS`) | Implementazione, 11 test verdi, status -> review |
| 2026-09-02 | Amelia (`CR`) | Code review avversariale: 1 decision-needed, 2 patch, 1 defer, 3 dismiss |
| 2026-09-02 | Giulio | Decisione sul `decision-needed`: il display scrive solo «Libera», il motivo resta nel report interno |
| 2026-09-02 | Amelia (`CR`) | 2 patch applicate, 4 test nuovi (di cui 3 su riunioni private), 15 verdi, status -> done |
