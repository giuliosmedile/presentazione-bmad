# Product Brief — SaleRiunioni

Autore: Mary (Business Analyst) · Revisione umana: Giulio, 2026-08-24
Stato: approvato

## Da dove si parte

Il servizio esiste dal 2022: legge i calendari delle sei sale da Outlook, alimenta
i display fuori dalle porte, tiene la pagina «sala libera adesso». **Le sale si
prenotano su Outlook** e continueranno a prenotarsi lì: qui non si sostituisce
niente.

La richiesta arrivata dal facility manager è una funzionalità precisa: se nessuno
fa check-in entro dieci minuti, la sala torna libera.

## Il problema, che non è la richiesta

La richiesta è già una soluzione. Il problema dietro è che **il calendario dice
«pieno» mentre le sale sono vuote**: prenotare costa dieci secondi, disdire costa
lo stesso, e non disdire non costa niente.

Prima di costruire, le ipotesi in campo erano due e portavano a due prodotti
diversi:

1. **Le sale sono poche.** Allora è un problema di metri quadri e di budget, e il
   software non c'entra: si compra spazio o si cambia sede.
2. **Le sale risultano occupate e non lo sono.** Allora è un problema di
   comportamento, e il software può fare qualcosa.

Costruire per la seconda mentre la verità è la prima significa buttare via mesi e
scoprirlo alla fine.

## L'evidenza

Non è una sensazione. Tre rilevazioni fatte prima di scrivere questo brief, due
delle quali con dati che avevamo già in casa:

| Fonte | Dato |
|---|---|
| Il nostro database, 4 settimane | 612 prenotazioni sulle 6 sale |
| Giro fisico, 3 giorni a campione, ore 10-12 e 14-16 | 41% delle sale risultate occupate erano vuote |
| Ticket all'help desk, 6 mesi | 38 richieste «non trovo una sala», nessuna su altri temi |

Il 41% è la cifra che decide fra le due ipotesi, e sceglie la seconda: le sale
non sono poche, sono occupate a vuoto. Va rimisurata dopo il rilascio con lo
stesso metodo: se non scende, l'ipotesi era sbagliata.

I ticket dicono una cosa in più che vale la pena scrivere: nessuno si lamenta
della qualità delle sale, del wifi o delle prese. Solo di non trovarne. È un
problema a una dimensione sola, ed è una fortuna.

## Chi ha il problema

- **Chi cerca una sala all'ultimo minuto** (tutti, saltuariamente). Cerca, non
  trova, gira per i piani, finisce a fare la call alla scrivania.
- **Chi prenota in anticipo per sicurezza** (project manager, HR). Prenota largo
  perché sa che dopo non troverebbe. Il comportamento è razionale: è il sistema a
  premiarlo.
- **Il facility manager.** Ogni anno gli chiedono se servono altre sale e non ha
  dati per rispondere.

## L'ipotesi

Il problema non è la scarsità di sale: è che nessuno libera quelle che non usa.
Se liberare diventa automatico invece che volontario, la capacità disponibile
cresce senza costruire niente.

**La cosa più piccola che valida l'ipotesi**: un check-in sul display della sala.
Se entro dieci minuti dall'inizio nessuno conferma la presenza, la prenotazione
decade e la sala torna prenotabile — anche su Outlook, altrimenti l'abbiamo
liberata solo per noi.

Va detta una cosa che il facility manager non ha chiesto e che pesa: **da questa
funzione in avanti il servizio smette di leggere e comincia a scrivere.** Scrive
sul calendario delle persone e sugli schermi appesi in corridoio. Non è un
dettaglio tecnico, è un cambio di rischio.

Se dopo un mese la quota di sale-vuote-ma-occupate non scende sotto il 20%,
l'ipotesi è sbagliata e il problema è davvero la capienza.

## Alternative considerate e scartate

| Alternativa | Perché no |
|---|---|
| Sensori di presenza nelle sale | Risolve meglio, ma è un progetto di facility con budget hardware e tempi da mesi. Da riprendere se il check-in manuale funziona ma stanca. |
| Limite di prenotazioni per persona | Punisce chi organizza tanto, che spesso è chi ne ha davvero bisogno. Tratta il sintomo. |
| Solo un report di utilizzo per il facility manager | Dà i dati ma non cambia il comportamento. Utile *dopo*, non al posto. |
| Non fare niente e comprare più sale | Nessuno sa se servono. È esattamente la domanda a cui non si sa rispondere. |

## Vincoli noti

Sistema esistente da quattro anni, due sviluppatori non a tempo pieno, nessuno
degli autori originali ancora in azienda. Outlook resta la fonte di verità.
Permessi Graph limitati alle sei caselle sala. Nessun budget hardware.

## Domande ancora aperte

1. Chi può forzare la liberazione di una sala oltre a chi ha prenotato? *(→ risolta
   nel PRD: solo il facility manager, con motivazione registrata)*
2. Il check-in si fa dal telefono o dal display in sala? *(→ decisa in UX: dal
   display, senza autenticazione)*
3. **Chi ha prenotato viene avvisato quando gli portiamo via la sala?**
   *(→ aperta. C'è un modo di farlo che è utile e uno che è umiliante, e non è una
   decisione che possa prendere io.)*

---

**Nota del revisore umano (Giulio):** la prima versione del brief dava per buona
la richiesta e partiva a descrivere la feature. L'ho rimandata indietro chiedendo
qual era il problema, ed è stato il momento in cui il brief è diventato utile: il
bivio fra «le sale sono poche» e «le sale risultano occupate» non era scritto da
nessuna parte, e senza quello avremmo costruito senza sapere per quale delle due
ipotesi.
