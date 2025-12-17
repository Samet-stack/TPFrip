import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VueSecretaire extends JPanel {
	
    private Modele modele;
    private JTextField txtTitre, txtDate, txtLieu;
    private JTable table;
    private DefaultTableModel modelTable;

    public VueSecretaire(Modele modele) {
        this.modele = modele;
        setLayout(new BorderLayout());

        // en haut Formulaire ajout vente
        JPanel panelHaut = new JPanel(new GridLayout(4, 2));
        panelHaut.add(new JLabel("Titre Vente :"));
        txtTitre = new JTextField();
        panelHaut.add(txtTitre);

        panelHaut.add(new JLabel("Date (YYYY-MM-DD) :"));
        txtDate = new JTextField();
        panelHaut.add(txtDate);

        panelHaut.add(new JLabel("Lieu :"));
        txtLieu = new JTextField();
        panelHaut.add(txtLieu);

        JButton btnAjout = new JButton("Créer la vente");
        btnAjout.addActionListener(e -> actionAjouter());
        panelHaut.add(btnAjout);

        add(panelHaut, BorderLayout.NORTH);

        // au centre Liste des ventes
        modelTable = new DefaultTableModel();
        modelTable.setColumnIdentifiers(new String[]{"ID", "Titre", "Date", "Lieu", "Statut"});
        table = new JTable(modelTable);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void rafraichir() {
        modelTable.setRowCount(0);
        ArrayList<Vente> lesVentes = modele.getLesVentes();
        for (Vente v : lesVentes) {
            modelTable.addRow(new Object[]{v.getIdVente(), v.getTitre(), v.getDateVente(), v.getLieu(), v.getStatut()});
        }
    }

    private void actionAjouter() {
        String t = txtTitre.getText();
        String d = txtDate.getText();
        String l = txtLieu.getText();
        if (modele.ajouterVente(t, d, l)) {
            JOptionPane.showMessageDialog(this, "Vente créée");
            rafraichir();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur (Format date ?)");
        }
    }
}