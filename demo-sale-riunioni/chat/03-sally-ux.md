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

🎨 È il motivo per cui insisto sulla scena invece che sui requisiti. Il PRD dice
«FR1 ricerca sala». Vero, ma non dice che chi cerca ha una mano sola e trenta
secondi.

Vado con `CU`. Tre decisioni che discendono direttamente dalla scena:

**Una schermata sola.** Nessun wizard. Cercare e prenotare sono lo stesso gesto.

**I default fanno il lavoro.** Quando: adesso. Per quanto: un'ora. Quante persone:
quante l'ultima volta. Chi ha fretta preme un bottone e basta.

**Ordinamento per capienza crescente sopra il richiesto.** Chi cerca per 4 non
deve vedere prima la sala da 12. Occupare una sala grande in due è l'altro modo di
sprecare capacità, e l'ordinamento è il posto dove lo si scoraggia senza vietarlo.

---

**giulio** — Aggiungimi anche una vista calendario settimanale.

🎨 Posso, ma prima ti chiedo: nella scena delle 10:58, cosa te ne fai?

---

**giulio** — Niente, in effetti. Ma per chi pianifica in anticipo?

🎨 Per chi pianifica in anticipo esiste già Outlook, e lo sa usare. Costruirne una
seconda versione dentro la nostra app vuol dire mantenerne una peggiore per
sempre, e distrarre la schermata dall'unico momento in cui è insostituibile.

Ti propongo di tenerla fuori dal primo rilascio e tracciarla nell'epic 2. Se dopo
il rilascio qualcuno la chiede davvero, la facciamo con i dati d'uso in mano
invece che per intuizione.

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
| Sala presa mentre decidevo | La riga sparisce con una transizione, non con un salto |
| Prima volta | Nessun onboarding. Si impara usando |
| Rete caduta | Lista in grigio con «Dati di 30 secondi fa». Lo schermo non si svuota mai |

Il secondo è quello che vi salva o vi affonda. Se premo Prenota e mi becco un
errore rosso senza capire cosa è successo, la prossima volta prenoto largo per
sicurezza — cioè il comportamento che stiamo cercando di eliminare.

---

**giulio** — E il check-in?

🎨 Non è una schermata. È una riga in cima alla stessa pagina, che compare
all'ora di inizio:

```
Sei in Sala Verde?   [ Sì, sono qui ]
Altrimenti la libero alle 11:10
```

Un bottone e una frase che dice cosa succede se non lo premi.

Niente countdown a cifre che scorrono: crea ansia e non aggiunge niente, l'orario
esatto basta. Il PRD parla di «minuto 7» e «minuto 10», ma quelli sono numeri per
chi implementa, non per chi guarda.

Ho aggiunto tre vincoli che passo a Winston: la lista si aggiorna da sola, il
check-in funziona dal telefono senza installare niente, e il conflitto di
prenotazione deve arrivare all'utente come un messaggio leggibile e non come un
errore generico.

---

**giulio** — Passa a Winston.

---

## Cosa mostrare di questa chat

- **La scena prima dei requisiti**: «cosa stavi facendo l'ultima volta?». È il
  metodo, non un vezzo
- Gli stati progettati esplicitamente, incluso «sala presa mentre decidevo»
- La riga di check-in che *non* è una schermata
- **Il checkpoint umano al contrario**: qui è l'agente a convincere l'umano a
  togliere la vista calendario. Da raccontare, perché rompe l'idea che il
  checkpoint serva solo a correggere la macchina
