# Sprint plan — Epic 1

Generato da: Amelia (Senior Software Engineer), codice menu `SP`
Input: `epics.md`, `architecture.md`, `implementation-readiness-report.md`
Data: 2026-08-28 · Revisione umana: Giulio

## Sequenza

| # | Story | Perché qui | Blocca |
|---|---|---|---|
| 1 | 1.1 Ricerca e prenotazione | Senza prenotazioni non c'è niente da liberare. Porta anche il vincolo D3, che va messo prima di avere dati | 1.2 |
| 2 | 1.2 Check-in e liberazione | È l'ipotesi del brief. Tutto il resto è contorno | 1.3 |
| 3 | 1.3 Avviso di liberazione | Ha senso solo dopo 1.2 | — |
| — | 1.4, 1.5 | Fermate da R1 dell'IR: manca la spec UX | — |

## Vincoli tecnici che attraversano più story

**D3 (vincolo di esclusione) va in 1.1.** Non è un dettaglio della prenotazione:
è lo schema. Aggiungerlo in 1.2 vorrebbe dire migrare prenotazioni già inserite
che potrebbero violarlo.

**Testcontainers va in piedi in 1.1.** Il vincolo D3 non è testabile su H2. La
prima story paga il costo dell'infrastruttura di test, le altre lo ereditano.

**Il job di D4 va scritto idempotente dal primo commit.** Renderlo idempotente
dopo significa riscriverlo.

## Rischi

| Rischio | Dove morde | Mitigazione |
|---|---|---|
| Il vincolo `EXCLUDE` richiede `btree_gist`, che è un'estensione | 1.1, prima migrazione | Prima migrazione Flyway crea l'estensione. Se l'ambiente non lo permette, si scopre subito e non a metà epic |
| Fusi orari sulle fasce | 1.1 e 1.2 | Tutto in UTC nel database, conversione solo in presentazione. Scritto nella story |
| Il job libera una prenotazione con check-in | 1.2 | NFR2. Test dedicato, non opzionale |

## Cosa NON è in questo sprint

Le notifiche (1.3 dipende da 1.2 ma la mail vera si può stubbare), la vista
calendario (tolta in UX), qualunque cosa dell'epic 2.

---

**Nota del revisore umano (Giulio):** il piano iniziale metteva 1.3 in parallelo a
1.2 «perché indipendenti». Non lo sono: una mail che annuncia una liberazione che
non esiste ancora è peggio del silenzio. Rimesse in sequenza. È scritto anche in
`epics.md`, ma nel piano di sprint era rientrato — segno che quando un vincolo
conta va ripetuto nel documento che verrà letto davvero.
