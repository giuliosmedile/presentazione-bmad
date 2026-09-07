package it.azienda.saleriunioni.sincronizzazione;

import it.azienda.saleriunioni.prenotazione.Prenotazione;
import it.azienda.saleriunioni.prenotazione.TipoPrenotazione;
import java.util.UUID;

/**
 * Da evento Graph a riga {@code prenotazione}. Scritto nel 2022, esteso nel 2024
 * quando sono arrivate le serie.
 *
 * <p>Vedi {@code architecture.md} § E4 per il motivo della chiave.
 */
public final class MappaturaEventoGraph {

    private MappaturaEventoGraph() {}

    public static Prenotazione da(EventoGraph evento, UUID salaId) {
        var tipo = tipoDi(evento);
        return new Prenotazione(
                UUID.randomUUID(),
                salaId,
                evento.inizio(),
                evento.fine(),
                tipo,
                chiaveDiSincronizzazione(evento, tipo),
                evento.organizzatore());
    }

    /**
     * La chiave con cui ritroviamo questa riga al prossimo giro di delta query.
     *
     * <p>ATTENZIONE: per le occorrenze non è l'id dell'occorrenza, è l'id della
     * serie. Gli id restituiti da {@code calendarView} per le occorrenze non sono
     * stabili fra due delta query: usarli come chiave duplicherebbe mezza tabella
     * a ogni giro. L'unica cosa stabile che Graph ci dà è l'id della serie, e
     * l'occorrenza la distinguiamo con l'orario di inizio — indice unico
     * {@code (id_evento_graph, inizio)} in {@code V3__sincronizzazione.sql}.
     *
     * <p>Non è un indirizzo a cui scrivere. Chi deve operare su Graph per una
     * singola occorrenza deve prima risolverne l'id vero.
     */
    private static String chiaveDiSincronizzazione(EventoGraph evento, TipoPrenotazione tipo) {
        return switch (tipo) {
            case SINGOLA, SERIE -> evento.id();
            case OCCORRENZA -> evento.idSerie();
        };
    }

    private static TipoPrenotazione tipoDi(EventoGraph evento) {
        if (evento.idSerie() == null) {
            return TipoPrenotazione.SINGOLA;
        }
        return evento.id().equals(evento.idSerie())
                ? TipoPrenotazione.SERIE
                : TipoPrenotazione.OCCORRENZA;
    }
}
