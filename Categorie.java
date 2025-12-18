/**
 * Cette classe définit l'objet Categorie qui permet de classifier les articles du catalogue.
 * Elle stocke l'identifiant unique, le nom de la catégorie ainsi qu'une brève description.
 * Cet objet est principalement utilisé pour organiser les vêtements et remplir les listes déroulantes.
 */
public class Categorie {
	
    // Liste des attributs définissant la catégorie en base de données
    private int idCategorie;
    private String nom;
    private String description;
    
    // Constructeur permettant d'instancier une catégorie avec ses données complètes
    public Categorie(int idCategorie, String nom, String description) {
        this.idCategorie = idCategorie;
        this.nom = nom;
        this.description = description;
    }
    
    // Accesseurs et mutateurs pour manipuler les données de l'objet
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

    // Méthode permettant d'afficher directement le nom dans les composants graphiques
    @Override
    public String toString() {
        return this.nom;
    }
}