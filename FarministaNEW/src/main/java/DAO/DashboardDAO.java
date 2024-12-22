package DAO;

import static DAO.DatabaseConnection.getConnection;
import Model.Bibit;
import Model.StatusTanam;
import Model.Supplier;
import Model.TransaksiBeliBibit;
import Model.TransaksiTanamPanen;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Penjualan;
import model.Produk;

public class DashboardDAO {
    private Connection connection;

    public DashboardDAO(Connection connection) {
        this.connection = connection;
    }

    // Fetch Produk untuk ditampilkan pada tabel produk berdasarkan status
    public ObservableList<StatusTanam> getProdukByStatus(String status, int idPetani) {
        ObservableList<StatusTanam> statusList = FXCollections.observableArrayList();
        String query = """
            SELECT 
                tb.id_transaksi,
                b.nama AS bibit, 
                s.nama AS supplier, 
                tb.status_tanam,
                tb.tanggal_supply,
                sb.id_supply_bibit
        FROM transaksi_beli_bibit tb
        JOIN supply_bibit sb ON tb.id_supply_bibit = sb.id_supply_bibit
        JOIN bibit b ON sb.id_bibit = b.id_bibit
        JOIN supplier s ON sb.id_supplier = s.id_supplier
        WHERE tb.status_tanam = ? AND tb.id_petani = ?
        """;

    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setString(1, status);
        ps.setInt(2, idPetani);
        try (ResultSet rs = ps.executeQuery()) {
            int no = 1;
            while (rs.next()) {
                StatusTanam transaksi = new StatusTanam(
                    no++,
                    rs.getInt("id_transaksi"),
                    rs.getString("bibit"),
                    rs.getString("supplier"),
                    rs.getString("status_tanam"),
                    rs.getString("tanggal_supply"),
                    rs.getInt("id_supply_bibit")
                );
                // Pernyataan debug untuk memastikan data diambil dengan benar
                System.out.println("Fetched: " + transaksi.getBibit() + ", " + transaksi.getSupplier());
                statusList.add(transaksi);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching getProdukByStatus data:");
            e.printStackTrace();
            }
        return statusList;
    }
    public ObservableList<TransaksiTanamPanen> getTransaksiTanamPanen(int idPetani) {
    ObservableList<TransaksiTanamPanen> produkList = FXCollections.observableArrayList();
    String query = """
        SELECT 
            p.id_produk,
            b.nama AS bibit, 
            p.stok,
            p.harga_produk,
            b.id_bibit
        FROM produk p
        JOIN bibit b ON p.id_bibit = b.id_bibit
        WHERE p.id_petani = ?
    """;

    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, idPetani);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TransaksiTanamPanen transaksi = new TransaksiTanamPanen(
                    rs.getInt("id_produk"),
                    rs.getString("bibit"),
                    rs.getInt("stok"),
                    rs.getDouble("harga_produk"),
                    rs.getInt("id_bibit")
                );
                produkList.add(transaksi);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error fetching TransaksiTanamPanen data:");
        e.printStackTrace();
    }
    return produkList;
}

    
    // Fetch Bibit
    public List<Bibit> getAllBibit() throws SQLException {
        List<Bibit> bibitList = new ArrayList<>();
        String query = "SELECT * FROM bibit";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                bibitList.add(new Bibit(rs.getInt("id_bibit"), rs.getString("nama")));
            }
        }
        return bibitList;
    }

    public String getBibitNameById(int idBibit) {
    String query = "SELECT nama FROM bibit WHERE id_bibit = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, idBibit);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("nama");
            }
        }
    } catch (SQLException e) {
        System.err.println("Error fetching bibit name: " + e.getMessage());
    }
    return null; // Return null if no matching record is found
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
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idBibit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Supplier supplier = new Supplier(
                        rs.getInt("id_supplier"),
                        rs.getString("nama"),
                        rs.getDouble("harga")
                    );
                    supplierList.add(supplier);
                }
            }
        }
        return supplierList;
    }

    public static ObservableList<TransaksiBeliBibit> getTransaksiBeliBibitData(int idPetani) {
        Connection conn = getConnection();
        ObservableList<TransaksiBeliBibit> list = FXCollections.observableArrayList();

        String query = """
            SELECT 
                tb.id_transaksi, 
                b.nama AS bibit, 
                s.nama AS supplier, 
                sb.harga, 
                tb.tanggal_supply, 
                tb.jumlah,
                sb.id_supply_bibit
            FROM transaksi_beli_bibit tb
            JOIN supply_bibit sb ON tb.id_supply_bibit = sb.id_supply_bibit
            JOIN bibit b ON sb.id_bibit = b.id_bibit
            JOIN supplier s ON sb.id_supplier = s.id_supplier
            WHERE tb.id_petani = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idPetani); // Set the id_petani parameter

            try (ResultSet rs = ps.executeQuery()) {
                int no = 1;
                while (rs.next()) {
                    TransaksiBeliBibit transaksi = new TransaksiBeliBibit(
                        no++,
                        rs.getInt("id_transaksi"),
                        rs.getString("bibit"),
                        rs.getString("supplier"),
                        rs.getDouble("harga"),
                        rs.getInt("jumlah"),
                        rs.getString("tanggal_supply"),
                        rs.getInt("id_supply_bibit")
                    );
                    list.add(transaksi);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching TransaksiBeliBibit data:");
            e.printStackTrace();
        }
        return list;
    }




    // Insert Transaksi Beli Bibit
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
 

    // Get Supply Bibit ID
    public int getSupplyBibitId(int idBibit, int idSupplier) {
    String query = "SELECT id_supply_bibit FROM supply_bibit WHERE id_bibit = ? AND id_supplier = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, idBibit);
        ps.setInt(2, idSupplier);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id_supply_bibit");
            }
        }
    } catch (SQLException e) {
        System.err.println("Error fetching supply_bibit ID: " + e.getMessage());
    }
    return -1; // Return -1 if no matching record is found
}

    // Get ID Petani by Username
    public int getIdPetaniByUsername(String username) {
        String query = "SELECT id_petani FROM petani WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_petani");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching petani ID: " + e.getMessage());
        }
        return 0;
    }

    // Get Harga by Supply Bibit
    public double getHargaBySupplyBibit(int idBibit, int idSupplier) {
        String query = "SELECT harga FROM supply_bibit WHERE id_bibit = ? AND id_supplier = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idBibit);
            ps.setInt(2, idSupplier);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("harga");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching harga supply_bibit: " + e.getMessage());
        }
        return 0.0;
    }
    
    public boolean tambahStatusTanam(StatusTanam statusTanam) throws SQLException {
    int idSupplyBibit = getSupplyBibitId(statusTanam.getIdBibit(), statusTanam.getIdSupplier());
    
    if (idSupplyBibit == -1) {
        System.err.println("No matching supply_bibit found for the given bibit and supplier.");
        return false; // Atau lempar pengecualian
    }

    // SQL untuk memperbarui status
    String sql = "UPDATE transaksi_beli_bibit SET status_tanam = ?, tanggal_supply = ? WHERE id_supply_bibit = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        // Set parameter dengan benar
        stmt.setString(1, statusTanam.getStatus()); // Status baru
        stmt.setString(2, statusTanam.getTanggalSupply()); // Tanggal supply baru
        stmt.setInt(3, idSupplyBibit); // ID supply_bibit yang akan diperbarui

        // Eksekusi pembaruan dan kembalikan true jika berhasil
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error during updating StatusTanam:");
        e.printStackTrace();
        throw e; // Lempar kembali pengecualian untuk penanganan lebih lanjut
    }
}

   public boolean hapusStatusTanam(int idTransaksi) throws SQLException {
    String query = "DELETE FROM transaksi_beli_bibit WHERE id_transaksi = ?"; 
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, idTransaksi);
        return stmt.executeUpdate() > 0; // Mengembalikan true jika berhasil
    }
}
    public boolean updateStatusTanam(int idTransaksi, String status, String tanggalSupply) throws SQLException {
    String query = "UPDATE transaksi_beli_bibit SET " +
                   "status_tanam = ?, " +
                   "tanggal_supply = ? " +
                   "WHERE id_transaksi = ?";

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, status); // status_tanam
        stmt.setString(2, tanggalSupply); // tanggal_supply
        stmt.setInt(3, idTransaksi); // id_transaksi
        return stmt.executeUpdate() > 0; // Mengembalikan true jika berhasil
    }
}
    
    public boolean tambahTransaksiTanamPanen(TransaksiTanamPanen transaksi) throws SQLException {
       String query = "INSERT INTO produk (id_bibit, stok, harga_produk, id_petani) VALUES (?, ?, ?, ?)";
       try (PreparedStatement stmt = connection.prepareStatement(query)) {
           stmt.setInt(1, transaksi.getIdBibit()); // Using ID bibit
           stmt.setInt(2, transaksi.getStok());
           stmt.setDouble(3, transaksi.getHargaProduk());
           stmt.setInt(4, transaksi.getIdPetani()); // Set the id_petani
           return stmt.executeUpdate() > 0; // Return true if successful
       }
   }


    public boolean hapusTransaksiTanamPanen(int idProduk) throws SQLException {
    String query = "DELETE FROM produk WHERE id_produk = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, idProduk);
        return stmt.executeUpdate() > 0; // Mengembalikan true jika berhasil
    }
}

    public boolean updateTransaksiTanamPanen(TransaksiTanamPanen transaksi) throws SQLException {
    String query = "UPDATE produk SET stok = ?, harga_produk = ? WHERE id_produk = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, transaksi.getStok()); // Set stok
        stmt.setDouble(2, transaksi.getHargaProduk()); // Set harga_produk
        stmt.setInt(3, transaksi.getIdProduk()); // Set id_produk untuk klausa WHERE

        // Debugging output
        System.out.println("Updating produk with ID: " + transaksi.getIdProduk());
        System.out.println("New stok: " + transaksi.getStok());
        System.out.println("New harga: " + transaksi.getHargaProduk());

        return stmt.executeUpdate() > 0; // Mengembalikan true jika pembaruan berhasil
    } catch (SQLException e) {
        System.err.println("SQL Error: " + e.getMessage());
        throw e; // Rethrow the exception after logging
    }
}



    public List<Produk> getProdukByPetani(int idPetani) throws SQLException {
    List<Produk> produkList = new ArrayList<>();
    String query = """
        SELECT 
            p.id_produk, 
            p.id_bibit 
        FROM produk p 
        WHERE p.id_petani = ?
    """;

    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, idPetani);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int idProduk = rs.getInt("id_produk");
                int idBibit = rs.getInt("id_bibit");
                String bibit = getBibitNameById(idBibit); // Method to get the bibit name based on id_bibit
                
                produkList.add(new Produk(idProduk, idPetani, idBibit, bibit));
            }
        }
    }
    return produkList;
}



    // Get Harga by Product
public double getHargaByProduct(int idProduk) {
    String query = "SELECT harga FROM produk WHERE id_produk = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, idProduk);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("harga");
            }
        }
    } catch (SQLException e) {
        System.err.println("Error fetching harga produk: " + e.getMessage());
    }
    return 0.0;
}

public boolean ModelPenjualan(Penjualan penjualan) {
    String query = "INSERT INTO penjualan (id_produk, id_petani, jumlah, total_harga) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, penjualan.getProdukId());
        ps.setInt(2, penjualan.getIdPetani()); // Assuming you have a way to set idPetani
        ps.setInt(3, penjualan.getJumlah());
        ps.setDouble(4, penjualan.getTotalHarga());
        
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; // Return true if the insertion was successful
    } catch (SQLException e) {
        System.err.println("Error inserting penjualan: " + e.getMessage());
        return false;
    }
}

    public List<Penjualan> getPenjualanData(int idPetani) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean insertPenjualan(Penjualan penjualan) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

public boolean tambahProdukJual(Penjualan penjualan) throws SQLException {
    String query = "INSERT INTO penjualan (id_produk, jumlah, total_harga) VALUES (?, ?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, penjualan.getProdukId()); // Menggunakan ID produk
        stmt.setInt(2, penjualan.getJumlah());
        stmt.setDouble(3, penjualan.getTotalHarga());
        return stmt.executeUpdate() > 0; // Mengembalikan true jika berhasil
    }
}

    
}