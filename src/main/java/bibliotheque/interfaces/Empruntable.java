package bibliotheque.interfaces;

import bibliotheque.model.Utilisateur;

/**
 * Interface définissant le comportement d'emprunt d'un document.
 */
public interface Empruntable {
    void emprunter(Utilisateur u) throws Exception;
    void retourner() throws Exception;
    boolean estDisponible();
}
