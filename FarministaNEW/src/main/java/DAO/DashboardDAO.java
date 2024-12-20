package DAO;

import static DAO.DatabaseConnection.getConnection;
import Model.Bibit;
import Model.Supplier;
import Model.TransaksiBeliBibit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DashboardDAO {
    private Connection connection;

    public DashboardDAO(Connection connection) {
        this.connection = connection;
    }

    // Fetch Bibit
    public List<Bibit> getAllBibit() throws SQLException {
        List<Bibit> bibitList = new ArrayList<>();
        String query = "SELECT * FROM bibit";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            bibitList.add(new Bibit(rs.getInt("id_bibit"), rs.getString("nama")));
        }
        return bibitList;
    }

    // Fetch Supplier berdasarkan Bibit
    public List<Supplier> getSuppliersByBibit(int idBibit) throws SQLException {
    List<Supplier> supplierList = new ArrayList<>();
    String query = """
        SELECT 
            s.id_supplier, 
            s.nama, 
            sb.harga 
        FROM supply_bibit sb 
        JOIN supplier s ON sb.id_supplier = s.id_supplier 
        WHERE sb.id_bibit = ?
    """;
    PreparedStatement ps = connection.prepareStatement(query);
    ps.setInt(1, idBibit);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        Supplier supplier = new Supplier(
            rs.getInt("id_supplier"),
            rs.getString("nama"),
            rs.getDouble("harga") // Include price
        );
        System.out.println("Fetched Supplier: " + supplier.getNama() + " for Bibit ID: " + idBibit); // Debug
        supplierList.add(supplier);
    }
    return supplierList;
}


    
    public int getSupplyBibitId(int idBibit, int idSupplier) throws SQLException {
        String query = "SELECT id_supply_bibit FROM supply_bibit WHERE id_bibit = ? AND id_supplier = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idBibit);
            preparedStatement.setInt(2, idSupplier);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id_supply_bibit");
            }
        }
        return 0; // Return 0 or handle error if no matching record is found
    }


    
    // Tambah Transaksi
    public boolean insertTransaksi(TransaksiBeliBibit transaksi) throws SQLException {
    String query = "INSERT INTO transaksi_beli_bibit (id_supply_bibit, id_petani, jumlah, total_harga, status_tanam, tanggal_supply) " +
                   "VALUES (?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        // Set parameter dari objek transaksi
        ps.setInt(1, transaksi.getIdSupplyBibit());  // ID supply bibit
        ps.setInt(2, transaksi.getIdPetani());       // ID petani
        ps.setInt(3, transaksi.getJumlah());         // Jumlah bibit
        ps.setDouble(4, transaksi.getTotalHarga());  // Total harga
        ps.setString(5, transaksi.getStatusTanam()); // Status tanam
        ps.setString(6, transaksi.getTanggalSupply());// Tanggal supply

        // Debugging: Print detail transaksi yang akan dimasukkan
        System.out.println("Preparing to insert transaction: " + transaksi.toString());
        System.out.println("Query: " + ps.toString());

        // Eksekusi query dan return hasil
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        // Debugging: Print error detail
        System.err.println("Error during insertTransaksi:");
        e.printStackTrace();
        throw e; // Rethrow exception agar bisa ditangani di tempat lain
    }
}

    
    public int getIdPetaniByUsername(String username) throws SQLException {
    String query = "SELECT id_petani FROM petani WHERE username = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, username);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id_petani");
            }
        }
    }
    return 0; // Return 0 jika tidak ditemukan
    }
    
    public double getHargaBySupplyBibit(int idBibit, int idSupplier) throws SQLException {
    String query = "SELECT harga FROM supply_bibit WHERE id_bibit = ? AND id_supplier = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, idBibit);
        ps.setInt(2, idSupplier);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("harga");
            }
        }
    }
    return 0.0; // Return 0.0 if no price is found
    }

    
    public static ObservableList<TransaksiBeliBibit> getTransaksiBeliBibitData() {
    // Mendapatkan koneksi ke database
    Connection conn = getConnection();
    // List untuk menyimpan data TransaksiBeliBibit
    ObservableList<TransaksiBeliBibit> list = FXCollections.observableArrayList();

    // Query untuk mengambil data dari beberapa tabel yang terhubung
    String query = """
        SELECT 
            tb.id_transaksi, 
            b.nama AS bibit, 
            s.nama AS supplier, 
            sb.harga, 
            tb.tanggal_supply, 
            tb.jumlah,
            sb.id_supply_bibit -- Mengambil id_supply_bibit
        FROM transaksi_beli_bibit tb
        JOIN supply_bibit sb ON tb.id_supply_bibit = sb.id_supply_bibit
        JOIN bibit b ON sb.id_bibit = b.id_bibit
        JOIN supplier s ON sb.id_supplier = s.id_supplier
    """;

    try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
        int no = 1; // Nomor urut untuk menampilkan data secara sequential
        while (rs.next()) {
            // Mengambil data dari ResultSet sesuai tipe data di database
            int idTransaksi = rs.getInt("id_transaksi");         // ID transaksi
            String bibitName = rs.getString("bibit");            // Nama bibit
            String supplierName = rs.getString("supplier");      // Nama supplier
            double harga = rs.getDouble("harga");                // Harga bibit
            String tanggalSupply = rs.getString("tanggal_supply"); // Tanggal supply
            int jumlah = rs.getInt("jumlah");                   // Jumlah bibit
            int idSupplyBibit = rs.getInt("id_supply_bibit");    // ID Supply Bibit

            // Membuat objek TransaksiBeliBibit
            TransaksiBeliBibit transaksi = new TransaksiBeliBibit(
                no++,                // Nomor urut untuk UI
                idTransaksi,         // ID transaksi
                bibitName,           // Nama bibit
                supplierName,        // Nama supplier
                harga,               // Harga bibit
                jumlah,              // Jumlah
                tanggalSupply,       // Tanggal supply
                idSupplyBibit        // ID Supply Bibit
            );

            // Menambahkan transaksi ke dalam list
            list.add(transaksi);
        }
    } catch (SQLException e) {
        // Menangani error SQL dan mencetak stack trace untuk debugging
        e.printStackTrace();
    }

    // Mengembalikan list data transaksi
    return list;
    }
}

    
