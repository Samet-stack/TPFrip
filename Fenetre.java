import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fenetre extends JFrame {
	
	// Attributs
    private Modele modele;
    private CardLayout cardLayout;
    private JPanel panelPrincipal;
    private Utilisateur utilisateurConnecte;

    // Vues
    private VueAuth vueAuth;
    private VueSecretaire vueSecretaire;
    private VueBenevole vueBenevole;
    private VueMaire vueMaire;

    public Fenetre() {
        setTitle("Fripouilles - Gestion Ventes �ph�m�res");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        modele = new Modele();

        cardLayout = new CardLayout(); // � changer pour le changement de vue
        panelPrincipal = new JPanel(cardLayout);

        // Initialisation des vues
        vueAuth = new VueAuth(modele, this);
        vueSecretaire = new VueSecretaire(modele);
        vueBenevole = new VueBenevole(modele);
        vueMaire = new VueMaire(modele);

        // Ajout au panel
        panelPrincipal.add(vueAuth, "AUTH");
        panelPrincipal.add(vueSecretaire, "SECRETAIRE");
        panelPrincipal.add(vueBenevole, "BENEVOLE");
        panelPrincipal.add(vueMaire, "MAIRE");

        add(panelPrincipal);
        setVisible(true);
        changerVue("AUTH");
    }

    public void changerVue(String nomVue) {
        cardLayout.show(panelPrincipal, nomVue);
        // Rafraichir les données au chargement de la vue
        if (nomVue.equals("SECRETAIRE")) vueSecretaire.rafraichir();
        if (nomVue.equals("BENEVOLE")) vueBenevole.rafraichir();
        if (nomVue.equals("MAIRE")) vueMaire.rafraichir();
    }

    public void setUtilisateurConnecte(Utilisateur u) {
        this.utilisateurConnecte = u;
        // Redirection selon le r�le
        if (u.getRole().equals("MAIRE")) {
            menuMaire();
            changerVue("MAIRE");
        } else if (u.getRole().equals("SECRETAIRE")) {
            menuSecretaire();
            changerVue("SECRETAIRE");
        } else if (u.getRole().equals("BENEVOLE")) {
            // On passe l'user � la vue b�n�vole pour l'enregistrement
            vueBenevole.setUtilisateur(u);
            menuBenevole();
            changerVue("BENEVOLE");
        }
    }

    private void menuMaire() {
        JMenuBar barre = new JMenuBar();
        JMenu menu = new JMenu("Menu Maire");
        JMenuItem item1 = new JMenuItem("Consulter Catalogue");
        JMenuItem itemQ = new JMenuItem("Déconnexion");
        
        item1.addActionListener(e -> changerVue("MAIRE"));
        itemQ.addActionListener(e -> deconnexion());

        menu.add(item1);
        menu.add(itemQ);
        barre.add(menu);
        setJMenuBar(barre);
    }

    private void menuSecretaire() {
        JMenuBar barre = new JMenuBar();
        JMenu menu = new JMenu("Menu Secrétaire");
        JMenuItem item1 = new JMenuItem("Gérer les Ventes");
        JMenuItem itemQ = new JMenuItem("Déconnexion");

        item1.addActionListener(e -> changerVue("SECRETAIRE"));
        itemQ.addActionListener(e -> deconnexion());

        menu.add(item1);
        menu.add(itemQ);
        barre.add(menu);
        setJMenuBar(barre);
    }

    private void menuBenevole() {
        JMenuBar barre = new JMenuBar();
        JMenu menu = new JMenu("Menu Bénévole");
        JMenuItem item1 = new JMenuItem("Enregistrer Articles");
        JMenuItem itemQ = new JMenuItem("Déconnexion");

        item1.addActionListener(e -> changerVue("BENEVOLE"));
        itemQ.addActionListener(e -> deconnexion());

        menu.add(item1);
        menu.add(itemQ);
        barre.add(menu);
        setJMenuBar(barre);
    }

    private void deconnexion() {
        utilisateurConnecte = null;
        setJMenuBar(null);
        changerVue("AUTH");
        revalidate();
    }
}