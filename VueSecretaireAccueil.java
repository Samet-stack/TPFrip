import javax.swing.*;
import java.awt.*;

/**
 * Écran de bienvenue principal pour le profil Secrétariat.
 * Sert de page d'atterrissage après une authentification réussie.
 * Utilise un positionnement centré pour offrir une interface épurée genre pro.
 */
public class VueSecretaireAccueil extends JPanel {

    public VueSecretaireAccueil() {
        // Configuration du Gestionnaire de Placement 
        // Le GridBagLayout est choisi ici car, sans contraintes spécifiques, 
        // il place par défaut le composant ajouté au centre exact du panneau.
        this.setLayout(new GridBagLayout());
        
        //  Création et Style du message d'accueil
        JLabel message = new JLabel("Bienvenue dans votre Espace Secrétariat");
        
        // Paramétrage de la typographie : Police sans-serif, mise en gras pour le titre
        message.setFont(new Font("Arial", Font.BOLD, 26));
        
        // Définition d'une couleur personnalisée  via les composantes RGB
        // Le commentaire "style en java un peu" est bien noté.
        message.setForeground(new Color(50, 50, 150)); 

        //  Ajout du composant au conteneur 
        // On insère le label dans le panneau "this" qui sera ensuite affiché dans la Fenetre
        this.add(message);
    }
}