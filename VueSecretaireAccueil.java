import javax.swing.*;
import java.awt.*;

public class VueSecretaireAccueil extends JPanel {

    public VueSecretaireAccueil() {
        // Le GridBagLayout est un gestionnaire de placement qui permet 
        // de centrer très facilement un composant au milieu de la fenêtre.
        this.setLayout(new GridBagLayout());
        
        // Création du texte de bienvenue
        JLabel message = new JLabel("Bienvenue dans votre Espace Secrétariat");
        
        // On modifie l'apparence du texte (Police Arial, en Gras, taille 26)
        message.setFont(new Font("Arial", Font.BOLD, 26));
        
        // On donne une couleur au texte (Rouge, Vert, Bleu)
        message.setForeground(new Color(50, 50, 150));  // style en java un  peu 

        // On ajoute le texte au panneau
        this.add(message);
    }
}