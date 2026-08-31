-- Story 1.1 — schema di base per sale e prenotazioni.
-- Decisione D3 di architecture.md: la sovrapposizione è impedita dal database,
-- non dal codice applicativo. Due richieste concorrenti passerebbero entrambe
-- un controllo fatto con SELECT + INSERT.

CREATE EXTENSION IF NOT EXISTS btree_gist;

CREATE TABLE sala (
    id       uuid PRIMARY KEY,
    nome     text NOT NULL UNIQUE,
    capienza int  NOT NULL CHECK (capienza > 0),
    piano    int  NOT NULL
);

CREATE TABLE prenotazione (
    id          uuid PRIMARY KEY,
    sala_id     uuid      NOT NULL REFERENCES sala (id),
    periodo     tstzrange NOT NULL,
    stato       text      NOT NULL CHECK (stato IN ('attiva', 'conclusa', 'disdetta', 'no_show', 'forzata')),
    prenotante  text      NOT NULL,
    check_in_at timestamptz,
    created_at  timestamptz NOT NULL DEFAULT now(),

    -- Il filtro sullo stato è la parte che si dimentica: senza, una sala
    -- liberata per no-show resterebbe bloccata per sempre, che è esattamente
    -- il problema che l'applicazione esiste per risolvere.
    CONSTRAINT prenotazione_no_overlap
        EXCLUDE USING gist (sala_id WITH =, periodo WITH &&)
        WHERE (stato = 'attiva')
);

-- Traccia di ogni transizione di stato. Serve a FR7 (motivazione della
-- forzatura) e a contare i no-show. Decisione D2: audit, non event sourcing.
CREATE TABLE prenotazione_evento (
    id              bigserial PRIMARY KEY,
    prenotazione_id uuid        NOT NULL REFERENCES prenotazione (id),
    da_stato        text        NOT NULL,
    a_stato         text        NOT NULL,
    motivazione     text,
    attore          text        NOT NULL,
    avvenuto_at     timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX idx_prenotazione_sala_periodo ON prenotazione USING gist (sala_id, periodo);
CREATE INDEX idx_prenotazione_attive       ON prenotazione (stato) WHERE stato = 'attiva';
