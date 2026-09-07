# Demo — SaleRiunioni

Materiale su cui sono costruite le **slide 25-32** di `oltre-il-vibe-coding.html`.
In sala non si naviga niente: la demo è fatta di slide, e il discorso da dire sta in
[`copione-demo.md`](../copione-demo.md). Nessuna dipendenza da BMAD, nessun rischio che
qualcosa non parta.

Questi file servono a due cose: come fonte da cui le slide sono state estratte, e come
roba da aprire davvero se qualcuno dopo il talk vuole vedere il materiale intero.

## Il progetto finto

Prenotazione delle sale riunioni interne. Scelto perché il dominio non va spiegato:
in sala lo capiscono tutti prima che tu finisca la frase.

**Non è greenfield, ed è il punto.** Il servizio esiste dal marzo 2022: legge i
calendari delle sei sale da Microsoft Graph, alimenta i display fuori dalle porte
e la pagina «sala libera adesso». **Le sale si prenotano su Outlook** e
continueranno a prenotarsi lì. Due sviluppatori, nessuno degli autori originali
ancora in azienda.

**BMAD entra nel 2026**, quattro anni dopo il primo commit. Gli artefatti di
planning non sono stati scritti prima del codice: sono stati ricostruiti leggendo
il codice, con `DP` (`bmad-document-project`). È il motivo per cui
`architecture.md` ha due parti, e la Parte I dichiara di essere una ricostruzione.

**Il problema**: nessuno disdice. Il calendario dice pieno, le sale sono vuote.
Il 41% delle sale risultate occupate erano vuote al giro fisico di controllo.

**La feature richiesta**: liberazione automatica. Nessun check-in entro 10 minuti,
la sala torna libera — anche su Outlook, e il display lo scrive.

**Il fatto di progetto che regge il finale**: il display è un cartello appeso in
corridoio. Dal 2022 esiste `TestoDisplay`, che per le riunioni marcate private
scrive «Riunione riservata» invece del titolo, e quella classe esiste perché una
volta era già andata male.

Tutti i numeri sono inventati ma coerenti fra loro: se qualcuno in sala li
incrocia fra due documenti, tornano.

## Cosa c'è dentro

```
demo-sale-riunioni/
├── progetto/                     il repo finto, navigabile
│   ├── _bmad/bmm/config.yaml     lingua italiano, artefatti in _bmad-output
│   ├── _bmad-output/
│   │   ├── project-context.md            ← lo carica ogni agente all'attivazione
│   │   ├── planning-artifacts/
│   │   │   ├── product-brief.md          Mary
│   │   │   ├── prd.md                    John
│   │   │   ├── epics.md                  John
│   │   │   ├── ux-spec.md                Sally
│   │   │   ├── architecture.md           Winston
│   │   │   └── implementation-readiness-report.md   Winston
│   │   └── implementation-artifacts/
│   │       ├── sprint-status.yaml        ← lo stato vero delle story, lo muovono i workflow
│   │       ├── sprint-plan-epic-1.md     Amelia SP
│   │       ├── deferred-work.md          dove finisce il bucket `defer` della review
│   │       ├── 1-1-check-in-dal-display.md   done
│   │       ├── 1-2-liberazione-…-sala.md     done, con Dev Agent Record e Review Findings
│   │       ├── 1-3-avviso-…md                in progress, si ferma su una domanda
│   │       └── review-1.2.diff               il diff prima/dopo, e le due righe sul display
│   ├── docs/glossario-dominio.md         Paige
│   └── src/                              quattro file veri da aprire durante la review
└── chat/
    ├── 01-mary-analyst.md
    ├── 02-john-pm.md
    ├── 03-sally-ux.md
    ├── 04-winston-architect.md
    ├── 05-paige-tech-writer.md
    ├── 06-amelia-sprint-planning.md      SP
    ├── 07-amelia-create-story.md         CS
    ├── 08-amelia-dev-story.md            DS
    └── 09-amelia-code-review.md          CR
```

Ogni chat finisce con un blocco **«Cosa mostrare»**: sono le due o tre cose che
valgono il tempo, se devi tagliare.

## Fedeltà

Le trascrizioni riproducono l'attivazione vera di BMAD 6.6.0 come installato in
`TIRRENO/BE_TRRN_API_Query_Engine`:

**Attivazione e menu**

- icona in testa a ogni messaggio dell'agente, per tutta la sessione
- saluto con `user_name` in `communication_language`
- promemoria su `bmad-help`
- menu a **tre colonne**: codice, descrizione, e **lo skill che parte**. È la terza
  che spiega il meccanismo — `CB` non è una preferenza di conversazione, è
  `bmad-product-brief` che si attiva col suo contesto
- codici e skill presi dai `customize.toml`
  (`BP MR DR TR CB WB DP` per Mary, `CP VP EP CE IR CC` per John, `CU` per Sally,
  `CA IR` per Winston, `DS QD QA CR SP CS ER` per Amelia, `DP WD MG VD EC` per Paige)
- le quattro voci di Paige `WD MG VD EC` non chiamano uno skill ma eseguono un
  prompt: nel menu vero la terza colonna mostra quello, ed è riprodotto così
- l'agente si ferma e aspetta: numero, codice o descrizione

**Code review (`chat/09`)**

Riproduce `bmad-code-review` come funziona davvero, e vale la pena saperlo perché
è il pezzo su cui gira il talk:

- **tre reviewer in parallelo con contesto asimmetrico per costruzione.**
  Blind Hunter riceve solo il diff; Edge Case Hunter il diff più accesso in
  lettura al progetto; Acceptance Auditor il diff, la spec e i context doc
- **deduplica** fra layer: il finding grosso è `blind+edge`, che è esattamente la
  regola di merge dello step di triage
- **triage in quattro bucket** — `decision-needed`, `patch`, `defer`, `dismiss` —
  non in severità alta/media/bassa, che in BMAD non esistono
- il `decision-needed` **si ferma e aspetta l'umano**: è l'unico che il tool non
  tocca da solo, e non è un caso
- findings scritti nella story con la sintassi vera
  (`- [ ] [Review][Patch] <titolo> [<file>:<riga>]`), i `defer` anche in
  `deferred-work.md`
- riga di riepilogo, menu numerato per le patch, aggiornamento dello status e
  sincronizzazione di `sprint-status.yaml`

**Stati e artefatti**

- gli stati della story sono quelli veri: `backlog → ready-for-dev → in-progress
  → review → done`, e li muovono i workflow, non l'agente che dice «fatto»
- `dev-story` chiude su una definition of done che è una lista da passare
- il Dev Agent Record, la File List e il Change Log stanno **dentro il file della
  story**

Niente di tutto questo è inventato. Se qualcuno in sala ha BMAD installato e
controlla, torna.

## Se apri i file dal vivo — 8 minuti

Non serve per il talk (lì bastano le slide 25-32). È il percorso da fare se qualcuno
chiede di vedere il materiale vero dopo, o se un giorno vuoi rifare la demo navigando.

| Min | Cosa | File |
|---|---|---|
| 0-1 | «Questo è il progetto, e ha quattro anni». Il 41% | `product-brief.md`, sezione L'evidenza |
| 1-2 | Come si parla a un agente: il menu a tre colonne | `chat/01-mary-analyst.md`, fino a `CB` |
| 2-3 | Il checkpoint umano, e la regola che sale nel contesto | `chat/01` la decisione di scrivere fuori, `chat/02` il party mode |
| 3-4 | Il vincolo che nessuno legge | `architecture.md` § E3 e `TestoDisplay.java` |
| 4-5 | La story è un contratto, e cosa resta dopo | `1-2-liberazione-…md`: AC, Task, Dev Agent Record |
| 5-8 | **Il pezzo forte**: la review a tre layer | `chat/09` + la sezione Review Findings della 1.2 |

Se hai **3 minuti soli**, fai solo la code review. È l'unica parte che mostra il
processo mentre intercetta qualcosa, che è la tesi del talk.

## Il momento migliore

Il `patch` della review 1.2. Il dev compone il testo del display dentro il
service: `"Libera — nessun check-in per " + prenotazione.titolo()`.

Cosa cambia sullo schermo in corridoio, alle 9:40:

```
prima    Libera — nessun check-in per «Colloquio di uscita — Marco Rossi»
dopo     Libera — nessun check-in per «Riunione riservata»
```

Non serve spiegare niente a nessuno: due righe e il danno si vede.

Ma il momento non è il bug: è **chi lo trova e chi no**. Nella stessa review
l'Acceptance Auditor scrive «spec soddisfatta» e l'Edge Case Hunter apre il
finding. Hanno ragione tutti e due, e la contraddizione è la tesi del talk messa
per iscritto dallo strumento invece che da te.

Il punto da far arrivare: quel codice era **corretto rispetto ai criteri di
accettazione della story** e sbagliato rispetto al progetto. AC3 chiede che il
display dica perché la sala è libera, e quel testo lo dice. I test erano verdi e
lo erano onestamente: nella suite non esiste una fixture con `privato = true`, e
su una riunione normale il metodo giusto e quello sbagliato restituiscono la
stessa identica stringa.

E il pezzo che vale il doppio: **il dev non ha scritto codice sciatto.** Ha
scritto due righe invece di chiamare una funzione, perché aggiungere un metodo a
una classe di formattazione per una stringa sola sembrava sproporzionato. È un
ragionamento che fa chiunque. Solo che quella non era una classe di formattazione:
era il posto dove stava un pezzo di conoscenza, e il nome non lo diceva.

E la domanda che chiude: senza la review, il difetto esce il primo giorno in cui
una riunione privata non fa check-in — statisticamente entro la settimana. E non
lo scopriamo noi: lo scopre qualcuno che passa in corridoio. Nel 2022 era andata
esattamente così.

## Cose da non dire

- **Non dire che è una sessione reale.** Se qualcuno chiede, è ricostruita su un
  progetto di esempio. La credibilità la porta la coerenza dei documenti, non
  fingere che sia un log.
- **Paige non ha una slide** nel deck. Se usi `chat/05`, presentala a voce come
  «c'è anche una sesta agente» oppure aggiungile l'identikit.
- La story 1.3 si ferma su una domanda aperta (cosa scrive l'avviso, visto che una
  mail può essere inoltrata e il titolo di una riunione privata non dovrebbe
  uscire da nessuna parte). È voluto: mostra un agente che si ferma invece di
  indovinare. Se non hai tempo di spiegarla, non aprirla.

## Se un giorno vuoi la demo vera

Serve installare BMAD in `progetto/` e rifare il giro davvero. Gli artefatti qui
diventano il piano B da mostrare se in sala non parte. La struttura delle cartelle
è già quella giusta.
