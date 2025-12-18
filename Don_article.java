/**
 * Cette classe gère la relation technique entre un don et un article spécifique.
 * Elle sert de table de liaison pour identifier l'origine de chaque vêtement.
 * Elle permet de maintenir l'intégrité des données entre les dons et le catalogue.
 */
public class Don_article {
	
	
	private int idDon;      // Référence à l'identifiant unique du don
	private int idArticle;  // Référence à l'identifiant unique de l'article lié
	

	public Don_article(int unIdDon, int unIdArticle) {
		// Affectation de l'ID du don passé en paramètre
		this.idDon = unIdDon;
		// Affectation de l'ID de l'article passé en paramètre
		this.idArticle = unIdArticle;
	}
}