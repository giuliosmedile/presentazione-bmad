# 💻 Amelia — Sprint planning (`SP`)

> Fase di implementazione, primo giro.
> Artefatto prodotto: `_bmad-output/implementation-artifacts/sprint-plan-epic-1.md`

---

**giulio** — Amelia, partiamo con l'epic 1.

💻 Ciao giulio. `bmad-help` se serve.

| # | Codice | Cosa |
|---|---|---|
| 1 | DS | Scrivere test e codice della prossima story, o di quella indicata |
| 2 | QD | Flusso rapido unificato: chiarisci, pianifica, implementa, rivedi, presenta |
| 3 | QA | Generare test API ed E2E per funzionalità esistenti |
| 4 | CR | Code review completa su più assi di qualità |
| 5 | SP | Generare o aggiornare lo sprint plan che sequenzia i task |
| 6 | CS | Preparare una story con tutto il contesto necessario |
| 7 | ER | Retrospettiva in party mode su tutto il lavoro di un'epic |

`SP`. Letti `epics.md`, `architecture.md`, `implementation-readiness-report.md`.

---

💻 Sequenza:

| # | Story | Perché qui |
|---|---|---|
| 1 | 1.1 Ricerca e prenotazione | Senza prenotazioni non c'è niente da liberare. Porta D3, che va prima dei dati |
| 2 | 1.2 Check-in e liberazione | È l'ipotesi del brief |
| 3 | 1.3 Avviso | In parallelo a 1.2, sono indipendenti |
| — | 1.4, 1.5 | Ferme: R1 dell'IR, manca la spec UX |

Tre vincoli che attraversano le story:

- **D3 va in 1.1.** Non è un dettaglio della prenotazione, è lo schema.
  Aggiungerlo in 1.2 vuol dire migrare dati che potrebbero già violarlo.
- **Testcontainers in piedi in 1.1.** D3 non è testabile su H2. La prima story
  paga l'infrastruttura, le altre la ereditano.
- **Il job di D4 idempotente dal primo commit.** Renderlo idempotente dopo
  significa riscriverlo.

---

**giulio** — 1.3 non va in parallelo a 1.2.

💻 Motivo?

---

**giulio** — Una mail che annuncia una liberazione che non esiste ancora è peggio
del silenzio. Sta scritto in `epics.md`.

💻 Confermo, riga 21 di `epics.md`: «1.3 non va rilasciata senza 1.2». L'avevo
letto e l'ho perso quando ho valutato le dipendenze **tecniche**: sul codice sono
davvero indipendenti, la dipendenza è di prodotto.

Rimesse in sequenza. Ho aggiunto la riga anche nel piano di sprint, non solo in
`epics.md`: se un vincolo conta, va ripetuto nel documento che verrà letto
davvero.

Rischi:

| Rischio | Dove | Mitigazione |
|---|---|---|
| `EXCLUDE` richiede `btree_gist` | 1.1, prima migrazione | L'estensione nella prima migrazione Flyway. Se l'ambiente non la permette si scopre subito, non a metà epic |
| Fusi orari sulle fasce | 1.1, 1.2 | Tutto UTC nel db, conversione solo in presentazione |
| Il job libera una prenotazione con check-in | 1.2 | NFR2. Test dedicato, non opzionale |

Piano scritto. Comincio dalla 1.1?

---

**giulio** — Sì, ma prima preparala con CS.

---

## Cosa mostrare

- Il menu di Amelia ha **sette voci**: è l'agente con più superficie, e SP/CS/DS/CR
  sono quattro momenti diversi dello stesso giro
- Il piano non ordina per stima ma per **dipendenza tecnica**
- **Il checkpoint umano**: 1.3 in parallelo. L'agente aveva ragione sul codice e
  torto sul prodotto — e lo dice, invece di limitarsi a obbedire
- La lezione che ne trae: un vincolo va ripetuto nel documento che verrà letto
