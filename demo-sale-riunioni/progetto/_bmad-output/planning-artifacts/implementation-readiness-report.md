# Implementation Readiness — Epic 1

Eseguito da: Winston (System Architect), codice menu `IR`
Data: 2026-08-27 · Esito: **pronto con due rilievi**

Controllo di coerenza fra `prd.md`, `ux-spec.md`, `architecture.md` e `epics.md`.
Non è una review di qualità: è un controllo che i quattro documenti dicano la
stessa cosa.

## Coerenza dei requisiti

| Requisito | PRD | UX | Architettura | Story |
|---|---|---|---|---|
| FR1 ricerca | ✅ | ✅ | ✅ D5 | 1.1 |
| FR2 prenotazione | ✅ | ✅ | ✅ D3 | 1.1 |
| FR3 check-in | ✅ | ✅ | ✅ D6 | 1.2 |
| FR4 liberazione | ✅ | ✅ | ✅ D4 | 1.2 |
| FR5 avviso | ✅ | ⚠️ | ✅ D6 | 1.3 |
| FR6 disdetta | ✅ | ❌ | — | 1.4 |
| FR7 forzatura | ✅ | ❌ | ✅ D2 | 1.5 |

## Rilievi

### R1 — FR6 e FR7 non hanno una spec UX (bloccante per 1.4 e 1.5, non per 1.1-1.3)

La disdetta e la forzatura sono nel PRD e reggono nel modello dati, ma nessuno ha
disegnato come si fanno. Le story 1.4 e 1.5 non sono implementabili così come
sono: chi le prende dovrebbe inventarsi l'interfaccia.

**Azione:** Sally deve estendere la spec prima che 1.4 entri in sprint. Non blocca
l'epic: 1.1, 1.2 e 1.3 sono complete su tutti e quattro i documenti.

### R2 — FR5 dice «al minuto 7», la UX non mostra il countdown

Non è una contraddizione, ma i due documenti raccontano due esperienze diverse:
il PRD implica che l'utente sappia quanto manca, la UX ha deliberatamente tolto il
countdown mostrando solo l'orario di scadenza.

**Azione:** nessuna modifica ai documenti, ma la story 1.3 deve citare
esplicitamente la scelta UX, altrimenti chi implementa aggiunge un timer perché
«il PRD parla di minuti».

## Cosa è invece coerente e non serve ricontrollare

- NFR2 (mai liberare con check-in presente) compare nel PRD, in D4 e nella
  strategia di test. È l'unico requisito ripetuto tre volte, ed è giusto così.
- Il vincolo di durata massima (4 ore) del PRD è riflesso in 1.1.
- L'ordine delle story rispetta le dipendenze tecniche di D3 e D4.

## Verdetto

Epic 1 pronta per l'implementazione limitatamente alle story **1.1, 1.2 e 1.3**.
1.4 e 1.5 restano in backlog finché R1 non è chiuso.
