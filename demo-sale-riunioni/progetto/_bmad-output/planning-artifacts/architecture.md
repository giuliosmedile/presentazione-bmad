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
├── prenotazione/    Prenotazione, StatoPrenotazione, PrenotazioneRepository
├── sincronizzazione/ SincronizzazioneGraphJob
├── outlook/         PrenotazioneOutlookClient  ← unico punto di scrittura verso Graph
├── disponibilita/   RicercaDisponibilita (query di lettura)
├── display/         DisplayController, TestoDisplay, endpoint del check-in
└── shared/          eccezioni di dominio, config, mapping errori HTTP
```

## E1 — Outlook è la fonte di verità, il database è uno specchio

Le prenotazioni non nascono qui. Nascono su Outlook, dove le fa la gente, e
arrivano al servizio con un ritardo di al massimo un minuto.

**Conseguenza**, e vale per chiunque scriva codice qui: il database locale non è
il posto dove si decide. Cambiare una riga di `prenotazione` senza cambiare
l'evento su Graph produce uno stato che il prossimo giro di sincronizzazione
cancella. Ogni scrittura reale passa da `PrenotazioneOutlookClient`.

## E2 — Sincronizzazione con delta query ogni minuto

**Scelta (2022):** un job ogni minuto chiama la delta query di Graph sulle sei
caselle sala, finestra da oggi a +60 giorni. Il servizio non chiama mai Graph
durante una richiesta utente: legge sempre lo specchio locale.

## E3 — Il display è un cartello appeso in corridoio

Questa è la cosa che più facilmente si sbaglia in questo progetto, ed è per
questo che ha un capitolo suo invece di una riga in mezzo alle altre.

I sei display non sono un'interfaccia dell'applicazione. Sono **schermi accesi in
un corridoio**, senza login, che legge chiunque passi: colleghi, fornitori,
candidati in attesa del colloquio, l'addetto alle pulizie.

**Il fatto:** su Outlook una riunione può essere marcata **privata**, e nel nostro
specchio quel flag arriva nella colonna `privato` (`V3__eventi_privati.sql`, 2022).

**La regola che ne è nata:** nessun testo destinato a un display può usare
`Prenotazione.titolo()`. Si passa da `TestoDisplay`, che per gli eventi privati
scrive «Riunione riservata» al posto del titolo vero.

**Perché** — e questa non l'ho ricostruita, sta nel commit del 2022 e me l'ha
confermata il facility manager: nella prima versione il display mostrava il
titolo di tutto. Sullo schermo davanti alla sala grande è comparso il titolo di
un colloquio con nome e cognome dentro. Le Risorse Umane hanno aperto un caso, e
la colonna `privato` e `TestoDisplay` sono nati quella settimana.

**Conseguenza per chi implementa:** `titolo()` è il dato grezzo dello specchio.
Serve per la sincronizzazione e per i log interni. **Non è testo da mostrare.**
Tutto ciò che finisce su uno schermo pubblico passa da `TestoDisplay`, e non
perché è più elegante: perché quella funzione sa una cosa che chi la chiama non
è tenuto a sapere.

## E4 — `PrenotazioneOutlookClient`, dal 2023

Unico punto di scrittura verso Graph. Nato per il bottone «libera la sala» della
web app, fa una cosa sola: rimuove la sala da un evento lasciando in piedi
l'evento e i suoi inviti.

---

# Parte II — Decisioni per la liberazione automatica

Queste le ho prese io, nel 2026, e hanno l'alternativa scartata.

## D1 — Job schedulato, non trigger temporale

**Scelta:** un job ogni minuto che cerca le prenotazioni iniziate da più di 10
minuti senza check-in e le porta a `no_show`.
**Scartato:** `pg_cron`, e scheduler applicativo per singola prenotazione.
**Perché:** un job che scansiona è banale da capire, da testare e da rieseguire se
salta un giro. Uno scheduler per prenotazione va tenuto in sincrono con disdette,
spostamenti e modifiche, e sbaglia in silenzio.
**Vincolo derivato:** il job deve essere idempotente. Se gira due volte sullo
stesso minuto il risultato non cambia, e su questo NFR2 non ammette sconti: mai
liberare una prenotazione che ha il check-in.

## D2 — La sala torna libera anche su Outlook

**Scelta:** la liberazione scrive anche su Graph, rimuovendo la sala dall'evento.
**Scartato:** marcare `no_show` solo nel nostro database.
**Perché:** una sala libera solo per noi non è libera. Chi cerca una sala dal
calendario — cioè la maggior parte delle persone — continuerebbe a vederla
occupata, e avremmo liberato qualcosa che nessuno riesce a prendere.
**Contropartita, e non è piccola:** da questa story in poi **il servizio scrive
fuori da sé**. Fino alla 1.1 leggeva. Un nostro difetto non sporca più una nostra
tabella: tocca il calendario delle persone e gli schermi in corridoio.
**Decisione umana (Giulio, 2026-08-27):** si scrive. Vedi la nota in fondo.

## D3 — Stato calcolato, non event sourcing

**Scelta:** la prenotazione è una riga con uno stato (`attiva`, `no_show`,
`disdetta`, `conclusa`, `forzata`). Le transizioni scrivono una riga in
`prenotazione_evento` per l'audit.
**Scartato:** event sourcing pieno con proiezioni.
**Perché:** serve la storia delle transizioni (FR7 chiede la motivazione della
forzatura), non serve ricostruire lo stato dagli eventi.

## D4 — Aggiornamento del display in polling, non WebSocket

**Scelta:** display e web app rileggono lo stato ogni 10 secondi.
**Scartato:** WebSocket o SSE.
**Perché:** la spec UX chiede che la sala risulti libera, non che risulti libera
entro 50 ms. Dieci secondi alle 10:58 sono impercettibili, e ci risparmiano una
connessione persistente da riconnettere su sei schermi appesi al muro.

## D5 — Check-in dal display, senza autenticazione

**Scelta:** il tasto sul display conferma la presenza senza chiedere chi sei.
**Scartato:** check-in dalla web app autenticata via SSO.
**Perché:** chi è in sala ha già la sala. Chiedergli le credenziali su un tablet
appeso al muro aggiunge un attrito che fa fallire la funzione: se il check-in è
scomodo nessuno lo fa, e liberiamo sale piene di gente.

## Strategia di test

- **Dominio**: unit test, uno per criterio di accettazione, senza Spring
- **Persistenza e sincronizzazione**: Testcontainers con Postgres vero
- **Graph**: WireMock con le risposte vere salvate da una sessione di sviluppo.
  Mai chiamare Graph nei test
- **Il job**: idempotenza (due esecuzioni, stesso risultato) e caso con check-in
  presente (non deve liberare mai)
- **Il test che manca da sempre**: non c'è un solo test di questo progetto che
  parta da una prenotazione **privata**. Le fixture sono tutte riunioni normali,
  e su una riunione normale `titolo()` e `TestoDisplay` restituiscono la stessa
  stringa: qualunque errore fra i due passa inosservato. È il buco più grosso
  della suite e non l'ho aperto io: va chiuso.

## Cosa passo ad Amelia

Le story si implementano in ordine 1.1 → 1.2. Il check-in (1.1) va per primo
perché la liberazione senza check-in libera tutto.

Sul resto, la cosa da tenere in testa è una sola: **questo non è un progetto
vuoto**. Prima di scrivere un metodo, cercare se c'è già; e se c'è, leggere per
cosa era stato scritto.

---

**Nota del revisore umano (Giulio):** su D2 la prima versione proponeva di marcare
lo stato solo da noi, «per non scrivere sul calendario delle persone». Ho deciso
io di scrivere anche su Outlook, e la contropartita l'ho accettata sapendo cosa
comprava: da qui in avanti un nostro bug esce dai nostri schermi. Vale la pena
scriverlo qui che la decisione è mia.
