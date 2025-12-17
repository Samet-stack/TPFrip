import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VueAuth extends JPanel implements ActionListener {
    
    private Modele modele;
    private Fenetre fenetre;
    private JTextField txtLogin;
    private JPasswordField txtMdp;
    private JButton btnValider;

    public VueAuth(Modele modele, Fenetre fenetre) {
        this.modele = modele;
        this.fenetre = fenetre;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;

        add(new JLabel("Login :"), gbc);
        gbc.gridx = 1;
        txtLogin = new JTextField(15);
        add(txtLogin, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        txtMdp = new JPasswordField(15);
        add(txtMdp, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        btnValider = new JButton("Se connecter");
        btnValider.addActionListener(this);
        add(btnValider, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String login = txtLogin.getText();
        String mdp = new String(txtMdp.getPassword());
        
        Utilisateur u = modele.authentifier(login, mdp);
        if (u != null) {
            txtLogin.setText("");
            txtMdp.setText("");
            fenetre.setUtilisateurConnecte(u);
        } else {
            JOptionPane.showMessageDialog(this, "Erreur login/mdp", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}