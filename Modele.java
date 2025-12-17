import java.sql.*;
import java.util.ArrayList;

public class Modele {

    private Connection connexion;
    private ResultSet rs;
    private PreparedStatement pst;

    public Modele() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connexion = DriverManager.getConnection("jdbc:mysql://localhost/fripouilles?serverTimezone=UTC", "root", "");
            System.out.println("Connexion réussie à la base Fripouilles");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver manquant : " + e);
        } catch (SQLException e) {
            System.out.println("Erreur connexion : " + e);
        }
    }

    public void fermerConnexion() {
        try {
            if (connexion != null) connexion.close();
        } catch (SQLException e) {
            System.out.println("Erreur fermeture : " + e);
        }
    }

    //Authentification

    public Utilisateur authentifier(String login, String mdp) {
        Utilisateur user = null;
        String sql = "SELECT * FROM utilisateur WHERE login = ? AND mdp = ?";
        try {
            pst = connexion.prepareStatement(sql);
            pst.setString(1, login);
            pst.setString(2, mdp);
            rs = pst.executeQuery();
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

    // gestion des ventes pour secrétaire et maire

    public ArrayList<Vente> getLesVentes() {
        ArrayList<Vente> liste = new ArrayList<>();
        String sql = "SELECT * FROM vente";
        try {
            pst = connexion.prepareStatement(sql);
            rs = pst.executeQuery();
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

    public boolean ajouterVente(String titre, String dateStr, String lieu) {
        String sql = "INSERT INTO vente (titre, date_vente, lieu, statut) VALUES (?, ?, ?, 'EN_PREPARATION')";
        try {
            pst = connexion.prepareStatement(sql);
            pst.setString(1, titre);
            pst.setString(2, dateStr); 
            pst.setString(3, lieu);
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur ajouterVente : " + e);
            return false;
        }
    }

    // gestion des categorie (liste déroulante)

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

    // gestion des article pour bénévoles

    public boolean ajouterArticleComplet(String nomArticle, String description, String taille, String couleur, String etat, int idCateg, int idVente, int idUser, String nomDonneur) {
        // 1. On crée d'abord le don
        int idDonCree = -1;
        try {
            String sqlDon = "INSERT INTO don (nom_donneur, date_don) VALUES (?, NOW())";
            pst = connexion.prepareStatement(sqlDon, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, nomDonneur);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                idDonCree = rs.getInt(1);
            }
            rs.close();
            pst.close();

            if (idDonCree == -1) return false;

            // 2. On crée l'article lié au don, à la vente et au bénévole
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