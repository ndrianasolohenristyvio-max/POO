package bibliotheque.gui;

import bibliotheque.model.*;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

/**
 * Panel de recherche multicritère.
 */
public class RecherchePanel {

    public Node getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label titre = new Label("Recherche Multicritère");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        HBox barreRecherche = new HBox(10);
        TextField champRecherche = new TextField();
        champRecherche.setPromptText("Titre, auteur, ISBN, tag, catégorie...");
        champRecherche.setPrefWidth(400);

        Button btnRechercher = new Button("🔍 Rechercher");
        btnRechercher.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnReserver = new Button("🔖 Réserver");
        btnReserver.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");
        btnReserver.setDisable(true);

        barreRecherche.getChildren().addAll(champRecherche, btnRechercher, btnReserver);

        ListView<String> resultats = new ListView<>();
        resultats.setPrefHeight(400);

        Label lblNbResultats = new Label("Saisissez un critère de recherche.");
        Label lblNotif = new Label();
        lblNotif.setStyle("-fx-text-fill: green;");

        // Stocker les documents résultats
        final List<Document>[] listeResultats = new List[]{null};

        btnRechercher.setOnAction(e -> {
            String critere = champRecherche.getText().trim();
            if (critere.isEmpty()) {
                lblNbResultats.setText("⚠ Veuillez saisir un critère.");
                return;
            }
            List<Document> docs = Catalogue.getInstance().rechercher(critere);
            listeResultats[0] = docs;
            ObservableList<String> items = FXCollections.observableArrayList();
            for (Document d : docs) {
                items.add(d.getTypeDocument() + " | " + d.getTitre() + " – " + d.getAuteur()
                    + " | " + (d.estDisponible() ? "✔ Disponible" : "✘ Emprunté")
                    + " | Tags: " + String.join(", ", d.getTags()));
            }
            resultats.setItems(items);
            lblNbResultats.setText(docs.size() + " résultat(s) pour « " + critere + " »");
            btnReserver.setDisable(docs.isEmpty());
        });

        // Sélection dans la liste
        resultats.getSelectionModel().selectedIndexProperty().addListener((obs, old, idx) -> {
            btnReserver.setDisable(idx.intValue() < 0 || listeResultats[0] == null);
        });

        // Réservation
        ComboBox<String> cbUtilisateur = new ComboBox<>();
        RegistreUtilisateurs.getInstance().getTous()
            .forEach(u -> cbUtilisateur.getItems().add(u.getId() + " – " + u.getNom()));
        cbUtilisateur.setPromptText("Choisir utilisateur...");

        btnReserver.setOnAction(e -> {
            int idx = resultats.getSelectionModel().getSelectedIndex();
            if (idx < 0 || listeResultats[0] == null || cbUtilisateur.getValue() == null) {
                lblNotif.setText("❌ Sélectionnez un document et un utilisateur.");
                lblNotif.setStyle("-fx-text-fill: red;");
                return;
            }
            Document doc = listeResultats[0].get(idx);
            String idU = cbUtilisateur.getValue().split(" – ")[0];
            Utilisateur u = RegistreUtilisateurs.getInstance().getParId(idU);
            try {
                doc.reserver(u);
                lblNotif.setText("✔ " + u.getNom() + " ajouté(e) en file d'attente pour « " + doc.getTitre() + » ».");
                lblNotif.setStyle("-fx-text-fill: green;");
            } catch (Exception ex) {
                lblNotif.setText("❌ " + ex.getMessage());
                lblNotif.setStyle("-fx-text-fill: red;");
            }
        });

        HBox hbReserver = new HBox(10, new Label("Utilisateur :"), cbUtilisateur, btnReserver);
        hbReserver.setPadding(new Insets(5, 0, 0, 0));

        champRecherche.setOnAction(e -> btnRechercher.fire());

        root.getChildren().addAll(titre, barreRecherche, lblNbResultats, resultats, hbReserver, lblNotif);
        return root;
    }
}
