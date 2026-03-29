package bibliotheque.patterns;

import bibliotheque.model.Emprunt;

/**
 * Interface Strategy pour le calcul des amendes.
 * Chaque type d'utilisateur a sa propre stratégie.
 */
public interface StrategieAmende {
    double calculerAmende(Emprunt emprunt);
    String getDescription();
}

// ======================== AMENDE ÉTUDIANT ========================
class AmendeEtudiant implements StrategieAmende {
    private static final double TARIF_PAR_JOUR = 200.0; // Ariary

    @Override
    public double calculerAmende(Emprunt emprunt) {
        return emprunt.joursDeRetard() * TARIF_PAR_JOUR;
    }

    @Override
    public String getDescription() {
        return "Amende Étudiant : " + TARIF_PAR_JOUR + " Ar/jour";
    }
}

// ======================== AMENDE ENSEIGNANT ========================
class AmendeEnseignant implements StrategieAmende {
    private static final double TARIF_PAR_JOUR = 100.0; // Tarif réduit

    @Override
    public double calculerAmende(Emprunt emprunt) {
        // Enseignants : tarif réduit + grace period de 3 jours
        long joursEffectifs = Math.max(0, emprunt.joursDeRetard() - 3);
        return joursEffectifs * TARIF_PAR_JOUR;
    }

    @Override
    public String getDescription() {
        return "Amende Enseignant : " + TARIF_PAR_JOUR + " Ar/jour (après 3j de grâce)";
    }
}

// ======================== AMENDE EXTERNE ========================
class AmendeExterne implements StrategieAmende {
    private static final double TARIF_PAR_JOUR = 500.0; // Tarif plus élevé

    @Override
    public double calculerAmende(Emprunt emprunt) {
        return emprunt.joursDeRetard() * TARIF_PAR_JOUR;
    }

    @Override
    public String getDescription() {
        return "Amende Externe : " + TARIF_PAR_JOUR + " Ar/jour";
    }
}
