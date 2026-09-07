# 💻 Amelia — Implementare la story (`DS`)

> Test prima, poi il codice. Task nell'ordine scritto.
> Artefatti toccati: il codice, i test, e il **Dev Agent Record** della story.

---

**giulio** — DS sulla 1.2.

💻 `DS`. Leggo `1-2-liberazione-automatica-della-sala.md`. Cinque AC, sei task.
Eseguo in ordine, non salto.

---

💻 Task 1 — il job.

`src/main/java/it/azienda/saleriunioni/liberazione/LiberazioneJob.java`

Ogni minuto, con `@SchedulerLock`: in cluster l'applicazione gira su tre repliche
e senza lock il job partirebbe tre volte. L'idempotenza di AC4 lo renderebbe
innocuo, ma tre volte innocuo sono comunque tre scritture verso Graph.

Task 1 ✅.

---

💻 Task 2-4 — selezione, transizione, rilascio della sala. Test rossi prima, poi
verdi.

`LiberazioneServiceTest`: 5 test, uno per AC, nomi come da convenzione
(`sala_liberata_se_nessun_check_in_entro_dieci_minuti`,
`prenotazione_con_check_in_mai_toccata`…).

Per il rilascio ho riusato `PrenotazioneOutlookClient.annulla()`: c'era già, e
`architecture.md` § D2 dice che verso Graph si passa da lì e che il client si
riusa invece di riscriverlo.

Tutti verdi tranne AC 4.

---

**giulio** — Perché AC 4 no?

💻 Perché con una sola esecuzione non è testabile. Il test lanciava il job una
volta e verificava il risultato: sarebbe passato anche senza `@SchedulerLock` e
anche con l'ordine delle operazioni sbagliato. Non stavo testando l'idempotenza,
stavo testando che il job funziona.

Riscritto: due esecuzioni con lo stesso `Clock` fisso, e la verifica non è sul
risultato finale ma sul **numero di chiamate a Graph**. Deve essere uno.

E lì ho trovato un secondo problema, nello stub: lo stub di WireMock rispondeva
`200` a qualunque `DELETE` su qualunque URL. Il test verificava che una chiamata
partisse, non quale. Stretto lo stub sull'URL esatto dell'evento.

Adesso è verde e fallisce se tolgo il lock. Prima non falliva.

---

**giulio** — Questa è la differenza fra un test e un test che serve.

💻 `LiberazioneIdempotenzaTest`. 11 test verdi in totale.

Due punti da segnalare, e su nessuno dei due decido io.

Il primo: AC 5 dice che la transizione locale resta se Graph fallisce. Sembra un
errore a chi legge il codice, e non lo è: è la scelta di D1, riconciliazione al
giro di sincronizzazione. L'ho scritto nelle Completion Notes perché in review
verrà sollevato.

Il secondo: i 10 minuti sono una costante di dominio, non una proprietà di
configurazione. Se il facility manager vuole poterla cambiare senza rilascio è una
decisione di PRD, non mia.

---

**giulio** — Va bene così. Aggiorna la story.

💻 Definition of done:

```
✓ tutti i task e subtask [x]      ✓ AC1-AC5 soddisfatti
✓ 11 test, 0 failure              ✓ File List completa
✓ Dev Agent Record compilato      ✓ Change Log aggiornato
✓ solo le sezioni permesse toccate
```

Scritto **dentro la story**, non qui:

- Agent Model Used: Amelia, skill `bmad-dev-story`
- Completion Notes: quattro voci, incluse le due segnalazioni sopra
- File List: 8 file, ognuno con nuovo/modificato
- Change Log

Status della story → `review`.

✅ `sprint-status.yaml` aggiornato: `1-2-liberazione-automatica-della-sala` → `review`.

Non decido io quando è finita: la definition of done è una lista che devo
passare, e lo stato lo muove il workflow, non la mia impressione.

---

**giulio** — CR. Chi scrive non è chi valida.

---

## Cosa mostrare

- Il **test che passava senza testare niente**, due volte di fila: l'esecuzione
  singola che non prova l'idempotenza, e lo stub che accettava qualunque URL. È
  un errore che fa chiunque, umano compreso
- E il seguito amaro: lo stub **stretto sull'URL giusto** resta verde lo stesso,
  perché la fixture è una prenotazione singola. Il test è migliorato e non ha
  trovato niente
- Il riuso di `annulla()` motivato con l'architettura alla mano: la cosa giusta da
  fare, e la strada per cui passa il bug
- **La definition of done non è un'autovalutazione**: è una lista che il workflow
  impone, e lo stato lo muove `sprint-status.yaml`, non l'agente che dice «fatto»
- Le due segnalazioni che Amelia fa **senza decidere da sola** (errore Graph,
  costante vs configurazione)
- Il **Dev Agent Record** che si scrive da solo: è il «diario della story» della
  slide 15
- La chiusura: passa a `CR` invece di auto-approvarsi
