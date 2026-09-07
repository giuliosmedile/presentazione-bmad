# PRD — Liberazione automatica delle sale

Autore: John (Product Manager) · Input: `product-brief.md`
Revisione umana: Giulio, 2026-08-25 · Stato: approvato per l'epic 1

> Non è il PRD del servizio: il servizio esiste dal 2022 e non ha mai avuto un
> PRD. È il PRD di quello che gli aggiungiamo adesso. Quello che c'è già è
> descritto in `architecture.md` Parte I.

## Obiettivo

Far scendere sotto il 20% la quota di sale che risultano occupate ma sono vuote,
misurata con lo stesso giro fisico usato nel brief.

Metrica di supporto: numero di prenotazioni andate a buon fine su sale liberate
automaticamente. Se è zero, la liberazione non sta servendo a nessuno.

## Cosa NON facciamo nel primo rilascio

Questa sezione è la più importante del documento e va letta per prima.

- Sale di sedi diverse da Milano
- Prenotare dal servizio. Si prenota su Outlook, punto.
- Cancellare o modificare una riunione. Si toglie la sala, l'evento resta
- Notifiche push. Solo mail, che c'è già
- Allargare i permessi Graph oltre le sei caselle sala

Se una di queste rientra, rientra dopo aver misurato.

## Requisiti funzionali

**FR1 — Ricerca «sala libera adesso».** Esiste dal 2022. Elencata qui perché la
liberazione deve riflettersi lì entro un giro di polling, non perché la si
riscriva.

**FR2 — Check-in dal display.** Dall'inizio della prenotazione, chi è in sala
conferma la presenza con un tocco sul display, senza autenticarsi.

**FR3 — Liberazione automatica.** Trascorsi 10 minuti dall'inizio senza check-in,
la prenotazione passa a `no-show` e la sala torna prenotabile per le fasce residue.

**FR4 — La sala torna libera anche su Outlook.** Una sala liberata deve risultare
libera a chi la cerca dal calendario, non solo a chi guarda il display. Si toglie
la sala dall'evento: l'evento resta nel calendario di chi l'ha creato.

**FR5 — Avviso prima della liberazione.** Al minuto 8 parte un avviso a chi ha
prenotato, con il link per fare check-in a distanza.

**FR6 — Disdetta dal display.** Chi esce prima può liberare la sala con un tocco.

**FR7 — Forzatura.** Il facility manager può liberare una sala occupata inserendo
una motivazione, che resta registrata.

**FR8 — Report no-show aggregato.** Numeri per sala e per fascia oraria. Mai per
persona, mai per reparto sotto le dieci persone.

## Requisiti non funzionali

- **NFR1** — La liberazione si riflette su display e ricerca entro 30 secondi
  (tre giri di polling). Su Outlook, entro il tempo di propagazione di Exchange,
  che non controlliamo.
- **NFR2** — La liberazione automatica non può avere falsi positivi: se il
  check-in è arrivato, la prenotazione non decade mai. In caso di dubbio si tiene
  la prenotazione.
- **NFR3** — Nessuna scrittura verso Outlook che tocchi qualcosa di diverso dalla
  sala. Il servizio ha i permessi per fare molto di più di quello che deve fare.
- **NFR4** — Accessibilità: il display deve essere usabile con un tocco solo, da
  chiunque, senza istruzioni sullo schermo.
- **NFR5** — Niente di quello che finisce su un display può contenere informazioni
  che chi ha creato la riunione ha marcato come private. Il display sta in
  corridoio e lo legge chiunque passi.

## Criteri di rilascio

L'epic 1 si considera rilasciabile quando:

1. Una prenotazione senza check-in decade entro 11 minuti dall'inizio e la sala
   risulta libera sia sul display sia su Outlook
2. Nessuna riunione è stata cancellata o modificata dal sistema oltre alla sala,
   verificato sui calendari veri di una settimana
3. Nessun test automatico rosso
4. Il giro fisico di misura è stato rifatto una volta, per avere il valore di
   partenza

## Epiche

| Epic | Titolo | Stato |
|---|---|---|
| 1 | Liberazione automatica delle sale non usate | in corso |
| 2 | Il debito che ci siamo trovati | non iniziata |
| 3 | Multi-sede | fuori perimetro |

Il dettaglio delle story sta in `epics.md`.

---

**Nota del revisore umano (Giulio):** FR4 l'ho aggiunto io. La prima versione
diceva «la sala torna prenotabile» senza specificare dove, e dove è tutto il
punto: se torna prenotabile solo da noi, chiunque cerchi una sala da Outlook
continua a vederla occupata e non abbiamo liberato niente.

Poi ho preteso che fosse scritta anche la contropartita, e adesso sta in
`project-context.md`: da questa epic in avanti il servizio **scrive fuori da sé**.
Non è una funzionalità in più, è un cambio di categoria di rischio, e voglio che
lo legga anche chi arriva fra un anno.
