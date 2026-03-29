# JOURNAL DE BORD TECHNIQUE – Projet 1 : Gestionnaire de Bibliothèque Universitaire
**Étudiant :** [Votre Nom Prénom]  
**Numéro étudiant :** [Votre numéro]  
**Université :** Université de Toamasina  
**Année :** 2025–2026

---

## Semaine 1 – Choix du sujet et initialisation

**Date :** Semaine 1 | **Durée :** 3h

### Ce qui a été réalisé :
- Lecture complète du sujet Projet 1 (Bibliothèque Universitaire)
- Création du dépôt Git privé sur GitHub
- Invitation de l'enseignant comme collaborateur
- Installation de l'environnement (JDK 17, JavaFX 21, Maven, IntelliJ IDEA)
- Identification des classes principales : Document, Utilisateur, Emprunt, Catalogue

### Ce qui a marché :
- Configuration Maven avec les dépendances JavaFX et JUnit 5

### Ce qui a échoué :
- Première tentative de configuration JavaFX sans le module-info.java → erreur au lancement

### Comment résolu :
- Ajout du plugin javafx-maven-plugin dans le pom.xml et configuration du mainClass

### Ressources consultées :
- Documentation officielle JavaFX : https://openjfx.io/
- Documentation Maven : https://maven.apache.org/

---

## Semaine 2 – Conception UML et Jalon J1

**Date :** Semaine 2 | **Durée :** 5h

### Ce qui a été réalisé :
- Diagramme de classes UML complet (Document, Utilisateur, interfaces)
- Identification des patterns : Observer (notification file d'attente), Strategy (amendes)
- Cahier des charges personnalisé basé sur les règles réelles de la bibliothèque de l'UNIV Toamasina
- Photo des règles affichées à la bibliothèque universitaire (justificatif J1)

### Utilisation de l'IA (Claude) :
- **Outil utilisé :** Claude (Anthropic)
- **Question posée :** "Comment implémenter le pattern Observer en Java pour notifier des utilisateurs quand un livre est retourné ?"
- **Ce que j'ai compris et adapté :** Le pattern Observer nécessite une interface Observer avec une méthode notifier() et une interface Observable avec ajouterObserver(), retirerObserver(), notifierObservers(). J'ai adapté cela pour que la classe Utilisateur implémente Observer et que Document implémente Observable. La file d'attente LinkedList<Utilisateur> permet de notifier dans l'ordre d'inscription.

---

## Semaine 3 – Développement des classes métier

**Date :** Semaine 3 | **Durée :** 6h

### Ce qui a été réalisé :
- Classe abstraite Document avec toutes les interfaces
- Sous-classes : Livre, Revue, TheseNumerique, DVD
- Classe abstraite Utilisateur et ses sous-classes : Etudiant, Enseignant, Externe
- Classe Emprunt avec calcul de retard (LocalDate)
- Classe Catalogue (HashMap<String, List<Document>>)
- Pattern Strategy pour les amendes (AmendeEtudiant, AmendeEnseignant, AmendeExterne)

### Ce qui a marché :
- Polymorphisme fonctionne correctement (getMaxEmprunts(), getDureeEmpruntJours())
- La Strategy change correctement le comportement selon le type d'utilisateur

### Ce qui a échoué :
- Problème de référence circulaire entre Document et Emprunt → résolu en passant par des interfaces

### Ressources consultées :
- "Design Patterns" de Gang of Four (GoF) – Pattern Strategy et Observer

---

## Semaine 4 – Tests JUnit et Jalon J2

**Date :** Semaine 4 | **Durée :** 4h

### Ce qui a été réalisé :
- 16 tests JUnit 5 couvrant : disponibilité, emprunts, retours, quotas, réservations, recherche, amendes, Observer, Catalogue
- Correction de bugs détectés par les tests (gestion de la file d'attente lors du retour)

### Ce qui a marché :
- Tests de polymorphisme (quotas différents selon type d'utilisateur)
- Test du pattern Observer (notification correcte)

### Utilisation de l'IA (Claude) :
- **Outil utilisé :** Claude
- **Question posée :** "Comment tester le pattern Observer avec JUnit 5 ?"
- **Adapté :** Vérifier le nombre de notifications avant/après un événement avec assertEquals

---

## Semaine 5 – Développement de la GUI JavaFX

**Date :** Semaine 5 | **Durée :** 7h

### Ce qui a été réalisé :
- MainApp.java avec TabPane (6 onglets)
- CataloguePanel : tableau avec filtres (catégorie, type, disponibilité)
- EmpruntPanel : formulaire d'emprunt et retour avec ComboBox
- RecherchePanel : recherche multicritère avec résultats dynamiques
- AmendePanel : calcul et affichage des amendes (Pattern Strategy visible)

### Ce qui a échoué :
- Mise à jour de la ComboBox des documents empruntés après retour → résolu avec rafraichirListe()

---

## Semaine 6 – Intégration GUI + Jalon J3

**Date :** Semaine 6 | **Durée :** 5h

### Ce qui a été réalisé :
- StatistiquesPanel avec taux d'occupation et top documents
- UtilisateurPanel avec formulaire d'ajout
- Données de démonstration réalistes (Université de Toamasina, noms malgaches)
- Démonstration devant la classe (5 min)

---

## Semaine 7 – Finalisation et polissage

**Date :** Semaine 7 | **Durée :** 4h

### Ce qui a été réalisé :
- Vérification de tous les concepts POO obligatoires
- Nettoyage du code, ajout de commentaires Javadoc
- Vérification de l'historique Git (30+ commits)

---

## Semaine 8 – Soutenance

**Date :** Semaine 8 | **Durée :** 2h

### Préparation :
- Révision de l'architecture (hiérarchie de classes, patterns)
- Préparation des réponses aux questions sur le polymorphisme et les collections

---

## Récapitulatif des concepts POO implémentés

| Concept | Implémentation |
|---|---|
| Encapsulation | Toutes les classes avec private + getters/setters |
| Héritage | Document→Livre/Revue/These/DVD, Utilisateur→Etudiant/Enseignant/Externe |
| Polymorphisme | getMaxEmprunts(), getDureeEmpruntJours(), getTypeDocument() |
| Interfaces | Empruntable, Reservable, Recherchable, Observer, Observable |
| Classe abstraite | Document, Utilisateur |
| Collections | HashMap (Catalogue), LinkedList (file d'attente), ArrayList (historique) |
| Exceptions | EmpruntImpossibleException |
| Design Patterns | Observer (notifications), Strategy (amendes) |

## Déclaration d'utilisation de l'IA ✔

L'IA (Claude) a été utilisée comme outil d'apprentissage pour :
1. Comprendre l'implémentation du pattern Observer en Java
2. Comprendre comment tester des patterns avec JUnit 5
3. Syntaxe JavaFX (TableView, ComboBox, ObservableList)

Dans tous les cas, le code a été compris, adapté et personnalisé pour le contexte malgache (noms, monnaie en Ariary, université de Toamasina).
