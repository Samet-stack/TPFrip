import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VueMaire extends JPanel {
	
    private Modele modele;
    private JComboBox<Vente> cbVentes;
    private JTable table;
    private DefaultTableModel modelTable;

    public VueMaire(Modele modele) {
        this.modele = modele;
        setLayout(new BorderLayout());

        JPanel panelHaut = new JPanel();
        panelHaut.add(new JLabel("Choisir une vente :"));
        cbVentes = new JComboBox<>();
        JButton btnVoir = new JButton("Voir le catalogue");
        btnVoir.addActionListener(e -> chargerCatalogue());
        panelHaut.add(cbVentes);
        panelHaut.add(btnVoir);
        
        add(panelHaut, BorderLayout.NORTH);

        modelTable = new DefaultTableModel();
        modelTable.setColumnIdentifiers(new String[]{"Nom Article", "Taille", "Description"});
        table = new JTable(modelTable);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void rafraichir() {
        cbVentes.removeAllItems();
        ArrayList<Vente> lesVentes = modele.getLesVentes();
        for (Vente v : lesVentes) cbVentes.addItem(v);
    }

    private void chargerCatalogue() {
        modelTable.setRowCount(0);
        Vente v = (Vente) cbVentes.getSelectedItem();
        if (v != null) {
            ArrayList<Article> arts = modele.getCatalogue(v.getIdVente());
            for (Article a : arts) {
                modelTable.addRow(new Object[]{a.getNom(), a.getTaille(), a.getDescription()});
            }
        }
    }
}