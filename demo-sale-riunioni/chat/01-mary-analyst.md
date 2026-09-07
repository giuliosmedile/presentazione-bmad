# 📊 Mary — Business Analyst

> Fase di analisi. Da una feature già decisa a un problema misurato.
> Artefatto prodotto: `_bmad-output/planning-artifacts/product-brief.md`

---

**giulio** — `/bmad-agent-analyst`

📊 Ciao giulio, sono Mary. Ricorda che puoi invocare `bmad-help` in qualunque
momento se vuoi un consiglio su come procedere.

Ecco cosa posso fare:

| # | Cod | Descrizione | Skill che parte |
|---|---|---|---|
| 1 | `BP` | Brainstorming guidato | `bmad-brainstorming` |
| 2 | `MR` | Ricerca di mercato, panorama competitivo, bisogni e trend | `bmad-market-research` |
| 3 | `DR` | Deep dive di dominio, terminologia e competenza specialistica | `bmad-domain-research` |
| 4 | `TR` | Fattibilità tecnica, opzioni architetturali, approcci | `bmad-technical-research` |
| 5 | `CB` | Creare o aggiornare il product brief | `bmad-product-brief` |
| 6 | `WB` | Working Backwards PRFAQ — forgiare e stressare il concept | `bmad-prfaq` |
| 7 | `DP` | Analizzare un progetto esistente e produrne la documentazione | `bmad-document-project` |

In attesa. Rispondi con un numero, un codice o una descrizione.

---

**giulio** — Il facility manager vuole la liberazione automatica delle sale: se
nessuno fa check-in entro dieci minuti, la sala torna libera. Va aggiunta al
servizio che abbiamo. `CB`

📊 `CB` — parte `bmad-product-brief`.

Prima di aprirlo, due cose.

La prima: quella che mi hai dato non è un problema, è già una soluzione.
«Liberazione automatica dopo dieci minuti» è una risposta — la domanda qual è?

La seconda: il servizio esiste già, e questo cambia da dove parto. Se non l'avete
mai fatto, il primo giro utile è `DP` su questo repository: mi leggo il codice e
ne esce la documentazione che non c'è. Senza, il brief lo scrivo al buio su un
sistema che ha quattro anni di decisioni dentro.

---

**giulio** — `DP` l'abbiamo già fatto la settimana scorsa, la documentazione c'è.
Il problema è che non si trova mai una sala.

📊 Bene, allora la leggo io da lì e non ti chiedo niente che sia già scritto.

Su «non si trova mai una sala»: è un'impressione o qualcuno l'ha misurata?

---

**giulio** — Impressione. Ma è la lamentela numero uno da mesi.

📊 Allora partiamo da lì, perché è il bivio che decide tutto il resto. Ci sono due
storie possibili dietro «non si trova una sala», e portano a due prodotti diversi:

- **Le sale sono poche.** Allora il problema è di budget e di mattoni, e nessun
  software lo risolve. La liberazione automatica non sposterebbe niente.
- **Le sale ci sono ma risultano occupate quando non lo sono.** Allora è un
  problema di comportamento, e lì si può intervenire.

La feature che mi hai chiesto ha senso solo nella seconda. Se costruiamo per la
seconda e la vera è la prima, abbiamo buttato tre mesi e il facility manager
avrebbe dovuto spendere quei soldi in metri quadri.

Servono dati prima di scrivere qualunque cosa. Cosa possiamo tirare fuori senza
chiedere budget?

---

**giulio** — Il nostro database ha già tutte le prenotazioni sincronizzate. E posso
far fare un giro nelle sale.

📊 Perfetto, e sono due fonti indipendenti: il database dice cosa il sistema
*crede*, il giro dice cosa succede *davvero*. Quando divergono, la distanza è il
problema.

Ti propongo `MR` sui dati che hai, poi `CB` per il brief. Ti chiedo tre cose:

1. Quattro settimane di prenotazioni, non una: una settimana la sporca qualunque
   festività
2. Il giro fisico su almeno 3 giorni diversi, in fascia 10-12 e 14-16
3. I ticket all'help desk sul tema, se esistono

---

**giulio** — MR. Ecco tutto. [query + 3 giri]

📊 Guarda qui, perché questo è il momento in cui il progetto ha trovato il suo
appiglio.

| Fonte | Dato |
|---|---|
| Il nostro database, 4 settimane | 612 prenotazioni su 6 sale |
| Delle quali | **70% occorrenze di serie ricorrenti** |
| Giro fisico, 3 giorni | **41% delle sale "occupate" erano vuote** |
| Ticket, 6 mesi | 38 richieste «non trovo una sala», zero su altri temi |

Il 41% decide il bivio, e sceglie la seconda ipotesi: le sale non sono poche, sono
occupate a vuoto. Il tuo facility manager ha ragione sulla direzione.

E ha una conseguenza scomoda che ti dico subito: **quel numero va rimisurato dopo
il rilascio**. Se non scende, il prodotto non è servito a niente, e sarà onesto
dirlo.

Il 70% invece è il dato che non mi aspettavo, ed è quello che cambia la forma di
quello che costruiremo. La maggior parte di ciò che libereremo non è una riunione:
è la ripetizione di una riunione decisa una volta e mai più guardata. Chi l'ha
creata magari non lavora nemmeno più qui.

I ticket dicono un'altra cosa utile: nessuno si lamenta delle sale, del wifi o
delle prese. Solo di non trovarne. È un problema a una dimensione sola, il che è
una fortuna.

---

**giulio** — Quindi la gente prenota e non disdice.

📊 Sì, ma attenzione a come lo formuliamo, perché da qui discende il prodotto.

Non è che la gente sia sbadata. È che **disdire non ha nessuna conseguenza**, e
prenotare largo «per sicurezza» è il comportamento razionale in un sistema dove
chi non prenota non trova. Stiamo osservando persone che rispondono bene a un
sistema fatto male.

E per il 70% non c'è nemmeno una scelta da fare: disdire una singola occorrenza di
una ricorrenza su Outlook è un gesto che metà delle persone non sa fare. Non
stiamo osservando pigrizia, stiamo osservando un'interfaccia.

Se lo formuliamo come «i colleghi sono sbadati», il prodotto diventa un sistema di
richiami e fallirà. Se lo formuliamo come «liberare deve costare zero ed essere
automatico», diventa una feature.

Passo a `CB` e ti scrivo il brief.

---

**giulio** — CB.

📊 Brief scritto in `_bmad-output/planning-artifacts/product-brief.md`.

Le parti che ti chiedo di guardare per prime:

- **Il bivio**, scritto per esteso: la feature che hai chiesto vale nell'ipotesi
  due, e la due l'abbiamo verificata. Serve fra sei mesi, quando qualcuno chiederà
  perché non avete comprato altre sale
- **L'ipotesi falsificabile**: se dopo un mese il 41% non scende sotto il 20%,
  l'ipotesi è sbagliata ed è la capienza il problema. L'ho scritta apposta in modo
  che si possa perdere
- **Le alternative scartate**, con il motivo: sensori di presenza, limite di
  prenotazioni a testa, scadenza automatica delle serie, solo un report
- **Tre domande aperte** che non ho voluto chiudere da sola. La terza è la più
  importante: le occorrenze si liberano una alla volta o si tocca la serie?

Ho anche proposto due leve per far scendere il numero: una mail al responsabile
dopo tre no-show, e una classifica dei reparti. I dati per farle ci sono.

---

**giulio** — Le leve toglile tutte e due.

📊 Tolte. Mi dici il motivo, così lo scrivo nel brief? Se resta solo qui dentro,
al prossimo giro te le ripropongo.

---

**giulio** — Perché in questa azienda una misura pubblica sul singolo diventa una
gara al contrario, e la gente smette di prenotare col proprio nome. Il primo
effetto è che il 41% smette di essere misurabile: prenotano col nome del collega o
non prenotano e occupano la sala lo stesso. Il dato lo teniamo aggregato.

📊 Registrato nel brief come nota del revisore, con il tuo motivo per esteso.

E dico una cosa che vale per il resto del progetto: questa non era
un'informazione che mi mancava — il dato ce l'avevo, la logica funzionava. Mi
mancava di sapere **come funziona questa azienda**. È il tipo di correzione che
non potevo fare da sola, ed è il motivo per cui il brief passa da te prima di
arrivare a John.

Una cosa però te la segnalo: il motivo l'ho scritto **nel brief**, e il brief
descrive questo prodotto. Il prossimo documento non lo legge.

Vuoi che passi il brief al product manager, o preferisci rileggerlo prima?

---

**giulio** — Ho letto. Passa a John.

---

## Cosa mostrare di questa chat

- Il menu a **tre colonne**: la terza dice quale skill parte. `CB` non e' una
  preferenza di conversazione, e' `bmad-product-brief` che si attiva col suo contesto
- **«Quella che mi hai dato non è un problema, è già una soluzione»**: arriva
  prima di qualunque cosa, su una feature che il committente ha gia' deciso
- Il bivio esplicitato (poche sale vs sale sprecate) invece di partire a costruire
- Il 70% di occorrenze, che nessuno cercava e che cambia la forma del prodotto
- **Il checkpoint umano**: le due leve punitive. L'agente aveva ragione sui dati
  e torto sull'azienda
- L'ultima riga di Mary: il motivo e' scritto nel brief, e nel brief resta. E'
  il gancio della slide dopo
