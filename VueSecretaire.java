import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VueSecretaire extends JPanel {
	
    private Modele modele;
    // Champs pour la vente
    private JTextField txtTitre, txtDate, txtLieu;
    // Champs pour le bénévole (ajoutés car ils manquaient pour que le code fonctionne)
    private JTextField txtLoginBen, txtMdpBen, txtNomBen, txtPreBen;
    
    private JTable table;
    private DefaultTableModel modelTable;
    private JLabel lblMessage; // Pour afficher les infos en bas sans popup

    public VueSecretaire(Modele modele) {
        this.modele = modele;
        this.setLayout(new BorderLayout());

        // --- ZONE DU HAUT : FORMULAIRES ---
        // On crée un panneau qui contient les deux formulaires côte à côte
        JPanel conteneurHaut = new JPanel(new GridLayout(1, 2, 10, 0));

        // 1. Formulaire Vente
        JPanel panelVente = new JPanel(new GridLayout(4, 2, 5, 5));
        panelVente.setBorder(BorderFactory.createTitledBorder("Gestion des Ventes"));
        
        panelVente.add(new JLabel("Titre Vente :"));
        txtTitre = new JTextField();
        panelVente.add(txtTitre);

        panelVente.add(new JLabel("Date (YYYY-MM-DD) :"));
        txtDate = new JTextField();
        panelVente.add(txtDate);

        panelVente.add(new JLabel("Lieu :"));
        txtLieu = new JTextField();
        panelVente.add(txtLieu);

        JButton btnAjout = new JButton("Créer la vente");
        panelVente.add(btnAjout);
        conteneurHaut.add(panelVente);

        // 2. Formulaire Bénévole
        JPanel panelBenevole = new JPanel(new GridLayout(5, 2, 5, 5));
        panelBenevole.setBorder(BorderFactory.createTitledBorder("Inscrire un Bénévole"));
        
        panelBenevole.add(new JLabel("Login :"));
        txtLoginBen = new JTextField();
        panelBenevole.add(txtLoginBen);
        
        panelBenevole.add(new JLabel("MDP :"));
        txtMdpBen = new JTextField();
        panelBenevole.add(txtMdpBen);
        
        panelBenevole.add(new JLabel("Nom :"));
        txtNomBen = new JTextField();
        panelBenevole.add(txtNomBen);
        
        panelBenevole.add(new JLabel("Prénom :"));
        txtPreBen = new JTextField();
        panelBenevole.add(txtPreBen);
        
        JButton btnInscrire = new JButton("Inscrire le bénévole");
        panelBenevole.add(btnInscrire);
        conteneurHaut.add(panelBenevole);

        this.add(conteneurHaut, BorderLayout.NORTH);

        // --- ZONE DU CENTRE : LE TABLEAU ---
        modelTable = new DefaultTableModel();
        modelTable.setColumnIdentifiers(new String[]{"ID", "Titre", "Date", "Lieu", "Statut"});
        table = new JTable(modelTable);
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- ZONE DU BAS : ACTIONS ET MESSAGES ---
        JPanel panelBas = new JPanel(new GridLayout(2, 1));
        
        // Boutons de modification et suppression
        JPanel panelActions = new JPanel(new FlowLayout());
        JButton btnModifier = new JButton("Modifier la vente sélectionnée");
        JButton btnSupprimer = new JButton("Supprimer la vente sélectionnée");
        panelActions.add(btnModifier);
        panelActions.add(btnSupprimer);
        
        // Label pour les messages (remplace les popups)
        lblMessage = new JLabel(" ");
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelBas.add(panelActions);
        panelBas.add(lblMessage);
        this.add(panelBas, BorderLayout.SOUTH);

        // --- LOGIQUE DES BOUTONS ---

        // Créer une vente
        btnAjout.addActionListener(e -> {
            if (modele.ajouterVente(txtTitre.getText(), txtDate.getText(), txtLieu.getText())) {
                lblMessage.setText("Vente créée avec succès !");
                rafraichir();
            } else {
                lblMessage.setText("Erreur lors de la création.");
            }
        });

        // Inscrire un bénévole
        btnInscrire.addActionListener(e -> {
            if (modele.ajouterBenevole(txtLoginBen.getText(), txtMdpBen.getText(), txtNomBen.getText(), txtPreBen.getText(), "", "")) {
                lblMessage.setText("Bénévole inscrit !");
                txtLoginBen.setText(""); txtMdpBen.setText("");
            } else {
                lblMessage.setText("Erreur d'inscription.");
            }
        });

        // Supprimer une vente
        btnSupprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ligne = table.getSelectedRow();
                if (ligne != -1) {
                    int id = (int) table.getValueAt(ligne, 0);
                    if (modele.supprimerVente(id)) {
                        lblMessage.setText("Vente supprimée avec succès.");
                        rafraichir();
                    } else {
                        lblMessage.setText("Erreur : Impossible de supprimer.");
                    }
                } else {
                    lblMessage.setText("Veuillez sélectionner une vente dans le tableau.");
                }
            }
        });

        // Modifier une vente
        btnModifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ligne = table.getSelectedRow();
                if (ligne != -1) {
                    int id = (int) table.getValueAt(ligne, 0);
                    if (modele.modifierVente(id, txtTitre.getText(), txtDate.getText(), txtLieu.getText())) {
                        lblMessage.setText("Vente mise à jour.");
                        rafraichir();
                    } else {
                        lblMessage.setText("Erreur de modification.");
                    }
                } else {
                    lblMessage.setText("Sélectionnez une ligne pour modifier.");
                }
            }
        });
    }

    public void rafraichir() {
        modelTable.setRowCount(0);
        ArrayList<Vente> lesVentes = modele.getLesVentes();
        for (Vente v : lesVentes) {
            modelTable.addRow(new Object[]{v.getIdVente(), v.getTitre(), v.getDateVente(), v.getLieu(), v.getStatut()});
        }
    }
}