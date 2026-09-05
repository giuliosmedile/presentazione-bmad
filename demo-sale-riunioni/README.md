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

**Il problema**: le sale si prenotano su Outlook e nessuno disdice. Il calendario
dice pieno, le sale sono vuote. Il 41% delle sale risultate occupate erano vuote
al giro fisico di controllo.

**L'ipotesi**: se liberare diventa automatico invece che volontario, la capacità
cresce senza costruire niente. Check-in in sala entro 10 minuti, altrimenti la
prenotazione decade.

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
│   │       ├── sprint-plan-epic-1.md     Amelia SP
│   │       ├── 1-1-…-di-una-sala.md      done, con Dev Agent Record completo
│   │       ├── 1-2-check-in-…md          in progress, si ferma su una domanda
│   │       └── review-1.1.diff           i tre rilievi, prima/dopo
│   ├── docs/glossario-dominio.md         Paige
│   └── src/                              tre file veri da aprire durante la review
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

- icona in testa a ogni messaggio dell'agente
- saluto con `user_name` in `communication_language`
- promemoria su `bmad-help`
- menu numerato con i **codici veri** presi dai `customize.toml`
  (`BP MR DR TR CB WB DP` per Mary, `CP VP EP CE IR CC` per John, `CU` per Sally,
  `CA IR` per Winston, `DS QD QA CR SP CS ER` per Amelia, `DP WD MG VD EC` per Paige)

Le voci di menu non sono inventate. Se qualcuno in sala ha BMAD installato e
controlla, tornano.

## Se apri i file dal vivo — 8 minuti

Non serve per il talk (lì bastano le slide 25-32). È il percorso da fare se qualcuno
chiede di vedere il materiale vero dopo, o se un giorno vuoi rifare la demo navigando.

| Min | Cosa | File |
|---|---|---|
| 0-1 | «Questo è il progetto». Problema e il 41% | `product-brief.md`, sezione L'evidenza |
| 1-3 | Come si parla a un agente: menu e codici | `chat/01-mary-analyst.md`, fino a `MR` |
| 3-4 | Il checkpoint umano | `chat/01`, la classifica dei reparti tolta |
| 4-5 | Cosa resta dopo: i file, non la chat | l'albero di `_bmad-output/` in un terminale |
| 5-8 | **Il pezzo forte**: la code review | `chat/09` + `review-1.1.diff` |

Se hai **3 minuti soli**, fai solo la code review. È l'unica parte che mostra il
processo mentre intercetta qualcosa, che è la tesi del talk.

## Il momento migliore

Il rilievo `[media]` della review 1.1: la query usava `stato <> 'disdetta'` invece
di `stato = 'attiva'`. Una prenotazione in `no_show` avrebbe continuato a occupare
la sala — cioè la funzionalità centrale del progetto non avrebbe funzionato.

Il punto da far arrivare: quel codice era **corretto rispetto ai criteri di
accettazione della story** e sbagliato rispetto al progetto. Nessun test lo
avrebbe preso, perché lo stato `no_show` arriva nella story dopo. L'ha preso la
rilettura contro l'architettura, fatta in una sessione separata.

E la domanda che chiude: senza la review, il bug sarebbe emerso nella 1.2 come
«la liberazione non funziona», e l'avremmo cercato nel job — che era corretto.

## Cose da non dire

- **Non dire che è una sessione reale.** Se qualcuno chiede, è ricostruita su un
  progetto di esempio. La credibilità la porta la coerenza dei documenti, non
  fingere che sia un log.
- **Paige non ha una slide** nel deck. Se usi `chat/05`, presentala a voce come
  «c'è anche una sesta agente» oppure aggiungile l'identikit.
- La story 1.2 si ferma su una domanda aperta (il job in cluster, che partirebbe
  su ogni replica). È voluto: mostra un agente che si ferma invece di indovinare.
  Se non hai tempo di spiegarla, non aprirla.

## Se un giorno vuoi la demo vera

Serve installare BMAD in `progetto/` e rifare il giro davvero. Gli artefatti qui
diventano il piano B da mostrare se in sala non parte. La struttura delle cartelle
è già quella giusta.
