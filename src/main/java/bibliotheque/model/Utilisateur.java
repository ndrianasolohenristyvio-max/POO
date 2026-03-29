package bibliotheque.model;

import bibliotheque.interfaces.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant un utilisateur de la bibliothèque.
 * Implémente Observer pour recevoir les notifications de disponibilité.
 */
public abstract class Utilisateur implements Observer {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private List<Emprunt> empruntsActifs;
    private List<String> notifications;

    public Utilisateur(String id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.empruntsActifs = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    // Méthodes abstraites à redéfinir (polymorphisme)
    public abstract int getMaxEmprunts();
    public abstract int getDureeEmpruntJours();
    public abstract String getTypeUtilisateur();

    public boolean peutEmprunter() {
        return empruntsActifs.size() < getMaxEmprunts();
    }

    public void ajouterEmprunt(Emprunt e) {
        empruntsActifs.add(e);
    }

    public void retirerEmprunt(Emprunt e) {
        empruntsActifs.remove(e);
    }

    @Override
    public void notifier(String message) {
        notifications.add(message);
        System.out.println("[NOTIF pour " + nom + "] " + message);
    }

    // Getters & Setters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Emprunt> getEmpruntsActifs() { return empruntsActifs; }
    public List<String> getNotifications() { return notifications; }

    @Override
    public String toString() {
        return getTypeUtilisateur() + " – " + prenom + " " + nom + " (" + id + ")";
    }
}
