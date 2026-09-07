package it.azienda.saleriunioni.liberazione;

import it.azienda.saleriunioni.outlook.PrenotazioneOutlookClient;
import it.azienda.saleriunioni.prenotazione.Prenotazione;
import it.azienda.saleriunioni.prenotazione.PrenotazioneRepository;
import it.azienda.saleriunioni.prenotazione.StatoPrenotazione;
import java.time.Clock;
import java.time.Duration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Liberazione automatica delle sale non usate (story 1.2, FR3).
 *
 * <p>Chiamato da {@code LiberazioneJob} ogni minuto. Vedi {@code architecture.md}
 * D1 per il perché di un job che scansiona invece di uno scheduler per
 * prenotazione.
 */
@Service
public class LiberazioneService {

    /** Da prd.md FR3. Costante di dominio: renderla configurabile è una scelta di prodotto. */
    private static final Duration ATTESA_CHECK_IN = Duration.ofMinutes(10);

    private final PrenotazioneRepository repository;
    private final PrenotazioneOutlookClient outlook;
    private final Clock clock;

    public LiberazioneService(
            PrenotazioneRepository repository, PrenotazioneOutlookClient outlook, Clock clock) {
        this.repository = repository;
        this.outlook = outlook;
        this.clock = clock;
    }

    public void liberaLeSaleNonUsate() {
        var scadute = repository.attiveIniziateDaPiuDi(ATTESA_CHECK_IN, clock.instant());
        scadute.stream().filter(p -> !p.haCheckIn()).forEach(this::libera);
    }

    /**
     * Un singolo rilascio. Lo stato passa a {@code no_show} prima della chiamata a
     * Graph, non dopo: se la chiamata fallisce il giro successivo non ritenta la
     * transizione (NFR2, idempotenza), e la riconciliazione la fa il job di
     * sincronizzazione.
     *
     * <p>Rilievo [Patch] della code review 1.2: qui c'era
     * {@code outlook.annulla(p.idEventoGraph())}. Su una prenotazione di tipo
     * {@code OCCORRENZA} quell'id è l'id della serie ({@code architecture.md} § E4),
     * quindi la chiamata cancellava la serie intera — tutte le occorrenze passate
     * e future, dai calendari di tutti i partecipanti, senza possibilità di
     * annullare.
     */
    @Transactional
    void libera(Prenotazione prenotazione) {
        repository.cambiaStato(
                prenotazione, StatoPrenotazione.no_show, "liberazione automatica", "sistema");
        outlook.rilasciaSala(prenotazione);
    }
}
