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

_Code review avversariale (Blind Hunter + Edge Case Hunter + Acceptance Auditor),
2026-08-29, modalità `full`. Verdetto Acceptance Auditor: **spec soddisfatta**
(AC1–AC5, tutti i checkbox Task). Triage: 1 `decision-needed`, 2 `patch`,
1 `defer`, 3 scartati come rumore._

- [x] [Review][Decision] APPLICATO (2026-08-29) — AC1 non determina l'ordine a parità di capienza: `ORDER BY s.capienza ASC` non è stabile fra due chiamate identiche, l'utente che ricarica vede le sale scambiate. Il criterio di spareggio è una scelta di prodotto, non patchabile senza decisione. **Risoluzione decisa (2026-08-29, Giulio): spareggio sul nome della sala.** Scartata l'alternativa «piano dell'utente prima»: serve il piano nel profilo, fronte non aperto in questa epic. [RicercaDisponibilita.java:41]
- [x] [Review][Patch] APPLICATO (2026-08-29) — La ricerca enumera per esclusione: `p.stato <> 'disdetta'` resta corretto solo finché nessuno aggiunge uno stato, e lo stato aggiunto **c'è già** (`architecture.md` § D4: `attiva | disdetta | no_show`; `StatoPrenotazione` lo ha nell'enum). Una prenotazione decaduta per no-show passa il filtro e continua a occupare la sala — la funzionalità per cui il progetto esiste, nella query principale. Corretto in `p.stato = 'attiva'`. Fonte `blind+edge`: il Blind Hunter ha visto la forma fragile nel diff, l'Edge Case Hunter è andato a leggere il progetto e ha trovato lo stato. L'Acceptance Auditor **non** l'ha segnalato, ed è coerente: rispetto agli AC della 1.1 quel predicato è corretto. [RicercaDisponibilita.java:34]
- [x] [Review][Patch] APPLICATO (2026-08-29) — La traduzione dell'errore è troppo larga: `catch (DataIntegrityViolationException)` fa diventare «sala occupata» qualunque vincolo violato. `architecture.md` § D3 lo vieta esplicitamente («si verifica il nome del vincolo»). Corretto verificando `prenotazione_no_overlap` e rilanciando il resto. Fonte `blind+edge`. [PrenotazioneService.java:78]
- [x] [Review][Defer] `PrenotazioneController` mappa 409 e 422; ogni altra eccezione esce come 500 senza corpo, timeout del database compreso — deferred, pre-esistente allo scaffold, non introdotto da questa story. Registrato in `deferred-work.md`. [PrenotazioneController.java:52]

_Scartati come rumore, tutti dal Blind Hunter (che per costruzione non ha
contesto): validazione null su `salaId` (c'è, `@NotNull` sul DTO a monte);
indice mancante sul filtro temporale (lo crea `V1__sale_e_prenotazioni.sql` col
vincolo di esclusione GiST); «4 ore è un magic number» (è l'AC4, costante
`DURATA_MASSIMA`)._

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
  Sollevato in code review come `patch`, corretto.
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
| 2026-08-29 | Amelia (`DS`) | Implementazione, 16 test verdi, status -> review |
| 2026-08-29 | Amelia (`CR`) | Code review avversariale: 1 decision-needed, 2 patch, 1 defer, 3 dismiss |
| 2026-08-29 | Giulio | Decisione sul `decision-needed`: spareggio sul nome della sala |
| 2026-08-29 | Amelia (`CR`) | 3 patch applicate, 3 test nuovi, 19 verdi, status -> done |
