# Contesto di progetto — SaleRiunioni

> Caricato da ogni agente BMAD all'attivazione. Se una convenzione non sta qui,
> l'agente non la conosce.

## Cos'è

Servizio interno che affianca Outlook sulle sei sale riunioni della sede di Milano.
**Le sale si continuano a prenotare su Outlook**: il servizio non sostituisce niente,
legge. Sincronizza i calendari delle sale da Microsoft Graph, alimenta i sei display
appesi fuori dalle porte, tiene una web app di ricerca «sala libera adesso» e manda
il report mensile al facility manager.

In produzione dal marzo 2022. Due sviluppatori, nessuno dei due a tempo pieno.
Chi l'ha scritto non lavora più qui.

**BMAD è entrato su questo progetto nel 2026**, quattro anni dopo il primo commit.
I documenti in `planning-artifacts/` non sono stati scritti prima del codice: sono
stati ricostruiti leggendo il codice. Dove un documento descrive una scelta del
2022, lo dice.

## Vincoli aziendali

- **Outlook resta la fonte di verità.** Le prenotazioni nascono e muoiono là. Il
  database locale è uno specchio: se le due versioni divergono, ha ragione Graph.
- **Autenticazione**: SSO aziendale esistente (OIDC). Non si costruisce login.
- **Dati**: restano on-premise, tranne quelli che per forza passano da Graph.
- **Stack imposto**: Java 21 + Spring Boot, PostgreSQL.
- **Deploy**: container sul cluster interno, pipeline già esistente.
- **Permessi Graph**: l'app ha `Calendars.ReadWrite` sulle sole sei caselle sala.
  Allargare il perimetro passa da IT security, non da noi.

## Regole di prodotto

Decise con l'umano, valgono per tutto il prodotto e non solo per il documento in
cui sono nate.

- **Una sala liberata deve risultare libera dove le persone guardano davvero.**
  Il display, la pagina di ricerca, e il calendario di Outlook. Deciso il
  2026-08-27 con John e Winston.
  *Perché*: quasi tutti cercano una sala dal calendario. Una sala che risulta
  libera solo nel nostro database non è libera: nessuno riesce a prenderla, e
  avremmo scritto una funzione che non produce l'effetto per cui esiste.
  *Contropartita accettata*: da qui in poi **il servizio scrive fuori da sé**.
  Un nostro difetto non resta più dentro una nostra tabella.

## Convenzioni di codice

- Package base `it.azienda.saleriunioni`.
- Nomi di dominio in italiano (`Sala`, `Prenotazione`, `Disdetta`), infrastruttura
  in inglese (`Repository`, `Controller`, `Config`).
- Un test per criterio di accettazione. I test si chiamano come l'AC che coprono:
  `sala_liberata_se_nessun_check_in_entro_dieci_minuti()`.
- Niente Lombok. Record Java dove il tipo è immutabile.
- Migrazioni con Flyway, mai `ddl-auto`.
- Ogni chiamata in scrittura verso Graph passa da `PrenotazioneOutlookClient`.

## Glossario minimo

Il glossario completo sta in `docs/glossario-dominio.md`. I termini che non si
traducono mai: **fascia** (slot da 30 minuti), **no-show** (prenotazione mai
usata), **check-in** (conferma di presenza in sala), **display** (lo schermo
appeso fuori dalla sala, che legge chiunque passi in corridoio).

## Cosa NON è in scope

Sale di altre sedi, catering, integrazione con i badge, prenotazione dal servizio
(si prenota su Outlook), modifica di un evento che non sia la sala. Se un
requisito sfiora questi temi, va segnalato prima di scriverlo.

## Regole per gli agenti

- Ogni decisione tecnica va scritta con l'alternativa scartata e il motivo.
  Un `architecture.md` che dice solo cosa abbiamo scelto è un documento a metà.
- Le story devono essere auto-contenute: chi le implementa non deve rileggere il PRD.
- Il codice del 2022 non è sbagliato perché è vecchio. Prima di cambiarlo, cerca
  perché è fatto così: quasi sempre la ragione è scritta in `architecture.md`.
- Se manca un'informazione, chiedila. Non inventare requisiti plausibili.
