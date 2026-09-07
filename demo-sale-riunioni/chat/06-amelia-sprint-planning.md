# 💻 Amelia — Sprint planning (`SP`)

> Fase di implementazione, primo giro.
> Artefatto prodotto: `_bmad-output/implementation-artifacts/sprint-plan-epic-1.md`

---

**giulio** — Amelia, partiamo con l'epic 1.

💻 Ciao giulio. `bmad-help` se serve.

| # | Cod | Descrizione | Skill che parte |
|---|---|---|---|
| 1 | `DS` | Scrivere test e codice della prossima story, o di quella indicata | `bmad-dev-story` |
| 2 | `QD` | Flusso rapido unificato: chiarisci, pianifica, implementa, rivedi, presenta | `bmad-quick-dev` |
| 3 | `QA` | Generare test API ed E2E per funzionalità esistenti | `bmad-qa-generate-e2e-tests` |
| 4 | `CR` | Code review completa su più assi di qualità | `bmad-code-review` |
| 5 | `SP` | Generare o aggiornare lo sprint plan che sequenzia i task | `bmad-sprint-planning` |
| 6 | `CS` | Preparare una story con tutto il contesto necessario | `bmad-create-story` |
| 7 | `ER` | Retrospettiva in party mode su tutto il lavoro di un'epic | `bmad-retrospective` |


In attesa. Rispondi con un numero, un codice o una descrizione.

`SP`. Letti `epics.md`, `architecture.md`, `implementation-readiness-report.md`.

---

💻 Sequenza:

| # | Story | Perché qui |
|---|---|---|
| 1 | 1.1 Check-in dal display | È il segnale. Senza, la liberazione non ha su cosa decidere |
| 2 | 1.2 Liberazione automatica | È l'ipotesi del brief |
| 3 | 1.3 Avviso | In parallelo a 1.2, sono indipendenti |
| — | 1.4, 1.5 | Ferme: R1 dell'IR, manca la spec UX del pannello |

Tre vincoli che attraversano le story:

- **La 1.2 è la prima cosa che scrive fuori da sé.** Fino alla 1.1 il servizio
  legge soltanto. Da qui produce testo che finisce sul calendario delle persone e
  sugli schermi in corridoio. Verso Graph si passa da
  `PrenotazioneOutlookClient`; verso un display, da `TestoDisplay`.
- **Il job idempotente dal primo commit** (D1). Renderlo idempotente dopo
  significa riscriverlo, e vale anche per il job dell'avviso in 1.3.
- **I permessi Graph non si toccano.** Se una story ne chiede di più si ferma e
  passa da IT security: non è una decisione di sprint.

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

Rischi, e il primo lo metto in grassetto perché è l'unico che non so mitigare:

| Rischio | Dove | Mitigazione |
|---|---|---|
| **Niente nella suite parte da una riunione privata, e la 1.2 scrive su schermi pubblici** | 1.2, e ogni testo che finisce su un display | Nessuna, in questo sprint. È R2 della IR, e lo accettiamo dichiarandolo. Chi implementa la 1.2 rilegga `architecture.md` § E3 prima di scrivere |
| Il job libera una prenotazione con check-in | 1.2 | NFR2. Test dedicato, non opzionale |
| Il job gira su tre repliche in cluster | 1.2 | `@SchedulerLock`, deciso qui per non farlo scoprire a metà story |
| Fusi orari sulle fasce | 1.1, 1.2 | Tutto UTC nel db, conversione solo in presentazione |

Sul primo ti dico anche cosa non è: non è una mitigazione. È un rischio scritto.
Se la 1.2 scrive su un display e nessun test parte da una riunione privata,
l'unica rete che resta è la review.

Piano scritto. Comincio dalla 1.1?

---

**giulio** — Sì, ma prima preparala con CS.

---

## Cosa mostrare

- Il menu di Amelia ha **sette voci**: è l'agente con più superficie, e SP/CS/DS/CR
  sono quattro momenti diversi dello stesso giro
- Il piano non ordina per stima ma per **dipendenza tecnica**
- Il rischio in grassetto che l'agente dichiara **e non sa mitigare**, dicendolo:
  e' lo stesso buco da cui passera' il bug tre slide dopo
- **Il checkpoint umano**: 1.3 in parallelo. L'agente aveva ragione sul codice e
  torto sul prodotto — e lo dice, invece di limitarsi a obbedire
- La lezione che ne trae: un vincolo va ripetuto nel documento che verrà letto
