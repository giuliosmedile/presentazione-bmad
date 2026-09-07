-- 2022, sei settimane dopo il primo rilascio.
--
-- Fino a qui salvavamo il titolo di ogni riunione e il display lo mostrava.
-- Sullo schermo davanti alla sala grande e' comparso il titolo di un colloquio,
-- con nome e cognome dentro. Il display sta in corridoio.
--
-- Da qui in avanti lo specchio porta anche il flag `privato`, che su Outlook
-- mette chi crea la riunione, e nessun testo destinato a un display usa piu'
-- direttamente la colonna `titolo`: si passa da TestoDisplay.
--
-- La colonna `titolo` resta, perche' serve alla sincronizzazione e ai log
-- interni. Non e' testo da mostrare.

ALTER TABLE prenotazione
    ADD COLUMN privato boolean NOT NULL DEFAULT false;

COMMENT ON COLUMN prenotazione.titolo IS
    'Dato grezzo dallo specchio Graph. NON usare per testo mostrato all''esterno: TestoDisplay.';

COMMENT ON COLUMN prenotazione.privato IS
    'Riunione marcata privata su Outlook. Sui display il titolo diventa "Riunione riservata".';

CREATE INDEX idx_prenotazione_private ON prenotazione (privato) WHERE privato;
