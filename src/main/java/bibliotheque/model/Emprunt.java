package bibliotheque.model;

import java.time.LocalDate;

/**
 * Représente un emprunt d'un document par un utilisateur.
 */
public class Emprunt {
    private Document document;
    private Utilisateur utilisateur;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;
    private boolean rendu;

    public Emprunt(Document document, Utilisateur utilisateur, int dureeJours) {
        this.document = document;
        this.utilisateur = utilisateur;
        this.dateEmprunt = LocalDate.now();
        this.dateRetourPrevue = dateEmprunt.plusDays(dureeJours);
        this.rendu = false;
    }

    public void effectuerRetour() {
        this.dateRetourEffective = LocalDate.now();
        this.rendu = true;
    }

    public boolean estEnRetard() {
        if (rendu) return dateRetourEffective.isAfter(dateRetourPrevue);
        return LocalDate.now().isAfter(dateRetourPrevue);
    }

    public long joursDeRetard() {
        if (!estEnRetard()) return 0;
        LocalDate reference = rendu ? dateRetourEffective : LocalDate.now();
        return dateRetourPrevue.until(reference).getDays();
    }

    // Getters
    public Document getDocument() { return document; }
    public Utilisateur getUtilisateur() { return utilisateur; }
    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public LocalDate getDateRetourPrevue() { return dateRetourPrevue; }
    public LocalDate getDateRetourEffective() { return dateRetourEffective; }
    public boolean isRendu() { return rendu; }

    @Override
    public String toString() {
        return document.getTitre() + " – empr. le " + dateEmprunt + " – retour prévu: " + dateRetourPrevue
               + (rendu ? " – rendu le " + dateRetourEffective : " – EN COURS")
               + (estEnRetard() ? " [RETARD: " + joursDeRetard() + "j]" : "");
    }
}
