package bibliotheque.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Catalogue de la bibliothèque.
 * Utilise HashMap<String, List<Document>> pour indexer par catégorie.
 * Singleton – une seule instance du catalogue.
 */
public class Catalogue {
    private static Catalogue instance;

    // HashMap indexé par catégorie
    private Map<String, List<Document>> documentParCategorie;

    // Index secondaire par ISBN pour accès rapide
    private Map<String, Document> documentParIsbn;

    // Historique global de tous les emprunts
    private List<Emprunt> historiqueGlobal;

    private Catalogue() {
        documentParCategorie = new HashMap<>();
        documentParIsbn = new HashMap<>();
        historiqueGlobal = new ArrayList<>();
    }

    public static Catalogue getInstance() {
        if (instance == null) instance = new Catalogue();
        return instance;
    }

    // Ajouter un document au catalogue
    public void ajouterDocument(Document doc) {
        documentParCategorie
            .computeIfAbsent(doc.getCategorie(), k -> new ArrayList<>())
            .add(doc);
        documentParIsbn.put(doc.getIsbn(), doc);
    }

    // Recherche multicritère
    public List<Document> rechercher(String critere) {
        return documentParIsbn.values().stream()
            .filter(d -> d.correspondA(critere))
            .collect(Collectors.toList());
    }

    // Filtrer par catégorie
    public List<Document> getParCategorie(String categorie) {
        return documentParCategorie.getOrDefault(categorie, new ArrayList<>());
    }

    // Filtrer par disponibilité
    public List<Document> getDisponibles() {
        return documentParIsbn.values().stream()
            .filter(Document::estDisponible)
            .collect(Collectors.toList());
    }

    // Filtrer par type
    public List<Document> getParType(String typeDocument) {
        return documentParIsbn.values().stream()
            .filter(d -> d.getTypeDocument().equals(typeDocument))
            .collect(Collectors.toList());
    }

    public Document getParIsbn(String isbn) {
        return documentParIsbn.get(isbn);
    }

    public Collection<Document> getTousDocuments() {
        return documentParIsbn.values();
    }

    public Set<String> getCategories() {
        return documentParCategorie.keySet();
    }

    public void ajouterEmpruntHistorique(Emprunt e) {
        historiqueGlobal.add(e);
    }

    public List<Emprunt> getHistoriqueGlobal() {
        return historiqueGlobal;
    }

    // Documents les plus empruntés (statistiques)
    public List<Document> getDocumentsPlusEmpruntes(int top) {
        return documentParIsbn.values().stream()
            .sorted((a, b) -> b.getHistorique().size() - a.getHistorique().size())
            .limit(top)
            .collect(Collectors.toList());
    }
}
