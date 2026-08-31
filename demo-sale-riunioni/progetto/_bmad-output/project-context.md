# Contesto di progetto — SaleRiunioni

> Caricato da ogni agente BMAD all'attivazione. Se una convenzione non sta qui,
> l'agente non la conosce.

## Cos'è

Applicazione interna per prenotare le sale riunioni della sede di Milano.
Sostituisce il calendario condiviso su Outlook, che nessuno disdice.

## Vincoli aziendali

- **Autenticazione**: SSO aziendale esistente (OIDC). Non si costruisce login.
- **Dati**: restano on-premise. Nessun servizio esterno tocca i dati dei dipendenti.
- **Stack imposto**: Java 21 + Spring Boot, PostgreSQL. È lo stack del team, non
  si discute per un'app interna da tre schermate.
- **Deploy**: container sul cluster interno, pipeline già esistente.

## Convenzioni di codice

- Package base `it.azienda.saleriunioni`.
- Nomi di dominio in italiano (`Sala`, `Prenotazione`, `Disdetta`), infrastruttura
  in inglese (`Repository`, `Controller`, `Config`).
- Un test per criterio di accettazione. I test si chiamano come l'AC che coprono:
  `prenotazione_rifiutata_se_sala_gia_occupata()`.
- Niente Lombok. Record Java dove il tipo è immutabile.
- Migrazioni con Flyway, mai `ddl-auto`.

## Glossario minimo

Il glossario completo sta in `docs/glossario-dominio.md`. I termini che non si
traducono mai: **fascia** (slot da 30 minuti), **no-show** (prenotazione mai
usata), **check-in** (conferma di presenza in sala).

## Cosa NON è in scope

Sale di altre sedi, ricorrenze, catering, integrazione con i badge, prenotazione
da mobile. Se un requisito sfiora questi temi, va segnalato prima di scriverlo.

## Regole per gli agenti

- Ogni decisione tecnica va scritta con l'alternativa scartata e il motivo.
  Un `architecture.md` che dice solo cosa abbiamo scelto è un documento a metà.
- Le story devono essere auto-contenute: chi le implementa non deve rileggere il PRD.
- Se manca un'informazione, chiedila. Non inventare requisiti plausibili.
