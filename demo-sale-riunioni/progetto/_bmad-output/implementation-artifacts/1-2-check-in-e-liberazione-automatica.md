# Story 1.2: Check-in e liberazione automatica

Status: in progress

## Story

As il facility manager,
I want che le sale prenotate e mai usate tornino disponibili da sole,
so that la capacità non resti bloccata da riunioni che non si terranno.

## Acceptance Criteria

1. **Given** una prenotazione `attiva` la cui fascia iniziale è cominciata
   **When** il prenotante fa check-in entro 10 minuti
   **Then** la prenotazione risulta confermata e non decade più, mai.

2. **Given** una prenotazione `attiva` iniziata da più di 10 minuti senza check-in
   **When** il job di liberazione gira
   **Then** la prenotazione passa a `no_show`, viene scritta la riga in
   `prenotazione_evento`, e la sala torna prenotabile per le fasce residue.

3. **Given** una prenotazione con check-in registrato
   **When** il job di liberazione gira, anche più volte
   **Then** la prenotazione resta `attiva`. (NFR2: nessun falso positivo, mai)

4. **Given** lo stesso insieme di prenotazioni scadute
   **When** il job gira due volte di seguito
   **Then** il risultato è identico e non vengono scritti eventi duplicati.
   (idempotenza, da D4)

5. **Given** una prenotazione già `disdetta`, `conclusa` o `forzata`
   **When** il job gira
   **Then** non viene toccata.

6. **Given** una prenotazione di 3 fasce di cui la prima è decaduta
   **When** la sala torna prenotabile
   **Then** sono prenotabili solo le fasce non ancora iniziate.

## Tasks / Subtasks

- [x] Migrazione `V2__check_in.sql`: colonna `check_in_at`, indice sulle attive scadute (AC: 1, 2)
- [x] `PrenotazioneService.checkIn()` con finestra di 10 minuti (AC: 1)
- [x] Test unitari del check-in, uno per AC (AC: 1)
- [ ] `LiberazioneService.liberaScadute()` idempotente (AC: 2, 3, 4, 5)
- [ ] `LiberazioneJob` schedulato al minuto (AC: 2)
- [ ] Test Testcontainers: doppia esecuzione, stesso risultato (AC: 4)
- [ ] Test Testcontainers: prenotazione con check-in mai liberata (AC: 3)
- [ ] Verifica fasce residue prenotabili dopo la liberazione (AC: 6)

## Dev Notes

### Contesto e ruolo della story

È l'ipotesi del product brief. Se questa story non funziona, il progetto non ha
prodotto niente: tutto il resto è contorno intorno a questa.

### Il requisito che non ammette sconti

NFR2, ripetuto in PRD, in D4 e nella strategia di test: **se il check-in è
arrivato, la prenotazione non decade mai**. In caso di dubbio si tiene la
prenotazione. Un falso positivo qui vuol dire una persona che si trova la sala
data via mentre ci sta dentro: perde la fiducia nello strumento e non la
riacquista più.

### Idempotenza (D4)

Il job va scritto idempotente dal primo commit, non reso idempotente dopo.
Condizione di selezione: `stato = 'attiva' AND check_in_at IS NULL AND inizio <
now() - interval '10 minutes'`. L'update porta a `no_show` filtrando di nuovo
sullo stesso predicato, così due esecuzioni concorrenti non si sovrappongono.

### Fasce residue (AC 6)

La liberazione non cancella la prenotazione: la porta a `no_show`. Il vincolo D3
è filtrato su `stato = 'attiva'`, quindi la sala torna automaticamente
disponibile. Non serve toccare il vincolo. Va però verificato che le fasce **già
iniziate** non risultino prenotabili: non si prenota il passato.

### Vincoli vincolanti qui

- Il job non usa `pg_cron` né scheduler per prenotazione (D4)
- Tutto in UTC, come in 1.1
- Un test per AC, con il nome dell'AC

### References

- `_bmad-output/planning-artifacts/architecture.md` § D4, D6, Strategia di test
- `_bmad-output/planning-artifacts/prd.md` § FR3, FR4, NFR2
- `_bmad-output/planning-artifacts/ux-spec.md` § Check-in
- `_bmad-output/implementation-artifacts/1-1-ricerca-e-prenotazione-di-una-sala.md`
  § Dev Agent Record (il filtro sullo stato `attiva`, non `!= disdetta`)

## Dev Agent Record

### Agent Model Used

Amelia (bmad-agent-dev), skill `bmad-dev-story` — esecuzione in corso

### Completion Notes List

- Check-in implementato e verde. La finestra di 10 minuti è una costante di
  dominio come la durata massima della 1.1, per coerenza.
- Fermata prima del job: il piano prevedeva `@Scheduled` su singola istanza, ma in
  cluster l'applicazione gira su più repliche e il job partirebbe N volte.
  L'idempotenza di AC 4 lo rende innocuo, ma va confermato che sia accettabile o
  serve un lock. **Domanda aperta per l'umano.**

### File List

```
src/main/resources/db/migration/V2__check_in.sql                            (nuovo)
src/main/java/it/azienda/saleriunioni/prenotazione/PrenotazioneService.java (modificato)
src/main/java/it/azienda/saleriunioni/prenotazione/PrenotazioneController.java (modificato)
src/test/java/it/azienda/saleriunioni/prenotazione/CheckInTest.java         (nuovo)
```

## Change Log

| Data | Chi | Cosa |
|---|---|---|
| 2026-08-29 | Amelia (`CS`) | Story creata |
| 2026-08-29 | Giulio | Aggiunto AC 6 (fasce residue): la story diceva «la sala torna prenotabile» senza dire per quali fasce |
| 2026-08-30 | Amelia (`DS`) | Check-in implementato, 5 test verdi. Sospesa sulla domanda del job in cluster |
