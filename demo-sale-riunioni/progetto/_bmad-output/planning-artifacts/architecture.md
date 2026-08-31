# Architettura — SaleRiunioni

Autore: Winston (System Architect) · Input: `prd.md`, `ux-spec.md`
Revisione umana: Giulio, 2026-08-27

## Forma generale

Un solo servizio Spring Boot, un solo database PostgreSQL, un frontend servito
dallo stesso servizio. Niente code, niente cache, niente secondo servizio.

Sei sale, qualche centinaio di prenotazioni al mese, un migliaio di utenti al
massimo. A questi numeri qualunque cosa più complessa di così è un costo che
paghiamo senza comprare niente.

## Decisioni

Ogni decisione riporta l'alternativa scartata. Un documento che elenca solo le
scelte fatte non serve a chi arriva fra un anno.

### D1 — Monolite, non microservizi

**Scelta:** un servizio unico.
**Scartato:** servizio prenotazioni + servizio notifiche.
**Perché:** la separazione avrebbe senso se le notifiche scalassero diversamente.
Con sei sale non scalano affatto. Il costo del deploy doppio e della chiamata di
rete lo pagheremmo da subito, il beneficio forse mai.
**Quando rivederla:** se arriviamo a più sedi con volumi diversi per sede.

### D2 — Stato calcolato, non event sourcing

**Scelta:** la prenotazione è una riga con uno stato (`attiva`, `no_show`,
`disdetta`, `conclusa`). Le transizioni scrivono una riga in `prenotazione_evento`
per l'audit.
**Scartato:** event sourcing pieno con proiezioni.
**Perché:** serve la storia delle transizioni (FR7 richiede la motivazione della
forzatura), non serve ricostruire lo stato dagli eventi. La tabella di audit dà
il 90% del valore al 10% del costo.

### D3 — Vincolo di esclusione nel database, non nel codice

**Scelta:** `EXCLUDE USING gist` su `(sala_id WITH =, periodo WITH &&)` con
`btree_gist`, filtrato sulle sole prenotazioni attive.
**Scartato:** controllo di sovrapposizione nel service con transazione
`SERIALIZABLE`.
**Perché:** la doppia prenotazione è l'unico bug che questa applicazione non si
può permettere, ed è esattamente il caso in cui due richieste concorrenti passano
entrambe il controllo applicativo. Il database lo sa fare correttamente, noi no.
**Conseguenza per chi implementa:** il conflitto arriva come violazione di
vincolo, va tradotto in un errore di dominio leggibile (NFR di UX: l'utente deve
capire cosa è successo). Non si intercetta genericamente `DataIntegrityViolation`:
si verifica il nome del vincolo.

### D4 — Liberazione con job schedulato, non con trigger temporale

**Scelta:** un job ogni minuto che cerca le prenotazioni iniziate da più di 10
minuti senza check-in e le porta a `no_show`.
**Scartato:** `pg_cron`, e scheduler applicativo per singola prenotazione.
**Perché:** un job che scansiona è banale da capire, da testare e da rieseguire se
salta un giro. Uno scheduler per prenotazione va tenuto in sincrono con disdette e
modifiche, e sbaglia in silenzio.
**Vincolo derivato:** il job deve essere idempotente. Se gira due volte sullo
stesso minuto, il risultato non cambia. Su questo NFR2 non ammette sconti: mai
liberare una prenotazione che ha il check-in.

### D5 — Aggiornamento della lista in polling, non WebSocket

**Scelta:** la pagina rilegge la disponibilità ogni 10 secondi mentre è aperta.
**Scartato:** WebSocket o SSE.
**Perché:** la spec UX chiede che la riga sparisca da sola, non che sparisca entro
50 ms. Dieci secondi di ritardo sono impercettibili nel momento delle 10:58, e ci
risparmiano una connessione persistente da gestire, monitorare e riconnettere.
**Quando rivederla:** se il tempo di reazione diventa un problema vero e misurato.

### D6 — Check-in dentro l'app autenticata

**Scelta:** il check-in si fa nella stessa web app, già autenticata via SSO.
**Scartato:** link firmato monouso nella mail.
**Perché:** il link firmato è un secondo meccanismo di autenticazione da
progettare, far scadere e proteggere. La mail di FR5 porta all'app, l'SSO fa il
resto. Meno superficie, meno cose che si rompono.

## Struttura

```
it.azienda.saleriunioni
├── sala/           Sala, CollezioneSale, SalaRepository
├── prenotazione/   Prenotazione, StatoPrenotazione, PrenotazioneService,
│                   PrenotazioneRepository, PrenotazioneController
├── disponibilita/  RicercaDisponibilita (query di lettura, non passa dai service di scrittura)
├── liberazione/    LiberazioneJob, LiberazioneService
└── shared/         eccezioni di dominio, config, mapping errori HTTP
```

La lettura (`disponibilita`) è separata dalla scrittura (`prenotazione`) perché la
ricerca è la cosa più usata e la più semplice: farla passare per il service di
dominio la complicherebbe senza motivo.

## Strategia di test

- **Dominio**: unit test, uno per criterio di accettazione, senza Spring
- **Persistenza e concorrenza**: Testcontainers con Postgres vero. Il vincolo D3
  non è testabile su H2, e testarlo su un database diverso da quello di
  produzione non prova niente
- **Il test che non può mancare**: due prenotazioni concorrenti sulla stessa sala
  e la stessa fascia, una sola deve passare
- **Il job**: test che verifica l'idempotenza (due esecuzioni, stesso risultato) e
  il caso con check-in presente (non deve mai liberare)

## Cosa passo ad Amelia

Le story si implementano in ordine 1.1 → 1.2. Il vincolo D3 va messo in piedi
nella 1.1, anche se la doppia prenotazione si nota davvero solo dopo. Metterlo
dopo significherebbe migrare dati già sporchi.

---

**Nota del revisore umano (Giulio):** la prima versione proponeva Redis per la
disponibilità, «per le letture frequenti». Sei sale. Ho chiesto quante letture al
secondo si aspettasse e la risposta è stata «poche». Cache tolta. È la cosa che
controllo sempre negli output di un architetto, umano o no: la complessità
aggiunta per problemi che non abbiamo.
