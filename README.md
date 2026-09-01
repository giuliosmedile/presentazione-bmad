# Oltre il vibe coding

Talk in italiano, 60 minuti: un approccio strutturato all'utilizzo dell'AI nello sviluppo
software. Pubblico aziendale, misto per competenza sull'AI.

## File

```
oltre-il-vibe-coding.html          la presentazione (CSS e JS inline, 40 slide)
demo-sale-riunioni/                il materiale per la slide Demo (ha un README suo)
assets/prompt-vibe-coding.png      lo screenshot del prompt (slide 8)
assets/codice-senza-memoria.png    il codice con la costante misteriosa (slide 12)
assets/analyst_mary.jpg            ritratto di Mary, Business Analyst (slide 16)
assets/pm_john.jpg                 ritratto di John, Product Manager (slide 17)
assets/architect_winston.jpg       ritratto di Winston, System Architect (slide 18)
assets/developer_amelia.jpg        ritratto di Amelia, Senior SW Engineer (slide 19)
assets/ux_sally.jpg                ritratto di Sally, UX Designer (slide 20)
assets/all_bmad_personas.jpg       i cinque insieme (slide 21)
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
| 03 · BMAD | 15-33 | ~22 min, di cui ~8 di demo |
| 04 · Trasferire conoscenza agli agenti | 34-37 | ~12 min |
| 05 · Chiusura | 38-40 | ~4 min |

Il capitolo 03 sta in 19 slide, di cui **otto sono la demo** (26-33). Non è più una demo dal
vivo: sono slide, quindi il tempo è prevedibile — circa un minuto l'una. Le undici slide che
la precedono devono stare in dodici minuti, quindi vanno tenute veloci.

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
| 15 | dove BMAD parcheggia memoria e decisioni: artefatti, contesto, fatti, story |
| 16-20 | un identikit a testa: Mary, John, Winston, Amelia, Sally |
| 21 | i cinque insieme, e la catena di artefatti che si passano |
| 22 | come si parla con un agente: il menu, i codici, la persona che resta attiva |
| 23 | la fase di planning: quattro conversazioni, quattro documenti |
| 24 | il loop di sviluppo: una story per giro |
| 25 | il diagramma del loop coi checkpoint umani |
| 26 | il caso della demo: sei sale, il 41%, l'ipotesi |
| 27-32 | **la demo**, sei momenti: menu · premessa · checkpoint · artefatti · review · perché |
| 33 | pitfall e strategie, la slide che il pubblico fotografa |

Le tre slide **22-24** rispondono alla domanda che arriva sempre («ma come ci si parla?») e
costruiscono verso il diagramma della 25, che le riassume. La 26 chiude il capitolo dal vivo.

La **15** apre il capitolo elencando i quattro posti dove una decisione si deposita:
gli artefatti nel repo, `project-context.md`, i `persistent_facts` nel `customize.toml`
dell'agente, e il diario che ogni story si lascia dietro. Sotto, la finestra con l'albero
del progetto — la stessa idea della slide 17 di `Sviluppo Agentico (standalone).html`, ma
rifatta con le classi `.code-window` di questo deck invece degli stili inline, e con
**l'albero vero di `BE_TRRN_API_Query_Engine`** al posto di quello generico: `_bmad-output/`
con contesto, planning e le 21 story, `.claude/skills/bmad-agent-*/customize.toml`, `docs/`.

Se cambi i percorsi, la colonna delle annotazioni va riallineata a mano: è padding di spazi
dentro un blocco `white-space: pre`, calcolato su 37 caratteri di prefisso.

Nota che la slide cita PRD e architettura prima che il pubblico sappia chi li scrive: se in
prova stona, sta meglio subito dopo la **23**, che dice la stessa cosa dal lato del planning.

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

La **22** chiude il blocco degli identikit con la tesi: quello che i cinque si passano è un
documento, non una conversazione. La **24** ci torna sopra («la conversazione è usa e getta,
i documenti no») ed è il ponte verso il capitolo 04, dove il problema diventa che quei
documenti non contengono la conoscenza che sta fuori dal repo. Non anticiparlo qui.

## Cosa è stato tolto dal capitolo 03

- **il roster** dei sei ruoli: ridondante, gli identikit presentano i nomi meglio
- **la slide sulle skill** con la matrice da riempire
- **le tre slide di walkthrough** (il caso, il loop in azione, il risultato): erano tutte
  segnaposto e facevano lo stesso lavoro della Demo dal vivo
- **«Punto per punto»**, il confronto BMAD vs vibe coding

Se ti servisse recuperarne una, stanno nei backup `oltre-il-vibe-coding.html.bak*`.

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


## Navigazione

| Comando | Effetto |
|---|---|
| `←` `→` / `Spazio` / `PagSu` `PagGiù` | slide precedente / successiva |
| `Home` / `Fine` | prima / ultima slide |
| rotella, swipe, pallini a destra | navigazione |
| `E` | attiva/disattiva la modalità modifica |
| `Ctrl+S` | esporta un HTML con le modifiche applicate |

## Note per il relatore

Ogni slide ha il testo da dire e i punti chiave. Apri DevTools con `F12`, stacca la finestra
su un secondo schermo: le note compaiono nella console a ogni cambio slide.

## Da riempire prima del talk

I segnaposto sono tra parentesi quadre `[così]` o in riquadri tratteggiati color pesca.

- **slide 1** — nome, ruolo, team, data
- **slide 5** — il dato di adozione: `__ %` e la fonte. **Verificala**: davanti a un pubblico
  tecnico un numero non citato viene contestato
- **slide 17-21** — i cinque identikit: **niente da riempire**. Semmai da tagliare: se in
  prova sfori, togli una o due delle cinque righe di **Cosa fa**, sono le più comprimibili
- **slide 23** — l'esempio di sessione con Mary: è verosimile ma inventato. Se hai una
  sessione vera, incollala qui: due righe di trascrizione reale valgono più di un mock
- **slide 27** — la **Demo**: il riquadro tratteggiato e la riga «cosa stai per mostrare»
  Il materiale sta in `demo-sale-riunioni/`, con scaletta da 8 minuti e versione da 3
- **slide 30** — Graphify e llm-wiki: cosa fanno, come si integrano, stato di maturità
- **slide 33** — link e QR code
- **slide 34** — nome e contatto interno

## La slide da curare

La **26** (il loop BMAD) è quella che regge il talk. Compare tre volte con lo stesso disegno
e centro diverso: completa (26), con il buco del contesto mancante (28), col grafo che lo
riempie (31). Il richiamo funziona perché il resto dell'immagine è identico — se ne modifichi
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
