import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Interface administrative permettant la création de nouveaux comptes pour les bénévoles.
 * Propose un formulaire structuré avec un retour visuel direct sur l'état de l'inscription.
 * Communique avec le Modèle pour enregistrer les données de manière persistante en SQL.
 */
public class VueSecretaireBenevoles extends JPanel {
    private Modele modele;
    private JTextField txtLog, txtMdp, txtNom, txtPre;
    private JLabel lblStatus;

    public VueSecretaireBenevoles(Modele m) {
        this.modele = m;
        
        //  Mise en page principale
        // On utilise GridBagLayout sur le panneau principal ("this") pour que 
        // le formulaire reste parfaitement centré, quelle que soit la taille de la fenêtre.
        this.setLayout(new GridBagLayout());

        // --- Construction du Formulaire ---
        // GridLayout(6, 2) crée une grille de 6 lignes et 2 colonnes avec espacement (10px)
        JPanel panForm = new JPanel(new GridLayout(6, 2, 10, 10));
        // Ajout d'une bordure avec titre pour encadrer visuellement les champs
        panForm.setBorder(BorderFactory.createTitledBorder("Inscrire un nouveau Bénévole"));

        // Ligne 1 : Identifiant
        panForm.add(new JLabel(" Identifiant (Login) :"));
        txtLog = new JTextField(15);
        panForm.add(txtLog);

        // Ligne 2 : Mot de passe
        panForm.add(new JLabel(" Mot de passe :"));
        txtMdp = new JTextField(15);
        panForm.add(txtMdp);

        // Ligne 3 : Nom
        panForm.add(new JLabel(" Nom de famille :"));
        txtNom = new JTextField(15);
        panForm.add(txtNom);

        // Ligne 4 : Prénom
        panForm.add(new JLabel(" Prénom :"));
        txtPre = new JTextField(15);
        panForm.add(txtPre);

        //  Feedback et Validation
        // Label initialisé à vide pour afficher les messages de succès ou d'erreur
        lblStatus = new JLabel(" ");
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton btnAdd = new JButton("Inscrire le membre");
        
        // --- Gestion de l'Action ---
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Extraction des textes et appel de la méthode SQL du Modèle
                // Note : Les deux derniers paramètres (email et tel) sont envoyés vides par défaut
                boolean ok = modele.ajouterBenevole(txtLog.getText(), txtMdp.getText(), txtNom.getText(), txtPre.getText(), "", "");
                
                if (ok) {
                    // Si l'insertion réussit : message en vert et nettoyage des champs
                    lblStatus.setText("Succès : Bénévole " + txtNom.getText() + " bien inscrit !");
                    lblStatus.setForeground(new Color(0, 120, 0));
                    
                    txtLog.setText(""); 
                    txtMdp.setText(""); 
                    txtNom.setText(""); 
                    txtPre.setText("");
                } else {
                    // En cas d'échec (ex: login déjà pris) : message d'alerte en rouge
                    lblStatus.setText("Erreur : Impossible d'inscrire ce bénévole.");
                    lblStatus.setForeground(Color.RED);
                }
            }
        });

        //  Assemblage Final 
        // On regroupe le statut, le formulaire et le bouton dans un BorderLayout
        JPanel conteneurFinal = new JPanel(new BorderLayout());
        conteneurFinal.add(lblStatus, BorderLayout.NORTH);
        conteneurFinal.add(panForm, BorderLayout.CENTER);
        conteneurFinal.add(btnAdd, BorderLayout.SOUTH);

        // On ajoute l'ensemble au centre du GridBagLayout de "this"
        this.add(conteneurFinal);
    }
}