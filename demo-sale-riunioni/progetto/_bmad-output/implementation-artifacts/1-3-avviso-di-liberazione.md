# Story 1.3: Avviso di liberazione imminente

Status: in progress

## Story

As chi ha prenotato una sala ed è in ritardo di otto minuti,
I want essere avvisato che sto per perderla,
so that possa fare check-in dal telefono invece di trovarla data via.

## Acceptance Criteria

1. **Given** una prenotazione attiva iniziata da 8 minuti senza check-in
   **When** gira il job di avviso
   **Then** parte un avviso a chi ha prenotato, una volta sola.

2. **Given** una prenotazione già avvisata
   **When** il job gira di nuovo
   **Then** non parte un secondo avviso.

3. **Given** una prenotazione che riceve il check-in dopo l'avviso
   **When** il job di liberazione gira
   **Then** la prenotazione non decade.

4. **Given** l'avviso inviato
   **When** l'utente lo apre
   **Then** può fare check-in dal link senza aprire la web app.

## Tasks / Subtasks

- [x] Colonna `avvisato_at` e migrazione (AC: 1, 2)
- [x] `AvvisoJob` a 8 minuti, riusa la selezione di `LiberazioneService` (AC: 1)
- [ ] Invio dell'avviso (AC: 1) — **sospeso, vedi domanda aperta**
- [ ] Check-in da link firmato (AC: 4)
- [ ] Test, uno per AC

## Dev Notes

### References

- `_bmad-output/planning-artifacts/architecture.md` § E3, E4, D1
- `_bmad-output/project-context.md` § Regole di prodotto
- `_bmad-output/implementation-artifacts/1-2-liberazione-automatica-della-sala.md`

## Dev Agent Record

### Agent Model Used

Amelia (bmad-agent-dev), skill `bmad-dev-story` — esecuzione sospesa

### Completion Notes List

- Selezione e marcatura fatte e verdi, riusando il predicato della 1.2 con una
  soglia diversa.
- Fermata prima dell'invio. **Domanda aperta per l'umano:** a chi va l'avviso.
  Il campo `organizzatore` per le occorrenze è l'organizzatore della *serie*, che
  in tre casi su quattro ha creato la ricorrenza anni fa e non è quello che oggi
  è in ritardo davanti alla sala. Avvisare lui è inutile e leggermente ridicolo.
  Le alternative che vedo sono tre, e nessuna è una decisione tecnica: avvisare
  comunque l'organizzatore della serie; avvisare tutti i partecipanti
  dell'occorrenza (ma `project-context.md` § Regole di prodotto pesa qui: una mail
  che dice «nessuno è in sala» a otto persone nomina di fatto chi non c'è);
  oppure non avvisare nessuno sulle occorrenze e tenere l'avviso per le singole.
