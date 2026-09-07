# Implementation Readiness — Epic 1

Eseguito da: Winston (System Architect), codice menu `IR`
Data: 2026-08-27 · Esito: **pronto con due rilievi**

Controllo di coerenza fra `prd.md`, `ux-spec.md`, `architecture.md` e `epics.md`.
Non è una review di qualità: è un controllo che i quattro documenti dicano la
stessa cosa.

## Coerenza dei requisiti

| Requisito | PRD | UX | Architettura | Story |
|---|---|---|---|---|
| FR1 ricerca (esistente) | ✅ | ✅ | ✅ E1, D4 | — |
| FR2 check-in dal display | ✅ | ✅ | ✅ D5 | 1.1 |
| FR3 liberazione automatica | ✅ | ✅ | ✅ D1 | 1.2 |
| FR4 sala libera su Outlook | ✅ | ✅ | ✅ D2, E4 | 1.2 |
| FR5 avviso | ✅ | ⚠️ | ✅ D1 | 1.3 |
| FR6 disdetta dal display | ✅ | ❌ | — | 1.4 |
| FR7 forzatura | ✅ | ❌ | ✅ D3 | 1.4 |
| FR8 report aggregato | ✅ | ❌ | — | 1.5 |

## Rilievi

### R1 — FR6, FR7 e FR8 non hanno una spec UX (bloccante per 1.4 e 1.5, non per 1.1-1.3)

Disdetta, forzatura e report sono nel PRD e reggono nel modello dati, ma nessuno
ha disegnato come si fanno. Le story 1.4 e 1.5 non sono implementabili così come
sono: chi le prende dovrebbe inventarsi l'interfaccia.

**Azione:** Sally deve estendere la spec prima che 1.4 entri in sprint. Non blocca
l'epic: 1.1, 1.2 e 1.3 sono complete su tutti e quattro i documenti.

### R2 — La strategia di test dichiara un buco che nessuna story chiude

`architecture.md` § Strategia di test dice, testualmente, che non esiste una sola
fixture di riunione **privata** in tutta la suite, e lo chiama «il buco più
grosso». Il motivo per cui è grosso è sottile: su una riunione normale il metodo
giusto e quello sbagliato restituiscono la stessa stringa, quindi un test verde
non prova niente.

Nessuna story dell'epic 1 lo chiude. E l'epic 1 è **tutta** su cose che finiscono
su schermi che legge chiunque passi.

Non è un'incoerenza fra documenti: i documenti sono d'accordo. È un rischio che
tutti e quattro conoscono e che nessuno ha assegnato a qualcuno.

**Azione:** o una story che chiude il buco entra nell'epic 1, o il rischio si
accetta per iscritto sapendo che l'unica rete che resta è la code review.
La decisione è dell'umano, non mia.

*(Accettato da Giulio il 2026-08-27: si va avanti, R2 resta aperto e dichiarato.)*

## Cosa è invece coerente e non serve ricontrollare

- NFR2 (mai liberare con check-in presente) compare nel PRD, in D1 e nella
  strategia di test. È l'unico requisito ripetuto tre volte, ed è giusto così.
- La regola di prodotto di `project-context.md` (una sala liberata deve risultare
  libera anche su Outlook) è riflessa in FR4 e nella spec UX.
- L'ordine delle story rispetta le dipendenze: il segnale prima dell'azione.

## Verdetto

Epic 1 pronta per l'implementazione limitatamente alle story **1.1, 1.2 e 1.3**.
1.4 e 1.5 restano in backlog finché R1 non è chiuso. R2 accettato e aperto.
