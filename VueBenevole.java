import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VueBenevole extends JPanel implements ActionListener {

    private Modele modele;
    private Utilisateur utilisateur;

    private JTextField txtNomArticle, txtDonneur, txtDescription;

    // Boutons radio pour la Taille
    private JRadioButton rbXS, rbS, rbL, rbXL, rbXXL, rb3XL, rb4XL;
    private ButtonGroup bgTaille;

    // Boutons radio pour l'Etat
    private JRadioButton rbCorrect, rbBon, rbNeuf;
    private ButtonGroup bgEtat;

    JComboBox<String> listeCouleur = new JComboBox<String>();
    private JComboBox<Categorie> cbCategorie;
    private JComboBox<Vente> cbVente;
    private JButton btnAjouter;

    public VueBenevole(Modele modele) {
    	
        this.modele = modele;
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("Enregistrement Article", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        add(lbl, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(8, 1, 15, 15));
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // 1. Donneur
        form.add(new JLabel("Nom Donneur :"));
        txtDonneur = new JTextField();
        form.add(txtDonneur);

        // 2. Vente
        form.add(new JLabel("Vente concernée :"));
        cbVente = new JComboBox<>();
        form.add(cbVente);

        // 3. Nom Article
        form.add(new JLabel("Nom Article :"));
        txtNomArticle = new JTextField();
        form.add(txtNomArticle);

        // 4. Catégorie
        form.add(new JLabel("Catégorie :"));
        cbCategorie = new JComboBox<>();
        form.add(cbCategorie);


        // 5. Couleur
        form.add(new JLabel("Couleur :"));
        listeCouleur.addItem("bleu");
        listeCouleur.addItem("violet");
        listeCouleur.addItem("rose");
        listeCouleur.addItem("rouge");
        listeCouleur.addItem("orange");
        listeCouleur.addItem("jaune");
        listeCouleur.addItem("vert");
        listeCouleur.addItem("noir");
        listeCouleur.addItem("marron");
        listeCouleur.addItem("gris");
        listeCouleur.addItem("blanc");
        form.add(listeCouleur);

        // 6. Taille
        form.add(new JLabel("Taille :"));
        
        // Création d'un panneau interne pour organiser les tailles sur 2 lignes
        JPanel panelTaille = new JPanel(new GridLayout(2, 4));

        rbXS = new JRadioButton("XS");
        rbS = new JRadioButton("S");
        rbL = new JRadioButton("L");
        rbXL = new JRadioButton("XL");
        rbXXL = new JRadioButton("XXL");
        rb3XL = new JRadioButton("3XL");
        rb4XL = new JRadioButton("4XL");

        // Groupe pour exclusion mutuelle
        bgTaille = new ButtonGroup();
        bgTaille.add(rbXS);
        bgTaille.add(rbS);
        bgTaille.add(rbL);
        bgTaille.add(rbXL);
        bgTaille.add(rbXXL);
        bgTaille.add(rb3XL);
        bgTaille.add(rb4XL);

        // Sélection par défaut
        rbL.setSelected(true);

        // Ajout au panneau
        panelTaille.add(rbXS);
        panelTaille.add(rbS);
        panelTaille.add(rbL);
        panelTaille.add(rbXL);
        panelTaille.add(rbXXL);
        panelTaille.add(rb3XL);
        panelTaille.add(rb4XL);

        form.add(panelTaille);

        // 6. Description
        form.add(new JLabel("Description :"));
        txtDescription = new JTextField();
        form.add(txtDescription);

        // 7. Etat
        form.add(new JLabel("Etat :"));
        JPanel panelEtat = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        rbCorrect = new JRadioButton("Etat correct");
        rbBon = new JRadioButton("Bon état");
        rbNeuf = new JRadioButton("Comme neuf");
        rbBon.setSelected(true); // Défaut

        bgEtat = new ButtonGroup();
        bgEtat.add(rbCorrect);
        bgEtat.add(rbBon);
        bgEtat.add(rbNeuf);

        panelEtat.add(rbCorrect);
        panelEtat.add(rbBon);
        panelEtat.add(rbNeuf);
        form.add(panelEtat);

        add(form, BorderLayout.CENTER);

        btnAjouter = new JButton("Enregistrer");
        btnAjouter.addActionListener(this);
        add(btnAjouter, BorderLayout.SOUTH);
    }

    public void setUtilisateur(Utilisateur u) {
        this.utilisateur = u;
    }

    public void rafraichir() {
        cbVente.removeAllItems();
        ArrayList<Vente> lesVentes = modele.getLesVentes();
        for (Vente v : lesVentes)
            cbVente.addItem(v);

        cbCategorie.removeAllItems();
        ArrayList<Categorie> lesCats = modele.getLesCategories();
        for (Categorie c : lesCats)
            cbCategorie.addItem(c);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String donneur = txtDonneur.getText();
            String nomArt = txtNomArticle.getText();
            String description = txtDescription.getText();

            // Récupération de la Taille
            String taille = "L"; // Valeur par défaut
            if (rbXS.isSelected())
                taille = "XS";
            else if (rbS.isSelected())
                taille = "S";
            else if (rbL.isSelected())
                taille = "L";
            else if (rbXL.isSelected())
                taille = "XL";
            else if (rbXXL.isSelected())
                taille = "XXL";
            else if (rb3XL.isSelected())
                taille = "3XL";
            else if (rb4XL.isSelected())
                taille = "4XL";

            // Récupération de l'Etat
            String etat = "Bon état";
            if (rbCorrect.isSelected())
                etat = "Etat correct";
            else if (rbNeuf.isSelected())
                etat = "Comme neuf";

            Vente v = (Vente) cbVente.getSelectedItem();
            Categorie c = (Categorie) cbCategorie.getSelectedItem();
            String couleur = (String) listeCouleur.getSelectedItem();

            if (v == null || c == null)
                return;

            boolean ok = modele.ajouterArticleComplet(nomArt, description, taille, couleur, etat, c.getIdCategorie(),
                    v.getIdVente(), utilisateur.getIdUtilisateur(), donneur);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Article enregistré avec succès !");
                // Reset champs
                txtNomArticle.setText("");
                txtDescription.setText("");
                // Reset sélections
                rbL.setSelected(true);
                rbBon.setSelected(true);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur de saisie : " + ex.getMessage());
        }
    }
}