package bibliotheque.model;

/**
 * Étudiant : max 3 emprunts, durée 14 jours.
 */
public class Etudiant extends Utilisateur {
    private String niveau;   // L1, L2, L3, M1, M2...
    private String filiere;

    public Etudiant(String id, String nom, String prenom, String email, String niveau, String filiere) {
        super(id, nom, prenom, email);
        this.niveau = niveau;
        this.filiere = filiere;
    }

    @Override public int getMaxEmprunts() { return 3; }
    @Override public int getDureeEmpruntJours() { return 14; }
    @Override public String getTypeUtilisateur() { return "Étudiant"; }

    public String getNiveau() { return niveau; }
    public String getFiliere() { return filiere; }
}
