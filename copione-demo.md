# Copione della demo — SaleRiunioni

Slide **25-33** del deck (quelle che sul contatore in basso a destra fanno 25/41 → 33/41).
Nove slide, otto minuti scarsi.

---

## Cosa stai mostrando, in breve

Un progetto finto ma coerente: un servizio interno che affianca Outlook sulle sale riunioni.
Il dominio non va spiegato — in sala lo capiscono prima che tu finisca la frase — e così
tutto il tempo se lo prende il processo invece del contesto.

**Non è greenfield, ed è la prima cosa da dire.** Il servizio esiste dal 2022: legge i
calendari delle sale da Outlook, accende i sei display fuori dalle porte, tiene la pagina
«sala libera adesso». Le sale si prenotano su Outlook e continueranno a prenotarsi lì.
Quello che arriva è una feature nuova su un codice che ha quattro anni e nessun autore
reperibile — cioè la situazione in cui è il pubblico lunedì mattina.

**La demo percorre il loop della slide 24 invece di raccontarlo.** Nove slide in ordine di
tempo: parli a un agente, fissi quello che avete deciso, produci una story, la implementi,
la fai rivedere.

| Slide | Cosa succede | Chi |
|---|---|---|
| 25 | il caso | — |
| 26 | attivazione e menu | Mary |
| 27 | la feature rimandata al problema | Mary |
| 28 | la decisione sale nel contesto | Mary → John + Winston |
| 29 | la story nasce, e pianta il vincolo | Amelia `CS` |
| 30 | la story viene implementata | Amelia `DS` |
| 31 | tre reviewer, tre contesti | Amelia `CR` |
| 32 | il verdetto contraddittorio | Amelia `CR` |
| 33 | pitfall e strategie | — |

**La demo ha due metà, e servono a due cose diverse.**

La prima (26-30) risponde alla domanda pratica che si stanno facendo tutti: *ma come ci si
parla, a 'sta roba, e cosa mi resta in mano dopo?* Vedono il menu, vedono un agente che
rimanda indietro una feature invece di partire a scriverla, vedono una decisione presa a
voce finire in un file che tutti gli agenti leggono, e vedono dove finisce il lavoro quando
chiudi il portatile.

La seconda (31-32) è il motivo per cui la demo esiste. È il processo che becca un difetto
che nessun test avrebbe preso. Fin lì hai raccontato un metodo ordinato; da lì dimostri che
l'ordine serve a qualcosa.

**Se ti resta poco tempo taglia dalla prima metà, mai dalla seconda.** Ma la 29 non
tagliarla: è quella che pianta il vincolo che la 31 raccoglie. Senza, la 31 è un colpo di
fortuna.

---

## Prima di partire

Tre cose da tenere a mente per tutta la demo.

**Non stai facendo vedere un tool che scrive codice.** Quello lo fanno già tutti. Stai
facendo vedere un processo che si accorge di qualcosa. Ogni volta che hai un dubbio su cosa
dire, torna lì.

**Il difetto non nasce da codice scritto male.** Nasce da due righe scritte a mano invece
che da una chiamata a una funzione che c'era già — con una motivazione che darebbe chiunque.
Se il pubblico esce pensando «l'agente ha sbagliato», hai perso il punto.

**Non usare mai parole che il pubblico deve imparare.** Non serve nessun gergo: c'è uno
schermo appeso in corridoio, e ci finisce sopra il titolo di un colloquio. Se ti senti in
procinto di spiegare un meccanismo, fermati — questa demo non ne ha uno da spiegare.

Poi dillo subito che il caso è ricostruito. Costa mezza frase e ti toglie di mezzo l'unica
obiezione che potrebbe farti perdere la sala a metà. Il caso è finto; **le meccaniche che
vedete sono quelle vere**, prese dall'installazione di BMAD 6.6.0 che gira su un progetto
nostro.

---

## Slide 25 — Il caso

*Sullo schermo: tre numeri grandi. 2022, 41%, 6.*

> Un giro completo su un caso piccolo. Sale riunioni: il dominio non ve lo devo spiegare.
>
> Ma attenzione a cosa **non** stiamo facendo. Non costruiamo un sistema di prenotazione: le
> sale si prenotano su Outlook, come da voi. Quello che esiste è un servizio interno, in
> piedi dal 2022, che legge i calendari delle sale, accende i sei display fuori dalle porte
> e manda un report mensile al facility. Quattro anni, due sviluppatori part-time, nessuno
> degli autori originali ancora in azienda.
>
> Adesso arriva una richiesta: se nessuno fa check-in entro dieci minuti, la sala torna
> libera. **Una feature nuova su un codice che c'è già.** Che è la situazione in cui siete
> tutti lunedì mattina.
>
> Il quarantuno per cento è il numero che giustifica il progetto: tre giri a piedi, in giorni
> diversi, quattro sale su dieci risultavano prenotate e dentro non c'era nessuno.
>
> Il sei — quello che sembra il numero inutile dei tre — è quello che ve lo farà ricordare.
> Sei schermi appesi nei corridoi, uno per sala, senza login. Li legge chiunque passi.
>
> Il caso è ricostruito, non è il log di una sessione vera. Le meccaniche che vedete — i
> menu, la review, gli stati — sono quelle vere, prese da un'installazione che gira su un
> progetto nostro.

**Non spiegare adesso perché contano i display.** Si pagano da soli alla 31, e se lo
anticipi qui la 31 diventa una conferma invece che una scoperta.

---

## Slide 26 — Attivazione e menu

*Sullo schermo: il terminale con l'attivazione di Mary e il menu a tre colonne.*

> Si attiva un agente. `bmad-agent-analyst`, e Mary è in linea.
>
> Non aspetta che le scriva un tema. Si presenta e apre un menu.
>
> Guardate la terza colonna. Ogni voce non è un argomento di conversazione: è uno **skill**
> che parte. Rispondo `CB` e parte `bmad-product-brief`, che ha i suoi passi, i suoi file di
> contesto, le sue regole.
>
> Quindi il punto non sono le due lettere. È che il contesto resta stretto per costruzione,
> non perché me lo ricordo io.
>
> Codici e nomi degli skill stanno in un file di configurazione dentro il repository. Se
> avete BMAD installato e andate a controllare, tornano.

**Se hai due secondi**, indica la settima voce, `DP`, documentare un progetto esistente: è
il primo gesto su un codice che c'è già, ed è quello che ha prodotto i documenti che gli
altri agenti leggeranno.

---

## Slide 27 — Ti ho chiesto una feature. Lei chiede il problema

*Sullo schermo: il bivio in mezzo, e in fondo il blocco verde oliva.*

Venti secondi in più qui.

> Io le ho dato una feature già decisa. Liberazione automatica, dieci minuti, fatemela.
>
> Guardate la prima riga, perché è quella che non fa nessun altro strumento: **quella che mi
> hai dato non è un problema, è già una soluzione. La domanda qual è?**
>
> Poi chiede se il problema è stato misurato. E quando dico di no, separa due ipotesi. O le
> sale sono poche — problema di muri e di budget, e la feature che ho chiesto non sposta
> niente. O risultano occupate quando non lo sono — problema di comportamento. Se costruisci
> per la seconda e la verità era la prima, hai buttato tre mesi e quei soldi andavano spesi
> in metri quadri.
>
> *[Fate la stessa richiesta a una chat qualsiasi stasera. Vi scrive il codice. Non vi chiede
> se il problema esiste.]*
>
> Il giro fisico dà il quarantuno per cento, e il bivio è deciso: il facility manager ha
> ragione sulla direzione.
>
> *[indica il blocco verde]*
>
> E poi fa una cosa che non le avevo chiesto. Mi dice che perché la funzione serva a
> qualcosa, la sala deve tornare libera **anche su Outlook** — quasi tutti cercano una sala
> dal calendario, e se la libero solo nel nostro database la vediamo libera noi e nessun
> altro.
>
> Ma questo vuol dire che da domani il servizio **smette di leggere e comincia a scrivere**.
> Fino a oggi copiava calendari e li mostrava. Da domani mette le mani sul calendario delle
> persone e sugli schermi appesi in corridoio.
>
> Non è un dettaglio tecnico: cambia il rischio di tutto il progetto. E infatti si ferma e me
> lo chiede.

---

## Slide 28 — La decisione sale nel contesto

*Sullo schermo: la conferma in alto, poi party mode con due agenti, e in fondo il riquadro
verde con la riga scritta in `project-context.md`.*

**È una slide corta ma non correrci sopra**, e in particolare non correre su Winston: è la
riga che pianta tutto il finale.

> Decido: si scrive anche su Outlook. Altrimenti abbiamo liberato una sala che nessuno riesce
> a prendere.
>
> E Mary fa una cosa che non le ho chiesto. Mi dice che quella decisione l'ha scritta **nel
> brief**, e che il brief descrive *questo* prodotto. Il prossimo documento non lo legge.
>
> Allora apro party mode e mi tiro dentro John e Winston insieme. Party mode non è una trovata
> scenica: ognuno dei due è un subagente vero, con la sua testa. Se sono d'accordo, sono
> d'accordo davvero.
>
> John decide il posto: `project-context.md`. E insiste per scriverci accanto il motivo, non
> solo la regola — un divieto senza motivo lo aggira il primo che ha una buona idea.
>
> *[rallenta]*
>
> Poi parla Winston, ed è la riga che vi chiedo di sentire. Dice: «scrivere fuori», qui, vuol
> dire **due** superfici e non una. Il calendario delle persone, e i sei display appesi in
> corridoio.
>
> E aggiunge una cosa che non gli ho chiesto: sui display il progetto ha già una regola, dal
> 2022. Non l'ha scritta lui. Se l'è trovata leggendo il codice.
>
> *[indica la riga grigia in fondo]*
>
> E adesso il pezzo che vale la slide. `project-context.md` non è un file qualsiasi: sta nei
> **persistent\_facts** di ogni agente, riga letterale, con la glob. Ogni agente lo carica
> all'attivazione, sempre.
>
> Quella regola dalla prossima attivazione ce l'hanno tutti, senza che io la ripeta. Anche
> Amelia, che quando l'abbiamo decisa non era nemmeno nella stanza.

**Se qualcuno chiede se è vero**: sì, ed è verificabile. In
`.claude/skills/bmad-agent-*/customize.toml` c'è la riga
`persistent_facts = ["file:{project-root}/**/project-context.md"]`, identica per tutti e sei
gli agenti.

**Se hai due secondi**, aggancia la slide 16: sono i quattro posti dove una decisione si
deposita, e questo è il secondo.

**Questa slide è anche il ponte verso il capitolo 04.** Qui il contesto è un file che tutti
leggono; nel capitolo 04 il problema diventa che un file solo non basta.

---

## Slide 29 — La story è un contratto

*Sullo schermo: tre righe grigie in cima al terminale, poi la story appena creata con
l'ultima riga evidenziata.*

**Il ponte va detto, non saltato.** Fra il brief e la story sono successe due cose che non
mostri, e una di queste — `architecture.md` — è il file su cui poggia tutto il finale.

**Poi non leggere la story.** Indichi la struttura e ti fermi sull'ultima riga.

> *[indica le tre righe grigie]*
>
> Cosa è successo nel mezzo, perché non ve lo faccio vedere: il brief è andato a John, che
> l'ha spaccato in un PRD e in epiche. Le epiche sono andate a Winston, che ha scritto
> l'architettura — e su un progetto di quattro anni «scrivere l'architettura» vuol dire
> leggersi il codice e ricostruire perché è fatto così. Due passaggi, ognuno chiuso con un
> checkpoint umano come quello che avete appena visto.
>
> E tenete a mente `architecture.md`, perché fra tre slide è il file che decide tutto.
>
> `CS`, create-story. Amelia legge l'architettura e il contesto di progetto, e scrive la
> story. In un file, non a voce.
>
> Non è un ticket con due righe. Criteri di accettazione numerati, e ogni task dice quale
> criterio soddisfa. Le note tecniche citano la fonte: percorso del file e sezione.
>
> Guardate il criterio tre, perché è quello su cui gira tutto: **il display deve dire che la
> sala è libera e per quale motivo.**
>
> *[indica l'ultima riga, e leggila piano]*
>
> Il display non è una schermata dell'applicazione: è un cartello appeso in corridoio. E una
> riunione, su Outlook, può essere marcata **privata**. Per il testo che ci finisce sopra c'è
> una classe apposta, e non si usa il titolo grezzo.
>
> Sta scritto nell'architettura del progetto. E non sta nei criteri di accettazione di questa
> story.
>
> Tenetela lì, serve fra due slide.

**Se qualcuno chiede perché non è un criterio di accettazione**, la risposta è buona e vale
la pena darla: perché non è un requisito. Un AC descrive cosa deve fare la story per essere
accettata, e nessuno ha chiesto niente sulle riunioni private. Sarebbe un vincolo di
progetto travestito da requisito — vale per tutto quello che il sistema mostra, non per
questa story.

---

## Slide 30 — Cosa resta dopo

*Sullo schermo: l'esecuzione di `DS`, la riga del display scritta a mano, la definition of
done, il passaggio di stato in fondo.*

> `DS`, dev-story. Implementa un task per volta. Guardate come parla: task, criterio, file,
> riga, test. Ogni riga è verificabile. Non è uno stile: nella configurazione dell'agente c'è
> scritto che deve parlare in percorsi di file e identificativi di criteri.
>
> *[indica il task 5, senza calcare]*
>
> Guardate il task cinque, perché fra due slide ci torniamo. Deve scrivere la riga del
> display, e la scrive lì dove sta lavorando: «Libera, nessun check-in per», più il titolo
> della riunione.
>
> E dice anche perché non l'ha messa nella classe dei testi: quelli hanno una forma diversa,
> e aggiungere un metodo per una stringa sola le sembrava sproporzionato.
>
> È un ragionamento onesto. È quello che avreste fatto anche voi.
>
> Alla fine passa una definition of done. Non decide lei quando ha finito: è una lista da
> spuntare.
>
> La domanda pratica: chiudo il portatile, cosa mi resta in mano?
>
> Questo. Non una chat da riaprire e riscorrere. Il diario di come è stata implementata sta
> dentro il file della story: modello usato, note, file toccati, change log. Si versiona, si
> diffa, passa in review come qualunque altro file.
>
> Ultima riga: lo stato non è nella mia testa né nella vostra. Sta in `sprint-status.yaml`, e
> a muoverlo è il workflow.

**Il task 5 va letto, non sottolineato.** Se lo annunci come indizio, il pubblico capisce
che c'è una trappola e la 31 perde la sorpresa. Leggilo come una cosa normale, perché lo è.

---

## Slide 31 — Tre reviewer, tre contesti

*Sullo schermo: i tre reviewer, poi due blocchi evidenziati che dicono cose opposte.*

Da qui rallenta.

> Story finita, codice scritto, test verdi. Faccio partire la code review, in una sessione
> nuova.
>
> Non manda un revisore. Ne manda tre in parallelo, con contesti diversi apposta.
>
> *[indica le tre righe, una alla volta]*
>
> Il **Blind Hunter** vede solo il diff. Niente specifica, niente progetto.
> L'**Edge Case Hunter** vede il diff e può leggere il progetto, la specifica no.
> L'**Acceptance Auditor** vede tutto: diff, story, documenti di contesto.
>
> *[blocco verde]* L'Acceptance Auditor, quello che ha la specifica, dice: **spec
> soddisfatta**. Criteri verificati, task spuntati, perimetro rispettato.
>
> *[blocco arancione]* L'Edge Case Hunter, che la specifica non ce l'ha e il progetto sì, è
> andato a leggersi l'architettura. E ha trovato che quel titolo lì non si può stampare così:
> il display è un cartello appeso in corridoio, una riunione su Outlook può essere marcata
> privata, e dal 2022 c'è una classe che per quelle scrive «Riunione riservata» al posto del
> titolo.
>
> Quella classe non è stata usata.
>
> Quindi alle nove e quaranta, sullo schermo davanti alla sala, compare il titolo vero di una
> riunione privata. E in corridoio ci passa chiunque.

*Fermati. Hanno appena visto due risposte opposte, e hanno ragione tutte e due.*

**Non descrivere il danno più di così.** Alla slide dopo si vede scritto, e vedere le due
righe fa più effetto di qualunque frase tu possa dire adesso.

---

## Slide 32 — Corretto per la story, sbagliato per il progetto

*Sullo schermo: il triage in quattro caselle, il diff, il prima/dopo sullo schermo, la
domanda.*

> Il triage non è una lista di gravità: sono quattro caselle. Una cosa da decidere insieme a
> me, due patch, una rinviata perché c'era già prima, tre buttate come rumore. E finiscono
> scritte dentro la story, con file e riga, non in una chat.
>
> *[indica le due righe in mezzo, e lascia il silenzio]*
>
> Queste due righe sono quello che vi portate a casa. Cosa c'era scritto sullo schermo in
> corridoio alle nove e quaranta, e cosa c'è scritto adesso.
>
> Domanda: i test erano verdi e l'hai scritta tu. Come ti è sfuggito?
>
> La risposta è la cosa più istruttiva della giornata, e non è quella che vi aspettate.
>
> Stava verificando il criterio tre — «il display dice che la sala è libera e perché» — e il
> testo lo dice. E l'ha scritto lì perché aggiungere un metodo a una classe di formattazione
> per una stringa sola le sembrava sproporzionato.
>
> È un ragionamento che fate anche voi, ed è quasi sempre giusto. Solo che **quella non era
> una classe di formattazione**: era il posto dove stava un pezzo di conoscenza, e il nome
> non lo diceva.
>
> I test erano verdi onestamente: in tutta la suite non esiste una riunione privata. Le
> fixture sono del 2022, e su una riunione normale il metodo giusto e quello sbagliato
> restituiscono la stessa identica stringa.
>
> *[indica la riga evidenziata]*
>
> Corretto rispetto ai criteri di accettazione. Sbagliato rispetto al progetto. E notate cosa
> **non** è successo: nessuno ha scritto codice sciatto.

**Se qualcuno chiede cosa succede senza la review**: esce il primo giorno in cui una riunione
privata non fa check-in — statisticamente entro la settimana. E non lo scopriamo noi: lo
scopre qualcuno che passa in corridoio. Nel progetto era già andata così nel 2022, ed è per
quello che quella classe esiste.

---

## Slide 33 — Pitfall e strategie

*Sullo schermo: due colonne, rossa e verde, sei voci per parte.*

> Fotografatela, è quella che vi serve lunedì.
>
> A sinistra i modi di buttare via il framework. Ve ne dico tre.
>
> Approvare senza leggere: il checkpoint diventa un timbro. Peggio che non averlo, perché vi
> dà l'impressione di aver controllato.
>
> Scrivere due righe invece di chiamare la funzione che c'era. Sembra un consiglio di stile e
> non lo è: quella funzione **sapeva una cosa** che chi la chiamava non era tenuto a sapere.
> Riscriverla a mano non è codice duplicato, è conoscenza buttata.
>
> Fermarsi al verdetto sui criteri di accettazione. «Spec soddisfatta» non vuol dire «codice
> giusto», e avete appena visto perché.
>
> A destra le contromisure. Se ne prendete una sola, la terza: ogni correzione diventa una
> riga scritta, col motivo. È quella che trasforma il tempo che ci mettete in memoria del
> progetto invece che in tempo perso.
>
> E se lavorate su qualcosa che esiste già — cioè tutti — guardate anche l'ultima. Il primo
> giro non è scrivere codice: è documentare quello che c'è. Quello che non è scritto, per un
> agente non esiste. E a essere onesti, neanche per voi fra sei mesi.

---

## Se sei in ritardo

| Tempo | Cosa fai |
|---|---|
| 8 min | tutto |
| 5 min | salti la 26 e la 30, le racconti a voce mentre passi |
| 3 min | **solo 29, 31, 32**. Il caso lo riassumi in una frase entrando |

Il taglio da non fare mai è la 32: senza quella, la 31 resta un difetto trovato per fortuna.

E **nella versione da 3 minuti la 29 resta**, anche se sembra la più sacrificabile: è quella
che pianta il vincolo sui display. Senza, la 31 non ha niente da raccogliere e il pubblico
deve fidarsi sulla parola.

Nella 29 il pezzo che si comprime è il ponte, non la story. A 3 minuti diventa una riga sola
— «in mezzo John ha fatto le epiche e Winston l'architettura, con i loro checkpoint» — ma
`architecture.md` va nominato comunque.

**La 28 è la prima a cadere sotto i 5 minuti**, ed è una perdita vera: perdi la prova (la
riga nei `persistent_facts`), il ponte verso il capitolo 04, e soprattutto la riga di Winston
che nomina i display. Se la tagli, quella riga va detta comunque entrando nella 29: «e nel
progetto c'è già una regola sui display, dal 2022, che nessuno di loro ha scritto».

Se salti la 30, il fatto che il dev abbia scritto il testo a mano va detto a voce entrando
nella 31: senza, la risposta di Amelia alla 32 non ha appiglio.

---

## Le domande che arrivano

**«Ma è una sessione vera?»**
No, il caso è ricostruito su un progetto di esempio. Ma le meccaniche sono quelle vere: i
codici di menu, gli skill, i tre reviewer, i quattro bucket del triage, gli stati della
story. Dillo senza giri di parole: la credibilità te la dà la coerenza, non il fingere.

**«Perché tre reviewer e non uno?»**
Perché è l'asimmetria che trova le cose. Se gli dai a tutti e tre lo stesso contesto, trovano
tutti e tre le stesse cose. Il Blind Hunter paga tre falsi positivi ed è lo stesso che apre
il finding grosso: non ha la specifica che gli dice che va tutto bene.

**«Ma un test non l'avrebbe preso?»**
No, e il motivo è la parte utile della risposta: in tutta la suite non esiste una fixture di
riunione privata. E su una riunione normale il metodo giusto e quello sbagliato restituiscono
la stessa identica stringa — quindi anche stringendo il test, resta verde. È il genere di
buco che c'è in ogni codebase con qualche anno addosso, e infatti la review lo classifica
come `defer`: vero, ma non l'hai rotto tu.

**«E il linter? Un'analisi statica non lo prende?»**
No. `titolo()` è un metodo pubblico legittimo, usato correttamente in mezzo al progetto per
la sincronizzazione e i log. Non c'è niente da segnalare a livello di codice: la differenza
sta in *dove finisce* quella stringa, e quello lo sa solo chi ha letto il progetto.

**«Quanto costa in token?»**
Più di una chat secca: i passaggi sono più d'uno, ogni agente si carica il contesto, e la
review ne fa partire tre in parallelo. Il paragone onesto non è con la chat: è con il costo
di trovare quel difetto quando lo trova qualcuno in corridoio.

**«E il codice che esce dall'azienda?»**
Sappi in anticipo qual è la policy interna e cita quella. Se non c'è, dillo che non c'è: è
una risposta migliore di una rassicurazione inventata.

**«Quanto ci vuole a impararlo?»**
La parte dei menu, cinque minuti. La parte difficile non è il tool: è prendere l'abitudine di
scrivere il motivo quando correggi qualcosa.

**«E se sbaglia la review?»**
Succede, e infatti tre finding su nove erano rumore. Per quello c'è il triage, e per quello
la review non sostituisce la tua: sposta la tua attenzione sui punti che contano invece che
su tutto il diff.

---

## Cose da non dire

- **Non dire che è una sessione reale.** Mai, nemmeno per sbaglio, nemmeno per fare colpo.
- **Non dire «l'agente ha sbagliato».** Non ha sbagliato: ha preso una decisione di stile che
  prenderebbe chiunque, e il codice che ne è uscito faceva quello che l'AC chiedeva. Se lo
  presenti come un errore del tool, il pubblico conclude «basta un modello migliore» e hai
  perso la tesi.
- **Non spiegare il meccanismo del display.** Non ce n'è uno da spiegare: c'è uno schermo in
  corridoio e ci finisce sopra un titolo. Se ti senti spiegare, ti stai perdendo.
- **Non promettere numeri di produttività.** Non ne hai, e in sala c'è sempre qualcuno che
  quei numeri li ha visti smontare.
- **Non aprire la story 1.3.** Si ferma su una domanda aperta — cosa scrive l'avviso, visto
  che una mail si può inoltrare. È interessante ma è un'altra conversazione, e ti mangia tre
  minuti.
- **Non dire «l'AI ha trovato il bug».** L'ha trovato un reviewer che aveva il progetto e non
  aveva la specifica. È una differenza che questo pubblico coglie, e ti fa guadagnare credito.
- **Non dire che l'Acceptance Auditor ha sbagliato.** Non ha sbagliato: rispetto agli AC quel
  codice è corretto. Se lo presenti come un errore del tool, perdi il punto.

---

## Il materiale

Tutto quello che c'è dietro sta in [`demo-sale-riunioni/`](demo-sale-riunioni/), che ha un
README suo con la struttura completa e la sezione **Fedeltà**, che dice riga per riga cosa è
preso dall'installazione vera.

Le due chat da cui viene questa demo sono `chat/01-mary-analyst.md` e
`chat/09-amelia-code-review.md`. La story con i Review Findings nella sintassi vera è
`progetto/_bmad-output/implementation-artifacts/1-2-liberazione-automatica-della-sala.md`,
e il vincolo che regge il finale sta in `planning-artifacts/architecture.md` § E3 — più il
codice vero in `src/main/java/it/azienda/saleriunioni/display/TestoDisplay.java`, che si può
aprire in sala in dieci secondi e si spiega da solo.

Se qualcuno in sala vuole vedere i file veri dopo il talk, sono navigabili così come sono.
