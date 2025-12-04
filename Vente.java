import java.util.Date;

public class Vente {

	private int idVente;
	private String titre;
	private Date date_vente;
	private String description;
	private String statut;
	
	public Vente(int unIdVente, String unTitre, Date uneDate_vente, String uneDescription, String unStatut) {
		this.idVente = unIdVente;
		this.titre = unTitre;
		this.date_vente = uneDate_vente;
		this.description = uneDescription;
		this.statut = unStatut;
	}

	public String getTitre() {
		// TODO Auto-generated method stub
		return null;
	}
}