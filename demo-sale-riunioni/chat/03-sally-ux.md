# 🎨 Sally — UX Designer

> Fase di pianificazione. Dai requisiti alle schermate.
> Artefatto prodotto: `_bmad-output/planning-artifacts/ux-spec.md`

---

**giulio** — Sally, c'è il PRD delle sale riunioni.

🎨 Ciao giulio. Se a un certo punto non sai dove andare, `bmad-help` c'è.

| # | Cod | Descrizione | Skill che parte |
|---|---|---|---|
| 1 | `CU` | Guida alla realizzazione del piano UX, per informare architettura e implementazione | `bmad-create-ux-design` |


In attesa. Rispondi con un numero, un codice o una descrizione.

Un solo codice, quindi la scelta è facile. Ma prima di aprire qualunque wireframe,
raccontami una scena: **l'ultima volta che tu personalmente non hai trovato una
sala, cosa stavi facendo?**

---

**giulio** — Camminavo verso la riunione con il portatile sotto il braccio.
Mancavano due minuti.

🎨 Ecco. Tienila stretta questa immagine, perché è tutto il progetto.

Una mano sola libera. In piedi. Due minuti. Non stai «cercando una sala», stai
**risolvendo un'emergenza mentre cammini**. Chi progetta pensando all'utente
seduto alla scrivania che pianifica il giovedì costruisce una cosa che alle 10:58
non si usa.

Tutto quello che disegno adesso serve quel momento. Se funziona lì, funziona anche
per chi ha calma. Il contrario non è vero.

---

**giulio** — Non ci avevo pensato in questi termini.

🎨 È il motivo per cui insisto sulla scena invece che sui requisiti.

E qui c'è una cosa che mi aiuta: **la schermata di ricerca esiste già dal 2022** e
funziona. Non ho da disegnare un prodotto, ho da disegnare due gesti nuovi dentro
un prodotto che le persone conoscono. È una fortuna, non un limite: quello che
disegno non deve essere imparato.

Vado con `CU`. Tre decisioni che discendono direttamente dalla scena:

**Il display ha un bottone solo.** «Sono qui». Niente login, niente scelta di
chi sei, niente conferma. Chi è in sala ha già la sala: chiedergli le credenziali
su un tablet appeso al muro è un attrito che fa fallire la funzione. Se il
check-in è scomodo nessuno lo fa, e noi liberiamo sale piene di gente.

**Il testo dice cosa succede se non premi.** Non «conferma la presenza», ma
«altrimenti la libero alle 11:10». Una funzione che agisce da sola deve dire
prima cosa farà.

**Niente countdown.** Il PRD parla di minuto 8 e minuto 10: quelli sono numeri per
chi implementa. Cifre che scorrono creano ansia e non aggiungono niente, l'orario
esatto basta.

---

**giulio** — Aggiungimi anche una vista calendario settimanale nella web app.

🎨 Posso, ma prima ti chiedo: nella scena delle 10:58, cosa te ne fai?

---

**giulio** — Niente, in effetti. Ma per chi pianifica in anticipo?

🎨 Per chi pianifica in anticipo **esiste già Outlook**, ed è dove prenota
comunque: il nostro servizio non prenota, di proposito. Costruire una seconda
vista calendario vuol dire mantenerne una peggiore per sempre e distrarre la
schermata dall'unico momento in cui è insostituibile.

Ti propongo di tenerla fuori e tracciarla. Se dopo il rilascio qualcuno la chiede
davvero, la facciamo con i dati d'uso in mano invece che per intuizione.

---

**giulio** — Hai ragione. Fuori.

🎨 Tracciata. E l'ho segnata nella nota di revisione come «la volta in cui
l'agente ha avuto ragione e l'umano torto», perché nel vostro racconto serve
anche quel caso — altrimenti sembra che il checkpoint umano esista solo per
correggere l'agente.

Poi c'è la parte che di solito nessuno progetta e che fa perdere fiducia: **gli
stati**.

| Stato | Cosa vede |
|---|---|
| Niente libero | «Niente alle 11. Alle 11:30 si liberano 2 sale.» con il bottone per spostarsi |
| Sala liberata mentre guardavo | La riga compare con una transizione, non con un salto |
| Display senza rete | Il tocco resta in coda e parte alla riconnessione, con l'ora del tocco |
| Ho fatto check-in ma sono in ritardo | Il tasto funziona anche dopo i 10 minuti: la finestra riguarda la liberazione, non il diritto di entrare |
| Prima volta | Nessun onboarding. Si impara usando |

Il terzo è quello che vi salva o vi affonda. Un display che perde il tocco perché
il wifi ha fatto un buco produce una persona a cui hanno dato via la sala mentre
ci stava dentro. Quella persona non lo usa più, e ha ragione.

---

**giulio** — E la sala liberata, chi la vede?

🎨 Tre posti, e vanno detti tutti e tre perché è facile ricordarne solo uno: il
display fuori dalla porta, la pagina «sala libera adesso», e **il calendario di
Outlook**.

Il terzo è quello che conta di più ed è quello che si dimentica sempre, perché
non è una nostra schermata. Se la sala torna libera solo da noi, chiunque la cerchi
dal calendario — cioè la maggior parte delle persone — continua a vederla
occupata. Avremmo liberato una sala che nessuno riesce a prendere.

L'ho scritto nella spec come vincolo, non come nota: passa a Winston e da lì
finisce in un requisito.

---

**giulio** — Passa a Winston.

---

## Cosa mostrare di questa chat

- **La scena prima dei requisiti**: «cosa stavi facendo l'ultima volta?». È il
  metodo, non un vezzo
- «Non ho da disegnare un prodotto, ho da disegnare due gesti dentro un prodotto
  che le persone conoscono» — è la differenza fra greenfield e brownfield detta
  da chi disegna
- Gli stati progettati esplicitamente, incluso il display senza rete
- **Il checkpoint umano al contrario**: qui è l'agente a convincere l'umano a
  togliere la vista calendario. Da raccontare, perché rompe l'idea che il
  checkpoint serva solo a correggere la macchina
- I tre posti dove la sala deve risultare libera, e il terzo che non è nostro
