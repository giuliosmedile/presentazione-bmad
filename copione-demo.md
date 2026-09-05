# Copione della demo — SaleRiunioni

Slide **25-32** del deck (quelle che sul contatore in basso a destra fanno 25/40 → 32/40).
Otto slide, otto minuti scarsi.

---

## Cosa stai mostrando, in breve

Un progetto finto ma coerente: prenotazione delle sale riunioni interne. L'ho scelto piccolo
apposta — il dominio non va spiegato, in sala lo capiscono prima che tu finisca la frase, e
così tutto il tempo se lo prende il processo invece del contesto.

**La demo ha due metà, e servono a due cose diverse.**

La prima metà (slide 26-29) risponde alla domanda pratica che si stanno facendo tutti:
*ma come ci si parla, a 'sta roba?* Vedono il menu, vedono che si risponde con due lettere,
vedono l'agente che rimanda indietro una domanda invece di partire a produrre, e vedono
dove finisce il lavoro quando chiudi il portatile.

La seconda metà (slide 30-31) è il motivo per cui la demo esiste. È il processo che becca
un bug che nessun test avrebbe preso. Fin lì hai raccontato un metodo ordinato; da lì
dimostri che l'ordine serve a qualcosa.

L'ultima (32) è il riassunto operativo: cosa non fare, cosa fare.

**Se ti resta poco tempo taglia la prima metà, mai la seconda.** Le slide 26-29 le racconti
a voce in un minuto. La 30 e la 31 no: lì devono leggere il diff.

---

## Prima di partire

Una cosa da tenere a mente per tutta la demo: **non stai facendo vedere un tool che scrive
codice**. Quello lo fanno già tutti. Stai facendo vedere un processo che si accorge di
qualcosa. Ogni volta che hai un dubbio su cosa dire, torna lì.

E dillo subito che il caso è ricostruito. Costa mezza frase e ti toglie di mezzo l'unica
obiezione che potrebbe farti perdere la sala a metà.

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
> Una precisazione così ve la tolgo subito: questo è un caso ricostruito su un progetto di
> esempio, non è il log di una sessione vera. I documenti però sono coerenti fra loro — se
> andate a incrociare i numeri fra un file e l'altro, tornano.

---

## Slide 26 — Il menu e i codici

*Sullo schermo: il terminale con l'attivazione di Mary e il menu numerato.*

> Si parte da un comando: `/bmad-agent-analyst` e Mary è attiva. Non è un tono di voce,
> è una riga che scrivo io.
>
> E la prima cosa che succede dopo non è che lui aspetta un tema. Si presenta e mi dà
> un menu.
>
> Guardate le sigle nella seconda colonna. BP, MR, DR, TR. Sono codici. Io rispondo `MR`
> e basta. Due lettere.
>
> Sembra una comodità e invece è il punto. Quando rispondo MR, quell'agente in quel momento
> sta facendo una cosa sola — ricerca di mercato — e non sta pensando a nient'altro. Il
> contesto resta stretto per costruzione, non perché mi sono ricordato di tenerlo stretto io.
>
> E questi codici non me li sono inventati per la slide. Stanno in un file di configurazione
> dentro il repository. Se qualcuno di voi ha BMAD installato e va a controllare, li trova
> uguali.

---

## Slide 27 — Contestare la premessa

*Sullo schermo: la domanda evidenziata in arancione e il bivio a due voci.*

> Qui però succede la cosa che non mi aspettavo.
>
> Io le ho chiesto di sistemare il problema delle sale. Lei, prima di propormi qualunque
> cosa, mi chiede se quel problema qualcuno l'ha misurato.
>
> E poi apre il bivio, che secondo me è il momento più utile di tutta la fase di analisi.
> Dietro «non trovo una sala» ci sono due storie diverse. O le sale sono poche, e allora è
> un problema di muri e di budget e il software non c'entra niente. Oppure le sale ci sono
> ma risultano occupate quando non lo sono, e lì invece si può fare qualcosa.
>
> Sono due prodotti diversi. Se costruisci per il secondo e la verità era il primo, hai
> buttato tre mesi.
>
> Fate la stessa identica richiesta a una chat qualsiasi, stasera. Vi scrive il codice.
> Non vi chiede se il problema esiste.

---

## Slide 28 — Il checkpoint umano

*Sullo schermo: due blocchi evidenziati, uno arancione e uno verde oliva.*

Questa è quella su cui vale la pena stare venti secondi in più.

> Lei aveva messo nel brief una classifica dei reparti che prenotano e non disdicono. Il
> dato c'era ed era corretto.
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
> Poi c'è l'ultima riga, quella verde, che per me vale da sola tutta la slide. Non le
> mancava un dato: il dato ce l'aveva, la logica funzionava. Le mancava di sapere come
> funziona questa azienda.
>
> Ecco. Quella roba lì gliela potevo dare solo io.

**Se hai due secondi**, aggancia qui la slide di prima sulla memoria: è lo stesso discorso,
visto in funzione.

---

## Slide 29 — Cosa resta dopo

*Sullo schermo: l'albero dei file di `_bmad-output/`.*

> Domanda pratica. Finita la giornata, chiudo il portatile: cosa mi resta in mano?
>
> Questo. File.
>
> Non una chat da riaprire e riscorrere cercando dove avevamo deciso una cosa, ma roba
> nella cartella del progetto. Che si versiona, si diffa e passa in review come qualunque
> altro file.
>
> Il primo in alto, `project-context`, è quello che ogni agente si carica quando parte.
> È lì che vive la memoria di cui parlavamo dieci minuti fa.
>
> Poi guardate le due righe annotate. Il `product-brief` non porta solo l'ipotesi: porta
> anche le alternative che abbiamo scartato. E `architecture` non contiene solo cosa
> abbiamo deciso: contiene il perché. Che di solito è la parte che si perde per prima.

---

## Slide 30 — La code review

*Sullo schermo: il diff. Due righe, una rossa e una verde.*

Da qui in avanti rallenta. È il pezzo per cui sono rimasti.

> E adesso il motivo per cui vi ho tenuto qui.
>
> Story finita, codice scritto, test tutti verdi. Faccio partire la code review — e la
> faccio partire in una **sessione nuova**, che è un dettaglio che vi torna fra un minuto.
>
> Trova tre cose. Vi faccio vedere quella di mezzo.
>
> Nella query della ricerca c'era scritto: *prendi le prenotazioni che non sono disdette*.
> Doveva esserci: *prendi le prenotazioni attive*.
>
> Due parole. Sembra la stessa cosa.
>
> Non lo è. Perché una prenotazione decaduta per no-show non è una prenotazione disdetta.
> Quindi con quel filtro lì continua a occupare la sala.
>
> Tradotto: la sala che il sistema libera da solo continuava a risultare occupata. La
> funzione per cui esiste tutto il progetto. Nella query principale.

*Fermati un attimo qui. Lascia che ci arrivino.*

---

## Slide 31 — Perché il checkpoint esiste

*Sullo schermo: la domanda «come ti è sfuggito?» e la risposta.*

> A quel punto gli faccio la domanda cattiva: come ti è sfuggito, visto che l'hai scritto tu?
>
> E la risposta è la cosa più istruttiva di tutta la giornata.
>
> Mentre implementava stava verificando i criteri di accettazione della story. E rispetto a
> quelli il codice era giusto. Nel database di test non esistevano prenotazioni in no-show,
> perché quello stato arriva nella story dopo. I due filtri davano lo stesso identico
> risultato.
>
> Corretto per la story. Sbagliato per il progetto.
>
> E nessun test lo avrebbe beccato. Non perché i test fossero scritti male: erano scritti
> bene, per quella story.
>
> Ultima domanda: e se non l'avessimo fatta, la review? Il bug sarebbe uscito nella story
> dopo, sotto forma di «la liberazione non funziona». E saremmo andati a cercarlo nel job.
> Che era corretto. Il difetto stava in una query della story precedente, già approvata e
> chiusa da giorni.
>
> Quanto ci mettevamo a trovarlo? Non lo so. Un giorno, due.
>
> Le tre correzioni poi sono diventate anche due test nuovi. Quindi da lì in avanti quel
> difetto non può più rientrare di nascosto.

---

## Slide 32 — Pitfall e strategie

*Sullo schermo: due colonne, rossa e verde.*

> Ultima e poi andiamo avanti. Questa fotografatela, perché è quella che vi serve lunedì.
>
> A sinistra i modi per buttare via il framework. Ve ne dico due.
>
> Il primo: scrivere il tema invece di scegliere la voce di menu. Se fate così vi ritrovate
> di nuovo il contesto largo, e siete tornati esattamente al punto da cui eravamo partiti.
>
> Il secondo lo vedo più spesso di tutti: approvare senza leggere. Il checkpoint diventa un
> timbro, e a quel punto tanto vale non averlo — anzi, è peggio, perché vi dà l'impressione
> di aver controllato.
>
> E il quarto lo avete appena visto in funzione: far rileggere il codice alla stessa
> sessione che l'ha scritto. Il bug di prima è uscito perché la review girava altrove.
>
> A destra le contromisure. Se ne prendete una sola, prendete la terza: ogni correzione
> diventa una riga scritta, col motivo. È quella che trasforma il tempo che ci mettete in
> memoria del progetto, invece che in tempo perso.

---

## Se sei in ritardo

| Tempo | Cosa fai |
|---|---|
| 8 min | tutto |
| 5 min | salti la 26 e la 28, le racconti a voce mentre passi |
| 3 min | **solo 30, 31, 32**. Il caso lo riassumi in una frase entrando |

Il taglio da non fare mai è la 31. Senza quella, la 30 resta un bug trovato per fortuna.

---

## Le domande che arrivano

**«Ma è una sessione vera?»**
No, è ricostruita su un progetto di esempio. I documenti sono coerenti fra loro, i codici di
menu sono quelli veri. Dillo senza giri di parole: la credibilità te la dà la coerenza, non
il fingere.

**«Quanto costa in token?»**
Più di una chat secca, perché i passaggi sono più d'uno e ogni agente si carica il contesto.
Il paragone onesto non è con la chat: è con il costo di trovare quel bug in produzione.

**«E il codice che esce dall'azienda?»**
Sappi in anticipo qual è la policy interna e cita quella. Se non c'è, dillo che non c'è: è
una risposta migliore di una rassicurazione inventata.

**«Quanto ci vuole a impararlo?»**
La parte dei menu, cinque minuti. La parte difficile non è il tool: è prendere l'abitudine di
scrivere il motivo quando correggi qualcosa.

**«E se sbaglia la review?»**
Succede. Per quello la review non sostituisce la tua: sposta la tua attenzione sui punti che
contano invece che su tutto il diff.

---

## Cose da non dire

- **Non dire che è una sessione reale.** Mai, nemmeno per sbaglio, nemmeno per fare colpo.
- **Non promettere numeri di produttività.** Non ne hai, e in sala c'è sempre qualcuno che
  quei numeri li ha visti smontare.
- **Non aprire la story 1.2.** Si ferma su una domanda aperta — il job che partirebbe su
  ogni replica in cluster. È interessante ma è un'altra conversazione, e ti mangia tre minuti.
- **Non dire «l'AI ha trovato il bug».** L'ha trovato una rilettura fatta con un contesto
  diverso. È una differenza che questo pubblico coglie, e ti fa guadagnare credito.

---

## Il materiale

Tutto quello che c'è dietro sta in [`demo-sale-riunioni/`](demo-sale-riunioni/), che ha un
README suo con la struttura completa. Le due chat da cui viene questa demo sono
`chat/01-mary-analyst.md` e `chat/09-amelia-code-review.md`; il diff mostrato nella slide 30
è `progetto/_bmad-output/implementation-artifacts/review-1.1.diff`.

Se qualcuno in sala vuole vedere i file veri dopo il talk, sono navigabili così come sono.
