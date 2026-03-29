package bibliotheque.model;

/**
 * Enseignant : max 10 emprunts, durée 30 jours.
 */
public class Enseignant extends Utilisateur {
    private String departement;
    private String grade; // Professeur, Maître de conférence, etc.

    public Enseignant(String id, String nom, String prenom, String email, String departement, String grade) {
        super(id, nom, prenom, email);
        this.departement = departement;
        this.grade = grade;
    }

    @Override public int getMaxEmprunts() { return 10; }
    @Override public int getDureeEmpruntJours() { return 30; }
    @Override public String getTypeUtilisateur() { return "Enseignant"; }

    public String getDepartement() { return departement; }
    public String getGrade() { return grade; }
}
