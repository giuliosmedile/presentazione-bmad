# Oltre il vibe coding

Talk in italiano, 60 minuti: un approccio strutturato all'utilizzo dell'AI nello sviluppo
software. Pubblico aziendale, misto per competenza sull'AI.

## File

```
oltre-il-vibe-coding.html          la presentazione (CSS e JS inline, 41 slide)
oltre-il-vibe-coding.pdf           export statico — non versionato, si rigenera
CLAUDE.md                          come si lavora sul deck: convenzioni e trappole
copione-demo.md                    il discorso da dire durante la demo, slide per slide
demo-sale-riunioni/                il materiale su cui la demo e' costruita (README suo)
assets/prompt-vibe-coding.png      lo screenshot del prompt (slide 7)
assets/codice-senza-memoria.png    il codice con la costante misteriosa (slide 11)
assets/analyst_mary.jpg            ritratto di Mary, Business Analyst (slide 18)
assets/pm_john.jpg                 ritratto di John, Product Manager (slide 19)
assets/architect_winston.jpg       ritratto di Winston, System Architect (slide 20)
assets/developer_amelia.jpg        ritratto di Amelia, Senior SW Engineer (slide 21)
assets/ux_sally.jpg                ritratto di Sally, UX Designer (slide 22)
assets/all_bmad_personas.jpg       i cinque insieme (slide 23)
sviluppo-agentico.html             versione precedente, altro taglio — puoi eliminarla
Sviluppo Agentico (standalone).html  il deck da cui è stato preso lo stile
```

Tema **Organic**: fondo crema, accenti terracotta e oliva, titoli in Caprasimo, testo in
Figtree. Il **terracotta indica sempre l'intervento umano** (checkpoint, artefatti di
passaggio), l'**oliva indica gli agenti e i nodi del grafo**. La coerenza cromatica lavora
per te: non romperla assegnando quei colori ad altro.

## Struttura

| Capitolo | Slide | Durata |
|---|---|---|
| 00 · Apertura | 1-5 | ~7 min |
| 01 · Overview dei tool | 6 | ~3 min |
| 02 · Vibe coding e debito cognitivo | 7-14 | ~12 min |
| 03 · BMAD | 15-33 | ~21 min, di cui ~8 di demo |
| 04 · Trasferire conoscenza agli agenti | 34-38 | ~12 min |
| 05 · Chiusura | 39-41 | ~4 min |

Il capitolo 03 sta in 19 slide (15-33), di cui **nove sono la demo** (25-33). Non è più una
demo dal vivo: sono slide, quindi il tempo è prevedibile — circa un minuto l'una. Le dieci
slide che la precedono hanno tredici minuti, quindi vanno tenute veloci.

Il capitolo corrente compare in alto al centro, così il pubblico ha il senso di avanzamento
senza spendere slide separatrici. Se preferisci le separatrici vere, la slide 2 (agenda) è
già predisposta: duplicala e aggiungi `class="agenda-row current"` alla riga del blocco
attivo.

Le due slide del **Blocco note e IDE** (3 e 4) aprono il talk subito dopo l'agenda, prima
del dato di adozione: sono l'alzata di mano che scalda la sala, e piantano l'idea che lo
strumento riduce l'attrito senza sostituire il pensiero. La slide del setup personale è
stata tolta.

## Immagini

`assets/prompt-vibe-coding.png` è lo screenshot del prompt («make me an app that is going to
make me a million dollars»), ritagliato dall'originale: com'era, due terzi dell'inquadratura
erano nero vuoto. Sta nella slide 7 e vale come definizione visiva del vibe coding.

`assets/codice-senza-memoria.png` è il codice Java con il commento datato e la costante
`291048`. Sta nella slide 11 ed è la prova concreta del punto: il codice dice cosa fa, il
perché è rimasto in una riunione del 2018. Se hai un esempio equivalente preso dal **tuo**
codebase, sostituiscilo — funziona ancora meglio.

**Attenzione al percorso relativo**: aprendo il file con doppio clic (`file://`) l'immagine
carica senza problemi, ma se sposti l'HTML devi portarti dietro la cartella `assets/`.

## Il blocco sulla memoria (slide 11-13)

La **11** mostra codice reale con una costante che nessuno sa spiegare, e chi paga il conto:
in azienda il debito cognitivo non lo paghi tu, lo paga il PM in review, chi è di turno quando
il servizio è giù, il collega — o l'agente — nuovo. È il caso concreto.

La **12** taglia la scorciatoia che tutti hanno in testa: tenere tutto in una chat sola. Il
contesto si consuma, vive e muore nella finestra, e riassumere non salva perché nel comprimere
butti via proprio quello che serviva a chi arriva dopo.

La **13** riformula la domanda — non «AI sì o AI no» ma «AI con quale processo» — e mette
i tre assi su cui la memoria serve: fra una sessione e l'altra, fra le persone del team, e nel
tempo (versionata, correggibile, in review).

Chiude sul punto più scomodo: le decisioni prese in pausa caffè, per mail o in riunione non
esistono finché qualcuno non le scrive. Qui c'è il **requisito**; il **meccanismo** (la
memoria a grafo) arriva nel capitolo 04, quindi non anticiparlo.


## Il capitolo 03, slide per slide (15-33)

| Slide | Cosa fa |
|---|---|
| 15 | il flusso ufficiale di BMAD: dal PRD alla story rivista, un artefatto per freccia |
| 16 | dove BMAD parcheggia memoria e decisioni: artefatti, contesto, fatti, story |
| 17 | memoria condivisa col team: lavoro in parallelo su branch diversi, propagazione via commit e PR |
| 18-22 | un identikit a testa: Mary, John, Winston, Amelia, Sally |
| 23 | i cinque insieme, e la catena di artefatti che si passano |
| 24 | il diagramma del loop coi checkpoint umani |
| 25 | il caso della demo: un servizio del 2022, il 41%, il 70% di ricorrenze |
| 26-32 | **la demo**, sette momenti: menu · checkpoint · **contesto** · story · artefatti · review · verdetto |
| 33 | pitfall e strategie, la slide che il pubblico fotografa |

La **23** (i cinque insieme) apre la strada al diagramma del loop della **24**, che riassume
visivamente il giro. La 25 apre la demo con il caso su cui gira tutto il resto del capitolo.

Le slide su «come ci si parla con un agente» e sui quattro documenti del planning sono state
tolte: dicevano in astratto quello che la demo, appena dopo, mostra succedere davvero — vedi
**Cosa è stato tolto dal capitolo 03**.

Quel taglio però lasciava un buco fra la **27** e la story: la demo passava dal product brief
di Mary a `create-story` senza dire chi avesse scritto `epics.md` e `architecture.md`, né
cosa si fosse deciso di costruire. Costava caro, perché `architecture.md` è il file su cui
poggia tutto il finale (**31-32**).

Adesso il buco lo chiudono due cose. La **28** mostra il gesto che mancava, e lo fa per
intero: la correzione dell'umano viene scritta nel brief col motivo, e poi tirata fuori dal
brief e promossa a regola di progetto in `project-context.md`, in party mode con John e
Winston. La 27 resta libera di fare una cosa sola — rimandare la feature al problema che la
giustifica. Il resto del planning
(PRD, epiche, architettura) resta fuori scena, riassunto in tre righe grigie in cima al
terminale della **29** — venti secondi parlati, non un momento in più. Il copione ha
entrambe per esteso.

La **14** chiude il capitolo 02 con la tesi in una riga: BMAD non aggiunge intelligenza
all'AI, aggiunge struttura al processo. È la cerniera, non l'apertura del capitolo 03.

La **15** apre il capitolo 03 con il flusso ufficiale: PRD → architettura → epiche e story,
poi il riquadro tratteggiato che si ripete una volta per story (`create-story`, `dev-story`,
`review-story`) e si chiude sempre con una revisione umana. Ogni freccia è un artefatto
scritto, non una conversazione che si perde.

**Paige** (technical writer) non è più nel deck: né fra gli identikit 18-22, né altrove.
Se qualcuno chiede del sesto ruolo, la sua trascrizione sta in
`demo-sale-riunioni/chat/05-paige-tech-writer.md`. Stessa sorte per la mappa a cinque
colonne che apriva il capitolo e per la fila dei cinque ritratti: stanno nella storia git.

La **16** elenca i quattro posti dove una decisione si deposita:
gli artefatti nel repo, `project-context.md`, i `persistent_facts` nel `customize.toml`
dell'agente, e il diario che ogni story si lascia dietro. Sotto, la finestra con l'albero
del progetto — la stessa idea della slide 17 di `Sviluppo Agentico (standalone).html`, ma
rifatta con le classi `.code-window` di questo deck invece degli stili inline, e con
**l'albero vero di `BE_TRRN_API_Query_Engine`** al posto di quello generico: `_bmad-output/`
con contesto, planning e le 21 story, `.claude/skills/bmad-agent-*/customize.toml`, `docs/`.

Se cambi i percorsi, la colonna delle annotazioni va riallineata a mano: è padding di spazi
dentro un blocco `white-space: pre`, calcolato su 37 caratteri di prefisso.

I primi due posti dell'elenco tornano dal vivo nella **28**: la decisione entra in
`project-context.md`, e quel file sta nei `persistent_facts` di ogni agente. La 16 dice
*dove*, la 28 fa vedere *come*. Se sposti una delle due, l'altra perde l'aggancio.

Nota che la slide cita PRD e architettura prima che il pubblico sappia chi li scrive: se in
prova stona, valuta di spostarla dopo il blocco identikit (17-21), quando John e Winston sono
già stati presentati.

Gli identikit hanno tutti lo stesso schema: ritratto a sinistra, carta d'identità a destra
con **Cosa fa** (cinque righe), **Principi** e le voci di menu; sotto, una fascia
**Esempio** con quello che gli dici tu, i codici di menu che sceglie, e il file che ne esce.
La ripetizione è il punto: dalla seconda slide il pubblico sa già dove guardare.

**L'ordine è Mary, John, Winston, Amelia, Sally.** Segue il flusso fino ad Amelia
(analisi → pianificazione → solutioning → implementazione) e mette Sally in coda. Il tag
riporta solo il contatore, «Identikit — N di 5»: la fase BMAD è stata tolta proprio perché
con Sally in fondo la numerazione tornava indietro.

Le **skill non hanno più una slide dedicata**: erano una matrice da riempire che diceva
meno dei chip in fondo a ogni identikit. Se ti serve la distinzione a voce, è una frase:
l'agente è il *chi*, la skill è il *come*, e più agenti possono invocare la stessa skill.

### Da dove viene il contenuto

Non è inventato né preso dalla documentazione pubblica: i campi sono quelli dei tuoi
`customize.toml`, letti da

```
TIRRENO/BE_TRRN_API_Query_Engine/.claude/skills/bmad-agent-*/customize.toml
```

`name`, `title`, `icon`, `role`, `identity`, `communication_style`, `principles`, `menu` —
tradotti in italiano, non riassunti. Se aggiorni BMAD e quei file cambiano, le slide vanno
riallineate a mano. In `_bmad/custom/` non c'erano override per-agente al momento della
scrittura, quindi valgono i valori base.

**Da verificare prima del talk**: i badge «Esempio» degli identikit e la prima slide della
demo mostrano ora il comando reale di attivazione (`/bmad-agent-analyst` e simili). Solo
`bmad-agent-dev` (Amelia) è confermato dalle trascrizioni in `demo-sale-riunioni/`; gli altri
quattro (`bmad-agent-analyst`, `bmad-agent-pm`, `bmad-agent-architect`, `bmad-agent-ux-expert`)
seguono la convenzione standard di BMAD-METHOD ma non sono stati riverificati uno per uno
contro i `customize.toml` reali del progetto. Controllali prima di mostrarli in sala.

Le cinque righe di **Cosa fa** sono invece testo tuo, non del `customize.toml`.

Il campo `identity` non sta più sulla slide, ma **è finito nelle note del relatore**: Porter e
Minto per Mary, Cagan e Torres per John, Don Norman e Alan Cooper per Sally, Fowler e Vogels
per Winston, Kent Beck per Amelia. Sono nel file dell'agente, e se hai un minuto in più
valgono come battuta: la persona di un agente è configurazione versionata, non un prompt
improvvisato.

### Due cose da sapere

**Gli agenti sono sei, non cinque.** C'è anche **Paige, Technical Writer** (📚, fase di
analisi): documenta il progetto per gli umani e per gli agenti futuri. Adesso che il roster
non c'è più, **Paige non compare da nessuna parte**. Se vuoi citarla ti servono un ritratto
e una slide identikit in più (il template è quello, si aggiunge in due minuti); altrimenti
sappi che stai presentando cinque agenti su sei, e se qualcuno del pubblico conosce BMAD
te lo farà notare.

**Questa installazione non ha PO, SM e QA come agenti separati.** Quelle funzioni sono voci
di menu dentro gli agenti esistenti — `bmad-create-story` e `bmad-sprint-planning` stanno
sotto Amelia, `bmad-check-implementation-readiness` sotto John e Winston. Se hai visto
Sarah, Bob o Quinn in giro, sono di una versione diversa di BMAD: non citarli.

La **23** chiude il blocco degli identikit con la tesi: quello che i cinque si passano è un
documento, non una conversazione. La demo lo dimostra («cosa resta dopo», slide 30) ed è il
ponte verso il capitolo 04, dove il problema diventa che quei documenti non contengono la
conoscenza che sta fuori dal repo. Non anticiparlo qui.

## Cosa è stato tolto dal capitolo 01

- **«Una modifica, oppure quindici»**: il confronto fra una richiesta in chat e l'albero di
  quindici azioni di un agente. La differenza di densità funzionava, ma il capitolo 02 dice
  già la stessa cosa con più forza, e il capitolo 01 arrivava lungo. Con lei se ne sono
  andate le classi `.act-single` e `.act-tree`, che non usava nessun altro.

## Cosa è stato tolto dal capitolo 03

- **il roster** dei sei ruoli: ridondante, gli identikit presentano i nomi meglio
- **la slide sulle skill** con la matrice da riempire
- **le tre slide di walkthrough** (il caso, il loop in azione, il risultato): erano tutte
  segnaposto e facevano lo stesso lavoro della Demo dal vivo
- **«Punto per punto»**, il confronto BMAD vs vibe coding
- **«come si parla con un agente»** (menu, codici, sessione d'esempio con Mary): la stessa
  cosa la demo la mostra due slide dopo, sul vero — non serve un'anteprima astratta
- **«dalla richiesta agli artefatti»** (quattro conversazioni, quattro documenti,
  «la conversazione è usa e getta»): stessa tesi già detta alla 12 e alla 16, e ridimostrata
  dal vivo dentro la demo («cosa resta dopo», slide 30). Tre ripetizioni della stessa frase
  prima del pay-off erano troppe

Se ti servisse recuperarne una, stanno nella storia git (`git log -- oltre-il-vibe-coding.html`).

## Una correzione fatta al deck

Dalla slide che apriva il capitolo BMAD in poi, ogni slide mostrava il tag e il titolo della
slide **successiva**: corpo, note del relatore e commenti HTML erano giusti, solo le
intestazioni erano scivolate su di una posizione. Probabile effetto collaterale della
rimozione della slide sul setup personale. Ora sono riallineate e i due residui che restavano
sulla 14 sono rientrati: il titolo è «Il framework in una slide» e il corpo porta la
formulazione buona, «BMAD non aggiunge intelligenza all'AI».

La lezione resta valida per il futuro: **i titoli si controllano dopo ogni riordino**, e la
verifica va fatta sul titolo dentro `script.slide-notes`, mai sui commenti HTML — quelli
stanno in coda al blocco precedente (vedi `CLAUDE.md`).

## La demo (slide 25-33)

Non è più una demo dal vivo: sono **otto slide** costruite sul materiale di
`demo-sale-riunioni/`. Niente da far partire in sala, tempo prevedibile, e i momenti
salienti sono già selezionati.

| Slide | Momento | Cosa deve arrivare |
|---|---|---|
| 25 | il caso | **brownfield**: il servizio c'è dal 2022, arriva una feature. Il 41% e il 70% |
| 26 | attivazione e menu | tre colonne, e la terza è **lo skill che parte**: `CB` → `bmad-product-brief` |
| 27 | la feature rimandata al problema | «quella che mi hai dato non è un problema, è già una soluzione». Poi il bivio, e il 70% che nessuno cercava |
| 28 | la decisione sale nel contesto | la leva punitiva tolta col motivo scritto, poi party mode: la regola esce dal brief ed entra in `project-context.md` |
| 29 | la story è un contratto | AC numerati, task che citano l'AC — e il vincolo E4 piantato nelle Dev Notes |
| 30 | cosa resta dopo | `DS`, il **riuso del client** seminato di sfuggita, la definition of done, lo stato in `sprint-status.yaml` |
| 31 | tre reviewer, tre contesti | Blind / Edge Case / Acceptance Auditor. Uno approva, l'altro trova la serie cancellata |
| 32 | corretto per la story | il triage in quattro bucket, il diff, «come ti è sfuggito?» e il decision-needed che richiama la 28 |
| 33 | pitfall e strategie | sei errori e sei contromisure, in parallelo |

Il discorso da dire, slide per slide, sta in **[`copione-demo.md`](copione-demo.md)**:
parole vere, non appunti. Serve soprattutto per le 31-32, dove il ritmo conta.

Le otto slide sono **in ordine di tempo** e percorrono il loop della 24 invece di
raccontarlo: parli a un agente, produci una story, la implementi, la fai rivedere. La
versione precedente spendeva quattro slide sulla fase di analisi e zero su `create-story` e
`dev-story`, e saltava dalla chat con Mary alla code review senza mostrare cosa c'era in
mezzo.

**Se devi tagliare**: tieni 29, 31, 32 e 33. La 29 sembra la più sacrificabile e non lo è:
è quella che pianta il vincolo sulle occorrenze, e senza di lei la 31 non ha niente da
raccogliere. Le 26, 27, 28 e 30 si raccontano a voce in un minuto — la 28 è la prima a
cadere, e quello che perdi è tre cose: la prova nei `persistent_facts`, il ponte verso il
capitolo 04, e il richiamo del decision-needed alla 32.

**Il caso è brownfield ed è una scelta.** La versione precedente costruiva da zero un
sistema di prenotazione, e in sala faceva partire la domanda sbagliata («ma perché non usate
Outlook?»). Adesso le sale si prenotano su Outlook come sempre, il servizio esiste dal 2022 e
quello che si aggiunge è una feature — cioè la situazione in cui è il pubblico lunedì
mattina. Ne guadagna anche il capitolo 04: un `project-context.md` solo basta molto meno
quando il codice ha quattro anni e nessun autore reperibile.

Le trascrizioni usano il componente `.term`: finestra scura, `white-space: pre`, e la
classe **`.term-hl`** per la riga su cui deve cadere l'occhio (terracotta) o `.term-hl.olive`
per le conclusioni dell'agente. Sotto ogni terminale c'è una fascia `.term-take` con
l'etichetta — *Strategia*, *Capacità*, *Pitfall*, *La tesi* — che dice la morale in una riga.

Il triage della review usa le classi **`.sev-decision`**, `.sev-patch`, `.sev-defer`,
`.sev-dismiss`: sono i quattro bucket veri di `bmad-code-review`, e `decision-needed` prende
il terracotta perché è letteralmente il finding che richiede te.

### Cosa è preso dall'installazione vera

Tutte le meccaniche vengono da BMAD 6.6.0 come installato in
`TIRRENO/BE_TRRN_API_Query_Engine`. Se qualcuno in sala ha BMAD e controlla, tornano:

- **il menu a tre colonne**, codice / descrizione / skill (o prompt), e i codici stessi
  (`BP MR DR TR CB WB DP` per Mary, `DS QD QA CR SP CS ER` per Amelia) dai `customize.toml`
- **i tre reviewer** di `bmad-code-review` e il contesto asimmetrico che ricevono:
  Blind Hunter solo il diff, Edge Case Hunter diff + progetto, Acceptance Auditor
  diff + spec + context doc
- **il triage in quattro bucket** e la sintassi dei finding scritti nella story
  (`- [ ] [Review][Patch] <titolo> [<file>:<riga>]`)
- **gli stati della story**: `backlog → ready-for-dev → in-progress → review → done`,
  e `sprint-status.yaml` che li tiene
- **la voce di Amelia**: il suo `communication_style` dice «ultra-succinct, speaks in file
  paths and AC IDs». Se la fai parlare in prosa, il pubblico che conosce lo strumento se
  ne accorge

**Onestà**: il caso è ricostruito su un progetto di esempio, non è il log di una sessione
reale. Il tag della 25 dice «caso di esempio» apposta. La distinzione da fare in sala è
questa: **il caso è finto, le meccaniche sono vere**. Detta così regge; detta a metà no.


## Il capitolo 04, slide per slide (34-38)

| Slide | Cosa fa |
|---|---|
| 34 | il limite che resta: lo stesso loop, col buco del contesto al centro |
| 35 | l'idea: file di contesto piatto contro grafo navigabile — e le fonti che non stanno nel repo |
| 36 | gli strumenti: Graphify, llm-wiki, le alternative, più il consiglio |
| 37 | Graphify in pratica: cosa gli dai in pasto e cosa ti risponde |
| 38 | il buco riempito: chi legge dal grafo e chi ci scrive |

La **35** dice che call, mail e note di corridoio diventano nodi come tutto il resto; la
**37** lo mostra: quattro `ingest`, una domanda vera («posso cambiare il separatore
decimale?»), e una risposta che cita quattro fonti di cui tre fuori dal repo. È il pay-off
del punto piantato alla 13 — le decisioni prese in pausa caffè non esistono finché qualcuno
non le scrive. Qui qualcuno le scrive in due minuti, e restano interrogabili.

**Il terminale della 37 è ricostruito**, come la demo del capitolo 03. Se qualcuno chiede,
dillo.

## La chiusura (slide 39-41)

| Slide | Cosa fa |
|---|---|
| 39 | il conto: cosa guadagni e cosa paghi, due colonne affiancate |
| 40 | il primo passo, piccolo: cosa fare lunedì mattina |
| 41 | contatti e domande |

La **39** ha sostituito una slide che elencava quattro tesi già dette («Quattro cose»).
Al suo posto un bilancio a due colonne, con lo stesso componente `.pf-grid` della **32**:
oliva a sinistra, terracotta a destra.

| Cosa guadagni | Cosa paghi |
|---|---|
| il processo rende l'AI più deterministica: l'esito dipende dagli artefatti, non dal prompt di quel giorno | il metodo si paga in token: ogni giro rilegge il contesto |
| l'approccio strutturato allevia il debito cognitivo: le decisioni le scrivi mentre le prendi | sei schiavo del framework: se una cosa non è nel processo non esiste, e piegarlo costa |
| gli artefatti restano: sopravvivono alla sessione e alla persona | c'è un overhead sui task piccoli: per un fix di due righe la cerimonia costa più del fix |

I due «guadagni» in cima chiudono le promesse dei capitoli 01 e 02; il terzo è il pay-off
del 04. I tre costi sono veri e vanno detti: **una chiusura che vende solo il lato buono
perde la sala che ha già provato e si è scottata**. La morale del parlato è che il conto
torna quando il lavoro è abbastanza grande da doverlo ricordare — ed è la rampa per la
**40**, che chiede un passo piccolo.

Da lasciare proiettata durante il Q&A.

## Versioning

Il progetto è un repo git. **Niente più file `.bak`**: la storia dei dieci backup fatti a
mano è stata ricostruita in commit datati, quindi non è andato perso niente — ogni backup
corrisponde byte per byte a un commit.

```bash
git log --oneline
```

```bash
git diff HEAD~1 -- oltre-il-vibe-coding.html
```

Per tornare a una versione precedente del deck senza perdere quella attuale:

```bash
git show <commit>:oltre-il-vibe-coding.html > prova.html
```

Il `.gitignore` esclude `*.bak*` (così un backup manuale non finisce per sbaglio nel repo)
e `*.pdf`, perché l'export pesa 16 MB ed è rigenerabile dall'HTML in un comando.

Il repo è solo locale: non c'è ancora un remote. Se lo vuoi su un server interno, basta
`git remote add origin <url>` e `git push -u origin main`.


## Navigazione

| Comando | Effetto |
|---|---|
| `←` `→` / `Spazio` / `PagSu` `PagGiù` | slide precedente / successiva |
| `Home` / `Fine` | prima / ultima slide |
| rotella, swipe | navigazione |
| `E` | attiva/disattiva la modalità modifica |
| `Ctrl+S` | esporta un HTML con le modifiche applicate |

## Note per il relatore

Ogni slide ha il testo da dire e i punti chiave. Apri DevTools con `F12`, stacca la finestra
su un secondo schermo: le note compaiono nella console a ogni cambio slide.

## Da riempire prima del talk

I segnaposto sono tra parentesi quadre `[così]` o in riquadri tratteggiati color pesca.

- **slide 1** — nome, ruolo, team, data
- **slide 18-22** — i cinque identikit: **niente da riempire**. Semmai da tagliare: se in
  prova sfori, togli una o due delle cinque righe di **Cosa fa**, sono le più comprimibili.
  **Verifica i comandi** `/bmad-agent-*` nei badge «Esempio»: solo `bmad-agent-dev` è
  confermato dalle trascrizioni, gli altri quattro sono la convenzione standard
- **slide 36** — Graphify, llm-wiki e le alternative: resta da confermare lo **stato di
  maturità** dei primi due, e il `[X]` del consiglio finale
- **slide 37** — il caso di Graphify in pratica è **ricostruito**: se hai una sessione vera,
  sostituisci le righe del terminale
- **slide 40** — link e QR code
- **slide 41** — nome e contatto interno

## La slide da curare

La **24** (il loop BMAD) è quella che regge il talk. Compare tre volte con lo stesso disegno
e centro diverso: completa (24), con il buco del contesto mancante (34), col grafo che lo
riempie (38). Il richiamo funziona perché il resto dell'immagine è identico — se ne modifichi
una, modificale tutte e tre.

Se puoi, costruiscila in build progressive: prima il ciclo, poi i quattro checkpoint che si
accendono. È il momento in cui vuoi che tutti guardino lo schermo.

## Modificare i testi

1. **Nel browser** — premi `E` (o passa il mouse in alto a sinistra e clicca la matita),
   clicca un testo e scrivi. Salvataggio automatico in `localStorage`. `Ctrl+S` scarica una
   copia del file con le modifiche dentro: sostituisci l'originale per renderle definitive.
2. **Nel file** — ogni slide è commentata (`<!-- ---- 9 · PERCHÉ FUNZIONA ---- -->`). Se
   cambi il testo visibile, aggiorna anche il blocco `<script class="slide-notes">` in fondo
   alla slide.

Dopo un inserimento o una cancellazione, rinumera tutti i `data-slide` in sequenza da `0` e
aggiorna `data-chapter`. Solo la prima slide ha `class="slide active"`.

## Aggiungere gli screenshot

Metti i file in `assets/` e inserisci una slide immagine:

```html
<div class="slide" data-slide="N" data-chapter="3">
  <p class="slide-tag anim-1">TAG</p>
  <h2 class="anim-2">Titolo</h2>
  <div class="image-frame anim-3 image-screenshot">
    <img src="assets/nome-file.png" alt="descrizione" class="slide-image">
  </div>
  <script type="application/json" class="slide-notes">
  {"title":"Titolo","script":"Cosa dico su questa slide.","notes":["punto 1","punto 2"]}
  </script>
</div>
```

Ingrandisci sempre i frammenti che contano: un terminale intero proiettato non lo legge
nessuno dalla terza fila in poi.

## Esportare in PDF

```bash
bash "C:/Users/giuli/.claude/plugins/cache/html-slides/html-slides/0.9.4/scripts/export-pdf.sh" oltre-il-vibe-coding.html
```

Richiede Node; Playwright si installa da solo al primo lancio. Le animazioni non vengono
preservate.

## Personalizzare i colori

Tutto passa dalle variabili in `:root`: `--color-bg`, `--color-surface`, `--color-text`,
`--color-accent` (terracotta), `--color-accent-2` (oliva), più le tre rampe tonali da 100 a
900. Cambiare quelle ricolora l'intera presentazione.

Regola pratica su carta chiara: l'accento pieno regge solo nel testo grande. Per le etichette
piccole scendi a `--color-accent-700`, altrimenti in proiezione spariscono.
