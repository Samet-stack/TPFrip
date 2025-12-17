import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * La classe Fenetre est le "Chef d'orchestre" de l'application.
 * Elle contient la barre de menu et change le panneau central (la vue) 
 * selon les clics de l'utilisateur.
 */
public class Fenetre extends JFrame {
    
    private Modele modele; // Référence vers la base de données
    private Utilisateur utilisateurConnecte; // Stocke qui est connecté (Maire, Secrétaire...)

    public Fenetre() {
        // Paramètres de base de la fenêtre
        this.setTitle("Fripouilles - Gestion du Catalogue");
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On crée l'unique exemplaire du Modèle pour tout le projet
        this.modele = new Modele();

        // Au lancement, on affiche l'écran d'authentification (Login)
        this.changerVue("AUTH");
        
        this.setVisible(true);
    }

    /**
     * Cette méthode est LA plus importante : elle vide la fenêtre et 
     * installe le nouveau panneau correspondant au nom demandé.
     */
    public void changerVue(String nomVue) {
        JPanel nouveauPanel = null;

        // --- CAS 1 : AUTHENTIFICATION ---
        if (nomVue.equals("AUTH")) {
            nouveauPanel = new VueAuth(modele, this);
        } 
        // --- CAS 2 : ACCUEIL SECRETAIRE ---
        else if (nomVue.equals("SEC_ACCUEIL")) {
            nouveauPanel = new VueSecretaireAccueil();
        } 
        // --- CAS 3 : GESTION DES VENTES (Créer/Modif/Suppr) ---
        else if (nomVue.equals("SEC_VENTES")) {
            VueSecretaireVentes vv = new VueSecretaireVentes(modele);
            vv.rafraichir(); // On remplit le tableau dès l'affichage
            nouveauPanel = vv;
        } 
        // --- CAS 4 : INSCRIPTION DES BENEVOLES ---
        else if (nomVue.equals("SEC_BENEVOLES")) {
            nouveauPanel = new VueSecretaireBenevoles(modele);
        }
        // --- CAS 5 : CONSULTATION DU CATALOGUE (Historique) ---
        else if (nomVue.equals("SEC_CATALOGUE")) {
            VueCatalogue vc = new VueCatalogue(modele);
            // On pourrait charger une vente spécifique ici (ex: vente n°1)
            vc.chargerArticles(1); 
            nouveauPanel = vc;
        }
        // --- CAS 6 : VUE POUR LES BENEVOLES ---
        else if (nomVue.equals("BENEVOLE")) {
            VueBenevole vb = new VueBenevole(modele);
            vb.setUtilisateur(utilisateurConnecte);
            vb.rafraichir();
            nouveauPanel = vb;
        }

        // Si on a bien trouvé un panneau à afficher
        if (nouveauPanel != null) {
            // On remplace le contenu de la fenêtre
            this.setContentPane(nouveauPanel);
            // Ces deux lignes forcent Java à redessiner l'écran proprement
            this.revalidate();
            this.repaint();
        }
    }

    /**
     * Cette méthode est appelée par VueAuth quand le login réussit.
     * Elle installe le bon menu selon le rôle (Secrétaire ou autre).
     */
    public void setUtilisateurConnecte(Utilisateur u) {
        this.utilisateurConnecte = u;
        
        // On vérifie le rôle pour donner les bons accès
        if (u.getRole().equals("SECRETAIRE")) {
            configurerMenuSecretaire(); // Menu complet
            this.changerVue("SEC_ACCUEIL");
        } else {
            configurerMenuSimple(u.getRole()); // Menu réduit (Déconnexion seulement)
            this.changerVue(u.getRole());
        }
    }

    /**
     * Crée la barre de menu déroulante spécifique à la secrétaire.
     */
    private void configurerMenuSecretaire() {
        JMenuBar barre = new JMenuBar();
        JMenu menu = new JMenu("Espace Secrétariat"); // Le titre cliquable en haut

        // On crée les différentes lignes du menu
        JMenuItem itemAcc = new JMenuItem("Aller à l'Accueil");
        JMenuItem itemVen = new JMenuItem("Gérer les Ventes");
        JMenuItem itemBen = new JMenuItem("Inscrire un Bénévole");
        JMenuItem itemCat = new JMenuItem("Consulter le Catalogue");
        JMenuItem itemDec = new JMenuItem("Se déconnecter");

        // Action : Retour à l'accueil
        itemAcc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changerVue("SEC_ACCUEIL");
            }
        });

        // Action : Écran de gestion des ventes (Tableau + Création)
        itemVen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changerVue("SEC_VENTES");
            }
        });

        // Action : Formulaire d'inscription des membres [cite: 60]
        itemBen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changerVue("SEC_BENEVOLES");
            }
        });

        // Action : Voir la liste des vêtements 
        itemCat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changerVue("SEC_CATALOGUE");
            }
        });

        // Action : Déconnexion (retour au Login)
        itemDec.addActionListener(new ActionDeconnexion());

        // On assemble les morceaux dans le menu
        menu.add(itemAcc);
        menu.add(itemVen);
        menu.add(itemBen);
        menu.add(itemCat);
        menu.addSeparator(); // Petite barre horizontale de séparation
        menu.add(itemDec);
        
        // On ajoute le menu à la barre, et la barre à la fenêtre
        barre.add(menu);
        this.setJMenuBar(barre);
    }

    /**
     * Menu simplifié pour le Maire ou les Bénévoles.
     */
    private void configurerMenuSimple(String role) {
        JMenuBar barre = new JMenuBar();
        JMenu menu = new JMenu("Session : " + role);
        JMenuItem itemDec = new JMenuItem("Se déconnecter");
        
        itemDec.addActionListener(new ActionDeconnexion());
        
        menu.add(itemDec);
        barre.add(menu);
        this.setJMenuBar(barre);
    }

    /**
     * Classe interne qui gère la déconnexion.
     */
    class ActionDeconnexion implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            utilisateurConnecte = null; // On oublie qui était connecté
            setJMenuBar(null); // On supprime le menu du haut
            changerVue("AUTH"); // On retourne à la page de mot de passe [cite: 59]
        }
    }
}