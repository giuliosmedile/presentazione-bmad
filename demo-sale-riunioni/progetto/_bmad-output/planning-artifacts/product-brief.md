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
| Il nostro database, 4 settimane | 612 prenotazioni sulle 6 sale, di cui il 70% occorrenze di serie ricorrenti |
| Giro fisico, 3 giorni a campione, ore 10-12 e 14-16 | 41% delle sale risultate occupate erano vuote |
| Ticket all'help desk, 6 mesi | 38 richieste «non trovo una sala», nessuna su altri temi |

Il 41% è la cifra che decide fra le due ipotesi, e sceglie la seconda: le sale
non sono poche, sono occupate a vuoto. Va rimisurata dopo il rilascio con lo
stesso metodo: se non scende, l'ipotesi era sbagliata.

Il 70% di occorrenze non era atteso e cambia la forma del prodotto: la maggior
parte di quello che libereremo non è una riunione, è una ripetizione di una
riunione decisa una volta e mai più guardata.

## Chi ha il problema

- **Chi cerca una sala all'ultimo minuto** (tutti, saltuariamente). Cerca, non
  trova, gira per i piani, finisce a fare la call alla scrivania.
- **Chi ha creato una ricorrenza tre anni fa** e non la usa più tutte le
  settimane. Non fa niente di male: nessuno gli ha mai chiesto di disdire, e
  disdire una singola occorrenza su Outlook è un gesto che metà delle persone non
  sa fare.
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

Se dopo un mese la quota di sale-vuote-ma-occupate non scende sotto il 20%,
l'ipotesi è sbagliata e il problema è davvero la capienza.

## Alternative considerate e scartate

| Alternativa | Perché no |
|---|---|
| Sensori di presenza nelle sale | Risolve meglio, ma è un progetto di facility con budget hardware e tempi da mesi. Da riprendere se il check-in manuale funziona ma stanca. |
| Limite di prenotazioni per persona | Punisce chi organizza tanto, che spesso è chi ne ha davvero bisogno. Tratta il sintomo. |
| Scadenza automatica delle serie dopo sei mesi | Colpisce il 70% delle prenotazioni con una regola che nessuno ha chiesto, e le riunioni che *servono* davvero tutte le settimane sono lì dentro. |
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
3. **Le occorrenze di una serie si liberano una alla volta o si tocca la serie?**
   *(→ una alla volta. Non è una scelta: toccare la serie vorrebbe dire cambiare
   un appuntamento ricorrente nel calendario di tutti i partecipanti.)*

---

**Nota del revisore umano (Giulio):** la prima versione del brief proponeva, come
leva, una mail al responsabile dopo tre no-show, e una classifica dei reparti.
Tolte tutte e due. Il dato è vero ma trasformarlo in una gara al contrario avrebbe
fatto smettere di prenotare col proprio nome nel giro di due settimane — e il
primo effetto sarebbe stato che il 41% smette di essere misurabile. Questo è il
tipo di decisione che l'agente non poteva prendere: non gli mancava
un'informazione, gli mancava di sapere come funziona questa azienda.
