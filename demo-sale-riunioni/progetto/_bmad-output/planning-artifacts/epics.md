# Epiche e story — SaleRiunioni

Autore: John (Product Manager) · Input: `prd.md`
Revisione umana: Giulio, 2026-08-25

## Epic 1 — Prenotare e liberare

Obiettivo: chiudere il ciclo completo su una sola sede, così da poter misurare se
l'ipotesi del brief regge.

| Story | Titolo | FR coperti | Stima | Stato |
|---|---|---|---|---|
| 1.1 | Ricerca e prenotazione di una sala | FR1, FR2 | M | done |
| 1.2 | Check-in e liberazione automatica | FR3, FR4 | M | in progress |
| 1.3 | Avviso di liberazione imminente | FR5 | S | draft |
| 1.4 | Disdetta esplicita | FR6 | S | backlog |
| 1.5 | Forzatura del facility manager | FR7 | S | backlog |

### Ordine e dipendenze

1.1 va per prima perché senza prenotazioni non c'è niente da liberare.
1.2 dipende da 1.1. 1.3 dipende da 1.2 (l'avviso ha senso solo se la liberazione
esiste). 1.4 e 1.5 sono indipendenti e possono entrare in qualunque momento.

**1.3 non va rilasciata senza 1.2.** Una mail che avvisa di una liberazione che
non avviene è peggio di nessuna mail.

## Epic 2 — Dati d'uso per il facility manager

Non iniziata. Ha senso solo dopo che l'epic 1 ha prodotto qualche settimana di
dati veri. Story abbozzate: utilizzo per sala e fascia, tasso di no-show
aggregato (mai per persona), export CSV.

## Epic 3 — Ricorrenze e multi-sede

Fuori MVP. Elencata qui solo perché la domanda arriva a ogni riunione ed è utile
avere un posto dove rispondere «è tracciata, non è adesso».
