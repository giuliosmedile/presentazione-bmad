package it.azienda.saleriunioni.disponibilita;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

/**
 * Query di sola lettura per la ricerca delle sale disponibili (FR1).
 *
 * <p>Non passa dal service di scrittura: da architecture.md, sezione Struttura.
 * La ricerca è l'operazione più usata e la più semplice, farla transitare per il
 * dominio la complicherebbe senza comprare niente.
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
                          -- Rilievo [media] della code review 1.1: qui c'era
                          -- `p.stato <> 'disdetta'`. Una prenotazione in no_show
                          -- avrebbe continuato a occupare la sala, cioè la
                          -- liberazione automatica non avrebbe liberato niente.
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
