package bibliotheque.model;

import bibliotheque.interfaces.*;
import java.util.*;

/**
 * Classe abstraite Document – racine de la hiérarchie documentaire.
 * Implémente Empruntable, Reservable, Recherchable, Observable.
 */
public abstract class Document implements Empruntable, Reservable, Recherchable, Observable {

    private String isbn;
    private String titre;
    private String auteur;
    private int annee;
    private String categorie;
    private boolean disponible;
    private Utilisateur emprunteurActuel;
    private Date dateEmprunt;

    // Pattern Observer
    private List<Observer> observers = new ArrayList<>();

    // File d'attente (LinkedList pour réservations)
    private LinkedList<Utilisateur> fileAttente = new LinkedList<>();

    // Historique des emprunts
    private List<Emprunt> historique = new ArrayList<>();

    // Tags pour la recherche
    private List<String> tags = new ArrayList<>();

    public Document(String isbn, String titre, String auteur, int annee, String categorie) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
        this.categorie = categorie;
        this.disponible = true;
    }

    public abstract String getTypeDocument();

    // ===================== Empruntable =====================
    @Override
    public void emprunter(Utilisateur u) throws Exception {
        if (!disponible) throw new Exception("Document '" + titre + "' non disponible.");
        if (!u.peutEmprunter()) throw new Exception(u + " a atteint son quota d'emprunts.");
        this.disponible = false;
        this.emprunteurActuel = u;
        this.dateEmprunt = new Date();
        Emprunt emprunt = new Emprunt(this, u, u.getDureeEmpruntJours());
        u.ajouterEmprunt(emprunt);
        historique.add(emprunt);
    }

    @Override
    public void retourner() throws Exception {
        if (disponible) throw new Exception("Document déjà disponible.");
        emprunteurActuel.retirerEmprunt(historique.get(historique.size() - 1));
        this.disponible = true;
        this.emprunteurActuel = null;
        this.dateEmprunt = null;
        // Notifier le premier en file d'attente (Observer)
        if (!fileAttente.isEmpty()) {
            Utilisateur suivant = fileAttente.peek();
            notifierObservers("Le document '" + titre + "' est maintenant disponible pour vous.");
        }
    }

    @Override
    public boolean estDisponible() { return disponible; }

    // ===================== Reservable =====================
    @Override
    public void reserver(Utilisateur u) throws Exception {
        if (fileAttente.contains(u)) throw new Exception("Déjà en file d'attente.");
        fileAttente.add(u);
        ajouterObserver(u);
    }

    @Override
    public void annulerReservation(Utilisateur u) {
        fileAttente.remove(u);
        retirerObserver(u);
    }

    @Override
    public Queue<Utilisateur> getFileAttente() { return fileAttente; }

    // ===================== Recherchable =====================
    @Override
    public boolean correspondA(String critere) {
        String c = critere.toLowerCase();
        return titre.toLowerCase().contains(c)
            || auteur.toLowerCase().contains(c)
            || categorie.toLowerCase().contains(c)
            || isbn.contains(c)
            || tags.stream().anyMatch(t -> t.toLowerCase().contains(c));
    }

    @Override
    public List<String> getTags() { return tags; }

    public void ajouterTag(String tag) { tags.add(tag); }

    // ===================== Observable =====================
    @Override
    public void ajouterObserver(Observer o) { observers.add(o); }

    @Override
    public void retirerObserver(Observer o) { observers.remove(o); }

    @Override
    public void notifierObservers(String message) {
        for (Observer o : observers) o.notifier(message);
    }

    // ===================== Getters / Setters =====================
    public String getIsbn() { return isbn; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public Utilisateur getEmprunteurActuel() { return emprunteurActuel; }
    public Date getDateEmprunt() { return dateEmprunt; }
    public List<Emprunt> getHistorique() { return historique; }

    @Override
    public String toString() {
        return "[" + getTypeDocument() + "] " + titre + " – " + auteur + " (" + annee + ") " + (disponible ? "✔" : "✘");
    }
}
