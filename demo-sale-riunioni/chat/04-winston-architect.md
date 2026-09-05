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

Vado con `CA`. Ho letto entrambi, più il contesto di progetto.

Comincio dalla domanda che decide la forma di tutto: **quanti numeri stiamo
parlando?** Sale, utenti, prenotazioni al giorno.

---

**giulio** — Sei sale, mille dipendenti, forse trenta prenotazioni al giorno.

🏗️ Bene, allora ti risparmio un mese di lavoro.

A questi numeri la risposta giusta è **la più noiosa possibile**: un servizio
Spring Boot, un database Postgres, il frontend servito dallo stesso servizio.
Niente code, niente cache, niente secondo servizio.

Ti spiego il ragionamento invece di darti il verdetto. Separare le notifiche in un
servizio a parte avrebbe senso se scalassero diversamente dalle prenotazioni. Con
sei sale non scalano affatto. Il costo del deploy doppio e della chiamata di rete
lo pagheremmo da subito; il beneficio forse mai. Quando avrà senso? Quando avrete
più sedi con volumi diversi. L'ho scritto come condizione di revisione, così fra un
anno si sa quando riaprire il discorso.

---

**giulio** — Mi aspettavo mi proponessi Redis per le letture.

🏗️ Nella prima stesura te l'avevo proposto. Poi mi sono chiesto quante letture al
secondo stiamo servendo davvero, e la risposta è: poche. Una cache davanti a una
query che gira in due millisecondi su seimila righe aggiunge un componente da
invalidare, monitorare e spiegare a chi arriva dopo.

L'ho tolta. Se poi NFR1 non regge sotto misura, torniamo qui.

Adesso la decisione che conta davvero, ed è l'unica su cui non transigo.

---

**giulio** — Dimmi.

🏗️ **La doppia prenotazione va impedita dal database, non dal codice.**

Il caso è questo: due persone premono Prenota nello stesso istante sulla stessa
sala. Nel codice fai la SELECT, vedi che è libera, fai la INSERT. Entrambe le
richieste vedono libero, entrambe inseriscono. Il controllo applicativo non ha un
bug: è proprio strutturalmente incapace di prevenire quel caso.

Postgres invece lo sa fare:

```sql
EXCLUDE USING gist (sala_id WITH =, periodo WITH &&) WHERE (stato = 'attiva')
```

Il `WHERE (stato = 'attiva')` è la parte che nessuno ricorda: senza, una sala
liberata per no-show resterebbe bloccata per sempre — cioè il bug esatto che
questo progetto esiste per evitare.

Conseguenza per chi implementa: il conflitto arriva come violazione di vincolo e
va tradotto in un errore leggibile, perché Sally ha chiesto che l'utente capisca
cosa è successo. E va verificato **quale** vincolo è stato violato, non
intercettato genericamente.

---

**giulio** — E la liberazione automatica come la fai?

🏗️ Job schedulato al minuto che cerca le prenotazioni scadute senza check-in.

Le alternative erano `pg_cron` e uno scheduler per singola prenotazione. Le ho
scartate entrambe: uno scheduler per prenotazione va tenuto in sincrono con
disdette e modifiche, e quando sbaglia sbaglia in silenzio. Un job che scansiona è
banale da capire, da testare e da rieseguire se salta un giro.

Vincolo che ne deriva e che ho scritto in grassetto nella story: **il job deve
essere idempotente dal primo commit**. E NFR2 non ammette sconti — se il check-in
c'è, la prenotazione non decade mai, in nessuna circostanza.

Sei decisioni in tutto, ognuna con la sua alternativa scartata e il motivo. Un
`architecture.md` che elenca solo cosa avete scelto è un documento a metà: fra un
anno nessuno saprà cosa avevate già considerato.

---

**giulio** — Fammi la IR prima di passare ad Amelia.

🏗️ `IR` eseguita. **Pronto con due rilievi.**

**R1 — FR6 e FR7 non hanno spec UX.** Disdetta e forzatura stanno nel PRD e
reggono nel modello dati, ma nessuno ha disegnato come si fanno. Le story 1.4 e
1.5 non sono implementabili: chi le prende dovrebbe inventarsi l'interfaccia.
Non blocca l'epic — 1.1, 1.2 e 1.3 sono complete su tutti e quattro i documenti —
ma 1.4 non entra in sprint finché Sally non estende la spec.

**R2 — il PRD parla di «minuto 7», la UX ha tolto il countdown.** Non è una
contraddizione, ma i due documenti raccontano due esperienze diverse. Se non lo
scriviamo nella story 1.3, chi implementa aggiunge un timer perché «il PRD parla
di minuti».

Verdetto: epic 1 pronta per 1.1, 1.2 e 1.3.

---

**giulio** — Perfetto, questo è esattamente il tipo di buco che ci sfugge sempre.

🏗️ È il motivo per cui `IR` è un passo separato e non un controllo che faccio
mentre scrivo. Quando scrivi un documento sei dentro quel documento; la coerenza
fra quattro documenti la vedi solo se ti fermi apposta a guardarla.

---

## Cosa mostrare di questa chat

- «Quanti numeri stiamo parlando» **prima** di qualunque proposta di architettura
- Redis proposto e poi ritirato dall'agente stesso, con il motivo
- Il vincolo `EXCLUDE` — la spiegazione del perché il controllo applicativo è
  strutturalmente incapace, non semplicemente buggato
- Ogni decisione con l'**alternativa scartata** e la condizione per rivederla
- **La IR che trova due buchi reali** fra documenti che, presi uno a uno,
  sembravano tutti a posto
