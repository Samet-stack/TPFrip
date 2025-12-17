import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Vue permettant d'afficher la liste des articles d'une vente (le catalogue).
 */
public class VueCatalogue extends JPanel {
    private Modele modele;
    private JTable table;
    private DefaultTableModel modelTable;

    public VueCatalogue(Modele m) {
        this.modele = m;
        this.setLayout(new BorderLayout());

        // Titre de l'écran
        JLabel titre = new JLabel("Catalogue des Articles", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(titre, BorderLayout.NORTH);

        // Configuration du tableau
        modelTable = new DefaultTableModel();
        modelTable.setColumnIdentifiers(new String[]{"Nom", "Description", "Taille", "Couleur", "État"});
        
        table = new JTable(modelTable);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Remplit le tableau en utilisant la méthode getCatalogue du modèle.
     */
    public void chargerArticles(int idVente) {
        // 1. On vide le tableau pour repartir à zéro
        modelTable.setRowCount(0); 

        // 2. CORRECTION ICI : On utilise bien le nom "getCatalogue" qui est dans ton Modele.java
        ArrayList<Article> liste = modele.getCatalogue(idVente);
        
        // 3. On ajoute les lignes une par une
        for (Article a : liste) {
            // On récupère les infos de l'objet Article
            Object[] ligne = {
                a.getNom(), 
                a.getDescription(), 
                a.getTaille(), 
                a.getCouleur(), 
                a.getEtat()
            };
            modelTable.addRow(ligne);
        }
    }
}