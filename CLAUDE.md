# CLAUDE.md — come si lavora su questo repo

Contesto per una sessione nuova. Il **cosa** (contenuto delle slide, struttura del talk)
sta in [`README.md`](README.md); qui c'è il **come**: convenzioni, trappole già pagate e il
giro di verifica da fare dopo ogni modifica.

---

## Il progetto

Presentazione «**Oltre il vibe coding**», talk aziendale in **italiano**, 60 minuti,
pubblico misto per competenza sull'AI. Tesi: il problema non è l'AI, è l'assenza di processo;
BMAD dà la struttura, la memoria a grafo dà il contesto.

File principale: **`oltre-il-vibe-coding.html`** — 41 slide, ~3100 righe, CSS e JS inline,
zero dipendenze a runtime tranne i font Google. Generato in origine con lo skill
`html-slides` (plugin 0.9.4), ma ormai è divergente: **non rigenerarlo dallo skill**,
si modifica a mano.

Stato: `main`, working tree pulito. C'e' un remote `origin`, ma il lavoro si fa in locale.

---

## Anatomia del file

```
<head>
  link ai font (Caprasimo, Figtree, JetBrains Mono)
  <style>  ~1440 righe, 215 classi
    :root con i token Organic
    base, chrome, tipografia, icone .ic, animazioni
    componenti (tri-grid, duo, loop-diagram, term, idkit, pf-grid, …)
    @media su ALTEZZA: 700 / 600 / 500 px
    modalità editing
  </style>
<body>
  chrome fisso (particles, branding, chapter-mark, progress, counter)
  <svg> nascosto con i <defs> globali (marker della freccia del loop)
  <div class="deck">  41 × <div class="slide" data-slide="N" data-chapter="C">
  <script>  runtime: navigazione, note in console, editor inline
```

### Le regole invariabili

1. `<div class="deck" id="deck">` avvolge tutte le slide
2. Le slide sono `<div class="slide">` con `data-slide` **sequenziale da 0**
3. Solo la prima ha `class="slide active"`
4. `goTo()`, `next()`, `prev()` sono funzioni globali
5. CSS e JS inline (eccetto i font)
6. Ogni slide ha in fondo `<script type="application/json" class="slide-notes">`
7. Ogni slide ha `data-chapter="0..5"` — pilota il segnalibro in alto
8. `<meta name="generator">` presente

Le note usano il formato `{"title": "…", "script": "…", "notes": [...]}`. Lo spazio dopo i due
punti c'è quasi ovunque ma **non dappertutto**: usa `"title":\s*"` e non uno dei due letterali,
altrimenti perdi delle slide senza accorgertene.

---

## Trappole già pagate

Queste sono costate tempo. Leggile prima di toccare qualcosa.

### 1. Le slide si identificano dal titolo nelle note, mai dai commenti HTML

I commenti `<!-- ---- N · TITOLO ---- -->` stanno **in coda al blocco precedente**, non
dentro la slide che nominano. Uno script che identifica le slide dai commenti è sfalsato di
uno — è già successo: ha cancellato la slide sbagliata e duplicato quella dopo.

Il modo giusto: split su `\n  <div class="slide`, poi `re.search(r'"title":\s*"(.*?)"', blocco)`.

### 2. Le keyframe sovrascrivono `transform`

`bounce-N`, `anim-N`, `pop-N` animano `transform` con `fill: both`. Se le metti su un
elemento centrato con `translate(-50%,-50%)`, a fine animazione la centratura **sparisce** e
l'elemento scivola via. È successo due volte: nodi del loop e checkpoint.

Soluzione: wrapper che posiziona (`.loop-pos`), figlio che si anima. Se ti serve solo un
fade, usa `fadeOnly`, che tocca l'opacità e basta.

### 3. PowerShell rovina l'UTF-8

`Get-Content` + `Set-Content` fa un doppio encoding: accenti ed emoji diventano `Ã¨`, `â€"`.
È già capitato una volta e il recupero (reverse CP1252 → UTF-8) ha funzionato per fortuna.

**Usa Python** con `io.open(..., encoding='utf-8')`, oppure `[IO.File]::ReadAllText/WriteAllText`
con encoding esplicito. Mai `Get-Content`/`Set-Content` su questi file.

Anche gli heredoc bash si rompono sugli apostrofi italiani (`l'agente`): meglio scrivere il
contenuto con lo strumento di scrittura file e poi concatenare.

### 4. Il pannello di anteprima non basta a verificare

Serve la pagina come `data:` URL, quindi:
- le immagini con percorso relativo **non caricano**
- `localStorage` lancia `SecurityError` (l'editor lo gestisce in try/catch, ma non lo testi)
- eventi wheel spuri fanno avanzare le slide da soli

Per verificare davvero, servi il progetto via HTTP:

```bash
python -m http.server 8765 --bind 127.0.0.1
```

e apri `http://127.0.0.1:8765/oltre-il-vibe-coding.html` in un tab nuovo. Ferma il server
quando hai finito.

### 5. `white-space: pre` sui terminali, e righe vuote vere

`.term-body` e `.code-body` hanno `white-space: pre`: le trascrizioni sono scritte a mano
riga per riga. Senza `pre` il menu collassa in un paragrafo unico.

Per la riga vuota **usa una riga vuota vera** nel sorgente. Un `<span>` con `display:block`
dentro un contesto `pre` fa a pugni con i newline e non rende.

### 6. In un contenitore flex ogni figlio inline diventa un elemento flex

Un `<strong>` dentro un `.pf-item` con `display:flex` si stacca dal testo, col gap in mezzo.
Per il testo scorrevole usa flusso normale + `::before` in `position:absolute`.

### 7. Contrasto sulla carta chiara

`--color-accent` (#c67139) regge solo nel testo grande. Per etichette piccole scendi a
`--color-accent-700`. Sotto 3:1 in proiezione spariscono.

L'unica eccezione tollerata è `.np-buttons` (le iconcine `− □ ×` della finestra Blocco note):
decorative, 2.35, identiche al deck di riferimento.

### 8. Il font emoji va prima del generico

`monospace` intercetta ogni carattere e uccide il fallback. Nella stack:
`'JetBrains Mono', 'Segoe UI Emoji', …, ui-monospace, monospace`.

### 9. Le chiavi dell'editor sono posizionali

`data-edit-key="sN-eM"` dipende dalla posizione della slide. Dopo ogni riordino **alza la
versione** di `storageKey` (adesso `oltre-il-vibe-coding-edits-v22`), altrimenti vecchi testi
salvati atterrano su elementi sbagliati. Testo perso è meglio di testo spalmato a caso.

---

## Il giro di verifica

Da eseguire nella console del browser dopo ogni modifica, a **1440×900 e 1024×576**.

```javascript
(() => {
  const sl=[...document.querySelectorAll('.slide')], over=[];
  sl.forEach((s,i)=>{
    goTo(i); document.getAnimations().forEach(a=>{try{a.finish()}catch(e){}});
    const sr=s.getBoundingClientRect(); let top=Infinity,bot=-Infinity;
    s.querySelectorAll('*').forEach(c=>{
      if(c.classList.contains('glow-blob')||c.tagName==='SCRIPT')return;
      if(c.closest('svg'))return;
      const r=c.getBoundingClientRect(); if(r.width===0&&r.height===0)return;
      top=Math.min(top,r.top); bot=Math.max(bot,r.bottom);});
    const a=Math.round(sr.top-top), b=Math.round(bot-sr.bottom);
    if(a>2||b>2) over.push({i,sopra:a,sotto:b});
  });
  goTo(0);
  return {overflow:over, count:sl.length,
    seq:sl.every((s,i)=>s.dataset.slide===String(i)),
    active:document.querySelectorAll('.slide.active').length,
    notes:sl.every(s=>{try{JSON.parse(s.querySelector('script.slide-notes').textContent);return true}catch(e){return false}}),
    immagini:[...document.querySelectorAll('.slide-image')].every(i=>i.naturalWidth>0)};
})()
```

Atteso: `overflow: []`, `count: 41`, `seq: true`, `active: 1`, `notes: true`, `immagini: true`.

**Importante**: `finish()` sulle animazioni prima di misurare. Senza, misuri a metà
transizione e i numeri sono spazzatura — è già successo (nodi larghi 31px invece di 104).

Escludi i `.glow-blob` dal calcolo: sono decorativi con offset negativi e danno falsi
positivi di overflow (sono clippati da `overflow:hidden`).

Poi: `read_console_messages` con `onlyErrors` deve tornare vuoto.

Per i tre diagrammi del loop c'è un controllo in più — angoli, scarto dal raggio,
sovrapposizioni fra nodi/centro/checkpoint. Vedi la sezione sul loop qui sotto.

---

## Design system

Tema **Organic**, preso dal deck di riferimento `Sviluppo Agentico (standalone).html`.

| Token | Valore | Uso |
|---|---|---|
| `--color-bg` | `#f5ead8` | carta |
| `--color-surface` | `#ebddc5` | schede |
| `--color-text` | `#201e1d` | inchiostro |
| `--color-accent` | `#c67139` | **terracotta = intervento umano** |
| `--color-accent-2` | `#7a8a5e` | **oliva = agenti e nodi del grafo** |
| `--color-danger` | `#b3543a` | problemi |

Più tre rampe da 100 a 900 (`--color-neutral-*`, `--color-accent-*`, `--color-accent-2-*`).

**La semantica dei colori è un impegno preso col pubblico**: il terracotta significa
"qui entri tu" in tutto il talk. Non assegnarlo ad altro.

Font: **Caprasimo** per i titoli (peso 400 e basta, non ha altri pesi), **Figtree** per il
testo, **JetBrains Mono** per il codice. I titoli usano `--font-heading-weight`, non `bold`.

Alias legacy: `--bg`, `--text-muted`, `--accent-blue` ecc. sono rimappati sui token Organic
in fondo a `:root`. I componenti vecchi li usano ancora; funzionano, non serve migrarli.

### Componenti principali

| Classe | Cosa |
|---|---|
| `.term` + `.term-body` | terminale simulato, `pre`, con `.term-hl` per la riga chiave |
| `.term-take` | fascia sotto il terminale con la morale (Strategia / Capacità / Pitfall / La tesi) |
| `.code-window` / `.np-window` | blocco codice scuro / finestra Blocco note chiara |
| `.loop-diagram.quad` | anello a 4 nodi con checkpoint, in 3 varianti |
| `.sev-decision/-patch/-defer/-dismiss` | i 4 bucket del triage di `bmad-code-review` |
| `.idkit` | identikit degli agenti BMAD |
| `.tri-grid`, `.duo`, `.pf-grid` | griglie a 3, confronto a 2, pitfall/strategie |
| `.image-frame.image-screenshot` | immagine incorniciata |
| `.placeholder` | riquadro tratteggiato: roba da riempire prima del talk |

### Il diagramma del loop

Compare **tre volte** con lo stesso disegno e centro diverso: completo (slide 24 del
contatore), col buco del contesto (34), col grafo che lo riempie (38). Il richiamo funziona
solo se il resto è identico: **se ne modifichi uno, modificali tutti e tre**.

Geometria: cerchio r=140 in viewBox 400×400, nodi a -90°/0°/90°/180° (`left/top` 50%/15%,
85%/50%, 50%/85%, 15%/50%), checkpoint sulle diagonali a 74.75%/25.25%. Tutto scala da
`--ls`, una variabile sola. Il marker della freccia sta nei `<defs>` globali in cima al body.

---

## Convenzioni git

Niente più file `.bak` — la storia dei dieci backup manuali è già stata ricostruita in
commit datati. Il `.gitignore` esclude `*.bak*` e `*.pdf` (l'export pesa 16 MB e si rigenera).

Messaggi in italiano, imperativi, senza prefissi di tipo. Corpo solo quando il perché non è
ovvio dal titolo. Trailer `Co-Authored-By: Claude Opus 5 <noreply@anthropic.com>`.

**Fai un commit per modifica logica**, non uno alla fine della sessione.

---

## Preferenze di lavoro emerse

- Si lavora **in italiano**, anche nei commenti del codice e nei commit
- Niente numeri inventati: i segnaposto restano `[fra parentesi]` finché non arriva il dato
  vero. Il dato di adozione (slide 5, 68%, JetBrains Developer Ecosystem Survey 2026) è
  confermato — vale ancora per le metriche del walkthrough e ogni nuovo numero che entra
- Il caso della demo è **ricostruito**, e va detto. Mai spacciarlo per una sessione reale.
  Ma le **meccaniche** sono vere e vanno tenute tali: sono prese da BMAD 6.6.0 in
  `TIRRENO/BE_TRRN_API_Query_Engine`. Prima di inventare come si comporta un agente, apri
  `.claude/skills/bmad-*/` e guarda. `customize.toml` ha i menu e le voci degli agenti,
  `bmad-code-review/steps/` ha i tre reviewer e il triage in quattro bucket, e i file in
  `_bmad-output/` mostrano che forma hanno davvero gli artefatti
- **Il caso è brownfield, e non è un dettaglio di colore.** Il servizio esiste dal 2022, le
  sale si prenotano su Outlook, BMAD entra su un codice che ha gia quattro anni. Se tocchi
  il materiale della demo, questa premessa regge tutto: gli artefatti di planning sono
  *ricostruiti leggendo il codice*, e il bug finale nasce dal riuso corretto di un metodo
  scritto nel 2023. Il pezzo che non si tocca e' `architecture.md` § E4 — per le occorrenze
  di serie `id_evento_graph` contiene l'id della serie — perche' e' piantato in tre posti
  indipendenti (architettura, migrazione `V3`, commento in `MappaturaEventoGraph`) ed e'
  quello che la code review raccoglie alla slide 32
- Quando un contenuto non sta in una slide, se ne fanno due invece di comprimere
- Verifica misurando, non a occhio: le regressioni di layout qui si vedono solo coi numeri

---

## Cosa manca ancora

Segnaposto da riempire prima del talk: elenco completo e aggiornato nel README, sezione
**Da riempire prima del talk**. Non duplicarlo qui — è già andato fuori sincrono una volta.

Idee rimaste in sospeso, mai implementate:

- build progressive sul diagramma del loop (prima il ciclo, poi i checkpoint che si accendono)
- slide separatrici di capitolo riusando l'agenda con `.agenda-row.current`
- sostituire lo screenshot del codice `291048` con uno preso dal codebase vero

---

## File del repo

```
oltre-il-vibe-coding.html   il deck (41 slide)
copione-demo.md             il discorso parlato della demo, slide per slide
README.md                   struttura del talk, contenuti, da-riempire
CLAUDE.md                   questo file
demo-sale-riunioni/         progetto finto + 9 trascrizioni (README suo)
assets/                     2 screenshot + 6 ritratti degli agenti
sviluppo-agentico.html      versione precedente, altro taglio
Sviluppo Agentico (standalone).html   il deck da cui viene lo stile
```
