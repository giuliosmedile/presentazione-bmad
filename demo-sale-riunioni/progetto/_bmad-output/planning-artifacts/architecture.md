# Architettura — SaleRiunioni

Autore: Winston (System Architect) · Input: `prd.md`, `ux-spec.md`, codice esistente
Revisione umana: Giulio, 2026-08-27

**Questo documento è per metà una ricostruzione.** Il servizio esiste dal marzo
2022 e non aveva documentazione di architettura. La Parte I è stata scritta
leggendo il codice e le migrazioni: descrive scelte che non ho preso io, e dove
ho ricostruito l'intenzione invece di trovarla scritta lo dico. La Parte II sono
le decisioni nuove, quelle per la liberazione automatica.

---

# Parte I — Com'è fatto oggi

Ricostruita dal codice, non da documenti preesistenti.

## Forma generale

Un solo servizio Spring Boot, un solo database PostgreSQL, un frontend servito
dallo stesso servizio. Niente code, niente cache, niente secondo servizio.

Sei sale, qualche centinaio di prenotazioni al mese, un migliaio di utenti al
massimo. A questi numeri la forma è proporzionata: non c'è niente da semplificare
e niente che chieda di essere spezzato.

```
it.azienda.saleriunioni
├── sala/            Sala, CollezioneSale, SalaRepository
├── prenotazione/    Prenotazione, TipoPrenotazione, StatoPrenotazione,
│                    PrenotazioneRepository, PrenotazioneService
├── sincronizzazione/ SincronizzazioneGraphJob, MappaturaEventoGraph
├── outlook/         PrenotazioneOutlookClient  ← unico punto di scrittura verso Graph
├── disponibilita/   RicercaDisponibilita (query di lettura)
├── display/         DisplayController, endpoint del check-in
└── shared/          eccezioni di dominio, config, mapping errori HTTP
```

## E1 — Outlook è la fonte di verità, il database è uno specchio

Le prenotazioni non nascono qui. Nascono su Outlook, dove le fa la gente, e
arrivano al servizio con un ritardo di al massimo un minuto.

**Conseguenza**, e vale per chiunque scriva codice qui: il database locale non è
il posto dove si decide. Cambiare una riga di `prenotazione` senza cambiare
l'evento su Graph produce uno stato che il prossimo giro di sincronizzazione
cancella. Ogni scrittura reale passa da `PrenotazioneOutlookClient`.

## E2 — Sincronizzazione con delta query su `calendarView`

**Scelta (2022):** un job ogni minuto chiama la delta query di Graph su
`/users/{sala}/calendarView` per le sei caselle sala, finestra da oggi a +60 giorni.
**Ricostruito, non documentato:** il `calendarView` restituisce le ricorrenze già
espanse in occorrenze. Chi l'ha scritto voleva una riga per riunione fisica,
perché il display deve mostrare *questa* riunione, non la regola che la genera.

## E3 — Tre tipi di prenotazione

`TipoPrenotazione` è `SINGOLA`, `OCCORRENZA`, `SERIE`.

Non è una distinzione teorica: **le occorrenze di serie sono il 70% delle righe**.
Gli stand-up, gli allineamenti settimanali, i comitati mensili. Una sala su tre
la mattina è occupata da una serie creata anni fa da qualcuno che magari non
lavora più qui.

## E4 — Per le occorrenze, `id_evento_graph` contiene l'id della serie

Questa è la cosa che più facilmente si sbaglia in questo progetto, ed è per
questo che ha un capitolo suo.

**Il fatto:** la colonna `id_evento_graph` **non contiene sempre l'id dell'evento
che la riga rappresenta.**

| `tipo` | cosa c'è in `id_evento_graph` |
|---|---|
| `SINGOLA` | l'id dell'evento |
| `SERIE` | l'id della serie |
| `OCCORRENZA` | **l'id della serie**, non quello dell'occorrenza |

L'occorrenza si identifica con la coppia `(id_evento_graph, inizio)`, ed è così
che è fatto l'indice unico in `V3__sincronizzazione.sql`.

**Perché (ricostruito):** gli id delle occorrenze restituiti da `calendarView` non
sono stabili fra una delta query e la successiva. Usarli come chiave voleva dire
duplicare mezza tabella a ogni giro. Chi l'ha scritto ha preso l'unica cosa stabile
che aveva — l'id della serie — e ha aggiunto l'orario di inizio per distinguere le
occorrenze.

**Conseguenza per chi implementa:** `prenotazione.idEventoGraph()` è una chiave di
sincronizzazione, **non un indirizzo a cui scrivere**. Passarla a un'operazione di
scrittura su Graph, per una riga di tipo `OCCORRENZA`, significa operare sulla
serie intera.

**Quando rivederla:** se un giorno passiamo a `/events` con espansione manuale
delle ricorrenze, questa colonna torna a voler dire una cosa sola e il capitolo si
può cancellare. Non è lavoro di questa epic.

## E5 — `PrenotazioneOutlookClient.annulla()` è del 2023 e nasce per le singole

Scritto per il bottone «libera la sala» della web app. Nella UI quel bottone
compare solo all'organizzatore e **solo sulle prenotazioni singole**: `ux-spec.md`
del 2023 lo dice, e il template lo nasconde sulle ricorrenti.

Il client fa una `DELETE` sull'evento indicato. Non guarda il tipo della
prenotazione, perché nel 2023 non gli arrivavano ricorrenti: il filtro era nella
UI, tre livelli più su.

---

# Parte II — Decisioni per la liberazione automatica

Queste le ho prese io, nel 2026, e hanno l'alternativa scartata.

## D1 — Job schedulato, non trigger temporale

**Scelta:** un job ogni minuto che cerca le prenotazioni iniziate da più di 10
minuti senza check-in e le porta a `no_show`.
**Scartato:** `pg_cron`, e scheduler applicativo per singola prenotazione.
**Perché:** un job che scansiona è banale da capire, da testare e da rieseguire se
salta un giro. Uno scheduler per prenotazione va tenuto in sincrono con disdette,
spostamenti e modifiche della serie, e sbaglia in silenzio.
**Vincolo derivato:** il job deve essere idempotente. Se gira due volte sullo
stesso minuto il risultato non cambia, e su questo NFR2 non ammette sconti: mai
liberare una prenotazione che ha il check-in.

## D2 — Il rilascio passa da `PrenotazioneOutlookClient`, che si riusa

**Scelta:** la liberazione chiama il client esistente. Non si apre una seconda
strada di scrittura verso Graph.
**Scartato:** un client nuovo dedicato alla liberazione.
**Perché:** due punti di scrittura verso Graph vogliono dire due posti dove
sbagliare i permessi, due gestioni del throttling, due comportamenti diversi
quando Graph risponde 429. Il client c'è, è l'unico punto di scrittura per
convenzione di progetto (`project-context.md`), e va riusato.
**Quando rivederla:** se la liberazione avesse bisogno di operazioni che il client
non sa fare.

## D3 — Stato calcolato, non event sourcing

**Scelta:** la prenotazione è una riga con uno stato (`attiva`, `no_show`,
`disdetta`, `conclusa`). Le transizioni scrivono una riga in `prenotazione_evento`
per l'audit.
**Scartato:** event sourcing pieno con proiezioni.
**Perché:** serve la storia delle transizioni (FR7 chiede la motivazione della
forzatura), non serve ricostruire lo stato dagli eventi. La tabella di audit dà il
90% del valore al 10% del costo.

## D4 — Aggiornamento del display in polling, non WebSocket

**Scelta:** display e web app rileggono la disponibilità ogni 10 secondi.
**Scartato:** WebSocket o SSE.
**Perché:** la spec UX chiede che la sala risulti libera, non che risulti libera
entro 50 ms. Dieci secondi di ritardo alle 10:58 sono impercettibili, e ci
risparmiano una connessione persistente da gestire e riconnettere su sei display
appesi al muro.

## D5 — Check-in dal display, senza autenticazione

**Scelta:** il tasto sul display conferma la presenza senza chiedere chi sei.
**Scartato:** check-in dalla web app autenticata via SSO.
**Perché:** chi è in sala ha già la sala. Chiedergli le credenziali su un tablet
appeso al muro aggiunge un attrito che fa fallire la funzione: se il check-in è
scomodo nessuno lo fa, e liberiamo sale piene di gente.
**Contropartita accettata:** chiunque passi davanti alla sala può fare check-in.
È un abuso possibile e non dannoso: chi lo fa tiene occupata una sala che avrebbe
tenuto occupata comunque.

## Strategia di test

- **Dominio**: unit test, uno per criterio di accettazione, senza Spring
- **Persistenza e sincronizzazione**: Testcontainers con Postgres vero
- **Graph**: WireMock con le risposte vere salvate da una sessione di sviluppo.
  Mai chiamare Graph nei test
- **Il job**: idempotenza (due esecuzioni, stesso risultato) e caso con check-in
  presente (non deve liberare mai)
- **Il test che manca da sempre**: non c'è un solo test di questo progetto che
  parta da una prenotazione di tipo `OCCORRENZA`. Le fixture sono tutte singole,
  perché nel 2022 le serie non erano sincronizzate. È il buco più grosso della
  suite e non l'ho chiuso io: va chiuso.

## Cosa passo ad Amelia

Le story si implementano in ordine 1.1 → 1.2. Il check-in (1.1) va per primo
perché la liberazione senza check-in libera tutto.

Sul resto, la cosa da tenere in testa è una sola: **questo non è un progetto
vuoto**. Prima di scrivere un metodo, cercare se c'è già; e se c'è, leggere per
cosa era stato scritto.

---

**Nota del revisore umano (Giulio):** la prima versione della Parte II proponeva un
client Graph nuovo per la liberazione, «per non toccare codice del 2023». Ho
chiesto io di riusare quello esistente: un secondo punto di scrittura verso Graph
è esattamente il genere di duplicazione che fra due anni nessuno sa più perché c'è.
Resto della mia idea. Vale la pena scriverlo qui che la decisione è mia.
