# Deferred work

Roba vera, non urgente adesso. Ci finisce quello che la code review classifica
come `defer`: problemi reali che **non** sono stati introdotti dalla modifica in
esame, e che quindi non si risolvono «di passaggio» dentro la story sbagliata.

Ogni voce riporta da dove arriva. Chi apre una story che tocca quella zona
guarda qui prima.

---

## Deferred from: code review of story-1.2 (2026-09-02)

- **Nessuna fixture di tipo `OCCORRENZA` in tutta la suite.** Tutte le
  prenotazioni di test sono singole: le fixture sono del 2022, quando le serie
  non erano sincronizzate, e nessuno le ha più guardate quando nel 2024 sono
  arrivate. È il motivo per cui il rilievo grosso della 1.2 — il rilascio che
  operava sulla serie — è arrivato fino alla review invece di essere un test
  rosso il giorno prima. Pre-esistente a questa story: `architecture.md`
  § Strategia di test lo dichiarava già. **Promosso a story 2.1**, perché una
  cosa così non sta in un file di lavoro rimandato.

## Deferred from: sprint planning epic-1 (2026-08-28)

- **Nessun indice sullo storico delle prenotazioni.** `prenotazione_evento`
  cresce senza limite e non ha indice su `prenotazione_id`. Irrilevante a 612
  prenotazioni in quattro settimane, non irrilevante fra un anno. Si guarda
  quando arriva il report aggregato (1.5), che è la prima cosa che legge quella
  tabella davvero.

- **`GestoreErroriHttp` non mappa il timeout verso Graph.** Ogni errore che non
  sia 404 esce come 500 senza corpo: il display non distingue «errore nostro» da
  «riprova fra un attimo» e mostra la stessa schermata grigia. Pre-esistente,
  del 2023. Da riprendere quando si tocca quella classe.
