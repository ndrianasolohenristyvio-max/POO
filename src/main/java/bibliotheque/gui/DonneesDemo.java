package bibliotheque.gui;

import bibliotheque.model.*;

/**
 * Initialise le catalogue avec des données réalistes (Université de Toamasina).
 */
public class DonneesDemo {

    public static void initialiser() {
        Catalogue cat = Catalogue.getInstance();

        // --- Documents ---
        Livre l1 = new Livre("978-2-07-040850-4", "Les Misérables", "Victor Hugo", 1862, "Littérature", "Gallimard", 1500);
        Livre l2 = new Livre("978-2-07-036822-8", "Algorithmes et Structures de Données", "Thomas Cormen", 2009, "Informatique", "MIT Press", 1312);
        Livre l3 = new Livre("978-2-10-078234-5", "Mathématiques Discrètes", "Jean-Paul Delahaye", 2015, "Mathématiques", "Dunod", 480);
        Livre l4 = new Livre("978-2-07-041239-6", "Histoire de Madagascar", "Solofo Randrianja", 2010, "Histoire", "Karthala", 320);

        Revue r1 = new Revue("ISSN-0003-3219", "Annales Informatiques", "Collectif", 2023, "Informatique", 45, "Trimestrielle");
        Revue r2 = new Revue("ISSN-1234-5678", "Revue Africaine", "Collectif", 2024, "Sciences Sociales", 12, "Mensuelle");

        TheseNumerique t1 = new TheseNumerique("THESE-001", "Intelligence Artificielle à Madagascar",
            "Rakoto Jean", 2023, "Informatique", "Université de Toamasina", "Pr. Rabe Marie", "Informatique");

        DVD d1 = new DVD("DVD-001", "Introduction à Java", "Oracle Education", 2022, "Informatique",
            180, "Oracle Team", "Français");

        // Ajouter des tags
        l2.ajouterTag("algorithmes"); l2.ajouterTag("java"); l2.ajouterTag("programmation");
        l4.ajouterTag("madagascar"); l4.ajouterTag("histoire");
        t1.ajouterTag("IA"); t1.ajouterTag("machine learning"); t1.ajouterTag("madagascar");

        cat.ajouterDocument(l1); cat.ajouterDocument(l2); cat.ajouterDocument(l3);
        cat.ajouterDocument(l4); cat.ajouterDocument(r1); cat.ajouterDocument(r2);
        cat.ajouterDocument(t1); cat.ajouterDocument(d1);

        // --- Utilisateurs ---
        RegistreUtilisateurs reg = RegistreUtilisateurs.getInstance();
        reg.ajouter(new Etudiant("ETU001", "Rakoto", "Jean", "jean.rakoto@univ-toamasina.mg", "L3", "Informatique"));
        reg.ajouter(new Etudiant("ETU002", "Rabe", "Marie", "marie.rabe@univ-toamasina.mg", "L2", "Mathématiques"));
        reg.ajouter(new Etudiant("ETU003", "Rasolofo", "Paul", "paul.rasolofo@univ-toamasina.mg", "M1", "Informatique"));
        reg.ajouter(new Enseignant("ENS001", "Rakotondrabe", "Hery", "h.rakotondrabe@univ-toamasina.mg", "Informatique", "Maître de Conférence"));
        reg.ajouter(new Enseignant("ENS002", "Randriamaro", "Voahangy", "v.randriamaro@univ-toamasina.mg", "Mathématiques", "Professeur"));
        reg.ajouter(new Externe("EXT001", "Rasoa", "Liva", "liva.rasoa@gmail.com", "CNTEMAD", "CIN-1234567"));
    }
}
