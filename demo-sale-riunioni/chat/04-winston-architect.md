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

🏗️ La decisione che vale è D2, e l'hai presa tu in party mode: la sala torna
libera anche su Outlook. Io ci ho aggiunto la contropartita scritta accanto, che è
la parte che di solito non si scrive: da questa story in avanti **il servizio
scrive fuori da sé**.

Fino alla 1.1 leggeva calendari e li mostrava. Da domani mette le mani su due
superfici che non sono nostre: il calendario delle persone, e i sei schermi
appesi nei corridoi.

---

**giulio** — Sui display avevi detto che c'era già una regola.

🏗️ Sì, e non è una mia decisione: è una cosa che ho trovato leggendo il codice.
Le ho dato un capitolo suo, § E3, invece di una riga in mezzo alle altre.

**Il display non è una schermata dell'applicazione. È un cartello acceso in un
corridoio.**

Nel database c'è una colonna `privato`, aggiunta sei settimane dopo il primo
rilascio, e c'è una classe `TestoDisplay` che per le riunioni private scrive
«Riunione riservata» invece del titolo. Il commento sulla migrazione dice perché,
e l'ho verificato col facility manager: nella prima versione il display mostrava
il titolo di tutto, e sullo schermo davanti alla sala grande è comparso il titolo
di un colloquio con nome e cognome. Le Risorse Umane hanno aperto un caso.

La conseguenza per chi implementa la scrivo in una riga sola: `titolo()` è il dato
grezzo dello specchio, serve alla sincronizzazione e ai log. **Non è testo da
mostrare.** Tutto quello che finisce su uno schermo passa da `TestoDisplay`.

E non perché è più elegante. Perché quella funzione sa una cosa che chi la chiama
non è tenuto a sapere.

---

**giulio** — Quindi chi implementa lo trova.

🏗️ Chi implementa lo trova **se lo legge**. Che non è la stessa cosa, e non ho un
modo per garantirlo: il documento è lungo, la story è auto-contenuta per
costruzione, e su una riunione normale il metodo giusto e quello sbagliato
restituiscono la stessa identica stringa. Non è un errore che si vede.

Quello che posso fare è metterlo anche nei rischi dello sprint plan e chiedere ad
Amelia di riportarlo nelle note della story. Fatte tutte e due.

---

**giulio** — E la liberazione automatica come la fai?

🏗️ Job schedulato al minuto che cerca le prenotazioni scadute senza check-in.

Le alternative erano `pg_cron` e uno scheduler per singola prenotazione. Scartate
entrambe: uno scheduler per prenotazione va tenuto in sincrono con disdette,
spostamenti e modifiche, e quando sbaglia sbaglia in silenzio. Un job
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
suite non esiste una fixture di riunione privata: l'ho scritto nella strategia di
test, ma scriverlo non lo chiude. Ed è un buco peggiore di quanto sembri, perché
su una riunione normale il metodo giusto e quello sbagliato danno lo stesso
risultato: il verde non prova niente. Nessuna story dell'epic 1 lo copre, e
l'epic 1 è tutta su cose che finiscono su schermi che legge chiunque passi.

Verdetto: epic 1 pronta per 1.1, 1.2 e 1.3. R2 resta aperto e dichiarato.

---

**giulio** — R2 lo teniamo aperto, non voglio allungare l'epic. Ma segnalo.

🏗️ Segnalato in tre posti: strategia di test, rischi dello sprint plan, e il
verdetto della IR. È il massimo che posso fare da qui: dopo, dipende da chi legge.

---

## Cosa mostrare di questa chat

- La premessa: **metà del documento è una ricostruzione**, ed è dichiarata come
  tale. È come si scrive architettura su un progetto che esiste già
- La contropartita di D2 scritta accanto alla scelta: da qui il servizio scrive
  fuori da sé, su due superfici che non sono nostre
- **§ E3 trovato leggendo il codice**: una colonna, una classe e un commento
  arrabbiato del 2022. È il pezzo che salva il finale della demo
- «Chi implementa lo trova **se lo legge**», e il motivo per cui non basta: su una
  riunione normale il metodo giusto e quello sbagliato danno la stessa stringa
- Ogni decisione con l'**alternativa scartata** e la condizione per rivederla
- **La IR che dichiara un rischio che nessuno chiude.** R2 è scritto tre volte e
  il bug passa lo stesso: un rischio scritto non è una mitigazione
