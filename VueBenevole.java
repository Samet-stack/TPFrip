import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Interface graphique dédiée aux bénévoles pour la saisie de nouveaux articles.
 * Permet de définir précisément les caractéristiques (taille, état, catégorie) d'un vêtement.
 * Gère l'enregistrement en base de données avec un retour visuel sur l'état de l'opération.
 */
public class VueBenevole extends JPanel {

    private Modele modele;
    private Utilisateur utilisateur;

    //  Zones de saisie de texte 
    private JTextField txtNomArticle, txtDonneur, txtDescription;

    //  Options à choix unique  pour la Taille 
    // On utilise un ButtonGroup pour garantir qu'une seule taille est cochée à la fois
    private JRadioButton rbXS, rbS, rbL, rbXL, rbXXL, rb3XL, rb4XL;
    private ButtonGroup bgTaille;

    //  Options à choix unique pour l'état du vêtement ---
    private JRadioButton rbCorrect, rbBon, rbNeuf;
    private ButtonGroup bgEtat;

    //  Listes déroulantes (ComboBox) pour les sélections dynamiques
    private JComboBox<String> cbCouleur = new JComboBox<String>();
    private JComboBox<Categorie> cbCategorie;
    private JComboBox<Vente> cbVente;
    
    private JButton btnAjouter;
    
    // Label de texte pour confirmer l'enregistrement (évite les fenêtres popup intrusives)
    private JLabel lblStatus;

    public VueBenevole(Modele modele) {
        this.modele = modele;
        // Organisation globale : Titre en haut, Formulaire au milieu, Bouton en bas
        setLayout(new BorderLayout());

        // --- SECTION NORD : Titre de la vue ---
        JLabel lblTitre = new JLabel("Enregistrement d'un nouvel Article", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitre, BorderLayout.NORTH);

        
        // GridLayout(8, 2) crée 8 lignes de deux colonnes (Libellé à gauche, Champ à droite)
        JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));

        // Champ Donneur
        form.add(new JLabel("Nom du Donneur :"));
        txtDonneur = new JTextField();
        form.add(txtDonneur);

        // Sélection de la vente (chargée depuis la BDD)
        form.add(new JLabel("Vente concernée :"));
        cbVente = new JComboBox<>();
        form.add(cbVente);

        // Nom de l'objet
        form.add(new JLabel("Nom de l'Article :"));
        txtNomArticle = new JTextField();
        form.add(txtNomArticle);

        // Sélection de la catégorie
        form.add(new JLabel("Catégorie :"));
        cbCategorie = new JComboBox<>();
        form.add(cbCategorie);

        // Liste des couleurs fixes
        form.add(new JLabel("Couleur :"));
        String[] listeCouleurs = {"bleu", "violet", "rose", "rouge", "orange", "jaune", "vert", "noir", "marron", "gris", "blanc"};
        for(String c : listeCouleurs) cbCouleur.addItem(c);
        form.add(cbCouleur);

        // Gestion des Tailles : On utilise un panneau interne pour ranger les boutons radio
        form.add(new JLabel("Taille :"));
        JPanel panelTaille = new JPanel(new GridLayout(2, 4));
        rbXS = new JRadioButton("XS"); rbS = new JRadioButton("S"); rbL = new JRadioButton("L");
        rbXL = new JRadioButton("XL"); rbXXL = new JRadioButton("XXL"); rb3XL = new JRadioButton("3XL"); rb4XL = new JRadioButton("4XL");
        
        // Groupement obligatoire pour l'exclusivité des boutons radio
        bgTaille = new ButtonGroup();
        bgTaille.add(rbXS); bgTaille.add(rbS); bgTaille.add(rbL); bgTaille.add(rbXL);
        bgTaille.add(rbXXL); bgTaille.add(rb3XL); bgTaille.add(rb4XL);
        rbL.setSelected(true); // "L" coché par défaut

        panelTaille.add(rbXS); panelTaille.add(rbS); panelTaille.add(rbL); panelTaille.add(rbXL);
        panelTaille.add(rbXXL); panelTaille.add(rb3XL); panelTaille.add(rb4XL);
        form.add(panelTaille);

        // Description libre
        form.add(new JLabel("Description libre :"));
        txtDescription = new JTextField();
        form.add(txtDescription);

        // État du vêtement (Boutons radio alignés horizontalement)
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

        // --- SECTION SUD : Validation et retours ---
        JPanel panelValidation = new JPanel(new GridLayout(2, 1));
        
        lblStatus = new JLabel(" "); // Message de confirmation initialement vide
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        panelValidation.add(lblStatus);

        btnAjouter = new JButton("Enregistrer dans le catalogue");
        // Liaison de l'action de sauvegarde
        btnAjouter.addActionListener(new ActionEnregistrer());
        panelValidation.add(btnAjouter);

        add(panelValidation, BorderLayout.SOUTH);
    }

    /**
     * Définit l'utilisateur bénévole qui réalise la saisie.
     */
    public void setUtilisateur(Utilisateur u) {
        this.utilisateur = u;
    }

    /**
     * Rafraîchit les ComboBox en allant chercher les données les plus récentes dans le Modele.
     */
    public void rafraichir() {
        // Chargement des ventes
        cbVente.removeAllItems();
        for (Vente v : modele.getLesVentes()) cbVente.addItem(v);

        // Chargement des catégories
        cbCategorie.removeAllItems();
        for (Categorie c : modele.getLesCategories()) cbCategorie.addItem(c);
    }

    /**
     * Classe interne gérant l'extraction des données du formulaire et l'appel SQL.
     */
    class ActionEnregistrer implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                // 1. Analyse des boutons radio pour déterminer la taille
                String taille = "L";
                if (rbXS.isSelected()) taille = "XS";
                else if (rbS.isSelected()) taille = "S";
                else if (rbXL.isSelected()) taille = "XL";
                else if (rbXXL.isSelected()) taille = "XXL";
                else if (rb3XL.isSelected()) taille = "3XL";
                else if (rb4XL.isSelected()) taille = "4XL";

                // 2. Analyse des boutons radio pour déterminer l'état
                String etat = "Bon état";
                if (rbCorrect.isSelected()) etat = "Etat correct";
                else if (rbNeuf.isSelected()) etat = "Comme neuf";

                // 3. Récupération des objets sélectionnés dans les ComboBox
                Vente v = (Vente) cbVente.getSelectedItem();
                Categorie c = (Categorie) cbCategorie.getSelectedItem();
                String couleur = (String) cbCouleur.getSelectedItem();

                // Vérification de sécurité (si les listes SQL étaient vides)
                if (v == null || c == null) {
                    lblStatus.setText("Sélectionnez une vente et une catégorie.");
                    lblStatus.setForeground(Color.RED);
                    return;
                }

                // 4. Envoi des données au Modele pour insertion SQL (Article + Don associé)
                boolean ok = modele.ajouterArticleComplet(txtNomArticle.getText(), txtDescription.getText(), 
                             taille, couleur, etat, c.getIdCategorie(), v.getIdVente(), 
                             utilisateur.getIdUtilisateur(), txtDonneur.getText());

                if (ok) {
                    // Feedback positif et nettoyage du formulaire pour la saisie suivante
                    lblStatus.setText("Succès : Article enregistré !");
                    lblStatus.setForeground(new Color(0, 150, 0));
                    txtNomArticle.setText("");
                    txtDescription.setText("");
                    txtDonneur.setText("");
                } else {
                    // Feedback d'erreur SQL
                    lblStatus.setText("Erreur lors de l'enregistrement SQL.");
                    lblStatus.setForeground(Color.RED);
                }

            } catch (Exception ex) {
                // Gestion des erreurs de saisie inattendues
                lblStatus.setText("Erreur de saisie : " + ex.getMessage());
                lblStatus.setForeground(Color.RED);
            }
        }
    }
}