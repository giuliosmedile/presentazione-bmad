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

> Un giro completo su un caso piccolo. Sale riunioni: il dominio non ve lo devo spiegare.
>
> Sei sale. Da mesi la lamentela numero uno è che non se ne trova una. Guardi il calendario:
> pieno. Passi davanti alle sale: vuote.
>
> Il numero che regge tutto è il quarantuno per cento. Tre giri a piedi, in giorni diversi:
> quattro sale su dieci risultavano prenotate e dentro non c'era nessuno.
>
> L'ipotesi: se liberare diventa automatico invece che volontario, la capacità cresce senza
> costruire niente. Niente sensori, niente hardware, niente budget.
>
> Il caso è ricostruito, non è il log di una sessione vera. Le meccaniche che vedete — i
> menu, la review, gli stati — sono quelle vere, prese da un'installazione che gira su un
> progetto nostro.

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

---

## Slide 27 — Il checkpoint umano

*Sullo schermo: il bivio in alto, poi due blocchi evidenziati, uno arancione e uno verde oliva.*

Venti secondi in più qui.

> Le chiedo di sistemare il problema delle sale. Non parte a produrre: separa due ipotesi.
> O le sale sono poche — problema di muri e di budget, il software non c'entra. O risultano
> occupate quando non lo sono — problema di comportamento. Sono due prodotti diversi. Se
> costruisci per il secondo e la verità era il primo, hai buttato tre mesi. Quindi prima i
> dati.
>
> *[Fate la stessa richiesta a una chat qualsiasi stasera. Vi scrive il codice. Non vi chiede
> se il problema esiste.]*
>
> Nel brief aveva messo una classifica dei reparti che prenotano e non disdicono. Il dato
> c'era ed era corretto.
>
> Le dico di toglierla. La toglie. Poi mi chiede perché — e dice anche perché me lo chiede:
> *se la ragione resta qui dentro, al prossimo giro te la ripropongo.*
>
> Glielo spiego: qui una classifica pubblica diventa una gara, e la gente smette di usare lo
> strumento pur di non finirci sopra.
>
> Se lo scrive. Nel documento, col motivo per esteso.
>
> Ultima riga. Non le mancava un dato: il dato ce l'aveva, la logica funzionava. Le mancava
> di sapere come funziona questa azienda. Quello glielo potevo dare solo io.

**Se hai due secondi**, aggancia la slide sulla memoria: stesso discorso, visto in funzione.

---

## Slide 28 — La story è un contratto

*Sullo schermo: la story appena creata, e l'ultima riga evidenziata.*

**Non leggere la story.** Indichi la struttura, poi ti fermi sull'ultima riga.

> `CS`, create-story. Amelia legge l'architettura e il contesto di progetto, e scrive la
> story. In un file, non a voce.
>
> Non è un ticket con due righe. Criteri di accettazione numerati, e ogni task dice quale
> criterio soddisfa. Le note tecniche citano la fonte: percorso del file e sezione.
>
> *[indica l'ultima riga]*
>
> Gli stati di una prenotazione sono tre: attiva, disdetta, `no_show`. Sta scritto
> nell'architettura del progetto. E `no_show` arriva nella story dopo.
>
> Sta nel progetto. Non sta nei criteri di accettazione di questa story.
>
> Tenetela lì, serve fra due slide.

---

## Slide 29 — Cosa resta dopo

*Sullo schermo: l'esecuzione di `DS`, la definition of done, il passaggio di stato in fondo.*

> `DS`, dev-story. Implementa un task per volta. Guardate come parla: task, criterio, file,
> riga, test. Ogni riga è verificabile. Non è uno stile: nella configurazione dell'agente c'è
> scritto che deve parlare in percorsi di file e identificativi di criteri.
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

---

## Slide 30 — Tre reviewer, tre contesti

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
> andato a leggersi l'architettura. Quel filtro enumera per esclusione. Gli stati sono tre,
> non due. Una prenotazione in no-show passa il filtro e continua a occupare la sala.
>
> La sala che il sistema libera da solo continuava a risultare occupata. La funzione per cui
> esiste il progetto. Nella query principale.

*Fermati. Hanno appena visto due risposte opposte, e hanno ragione tutte e due.*

---

## Slide 31 — Corretto per la story, sbagliato per il progetto

*Sullo schermo: il triage in quattro caselle, il diff, e la domanda.*

> Il triage non è una lista di gravità: sono quattro caselle. Una cosa da decidere insieme a
> me, due patch, una rinviata perché c'era già prima, tre buttate come rumore. E finiscono
> scritte dentro la story, con file e riga, non in una chat.
>
> Domanda: i test erano verdi e l'hai scritta tu. Come ti è sfuggito?
>
> Stava verificando il criterio uno — «mostra solo le sale libere» — e rispetto a quello il
> predicato è giusto. I test di questa story non creano prenotazioni in no-show, perché
> quello stato nasce nella story dopo. I due filtri danno lo stesso risultato.
>
> *[indica la riga evidenziata]*
>
> Corretto rispetto ai criteri di accettazione. Sbagliato rispetto al progetto.
>
> Senza la review: il bug usciva nella story dopo, come «la liberazione non funziona».
> Saremmo andati a cercarlo nel job, che era corretto. Il difetto stava in una query della
> story precedente, già approvata e chiusa da giorni. Un giorno, due, per trovarlo.
>
> Le patch diventano tre test. Da lì in avanti quel difetto non rientra più di nascosto.

---

## Slide 32 — Pitfall e strategie

*Sullo schermo: due colonne, rossa e verde.*

> Fotografatela, è quella che vi serve lunedì.
>
> A sinistra i modi di buttare via il framework. Ve ne dico tre.
>
> Scrivere il tema invece di scegliere la voce di menu: il contesto torna largo, e siete
> tornati al punto da cui eravamo partiti.
>
> Approvare senza leggere: il checkpoint diventa un timbro. Peggio che non averlo, perché vi
> dà l'impressione di aver controllato.
>
> Fermarsi al verdetto sui criteri di accettazione. «Spec soddisfatta» non vuol dire «codice
> giusto», e avete appena visto perché.
>
> A destra le contromisure. Se ne prendete una sola, la terza: ogni correzione diventa una
> riga scritta, col motivo. È quella che trasforma il tempo che ci mettete in memoria del
> progetto invece che in tempo perso.

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
