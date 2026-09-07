# 💻 Amelia — Preparare la story (`CS`)

> Il passo che rende la story auto-contenuta. È qui che si decide se
> l'implementazione andrà liscia.
> Artefatto prodotto: `implementation-artifacts/1-2-liberazione-automatica-della-sala.md`

---

**giulio** — CS sulla 1.2.

💻 `CS`. Raccolgo il contesto da: `epics.md` (riga della story), `prd.md` (FR3,
FR4, NFR2), `ux-spec.md` (display e stati), `architecture.md` (E1, E4, E5, D1, D2,
strategia di test), `docs/glossario-dominio.md` (liberazione, serie, occorrenza),
`project-context.md` (convenzioni e regole di prodotto).

---

💻 Story preparata. Cinque criteri di accettazione:

1. Prenotazione senza check-in oltre i 10 minuti → `no_show`, sala libera su
   ricerca e display
2. Prenotazione con check-in → mai toccata
3. La sala non risulta più occupata **su Outlook**
4. Due esecuzioni ravvicinate del job → stesso risultato, nessuna doppia scrittura
5. Errore di Graph → la transizione locale resta, l'errore è loggato

Note di sviluppo: il rilascio passa da `PrenotazioneOutlookClient` (D2), job
idempotente dal primo commit (D1), niente Lombok, un test per AC col nome dell'AC,
mai chiamare Graph nei test.

E una nota che ho messo in evidenza da sola, perché mi sembra la cosa più facile
da sbagliare in questa story: la tabella di `architecture.md` § E4 su cosa
contiene `id_evento_graph`.

**Sta nel progetto. Non sta nei criteri di accettazione di questa story.**

---

**giulio** — Se sta nelle note, perché non è un AC?

💻 Perché un criterio di accettazione descrive cosa deve fare la story per essere
accettata, e nessuno ha chiesto niente sulle serie: FR3 e FR4 parlano di «la
prenotazione» e «la sala». Un AC che dice «non cancellare la serie» sarebbe un
vincolo di progetto travestito da requisito.

Il posto giusto per un vincolo di progetto è il progetto, e lì c'è già. Io lo
riporto nelle note perché chi implementa legge la story e basta.

Quello che non posso fare è garantire che venga letto.

---

**giulio** — Cosa succede se Graph risponde male mentre rilasci?

💻 Non è specificato da nessuna parte. Il PRD non lo dice, l'architettura dice
solo che la riconciliazione la fa il job di sincronizzazione.

Se lo lascio aperto, lo decide chi implementa mentre implementa — cioè si decide
per caso, e la scelta finisce solo nel codice.

---

**giulio** — Allora mettilo come AC. La transizione locale resta e l'errore si
logga: la riconciliazione è del sync, non di questo job.

💻 Aggiunto come AC 5. Registrato nel Change Log come tua aggiunta, non mia: l'ho
sollevato ma la decisione è tua, e fra sei mesi la differenza conta.

---

**giulio** — Perché insisti tanto sul contesto dentro la story?

💻 Perché quando eseguo `DS` leggo **solo la story**. Non rileggo il PRD,
l'architettura e la spec UX: se dovessi tenerli tutti in finestra a ogni giro, il
contesto crescerebbe a ogni story finché non comincio a perdere pezzi.

La regola sta in `project-context.md`: «le story devono essere auto-contenute, chi
le implementa non deve rileggere il PRD».

Quindi `CS` è il momento in cui il contesto si comprime una volta sola. Se la
story non basta da sola, il problema non è mio in fase di implementazione: è qui.

---

**giulio** — Chiaro. DS.

---

## Cosa mostrare

- `CS` **legge sei file** e ne produce uno solo: è la compressione del contesto
- La tabella di E4 riportata nelle note, con la riga che pianta tutto il finale:
  **sta nel progetto, non sta nei criteri di accettazione di questa story**
- La risposta a «perché non è un AC»: è la distinzione fra requisito e vincolo di
  progetto, ed è esattamente la crepa da cui passera' il bug
- **Il checkpoint umano**: l'AC 5 non e' una correzione, e' una domanda dell'umano
  che diventa un criterio — e finisce nel Change Log a nome suo
- La spiegazione del perché il contesto va nella story: è il motivo per cui il
  loop resta sostenibile giro dopo giro
