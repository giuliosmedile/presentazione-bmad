package it.azienda.saleriunioni.display;

import it.azienda.saleriunioni.prenotazione.Prenotazione;

/**
 * Tutto il testo che finisce su un display passa da qui.
 *
 * <p>Scritta nel 2022, e non per eleganza. Nella prima versione il display
 * mostrava il titolo di qualunque riunione. Sullo schermo davanti alla sala
 * grande è comparso il titolo di un colloquio, con nome e cognome dentro, e in
 * corridoio è passata mezza azienda. Le Risorse Umane hanno aperto un caso.
 *
 * <p>Il display non è una schermata dell'applicazione: è un cartello acceso in un
 * corridoio, senza login, che legge chiunque passi. Vedi {@code architecture.md}
 * § E3.
 *
 * <p><b>Chi scrive testo per un display non deve ricordarsi di questa storia.</b>
 * Deve usare questa classe, che se la ricorda al posto suo.
 */
public final class TestoDisplay {

    private static final String TITOLO_RISERVATO = "Riunione riservata";

    private TestoDisplay() {}

    /**
     * Il titolo mostrabile di una prenotazione.
     *
     * <p>Per le riunioni marcate private restituisce «Riunione riservata». Per
     * tutte le altre restituisce il titolo vero — ed è il motivo per cui un uso
     * sbagliato è invisibile finché non capita davvero una riunione privata.
     */
    public static String titoloVisibile(Prenotazione prenotazione) {
        return prenotazione.privato() ? TITOLO_RISERVATO : prenotazione.titolo();
    }

    /** La riga che il display mostra mentre la sala è occupata. */
    public static String rigaOccupata(Prenotazione prenotazione) {
        return "%s · %s".formatted(titoloVisibile(prenotazione), prenotazione.fascia());
    }

    /**
     * La riga che il display mostra quando la sala è stata liberata.
     *
     * <p>Aggiunta per la story 1.2, dopo la code review. Prima quel testo lo
     * componeva {@code LiberazioneService} per conto suo, con
     * {@code prenotazione.titolo()}: sul display finiva il titolo vero anche per
     * le riunioni private. Il rilievo era che la riga nuova andava scritta qui e
     * non là, perché è qui che sta il pezzo di conoscenza.
     */
    public static String rigaLibera(Prenotazione prenotazioneDecaduta) {
        return "Libera — nessun check-in per %s"
                .formatted(titoloVisibile(prenotazioneDecaduta));
    }
}
