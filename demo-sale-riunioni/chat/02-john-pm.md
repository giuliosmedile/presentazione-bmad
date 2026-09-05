# 📋 John — Product Manager

> Fase di pianificazione. Dal brief a un piano che si può eseguire.
> Artefatti prodotti: `prd.md`, `epics.md`

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

Hai detto «facciamo il PRD», quindi vado su `CP`. Ho letto il brief.

Prima domanda, e ci tengo: **perché adesso?** Il 41% è lì da anni. Cosa è cambiato
questo mese che rende sensato spenderci tre sprint?

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

📋 Non funziona così: se lo decido io, fra due settimane qualcuno chiede le
ricorrenze e io non ho niente da opporre. Te le elenco, tu tagli.

Dal brief e dal contesto di progetto ho tirato fuori questi candidati:

- Sale di altre sedi
- Riunioni ricorrenti
- App mobile nativa
- Catering e allestimenti
- Integrazione con i badge
- Notifiche push
- Statistiche per reparto o per persona

---

**giulio** — Tutti fuori. Le ricorrenze le chiederanno di sicuro, ma fuori.

📋 Tutti e sette fuori, e le ricorrenze finiscono in un'epic 3 dichiarata «fuori
MVP». Non è pignoleria: avere un posto dove rispondere «è tracciata, non è adesso»
vale più di dire di no a voce.

PRD scritto. Sette requisiti funzionali, quattro non funzionali. Le due cose da
guardare:

- **NFR2**: la liberazione non può avere falsi positivi. Se il check-in c'è, la
  prenotazione non decade mai. In caso di dubbio si tiene.
- La sezione «cosa NON facciamo» è la prima del documento, non l'ultima.

Ho incluso anche FR8, la classifica dei no-show per reparto: nel brief c'era il
dato ed era solido.

---

**giulio** — No. Mary l'aveva già tolta, è rientrata.

📋 Ritolta. E hai ragione a farmelo notare due volte: il dato è nel brief, quindi
finché resta lì continuerò a ripescarlo.

L'ho messa nella sezione «cosa NON facciamo» con la riga esplicita, che è il posto
dove una decisione del genere sopravvive. Nella nota di revisione ho scritto il
motivo. Adesso è in due documenti: non dovrebbe tornare più.

---

**giulio** — Bene. CE, facciamo le story.

📋 Cinque story sull'epic 1.

| Story | Titolo | FR | Stima |
|---|---|---|---|
| 1.1 | Ricerca e prenotazione | FR1, FR2 | M |
| 1.2 | Check-in e liberazione automatica | FR3, FR4 | M |
| 1.3 | Avviso di liberazione imminente | FR5 | S |
| 1.4 | Disdetta esplicita | FR6 | S |
| 1.5 | Forzatura del facility manager | FR7 | S |

Una dipendenza da rispettare: **1.3 non si rilascia senza 1.2**. Una mail che
avvisa di una liberazione che non avviene è peggio di nessuna mail.

Ho anche stretto FR2 mentre scrivevo: la prima versione non metteva un tetto alla
durata di una prenotazione. Ho messo 4 ore. È arbitrario, ma un limite arbitrario
è meglio di nessun limite — e adesso che è scritto qualcuno può contestarlo.

Passo a Sally per i flussi, o vuoi rileggere?

---

**giulio** — Passa a Sally.

---

## Cosa mostrare di questa chat

- «**Perché adesso?**» — la domanda che ha fatto emergere il rientro in ufficio,
  che nel brief non c'era e ha cambiato le priorità
- Il rifiuto di decidere lo scope da solo: «te le elenco, tu tagli»
- La distinzione fra la **leva** (no-show) e il **risultato** (disponibilità
  percepita)
- **Il checkpoint umano**: la classifica rientrata dal brief. Mostra che una
  decisione detta a voce non sopravvive — deve stare scritta nel documento
