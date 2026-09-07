# 📋 John — Product Manager

> Fase di pianificazione. Dal brief a un piano che si può eseguire.
> Artefatti prodotti: `prd.md`, `epics.md`, una riga in `project-context.md`

---

**giulio** — John, c'è il brief di Mary sulle sale riunioni. Facciamo il PRD.

📋 Ciao giulio. Ricorda che c'è `bmad-help` se ti serve una mano a scegliere.

| # | Cod | Descrizione | Skill che parte |
|---|---|---|---|
| 1 | `CP` | Facilitazione esperta per produrre il Product Requirements Document | `bmad-create-prd` |
| 2 | `VP` | Validare che un PRD sia completo, snello, ben organizzato e coerente | `bmad-validate-prd` |
| 3 | `EP` | Aggiornare un PRD esistente | `bmad-edit-prd` |
| 4 | `CE` | Creare la lista di Epiche e Story che guiderà lo sviluppo | `bmad-create-epics-and-stories` |
| 5 | `IR` | Verificare che PRD, UX, architettura ed epiche siano allineati | `bmad-check-implementation-readiness` |
| 6 | `CC` | Decidere come procedere se emerge un cambiamento a metà implementazione | `bmad-correct-course` |


In attesa. Rispondi con un numero, un codice o una descrizione.

Hai detto «facciamo il PRD», quindi vado su `CP`. Ho letto il brief e la
documentazione del progetto.

Prima domanda, e ci tengo: **perché adesso?** Il servizio ha quattro anni e il 41%
è lì da sempre. Cosa è cambiato questo mese che rende sensato spenderci tre sprint?

---

**giulio** — Onestamente? Sono aumentate le lamentele da quando siamo tornati in
ufficio quattro giorni su cinque.

📋 Ecco, questo è un pezzo che nel brief non c'è e cambia le priorità. Non stiamo
risolvendo un problema stabile: stiamo inseguendo una curva che sta ancora
salendo. Vuol dire due cose:

1. Rilasciare presto vale più che rilasciare completo
2. La misura di partenza va presa **adesso**, perché fra tre mesi il confronto
   sarà sporcato dall'aumento di presenze

Lo scrivo nel PRD. Seconda domanda: quando dici «risolto», cosa vedi?

---

**giulio** — Che uno alle 10:58 trova una sala.

📋 Bene, allora l'obiettivo non è «ridurre i no-show»: è **la disponibilità
percepita all'ultimo minuto**. Sembra la stessa cosa e non lo è. Il no-show è la
leva, non il risultato.

Metto come obiettivo il 41% che scende sotto il 20%, con una metrica di supporto:
quante prenotazioni vanno a buon fine su sale liberate automaticamente. Se quel
numero è zero, la liberazione funziona ma non serve a nessuno, e lo scopriremmo
solo così.

Ora la parte che mi interessa di più, e su cui insisto: **cosa non facciamo?**

---

**giulio** — Non lo so, dimmi tu.

📋 Non funziona così: se lo decido io, fra due settimane qualcuno chiede di
prenotare dall'app e io non ho niente da opporre. Te li elenco, tu tagli.

Dal brief e dal contesto di progetto ho tirato fuori questi candidati:

- Sale di altre sedi
- Prenotare dal servizio invece che da Outlook
- Cancellare o modificare una riunione: si toglie la sala, l'evento resta
- Allargare i permessi Graph oltre le sei caselle sala
- Notifiche push
- Report per persona

---

**giulio** — Tutti fuori.

📋 Tutti e sei fuori, e il terzo l'ho messo dentro FR4 e non solo nella lista, così
chi legge i requisiti lo trova nel punto in cui gli serve.

PRD scritto. Otto requisiti funzionali, cinque non funzionali. Le tre cose da
guardare:

- **FR4**: la sala torna libera anche su Outlook. L'ho aggiunto io: la prima
  versione diceva «la sala torna prenotabile» senza dire dove, e dove è tutto il
  punto
- **NFR2**: la liberazione non può avere falsi positivi. Se il check-in c'è, la
  prenotazione non decade mai
- La sezione «cosa NON facciamo» è la prima del documento, non l'ultima

---

**giulio** — Su FR4. Mary l'aveva sollevata: liberare anche su Outlook vuol dire
che da qui in poi il servizio scrive fuori da sé. L'ho scritto nel brief, ma il
brief lo legge chi lavora su questa cosa e basta. `party-mode` con Winston.

📋 D'accordo, e ti dico perché sono d'accordo: il PRD descrive *questa* cosa che
stiamo aggiungendo. La story della settimana prossima non lo rilegge, e fra sei
mesi chi apre l'epic 2 nemmeno.

Il posto è `project-context.md`, che ogni agente carica all'attivazione. Non è un
documento di prodotto: è la riga che tutti leggono prima di leggere qualunque
altra cosa.

E la scrivo con la contropartita accanto, non solo con la scelta. Una regola
scritta senza il motivo viene aggirata dal primo che ha una buona idea.

🏗️ Winston. E il riflesso tecnico me lo prendo io, perché è più grosso di come
suona: «scrivere fuori» qui vuol dire due superfici, non una. Il calendario delle
persone, **e i sei display appesi in corridoio**.

Sui display il progetto ha già una regola, dal 2022, e non l'ho scritta io: me la
sono trovata leggendo il codice. La riporto in `architecture.md` quando ci arrivo,
perché adesso torna a contare.

---

**giulio** — Perfetto. CE, facciamo le story.

📋 Cinque story sull'epic 1.

| Story | Titolo | FR | Stima |
|---|---|---|---|
| 1.1 | Check-in dal display | FR2 | M |
| 1.2 | Liberazione automatica della sala | FR3, FR4 | M |
| 1.3 | Avviso di liberazione imminente | FR5 | S |
| 1.4 | Pannello del facility manager | FR6, FR7 | M |
| 1.5 | Report aggregato | FR8 | S |

Due cose da rispettare. **1.1 prima di 1.2**: senza check-in non c'è nessun
segnale da cui capire se una sala è usata. E **1.3 non si rilascia senza 1.2**: un
avviso che annuncia una liberazione che non avviene è peggio di nessun avviso.

Ho anche messo in `epics.md` una nota che non è una story: dalla 1.2 in poi il
servizio **scrive fuori da sé**, mentre fino alla 1.1 leggeva soltanto. È un
cambio di categoria, non una funzionalità in più — da lì un nostro difetto non
sporca una nostra tabella, lo vede qualcuno.

Passo a Sally per i flussi, o vuoi rileggere?

---

**giulio** — Passa a Sally.

---

## Cosa mostrare di questa chat

- «**Perché adesso?**» — la domanda che ha fatto emergere il rientro in ufficio,
  che nel brief non c'era e ha cambiato le priorità
- Il rifiuto di decidere lo scope da solo: «te li elenco, tu tagli»
- La distinzione fra la **leva** (no-show) e il **risultato** (disponibilità
  percepita)
- **Il checkpoint umano**: una decisione che sta in un documento solo non
  sopravvive al documento dopo. È l'umano a dire «non basta»
- **Party mode**: due agenti veri nella stessa stanza, e la regola che sale da
  «nota nel brief» a riga di `project-context.md`. Winston ci aggiunge il
  riflesso tecnico che John non avrebbe visto — e nomina i display, che è il
  gancio di tutto il finale
- La nota sul cambio di categoria: da qui in poi il sistema scrive fuori da sé
