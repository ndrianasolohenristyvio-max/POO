package bibliotheque.gui;

import bibliotheque.model.*;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Tableau de bord statistiques.
 */
public class StatistiquesPanel {

    public Node getView() {
        VBox root = new VBox(12);
        root.setPadding(new Insets(15));

        Label titre = new Label("Tableau de Bord – Statistiques");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Statistiques générales
        Catalogue cat = Catalogue.getInstance();
        long total = cat.getTousDocuments().size();
        long dispos = cat.getTousDocuments().stream().filter(Document::estDisponible).count();
        long empruntes = total - dispos;

        GridPane stats = new GridPane();
        stats.setHgap(20); stats.setVgap(10);
        stats.setPadding(new Insets(10));
        stats.setStyle("-fx-background-color: #f5f5f5; -fx-border-radius: 8; -fx-background-radius: 8;");

        ajouterStat(stats, "📚 Total documents", String.valueOf(total), 0);
        ajouterStat(stats, "✔ Disponibles", String.valueOf(dispos), 1);
        ajouterStat(stats, "✘ Empruntés", String.valueOf(empruntes), 2);
        ajouterStat(stats, "👤 Utilisateurs", String.valueOf(RegistreUtilisateurs.getInstance().getTous().size()), 3);
        double tauxOccupation = total > 0 ? (empruntes * 100.0 / total) : 0;
        ajouterStat(stats, "📈 Taux d'occupation", String.format("%.1f%%", tauxOccupation), 4);

        // Documents les plus empruntés
        Label lblTop = new Label("📊 Top 5 documents les plus empruntés :");
        lblTop.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        ListView<String> listTop = new ListView<>();
        ObservableList<String> topItems = FXCollections.observableArrayList();
        cat.getDocumentsPlusEmpruntes(5).forEach(d ->
            topItems.add(d.getTitre() + " – " + d.getHistorique().size() + " emprunt(s)")
        );
        if (topItems.isEmpty()) topItems.add("Aucun emprunt enregistré.");
        listTop.setItems(topItems);
        listTop.setPrefHeight(150);

        // Répartition par catégorie
        Label lblCategories = new Label("📂 Documents par catégorie :");
        lblCategories.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        ListView<String> listCat = new ListView<>();
        ObservableList<String> catItems = FXCollections.observableArrayList();
        cat.getCategories().forEach(c ->
            catItems.add(c + " : " + cat.getParCategorie(c).size() + " document(s)")
        );
        listCat.setItems(catItems);
        listCat.setPrefHeight(150);

        root.getChildren().addAll(titre, stats, lblTop, listTop, lblCategories, listCat);
        return root;
    }

    private void ajouterStat(GridPane grid, String label, String valeur, int col) {
        VBox box = new VBox(4);
        box.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-radius: 6; -fx-background-radius: 6; -fx-effect: dropshadow(gaussian, #ccc, 4, 0, 0, 2);");
        Label lbl = new Label(label);
        lbl.setStyle("-fx-text-fill: #666;");
        Label val = new Label(valeur);
        val.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1565C0;");
        box.getChildren().addAll(lbl, val);
        grid.add(box, col, 0);
    }
}
