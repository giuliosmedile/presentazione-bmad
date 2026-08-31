# Product Brief — SaleRiunioni

Autore: Mary (Business Analyst) · Revisione umana: Giulio, 2026-08-24
Stato: approvato

## Il problema

Le sale riunioni della sede di Milano si prenotano su un calendario Outlook
condiviso. Prenotare costa dieci secondi, disdire costa lo stesso — ma nessuno
disdice, perché non c'è nessuna conseguenza a non farlo.

Il risultato è che il calendario dice «pieno» mentre le sale sono vuote.

## L'evidenza

Non è una sensazione. Tre rilevazioni fatte prima di scrivere questo brief:

| Fonte | Dato |
|---|---|
| Export calendario, 4 settimane | 612 prenotazioni sulle 6 sale |
| Giro fisico, 3 giorni a campione, ore 10-12 e 14-16 | 41% delle sale risultate occupate erano vuote |
| Ticket all'help desk, 6 mesi | 38 richieste «non trovo una sala», nessuna su altri temi |

Il 41% è la cifra che regge tutto il resto. Va rimisurata dopo il rilascio: se
non scende, il prodotto non è servito a niente.

## Chi ha il problema

- **Chi cerca una sala all'ultimo minuto** (tutti, saltuariamente). Cerca, non
  trova, gira per i piani, finisce a fare la call alla scrivania.
- **Chi organizza in anticipo** (project manager, HR). Prenota largo «per
  sicurezza», perché sa che dopo non troverebbe. Il comportamento è razionale:
  è il sistema a premiarlo.
- **Il facility manager**. Non ha dati d'uso, e ogni anno gli chiedono se
  servono altre sale. Non sa rispondere.

## L'ipotesi

Il problema non è la scarsità di sale: è che nessuno libera quelle che non usa.
Se liberare diventa automatico invece che volontario, la capacità disponibile
cresce senza costruire niente.

**La cosa più piccola che valida l'ipotesi**: un check-in in sala. Se entro dieci
minuti dall'inizio nessuno conferma la presenza, la prenotazione decade e la sala
torna prenotabile.

Se dopo un mese il tasso di sale-vuote-ma-occupate non scende sotto il 20%,
l'ipotesi è sbagliata e il problema è davvero la capienza.

## Alternative considerate e scartate

| Alternativa | Perché no |
|---|---|
| Sensori di presenza nelle sale | Risolve meglio, ma è un progetto di facility con budget hardware e tempi da mesi. Da riprendere se il check-in manuale funziona ma stanca. |
| Limite di prenotazioni per persona | Punisce chi organizza tanto, che spesso è chi ne ha davvero bisogno. Tratta il sintomo. |
| Solo dashboard di utilizzo per il facility manager | Dà i dati ma non cambia il comportamento. Utile *dopo*, non al posto. |
| Non fare niente e comprare più sale | Nessuno sa se servono. È esattamente la domanda a cui non si sa rispondere. |

## Vincoli noti

SSO aziendale già in essere, dati on-premise, stack del team (Java/Spring/Postgres).
Nessun budget hardware. Una sola sede nel primo rilascio.

## Domande ancora aperte

1. Chi può forzare la liberazione di una sala oltre al prenotante? *(→ risolta nel
   PRD: solo il facility manager, con motivazione registrata)*
2. Il check-in si fa dal telefono o da un tablet in sala? *(→ decisa in UX: dal
   proprio dispositivo, niente hardware nuovo)*
3. Le riunioni ricorrenti decadono una alla volta o tutte insieme? *(→ fuori scope
   MVP, le ricorrenze non ci sono)*

---

**Nota del revisore umano (Giulio):** la prima versione del brief proponeva anche
una classifica dei "peggiori no-show" per reparto. Tolta. Il dato è vero ma
trasformarlo in gara interna avrebbe fatto disattivare lo strumento in due
settimane. Questo è il tipo di decisione che l'agente non poteva prendere: non
gli mancava un'informazione, gli mancava di sapere come funziona questa azienda.
