
public class Article {
	
	// Attributs
    private int idArticle;
    private String nom;
    private String description;
    private String taille;
    private String couleur;
    private String etat;
    private int idCategorie;
    private int idVente;
    private int idDon;
    private int idUtilisateur;
    
    // Constructeurs
    public Article(int idArticle, String nom, String description, String taille, String couleur, String etat, int idCategorie, int idVente, int idDon, int idUtilisateur) {
        this.idArticle = idArticle;
        this.nom = nom;
        this.description = description;
        this.taille = taille;
        this.couleur = couleur;
        this.etat = etat;
        this.idCategorie = idCategorie;
        this.idVente = idVente;
        this.idDon = idDon;
        this.idUtilisateur = idUtilisateur;
    }
    
    // Getteurs/Setteurs
    public int getIdArticle() {
        return this.idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getTaille() {
        return this.taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }
    
    
    public String getCouleur() {
        return this.couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
    
    
    public String getEtat() {
    	return this.etat;
    }
    
    public void setEtat(String etat) {
    	this.etat = etat;
    }
    
    public int getIdCategorie() {
        return this.idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getIdVente() {
        return this.idVente;
    }

    public void setIdVente(int idVente) {
        this.idVente = idVente;
    }

    public int getIdDon() {
        return this.idDon;
    }

    public void setIdDon(int idDon) {
        this.idDon = idDon;
    }

    public int getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    public String toString() {
        return this.nom + " (" + this.taille + ")";
    }
}