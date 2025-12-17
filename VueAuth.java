import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Cette classe représente l'écran de connexion (Authentification).
 * Elle est construite comme un panneau (JPanel) qui sera placé dans la fenêtre principale.
 */
public class VueAuth extends JPanel {
    
    // Objets nécessaires pour le fonctionnement
    private Modele modele;
    private Fenetre fenetre;
    
    // Composants de saisie
    private JTextField txtLogin;
    private JPasswordField txtMdp;
    private JButton btnValider;
    
    // Label pour afficher les erreurs sans ouvrir de popup
    private JLabel lblErreur;

    public VueAuth(Modele modele, Fenetre fenetre) {
        this.modele = modele;
        this.fenetre = fenetre;
        
        // GridBagLayout permet de centrer précisément les composants au milieu de l'écran
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Ajoute de l'espace (marges) autour de chaque composant
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Ligne 0 : Login
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Login :"), gbc);
        
        gbc.gridx = 1;
        txtLogin = new JTextField(15);
        add(txtLogin, gbc);

        // Ligne 1 : Mot de passe
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Mot de passe :"), gbc);
        
        gbc.gridx = 1;
        txtMdp = new JPasswordField(15);
        add(txtMdp, gbc);

        // Ligne 2 : Zone de message d'erreur (initialement vide)
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2; // Le message prend la largeur des deux colonnes
        lblErreur = new JLabel(" ");
        lblErreur.setForeground(Color.RED); // Texte en rouge pour signaler un problème
        add(lblErreur, gbc);

        // Ligne 3 : Bouton de validation
        gbc.gridy = 3;
        btnValider = new JButton("Se connecter");
        
        // On attache l'action au bouton en utilisant la classe interne définie plus bas
        btnValider.addActionListener(new ActionConnexion());
        add(btnValider, gbc);
    }

    /**
     * Classe interne qui gère uniquement le clic sur le bouton de connexion.
     * C'est ici que l'on vérifie si l'utilisateur existe dans la base.
     */
    class ActionConnexion implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String login = txtLogin.getText();
            // On transforme le mot de passe masqué en texte lisible pour la vérification
            String mdp = new String(txtMdp.getPassword());
            
            // Appel de la méthode du modèle pour chercher l'utilisateur en SQL
            Utilisateur u = modele.authentifier(login, mdp);
            
            if (u != null) {
                // Si l'utilisateur est trouvé, on vide les champs et on change de vue
                txtLogin.setText("");
                txtMdp.setText("");
                lblErreur.setText(" ");
                fenetre.setUtilisateurConnecte(u);
            } else {
                // Si la connexion échoue, on affiche le message en rouge sans popup
                lblErreur.setText("Erreur : Login ou mot de passe incorrect.");
            }
        }
    }
}