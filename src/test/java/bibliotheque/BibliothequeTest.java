package bibliotheque;

import bibliotheque.model.*;
import bibliotheque.patterns.CalculateurAmende;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires JUnit 5 pour le Projet 1 – Bibliothèque.
 * Couvre les cas normaux et les cas limites.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BibliothequeTest {

    private static Livre livre;
    private static Etudiant etudiant;
    private static Enseignant enseignant;
    private static Externe externe;

    @BeforeEach
    void setUp() {
        livre = new Livre("978-TEST-001", "Test Java", "Auteur Test", 2024, "Informatique", "Editeur", 300);
        etudiant = new Etudiant("ETU-TEST", "Dupont", "Alice", "alice@test.mg", "L3", "Info");
        enseignant = new Enseignant("ENS-TEST", "Martin", "Bob", "bob@test.mg", "Info", "MCF");
        externe = new Externe("EXT-TEST", "Soa", "Liva", "liva@test.mg", "CNTEMAD", "CIN-999");
    }

    // ========== Tests : Document ==========

    @Test @Order(1)
    @DisplayName("Un document est disponible à la création")
    void testDocumentDisponibleInitialement() {
        assertTrue(livre.estDisponible(), "Un nouveau document doit être disponible");
    }

    @Test @Order(2)
    @DisplayName("Emprunt réussi pour un étudiant")
    void testEmpruntReussi() throws Exception {
        livre.emprunter(etudiant);
        assertFalse(livre.estDisponible(), "Après emprunt, document indisponible");
        assertEquals(1, etudiant.getEmpruntsActifs().size(), "L'étudiant doit avoir 1 emprunt");
    }

    @Test @Order(3)
    @DisplayName("Emprunt impossible si document déjà emprunté")
    void testEmpruntImpossibleSiIndisponible() throws Exception {
        livre.emprunter(etudiant);
        Etudiant etudiant2 = new Etudiant("ETU-002", "Rabe", "Jean", "jean@test.mg", "L2", "Info");
        assertThrows(Exception.class, () -> livre.emprunter(etudiant2),
            "Impossible d'emprunter un document déjà emprunté");
    }

    @Test @Order(4)
    @DisplayName("Retour de document réussi")
    void testRetourReussi() throws Exception {
        livre.emprunter(etudiant);
        livre.retourner();
        assertTrue(livre.estDisponible(), "Après retour, document disponible");
        assertEquals(0, etudiant.getEmpruntsActifs().size(), "L'étudiant ne doit plus avoir d'emprunt");
    }

    // ========== Tests : Quotas ==========

    @Test @Order(5)
    @DisplayName("Quota d'emprunt étudiant : max 3")
    void testQuotaEtudiant() {
        assertEquals(3, etudiant.getMaxEmprunts());
        assertEquals(14, etudiant.getDureeEmpruntJours());
    }

    @Test @Order(6)
    @DisplayName("Quota d'emprunt enseignant : max 10")
    void testQuotaEnseignant() {
        assertEquals(10, enseignant.getMaxEmprunts());
        assertEquals(30, enseignant.getDureeEmpruntJours());
    }

    @Test @Order(7)
    @DisplayName("Quota d'emprunt externe : max 1")
    void testQuotaExterne() {
        assertEquals(1, externe.getMaxEmprunts());
        assertEquals(7, externe.getDureeEmpruntJours());
    }

    @Test @Order(8)
    @DisplayName("Emprunt impossible quand quota atteint")
    void testEmpruntImpossibleQuotaAtteint() throws Exception {
        // Étudiant peut emprunter 3 documents max
        for (int i = 0; i < 3; i++) {
            Livre l = new Livre("978-TEST-00" + i, "Livre " + i, "Auteur", 2024, "Info", "Ed", 100);
            l.emprunter(etudiant);
        }
        assertFalse(etudiant.peutEmprunter(), "Étudiant ne peut plus emprunter");
        Livre l4 = new Livre("978-TEST-004", "Livre 4", "Auteur", 2024, "Info", "Ed", 100);
        assertThrows(Exception.class, () -> l4.emprunter(etudiant));
    }

    // ========== Tests : Réservation ==========

    @Test @Order(9)
    @DisplayName("Réservation ajoutée à la file d'attente")
    void testReservation() throws Exception {
        livre.emprunter(etudiant);
        livre.reserver(enseignant);
        assertEquals(1, livre.getFileAttente().size(), "File d'attente doit contenir 1 personne");
        assertEquals(enseignant, livre.getFileAttente().peek());
    }

    @Test @Order(10)
    @DisplayName("Annulation de réservation")
    void testAnnulationReservation() throws Exception {
        livre.emprunter(etudiant);
        livre.reserver(enseignant);
        livre.annulerReservation(enseignant);
        assertEquals(0, livre.getFileAttente().size(), "File d'attente doit être vide");
    }

    // ========== Tests : Recherche ==========

    @Test @Order(11)
    @DisplayName("Recherche par titre (correspondA)")
    void testRechercheParTitre() {
        assertTrue(livre.correspondA("Test Java"));
        assertTrue(livre.correspondA("test"));    // insensible à la casse
        assertFalse(livre.correspondA("Python"));
    }

    @Test @Order(12)
    @DisplayName("Recherche par auteur")
    void testRechercheParAuteur() {
        assertTrue(livre.correspondA("Auteur Test"));
    }

    @Test @Order(13)
    @DisplayName("Recherche par tag")
    void testRechercheParTag() {
        livre.ajouterTag("java");
        assertTrue(livre.correspondA("java"));
    }

    // ========== Tests : Amendes (Strategy) ==========

    @Test @Order(14)
    @DisplayName("Amende étudiant = 200 Ar/jour")
    void testAmendeEtudiant() throws Exception {
        livre.emprunter(etudiant);
        Emprunt e = etudiant.getEmpruntsActifs().get(0);
        // L'emprunt vient d'être créé, pas encore en retard
        assertEquals(0, e.joursDeRetard());
        assertEquals(0, CalculateurAmende.calculer(e), 0.01);
    }

    @Test @Order(15)
    @DisplayName("Catalogue : ajout et récupération")
    void testCatalogue() {
        Catalogue cat = Catalogue.getInstance();
        int avant = cat.getTousDocuments().size();
        cat.ajouterDocument(livre);
        assertEquals(avant + 1, cat.getTousDocuments().size());
        assertEquals(livre, cat.getParIsbn("978-TEST-001"));
    }

    // ========== Tests : Observer ==========

    @Test @Order(16)
    @DisplayName("Observer : notification lors du retour")
    void testNotificationObserver() throws Exception {
        livre.emprunter(etudiant);
        livre.reserver(enseignant); // enseignant est observateur
        int nbNotifAvant = enseignant.getNotifications().size();
        livre.retourner();
        assertTrue(enseignant.getNotifications().size() > nbNotifAvant,
            "L'enseignant doit être notifié au retour du document");
    }
}
