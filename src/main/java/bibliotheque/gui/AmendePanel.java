package bibliotheque.gui;

import bibliotheque.model.*;
import bibliotheque.patterns.CalculateurAmende;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Panel d'affichage et de calcul des amendes (Pattern Strategy).
 */
public class AmendePanel {

    public Node getView() {
        VBox root = new VBox(12);
        root.setPadding(new Insets(15));

        Label titre = new Label("Gestion des Amendes");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label info = new Label("ℹ Stratégies : Étudiant = 200 Ar/j | Enseignant = 100 Ar/j (grâce 3j) | Externe = 500 Ar/j");
        info.setStyle("-fx-text-fill: #555; -fx-font-style: italic;");

        TableView<String[]> table = new TableView<>();
        ObservableList<String[]> data = FXCollections.observableArrayList();

        TableColumn<String[], String> colUser = new TableColumn<>("Utilisateur");
        colUser.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue()[0]));
        colUser.setPrefWidth(180);

        TableColumn<String[], String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue()[1]));
        colType.setPrefWidth(100);

        TableColumn<String[], String> colDoc = new TableColumn<>("Document");
        colDoc.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue()[2]));
        colDoc.setPrefWidth(220);

        TableColumn<String[], String> colRetard = new TableColumn<>("Jours retard");
        colRetard.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue()[3]));
        colRetard.setPrefWidth(100);

        TableColumn<String[], String> colAmende = new TableColumn<>("Amende (Ar)");
        colAmende.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue()[4]));
        colAmende.setPrefWidth(120);

        table.getColumns().addAll(colUser, colType, colDoc, colRetard, colAmende);
        table.setItems(data);
        VBox.setVgrow(table, Priority.ALWAYS);

        Button btnActualiser = new Button("🔄 Actualiser");
        btnActualiser.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        Label lblTotal = new Label("Total amendes : 0 Ar");
        lblTotal.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #d32f2f;");

        btnActualiser.setOnAction(e -> {
            data.clear();
            double total = 0;
            for (Utilisateur u : RegistreUtilisateurs.getInstance().getTous()) {
                for (Emprunt emp : u.getEmpruntsActifs()) {
                    if (emp.estEnRetard()) {
                        double amende = CalculateurAmende.calculer(emp);
                        total += amende;
                        data.add(new String[]{
                            u.getNom() + " " + u.getPrenom(),
                            u.getTypeUtilisateur(),
                            emp.getDocument().getTitre(),
                            String.valueOf(emp.joursDeRetard()),
                            String.format("%.0f", amende)
                        });
                    }
                }
            }
            if (data.isEmpty()) {
                lblTotal.setText("✔ Aucune amende en cours.");
                lblTotal.setStyle("-fx-text-fill: green;");
            } else {
                lblTotal.setText("Total amendes : " + String.format("%.0f", total) + " Ar");
                lblTotal.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #d32f2f;");
            }
        });

        // Déclencher l'actualisation immédiate
        btnActualiser.fire();

        root.getChildren().addAll(titre, info, btnActualiser, table, lblTotal);
        return root;
    }
}
