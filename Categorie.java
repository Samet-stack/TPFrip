
public class Categorie {
	
	// Attributs
    private int idCategorie;
    private String nom;
    private String description;
    
    // Constructeurs
    public Categorie(int idCategorie, String nom, String description) {
        this.idCategorie = idCategorie;
        this.nom = nom;
        this.description = description;
    }
    
    // Getteurs/Setteurs
    public int getIdCategorie() {
        return this.idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
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

    @Override
    public String toString() {
        return this.nom;
    }
}