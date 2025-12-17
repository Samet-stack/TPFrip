import java.util.Date;

public class Vente {
	
	// Attributs
    private int idVente;
    private String titre;
    private Date dateVente;
    private String lieu;
    private String statut;

    // Constructeurs
    public Vente(int idVente, String titre, Date dateVente, String lieu, String statut) {
        this.idVente = idVente;
        this.titre = titre;
        this.dateVente = dateVente;
        this.lieu = lieu;
        this.statut = statut;
    }

    // Getteurs/Setteurs
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

    @Override
    public String toString() {
        return this.titre + " (" + this.lieu + ")";
    }
}