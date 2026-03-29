package bibliotheque.gui;

import bibliotheque.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

/**
 * Panel d'affichage du catalogue avec filtres.
 */
public class CataloguePanel {

    private TableView<Document> tableView;
    private ObservableList<Document> data;
    private ComboBox<String> filtreCategorie;
    private ComboBox<String> filtreType;
    private ComboBox<String> filtreDispo;

    public Node getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        // --- Titre ---
        Label titre = new Label("Catalogue de la Bibliothèque");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // --- Filtres ---
        HBox filtres = new HBox(10);
        filtres.setPadding(new Insets(5));

        filtreCategorie = new ComboBox<>();
        filtreCategorie.getItems().add("Toutes catégories");
        filtreCategorie.getItems().addAll(Catalogue.getInstance().getCategories());
        filtreCategorie.setValue("Toutes catégories");

        filtreType = new ComboBox<>();
        filtreType.getItems().addAll("Tous types", "Livre", "Revue", "Thèse Numérique", "DVD");
        filtreType.setValue("Tous types");

        filtreDispo = new ComboBox<>();
        filtreDispo.getItems().addAll("Tous", "Disponibles", "Empruntés");
        filtreDispo.setValue("Tous");

        Button btnFiltrer = new Button("🔍 Filtrer");
        btnFiltrer.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnFiltrer.setOnAction(e -> appliquerFiltres());

        Button btnReset = new Button("↺ Reset");
        btnReset.setOnAction(e -> {
            filtreCategorie.setValue("Toutes catégories");
            filtreType.setValue("Tous types");
            filtreDispo.setValue("Tous");
            chargerDonnees();
        });

        filtres.getChildren().addAll(
            new Label("Catégorie:"), filtreCategorie,
            new Label("Type:"), filtreType,
            new Label("Dispo:"), filtreDispo,
            btnFiltrer, btnReset
        );

        // --- Tableau ---
        tableView = new TableView<>();
        data = FXCollections.observableArrayList();

        TableColumn<Document, String> colIsbn = new TableColumn<>("ISBN");
        colIsbn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIsbn()));
        colIsbn.setPrefWidth(150);

        TableColumn<Document, String> colTitre = new TableColumn<>("Titre");
        colTitre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTitre()));
        colTitre.setPrefWidth(250);

        TableColumn<Document, String> colAuteur = new TableColumn<>("Auteur");
        colAuteur.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAuteur()));
        colAuteur.setPrefWidth(150);

        TableColumn<Document, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTypeDocument()));
        colType.setPrefWidth(120);

        TableColumn<Document, String> colCategorie = new TableColumn<>("Catégorie");
        colCategorie.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategorie()));
        colCategorie.setPrefWidth(120);

        TableColumn<Document, String> colDispo = new TableColumn<>("Disponible");
        colDispo.setCellValueFactory(c -> new SimpleStringProperty(
            c.getValue().estDisponible() ? "✔ Oui" : "✘ Non"));
        colDispo.setPrefWidth(90);

        TableColumn<Document, String> colEmprunteur = new TableColumn<>("Emprunteur");
        colEmprunteur.setCellValueFactory(c -> new SimpleStringProperty(
            c.getValue().getEmprunteurActuel() != null
                ? c.getValue().getEmprunteurActuel().getNom()
                : "-"));
        colEmprunteur.setPrefWidth(120);

        tableView.getColumns().addAll(colIsbn, colTitre, colAuteur, colType, colCategorie, colDispo, colEmprunteur);
        tableView.setItems(data);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        chargerDonnees();

        // --- Détail document sélectionné ---
        TextArea details = new TextArea();
        details.setEditable(false);
        details.setPrefHeight(100);
        details.setPromptText("Sélectionnez un document pour voir les détails...");

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, old, doc) -> {
            if (doc != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Titre : ").append(doc.getTitre()).append("\n");
                sb.append("Auteur : ").append(doc.getAuteur()).append("\n");
                sb.append("Type : ").append(doc.getTypeDocument()).append("\n");
                sb.append("Catégorie : ").append(doc.getCategorie()).append("\n");
                sb.append("Disponible : ").append(doc.estDisponible() ? "Oui" : "Non").append("\n");
                sb.append("Nb emprunts historique : ").append(doc.getHistorique().size()).append("\n");
                sb.append("File d'attente : ").append(doc.getFileAttente().size()).append(" personne(s)\n");
                sb.append("Tags : ").append(String.join(", ", doc.getTags())).append("\n");
                details.setText(sb.toString());
            }
        });

        root.getChildren().addAll(titre, filtres, tableView, new Label("Détails :"), details);
        return root;
    }

    private void chargerDonnees() {
        data.clear();
        data.addAll(Catalogue.getInstance().getTousDocuments());
    }

    private void appliquerFiltres() {
        Collection<Document> tous = Catalogue.getInstance().getTousDocuments();
        List<Document> filtres = new ArrayList<>(tous);

        String cat = filtreCategorie.getValue();
        if (cat != null && !cat.equals("Toutes catégories"))
            filtres.removeIf(d -> !d.getCategorie().equals(cat));

        String type = filtreType.getValue();
        if (type != null && !type.equals("Tous types"))
            filtres.removeIf(d -> !d.getTypeDocument().equals(type));

        String dispo = filtreDispo.getValue();
        if ("Disponibles".equals(dispo)) filtres.removeIf(d -> !d.estDisponible());
        if ("Empruntés".equals(dispo))   filtres.removeIf(Document::estDisponible);

        data.clear();
        data.addAll(filtres);
    }
}
