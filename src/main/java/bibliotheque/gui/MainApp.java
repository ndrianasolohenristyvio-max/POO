package bibliotheque.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import bibliotheque.model.*;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bibliothèque Universitaire – UNIV Toamasina");

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabCatalogue   = new Tab("📚 Catalogue",    new CataloguePanel().getView());
        Tab tabEmprunt     = new Tab("📋 Emprunts",     new EmpruntPanel().getView());
        Tab tabRecherche   = new Tab("🔍 Recherche",    new RecherchePanel().getView());
        Tab tabAmendes     = new Tab("💰 Amendes",      new AmendePanel().getView());
        Tab tabStats       = new Tab("📊 Statistiques", new StatistiquesPanel().getView());
        Tab tabUtilisateurs= new Tab("👤 Utilisateurs", new UtilisateurPanel().getView());

        tabPane.getTabs().addAll(tabCatalogue, tabEmprunt, tabRecherche, tabAmendes, tabStats, tabUtilisateurs);

        // Données de démonstration
        DonneesDemo.initialiser();

        Scene scene = new Scene(tabPane, 1100, 700);
        scene.getStylesheets().add(getClass().getResource("/style.css") != null
            ? getClass().getResource("/style.css").toExternalForm() : "");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
