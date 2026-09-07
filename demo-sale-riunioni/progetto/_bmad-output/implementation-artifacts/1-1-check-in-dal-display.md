# Story 1.1: Check-in dal display

Status: done

## Story

As chi è entrato in sala per la riunione,
I want confermare con un tocco che la sala è occupata davvero,
so that il sistema sappia distinguere una sala usata da una prenotata e basta.

## Acceptance Criteria

1. **Given** una prenotazione attiva la cui fascia iniziale è cominciata
   **When** qualcuno tocca «Sono qui» sul display della sala
   **Then** il check-in è registrato con l'orario e la prenotazione non decade
   più, mai.

2. **Given** una prenotazione non ancora iniziata
   **When** il display viene toccato
   **Then** il check-in è rifiutato e il display spiega da che ora sarà possibile.

3. **Given** una prenotazione iniziata da più di 10 minuti
   **When** il display viene toccato
   **Then** il check-in è comunque accettato: la finestra dei 10 minuti riguarda
   la liberazione, non il diritto di entrare.

4. **Given** un display senza rete
   **When** qualcuno tocca «Sono qui»
   **Then** il tocco viene messo in coda locale e inviato alla riconnessione, con
   l'orario del tocco e non quello dell'invio.

5. **Given** il display di una sala libera
   **When** nessuna prenotazione è in corso
   **Then** il tasto non compare.

## Tasks / Subtasks

- [x] Migrazione `V6__check_in.sql`: colonna `check_in_at` e indice sulle attive (AC: 1)
- [x] `DisplayController.checkIn()` senza autenticazione (AC: 1, 2, 3, D5)
- [x] Coda locale del display e invio differito (AC: 4)
- [x] Stato del tasto sul template del display (AC: 5)
- [x] Test unitari, uno per AC
- [x] Test end-to-end del display con rete assente (AC: 4)

### Review Findings

_Code review avversariale, 2026-08-31, modalità `full`. Verdetto Acceptance
Auditor: spec soddisfatta. Triage: 1 `patch`, 2 scartati come rumore._

- [x] [Review][Patch] APPLICATO (2026-08-31) — Il check-in differito (AC4) usava
  l'orario di arrivo al server e non quello del tocco: una riconnessione dopo
  venti minuti registrava un check-in fuori finestra. `architecture.md` § D5 dice
  che il display non autentica ma **deve** essere affidabile sul tempo. Corretto
  passando l'istante del tocco nel corpo della richiesta. Fonte `blind+edge`.
  [DisplayController.java:38]

## Dev Notes

### Contesto e ruolo della story

È il segnale. Senza check-in la 1.2 non ha niente su cui decidere, ed è per questo
che va per prima anche se da sola non produce nessun effetto visibile.

### Il requisito che non ammette sconti

NFR2, ripetuto in PRD, in D1 e nella strategia di test: **se il check-in è
arrivato, la prenotazione non decade mai.** In caso di dubbio si tiene la
prenotazione. Un falso positivo qui vuol dire una persona che si vede dare via la
sala mentre ci sta dentro: perde la fiducia nello strumento e non la riacquista.

### Perché il display non autentica

Decisione D5. Chi è in sala ha già la sala; chiedergli le credenziali su un tablet
appeso al muro aggiunge attrito a un gesto che deve costare un secondo. L'abuso
possibile — chi passa e tocca — è innocuo.

### Vincoli vincolanti qui

- Tutto in UTC, conversione solo nel controller
- Un test per AC, con il nome dell'AC
- Il display non scrive su Graph: questa story non tocca Outlook

### References

- `_bmad-output/planning-artifacts/architecture.md` § D4, D5, Strategia di test
- `_bmad-output/planning-artifacts/prd.md` § FR2, NFR2
- `_bmad-output/planning-artifacts/ux-spec.md` § Display di sala
- `docs/glossario-dominio.md` § Check-in

## Dev Agent Record

### Agent Model Used

Amelia (bmad-agent-dev), skill `bmad-dev-story`

### Completion Notes List

- La coda locale del display è in `localStorage`: il display è un browser in
  kiosk, non un'app. Alternativa scartata: nessuna coda e tocco perso.
- AC3 sembra in contraddizione con la liberazione ed è invece il caso più
  frequente in assoluto: la gente entra in ritardo. Chiesto conferma prima di
  implementarlo, confermato.

### File List

```
src/main/resources/db/migration/V6__check_in.sql                            (nuovo)
src/main/java/it/azienda/saleriunioni/display/DisplayController.java        (modificato)
src/main/resources/templates/display.html                                   (modificato)
src/test/java/it/azienda/saleriunioni/display/CheckInTest.java              (nuovo)
```

## Change Log

| Data | Chi | Cosa |
|---|---|---|
| 2026-08-30 | Amelia (`CS`) | Story creata dal piano di sprint |
| 2026-08-30 | Giulio | Aggiunto AC 3 (check-in in ritardo): mancava, ed è il caso più comune |
| 2026-08-31 | Amelia (`DS`) | Implementazione, 9 test verdi, status -> review |
| 2026-08-31 | Amelia (`CR`) | Code review: 1 patch, 2 dismiss. Patch applicata, status -> done |
