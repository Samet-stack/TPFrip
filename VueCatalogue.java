import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Cette vue affiche sous forme de tableau tous les articles liés à une vente.
 * Elle permet de consulter les détails (taille, couleur, état) des vêtements.
 * Les données sont chargées dynamiquement depuis la base de données via le modèle.
 */
public class VueCatalogue extends JPanel {
    // Référence au modèle pour l'accès aux données SQL
    private Modele modele;
    // Composant graphique affichant les données en colonnes
    private JTable table;
    // Gestionnaire de données du tableau (permet d'ajouter/supprimer des lignes)
    private DefaultTableModel modelTable;

    public VueCatalogue(Modele m) {
        this.modele = m;
        // Utilisation d'un BorderLayout pour que le tableau occupe tout l'espace central
        this.setLayout(new BorderLayout());

        //  Titre de l'écran
        // Création d'un label centré avec une police en gras pour l'en-tête
        JLabel titre = new JLabel("Catalogue des Articles", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(titre, BorderLayout.NORTH);

        // --- Configuration du tableau ---
        // Initialisation du modèle de données avec les titres des colonnes
        modelTable = new DefaultTableModel();
        modelTable.setColumnIdentifiers(new String[]{"Nom", "Description", "Taille", "Couleur", "État"});
        
        // Création de la table graphique basée sur le modèle de données
        table = new JTable(modelTable);
        // Ajout d'un JScrollPane pour permettre le défilement si la liste est longue
        this.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Remplit le tableau en utilisant la méthode getCatalogue du modèle.
     * Cette méthode vide l'affichage actuel avant de recharger les nouvelles données.
     */
    public void chargerArticles(int idVente) {
        //  ÉTAPE 1 : Nettoyage 
        // On remet le nombre de lignes à zéro pour éviter d'empiler les anciens articles
        modelTable.setRowCount(0); 

        //  ÉTAPE 2 : Récupération des données 
        // On interroge le modèle SQL pour récupérer la liste des objets Article
        ArrayList<Article> liste = modele.getCatalogue(idVente);
        
        //  ÉTAPE 3 : Remplissage du tableau 
        // On parcourt chaque article reçu de la base de données
        for (Article a : liste) {
            // Création d'un tableau d'objets représentant une ligne (une colonne par attribut)
            Object[] ligne = {
                a.getNom(),           // Colonne 1 : Nom
                a.getDescription(),    // Colonne 2 : Description
                a.getTaille(),        // Colonne 3 : Taille
                a.getCouleur(),       // Colonne 4 : Couleur
                a.getEtat()           // Colonne 5 : État
            };
            // Ajout physique de la ligne dans le modèle du tableau pour l'affichage
            modelTable.addRow(ligne);
        }
    }
}