package bibliotheque.patterns;

import bibliotheque.model.*;

/**
 * Calcule les amendes en utilisant la stratégie appropriée.
 * Applique le pattern Strategy.
 */
public class CalculateurAmende {

    public static StrategieAmende getStrategie(Utilisateur utilisateur) {
        if (utilisateur instanceof Etudiant) return new AmendeEtudiant();
        if (utilisateur instanceof Enseignant) return new AmendeEnseignant();
        if (utilisateur instanceof Externe) return new AmendeExterne();
        throw new IllegalArgumentException("Type d'utilisateur inconnu : " + utilisateur.getClass());
    }

    public static double calculer(Emprunt emprunt) {
        StrategieAmende strategie = getStrategie(emprunt.getUtilisateur());
        return strategie.calculerAmende(emprunt);
    }
}
