package it.azienda.saleriunioni.disponibilita;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

/**
 * Query di sola lettura per la ricerca «sala libera adesso» della web app.
 * Scritta nel 2022, mai più toccata.
 *
 * <p>Legge lo specchio locale dei calendari, non Graph: interrogare Graph a ogni
 * ricerca vorrebbe dire sei chiamate di rete per una pagina che si ricarica ogni
 * dieci secondi.
 */
@Component
public class RicercaDisponibilita {

    private final JdbcClient jdbc;

    public RicercaDisponibilita(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Sale con capienza sufficiente e senza prenotazioni attive sovrapposte,
     * ordinate per capienza crescente (spec UX: chi cerca per 4 non deve vedere
     * prima la sala da 12).
     */
    public List<SalaDisponibile> cerca(OffsetDateTime da, OffsetDateTime a, int partecipanti) {
        return jdbc.sql("""
                SELECT s.id, s.nome, s.capienza, s.piano
                FROM sala s
                WHERE s.capienza >= :partecipanti
                  AND NOT EXISTS (
                        SELECT 1
                        FROM prenotazione p
                        WHERE p.sala_id = s.id
                          AND p.stato = 'attiva'
                          AND p.periodo && tstzrange(:da, :a, '[)')
                  )
                ORDER BY s.capienza ASC, s.nome ASC
                """)
                .param("partecipanti", partecipanti)
                .param("da", da)
                .param("a", a)
                .query(SalaDisponibile.class)
                .list();
    }
}
