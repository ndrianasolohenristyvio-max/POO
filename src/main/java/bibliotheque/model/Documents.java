package bibliotheque.model;

// ======================== LIVRE ========================
class Livre extends Document {
    private String editeur;
    private int nbPages;

    public Livre(String isbn, String titre, String auteur, int annee, String categorie, String editeur, int nbPages) {
        super(isbn, titre, auteur, annee, categorie);
        this.editeur = editeur;
        this.nbPages = nbPages;
    }

    @Override public String getTypeDocument() { return "Livre"; }
    public String getEditeur() { return editeur; }
    public int getNbPages() { return nbPages; }
}

// ======================== REVUE ========================
class Revue extends Document {
    private int numero;
    private String periodicite; // Mensuelle, Trimestrielle, etc.

    public Revue(String isbn, String titre, String auteur, int annee, String categorie, int numero, String periodicite) {
        super(isbn, titre, auteur, annee, categorie);
        this.numero = numero;
        this.periodicite = periodicite;
    }

    @Override public String getTypeDocument() { return "Revue"; }
    public int getNumero() { return numero; }
    public String getPeriodicite() { return periodicite; }
}

// ======================== THESE NUMERIQUE ========================
class TheseNumerique extends Document {
    private String universite;
    private String directeur;
    private String specialite;

    public TheseNumerique(String isbn, String titre, String auteur, int annee, String categorie,
                          String universite, String directeur, String specialite) {
        super(isbn, titre, auteur, annee, categorie);
        this.universite = universite;
        this.directeur = directeur;
        this.specialite = specialite;
    }

    @Override public String getTypeDocument() { return "Thèse Numérique"; }
    public String getUniversite() { return universite; }
    public String getDirecteur() { return directeur; }
    public String getSpecialite() { return specialite; }
}

// ======================== DVD ========================
class DVD extends Document {
    private int dureeMins;
    private String realisateur;
    private String langue;

    public DVD(String isbn, String titre, String auteur, int annee, String categorie,
               int dureeMins, String realisateur, String langue) {
        super(isbn, titre, auteur, annee, categorie);
        this.dureeMins = dureeMins;
        this.realisateur = realisateur;
        this.langue = langue;
    }

    @Override public String getTypeDocument() { return "DVD"; }
    public int getDureeMins() { return dureeMins; }
    public String getRealisateur() { return realisateur; }
    public String getLangue() { return langue; }
}
