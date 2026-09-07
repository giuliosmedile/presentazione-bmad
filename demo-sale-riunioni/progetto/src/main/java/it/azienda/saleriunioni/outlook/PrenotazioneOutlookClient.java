package it.azienda.saleriunioni.outlook;

import it.azienda.saleriunioni.prenotazione.Prenotazione;
import it.azienda.saleriunioni.prenotazione.TipoPrenotazione;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Unico punto di scrittura verso Microsoft Graph (convenzione di
 * {@code project-context.md}).
 *
 * <p>Storia di questa classe, perché serve a capirla:
 *
 * <ul>
 *   <li><b>2023</b> — nasce con il solo {@link #annulla(String)}, per il bottone
 *       «libera la sala» della web app. Quel bottone la UI lo mostra soltanto
 *       all'organizzatore e soltanto sulle prenotazioni singole.
 *   <li><b>2024</b> — arriva la sincronizzazione delle serie. La classe non viene
 *       toccata: le ricorrenti continuano a non passare di qui, perché il filtro
 *       è nella UI.
 *   <li><b>2026</b> — la liberazione automatica ha bisogno di scrivere su Graph, e
 *       le ricorrenti arrivano qui per la prima volta.
 * </ul>
 */
@Component
public class PrenotazioneOutlookClient {

    private final RestClient graph;

    public PrenotazioneOutlookClient(RestClient graph) {
        this.graph = graph;
    }

    /**
     * Cancella l'evento indicato dalla casella della sala.
     *
     * <p>Scritto nel 2023 per le sole prenotazioni singole. Non guarda il tipo
     * della prenotazione: all'epoca non poteva arrivargli altro.
     *
     * <p>Se l'id passato è quello di una serie, cancella la serie intera. Per
     * tutti quelli che chiamano, e per il campo {@code idEventoGraph} delle
     * occorrenze, vedi {@code architecture.md} § E4.
     */
    public void annulla(String idEventoGraph) {
        graph.delete()
                .uri("/users/{sala}/events/{id}", casellaSala(), idEventoGraph)
                .retrieve()
                .toBodilessEntity();
    }

    /**
     * Rilascia la sala su una prenotazione: rimuove la sala dai partecipanti
     * dell'evento, lasciando in piedi l'evento e i suoi inviti.
     *
     * <p>Aggiunto per la story 1.2. Sulle occorrenze risolve l'id vero
     * dell'occorrenza prima di scrivere: {@code idEventoGraph} è una chiave di
     * sincronizzazione e per le occorrenze contiene l'id della serie.
     *
     * <p>Decisione umana del 2026-09-02 (review 1.2): si rilascia la sala, non si
     * cancella l'evento. Cancellare farebbe sparire la riunione dai calendari di
     * tutti i partecipanti, che non è quello che il prodotto vuole fare.
     */
    public void rilasciaSala(Prenotazione prenotazione) {
        var idSuCuiScrivere =
                prenotazione.tipo() == TipoPrenotazione.OCCORRENZA
                        ? idOccorrenza(prenotazione)
                        : prenotazione.idEventoGraph();

        graph.post()
                .uri("/users/{sala}/events/{id}/decline", casellaSala(), idSuCuiScrivere)
                .body(new RifiutoSala("La sala è stata liberata: nessun check-in entro 10 minuti."))
                .retrieve()
                .toBodilessEntity();
    }

    /** L'id dell'occorrenza, che è l'unico su cui si può scrivere senza toccare la serie. */
    private String idOccorrenza(Prenotazione prenotazione) {
        return graph.get()
                .uri(
                        "/users/{sala}/events/{idSerie}/instances?startDateTime={da}&endDateTime={a}",
                        casellaSala(),
                        prenotazione.idEventoGraph(),
                        prenotazione.inizio(),
                        prenotazione.fine())
                .retrieve()
                .body(IstanzeGraph.class)
                .unica()
                .id();
    }
}
