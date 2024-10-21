package com.mycompany.farminista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String username;
    private String password;
    private String role; // Add a role attribute

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Overloaded constructor to include role
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean register() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO petani (Username, PASSWORD, role) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, this.username);
            stmt.setString(2, this.password);
            stmt.setString(3, this.role); // Insert role into database
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login() {
    try (Connection connection = DatabaseConnection.getConnection()) {
        String query = "SELECT role FROM petani WHERE Username = ? AND PASSWORD = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, this.username);
        stmt.setString(2, this.password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            this.role = rs.getString("role"); // Get the user's role
            return true; // Successful login
        } else {
            return false; // Invalid login
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Return false in case of error
    }
}
    
public boolean isAdmin() {
        return "admin".equalsIgnoreCase(this.role); // Change "admin" to whatever your admin role is
    }

    // Getters and Setters for role if needed
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
