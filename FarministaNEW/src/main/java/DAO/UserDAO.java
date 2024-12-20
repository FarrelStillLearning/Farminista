package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public String getUserRole(String username, String password) {
        String sql = "SELECT role FROM petani WHERE username = ? AND password = ?";
        Connection connect = DatabaseConnection.getConnection();

        try (PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setString(1, username);
            prepare.setString(2, password);
            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                return result.getString("role"); // Return the user's role
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Return null if no user is found or an error occurs
    }
    public boolean registerUser(String username, String password, String role) {
    String sql = "INSERT INTO petani (username, password, role) VALUES (?, ?, ?)";
    Connection connect = DatabaseConnection.getConnection();

    try (PreparedStatement prepare = connect.prepareStatement(sql)) {
        prepare.setString(1, username);
        prepare.setString(2, password);
        prepare.setString(3, role);

        int result = prepare.executeUpdate();
        return result > 0; // Returns true if insertion is successful
    } catch (Exception e) {
        e.printStackTrace();
        return false;
        }
    }
}
