# Spec UX — SaleRiunioni

Autrice: Sally (UX Designer) · Input: `prd.md`
Revisione umana: Giulio, 2026-08-26

## Il momento che conta

Sono le 10:58. La riunione è alle 11. Sto camminando con il portatile sotto il
braccio e ho una mano sola libera. Devo trovare una sala **adesso**.

Tutto il resto del design serve questo momento. Se funziona qui, funziona anche
per chi prenota con calma il martedì per il giovedì.

## Flusso principale — trova e prenota

Una schermata sola. Nessun wizard, nessun passo intermedio.

```
┌────────────────────────────────────────────────┐
│  Quando   [ ora ▾ ]   Per quanto  [ 1h ▾ ]     │
│  Quante persone  [ 4 ]                         │
├────────────────────────────────────────────────┤
│  ● Sala Verde      4 posti   11:00-12:00  [Prenota] │
│  ● Sala Arancio    6 posti   11:00-12:00  [Prenota] │
│  ○ Sala Grande    12 posti   libera 11:30  [Prenota] │
└────────────────────────────────────────────────┘
```

I default fanno il lavoro: **ora**, **un'ora**, **il numero di partecipanti
dell'ultima prenotazione**. Chi ha fretta preme un solo bottone.

L'ordinamento è per capienza crescente sopra il richiesto: chi cerca per 4 non
deve vedere prima la sala da 12. Occupare una sala grande in 2 è l'altro modo di
sprecare capacità.

## Stati da progettare, non da improvvisare

| Stato | Cosa vede l'utente |
|---|---|
| Nessuna sala libera | «Niente libero alle 11. Alle 11:30 si liberano 2 sale.» con bottone per spostarsi |
| Sala presa mentre decidevo | «La Sala Verde è appena stata presa.» La lista si aggiorna da sola, la riga sparisce con una transizione, non con un salto |
| Prima volta che entro | Nessun onboarding. La schermata è già utilizzabile, si impara usandola |
| Errore di rete | La lista resta visibile in grigio con «Dati di 30 secondi fa». Non si svuota mai lo schermo |

Lo stato «sala presa mentre decidevo» è quello che di solito nessuno progetta ed
è quello che fa perdere fiducia nello strumento.

## Check-in

Il check-in non è una schermata: è una **riga in cima alla stessa pagina**, che
compare all'ora di inizio.

```
┌────────────────────────────────────────────────┐
│  Sei in Sala Verde?   [ Sì, sono qui ]         │
│  Altrimenti la libero alle 11:10               │
└────────────────────────────────────────────────┘
```

Un bottone, una frase che dice cosa succede se non lo premi. Il countdown non si
mostra a cifre che scorrono: crea ansia e non serve, l'orario esatto basta.

## Accessibilità

- Tutto raggiungibile da tastiera, ordine di tab uguale all'ordine visivo
- Il bottone «Prenota» ha come label accessibile «Prenota Sala Verde 11:00-12:00»,
  non «Prenota»: chi usa uno screen reader sente le righe fuori contesto
- Contrasto AA su tutti i testi, stato libera/occupata mai affidato al solo colore
  (c'è il pallino pieno o vuoto)
- Nessuna animazione sopra i 200 ms, e tutte disattivate con `prefers-reduced-motion`

## Cosa passo a Winston

Tre vincoli che l'architettura deve reggere:

1. La lista si aggiorna da sola mentre la guardo (la riga che sparisce non è un
   dettaglio estetico: è come si evita la doppia prenotazione percepita)
2. Il check-in deve funzionare dal telefono senza installare niente e senza login
   aggiuntivo: link firmato in mail, oppure la stessa web app già autenticata
3. La prenotazione deve fallire in modo esplicito e leggibile se qualcuno arriva
   prima: l'utente deve capire *cosa* è successo, non vedere un errore generico

---

**Nota del revisore umano (Giulio):** avevo chiesto anche una vista a calendario
settimanale. Sally l'ha disegnata e poi mi ha convinto a toglierla: nel momento
delle 10:58 non serve a niente, e per chi prenota in anticipo Outlook c'è già.
Tolta dal primo rilascio, tracciata nell'epic 2. Questa è la volta in cui
l'agente ha avuto ragione e io torto.
