package com.mycompany.farminista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiBibitDAO {

    public void tambahTransaksi(TransaksiBibit transaksi) {
        String sql = "INSERT INTO transaksi_beli_bibit (id_supply_bibit, id_petani, jumlah, total_harga, Status_tanam, tanggal_supply) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, transaksi.getIdSupplyBibit());
            pstmt.setInt(2, transaksi.getIdPetani());
            pstmt.setInt(3, transaksi.getJumlah());
            pstmt.setDouble(4, transaksi.getTotalHarga());
            pstmt.setString(5, transaksi.getStatusTanam());
            pstmt.setDate(6, transaksi.getTanggalSupply());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data berhasil ditambahkan ke transaksi pembelian bibit.");
            } else {
                System.out.println("Gagal menambahkan data ke transaksi pembelian bibit.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TransaksiBibit> getAllTransaksi() {
        List<TransaksiBibit> transaksiList = new ArrayList<>();
        String sql = "SELECT * FROM transaksi_beli_bibit";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TransaksiBibit transaksi = new TransaksiBibit(
                        rs.getInt("id_transaksi"),
                        rs.getInt("id_supply_bibit"),
                        rs.getInt("id_petani"),
                        rs.getInt("jumlah"),
                        rs.getDouble("total_harga"),
                        rs.getString("Status_tanam"),
                        rs.getDate("tanggal_supply")
                );
                transaksiList.add(transaksi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaksiList;
    }

    public void hapusTransaksi(int idTransaksi) {
        String sql = "DELETE FROM transaksi_beli_bibit WHERE id_transaksi = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idTransaksi);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Data berhasil dihapus.");
            } else {
                System.out.println("Data dengan ID tersebut tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editTransaksi(TransaksiBibit transaksi) {
        String sql = "UPDATE transaksi_beli_bibit SET id_supply_bibit = ?, id_petani = ?, jumlah = ?, total_harga = ?, Status_tanam = ?, tanggal_supply = ? WHERE id_transaksi = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, transaksi.getIdSupplyBibit());
            pstmt.setInt(2, transaksi.getIdPetani());
            pstmt.setInt(3, transaksi.getJumlah());
            pstmt.setDouble(4, transaksi.getTotalHarga());
            pstmt.setString(5, transaksi.getStatusTanam());
            pstmt.setDate(6, transaksi.getTanggalSupply());
            pstmt.setInt(7, transaksi.getIdTransaksi());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Data transaksi berhasil diperbarui.");
            } else {
                System.out.println("Gagal memperbarui data transaksi.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
