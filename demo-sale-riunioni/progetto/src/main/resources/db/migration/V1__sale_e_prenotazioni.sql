-- 2022 — schema iniziale. Lo specchio locale dei calendari delle sale.
-- Le prenotazioni non nascono qui: arrivano da Graph. Questa tabella esiste per
-- mostrarle sui display e per contarle.

CREATE TABLE sala (
    id            uuid PRIMARY KEY,
    nome          text NOT NULL UNIQUE,
    capienza      int  NOT NULL CHECK (capienza > 0),
    piano         int  NOT NULL,
    casella_graph text NOT NULL UNIQUE   -- la mailbox risorsa della sala
);

CREATE TABLE prenotazione (
    id              uuid PRIMARY KEY,
    sala_id         uuid      NOT NULL REFERENCES sala (id),
    periodo         tstzrange NOT NULL,
    stato           text      NOT NULL CHECK (stato IN ('attiva', 'conclusa', 'disdetta', 'no_show', 'forzata')),
    titolo          text      NOT NULL,
    organizzatore   text      NOT NULL,
    id_evento_graph text      NOT NULL UNIQUE,
    check_in_at     timestamptz,
    created_at      timestamptz NOT NULL DEFAULT now()
);

-- Traccia di ogni transizione di stato. Serve alla motivazione della forzatura
-- e a contare i no-show.
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
