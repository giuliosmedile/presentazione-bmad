# 💻 Amelia — Preparare la story (`CS`)

> Il passo che rende la story auto-contenuta. È qui che si decide se
> l'implementazione andrà liscia.
> Artefatto prodotto: `implementation-artifacts/1-1-ricerca-e-prenotazione-di-una-sala.md`

---

**giulio** — CS sulla 1.1.

💻 `CS`. Raccolgo il contesto da: `epics.md` (riga della story), `prd.md` (FR1,
FR2, NFR1), `ux-spec.md` (ordinamento e stati), `architecture.md` (D3, D5,
strategia di test), `docs/glossario-dominio.md` (fascia, prenotazione),
`project-context.md` (convenzioni).

---

💻 Story preparata. Cinque criteri di accettazione:

1. Ricerca filtrata per capienza e sovrapposizione, ordinata per capienza crescente
2. Prenotazione crea stato `attiva`, la sala sparisce dalle ricerche
3. Due richieste concorrenti: una passa, l'altra riceve `SalaGiaOccupataException`
4. Durata > 4 ore rifiutata
5. Fasce non contigue rifiutate

Note di sviluppo: D3 con il `WHERE (stato = 'attiva')`, UTC ovunque, niente
Lombok, un test per AC con il nome dell'AC, `RicercaDisponibilita` fuori dal
service di scrittura.

---

**giulio** — L'AC 5 sulle fasce contigue da dove esce? Non è nel PRD.

💻 Corretto, non c'è. L'ho dedotto da `glossario-dominio.md`: «una riunione di
un'ora occupa **due fasce contigue**». Il PRD dice «una o più fasce contigue» in
FR2 ma non specifica cosa succede se non lo sono.

Se lo tolgo, il comportamento resta indefinito e chi implementa sceglie da solo.

---

**giulio** — No, tienilo. Anzi, meglio che l'hai trovato: senza sarebbe stato un
bug scoperto in produzione.

💻 Tenuto. Registrato nel Change Log come tua aggiunta, non mia: l'ho proposto ma
la decisione di tenerlo è tua, e fra sei mesi la differenza conta.

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
- L'AC 5 dedotto dal **glossario**, non dal PRD — la conoscenza di dominio scritta
  da Paige che torna utile due agenti dopo
- **Il checkpoint umano**: qui l'umano non corregge, *conferma* — e la conferma
  finisce nel Change Log a nome suo
- La spiegazione del perché il contesto va nella story: è il motivo per cui il
  loop resta sostenibile giro dopo giro
