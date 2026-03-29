# Projet 1 – Gestionnaire de Bibliothèque Universitaire
**Université de Toamasina | L3 Informatique | POO Java 2025–2026**

## Description
Application JavaFX de gestion d'une bibliothèque universitaire : catalogue, emprunts, retours, réservations, amendes, recherche multicritère.

## Architecture

```
src/main/java/bibliotheque/
├── model/
│   ├── Document.java          (abstract – Empruntable, Reservable, Recherchable, Observable)
│   ├── Documents.java         (Livre, Revue, TheseNumerique, DVD)
│   ├── Utilisateur.java       (abstract – Observer)
│   ├── Etudiant.java          (max 3 emprunts, 14 jours)
│   ├── Enseignant.java        (max 10 emprunts, 30 jours)
│   ├── Externe.java           (max 1 emprunt, 7 jours)
│   ├── Emprunt.java           (suivi retard, calcul jours)
│   ├── Catalogue.java         (Singleton, HashMap par catégorie)
│   └── RegistreUtilisateurs.java (Singleton)
├── interfaces/
│   ├── Empruntable.java
│   ├── Reservable.java
│   ├── Recherchable.java
│   ├── Observer.java
│   └── Observable.java
├── patterns/
│   ├── StrategieAmende.java   (interface + AmendeEtudiant/Enseignant/Externe)
│   └── CalculateurAmende.java
├── exceptions/
│   └── EmpruntImpossibleException.java
└── gui/
    ├── MainApp.java
    ├── CataloguePanel.java
    ├── EmpruntPanel.java
    ├── RecherchePanel.java
    ├── AmendePanel.java
    ├── StatistiquesPanel.java
    ├── UtilisateurPanel.java
    └── DonneesDemo.java
```

## Concepts POO

| Concept | Détail |
|---|---|
| Héritage (2 niveaux) | Document→Livre, Utilisateur→Etudiant |
| Polymorphisme | getMaxEmprunts(), getTypeDocument() |
| Interfaces (5) | Empruntable, Reservable, Recherchable, Observer, Observable |
| Classe abstraite | Document, Utilisateur |
| Collections | HashMap, LinkedList, ArrayList |
| Design Patterns | **Observer** (notifications) + **Strategy** (amendes) |
| Exception | EmpruntImpossibleException |

## Lancer le projet

```bash
mvn javafx:run
```

## Tests

```bash
mvn test
```

16 tests JUnit 5 couvrant tous les cas.

## Contexte local
Modélise la bibliothèque de l'Université de Toamasina avec :
- Noms malgaches pour les utilisateurs de démonstration
- Amendes en Ariary (Ar)
- Données : Rakoto, Rabe, Rasolofo, Rakotondrabe...
