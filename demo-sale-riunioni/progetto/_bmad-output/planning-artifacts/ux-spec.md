# Spec UX — Liberazione automatica

Autrice: Sally (UX Designer) · Input: `prd.md`
Revisione umana: Giulio, 2026-08-26

## Il momento che conta

Sono le 10:58. La riunione è alle 11. Sto camminando con il portatile sotto il
braccio e ho una mano sola libera. Devo trovare una sala **adesso**.

Tutto il resto del design serve questo momento. Se funziona qui, funziona anche
per chi prenota con calma il martedì per il giovedì.

## Cosa c'è già, e non si tocca

La pagina «sala libera adesso» esiste dal 2022 e funziona. Non la ridisegno: due
gesti nuovi dentro qualcosa che le persone conoscono valgono più di una schermata
nuova che devono imparare.

```
┌────────────────────────────────────────────────┐
│  Quando   [ ora ▾ ]   Per quanto  [ 1h ▾ ]     │
│  Quante persone  [ 4 ]                         │
├────────────────────────────────────────────────┤
│  ● Sala Verde      4 posti   libera adesso     │
│  ● Sala Arancio    6 posti   libera adesso     │
│  ○ Sala Grande    12 posti   libera 11:30      │
└────────────────────────────────────────────────┘
```

Quello che cambia è **cosa mostra**: da adesso in poi ci compaiono dentro anche le
sale che il sistema ha liberato da solo. Non serve nessuna etichetta «liberata
automaticamente»: a chi cerca una sala non interessa perché è libera.

## Il display di sala

Il tasto di check-in vive sul display fuori dalla porta, non nella web app.

```
┌────────────────────────────────────────────────┐
│  SALA VERDE                                    │
│  Allineamento settimanale · 11:00-12:00        │
│                                                │
│         [        Sono qui        ]             │
│                                                │
│  Altrimenti la libero alle 11:10               │
└────────────────────────────────────────────────┘
```

Tre scelte, e tutte e tre discendono dalla scena:

**Un bottone solo, nessun login.** Chi è in sala ha già la sala. Chiedergli le
credenziali su un tablet appeso al muro è un attrito che fa fallire la funzione:
se il check-in è scomodo nessuno lo fa, e liberiamo sale piene di gente.

**Il testo dice cosa succede se non premi.** Non «conferma la presenza», ma
«altrimenti la libero alle 11:10». Una funzione che agisce da sola annuncia prima
cosa farà.

**Niente countdown a cifre che scorrono.** Crea ansia e non aggiunge niente:
l'orario esatto basta. Il PRD parla di minuto 8 e minuto 10, ma quelli sono
numeri per chi implementa.

**Il display non è una schermata: è un cartello.** Sta in corridoio, non ha login,
e lo legge chiunque passi — compreso chi aspetta di essere ricevuto. Su cosa ci si
può scrivere sopra non decido io: c'è già una regola nel progetto, dal 2022, e
`architecture.md` § E3 racconta anche perché è nata. Io la eredito e la applico.

In pratica, per questa story: il testo del display lo compone `TestoDisplay`, non
chi lo mostra.

## Stati da progettare, non da improvvisare

| Stato | Cosa vede la persona |
|---|---|
| Nessuna sala libera | «Niente libero alle 11. Alle 11:30 si liberano 2 sale.» con bottone per spostarsi |
| Sala liberata mentre guardavo | La riga compare con una transizione, non con un salto |
| Display senza rete | Il tocco resta in coda e parte alla riconnessione, con l'ora del tocco e non quella dell'invio |
| Check-in in ritardo | Il tasto funziona anche dopo i 10 minuti: la finestra riguarda la liberazione, non il diritto di entrare in sala |
| Prima volta | Nessun onboarding. Si impara usando |

Il display senza rete è quello che di solito nessuno progetta ed è quello che fa
perdere fiducia: un tocco perso produce una persona a cui hanno dato via la sala
mentre ci stava dentro. Quella persona non usa più lo strumento, e ha ragione.

## Dove deve risultare libera una sala liberata

Tre posti, e vanno detti tutti e tre perché è facile ricordarne solo uno:

1. Il display fuori dalla porta
2. La pagina «sala libera adesso»
3. **Il calendario di Outlook**

Il terzo è quello che conta di più e quello che si dimentica sempre, perché non è
una nostra schermata. Se la sala torna libera solo da noi, chiunque la cerchi dal
calendario — cioè la maggior parte delle persone — continua a vederla occupata, e
avremmo liberato una sala che nessuno riesce a prendere.

Questo è un vincolo, non una nota.

## Accessibilità

- Il display si usa con un tocco solo, senza istruzioni sullo schermo
- Area del tasto grande almeno 44×44 mm reali: si preme camminando, con una mano
  sola, spesso senza guardare
- Contrasto AA su tutti i testi, stato libera/occupata mai affidato al solo colore
  (c'è il pallino pieno o vuoto)
- Tutto raggiungibile da tastiera nella web app, ordine di tab uguale a quello visivo
- Nessuna animazione sopra i 200 ms, tutte disattivate con `prefers-reduced-motion`

## Cosa passo a Winston

Tre vincoli che l'architettura deve reggere:

1. La sala liberata deve risultare libera **anche su Outlook**: si toglie la sala
   dall'evento, l'evento resta
2. Il display deve poter accodare un tocco e inviarlo dopo, con l'ora del tocco
3. La lista si aggiorna da sola mentre la guardo: la riga che compare non è un
   dettaglio estetico, è come si evita che due persone corrano sulla stessa sala

---

**Nota del revisore umano (Giulio):** avevo chiesto anche una vista a calendario
settimanale nella web app. Sally l'ha disegnata e poi mi ha convinto a toglierla:
nel momento delle 10:58 non serve a niente, e per chi prenota in anticipo Outlook
c'è già — ed è dove si prenota comunque, visto che il nostro servizio non prenota
di proposito. Tolta e tracciata. Questa è la volta in cui l'agente ha avuto
ragione e io torto.
