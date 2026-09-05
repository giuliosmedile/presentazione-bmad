# Deferred work

Roba vera, non urgente adesso. Ci finisce quello che la code review classifica
come `defer`: problemi reali che **non** sono stati introdotti dalla modifica in
esame, e che quindi non si risolvono «di passaggio» dentro la story sbagliata.

Ogni voce riporta da dove arriva. Chi apre una story che tocca quella zona
guarda qui prima.

---

## Deferred from: code review of story-1.1 (2026-08-29)

- **`PrenotazioneController` non mappa il timeout del database.**
  `PrenotazioneController.java:52` mappa 409 (`SalaGiaOccupataException`) e 422
  (`DurataMassimaSuperataException`). Ogni altra eccezione esce come 500 senza
  corpo, timeout della connessione compreso: il client non distingue «errore
  nostro» da «riprova fra un attimo». Pre-esistente allo scaffold, non introdotto
  dalla 1.1. Da riprendere quando si tocca `GestoreErroriHttp`.

---

## Deferred from: sprint planning epic-1 (2026-08-28)

- **Nessun indice sullo storico delle prenotazioni.** `prenotazione_evento`
  cresce senza limite e non ha indice su `prenotazione_id`. Irrilevante a 612
  prenotazioni in quattro settimane, non irrilevante fra un anno. Si guarda
  quando arriva il report di utilizzo (1.5), che è la prima cosa che legge quella
  tabella davvero.
