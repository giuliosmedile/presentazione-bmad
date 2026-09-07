# Copione della demo — SaleRiunioni

Slide **25-33** del deck (quelle che sul contatore in basso a destra fanno 25/41 → 33/41).
Nove slide, otto minuti scarsi.

---

## Cosa stai mostrando, in breve

Un progetto finto ma coerente: un servizio interno che affianca Outlook sulle sale riunioni.
Il dominio non va spiegato — in sala lo capiscono prima che tu finisca la frase — e così
tutto il tempo se lo prende il processo invece del contesto.

**Non è greenfield, ed è la prima cosa da dire.** Il servizio esiste dal 2022: legge i
calendari delle sale da Outlook, accende i display fuori dalle porte, tiene la pagina «sala
libera adesso». Le sale si prenotano su Outlook e continueranno a prenotarsi lì. Quello che
arriva è una feature nuova su un codice che ha quattro anni e nessun autore reperibile —
cioè la situazione in cui è il pubblico lunedì mattina.

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

La seconda (31-32) è il motivo per cui la demo esiste. È il processo che becca un bug che
nessun test avrebbe preso. Fin lì hai raccontato un metodo ordinato; da lì dimostri che
l'ordine serve a qualcosa.

**Se ti resta poco tempo taglia dalla prima metà, mai dalla seconda.** Ma la 29 non
tagliarla: è quella che pianta il vincolo che la 31 raccoglie. Senza, la 31 è un colpo di
fortuna.

---

## Prima di partire

Due cose da tenere a mente per tutta la demo.

**Non stai facendo vedere un tool che scrive codice.** Quello lo fanno già tutti. Stai
facendo vedere un processo che si accorge di qualcosa. Ogni volta che hai un dubbio su cosa
dire, torna lì.

**E il bug non nasce da codice scritto male.** Nasce da codice riusato bene senza sapere
perché era stato scritto così. Se il pubblico esce pensando «l'agente ha sbagliato», hai
perso il punto: l'agente ha fatto quello che avrebbero fatto loro.

Poi dillo subito che il caso è ricostruito. Costa mezza frase e ti toglie di mezzo l'unica
obiezione che potrebbe farti perdere la sala a metà. Il caso è finto; **le meccaniche che
vedete sono quelle vere**, prese dall'installazione di BMAD 6.6.0 che gira su un progetto
nostro.

---

## Slide 25 — Il caso

*Sullo schermo: tre numeri grandi. 2022, 41%, 70%.*

> Un giro completo su un caso piccolo. Sale riunioni: il dominio non ve lo devo spiegare.
>
> Ma attenzione a cosa **non** stiamo facendo. Non costruiamo un sistema di prenotazione: le
> sale si prenotano su Outlook, come da voi. Quello che esiste è un servizio interno, in
> piedi dal 2022, che legge i calendari delle sale e accende i display fuori dalle porte.
> Quattro anni, due sviluppatori part-time, nessuno degli autori originali ancora in azienda.
>
> Adesso arriva una richiesta: se nessuno fa check-in entro dieci minuti, la sala torna
> libera. **Una feature nuova su un codice che c'è già.** Che è la situazione in cui siete
> tutti lunedì mattina.
>
> Il quarantuno per cento è il numero che giustifica il progetto: tre giri a piedi, in giorni
> diversi, quattro sale su dieci risultavano prenotate e dentro non c'era nessuno.
>
> Il settanta per cento è quello che ve lo farà ricordare. Tenetelo lì.
>
> Il caso è ricostruito, non è il log di una sessione vera. Le meccaniche che vedete — i
> menu, la review, gli stati — sono quelle vere, prese da un'installazione che gira su un
> progetto nostro.

**Non spiegare adesso perché conta il 70%.** Si paga da solo alla 31, e se lo anticipi qui
la 31 diventa una conferma invece che una scoperta.

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

*Sullo schermo: il bivio in mezzo, e in fondo il blocco verde oliva col 70%.*

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
> Ma la riga che conta è l'ultima, ed è un dato che nessuno stava cercando. Il settanta per
> cento delle prenotazioni sono occorrenze di serie ricorrenti. Non stiamo liberando
> riunioni: stiamo liberando **ripetizioni di riunioni** decise una volta e mai più guardate.
>
> Tenetevelo lì.

---

## Slide 28 — La decisione sale nel contesto

*Sullo schermo: la rimozione in alto, poi party mode con due agenti, e in fondo il riquadro
verde con la riga scritta in `project-context.md`.*

**Due gesti in una slide.** Il primo è la correzione scritta; il secondo è la stessa
correzione promossa a regola di tutti. Non correre sul secondo: è quello che torna alla 32.

> Il brief è pronto, e dentro ci sono due leve per far scendere il numero: una mail al
> responsabile dopo tre no-show, e una classifica dei reparti. I dati ci sono, la logica
> funziona.
>
> Le dico di toglierle. Le toglie. Poi mi chiede perché — e dice anche perché me lo chiede:
> *se la ragione resta qui dentro, al prossimo giro te le ripropongo.*
>
> Glielo spiego: qui una misura pubblica sul singolo diventa una gara al contrario, e la
> gente smette di prenotare col proprio nome. Prenotano col nome del collega, o non prenotano
> e occupano la sala lo stesso. **Il primo effetto è che il quarantuno per cento smette di
> essere misurabile** — cioè perdiamo il numero su cui si regge tutto il progetto.
>
> Se lo scrive nel brief, col motivo per esteso.
>
> E poi fa una cosa che non le ho chiesto: mi dice che il motivo l'ha scritto **nel brief**,
> e che il brief descrive *questo* prodotto. Il prossimo documento non lo legge.
>
> Allora apro party mode e mi tiro dentro John e Winston insieme. Party mode non è una trovata
> scenica: ognuno dei due è un subagente vero, con la sua testa. Se sono d'accordo, sono
> d'accordo davvero.
>
> John decide il posto: `project-context.md`. Winston ci vede subito il riflesso tecnico —
> niente endpoint che espone il conteggio per persona — e se lo porta in architettura.
>
> *[indica la riga grigia in fondo]*
>
> E adesso il pezzo che vale la slide. `project-context.md` non è un file qualsiasi: sta nei
> **persistent\_facts** di ogni agente, riga letterale, con la glob. Ogni agente lo carica
> all'attivazione, sempre.
>
> Quella regola dalla prossima attivazione ce l'hanno tutti, senza che io la ripeta. Anche
> Amelia, che quando l'abbiamo decisa non era nemmeno nella stanza.
>
> **Tenetela a mente, perché torna alla fine della demo da sola.**

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
> *[indica l'ultima riga, e leggila piano]*
>
> Nel database c'è una colonna con l'identificativo dell'evento Outlook. Per le riunioni
> **ricorrenti**, quella colonna non contiene l'id di quella riunione: contiene l'id della
> **serie**.
>
> Sta scritto nell'architettura del progetto. E non sta nei criteri di accettazione di questa
> story.
>
> Tenetela lì, serve fra due slide.

**Se qualcuno chiede perché non è un criterio di accettazione**, la risposta è buona e vale
la pena darla: perché non è un requisito. Un AC descrive cosa deve fare la story per essere
accettata, e nessuno ha chiesto niente sulle serie. Un AC che dice «non cancellare la serie»
sarebbe un vincolo di progetto travestito da requisito.

---

## Slide 30 — Cosa resta dopo

*Sullo schermo: l'esecuzione di `DS`, il riuso del client, la definition of done, il
passaggio di stato in fondo.*

> `DS`, dev-story. Implementa un task per volta. Guardate come parla: task, criterio, file,
> riga, test. Ogni riga è verificabile. Non è uno stile: nella configurazione dell'agente c'è
> scritto che deve parlare in percorsi di file e identificativi di criteri.
>
> *[indica il task 5, senza calcare]*
>
> Guardate il task cinque, perché fra due slide ci torniamo. Per rilasciare la sala su Outlook
> **riusa un metodo che c'era già**, e lo fa perché glielo dice l'architettura: verso Graph si
> passa da quel client, e il client si riusa invece di riscriverlo.
>
> È la cosa giusta da fare. È quello che avreste fatto anche voi.
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
> a muoverlo è il workflow. Backlog, pronta per lo sviluppo, in lavorazione, in review,
> chiusa.

**Il riuso del client va letto, non sottolineato.** Se lo annunci come indizio, il pubblico
capisce che c'è una trappola e la 31 perde la sorpresa. Leggilo come una cosa normale, perché
lo è.

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
> andato a leggersi l'architettura e ha trovato cosa c'è dentro quel campo. Per le riunioni
> ricorrenti non è l'id di quella riunione: è l'id della serie.
>
> Quindi quella riga non libera una sala. **Cancella la serie intera.** Tutte le occorrenze,
> passate e future, dal calendario di tutti i partecipanti. E su Exchange non si annulla.
>
> Le ricorrenti sono il settanta per cento delle prenotazioni di questo sistema.

*Fermati. Hanno appena visto due risposte opposte, e hanno ragione tutte e due.*

**Se vuoi il colpo in più**, aggiungi una frase sola: *il martedì mattina allo stand-up non
si presenta nessuno, perché l'invito non c'è più nel calendario di nessuno.* È il momento in
cui anche chi non scrive codice sente il danno.

---

## Slide 32 — Corretto per la story, sbagliato per il progetto

*Sullo schermo: il triage in quattro caselle, il diff, la domanda, e il decision-needed.*

> Il triage non è una lista di gravità: sono quattro caselle. Una cosa da decidere insieme a
> me, due patch, una rinviata perché c'era già prima, tre buttate come rumore. E finiscono
> scritte dentro la story, con file e riga, non in una chat.
>
> Domanda: i test erano verdi e l'hai scritta tu. Come ti è sfuggito?
>
> La risposta è la cosa più istruttiva della giornata, e non è quella che vi aspettate.
>
> Stava verificando il criterio tre — «la sala non risulta più occupata su Outlook» — e la
> cancellazione lo ottiene. E il metodo l'ha riusato perché glielo dice l'architettura: verso
> Graph si passa da quel client, e il client si riusa invece di riscriverlo.
>
> **Ha letto il capitolo giusto e ha fatto la cosa giusta.** Quello che non ha letto è un
> altro capitolo dello stesso documento, sessanta righe più su.
>
> I test erano verdi onestamente: in tutta la suite non esiste una prenotazione ricorrente.
> Le fixture sono del 2022, quando le serie non c'erano ancora.
>
> *[indica la riga evidenziata]*
>
> Corretto rispetto ai criteri di accettazione. Sbagliato rispetto al progetto. E notate cosa
> **non** è successo: nessuno ha scritto codice sciatto.
>
> *[indica il decision-needed]*
>
> Ultima cosa, ed è quella che mi ha sorpreso di più. Per rilasciare la sala, Outlook manda
> una notifica all'organizzatore. E la review si ferma — perché quella notifica sfiora la
> regola che abbiamo scritto insieme quattro slide fa.
>
> Non la decide da sola. Aspetta me.
>
> Quella regola l'ho scritta io, venti minuti prima, in una conversazione su tutt'altro. Ed è
> quella che adesso ferma lo strumento.

**Se qualcuno chiede cosa succede senza la review**: il bug esce il martedì mattina, e la
segnalazione arriva come «Outlook ha cancellato le riunioni». Nessuno lo collega a un job
schedulato che gira di notte su un'altra funzionalità.

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
> Riusare un metodo che c'è già senza cercare perché era stato scritto così. L'avete appena
> visto: riusare era la cosa giusta, non cercare il perché no.
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

Il taglio da non fare mai è la 32: senza quella, la 31 resta un bug trovato per fortuna.

E **nella versione da 3 minuti la 29 resta**, anche se sembra la più sacrificabile: è quella
che pianta il vincolo sulle occorrenze. Senza, la 31 non ha niente da raccogliere e il
pubblico deve fidarsi sulla parola.

Nella 29 il pezzo che si comprime è il ponte, non la story. A 3 minuti diventa una riga sola
— «in mezzo John ha fatto le epiche e Winston l'architettura, con i loro checkpoint» — ma
`architecture.md` va nominato comunque.

**La 28 è la prima a cadere sotto i 5 minuti**, ed è una perdita vera: perdi la prova (la
riga nei `persistent_facts`), il ponte verso il capitolo 04, e il richiamo del
decision-needed alla 32. Se la tagli, alla 32 di' solo «si ferma su una regola di prodotto»
senza spiegare da dove viene, altrimenti apri una parentesi che non chiudi.

Se salti la 30, il riuso del client va detto a voce entrando nella 31: senza, la risposta
di Amelia alla 32 non ha appiglio.

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
prenotazione ricorrente. Le fixture sono del 2022, le serie sono arrivate nel 2024, e nessuno
è tornato indietro a guardarle. È il genere di buco che c'è in ogni codebase con qualche anno
addosso — e infatti la review lo classifica come `defer`, cioè «vero, ma non l'hai rotto tu».

**«Quanto costa in token?»**
Più di una chat secca: i passaggi sono più d'uno, ogni agente si carica il contesto, e la
review ne fa partire tre in parallelo. Il paragone onesto non è con la chat: è con il costo
di trovare quel bug in produzione.

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
- **Non dire «l'agente ha sbagliato».** Non ha sbagliato: ha riusato codice esistente perché
  il progetto gli dice di riusarlo, e il codice riusato faceva quello che l'AC chiedeva. Se
  lo presenti come un errore del tool, il pubblico conclude «basta un modello migliore» e hai
  perso la tesi.
- **Non promettere numeri di produttività.** Non ne hai, e in sala c'è sempre qualcuno che
  quei numeri li ha visti smontare.
- **Non aprire la story 1.3.** Si ferma su una domanda aperta — a chi mandare l'avviso, se
  l'organizzatore della serie l'ha creata tre anni fa. È interessante ma è un'altra
  conversazione, e ti mangia tre minuti.
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
e il vincolo che regge il finale sta in `planning-artifacts/architecture.md` § E4.

Se qualcuno in sala vuole vedere i file veri dopo il talk, sono navigabili così come sono.
