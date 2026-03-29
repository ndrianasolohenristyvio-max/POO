package bibliotheque.gui;

import bibliotheque.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Panel de gestion des utilisateurs.
 */
public class UtilisateurPanel {

    public Node getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label titre = new Label("Gestion des Utilisateurs");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Tableau des utilisateurs
        TableView<Utilisateur> table = new TableView<>();
        ObservableList<Utilisateur> data = FXCollections.observableArrayList(
            RegistreUtilisateurs.getInstance().getTous()
        );

        TableColumn<Utilisateur, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getId()));
        colId.setPrefWidth(80);

        TableColumn<Utilisateur, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNom() + " " + c.getValue().getPrenom()));
        colNom.setPrefWidth(180);

        TableColumn<Utilisateur, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTypeUtilisateur()));
        colType.setPrefWidth(100);

        TableColumn<Utilisateur, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        colEmail.setPrefWidth(220);

        TableColumn<Utilisateur, String> colQuota = new TableColumn<>("Quota");
        colQuota.setCellValueFactory(c -> new SimpleStringProperty(
            c.getValue().getEmpruntsActifs().size() + "/" + c.getValue().getMaxEmprunts()
        ));
        colQuota.setPrefWidth(80);

        TableColumn<Utilisateur, String> colDuree = new TableColumn<>("Durée (j)");
        colDuree.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getDureeEmpruntJours())));
        colDuree.setPrefWidth(80);

        table.getColumns().addAll(colId, colNom, colType, colEmail, colQuota, colDuree);
        table.setItems(data);
        VBox.setVgrow(table, Priority.ALWAYS);

        // Détail utilisateur – notifications
        Label lblNotifs = new Label("Notifications de l'utilisateur sélectionné :");
        lblNotifs.setStyle("-fx-font-weight: bold;");
        ListView<String> listNotifs = new ListView<>();
        listNotifs.setPrefHeight(120);

        table.getSelectionModel().selectedItemProperty().addListener((obs, old, u) -> {
            if (u != null) {
                ObservableList<String> notifs = FXCollections.observableArrayList(u.getNotifications());
                if (notifs.isEmpty()) notifs.add("Aucune notification.");
                listNotifs.setItems(notifs);
            }
        });

        // Formulaire ajout utilisateur
        TitledPane ajout = new TitledPane();
        ajout.setText("➕ Ajouter un utilisateur");
        ajout.setExpanded(false);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8); grid.setPadding(new Insets(10));

        TextField tfId = new TextField(); tfId.setPromptText("ETU004");
        TextField tfNom = new TextField(); tfNom.setPromptText("Nom");
        TextField tfPrenom = new TextField(); tfPrenom.setPromptText("Prénom");
        TextField tfEmail = new TextField(); tfEmail.setPromptText("email@univ-toamasina.mg");
        ComboBox<String> cbType = new ComboBox<>();
        cbType.getItems().addAll("Étudiant", "Enseignant", "Externe");
        cbType.setValue("Étudiant");

        Button btnAjouter = new Button("✔ Ajouter");
        btnAjouter.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        Label lblMsg = new Label();

        btnAjouter.setOnAction(e -> {
            String id = tfId.getText().trim();
            String nom = tfNom.getText().trim();
            String prenom = tfPrenom.getText().trim();
            String email = tfEmail.getText().trim();
            if (id.isEmpty() || nom.isEmpty()) {
                lblMsg.setText("❌ ID et nom obligatoires."); lblMsg.setStyle("-fx-text-fill:red;");
                return;
            }
            Utilisateur u;
            switch (cbType.getValue()) {
                case "Enseignant" -> u = new Enseignant(id, nom, prenom, email, "Informatique", "Enseignant");
                case "Externe"    -> u = new Externe(id, nom, prenom, email, "-", "-");
                default           -> u = new Etudiant(id, nom, prenom, email, "L3", "Informatique");
            }
            RegistreUtilisateurs.getInstance().ajouter(u);
            data.add(u);
            lblMsg.setText("✔ Utilisateur " + nom + " ajouté.");
            lblMsg.setStyle("-fx-text-fill:green;");
            tfId.clear(); tfNom.clear(); tfPrenom.clear(); tfEmail.clear();
        });

        grid.add(new Label("ID :"), 0, 0); grid.add(tfId, 1, 0);
        grid.add(new Label("Nom :"), 0, 1); grid.add(tfNom, 1, 1);
        grid.add(new Label("Prénom :"), 0, 2); grid.add(tfPrenom, 1, 2);
        grid.add(new Label("Email :"), 0, 3); grid.add(tfEmail, 1, 3);
        grid.add(new Label("Type :"), 0, 4); grid.add(cbType, 1, 4);
        grid.add(btnAjouter, 1, 5); grid.add(lblMsg, 0, 6, 2, 1);
        ajout.setContent(grid);

        root.getChildren().addAll(titre, table, lblNotifs, listNotifs, ajout);
        return root;
    }
}
