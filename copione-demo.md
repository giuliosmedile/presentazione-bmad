# Copione della demo — SaleRiunioni

Slide **25-32** del deck (quelle che sul contatore in basso a destra fanno 25/40 → 32/40).
Otto slide, otto minuti scarsi.

---

## Cosa stai mostrando, in breve

Un progetto finto ma coerente: prenotazione delle sale riunioni interne. L'ho scelto piccolo
apposta — il dominio non va spiegato, in sala lo capiscono prima che tu finisca la frase, e
così tutto il tempo se lo prende il processo invece del contesto.

**La demo percorre il loop della slide 24 invece di raccontarlo.** Otto slide, e sono in
ordine di tempo: parli a un agente, produci una story, la implementi, la fai rivedere.

| Slide | Cosa succede | Chi |
|---|---|---|
| 25 | il caso | — |
| 26 | attivazione e menu | Mary |
| 27 | il checkpoint umano | Mary |
| 28 | la story nasce | Amelia `CS` |
| 29 | la story viene implementata | Amelia `DS` |
| 30 | tre reviewer, tre contesti | Amelia `CR` |
| 31 | il verdetto contraddittorio | Amelia `CR` |
| 32 | pitfall e strategie | — |

**La demo ha due metà, e servono a due cose diverse.**

La prima (26-29) risponde alla domanda pratica che si stanno facendo tutti: *ma come ci si
parla, a 'sta roba, e cosa mi resta in mano dopo?* Vedono il menu, vedono un agente che
rimanda indietro una domanda invece di partire a produrre, vedono nascere una story e vedono
dove finisce il lavoro quando chiudi il portatile.

La seconda (30-31) è il motivo per cui la demo esiste. È il processo che becca un bug che
nessun test avrebbe preso. Fin lì hai raccontato un metodo ordinato; da lì dimostri che
l'ordine serve a qualcosa.

**Se ti resta poco tempo taglia dalla prima metà, mai dalla seconda.** Ma la 28 non tagliarla:
è quella che pianta il vincolo che la 30 raccoglie. Senza, la 30 è un colpo di fortuna.

---

## Prima di partire

Una cosa da tenere a mente per tutta la demo: **non stai facendo vedere un tool che scrive
codice**. Quello lo fanno già tutti. Stai facendo vedere un processo che si accorge di
qualcosa. Ogni volta che hai un dubbio su cosa dire, torna lì.

E dillo subito che il caso è ricostruito. Costa mezza frase e ti toglie di mezzo l'unica
obiezione che potrebbe farti perdere la sala a metà. Il caso è finto; **le meccaniche che
vedete sono quelle vere**, prese dall'installazione di BMAD 6.6.0 che gira su un progetto
nostro. Questa distinzione dilla, perché è quella che ti tiene la credibilità.

---

## Slide 25 — Il caso

*Sullo schermo: tre numeri grandi. 612 prenotazioni, 41%, 38 ticket.*

> Vi faccio vedere un giro completo su un caso piccolo. Piccolo apposta, così non devo
> spiegarvi il dominio: sale riunioni.
>
> Sei sale. La lamentela numero uno da mesi è che non se ne trova mai una libera.
> Guardi il calendario ed è pieno. Passi davanti alle sale e sono vuote.
>
> Il numero in mezzo è quello che regge tutto il resto. Quarantuno per cento. Tre giri
> fatti a piedi, in giorni diversi: quattro sale su dieci risultavano prenotate e dentro
> non c'era nessuno.
>
> Da lì l'idea. Se liberare una sala smette di essere una cosa che ti devi ricordare di
> fare e diventa automatica, la capacità cresce senza costruire niente. Niente sensori,
> niente hardware, niente budget.
>
> Una precisazione così ve la tolgo subito: il caso è ricostruito su un progetto di esempio,
> non è il log di una sessione vera. Ma le meccaniche che vedete — i menu, la review, gli
> stati — sono quelle vere, prese da un'installazione che gira su un progetto nostro.

---

## Slide 26 — Attivazione e menu

*Sullo schermo: il terminale con l'attivazione di Mary e il menu a tre colonne.*

> Prima cosa: si attiva un agente. Non è un tono di voce, è una riga che scrivo io.
> `bmad-agent-analyst`, e Mary è in linea.
>
> E la prima cosa che fa non è aspettare che le scriva un tema. Si presenta e apre un menu.
>
> Adesso guardate la **terza colonna**, perché è quella che spiega il meccanismo e di solito
> non ve la fa vedere nessuno. Ogni voce non è un'opzione di conversazione: è uno **skill**
> che parte. Rispondo `CB` e si attiva `bmad-product-brief`, che è un pezzo di software con
> i suoi passi, i suoi file di contesto, le sue regole.
>
> Il vantaggio quindi non è la comodità delle due lettere. È che il contesto resta stretto
> **per costruzione**, non perché mi sono ricordato io di tenerlo stretto.
>
> E questi non me li sono inventati per la slide. Codici e nomi degli skill stanno in un file
> di configurazione dentro il repository. Se qualcuno di voi ha BMAD installato e va a
> controllare, li trova uguali.

---

## Slide 27 — Il checkpoint umano

*Sullo schermo: il bivio in alto, poi due blocchi evidenziati, uno arancione e uno verde oliva.*

Questa è quella su cui vale la pena stare venti secondi in più.

> Prima parte, veloce: le chiedo di sistemare il problema delle sale e lei **non parte a
> produrre**. Separa due ipotesi. O le sale sono poche, e allora è un problema di muri e di
> budget e il software non c'entra niente. Oppure risultano occupate quando non lo sono, e
> lì si può fare qualcosa. Sono due prodotti diversi: se costruisci per il secondo e la
> verità era il primo, hai buttato tre mesi. Quindi prima i dati.
>
> *[fai la stessa richiesta a una chat qualsiasi stasera: vi scrive il codice, non vi chiede
> se il problema esiste]*
>
> Adesso la parte che vale la slide. Lei aveva messo nel brief una classifica dei reparti che
> prenotano e non disdicono. Il dato c'era ed era corretto.
>
> Io le dico di toglierla. E lei la toglie. Ma poi mi chiede perché.
>
> E guardate che vi dice anche il motivo per cui me lo chiede: *se la ragione resta qui
> dentro, al prossimo giro te la ripropongo.*
>
> Allora glielo spiego. Qui dentro una classifica pubblica diventa una gara, e la gente
> smette di usare lo strumento pur di non finirci sopra.
>
> E lei se lo scrive. Nel documento, col motivo per esteso.
>
> Poi c'è l'ultima riga, quella verde, che per me vale da sola tutta la slide. Non le mancava
> un dato: il dato ce l'aveva, la logica funzionava. Le mancava di sapere come funziona
> questa azienda.
>
> Ecco. Quella roba lì gliela potevo dare solo io.

**Se hai due secondi**, aggancia qui la slide sulla memoria: è lo stesso discorso, visto in
funzione.

---

## Slide 28 — La story è un contratto

*Sullo schermo: la story appena creata, e l'ultima riga evidenziata.*

**Non leggere la story.** Indichi la struttura, e poi ti fermi sull'ultima riga.

> Passiamo alla parte di implementazione. `CS`, create-story: Amelia legge l'architettura e
> il contesto di progetto, e scrive la story. Non me la racconta: la scrive in un file.
>
> E non è un ticket con due righe. Guardate la forma. Criteri di accettazione numerati. E
> ogni task, sotto, dice **quale criterio soddisfa**. Le note tecniche citano la fonte, col
> percorso del file e la sezione.
>
> Adesso vi chiedo una cosa sola, ed è l'unica di questa slide.
>
> *[indica l'ultima riga]*
>
> Gli stati di una prenotazione sono tre: attiva, disdetta, e no_show. Sta scritto
> nell'architettura del progetto. E `no_show` arriva nella story **dopo**.
>
> Quindi: sta nel progetto, non sta nei criteri di accettazione di questa story.
>
> Tenetela lì. Fra due slide serve.

---

## Slide 29 — Cosa resta dopo

*Sullo schermo: l'esecuzione di `DS`, la definition of done, il passaggio di stato in fondo.*

> `DS`, dev-story. Implementa un task per volta, e notate come parla: task, criterio, file,
> riga, test. Ogni riga è verificabile. Non è uno stile: sta scritto nella configurazione
> dell'agente che deve parlare in percorsi di file e identificativi di criteri.
>
> Alla fine passa una definition of done che non può saltare. Non è lei che decide quando ha
> finito: è una lista che deve spuntare.
>
> Ma la domanda vera è quella pratica. Finita la giornata, chiudo il portatile: cosa mi resta
> in mano?
>
> Questo. Non una chat da riaprire e riscorrere cercando dove avevamo deciso una cosa. Il
> diario di **come** è stata implementata sta dentro il file della story: il modello usato,
> le note, i file toccati, il change log. Si versiona, si diffa, passa in review come
> qualunque altro file.
>
> E l'ultima riga: lo stato non è nella mia testa né nella vostra. Sta in un file,
> `sprint-status.yaml`, e a muoverlo è il workflow. Backlog, pronta per lo sviluppo, in
> lavorazione, in review, chiusa.

---

## Slide 30 — Tre reviewer, tre contesti

*Sullo schermo: i tre reviewer, poi due blocchi evidenziati che dicono cose opposte.*

Da qui in avanti rallenta. È il pezzo per cui sono rimasti.

> E adesso il motivo per cui vi ho tenuto qui.
>
> Story finita, codice scritto, test tutti verdi. Faccio partire la code review, e la faccio
> partire in una **sessione nuova**.
>
> E qui BMAD fa una cosa che non mi aspettavo. Non manda un revisore. Ne manda **tre, in
> parallelo**, e gli dà apposta contesti diversi.
>
> *[indica le tre righe, una alla volta]*
>
> Il **Blind Hunter** vede solo il diff. Niente specifica, niente progetto, niente.
> L'**Edge Case Hunter** vede il diff e può leggere il progetto, ma la specifica non ce l'ha.
> L'**Acceptance Auditor** vede tutto: diff, story, documenti di contesto.
>
> Adesso guardate cosa tornano indietro.
>
> *[blocco verde]* L'Acceptance Auditor — quello che ha la specifica — dice: **spec
> soddisfatta**. Criteri verificati, task tutti spuntati, perimetro rispettato.
>
> *[blocco arancione]* L'Edge Case Hunter — quello che la specifica non ce l'ha, ma il
> progetto sì — è andato a leggersi l'architettura. E dice: quel filtro enumera per
> esclusione. Gli stati sono tre, non due. Una prenotazione in no-show passa il filtro e
> continua a occupare la sala.
>
> Cioè: la sala che il sistema libera da solo continuava a risultare occupata. La funzione
> per cui esiste tutto il progetto. Nella query principale.

*Fermati un attimo qui. Lascia che ci arrivino: hanno appena visto due risposte opposte, e
hanno ragione tutte e due.*

---

## Slide 31 — Corretto per la story, sbagliato per il progetto

*Sullo schermo: il triage in quattro caselle, il diff, e la domanda cattiva.*

> Prima cosa, veloce, ma dilla perché è quella che fa capire che è uno strumento serio e non
> un chatbot che commenta il codice. Il triage non è una lista di gravità: sono **quattro
> caselle**. Una cosa da decidere insieme a me, due patch, una rinviata perché c'era già
> prima, e tre buttate come rumore. E finiscono scritte **dentro la story**, col file e la
> riga, non in una chat.
>
> Poi la domanda cattiva: i test erano verdi e l'hai scritta tu. Come ti è sfuggito?
>
> E la risposta è la cosa più istruttiva di tutta la giornata.
>
> Stava verificando il criterio uno — «mostra solo le sale libere» — e rispetto a quello il
> predicato è giusto. I test di questa story non creano prenotazioni in no-show, perché
> quello stato nasce nella story dopo. Quindi i due filtri danno lo stesso identico risultato.
>
> *[indica la riga evidenziata]*
>
> Corretto rispetto ai criteri di accettazione. Sbagliato rispetto al progetto.
>
> E se non l'avessimo fatta, la review? Il bug sarebbe uscito nella story dopo, sotto forma
> di «la liberazione non funziona». E saremmo andati a cercarlo nel job. Che era corretto.
> Il difetto stava in una query della story precedente, già approvata e chiusa da giorni.
>
> Quanto ci mettevamo a trovarlo? Non lo so. Un giorno, due.
>
> Ultima cosa: le patch diventano tre test nuovi. Da lì in avanti quel difetto non può più
> rientrare di nascosto.

---

## Slide 32 — Pitfall e strategie

*Sullo schermo: due colonne, rossa e verde.*

> Ultima e poi andiamo avanti. Questa fotografatela, perché è quella che vi serve lunedì.
>
> A sinistra i modi per buttare via il framework. Ve ne dico tre.
>
> Il primo: scrivere il tema invece di scegliere la voce di menu. Se fate così vi ritrovate
> di nuovo il contesto largo, e siete tornati esattamente al punto da cui eravamo partiti.
>
> Il secondo lo vedo più spesso di tutti: approvare senza leggere. Il checkpoint diventa un
> timbro, e a quel punto tanto vale non averlo — anzi, è peggio, perché vi dà l'impressione
> di aver controllato.
>
> Il quinto lo avete appena visto in funzione: fermarsi al verdetto sui criteri di
> accettazione. «Spec soddisfatta» non vuol dire «codice giusto», e stasera avete visto
> perché.
>
> A destra le contromisure. Se ne prendete una sola, prendete la terza: ogni correzione
> diventa una riga scritta, col motivo. È quella che trasforma il tempo che ci mettete in
> memoria del progetto, invece che in tempo perso.

---

## Se sei in ritardo

| Tempo | Cosa fai |
|---|---|
| 8 min | tutto |
| 5 min | salti la 26 e la 29, le racconti a voce mentre passi |
| 3 min | **solo 28, 30, 31**. Il caso lo riassumi in una frase entrando |

Il taglio da non fare mai è la 31: senza quella, la 30 resta un bug trovato per fortuna.

E **nella versione da 3 minuti la 28 resta**, anche se sembra la più sacrificabile: è quella
che pianta `no_show` come vincolo di progetto. Senza, la 30 non ha niente da raccogliere e
il pubblico deve fidarsi sulla parola.

---

## Le domande che arrivano

**«Ma è una sessione vera?»**
No, il caso è ricostruito su un progetto di esempio. Ma le meccaniche sono quelle vere: i
codici di menu, gli skill, i tre reviewer, i quattro bucket del triage, gli stati della
story. Dillo senza giri di parole: la credibilità te la dà la coerenza, non il fingere.

**«Perché tre reviewer e non uno?»**
Perché è l'asimmetria che trova le cose. Se gli dai a tutti e tre lo stesso contesto,
trovano tutti e tre le stesse cose. Il Blind Hunter paga tre falsi positivi ed è lo stesso
che apre il finding grosso: non ha la specifica che gli dice che va tutto bene.

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
- **Non promettere numeri di produttività.** Non ne hai, e in sala c'è sempre qualcuno che
  quei numeri li ha visti smontare.
- **Non aprire la story 1.2.** Si ferma su una domanda aperta — il job che partirebbe su
  ogni replica in cluster. È interessante ma è un'altra conversazione, e ti mangia tre minuti.
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
`progetto/_bmad-output/implementation-artifacts/1-1-ricerca-e-prenotazione-di-una-sala.md`.

Se qualcuno in sala vuole vedere i file veri dopo il talk, sono navigabili così come sono.
