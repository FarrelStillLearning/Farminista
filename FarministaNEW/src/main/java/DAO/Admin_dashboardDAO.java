package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BibitData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Admin_dashboardDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/farminista";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public ObservableList<BibitData> getAllBibitData() {
        ObservableList<BibitData> dataList = FXCollections.observableArrayList();

        String query = """
            SELECT b.nama AS bibit, s.nama AS supplier, sb.harga AS harga
            FROM bibit b
            JOIN supply_bibit sb ON b.id = sb.bibit_id
            JOIN supplier s ON sb.supplier_id = s.id;
        """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                dataList.add(new BibitData(
                        resultSet.getString("bibit"),
                        resultSet.getString("supplier"),
                        resultSet.getDouble("harga")
                ));
            }
        } catch (Exception e) {
        }

        return dataList;
    }
}
