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
assets/prompt-vibe-coding.png      lo screenshot del prompt (slide 8)
assets/codice-senza-memoria.png    il codice con la costante misteriosa (slide 12)
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
| 01 · Overview dei tool | 6-7 | ~5 min |
| 02 · Vibe coding e debito cognitivo | 8-14 | ~12 min |
| 03 · BMAD | 15-33 | ~21 min, di cui ~8 di demo |
| 04 · Trasferire conoscenza agli agenti | 34-38 | ~12 min |
| 05 · Chiusura | 39-41 | ~4 min |

Il capitolo 03 sta in 19 slide, di cui **otto sono la demo** (26-33). Non è più una demo dal
vivo: sono slide, quindi il tempo è prevedibile — circa un minuto l'una. Le undici slide che
la precedono devono stare in undici minuti, quindi vanno tenute veloci.

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
erano nero vuoto. Sta nella slide 8 e vale come definizione visiva del vibe coding.

`assets/codice-senza-memoria.png` è il codice Java con il commento datato e la costante
`291048`. Sta nella slide 12 ed è la prova concreta del punto: il codice dice cosa fa, il
perché è rimasto in una riunione del 2018. Se hai un esempio equivalente preso dal **tuo**
codebase, sostituiscilo — funziona ancora meglio.

**Attenzione al percorso relativo**: aprendo il file con doppio clic (`file://`) l'immagine
carica senza problemi, ma se sposti l'HTML devi portarti dietro la cartella `assets/`.

## La coppia sulla memoria (slide 12-13)

La **12** mostra codice reale con una costante che nessuno sa spiegare: è il caso concreto.
La **13** ne trae la tesi — un agente di sviluppo è un collega nuovo, e come a un collega
nuovo gli devi fare onboarding. La memoria gli serve su tre assi: fra una sessione e l'altra,
fra le persone del team, e nel tempo (versionata, correggibile, in review).

Chiude sul punto più scomodo: le decisioni prese in pausa caffè, per mail o in riunione non
esistono finché qualcuno non le scrive. Qui c'è il **requisito**; il **meccanismo** (la
memoria a grafo) arriva nel capitolo 04, quindi non anticiparlo.


## Il capitolo 03, slide per slide (15-26)

| Slide | Cosa fa |
|---|---|
| 15 | la panoramica su BMAD: cinque fasi, sei ruoli, e gli artefatti veri che ne escono |
| 16 | dove BMAD parcheggia memoria e decisioni: artefatti, contesto, fatti, story |
| 17 | memoria condivisa col team: lavoro in parallelo su branch diversi, propagazione via commit e PR |
| 18-22 | un identikit a testa: Mary, John, Winston, Amelia, Sally |
| 23 | i cinque insieme, e la catena di artefatti che si passano |
| 24 | il loop di sviluppo: una story per giro |
| 25 | il diagramma del loop coi checkpoint umani |
| 26 | il caso della demo: sei sale, il 41%, l'ipotesi |
| 27-32 | **la demo**, sei momenti: menu · premessa · checkpoint · artefatti · review · perché |
| 33 | pitfall e strategie, la slide che il pubblico fotografa |

La **24** (il loop di sviluppo) apre la strada al diagramma della **25**, che la riassume
visivamente. La 26 chiude il capitolo dal vivo, con il caso della demo.

Le slide su «come ci si parla con un agente» e sui quattro documenti del planning sono state
tolte: dicevano in astratto quello che la demo, appena dopo, mostra succedere davvero — vedi
**Cosa è stato tolto dal capitolo 03**.

La **15** apre il capitolo con la mappa: BMAD non è un agente che fa tutto, è una squadra
che copre il ciclo intero. Cinque colonne — analisi, prodotto e UX, architettura,
documentazione, codice e verifica — con l'agente e il file che ogni fase produce.

I nomi dei file sono quelli veri di `demo-sale-riunioni/progetto/_bmad-output/`
(`product-brief.md`, `prd.md`, `epics.md`, `ux-spec.md`, `architecture.md`,
`docs/glossario-dominio.md`), quindi la slide e la demo si confermano a vicenda.

La colonna di **Paige** (technical writer) è quella che porta il punto: il framework copre
anche la parte non tecnica, e la riga di chiusura fa notare che quasi tutto quello che esce
sono documenti. Paige **non ha un identikit** fra le 18-22: se qualcuno chiede, è il sesto
ruolo, e la sua trascrizione sta in `demo-sale-riunioni/chat/05-paige-tech-writer.md`.

La versione precedente di questa slide («non sono tool, sono persone a tua disposizione», con
la fila dei cinque ritratti) sta nella storia git: la tesi regge ancora, ma come premessa agli
identikit diceva meno della mappa.

La **16** elenca i quattro posti dove una decisione si deposita:
gli artefatti nel repo, `project-context.md`, i `persistent_facts` nel `customize.toml`
dell'agente, e il diario che ogni story si lascia dietro. Sotto, la finestra con l'albero
del progetto — la stessa idea della slide 17 di `Sviluppo Agentico (standalone).html`, ma
rifatta con le classi `.code-window` di questo deck invece degli stili inline, e con
**l'albero vero di `BE_TRRN_API_Query_Engine`** al posto di quello generico: `_bmad-output/`
con contesto, planning e le 21 story, `.claude/skills/bmad-agent-*/customize.toml`, `docs/`.

Se cambi i percorsi, la colonna delle annotazioni va riallineata a mano: è padding di spazi
dentro un blocco `white-space: pre`, calcolato su 37 caratteri di prefisso.

Nota che la slide cita PRD e architettura prima che il pubblico sappia chi li scrive: se in
prova stona, valuta di spostarla dopo il blocco identikit (18-22), quando John e Winston sono
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
rimozione della slide sul setup personale. Ora sono riallineate, e la **15** ha di nuovo un
titolo suo («BMAD — struttura al processo»).

Due residui da guardare, entrambi sulla **15**:

- il `cb-body` dice ancora «Un prompt, tutto insieme», che suona come il contro-esempio
  della **16** («Ruoli, non prompt giganti»). Potrebbe essere scivolato anche quello
- il titolo l'ho scritto io. Se preferisci la tua formulazione — «BMAD non aggiunge
  intelligenza all'AI» — cambialo: è la stessa idea detta meglio

## La demo (slide 26-33)

Non è più una demo dal vivo: sono **otto slide** costruite sul materiale di
`demo-sale-riunioni/`. Niente da far partire in sala, tempo prevedibile, e i momenti
salienti sono già selezionati.

| Slide | Momento | Cosa deve arrivare |
|---|---|---|
| 26 | il caso | sei sale, il 41%, l'ipotesi falsificabile |
| 27 | il menu e i codici | non scrivi un prompt, scegli una voce: `MR`, due lettere |
| 28 | contestare la premessa | «è un'impressione o qualcuno l'ha misurata?» e il bivio |
| 29 | il checkpoint umano | la classifica tolta, e il motivo che **va scritto** |
| 30 | cosa resta dopo | l'albero di `_bmad-output/`: file, non chat |
| 31 | la code review | il diff `stato <> 'disdetta'` → `stato = 'attiva'` |
| 32 | perché il checkpoint esiste | corretto per la story, sbagliato per il progetto |
| 33 | pitfall e strategie | cinque errori e cinque contromisure, in parallelo |

Il discorso da dire, slide per slide, sta in **[`copione-demo.md`](copione-demo.md)**:
parole vere, non appunti. Serve soprattutto per le 31-32, dove il ritmo conta.

**Se devi tagliare**: tieni 31, 32 e 33. Sono le uniche che mostrano il processo *mentre
intercetta qualcosa*, che è la tesi del talk. Le 27-30 si raccontano a voce in un minuto.

Le trascrizioni usano il componente `.term`: finestra scura, `white-space: pre`, e la
classe **`.term-hl`** per la riga su cui deve cadere l'occhio (terracotta) o `.term-hl.olive`
per le conclusioni dell'agente. Sotto ogni terminale c'è una fascia `.term-take` con
l'etichetta — *Strategia*, *Capacità*, *Pitfall*, *La tesi* — che dice la morale in una riga.

I codici di menu (`BP MR DR TR CB WB DP`) non sono inventati: vengono dai `customize.toml`.
Se qualcuno in sala ha BMAD installato e controlla, tornano.

**Onestà**: il caso è ricostruito su un progetto di esempio, non è il log di una sessione
reale. Il tag della 26 dice «caso di esempio» apposta. Se qualcuno chiede, dillo — la
credibilità la porta la coerenza dei documenti, non fingere che sia un log.


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
- **slide 26** — la **Demo**: il riquadro tratteggiato e la riga «cosa stai per mostrare»
  Il materiale sta in `demo-sale-riunioni/`, con scaletta da 8 minuti e versione da 3
- **slide 36** — Graphify, llm-wiki e le alternative: resta da confermare lo **stato di
  maturità** dei primi due, e il `[X]` del consiglio finale
- **slide 37** — il caso di Graphify in pratica è **ricostruito**: se hai una sessione vera,
  sostituisci le righe del terminale
- **slide 40** — link e QR code
- **slide 41** — nome e contatto interno

## La slide da curare

La **25** (il loop BMAD) è quella che regge il talk. Compare tre volte con lo stesso disegno
e centro diverso: completa (25), con il buco del contesto mancante (34), col grafo che lo
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
