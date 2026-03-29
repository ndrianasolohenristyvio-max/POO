package bibliotheque.interfaces;

import bibliotheque.model.Utilisateur;
import java.util.Queue;

/**
 * Interface définissant le comportement de réservation d'un document.
 */
public interface Reservable {
    void reserver(Utilisateur u) throws Exception;
    void annulerReservation(Utilisateur u);
    Queue<Utilisateur> getFileAttente();
}
