package bibliotheque.gui;

import bibliotheque.model.*;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Panel de gestion des emprunts et retours.
 */
public class EmpruntPanel {

    private ListView<String> listeEmpruntsActifs;

    public Node getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label titre = new Label("Gestion des Emprunts / Retours");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // ---- SECTION EMPRUNT ----
        TitledPane panneauEmprunt = new TitledPane();
        panneauEmprunt.setText("📥 Nouvel Emprunt");

        GridPane gridEmprunt = new GridPane();
        gridEmprunt.setHgap(10); gridEmprunt.setVgap(8);
        gridEmprunt.setPadding(new Insets(10));

        ComboBox<String> cbUtilisateur = new ComboBox<>();
        RegistreUtilisateurs.getInstance().getTous()
            .forEach(u -> cbUtilisateur.getItems().add(u.getId() + " – " + u.getNom() + " " + u.getPrenom()));

        ComboBox<String> cbDocument = new ComboBox<>();
        Catalogue.getInstance().getTousDocuments()
            .forEach(d -> cbDocument.getItems().add(d.getIsbn() + " – " + d.getTitre()));

        Button btnEmprunter = new Button("📥 Emprunter");
        btnEmprunter.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        Label lblResultatEmprunt = new Label();

        btnEmprunter.setOnAction(e -> {
            String selU = cbUtilisateur.getValue();
            String selD = cbDocument.getValue();
            if (selU == null || selD == null) {
                lblResultatEmprunt.setText("❌ Sélectionnez un utilisateur et un document.");
                lblResultatEmprunt.setStyle("-fx-text-fill: red;");
                return;
            }
            String idU = selU.split(" – ")[0];
            String isbn = selD.split(" – ")[0];
            Utilisateur u = RegistreUtilisateurs.getInstance().getParId(idU);
            Document doc = Catalogue.getInstance().getParIsbn(isbn);
            try {
                doc.emprunter(u);
                Catalogue.getInstance().ajouterEmpruntHistorique(u.getEmpruntsActifs().get(u.getEmpruntsActifs().size()-1));
                lblResultatEmprunt.setText("✔ Emprunt enregistré pour " + u.getNom() + " – retour prévu dans " + u.getDureeEmpruntJours() + " jours.");
                lblResultatEmprunt.setStyle("-fx-text-fill: green;");
                rafraichirListe();
            } catch (Exception ex) {
                lblResultatEmprunt.setText("❌ " + ex.getMessage());
                lblResultatEmprunt.setStyle("-fx-text-fill: red;");
            }
        });

        gridEmprunt.add(new Label("Utilisateur :"), 0, 0); gridEmprunt.add(cbUtilisateur, 1, 0);
        gridEmprunt.add(new Label("Document :"), 0, 1);    gridEmprunt.add(cbDocument, 1, 1);
        gridEmprunt.add(btnEmprunter, 1, 2);
        gridEmprunt.add(lblResultatEmprunt, 0, 3, 2, 1);
        panneauEmprunt.setContent(gridEmprunt);

        // ---- SECTION RETOUR ----
        TitledPane panneauRetour = new TitledPane();
        panneauRetour.setText("📤 Retour de Document");

        GridPane gridRetour = new GridPane();
        gridRetour.setHgap(10); gridRetour.setVgap(8);
        gridRetour.setPadding(new Insets(10));

        ComboBox<String> cbDocumentRetour = new ComboBox<>();
        Catalogue.getInstance().getTousDocuments().stream()
            .filter(d -> !d.estDisponible())
            .forEach(d -> cbDocumentRetour.getItems().add(d.getIsbn() + " – " + d.getTitre()));

        Button btnRetourner = new Button("📤 Retourner");
        btnRetourner.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        Label lblResultatRetour = new Label();

        btnRetourner.setOnAction(e -> {
            String selD = cbDocumentRetour.getValue();
            if (selD == null) {
                lblResultatRetour.setText("❌ Sélectionnez un document.");
                lblResultatRetour.setStyle("-fx-text-fill: red;");
                return;
            }
            String isbn = selD.split(" – ")[0];
            Document doc = Catalogue.getInstance().getParIsbn(isbn);
            try {
                doc.retourner();
                lblResultatRetour.setText("✔ Document '" + doc.getTitre() + "' retourné avec succès.");
                lblResultatRetour.setStyle("-fx-text-fill: green;");
                cbDocumentRetour.getItems().remove(selD);
                rafraichirListe();
            } catch (Exception ex) {
                lblResultatRetour.setText("❌ " + ex.getMessage());
                lblResultatRetour.setStyle("-fx-text-fill: red;");
            }
        });

        gridRetour.add(new Label("Document emprunté :"), 0, 0); gridRetour.add(cbDocumentRetour, 1, 0);
        gridRetour.add(btnRetourner, 1, 1);
        gridRetour.add(lblResultatRetour, 0, 2, 2, 1);
        panneauRetour.setContent(gridRetour);

        // ---- LISTE EMPRUNTS ACTIFS ----
        Label lblListe = new Label("📋 Emprunts actifs :");
        lblListe.setStyle("-fx-font-weight: bold;");
        listeEmpruntsActifs = new ListView<>();
        listeEmpruntsActifs.setPrefHeight(200);
        rafraichirListe();

        root.getChildren().addAll(titre, panneauEmprunt, panneauRetour, lblListe, listeEmpruntsActifs);
        return root;
    }

    private void rafraichirListe() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Utilisateur u : RegistreUtilisateurs.getInstance().getTous()) {
            for (Emprunt emp : u.getEmpruntsActifs()) {
                String retard = emp.estEnRetard() ? " ⚠ RETARD " + emp.joursDeRetard() + "j" : "";
                items.add(u.getNom() + " → " + emp.getDocument().getTitre()
                    + " (retour: " + emp.getDateRetourPrevue() + ")" + retard);
            }
        }
        if (items.isEmpty()) items.add("Aucun emprunt actif.");
        listeEmpruntsActifs.setItems(items);
    }
}
