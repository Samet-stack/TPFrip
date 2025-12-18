import java.util.Date;

/**
 * Cette classe définit l'objet Vente utilisé pour organiser les événements de l'association.
 * Elle permet de stocker et manipuler les informations telles que le titre, la date et le lieu.
 * Sert de passerelle entre les données de la base SQL et l'affichage dans les tableaux Swing.
 */
public class Vente {
	
	//  Liste des propriétés de la vente
    private int idVente; // Identifiant unique (Clé primaire)
    private String titre; // Nom de l'événement
    private Date dateVente; // Date de réalisation de la vente
    private String lieu; // Localisation de l'événement
    private String statut; // État de la vente (ex: en préparation)

    // Constructeur pour initialiser un objet Vente complet 
    public Vente(int idVente, String titre, Date dateVente, String lieu, String statut) {
        // Assignation des valeurs reçues aux attributs de la classe
        this.idVente = idVente;
        this.titre = titre;
        this.dateVente = dateVente;
        this.lieu = lieu;
        this.statut = statut;
    }

    //  Liste des Accesseurs
    
    public int getIdVente() {
        return this.idVente;
    }

    public void setIdVente(int idVente) {
        this.idVente = idVente;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDateVente() {
        return this.dateVente;
    }

    public void setDateVente(Date dateVente) {
        this.dateVente = dateVente;
    }

    public String getLieu() {
        return this.lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getStatut() {
        return this.statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    //  Redéfinition de la méthode d'affichage 
    @Override
    public String toString() {
        // Retourne une version texte simplifiée pour les JComboBox (Listes déroulantes)
        return this.titre + " (" + this.lieu + ")";
    }
}