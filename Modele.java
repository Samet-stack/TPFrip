import java.sql.*;
import java.util.ArrayList;

/**
 * Cette classe centralise toutes les interactions avec la base de données MySQL.
 * Elle gère la connexion, l'exécution des requêtes SQL et la transformation des résultats en objets.
 * C'est le cœur logique (Modèle) qui permet de manipuler les données de l'application Fripouilles.
 */
public class Modele {

    // Objets JDBC pour la connexion et l'exécution des requêtes
    private Connection connexion;
    private ResultSet rs;
    private PreparedStatement pst;

    public Modele() {
        try {
            // Chargement du pilote JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Établissement de la connexion avec les paramètres (URL, utilisateur, mot de passe)
            this.connexion = DriverManager.getConnection("jdbc:mysql://localhost/fripouilles?serverTimezone=UTC", "root", "");
            System.out.println("Connexion réussie à la base Fripouilles");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver manquant : " + e);
        } catch (SQLException e) {
            System.out.println("Erreur connexion : " + e);
        }
    }

    /**
     * Ferme proprement la connexion à la base de données pour libérer les ressources.
     */
    public void fermerConnexion() {
        try {
            if (connexion != null) connexion.close();
        } catch (SQLException e) {
            System.out.println("Erreur fermeture : " + e);
        }
    }

    // SECTION : AUTHENTIFICATION 

    /**
     * Vérifie les identifiants en base et retourne un objet Utilisateur si le login réussit.
     */
    public Utilisateur authentifier(String login, String mdp) {
        Utilisateur user = null;
        // Requête sécurisée avec des "?" pour éviter les injections SQL
        String sql = "SELECT * FROM utilisateur WHERE login = ? AND mdp = ?";
        try {
            pst = connexion.prepareStatement(sql);
            pst.setString(1, login); // Remplacement du 1er "?"
            pst.setString(2, mdp);   // Remplacement du 2ème "?"
            rs = pst.executeQuery();
            
            // Si une ligne correspond, on crée l'objet Utilisateur
            if (rs.next()) {
                user = new Utilisateur(
                    rs.getInt("idUtilisateur"),
                    rs.getString("login"),
                    rs.getString("role"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("telephone")
                );
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Erreur auth : " + e);
        }
        return user;
    }

    // SECTION : GESTION DES VENTES
    /**
     * Récupère la liste complète des ventes enregistrées.
     */
    public ArrayList<Vente> getLesVentes() {
        ArrayList<Vente> liste = new ArrayList<>();
        String sql = "SELECT * FROM vente";
        try {
            pst = connexion.prepareStatement(sql);
            rs = pst.executeQuery();
            // Parcours du résultat ligne par ligne
            while (rs.next()) {
                liste.add(new Vente(
                    rs.getInt("idVente"),
                    rs.getString("titre"),
                    rs.getDate("date_vente"),
                    rs.getString("lieu"),
                    rs.getString("statut")
                ));
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Erreur getLesVentes : " + e);
        }
        return liste;
    }

    /**
     * Insère une nouvelle vente avec un statut par défaut "EN_PREPARATION".
     */
    public boolean ajouterVente(String titre, String dateStr, String lieu) {
        String sql = "INSERT INTO vente (titre, date_vente, lieu, statut) VALUES (?, ?, ?, 'EN_PREPARATION')";
        try {
            pst = connexion.prepareStatement(sql);
            pst.setString(1, titre);
            pst.setString(2, dateStr); 
            pst.setString(3, lieu);
            pst.executeUpdate(); // Utilisation de executeUpdate pour les INSERT
            pst.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur ajouterVente : " + e);
            return false;
        }
    }

    // SECTION : GESTION DES CATÉGORIES

    /**
     * Récupère les catégories pour remplir les listes déroulantes (JComboBox).
     */
    public ArrayList<Categorie> getLesCategories() {
        ArrayList<Categorie> liste = new ArrayList<>();
        String sql = "SELECT * FROM categorie";
        try {
            pst = connexion.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                liste.add(new Categorie(
                    rs.getInt("idCategorie"),
                    rs.getString("nom"),
                    rs.getString("description")
                ));
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Erreur getLesCategories : " + e);
        }
        return liste;
    }

    //  SECTION : GESTION DES ARTICLES (BÉNÉVOLES)

    /**
     * Opération complexe : Crée d'abord un don, récupère son ID, puis crée l'article lié.
     */
    public boolean ajouterArticleComplet(String nomArticle, String description, String taille, String couleur, String etat, int idCateg, int idVente, int idUser, String nomDonneur) {
        int idDonCree = -1;
        try {
            // Étape 1 : Création du don et récupération de la clé générée automatiquement
            String sqlDon = "INSERT INTO don (nom_donneur, date_don) VALUES (?, NOW())";
            pst = connexion.prepareStatement(sqlDon, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, nomDonneur);
            pst.executeUpdate();
            
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                idDonCree = rs.getInt(1); // On récupère l'ID du don fraîchement créé
            }
            rs.close();
            pst.close();

            if (idDonCree == -1) return false;

            // Étape 2 : Création de l'article en utilisant l'ID du don précédemment obtenu
            String sqlArt = "INSERT INTO article (nom, description, taille, couleur, etat, idCategorie, idVente, idDon, idUtilisateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst = connexion.prepareStatement(sqlArt);
            pst.setString(1, nomArticle);
            pst.setString(2, description);
            pst.setString(3, taille);
            pst.setString(4, couleur);
            pst.setString(5, etat);
            pst.setInt(6, idCateg);
            pst.setInt(7, idVente);
            pst.setInt(8, idDonCree);
            pst.setInt(9, idUser);
            
            pst.executeUpdate();
            pst.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur ajouterArticleComplet : " + e);
            return false;
        }
    }

    /**
     * Ajoute un nouvel utilisateur avec le rôle figé à 'BENEVOLE'.
     */
    public boolean ajouterBenevole(String login, String mdp, String nom, String prenom, String email, String tel) {
    	String sql = "INSERT INTO utilisateur (login, mdp, role, nom, prenom, email, telephone) VALUES (?, ?, 'BENEVOLE', ?, ?, ?, ?)";
        try {
        	PreparedStatement pst = connexion.prepareStatement(sql);
        	pst.setString(1, login);
        	pst.setString(2, mdp);
        	pst.setString(3, nom);
        	pst.setString(4, prenom);
        	pst.setString(5, email);
        	pst.setString(6, tel);
        	pst.executeUpdate(); 
        	pst.close();
            return true;
        } catch (SQLException e) {
        	System.out.println("Erreur ajout bénévole : " + e.getMessage());
        	return false;
        }
    }			
        
    /**
     * Met à jour les informations d'une vente existante à partir de son ID.
     */
    public boolean modifierVente(int idVente, String titre, String date, String lieu) {
        String sql = "UPDATE vente SET titre = ?, date_vente = ?, lieu = ? WHERE idVente = ?";
        try {
            PreparedStatement pst = connexion.prepareStatement(sql);
            pst.setString(1, titre);
            pst.setString(2, date);
            pst.setString(3, lieu);
            pst.setInt(4, idVente);
            pst.executeUpdate(); 
            pst.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur modification vente : " + e.getMessage());
            return false;
        }
    }
    										
    /**
     * Supprime une vente de la base de données via son identifiant.
     */
    public boolean supprimerVente(int idVente) {
        String sql = "DELETE FROM vente WHERE idVente = ?";
        try {
            PreparedStatement pst = connexion.prepareStatement(sql);
            pst.setInt(1,idVente);
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            // Échec potentiel si des articles sont encore rattachés à cette vente (Contrainte d'intégrité)
            System.out.println("Erreur suppression vente : " + e.getMessage());
            return false;
        }
    }
    	    	
    /**
     * Récupère tous les articles associés à une vente spécifique pour l'affichage du catalogue.
     */
    public ArrayList<Article> getCatalogue(int idVente) {
        ArrayList<Article> liste = new ArrayList<>();
        String sql = "SELECT * FROM article WHERE idVente = ?";
        try {
            pst = connexion.prepareStatement(sql);
            pst.setInt(1, idVente);
            rs = pst.executeQuery();
            while (rs.next()) {
                liste.add(new Article(
                    rs.getInt("idArticle"),
                    rs.getString("nom"),
                    rs.getString("description"),
                    rs.getString("taille"),
                    rs.getString("couleur"),
                    rs.getString("etat"),
                    rs.getInt("idCategorie"),
                    rs.getInt("idVente"),
                    rs.getInt("idDon"),
                    rs.getInt("idUtilisateur")
                ));
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Erreur getCatalogue : " + e);
        }
        return liste;
    }
}