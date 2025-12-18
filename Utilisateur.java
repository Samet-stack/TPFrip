/**
 * Cette classe définit l'objet Utilisateur qui gère les profils de l'application.
 * Elle permet de stocker les informations personnelles, les identifiants et le rôle.
 * Cet objet est utilisé pour gérer les sessions de connexion et les droits d'accès.
 */
public class Utilisateur {
   
	//   Attributs : Informations stockées pour chaque utilisateur  
    private int idUtilisateur;   // Identifiant unique dans la base de données
    private String login;        // Identifiant de connexion
    private String role;         // Rôle de l'utilisateur (MAIRE, SECRETAIRE, BENEVOLE)
    private String nom;          // Nom de famille
    private String prenom;       // Prénom
    private String email;        // Adresse de courrier électronique
    private String telephone;    // Numéro de téléphone de contact

    // Constructeur : Initialisation d'un utilisateur avec toutes ses données 
    public Utilisateur(int idUtilisateur, String login, String role, String nom, String prenom, String email, String telephone) {
        // Assignation des paramètres reçus aux variables de l'instance
        this.idUtilisateur = idUtilisateur;
        this.login = login;
        this.role = role;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }
    
    // Getteurs / Setteurs : Méthodes d'accès et de modification des données
    
    public int getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}