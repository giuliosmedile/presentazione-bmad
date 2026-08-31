package it.azienda.saleriunioni.prenotazione;

import it.azienda.saleriunioni.shared.DurataMassimaSuperataException;
import it.azienda.saleriunioni.shared.FasceNonContigueException;
import it.azienda.saleriunioni.shared.SalaGiaOccupataException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Scrittura delle prenotazioni (FR2). La lettura sta in {@code disponibilita}. */
@Service
public class PrenotazioneService {

    /**
     * Da prd.md FR2. Costante di dominio e non proprietà di configurazione:
     * renderla configurabile è una decisione di prodotto, non tecnica.
     */
    private static final Duration DURATA_MASSIMA = Duration.ofHours(4);

    private final PrenotazioneRepository repository;

    public PrenotazioneService(PrenotazioneRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Prenotazione prenota(
            UUID salaId, OffsetDateTime da, OffsetDateTime a, String prenotante) {

        if (Duration.between(da, a).compareTo(DURATA_MASSIMA) > 0) {
            throw new DurataMassimaSuperataException(DURATA_MASSIMA);
        }
        if (!fasceContigue(da, a)) {
            throw new FasceNonContigueException(da, a);
        }

        var prenotazione = Prenotazione.attiva(salaId, da, a, prenotante);

        try {
            repository.inserisci(prenotazione);
        } catch (DataIntegrityViolationException e) {
            // architecture.md D3: verificare QUALE vincolo è stato violato.
            // Intercettare genericamente significa che un CHECK sulla durata o
            // una foreign key arriverebbero all'utente come "sala occupata".
            // Rilievo [alta] della code review 1.1.
            if (violazioneDi(e, "prenotazione_no_overlap")) {
                throw new SalaGiaOccupataException(salaId, da, a);
            }
            throw e;
        }
        return prenotazione;
    }

    private static boolean violazioneDi(DataIntegrityViolationException e, String vincolo) {
        var causa = e.getMostSpecificCause().getMessage();
        return causa != null && causa.contains(vincolo);
    }

    private static boolean fasceContigue(OffsetDateTime da, OffsetDateTime a) {
        var minuti = Duration.between(da, a).toMinutes();
        return minuti > 0 && minuti % 30 == 0 && da.getMinute() % 30 == 0;
    }
}
