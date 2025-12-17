import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VueSecretaireBenevoles extends JPanel {
    private Modele modele;
    private JTextField txtLog, txtMdp, txtNom, txtPre;
    private JLabel lblStatus;

    public VueSecretaireBenevoles(Modele m) {
        this.modele = m;
        // GridBagLayout pour que le formulaire reste bien au centre de la fenêtre
        this.setLayout(new GridBagLayout());

        // Panneau interne pour regrouper les champs de saisie
        JPanel panForm = new JPanel(new GridLayout(6, 2, 10, 10));
        panForm.setBorder(BorderFactory.createTitledBorder("Inscrire un nouveau Bénévole"));

        panForm.add(new JLabel(" Identifiant (Login) :"));
        txtLog = new JTextField(15);
        panForm.add(txtLog);

        panForm.add(new JLabel(" Mot de passe :"));
        txtMdp = new JTextField(15);
        panForm.add(txtMdp);

        panForm.add(new JLabel(" Nom de famille :"));
        txtNom = new JTextField(15);
        panForm.add(txtNom);

        panForm.add(new JLabel(" Prénom :"));
        txtPre = new JTextField(15);
        panForm.add(txtPre);

        // Zone pour afficher si l'inscription a marché (sans popup)
        lblStatus = new JLabel(" ");
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton btnAdd = new JButton("Inscrire le membre");
        
        // Action déclenchée au clic sur le bouton
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // On appelle la méthode du modèle pour faire l'INSERT SQL
                boolean ok = modele.ajouterBenevole(txtLog.getText(), txtMdp.getText(), txtNom.getText(), txtPre.getText(), "", "");
                
                if (ok) {
                    lblStatus.setText("Succès : Bénévole " + txtNom.getText() + " bien inscrit !");
                    lblStatus.setForeground(new Color(0, 120, 0));
                    // On vide les champs pour pouvoir en inscrire un autre
                    txtLog.setText(""); txtMdp.setText(""); txtNom.setText(""); txtPre.setText("");
                } else {
                    lblStatus.setText("Erreur : Impossible d'inscrire ce bénévole.");
                    lblStatus.setForeground(Color.RED);
                }
            }
        });

        // On assemble les morceaux dans un conteneur final
        JPanel conteneurFinal = new JPanel(new BorderLayout());
        conteneurFinal.add(lblStatus, BorderLayout.NORTH);
        conteneurFinal.add(panForm, BorderLayout.CENTER);
        conteneurFinal.add(btnAdd, BorderLayout.SOUTH);

        this.add(conteneurFinal);
    }
}