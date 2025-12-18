import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Interface de gestion complète des ventes destinée au secrétariat.
 * Permet la création de nouveaux événements, la modification et la suppression de ventes existantes.
 * Intègre un tableau  avec la base de données via le Modèle.
 */
public class VueSecretaireVentes extends JPanel {
    private Modele modele; 
    private JTextField txtTitre, txtDate, txtLieu; // Champs de saisie pour les informations de vente
    private JTable table; // Composant graphique d'affichage des données en colonnes
    private DefaultTableModel modelTable; // Structure gérant les données du tableau (lignes/colonnes)
    private JLabel lblMsg; // Zone de notification textuelle pour les retours utilisateurs

    public VueSecretaireVentes(Modele m) {
        this.modele = m;
        // L'organisation en BorderLayout permet de structurer la vue de manière équilibrée
        this.setLayout(new BorderLayout());

        //  SECTION NORD (NORTH) : FORMULAIRE DE CRÉATION
        // Utilisation d'un GridLayout pour aligner proprement les labels et les champs
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

        // Bouton de validation pour l'insertion SQL
        JButton btnAjout = new JButton("Enregistrer la vente");
        panHaut.add(btnAjout);
        
        // Logique de création au clic sur "Enregistrer"
        btnAjout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Appel du Modèle : si l'insertion réussit, on informe et on rafraîchit
                if (modele.ajouterVente(txtTitre.getText(), txtDate.getText(), txtLieu.getText())) {
                    lblMsg.setText("Succès : Vente enregistrée !");
                    lblMsg.setForeground(new Color(0, 150, 0)); // Notification en vert
                    rafraichir(); // Mise à jour immédiate du tableau visuel
                } else {
                    lblMsg.setText("Erreur lors de la création.");
                    lblMsg.setForeground(Color.RED); // Notification en rouge
                }
            }
        });

        this.add(panHaut, BorderLayout.NORTH);

        //  SECTION CENTRALE (CENTER)
        modelTable = new DefaultTableModel();
        // Configuration des en-têtes du tableau
        modelTable.setColumnIdentifiers(new String[]{"ID", "Titre", "Date", "Lieu"});
        table = new JTable(modelTable);
        // JScrollPane est indispensable pour afficher les titres de colonnes et scroller
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        //  SECTION BASSE (SOUTH) : ACTIONS DE MAINTENANCE (MODIF/SUPPR)
        JPanel panBas = new JPanel(new BorderLayout());
        
        // Panneau horizontal pour les boutons d'action
        JPanel panBoutons = new JPanel(new FlowLayout());
        JButton btnModifier = new JButton("Modifier sélection");
        JButton btnSupprimer = new JButton("Supprimer sélection");
        panBoutons.add(btnModifier);
        panBoutons.add(btnSupprimer);

        // Zone de message pour les avertissements ou confirmations
        lblMsg = new JLabel(" ");
        lblMsg.setHorizontalAlignment(SwingConstants.CENTER);

        //  Action Modifier : Mise à jour de la ligne sélectionnée 
        btnModifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ligne = table.getSelectedRow(); // Vérifie si l'utilisateur a cliqué sur une ligne
                if (ligne != -1) { 
                    // Récupération de l'identifiant caché dans la première colonne 
                    int id = (int) table.getValueAt(ligne, 0); 
                    // Mise à jour SQL avec les valeurs actuellement saisies dans les champs du haut
                    if (modele.modifierVente(id, txtTitre.getText(), txtDate.getText(), txtLieu.getText())) {
                        lblMsg.setText("Vente mise à jour.");
                        rafraichir(); // Recharger les données pour vérifier la modification
                    }
                } else {
                    lblMsg.setText("Veuillez d'abord cliquer sur une ligne du tableau.");
                }
            }
        });

        // Action Supprimer : Retrait définitif de la vente 
        btnSupprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ligne = table.getSelectedRow();
                if (ligne != -1) {
                    // Récupération de l'ID pour savoir quel enregistrement supprimer en base
                    int id = (int) table.getValueAt(ligne, 0);
                    if (modele.supprimerVente(id)) {
                        lblMsg.setText("Vente supprimée avec succès.");
                        rafraichir(); // On vide et on remplit le tableau
                    }
                } else {
                    lblMsg.setText("Sélectionnez une vente à supprimer dans le tableau.");
                }
            }
        });

        // Assemblage de la partie basse (Boutons en haut, Message en bas)
        panBas.add(panBoutons, BorderLayout.NORTH);
        panBas.add(lblMsg, BorderLayout.SOUTH);
        this.add(panBas, BorderLayout.SOUTH);
    }

    /**
     * Procédure de rafraîchissement des données du tableau.
     * Cette méthode nettoie l'interface et recharge la liste depuis le Modèle SQL.
     */
    public void rafraichir() {
        // Étape 1 : On vide le modèle de données du tableau
        modelTable.setRowCount(0); 
        // Étape 2 : On demande au Modèle la liste fraîche des ventes
        ArrayList<Vente> liste = modele.getLesVentes(); 
        // Étape 3 : On reconstruit chaque ligne du tableau visuel
        for (Vente v : liste) {
            modelTable.addRow(new Object[]{
                v.getIdVente(), 
                v.getTitre(), 
                v.getDateVente(), 
                v.getLieu()
            });
        }
    }
}