# 💻 Amelia — Implementare la story (`DS`)

> Test prima, poi il codice. Task nell'ordine scritto.
> Artefatti toccati: il codice, i test, e il **Dev Agent Record** della story.

---

**giulio** — DS sulla 1.1.

💻 `DS`. Leggo `1-1-ricerca-e-prenotazione-di-una-sala.md`. Cinque AC, sette task.
Eseguo in ordine, non salto.

---

💻 Task 1 — migrazione.

`src/main/resources/db/migration/V1__sale_e_prenotazioni.sql`

```sql
CREATE EXTENSION IF NOT EXISTS btree_gist;

CREATE TABLE prenotazione (
    id          uuid PRIMARY KEY,
    sala_id     uuid NOT NULL REFERENCES sala(id),
    periodo     tstzrange NOT NULL,
    stato       text NOT NULL,
    prenotante  text NOT NULL,
    CONSTRAINT prenotazione_no_overlap
      EXCLUDE USING gist (sala_id WITH =, periodo WITH &&)
      WHERE (stato = 'attiva')
);
```

Task 1 ✅. AC 1, 2, 3 coperti a livello di schema.

---

💻 Task 2-5 — dominio, ricerca, service, controller. Test rossi prima, poi verdi.

`PrenotazioneServiceTest`: 5 test, uno per AC, nomi come da convenzione
(`prenotazione_rifiutata_se_sala_gia_occupata`, `durata_massima_superata_rifiutata`…).

Tutti verdi tranne AC 3.

---

**giulio** — Perché AC 3 no?

💻 Perché con un thread solo non è testabile. Le due transazioni concorrenti non
si sovrappongono mai se le eseguo in sequenza: il test passa sempre, e passerebbe
anche senza il vincolo.

Riscritto con due thread e una `CountDownLatch` che le fa partire insieme.

Su H2 passava comunque — H2 non ha `EXCLUDE USING gist`, quindi non stavo
testando niente. Spostato su Testcontainers con Postgres reale, come da strategia
di test in `architecture.md`.

Adesso è verde e fallisce se tolgo il vincolo. Prima non falliva.

---

**giulio** — Questa è la differenza fra un test e un test che serve.

💻 `PrenotazioneConcorrenzaTest`. 14 test verdi in totale.

Un punto da segnalare: l'**ordinamento per capienza crescente** non è nel PRD, è
nella spec UX. L'ho implementato come da spec UX. Segnalo perché il PRD dovrebbe
citarlo: se qualcuno legge solo quello, non sa che l'ordine è una scelta e non un
caso.

Secondo punto: durata massima 4 ore implementata come costante di dominio, non
come proprietà di configurazione. Se deve diventare configurabile è una decisione
di PRD, non mia.

---

**giulio** — Va bene così. Aggiorna la story.

💻 Dev Agent Record aggiornato:

- Agent Model Used: Amelia, skill `bmad-dev-story`
- Completion Notes: quattro voci, incluse le due segnalazioni sopra
- File List: 15 file, tutti elencati con nuovo/modificato

Change Log aggiornato.

Vuoi che chiuda la story o passo a `CR`?

---

**giulio** — CR. Chi scrive non è chi valida.

---

## Cosa mostrare

- Il **test che passava senza testare niente**: è il momento migliore della demo
  perché è un errore che fa chiunque, umano compreso
- H2 vs Postgres reale: il motivo per cui `architecture.md` imponeva Testcontainers
- Le due segnalazioni che Amelia fa **senza decidere da sola** (ordinamento nel
  PRD, costante vs configurazione)
- Il **Dev Agent Record** che si scrive da solo: è il «diario della story» della
  slide 15
- La chiusura: passa a `CR` invece di auto-approvarsi
