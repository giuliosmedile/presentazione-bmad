# 💻 Amelia — Code review (`CR`)

> Rilettura su più assi di qualità, in sessione separata dall'implementazione.
> Artefatto toccato: la sezione **Review Findings** della story.

---

**giulio** — CR sulla 1.1.

💻 `CR`. Sessione nuova. Rileggo il diff contro gli AC della story, il contesto di
progetto e `architecture.md`. Non riuso il ragionamento con cui ho implementato.

---

💻 Tre rilievi.

### [alta] Traduzione dell'errore troppo larga

`PrenotazioneService.java:78`

```java
} catch (DataIntegrityViolationException e) {
    throw new SalaGiaOccupataException(salaId, periodo);
}
```

Qualunque vincolo violato diventa «sala occupata». Se domani aggiungiamo un
`CHECK` sulla durata o una foreign key, l'utente si vede «sala occupata» per un
errore che non c'entra niente.

`architecture.md` § D3 lo dice esplicitamente: «non si intercetta genericamente
`DataIntegrityViolation`: si verifica il nome del vincolo».

Fix: controllare che sia `prenotazione_no_overlap`, rilanciare gli altri.

### [media] Il filtro della ricerca usa il predicato sbagliato

`RicercaDisponibilita.java:34` — `stato != 'disdetta'`

Deve essere `stato = 'attiva'`.

Con il predicato attuale una prenotazione in `no_show` continua a occupare la
sala, perché `no_show != disdetta` è vero.

Cioè: **la sala liberata automaticamente non risulterebbe libera.** È esattamente
il bug che questo progetto esiste per evitare, ed è nella query principale.

### [bassa] Timestamp con fuso locale in due test

`PrenotazioneServiceTest:112` e `:145` usano `LocalDateTime.now()`. Il resto del
codice è UTC. I test passano sulla macchina di chi li ha scritti.

---

**giulio** — Il secondo è grosso. Come ti è sfuggito mentre implementavi?

💻 Perché mentre implementavo stavo verificando che la ricerca escludesse le sale
prenotate, e quel predicato lo fa: con solo prenotazioni `attiva` nel database, i
due filtri danno lo stesso risultato. I test della 1.1 non creano prenotazioni
`no_show`, perché lo stato `no_show` arriva nella 1.2.

È corretto rispetto agli AC della 1.1 e sbagliato rispetto al progetto. Il tipo di
errore che si vede solo rileggendo contro l'architettura invece che contro i test.

---

**giulio** — E se non avessimo fatto la review?

💻 Il bug sarebbe emerso in 1.2, come «la liberazione non funziona». Avremmo
cercato nel job, che sarebbe stato corretto. Il difetto era nella query della
story precedente, già approvata.

---

**giulio** — Chiudi i tre rilievi.

💻 Chiusi. `DS` di nuovo:

- Verifica del nome del vincolo, `PrenotazioneServiceTest` esteso con un caso di
  vincolo diverso violato
- Predicato corretto in `stato = 'attiva'`, più un test che inserisce una
  prenotazione `no_show` e verifica che la sala risulti libera
- Timestamp portati a UTC

**16 test verdi.** I due nuovi coprono i rilievi alta e media: se qualcuno
reintroduce il difetto, il test rosso lo dice.

Review Findings scritti nella story. Status pronto per la tua approvazione.

---

**giulio** — Approvata. Merge.

---

## Cosa mostrare

- La review è una **sessione separata**: non è l'implementatore che si rilegge
  con la testa con cui ha scritto
- Il rilievo `[media]` è il pezzo forte della demo: codice **corretto rispetto
  agli AC della story e sbagliato rispetto al progetto**. Nessun test lo avrebbe
  preso
- La risposta a «come ti è sfuggito» — spiega perché il checkpoint esiste
- «Se non avessimo fatto la review» — il bug sarebbe stato cercato nel posto
  sbagliato, nella story dopo
- I due test aggiunti sui rilievi: la correzione diventa permanente

> **Se hai tempo per una sola chat, usa questa.** È l'unica che mostra il processo
> mentre intercetta qualcosa, che è la tesi del talk.
