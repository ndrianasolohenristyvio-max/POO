package bibliotheque.model;

/**
 * Utilisateur externe : max 1 emprunt, durée 7 jours.
 */
public class Externe extends Utilisateur {
    private String organisation;
    private String pieceIdentite;

    public Externe(String id, String nom, String prenom, String email, String organisation, String pieceIdentite) {
        super(id, nom, prenom, email);
        this.organisation = organisation;
        this.pieceIdentite = pieceIdentite;
    }

    @Override public int getMaxEmprunts() { return 1; }
    @Override public int getDureeEmpruntJours() { return 7; }
    @Override public String getTypeUtilisateur() { return "Externe"; }

    public String getOrganisation() { return organisation; }
    public String getPieceIdentite() { return pieceIdentite; }
}
