# 🏗️ Winston — System Architect

> Fase di solutioning. Le decisioni che poi vincolano.
> Artefatti prodotti: `architecture.md`, `implementation-readiness-report.md`

---

**giulio** — Winston, PRD e spec UX sono pronti.

🏗️ Ciao giulio. `bmad-help` è sempre disponibile.

| # | Cod | Descrizione | Skill che parte |
|---|---|---|---|
| 1 | `CA` | Workflow guidato per documentare le decisioni tecniche e tenere in riga l'implementazione | `bmad-create-architecture` |
| 2 | `IR` | Verificare che PRD, UX, architettura ed epiche siano allineati | `bmad-check-implementation-readiness` |


In attesa. Rispondi con un numero, un codice o una descrizione.

Vado con `CA`. Ho letto entrambi, più il contesto di progetto e la documentazione
che è uscita da `DP`.

Una premessa sul documento che sto per scrivere: metà non sono decisioni mie. Il
servizio ha quattro anni e le scelte importanti sono già state prese da qualcuno
che non c'è più. Quella metà la scrivo come ricostruzione, e dove ho dedotto
l'intenzione invece di trovarla scritta lo dico.

---

**giulio** — E la parte tua?

🏗️ Poca roba, e va bene così: la forma del sistema è già decisa e non c'è motivo
di cambiarla per aggiungere un job.

Un servizio Spring Boot, un Postgres, il frontend servito dallo stesso servizio.
Sei sale, mille dipendenti, trenta prenotazioni al giorno. A questi numeri
qualunque cosa più complessa è un costo che paghiamo senza comprare niente.

Le mie decisioni nuove sono cinque, e la più interessante è la più piccola.

---

**giulio** — Dimmi.

🏗️ Nella prima stesura avevo proposto un client Graph nuovo, dedicato alla
liberazione. Il ragionamento era: non tocco codice del 2023 che non ho scritto io,
riduco il rischio.

Poi mi sono chiesto cosa costa davvero. Due punti di scrittura verso Graph
vogliono dire due posti dove sbagliare i permessi, due gestioni del throttling,
due comportamenti diversi quando Exchange risponde 429. E fra due anni nessuno
saprà più perché ce ne sono due.

Riuso quello esistente. È anche la convenzione scritta in `project-context.md`:
ogni scrittura verso Graph passa da `PrenotazioneOutlookClient`.

---

**giulio** — Confermo, riusalo. Non voglio due strade verso Outlook.

🏗️ Registrato come D2, con l'alternativa scartata e con il tuo nome sulla
conferma.

Adesso però la cosa importante di questo documento, e non è una mia decisione: è
una cosa che ho trovato leggendo il codice.

---

**giulio** — Vai.

🏗️ **La colonna `id_evento_graph` non contiene sempre l'id dell'evento che la riga
rappresenta.**

| `tipo` | cosa c'è dentro |
|---|---|
| `SINGOLA` | l'id dell'evento |
| `SERIE` | l'id della serie |
| `OCCORRENZA` | **l'id della serie** |

Ci sono arrivato dalla migrazione del 2024: l'indice unico è su
`(id_evento_graph, inizio)`, e un indice del genere ha senso solo se quella
colonna da sola non identifica una riga.

Il perché l'ho ricostruito e sta scritto come ricostruzione: gli id delle
occorrenze che Graph restituisce nel `calendarView` non sono stabili fra due delta
query. Chi l'ha scritto ha preso l'unica cosa stabile che aveva — l'id della serie
— e ha aggiunto l'orario per distinguere le occorrenze. Era la scelta giusta con
le informazioni che aveva.

Ma la conseguenza vale adesso: **quella colonna è una chiave di sincronizzazione,
non un indirizzo a cui scrivere.** Le occorrenze sono il 70% delle righe, e dalla
1.2 in poi cominciamo a scrivere.

Le ho dato un capitolo suo, § E4, invece di una riga in mezzo alle altre.

---

**giulio** — Quindi chi implementa lo trova.

🏗️ Chi implementa lo trova **se lo legge**. Che non è la stessa cosa, e non ho un
modo per garantirlo: il documento è lungo, la story è auto-contenuta per
costruzione, e il capitolo che serve sta sessanta righe più su di quello che
cerchi.

Quello che posso fare è metterlo anche nei rischi dello sprint plan e chiedere ad
Amelia di riportarlo nelle note della story. Fatte tutte e due.

---

**giulio** — E la liberazione automatica come la fai?

🏗️ Job schedulato al minuto che cerca le prenotazioni scadute senza check-in.

Le alternative erano `pg_cron` e uno scheduler per singola prenotazione. Scartate
entrambe: uno scheduler per prenotazione va tenuto in sincrono con disdette,
spostamenti e modifiche della serie, e quando sbaglia sbaglia in silenzio. Un job
che scansiona è banale da capire, da testare e da rieseguire se salta un giro.

Vincolo che ne deriva: **il job deve essere idempotente dal primo commit**. E NFR2
non ammette sconti — se il check-in c'è, la prenotazione non decade mai.

Cinque decisioni nuove, ognuna con la sua alternativa scartata e il motivo. Un
`architecture.md` che elenca solo cosa avete scelto è un documento a metà: fra un
anno nessuno saprà cosa avevate già considerato. Su questo progetto ne avete la
prova, perché è esattamente quello che è successo fra il 2022 e adesso.

---

**giulio** — Fammi la IR prima di passare ad Amelia.

🏗️ `IR` eseguita. **Pronto con due rilievi.**

**R1 — FR6 e FR7 non hanno spec UX.** Disdetta dal display e forzatura stanno nel
PRD e reggono nel modello dati, ma nessuno ha disegnato come si fanno. Le story
1.4 e 1.5 non sono implementabili: chi le prende dovrebbe inventarsi
l'interfaccia. Non blocca l'epic — 1.1, 1.2 e 1.3 sono complete su tutti e quattro
i documenti — ma 1.4 non entra in sprint finché Sally non estende la spec.

**R2 — la strategia di test dichiara un buco che nessuna story chiude.** Nella
suite non esiste una fixture di tipo `OCCORRENZA`: l'ho scritto nella strategia di
test, ma scriverlo non lo chiude. Nessuna story dell'epic 1 lo copre, e l'epic 1 è
tutta sulle scritture verso i calendari.

Verdetto: epic 1 pronta per 1.1, 1.2 e 1.3. R2 resta aperto e dichiarato.

---

**giulio** — R2 lo teniamo aperto, non voglio allungare l'epic. Ma segnalo.

🏗️ Segnalato in tre posti: strategia di test, rischi dello sprint plan, e il
verdetto della IR. È il massimo che posso fare da qui: dopo, dipende da chi legge.

---

## Cosa mostrare di questa chat

- La premessa: **metà del documento è una ricostruzione**, ed è dichiarata come
  tale. È come si scrive architettura su un progetto che esiste già
- Il client nuovo proposto e poi ritirato dall'agente stesso, con il motivo — e
  la conferma umana che finisce nel documento col nome sopra
- **§ E4 trovato leggendo una migrazione**: l'indice unico su due colonne ha senso
  solo se la prima da sola non basta. È il pezzo che salva il finale della demo
- «Chi implementa lo trova **se lo legge**»: l'agente dice da solo qual è il limite
  di quello che sta facendo
- Ogni decisione con l'**alternativa scartata** e la condizione per rivederla
- **La IR che dichiara un rischio che nessuno chiude.** R2 è scritto tre volte e
  il bug passa lo stesso: un rischio scritto non è una mitigazione
