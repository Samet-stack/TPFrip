import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Cette classe représente l'espace de travail des bénévoles.
 * Elle permet de saisir toutes les caractéristiques d'un vêtement donné.
 */
public class VueBenevole extends JPanel {

    private Modele modele;
    private Utilisateur utilisateur;

    // Zones de saisie de texte
    private JTextField txtNomArticle, txtDonneur, txtDescription;

    // Options à choix unique (Boutons radio) pour la Taille
    private JRadioButton rbXS, rbS, rbL, rbXL, rbXXL, rb3XL, rb4XL;
    private ButtonGroup bgTaille;

    // Options à choix unique pour l'état du vêtement
    private JRadioButton rbCorrect, rbBon, rbNeuf;
    private ButtonGroup bgEtat;

    // Listes déroulantes pour les sélections
    private JComboBox<String> cbCouleur = new JComboBox<String>();
    private JComboBox<Categorie> cbCategorie;
    private JComboBox<Vente> cbVente;
    
    private JButton btnAjouter;
    
    // Label de texte pour confirmer l'enregistrement (remplace le popup)
    private JLabel lblStatus;

    public VueBenevole(Modele modele) {
        this.modele = modele;
        // Organisation : Titre au Nord, Formulaire au Centre, Validation au Sud
        setLayout(new BorderLayout());

        // --- Titre ---
        JLabel lblTitre = new JLabel("Enregistrement d'un nouvel Article", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitre, BorderLayout.NORTH);

        // --- Formulaire ---
        // Une grille de 8 lignes permet d'aligner parfaitement les libellés et les champs
        JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));

        form.add(new JLabel("Nom du Donneur :"));
        txtDonneur = new JTextField();
        form.add(txtDonneur);

        form.add(new JLabel("Vente concernée :"));
        cbVente = new JComboBox<>();
        form.add(cbVente);

        form.add(new JLabel("Nom de l'Article :"));
        txtNomArticle = new JTextField();
        form.add(txtNomArticle);

        form.add(new JLabel("Catégorie :"));
        cbCategorie = new JComboBox<>();
        form.add(cbCategorie);

        form.add(new JLabel("Couleur :"));
        String[] listeCouleurs = {"bleu", "violet", "rose", "rouge", "orange", "jaune", "vert", "noir", "marron", "gris", "blanc"};
        for(String c : listeCouleurs) cbCouleur.addItem(c);
        form.add(cbCouleur);

        // Gestion des Tailles avec un panneau interne
        form.add(new JLabel("Taille :"));
        JPanel panelTaille = new JPanel(new GridLayout(2, 4));
        rbXS = new JRadioButton("XS"); rbS = new JRadioButton("S"); rbL = new JRadioButton("L");
        rbXL = new JRadioButton("XL"); rbXXL = new JRadioButton("XXL"); rb3XL = new JRadioButton("3XL"); rb4XL = new JRadioButton("4XL");
        
        // On groupe les boutons pour qu'on ne puisse en cocher qu'un seul à la fois
        bgTaille = new ButtonGroup();
        bgTaille.add(rbXS); bgTaille.add(rbS); bgTaille.add(rbL); bgTaille.add(rbXL);
        bgTaille.add(rbXXL); bgTaille.add(rb3XL); bgTaille.add(rb4XL);
        rbL.setSelected(true); // Taille par défaut

        panelTaille.add(rbXS); panelTaille.add(rbS); panelTaille.add(rbL); panelTaille.add(rbXL);
        panelTaille.add(rbXXL); panelTaille.add(rb3XL); panelTaille.add(rb4XL);
        form.add(panelTaille);

        form.add(new JLabel("Description libre :"));
        txtDescription = new JTextField();
        form.add(txtDescription);

        form.add(new JLabel("État actuel :"));
        JPanel panelEtat = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbCorrect = new JRadioButton("Etat correct");
        rbBon = new JRadioButton("Bon état");
        rbNeuf = new JRadioButton("Comme neuf");
        rbBon.setSelected(true);

        bgEtat = new ButtonGroup();
        bgEtat.add(rbCorrect); bgEtat.add(rbBon); bgEtat.add(rbNeuf);
        panelEtat.add(rbCorrect); panelEtat.add(rbBon); panelEtat.add(rbNeuf);
        form.add(panelEtat);

        add(form, BorderLayout.CENTER);

        // --- Zone de validation (Sud) ---
        JPanel panelValidation = new JPanel(new GridLayout(2, 1));
        
        lblStatus = new JLabel(" "); // Message de confirmation
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        panelValidation.add(lblStatus);

        btnAjouter = new JButton("Enregistrer dans le catalogue");
        // Utilisation de la classe interne pour l'action du bouton
        btnAjouter.addActionListener(new ActionEnregistrer());
        panelValidation.add(btnAjouter);

        add(panelValidation, BorderLayout.SOUTH);
    }

    public void setUtilisateur(Utilisateur u) {
        this.utilisateur = u;
    }

    /**
     * Rafraîchit les listes déroulantes en allant chercher les dernières infos SQL.
     */
    public void rafraichir() {
        cbVente.removeAllItems();
        for (Vente v : modele.getLesVentes()) cbVente.addItem(v);

        cbCategorie.removeAllItems();
        for (Categorie c : modele.getLesCategories()) cbCategorie.addItem(c);
    }

    /**
     * Classe interne dédiée à l'enregistrement de l'article.
     */
    class ActionEnregistrer implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                // On récupère la taille sélectionnée
                String taille = "L";
                if (rbXS.isSelected()) taille = "XS";
                else if (rbS.isSelected()) taille = "S";
                else if (rbXL.isSelected()) taille = "XL";
                else if (rbXXL.isSelected()) taille = "XXL";
                else if (rb3XL.isSelected()) taille = "3XL";
                else if (rb4XL.isSelected()) taille = "4XL";

                // On récupère l'état sélectionné
                String etat = "Bon état";
                if (rbCorrect.isSelected()) etat = "Etat correct";
                else if (rbNeuf.isSelected()) etat = "Comme neuf";

                // Récupération des objets sélectionnés dans les JComboBox
                Vente v = (Vente) cbVente.getSelectedItem();
                Categorie c = (Categorie) cbCategorie.getSelectedItem();
                String couleur = (String) cbCouleur.getSelectedItem();

                if (v == null || c == null) {
                    lblStatus.setText("Sélectionnez une vente et une catégorie.");
                    lblStatus.setForeground(Color.RED);
                    return;
                }

                // Insertion via le modèle
                boolean ok = modele.ajouterArticleComplet(txtNomArticle.getText(), txtDescription.getText(), 
                             taille, couleur, etat, c.getIdCategorie(), v.getIdVente(), 
                             utilisateur.getIdUtilisateur(), txtDonneur.getText());

                if (ok) {
                    // Succès : on informe l'utilisateur en vert et on vide les champs
                    lblStatus.setText("Succès : Article enregistré !");
                    lblStatus.setForeground(new Color(0, 150, 0));
                    txtNomArticle.setText("");
                    txtDescription.setText("");
                    txtDonneur.setText("");
                } else {
                    lblStatus.setText("Erreur lors de l'enregistrement SQL.");
                    lblStatus.setForeground(Color.RED);
                }

            } catch (Exception ex) {
                lblStatus.setText("Erreur de saisie : " + ex.getMessage());
                lblStatus.setForeground(Color.RED);
            }
        }
    }
}