# PRD — SaleRiunioni

Autore: John (Product Manager) · Input: `product-brief.md`
Revisione umana: Giulio, 2026-08-25 · Stato: approvato per l'epic 1

## Obiettivo

Far scendere sotto il 20% la quota di sale che risultano occupate ma sono vuote,
misurata con lo stesso giro fisico usato nel brief.

Metrica di supporto: numero di prenotazioni andate a buon fine su sale liberate
automaticamente. Se è zero, la liberazione non sta servendo a nessuno.

## Cosa NON facciamo nel primo rilascio

Questa sezione è la più importante del documento e va letta per prima.

- Sale di sedi diverse da Milano
- Riunioni ricorrenti
- Prenotazione da app mobile nativa (il web responsive basta)
- Catering, allestimenti, richieste speciali
- Integrazione con i tornelli o i badge
- Notifiche push. Solo mail, che c'è già.
- Statistiche per reparto o per persona (decisione di prodotto, non tecnica)

Se una di queste rientra, rientra dopo aver misurato.

## Requisiti funzionali

**FR1 — Ricerca sala.** Data una fascia oraria e un numero di partecipanti, il
sistema elenca le sale libere e prenotabili, ordinate per capienza crescente
rispetto ai partecipanti richiesti.

**FR2 — Prenotazione.** Un dipendente autenticato prenota una sala libera per una
o più fasce contigue, fino a un massimo di 4 ore consecutive.

**FR3 — Check-in.** Dall'inizio della prenotazione e per i 10 minuti successivi,
il prenotante può confermare la presenza dal proprio dispositivo.

**FR4 — Liberazione automatica.** Trascorsi 10 minuti dall'inizio senza check-in,
la prenotazione passa a `no-show` e la sala torna prenotabile per le fasce residue.

**FR5 — Avviso prima della liberazione.** Al minuto 7 il prenotante riceve una mail
che avvisa della liberazione imminente e contiene il link per il check-in.

**FR6 — Disdetta esplicita.** Il prenotante può disdire in qualunque momento prima
della fine. La sala torna prenotabile immediatamente.

**FR7 — Forzatura.** Il facility manager può liberare una sala occupata inserendo
una motivazione, che resta registrata.

## Requisiti non funzionali

- **NFR1** — La ricerca risponde entro 300 ms al 95° percentile con 6 sale e
  12 mesi di storico. Numeri piccoli, ma vanno verificati e non assunti.
- **NFR2** — La liberazione automatica non può avere falsi positivi: se il check-in
  è arrivato, la prenotazione non decade mai. In caso di dubbio si tiene la
  prenotazione.
- **NFR3** — Accessibilità: navigazione completa da tastiera, contrasto AA.
  L'app è interna e obbligatoria, quindi deve funzionare per tutti.
- **NFR4** — Nessun dato personale oltre a quelli già esposti dall'SSO.

## Criteri di rilascio

L'epic 1 si considera rilasciabile quando:

1. Un dipendente può cercare, prenotare, fare check-in e disdire senza assistenza
2. Una prenotazione senza check-in decade entro 11 minuti dall'inizio
3. Nessun test automatico rosso
4. Il giro fisico di misura è stato rifatto una volta, per avere il valore di partenza

## Epiche

| Epic | Titolo | Stato |
|---|---|---|
| 1 | Prenotare e liberare | in corso |
| 2 | Dati d'uso per il facility manager | non iniziata |
| 3 | Ricorrenze e multi-sede | fuori MVP |

Il dettaglio delle story sta in `epics.md`.

---

**Nota del revisore umano (Giulio):** la prima stesura aveva FR8 «classifica dei
no-show per reparto» — rientrata dal brief nonostante l'avessi tolta lì. L'ho
ritolta e ho aggiunto la riga esplicita nella sezione «cosa NON facciamo», che è
il posto dove una decisione del genere sopravvive. Se sta solo nella mia testa,
al prossimo giro torna.

Ho anche stretto FR2: la prima versione non metteva limiti alla durata. Quattro
ore è arbitrario, ma un limite arbitrario è meglio di nessun limite.
