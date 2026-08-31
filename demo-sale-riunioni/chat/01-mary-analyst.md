# 📊 Mary — Business Analyst

> Fase di analisi. Da un'idea vaga a un brief difendibile.
> Artefatto prodotto: `_bmad-output/planning-artifacts/product-brief.md`

---

**giulio** — Mary, dobbiamo fare qualcosa per le sale riunioni. Non se ne trova mai una.

📊 Ciao giulio. Ricorda che puoi invocare `bmad-help` in qualunque momento se vuoi
un consiglio su come procedere.

Ecco cosa posso fare:

| # | Codice | Cosa |
|---|---|---|
| 1 | BP | Brainstorming guidato |
| 2 | MR | Ricerca di mercato, panorama competitivo, bisogni e trend |
| 3 | DR | Deep dive di dominio, terminologia e competenza specialistica |
| 4 | TR | Fattibilità tecnica, opzioni architetturali, approcci |
| 5 | CB | Creare o aggiornare il product brief |
| 6 | WB | Working Backwards PRFAQ — forgiare e stressare il concept |
| 7 | DP | Analizzare un progetto esistente e produrne la documentazione |

Prima di scegliere, una domanda: «non se ne trova mai una» è un'impressione o
qualcuno l'ha misurata?

---

**giulio** — Impressione. Ma è la lamentela numero uno da mesi.

📊 Allora partiamo da lì, perché è il bivio che decide tutto il resto. Ci sono due
storie possibili dietro «non si trova una sala», e portano a due prodotti diversi:

- **Le sale sono poche.** Allora il problema è di budget e mattoni, e un software
  non lo risolve.
- **Le sale ci sono ma risultano occupate quando non lo sono.** Allora è un
  problema di comportamento, e lì si può intervenire.

Non sono ipotesi equivalenti: se costruiamo per la seconda e la vera è la prima,
abbiamo buttato tre mesi. Servono dati prima di scrivere qualunque cosa.

Cosa possiamo tirare fuori senza chiedere budget?

---

**giulio** — L'export del calendario Outlook ce l'ho. E posso far fare un giro nelle sale.

📊 Perfetto, sono esattamente le due fonti che servono, e sono indipendenti: una
dice cosa il sistema *crede*, l'altra cosa succede *davvero*. Quando divergono, la
distanza è il problema.

Ti propongo `MR` sui dati che hai, poi `CB` per il brief. Ti chiedo tre cose:

1. Export di 4 settimane, non di una: una settimana la sporca qualunque festività
2. Il giro fisico su almeno 3 giorni diversi, in fascia 10-12 e 14-16
3. I ticket all'help desk sul tema, se esistono

---

**giulio** — MR. Ecco i dati. [export + 3 giri]

📊 Guarda qui, perché questo è il momento in cui il progetto ha trovato il suo
appiglio.

| Fonte | Dato |
|---|---|
| Calendario, 4 settimane | 612 prenotazioni su 6 sale |
| Giro fisico, 3 giorni | **41% delle sale "occupate" erano vuote** |
| Ticket, 6 mesi | 38 richieste «non trovo una sala», zero su altri temi |

Il 41% è il numero che regge tutto. E ha una conseguenza scomoda che ti dico
subito: **va rimisurato dopo il rilascio**. Se non scende, il prodotto non è
servito a niente, e sarà onesto dirlo.

I ticket dicono un'altra cosa interessante: nessuno si lamenta della qualità delle
sale, del wifi o delle prese. Solo di non trovarne. È un problema a una dimensione
sola, il che è una fortuna.

---

**giulio** — Quindi la gente prenota e non disdice.

📊 Sì, ma attenzione a come lo formuliamo, perché da qui discende il prodotto.

Non è che la gente sia sbadata. È che **disdire non ha nessuna conseguenza**, e
prenotare largo «per sicurezza» è il comportamento razionale in un sistema dove
chi non prenota non trova. Stiamo osservando persone che rispondono bene a un
sistema fatto male.

Se lo formuliamo come «i colleghi sono sbadati», il prodotto diventa un sistema di
richiami e fallirà. Se lo formuliamo come «liberare deve costare zero ed essere
automatico», diventa una feature.

Passo a `CB` e ti scrivo il brief.

---

**giulio** — CB.

📊 Brief scritto in `_bmad-output/planning-artifacts/product-brief.md`.

Le parti che ti chiedo di guardare per prime:

- **L'ipotesi falsificabile**: se dopo un mese il 41% non scende sotto il 20%,
  l'ipotesi è sbagliata ed è la capienza il problema. L'ho scritta apposta in modo
  che si possa perdere.
- **Le alternative scartate**, con il motivo: sensori di presenza, limite di
  prenotazioni a testa, sola dashboard. Servono quando fra sei mesi qualcuno
  chiederà «ma perché non avete messo i sensori».
- **Tre domande aperte** che non ho voluto chiudere da sola.

Ho anche aggiunto una classifica dei reparti con più no-show: il dato c'è ed è
solido.

---

**giulio** — La classifica toglila.

📊 Tolta. Mi dici il motivo, così lo scrivo nel brief? Se resta solo qui dentro,
al prossimo giro la ripropongo.

---

**giulio** — Perché in questa azienda una classifica pubblica diventa una gara, e
la gente smette di usare lo strumento per non finirci sopra. Il dato lo teniamo
aggregato.

📊 Registrato nel brief come nota del revisore, con il tuo motivo per esteso.

E dico una cosa che vale per il resto del progetto: questa non era
un'informazione che mi mancava — il dato ce l'avevo, la logica funzionava. Mi
mancava di sapere **come funziona questa azienda**. È il tipo di correzione che
non potevo fare da sola, ed è il motivo per cui il brief passa da te prima di
arrivare a John.

Vuoi che passi il brief al product manager, o preferisci rileggerlo prima?

---

**giulio** — Ho letto. Passa a John.

---

## Cosa mostrare di questa chat

- Il menu con i codici: si risponde con **due lettere**, non con un prompt
- La domanda «è un'impressione o qualcuno l'ha misurata?» arriva **prima** di
  qualunque proposta
- Il bivio esplicitato (poche sale vs sale sprecate) invece di partire a costruire
- L'ipotesi scritta in modo che possa essere smentita
- **Il checkpoint umano**: la classifica dei reparti. L'agente aveva ragione sui
  dati e torto sull'azienda
