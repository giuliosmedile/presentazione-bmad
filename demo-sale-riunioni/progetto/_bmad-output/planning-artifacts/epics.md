# Epiche e story — SaleRiunioni

Autore: John (Product Manager) · Input: `prd.md`
Revisione umana: Giulio, 2026-08-26

> Il servizio esiste dal 2022. Queste sono le prime epiche scritte, non le prime
> cose fatte: quello che c'è già non è elencato qui, è descritto in
> `architecture.md` Parte I.

## Epic 1 — Liberazione automatica delle sale non usate

Obiettivo: chiudere il ciclo segnale → azione su una sola sede, così da poter
misurare se l'ipotesi del brief regge.

| Story | Titolo | FR coperti | Stima | Stato |
|---|---|---|---|---|
| 1.1 | Check-in dal display | FR2 | M | done |
| 1.2 | Liberazione automatica della sala | FR3, FR4 | M | done |
| 1.3 | Avviso di liberazione imminente | FR5 | S | in progress |
| 1.4 | Pannello del facility manager | FR6, FR7 | M | backlog |
| 1.5 | Report no-show aggregato | FR8 | S | backlog |

### Ordine e dipendenze

1.1 va per prima perché senza check-in non c'è nessun segnale da cui capire se
una sala è usata. 1.2 dipende da 1.1. 1.3 dipende da 1.2: un avviso che annuncia
una liberazione che non avviene è peggio di nessun avviso.

**1.4 e 1.5 non partono** finché non c'è la spec UX del pannello: R1
dell'implementation readiness report.

### Il salto che c'è dentro la 1.2

Fino alla 1.1 il servizio **legge** i calendari. Dalla 1.2 ci **scrive**. È un
cambio di categoria, non una funzionalità in più: da lì in poi un nostro bug non
sporca una nostra tabella, tocca il calendario delle persone. Vale la pena averlo
scritto in un posto che si rilegge.

## Epic 2 — Il debito che ci siamo trovati

Nata da un `defer` della code review sulla 1.2, non da un desiderio di prodotto.

| Story | Titolo | Stato |
|---|---|---|
| 2.1 | Fixture di tipo `OCCORRENZA` in tutta la suite | backlog |
| 2.2 | Mappatura degli errori Graph nel display | backlog |

2.1 è la più importante cosa non urgente di questo progetto. Le occorrenze sono
il 70% delle prenotazioni e non c'è un solo test che parta da una: significa che
la suite verde ci dice qualcosa sul 30% del traffico.

## Epic 3 — Multi-sede

Fuori perimetro. Elencata qui solo perché la domanda arriva a ogni riunione ed è
utile avere un posto dove rispondere «è tracciata, non è adesso».
