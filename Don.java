import java.sql.Date;

/**
 * Cette classe définit l'objet Don qui enregistre les contributions des donateurs.
 * Elle permet de stocker l'identité du donneur, son contact et la date de l'apport.
 * Cet objet est utilisé pour assurer la traçabilité des articles reçus par l'association.
 */
public class Don {
	
	//  Attributs : Informations relatives au donateur et à la transaction 
	private int idDon;          // Identifiant unique du don en base de données
	private String nomDonneur;   // Nom complet de la personne effectuant le don
	private String emailDonneur; // Adresse de contact du donneur
	private Date dateDon;       // Date précise de la réception du don
	
    // Constructeur : Initialisation complète d'un nouvel objet Don 
	public Don(int idDon, String nomDonneur, String emailDonneur, Date dateDon) {
		// Assignation des valeurs passées en paramètres aux attributs de l'instance
		this.idDon = idDon;
		this.nomDonneur = nomDonneur;
		this.emailDonneur = emailDonneur;
		this.dateDon = dateDon;
	}
	
    //  Getteurs et Setteurs : Méthodes d'accès et de modification
	public int getIdDon() {
		return idDon;
	}

	public void setIdDon(int idDon) {
		this.idDon = idDon;
	}

	public String getNomDonneur() {
		return nomDonneur;
	}

	public void setNomDonneur(String nomDonneur) {
		this.nomDonneur = nomDonneur;
	}

	public String getEmailDonneur() {
		return emailDonneur;
	}

	public void setEmailDonneur(String emailDonneur) {
		this.emailDonneur = emailDonneur;
	}

	public Date getDateDon() {
		return dateDon;
	}

	public void setDateDon(Date dateDon) {
		this.dateDon = dateDon;
	}
	
	//  Méthode d'affichage personnalisée
	@Override
	public String toString() {
		// Retourne le nom du donneur suivi de la date entre parenthèses
		// Pratique pour l'affichage dans les journaux de logs ou les interfaces
		return nomDonneur + " (" + dateDon + ")";
	}
}