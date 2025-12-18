import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Interface graphique dédiée à l'authentification des utilisateurs de l'application.
 * Ce panneau (JPanel) permet la saisie sécurisée des identifiants (login et mot de passe).
 * Il assure la transition vers l'interface principale après validation par le modèle SQL.
 */
public class VueAuth extends JPanel {
    
    // Objets de pilotage : accès aux données (modele) et contrôle de navigation (fenetre)
    private Modele modele;
    private Fenetre fenetre;
    
    // Éléments du formulaire de saisie
    private JTextField txtLogin;
    private JPasswordField txtMdp;
    private JButton btnValider;
    
    // Composant textuel pour le retour utilisateur immédiat en cas d'erreur
    private JLabel lblErreur;

    public VueAuth(Modele modele, Fenetre fenetre) {
        this.modele = modele;
        this.fenetre = fenetre;
        
        //  CONFIGURATION DE LA MISE EN PAGE 
        // Le GridBagLayout est utilisé pour un centrage parfait et flexible des composants
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Définition des marges internes (5 pixels) pour aérer le formulaire
        gbc.insets = new Insets(5, 5, 5, 5);
        
        //  LIGNE 0 : CHAMP LOGIN 
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Login :"), gbc);
        
        gbc.gridx = 1;
        txtLogin = new JTextField(15); // Largeur fixe pour l'homogénéité visuelle
        add(txtLogin, gbc);

        //  LIGNE 1 : CHAMP MOT DE PASSE 
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Mot de passe :"), gbc);
        
        gbc.gridx = 1;
        txtMdp = new JPasswordField(15); // Masquage automatique des caractères
        add(txtMdp, gbc);

        //  LIGNE 2 : ZONE DE NOTIFICATION 
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2; // Le label s'étend sur les deux colonnes du formulaire
        lblErreur = new JLabel(" ");
        lblErreur.setForeground(Color.RED); // Couleur d'alerte pour les messages d'erreur
        add(lblErreur, gbc);

        //  LIGNE 3 : BOUTON D'ACTION 
        gbc.gridy = 3;
        btnValider = new JButton("Se connecter");
        
        // Attachement de l'écouteur d'événement pour traiter le clic
        btnValider.addActionListener(new ActionConnexion());
        add(btnValider, gbc);
    }

    /**
     * Classe interne gérant l'événement du clic sur le bouton de connexion.
     * Cette structure sépare la construction graphique de la logique métier.
     */
    class ActionConnexion implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Récupération des données saisies par l'utilisateur
            String login = txtLogin.getText();
            
            // Conversion sécurisée du mot de passe (tableau de char vers String)
            String mdp = new String(txtMdp.getPassword());
            
            // Vérification des droits via le moteur de données SQL
            Utilisateur u = modele.authentifier(login, mdp);
            
            if (u != null) {
                // CAS SUCCÈS : Nettoyage des champs et passage à l'écran suivant
                txtLogin.setText("");
                txtMdp.setText("");
                lblErreur.setText(" ");
                
                // Informe la fenêtre principale de l'identité de l'utilisateur connecté
                fenetre.setUtilisateurConnecte(u);
            } else {
                // CAS ÉCHEC : Information visuelle sans interrompre l'expérience par un popup
                lblErreur.setText("Erreur : Login ou mot de passe incorrect.");
            }
        }
    }
}