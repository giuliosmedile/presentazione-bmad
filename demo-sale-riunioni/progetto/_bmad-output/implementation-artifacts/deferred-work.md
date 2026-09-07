# Deferred work

Roba vera, non urgente adesso. Ci finisce quello che la code review classifica
come `defer`: problemi reali che **non** sono stati introdotti dalla modifica in
esame, e che quindi non si risolvono «di passaggio» dentro la story sbagliata.

Ogni voce riporta da dove arriva. Chi apre una story che tocca quella zona
guarda qui prima.

---

## Deferred from: code review of story-1.2 (2026-09-02)

- **Nessuna fixture di riunione privata in tutta la suite.** Tutte le
  prenotazioni di test hanno `privato = false`. È peggio di un buco normale:
  su una riunione non privata il metodo giusto e quello sbagliato restituiscono
  la stessa stringa, quindi il test verde non è una prova di niente. È il motivo
  per cui il rilievo grosso della 1.2 — il titolo esposto sul display — è
  arrivato fino alla review invece di essere un test rosso il giorno prima.
  Pre-esistente a questa story: `architecture.md` § Strategia di test lo
  dichiarava già. **Promosso a story 2.1.**

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
