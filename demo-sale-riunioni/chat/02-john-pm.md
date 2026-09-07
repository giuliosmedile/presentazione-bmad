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
- Toccare una serie ricorrente invece della singola occorrenza
- Allargare i permessi Graph oltre le sei caselle sala
- Notifiche push
- Statistiche per reparto o per persona

---

**giulio** — Tutti fuori. E il terzo scrivilo grosso: la serie non si tocca mai.

📋 Tutti e sei fuori, e il terzo l'ho messo dentro FR4 e non solo nella lista, così
chi legge i requisiti lo trova nel punto in cui gli serve.

Su quello vorrei essere preciso, perché il brief di Mary lascia una porta aperta.
Il 70% delle prenotazioni sono occorrenze: se «liberare la sala» volesse dire
«toccare la ricorrenza», il prodotto cambierebbe natura. Non staremmo liberando
una sala, staremmo modificando l'agenda delle persone.

PRD scritto. Otto requisiti funzionali, quattro non funzionali. Le tre cose da
guardare:

- **FR4**: la sala torna libera anche su Outlook, e sulle serie vale per la
  singola occorrenza. L'ho aggiunto io: la prima versione diceva «la sala torna
  prenotabile» senza dire dove, e dove è tutto il punto
- **NFR2**: la liberazione non può avere falsi positivi. Se il check-in c'è, la
  prenotazione non decade mai
- La sezione «cosa NON facciamo» è la prima del documento, non l'ultima

Ho incluso anche FR8 come report per reparto con la classifica dei peggiori: nel
brief c'era il dato ed era solido.

---

**giulio** — No. Mary l'aveva già tolta, è rientrata.

📋 Ritolta. E hai ragione a farmelo notare due volte: il dato è nel brief, quindi
finché resta solo lì continuerò a ripescarlo. FR8 resta come aggregato per sala e
per fascia, mai per persona.

L'ho messa nella sezione «cosa NON facciamo» con la riga esplicita, e nella nota
di revisione ho scritto il motivo. Adesso è in due documenti.

---

**giulio** — Due documenti non bastano. Questa regola non vale per il PRD, vale per
il prodotto. `party-mode` con Winston.

📋 D'accordo, e ti dico perché sono d'accordo: il PRD descrive *questa* cosa che
stiamo aggiungendo. La story della settimana prossima non lo rilegge, e fra sei
mesi chi apre l'epic 2 nemmeno.

Il posto è `project-context.md`, che ogni agente carica all'attivazione. Non è un
documento di prodotto: è la riga che tutti leggono prima di leggere qualunque
altra cosa.

Scrivo la regola con il motivo per esteso, non solo il divieto. Un divieto senza
motivo viene aggirato dal primo che ha una buona idea.

🏗️ Winston. E ha un riflesso tecnico che mi prendo io: se la regola è «nessuna
misura pubblica sul singolo», allora **nessun endpoint espone il conteggio dei
no-show per persona**, nemmeno interno, nemmeno «solo per il facility manager».
Un endpoint che esiste prima o poi qualcuno lo chiama.

Lo scrivo come conseguenza tecnica sotto la regola, e lo porto in `architecture.md`
quando ci arrivo.

---

**giulio** — Perfetto. CE, facciamo le story.

📋 Cinque story sull'epic 1.

| Story | Titolo | FR | Stima |
|---|---|---|---|
| 1.1 | Check-in dal display | FR2 | M |
| 1.2 | Liberazione automatica della sala | FR3, FR4 | M |
| 1.3 | Avviso di liberazione imminente | FR5 | S |
| 1.4 | Pannello del facility manager | FR6, FR7 | M |
| 1.5 | Report no-show aggregato | FR8 | S |

Due cose da rispettare. **1.1 prima di 1.2**: senza check-in non c'è nessun
segnale da cui capire se una sala è usata. E **1.3 non si rilascia senza 1.2**: un
avviso che annuncia una liberazione che non avviene è peggio di nessun avviso.

Ho anche messo in `epics.md` una nota che non è una story: dalla 1.2 in poi il
servizio **scrive** su Outlook, mentre fino alla 1.1 leggeva soltanto. È un cambio
di categoria, non una funzionalità in più — da lì un nostro bug non sporca una
nostra tabella, tocca il calendario delle persone.

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
- **Il checkpoint umano**: la classifica rientrata dal brief. Una decisione che
  sta in un documento solo torna indietro al documento dopo
- **Party mode**: due agenti veri nella stessa stanza, e la regola che sale da
  «nota nel brief» a riga di `project-context.md`. Winston ci aggiunge il
  riflesso tecnico che John non avrebbe visto
- La nota sul cambio di categoria: da qui in poi il sistema scrive sul calendario
  delle persone
