import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Cette classe est la structure de base du logiciel. 
 * Elle sert de cadre pour afficher les différents formulaires (Vues).
 */
public class Fenetre extends JFrame {
    
    // Attribut qui contient la connexion à la base de données
    // On le garde ici pour pouvoir le donner à chaque nouveau panneau affiché
    private Modele modele;
    
    // Garde en mémoire l'utilisateur qui s'est connecté
    // Utile pour savoir si on doit afficher l'écran du Maire, du Bénévole ou du Secrétaire
    private Utilisateur utilisateurConnecte;

    /**
     * Le constructeur : il s'exécute une seule fois au lancement du programme.
     */
    public Fenetre() {
        // Définit le nom du logiciel en haut de la fenêtre
        this.setTitle("Fripouilles - Gestion du Catalogue");
        
        // Définit une taille fixe pour que l'interface soit toujours lisible
        this.setSize(1000, 700);
        
        // Force la fenêtre à apparaître au milieu de l'écran de l'ordinateur
        this.setLocationRelativeTo(null); 
        
        // Indique au système d'arrêter complètement le programme si on ferme la fenêtre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On initialise la connexion SQL pour qu'elle soit prête dès le départ
        this.modele = new Modele();

        // On commence par afficher l'écran de connexion (VueAuth)
        this.changerVue("AUTH");
        
        // Rend l'ensemble visible à l'utilisateur
        this.setVisible(true);
    }

    /**
     * C'est la fonction qui gère le changement d'écran sans ouvrir de popup.
     * Elle vide la fenêtre et installe un nouveau panneau à la place.
     */
    public void changerVue(String nomVue) {
        // Cette variable va stocker le nouveau panneau à installer
        JPanel nouveauPanel = null;

        // On vérifie quel écran est demandé
        if (nomVue.equals("AUTH")) {
            // On crée le panneau de connexion et on lui donne 'this' (la fenêtre)
            // pour qu'il puisse nous signaler quand un utilisateur a réussi à se connecter
            nouveauPanel = new VueAuth(modele, this);
        } 
        else if (nomVue.equals("SECRETAIRE")) {
            VueSecretaire vs = new VueSecretaire(modele);
            vs.rafraichir(); // On charge immédiatement la liste des ventes SQL
            nouveauPanel = vs;
        } 
        else if (nomVue.equals("BENEVOLE")) {
            VueBenevole vb = new VueBenevole(modele);
            vb.setUtilisateur(utilisateurConnecte); // On dit au formulaire qui est le bénévole
            vb.rafraichir(); // On charge les catégories et les ventes
            nouveauPanel = vb;
        } 
        else if (nomVue.equals("MAIRE")) {
            VueMaire vm = new VueMaire(modele);
            vm.rafraichir(); // On prépare les catalogues pour le maire
            nouveauPanel = vm;
        }

        // Si on a bien créé un panneau
        if (nouveauPanel != null) {
            // setContentPane retire l'ancien panneau et "colle" le nouveau au centre
            this.setContentPane(nouveauPanel);
            
            // Ces deux lignes forcent l'ordinateur à rafraîchir l'affichage
            // pour éviter que l'écran reste blanc ou bugge lors du changement
            this.revalidate();
            this.repaint();
        }
    }

    /**
     * Cette méthode reçoit l'utilisateur qui vient de se connecter.
     * Elle déclenche le changement de menu et d'écran selon son métier.
     */
    public void setUtilisateurConnecte(Utilisateur u) {
        this.utilisateurConnecte = u;
        
        // Selon le rôle écrit dans la base de données, on adapte l'interface
        if (u.getRole().equals("MAIRE")) {
            this.configurerMenu("Espace Maire");
            this.changerVue("MAIRE");
        } 
        else if (u.getRole().equals("SECRETAIRE")) {
            this.configurerMenu("Espace Secrétariat");
            this.changerVue("SECRETAIRE");
        } 
        else if (u.getRole().equals("BENEVOLE")) {
            this.configurerMenu("Espace Bénévole");
            this.changerVue("BENEVOLE");
        }
    }

    /**
     * Crée la barre de navigation en haut de la fenêtre.
     */
    private void configurerMenu(String labelMenu) {
        // Création de la barre horizontale
        JMenuBar barre = new JMenuBar();
        
        // Création du bouton de menu qui contiendra les options
        JMenu menuOption = new JMenu(labelMenu);
        
        // Création de la ligne cliquable pour se déconnecter
        JMenuItem itemLogOut = new JMenuItem("Se déconnecter");
        
        // On attache une classe spéciale (en bas) qui gère le clic
        itemLogOut.addActionListener(new ActionDeconnexion());

        // On assemble le tout : l'item dans le menu, le menu dans la barre
        menuOption.add(itemLogOut);
        barre.add(menuOption);
        
        // On installe la barre finie en haut de la fenêtre
        this.setJMenuBar(barre);
    }

    /**
     * Petite classe interne qui ne s'occupe que de la déconnexion.
     * C'est plus propre et plus simple que de tout mélanger.
     */
    class ActionDeconnexion implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // On oublie l'utilisateur actuel
            utilisateurConnecte = null; 
            
            // On enlève la barre de menu pour revenir à l'écran de login propre
            setJMenuBar(null); 
            
            // On demande à la fenêtre de revenir sur l'affichage AUTH
            changerVue("AUTH"); 
        }
    }
}