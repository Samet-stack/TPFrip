import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VueSecretaireVentes extends JPanel {
    private Modele modele; // Pour parler à la base de données
    private JTextField txtTitre, txtDate, txtLieu; // Zones de saisie
    private JTable table; // Le tableau visuel
    private DefaultTableModel modelTable; // Le "cerveau" du tableau (gère les lignes)
    private JLabel lblMsg; // Pour afficher les messages sans faire de popup

    public VueSecretaireVentes(Modele m) {
        this.modele = m;
        // BorderLayout sépare la vue en 5 zones (Nord, Sud, Est, Ouest, Centre)
        this.setLayout(new BorderLayout());

        // --- PARTIE HAUTE (NORTH) : LE FORMULAIRE DE CRÉATION ---
        // On utilise une grille de 4 lignes et 2 colonnes
        JPanel panHaut = new JPanel(new GridLayout(4, 2, 10, 10));
        panHaut.setBorder(BorderFactory.createTitledBorder("Ajouter une nouvelle vente"));
        
        panHaut.add(new JLabel(" Titre de la vente :"));
        txtTitre = new JTextField();
        panHaut.add(txtTitre);

        panHaut.add(new JLabel(" Date (AAAA-MM-JJ) :"));
        txtDate = new JTextField();
        panHaut.add(txtDate);

        panHaut.add(new JLabel(" Lieu :"));
        txtLieu = new JTextField();
        panHaut.add(txtLieu);

        JButton btnAjout = new JButton("Enregistrer la vente");
        panHaut.add(btnAjout);
        
        // Action du bouton Créer (méthode classique sans flèche)
        btnAjout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // On appelle la fonction du modèle pour insérer en SQL
                if (modele.ajouterVente(txtTitre.getText(), txtDate.getText(), txtLieu.getText())) {
                    lblMsg.setText("Succès : Vente enregistrée !");
                    lblMsg.setForeground(new Color(0, 150, 0)); // Texte en vert
                    rafraichir(); // On remet le tableau à jour pour voir la nouvelle vente
                } else {
                    lblMsg.setText("Erreur lors de la création.");
                    lblMsg.setForeground(Color.RED); // Texte en rouge
                }
            }
        });

        this.add(panHaut, BorderLayout.NORTH);

        // --- PARTIE CENTRALE (CENTER) : LE TABLEAU ---
        modelTable = new DefaultTableModel();
        // On définit les titres des colonnes
        modelTable.setColumnIdentifiers(new String[]{"ID", "Titre", "Date", "Lieu"});
        table = new JTable(modelTable);
        // On met le tableau dans un JScrollPane pour avoir des barres de défilement
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- PARTIE BASSE (SOUTH) : ACTIONS SUR LA SÉLECTION ---
        JPanel panBas = new JPanel(new BorderLayout());
        
        // Panneau pour les boutons de modification et suppression
        JPanel panBoutons = new JPanel(new FlowLayout());
        JButton btnModifier = new JButton("Modifier sélection");
        JButton btnSupprimer = new JButton("Supprimer sélection");
        panBoutons.add(btnModifier);
        panBoutons.add(btnSupprimer);

        // Label pour les messages d'info
        lblMsg = new JLabel(" ");
        lblMsg.setHorizontalAlignment(SwingConstants.CENTER);

        // --- Action Modifier ---
        btnModifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ligne = table.getSelectedRow(); // On regarde quelle ligne est cliquée
                if (ligne != -1) { // Si une ligne est bien sélectionnée
                    int id = (int) table.getValueAt(ligne, 0); // On récupère l'ID (colonne 0)
                    // On modifie avec les nouvelles valeurs tapées dans les champs du haut
                    if (modele.modifierVente(id, txtTitre.getText(), txtDate.getText(), txtLieu.getText())) {
                        lblMsg.setText("Vente mise à jour.");
                        rafraichir();
                    }
                } else {
                    lblMsg.setText("Veuillez d'abord cliquer sur une ligne du tableau.");
                }
            }
        });

        // --- Action Supprimer ---
        btnSupprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ligne = table.getSelectedRow();
                if (ligne != -1) {
                    int id = (int) table.getValueAt(ligne, 0);
                    if (modele.supprimerVente(id)) {
                        lblMsg.setText("Vente supprimée avec succès.");
                        rafraichir();
                    }
                } else {
                    lblMsg.setText("Sélectionnez une vente à supprimer dans le tableau.");
                }
            }
        });

        panBas.add(panBoutons, BorderLayout.NORTH);
        panBas.add(lblMsg, BorderLayout.SOUTH);
        this.add(panBas, BorderLayout.SOUTH);
    }

    /**
     * Cette fonction vide le tableau et le remplit avec les données 
     * les plus récentes de la base de données.
     */
    public void rafraichir() {
        modelTable.setRowCount(0); // On vide toutes les lignes
        ArrayList<Vente> liste = modele.getLesVentes(); // On récupère la liste SQL
        for (Vente v : liste) {
            // On ajoute une ligne pour chaque vente trouvée
            modelTable.addRow(new Object[]{v.getIdVente(), v.getTitre(), v.getDateVente(), v.getLieu()});
        }
    }
}