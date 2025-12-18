import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*; // Ajout nécessaire pour ActionListener et ActionEvent
import java.util.ArrayList;

/**
 * Interface de consultation dédiée au rôle de Maire.
 * Permet de sélectionner une vente spécifique via une liste déroulante.
 * Affiche dynamiquement le catalogue des articles associés dans un tableau récapitulatif.
 */
public class VueMaire extends JPanel {
	
    private Modele modele;
    private JComboBox<Vente> cbVentes;
    private JTable table;
    private DefaultTableModel modelTable;

    public VueMaire(Modele modele) {
        this.modele = modele;
        // Utilisation du BorderLayout pour organiser les filtres en haut et les données au centre
        setLayout(new BorderLayout());

        //  SECTION HAUTE : Filtres et Sélection 
        JPanel panelHaut = new JPanel();
        panelHaut.add(new JLabel("Choisir une vente :"));
        
        // Menu déroulant pour choisir l'événement de vente
        cbVentes = new JComboBox<>();
        
        // Bouton déclenchant le chargement des données
        JButton btnVoir = new JButton("Voir le catalogue");
        
        //  Remplacement de la flèche par une classe anonyme  e - tjr pas capter  ce raccourci donc je remplace
        btnVoir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cette méthode est exécutée dès qu'on clique sur le bouton
                chargerCatalogue();
            }
        });
        
        panelHaut.add(cbVentes);
        panelHaut.add(btnVoir);
        
        add(panelHaut, BorderLayout.NORTH);

        //  SECTION CENTRALE : Affichage des résultats 
        // Configuration du modèle de tableau avec 3 colonnes spécifiques
        modelTable = new DefaultTableModel();
        modelTable.setColumnIdentifiers(new String[]{"Nom Article", "Taille", "Description"});
        
        // Création de la table et intégration dans un scroll pane (barres de défilement)
        table = new JTable(modelTable);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Met à jour la liste déroulante des ventes.
     * Cette méthode est appelée lors du passage sur cet écran pour garantir des données à jour.
     */
    public void rafraichir() {
        // On nettoie la liste actuelle
        cbVentes.removeAllItems();
        // On récupère la liste des ventes depuis la base de données via le modèle
        ArrayList<Vente> lesVentes = modele.getLesVentes();
        // On remplit le composant graphique avec les nouveaux objets Vente
        for (Vente v : lesVentes) {
            cbVentes.addItem(v);
        }
    }

    /**
     * Récupère les articles liés à la vente sélectionnée et les affiche dans le tableau.
     */
    private void chargerCatalogue() {
        // On vide le tableau avant d'ajouter les nouvelles lignes (nettoyage)
        modelTable.setRowCount(0);
        
        // On récupère l'objet Vente sélectionné par l'utilisateur dans la liste
        Vente v = (Vente) cbVentes.getSelectedItem();
        
        // Si une vente est bien sélectionnée
        if (v != null) {
            // Requête SQL (via le modèle) pour obtenir les articles de cette vente précise
            ArrayList<Article> arts = modele.getCatalogue(v.getIdVente());
            
            // Parcours de la liste pour remplir les lignes du tableau graphique
            for (Article a : arts) {
                // Ajout d'une ligne avec les colonnes Nom, Taille et Description
                modelTable.addRow(new Object[]{
                    a.getNom(), 
                    a.getTaille(), 
                    a.getDescription()
                });
            }
        }
    }
}