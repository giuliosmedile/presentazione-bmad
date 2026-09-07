package it.azienda.saleriunioni.outlook;

import it.azienda.saleriunioni.prenotazione.Prenotazione;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Unico punto di scrittura verso Microsoft Graph (convenzione di
 * {@code project-context.md}, e {@code architecture.md} § E4).
 *
 * <p>Nato nel 2023 per il bottone «libera la sala» della web app. Fa una cosa
 * sola: rimuove la sala dai partecipanti dell'evento, lasciando in piedi
 * l'evento e i suoi inviti. Non cancella niente: la riunione resta nel
 * calendario di chi l'ha creata, senza più la sala.
 */
@Component
public class PrenotazioneOutlookClient {

    private final RestClient graph;

    public PrenotazioneOutlookClient(RestClient graph) {
        this.graph = graph;
    }

    /** Rilascia la sala sull'evento indicato. */
    public void rilasciaSala(Prenotazione prenotazione) {
        graph.post()
                .uri(
                        "/users/{sala}/events/{id}/decline",
                        prenotazione.casellaSala(),
                        prenotazione.idEventoGraph())
                .body(new RifiutoSala("La sala è stata liberata: nessun check-in entro 10 minuti."))
                .retrieve()
                .toBodilessEntity();
    }
}
