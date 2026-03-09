import java.sql.*;

public class CheckLogins {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://172.16.203.112/fripouilles?serverTimezone=UTC", "sio", "slam");
            System.out.println("Connexion OK");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT login, mdp, role FROM utilisateur");
            
            System.out.println("Utilisateurs en base :");
            while (rs.next()) {
                System.out.println("- Login: " + rs.getString("login") + " | Mdp: " + rs.getString("mdp") + " | Role: " + rs.getString("role"));
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
