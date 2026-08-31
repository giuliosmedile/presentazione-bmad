# Story 1.1: Ricerca e prenotazione di una sala

Status: done

## Story

As un dipendente che deve riunirsi fra dieci minuti,
I want vedere quali sale sono libere adesso e prenotarne una con un click,
so that non debba girare per i piani sperando di trovare posto.

## Acceptance Criteria

1. **Given** una fascia oraria e un numero di partecipanti
   **When** cerco le sale disponibili
   **Then** ottengo solo le sale con capienza ≥ partecipanti e senza prenotazioni
   attive sovrapposte, ordinate per capienza crescente.

2. **Given** una sala libera per le fasce richieste
   **When** prenoto
   **Then** la prenotazione è creata in stato `attiva` e la sala non compare più
   nelle ricerche per quelle fasce.

3. **Given** due richieste di prenotazione concorrenti sulla stessa sala e sulle
   stesse fasce
   **When** arrivano insieme
   **Then** esattamente una va a buon fine e l'altra riceve un errore di dominio
   `SalaGiaOccupataException`, non un errore generico di database.

4. **Given** una richiesta di prenotazione più lunga di 4 ore
   **When** la invio
   **Then** viene rifiutata con `DurataMassimaSuperataException`.

5. **Given** fasce non contigue
   **When** provo a prenotarle in una sola richiesta
   **Then** la richiesta è rifiutata.

## Tasks / Subtasks

- [x] Migrazione Flyway `V1__sale_e_prenotazioni.sql` (AC: 1, 2, 3)
  - [x] Estensione `btree_gist`
  - [x] Tabelle `sala`, `prenotazione`, `prenotazione_evento`
  - [x] Vincolo `EXCLUDE USING gist (sala_id WITH =, periodo WITH &&) WHERE (stato = 'attiva')`
- [x] Dominio `Prenotazione` e `StatoPrenotazione` (AC: 2, 4, 5)
  - [x] Validazione durata massima e contiguità delle fasce
- [x] `RicercaDisponibilita` come query di sola lettura (AC: 1)
- [x] `PrenotazioneService.prenota()` (AC: 2, 3, 4, 5)
  - [x] Traduzione della violazione di vincolo in `SalaGiaOccupataException`
- [x] `PrenotazioneController` + mapping errori HTTP 409 / 422
- [x] Test unitari, uno per AC
- [x] Test Testcontainers per AC 3 (concorrenza vera, due thread)

### Review Findings

Dalla code review (`CR`) del 2026-08-29, tutte chiuse:

- **[alta]** La violazione di vincolo era intercettata come
  `DataIntegrityViolationException` generica: qualunque altro vincolo violato
  sarebbe stato riportato all'utente come «sala occupata». Corretto verificando il
  nome del vincolo `prenotazione_no_overlap`.
- **[media]** `RicercaDisponibilita` filtrava sulle prenotazioni con `stato != 'disdetta'`
  invece che `stato = 'attiva'`: le prenotazioni `no_show` sarebbero rimaste a
  occupare la sala, cioè esattamente il bug che il progetto esiste per evitare.
- **[bassa]** Timestamp salvati con fuso locale in due test. Portati a UTC.

## Dev Notes

### Contesto e ruolo della story

È la prima story dell'epic e porta lo schema. Le successive ereditano tabelle,
vincoli e infrastruttura di test: quello che si sbaglia qui si paga due volte.

### Vincolo D3 — perché nel database

Da `architecture.md`, decisione D3. La doppia prenotazione è l'unico bug non
tollerabile e nasce da due richieste concorrenti che passano entrambe un controllo
applicativo. Il controllo sta nel database perché è l'unico posto dove è corretto.

```sql
ALTER TABLE prenotazione ADD CONSTRAINT prenotazione_no_overlap
  EXCLUDE USING gist (sala_id WITH =, periodo WITH &&)
  WHERE (stato = 'attiva');
```

Il `WHERE (stato = 'attiva')` è la parte che conta: senza, una sala liberata per
no-show resterebbe bloccata per sempre.

### Fusi orari

`periodo` è `tstzrange`, tutto in UTC. La conversione avviene solo nel controller.
Nessun `LocalDateTime` sotto il livello di presentazione.

### Vincoli vincolanti qui

- Niente Lombok (project-context)
- I test si chiamano come l'AC che coprono
- `RicercaDisponibilita` non passa da `PrenotazioneService` (architecture, sezione Struttura)

### References

- `_bmad-output/planning-artifacts/architecture.md` § D3, D5, Strategia di test
- `_bmad-output/planning-artifacts/prd.md` § FR1, FR2, NFR1
- `_bmad-output/planning-artifacts/ux-spec.md` § ordinamento per capienza
- `docs/glossario-dominio.md` § Fascia, Prenotazione

## Dev Agent Record

### Agent Model Used

Amelia (bmad-agent-dev), skill `bmad-dev-story`

### Completion Notes List

- AC 3 non è stato testabile con un unico thread: il test usa due thread e una
  `CountDownLatch` per far partire le due transazioni insieme. Su H2 il test
  passava sempre, il che è il motivo per cui la strategia di test impone Postgres.
- Il primo tentativo di traduzione dell'errore intercettava
  `DataIntegrityViolationException` senza guardare quale vincolo fosse violato.
  Sollevato in code review, corretto.
- L'ordinamento per capienza crescente non era nel PRD ma nella spec UX. Applicato
  come da spec UX; segnalato perché il PRD dovrebbe citarlo.
- Durata massima 4 ore: implementata come costante di dominio, non come proprietà
  di configurazione. Se deve diventare configurabile, va deciso a livello di PRD.

### File List

```
src/main/resources/db/migration/V1__sale_e_prenotazioni.sql       (nuovo)
src/main/java/it/azienda/saleriunioni/sala/Sala.java              (nuovo)
src/main/java/it/azienda/saleriunioni/sala/SalaRepository.java    (nuovo)
src/main/java/it/azienda/saleriunioni/prenotazione/Prenotazione.java        (nuovo)
src/main/java/it/azienda/saleriunioni/prenotazione/StatoPrenotazione.java   (nuovo)
src/main/java/it/azienda/saleriunioni/prenotazione/PrenotazioneService.java (nuovo)
src/main/java/it/azienda/saleriunioni/prenotazione/PrenotazioneRepository.java (nuovo)
src/main/java/it/azienda/saleriunioni/prenotazione/PrenotazioneController.java (nuovo)
src/main/java/it/azienda/saleriunioni/disponibilita/RicercaDisponibilita.java  (nuovo)
src/main/java/it/azienda/saleriunioni/shared/SalaGiaOccupataException.java     (nuovo)
src/main/java/it/azienda/saleriunioni/shared/DurataMassimaSuperataException.java (nuovo)
src/main/java/it/azienda/saleriunioni/shared/GestoreErroriHttp.java            (nuovo)
src/test/java/it/azienda/saleriunioni/prenotazione/PrenotazioneServiceTest.java (nuovo)
src/test/java/it/azienda/saleriunioni/prenotazione/PrenotazioneConcorrenzaTest.java (nuovo)
src/test/java/it/azienda/saleriunioni/disponibilita/RicercaDisponibilitaTest.java (nuovo)
```

## Change Log

| Data | Chi | Cosa |
|---|---|---|
| 2026-08-28 | Amelia (`CS`) | Story creata dal piano di sprint |
| 2026-08-28 | Giulio | Aggiunto AC 5 (fasce contigue): mancava, e senza sarebbe stato un bug scoperto in produzione |
| 2026-08-29 | Amelia (`DS`) | Implementazione, 14 test verdi |
| 2026-08-29 | Amelia (`CR`) | Code review, 3 rilievi |
| 2026-08-29 | Amelia (`DS`) | Rilievi chiusi, 16 test verdi |
| 2026-08-29 | Giulio | Approvata, merge |
