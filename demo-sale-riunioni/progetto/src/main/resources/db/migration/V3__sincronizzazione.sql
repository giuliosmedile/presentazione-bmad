-- 2024 — arrivano le serie ricorrenti.
--
-- Fino a qui una riga = un evento, e `id_evento_graph` era l'id di quell'evento.
-- Con le occorrenze quell'equazione si rompe: gli id che `calendarView` ci dà per
-- le occorrenze cambiano fra una delta query e la successiva, quindi come chiave
-- non si possono usare.
--
-- Scelta: per le occorrenze `id_evento_graph` contiene l'id DELLA SERIE, e la
-- singola occorrenza si identifica con la coppia (id_evento_graph, inizio).
-- L'indice unico qui sotto è quella scelta scritta nello schema.
--
-- Conseguenza per chi legge questa colonna: `id_evento_graph` è una chiave di
-- sincronizzazione, non l'indirizzo di un evento su cui scrivere.

ALTER TABLE prenotazione
    ADD COLUMN tipo text NOT NULL DEFAULT 'SINGOLA'
        CHECK (tipo IN ('SINGOLA', 'SERIE', 'OCCORRENZA'));

-- Cade il vincolo del 2022: per le occorrenze l'id non è più unico da solo,
-- perché tutte le occorrenze della stessa serie lo condividono.
ALTER TABLE prenotazione DROP CONSTRAINT prenotazione_id_evento_graph_key;

CREATE UNIQUE INDEX idx_prenotazione_chiave_sync
    ON prenotazione (id_evento_graph, lower(periodo));

CREATE INDEX idx_prenotazione_occorrenze
    ON prenotazione (id_evento_graph) WHERE tipo = 'OCCORRENZA';
