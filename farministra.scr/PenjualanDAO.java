package com.mycompany.farminista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PenjualanDAO {
    private Connection connection;

    public PenjualanDAO() {
        
    }

    // Menambahkan penjualan
    public void addPenjualan(Penjualan penjualan) {
        String sql = "INSERT INTO penjualan (id_produk, jumlah, total) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, penjualan.getIdProduk());
            pstmt.setInt(2, penjualan.getJumlah());
            pstmt.setDouble(3, penjualan.getTotal());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Menampilkan semua penjualan
    public List<Penjualan> getAllPenjualan() {
        List<Penjualan> penjualanList = new ArrayList<>();
        String sql = "SELECT * FROM penjualan";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Penjualan penjualan = new Penjualan(
                        rs.getInt("id_penjualan"),
                        rs.getInt("id_produk"),
                        rs.getInt("jumlah"),
                        rs.getDouble("total")
                );
                penjualanList.add(penjualan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return penjualanList;
    }

    // Menghapus penjualan berdasarkan ID
    public void deletePenjualan(int idPenjualan) {
        String sql = "DELETE FROM penjualan WHERE id_penjualan = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPenjualan);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
