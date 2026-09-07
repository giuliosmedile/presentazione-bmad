# Sprint plan — Epic 1

Generato da: Amelia (Senior Software Engineer), codice menu `SP`
Input: `epics.md`, `architecture.md`, `implementation-readiness-report.md`
Data: 2026-08-28 · Revisione umana: Giulio

## Sequenza

| # | Story | Perché qui | Blocca |
|---|---|---|---|
| 1 | 1.1 Check-in dal display | È il segnale. Senza, la liberazione non ha su cosa decidere | 1.2 |
| 2 | 1.2 Liberazione automatica | È l'ipotesi del brief. Tutto il resto è contorno | 1.3 |
| 3 | 1.3 Avviso di liberazione | Ha senso solo dopo 1.2 | — |
| — | 1.4, 1.5 | Fermate da R1 dell'IR: manca la spec UX del pannello | — |

## Vincoli tecnici che attraversano più story

**La 1.2 è la prima cosa che scrive su Outlook.** Fino a lì il servizio legge.
Ogni scrittura passa da `PrenotazioneOutlookClient` (D2, e convenzione di
`project-context.md`): non si apre una seconda strada verso Graph.

**Il job va scritto idempotente dal primo commit.** Renderlo idempotente dopo
significa riscriverlo. Vale per il job di liberazione (1.2) e per quello
dell'avviso (1.3), che riusa la stessa selezione con una soglia diversa.

**Il perimetro dei permessi Graph non si tocca.** L'app ha `Calendars.ReadWrite`
sulle sole sei caselle sala. Se una story ha bisogno di più, si ferma e si passa
da IT security: non è una decisione di sprint.

## Rischi

| Rischio | Dove morde | Mitigazione |
|---|---|---|
| **Le occorrenze di serie sono il 70% delle righe e la suite non le copre** | 1.2, e ogni cosa che scrive su Graph | Nessuna, in questo sprint. È il rischio che accettiamo consapevolmente: la strategia di test in `architecture.md` lo dichiara. Chi implementa la 1.2 rilegga § E4 prima di scrivere |
| Il job libera una prenotazione con check-in | 1.2 | NFR2. Test dedicato, non opzionale |
| Il job gira su più repliche in cluster | 1.2 | `@SchedulerLock`. Deciso in pianificazione per non farlo scoprire a metà story |
| Fusi orari sulle fasce | 1.1 e 1.2 | Tutto in UTC nel database, conversione solo in presentazione. Scritto nella story |

## Cosa NON è in questo sprint

L'invio vero delle notifiche (1.3 può stubbare), il pannello del facility
manager, qualunque cosa dell'epic 2 — inclusa la 2.1, che pure sarebbe la cosa
più utile da fare.

---

**Nota del revisore umano (Giulio):** il piano iniziale metteva 1.3 in parallelo a
1.2 «perché indipendenti». Non lo sono: un avviso che annuncia una liberazione che
non esiste ancora è peggio del silenzio. Rimesse in sequenza. È scritto anche in
`epics.md`, ma nel piano di sprint era rientrato — segno che quando un vincolo
conta va ripetuto nel documento che verrà letto davvero.

**Seconda nota, aggiunta dopo la review della 1.2 (2026-09-02):** la riga in
grassetto nella tabella dei rischi c'era già, e l'ho letta io in pianificazione.
Non è bastata a evitare il bug: è bastata a farlo trovare, perché il reviewer che
legge il progetto trova anche questa. Un rischio scritto non è una mitigazione.
