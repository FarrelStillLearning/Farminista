package DAO;  
  
import static DAO.DatabaseConnection.getConnection;  
import Model.Bibit;  
import Model.StatusTanam;  
import Model.Supplier;  
import Model.TanamPanen;  
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
  
    // Ensure connection is valid before executing queries  
    private void checkConnection() throws SQLException {  
        if (connection == null || connection.isClosed()) {  
            connection = getConnection(); // Re-establish connection  
        }  
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
                sb.id_supply_bibit,  
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
                        rs.getInt("id_supply_bibit"),  
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
  
    public ObservableList<TransaksiBeliBibit> getTransaksiBeliBibitData(int idPetani) {  
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
  
    public ObservableList<TanamPanen> getTanamPanen(int idPetani) {  
        Connection conn = getConnection(); // Pastikan Anda memiliki metode untuk mendapatkan koneksi database    
        ObservableList<TanamPanen> tanamPanenList = FXCollections.observableArrayList();  
  
        String query = """  
            SELECT    
                ttp.id_tanam_panen,    
                b.nama AS bibit,    
                s.nama AS supplier,    
                tb.tanggal_supply AS tanggalSupply,    
                ttp.waktu_tanam AS tanggalTanam,    
                ttp.waktu_panen AS tanggalPanen,    
                tb.id_transaksi AS idTransaksi  -- Ambil id_transaksi    
            FROM transaksi_beli_bibit tb    
            LEFT JOIN transaksi_tanam_panen ttp ON tb.id_transaksi = ttp.id_transaksi    
            JOIN supply_bibit sb ON tb.id_supply_bibit = sb.id_supply_bibit    
            JOIN bibit b ON sb.id_bibit = b.id_bibit    
            JOIN supplier s ON sb.id_supplier = s.id_supplier    
            WHERE tb.id_petani = ?    
        """;  
  
        try (PreparedStatement ps = conn.prepareStatement(query)) {  
            ps.setInt(1, idPetani);  
  
            try (ResultSet rs = ps.executeQuery()) {  
                int no = 0;  
                while (rs.next()) {  
                    TanamPanen tanamPanen = new TanamPanen(  
                        no++,  
                        rs.getString("bibit"),  
                        rs.getString("supplier"),  
                        rs.getString("tanggalSupply"),  
                        rs.getString("tanggalTanam"),  
                        rs.getString("tanggalPanen"),  
                        rs.getInt("id_tanam_panen"),  
                        rs.getInt("idTransaksi") // Ambil id_transaksi    
                    );  
                    tanamPanenList.add(tanamPanen);  
                }  
            }  
        } catch (SQLException e) {  
            System.err.println("Error fetching TanamPanen data:");  
            e.printStackTrace();  
        }  
        return tanamPanenList;  
    }  
  
    public boolean updateTransaksiTanamPanen(TanamPanen tanamPanen) throws SQLException {  
        Connection conn = getConnection(); // Pastikan Anda memiliki metode untuk mendapatkan koneksi database    
        String query = "UPDATE transaksi_tanam_panen SET waktu_tanam = ?, waktu_panen = ? WHERE id_tanam_panen = ?";  
        try (PreparedStatement stmt = conn.prepareStatement(query)) {  
            stmt.setString(1, tanamPanen.getTanggalTanam());  
            stmt.setString(2, tanamPanen.getTanggalPanen());  
            stmt.setInt(3, tanamPanen.getIdTanamPanen()); // Gunakan id_tanam_panen untuk update  
  
            // Tambahkan log untuk melihat nilai yang akan digunakan    
            System.out.println("Updating TanamPanen: " +  
                "waktu_tanam = " + tanamPanen.getTanggalTanam() +  
                ", waktu_panen = " + tanamPanen.getTanggalPanen() +  
                ", id_tanam_panen = " + tanamPanen.getIdTanamPanen());  
  
            return stmt.executeUpdate() > 0; // Kembalikan true jika berhasil    
        }  
    }  
  
    public boolean insertTransaksi(TransaksiBeliBibit transaksi) throws SQLException {  
        // Cek apakah supply bibit sudah ada  
        if (isSupplyBibitExists(transaksi.getIdSupplyBibit())) {  
            // Jika sudah ada, tambahkan jumlahnya  
            return updateTransactionQuantity(transaksi.getIdSupplyBibit(), transaksi.getIdPetani(), transaksi.getJumlah(), transaksi.getTotalHarga());  
        } else {  
            // Jika belum ada, masukkan transaksi baru  
            String query = "INSERT INTO transaksi_beli_bibit (id_supply_bibit, id_petani, jumlah, total_harga, status_tanam, tanggal_supply) VALUES (?, ?, ?, ?, ?, ?)";  
            try (PreparedStatement ps = connection.prepareStatement(query)) {  
                ps.setInt(1, transaksi.getIdSupplyBibit());  
                ps.setInt(2, transaksi.getIdPetani());  
                ps.setInt(3, transaksi.getJumlah());  
                ps.setDouble(4, transaksi.getTotalHarga());  
                ps.setString(5, transaksi.getStatusTanam());  
                ps.setString(6, transaksi.getTanggalSupply());  
                return ps.executeUpdate() > 0; // Kembalikan true jika berhasil  
            }  
        }  
    }  
  
    public boolean checkTransactionExists(int idSupplyBibit, int idPetani) throws SQLException {  
        String query = "SELECT COUNT(*) FROM transaksi_beli_bibit WHERE id_supply_bibit = ? AND id_petani = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, idSupplyBibit);  
            ps.setInt(2, idPetani);  
            ResultSet rs = ps.executeQuery();  
            if (rs.next()) {  
                return rs.getInt(1) > 0; // Return true if exists  
            }  
        }  
        return false; // Return false if not found  
    }  
  
    public boolean updateTransactionQuantity(int idSupplyBibit, int idPetani, int jumlah, double totalHarga) throws SQLException {  
        String query = "UPDATE transaksi_beli_bibit SET jumlah = ?, total_harga = ? WHERE id_supply_bibit = ? AND id_petani = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, jumlah);  
            ps.setDouble(2, totalHarga);  
            ps.setInt(3, idSupplyBibit);  
            ps.setInt(4, idPetani);  
            return ps.executeUpdate() > 0; // Return true if successful  
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
  
    public boolean isSupplyBibitExists(int idSupplyBibit) throws SQLException {  
        String query = "SELECT COUNT(*) FROM supply_bibit WHERE id_supply_bibit = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, idSupplyBibit);  
            try (ResultSet rs = ps.executeQuery()) {  
                if (rs.next()) {  
                    return rs.getInt(1) > 0; // Return true if exists  
                }  
            }  
        }  
        return false; // Return false if not found  
    }  
  
    public boolean isPetaniValid(int petaniId) {  
        String query = "SELECT COUNT(*) FROM petani WHERE id_petani = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, petaniId);  
            ResultSet rs = ps.executeQuery();  
            if (rs.next()) {  
                return rs.getInt(1) > 0; // Returns true if petani exists  
            }  
        } catch (SQLException e) {  
            System.err.println("Error checking petani validity: " + e.getMessage());  
        }  
        return false; // Petani does not exist  
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
        return 0.0; // Kembalikan 0 jika tidak ditemukan  
    }  
  
    public boolean hapusTransaksiBeliBibit(int idTransaksi) throws SQLException {  
        String query = "DELETE FROM transaksi_beli_bibit WHERE id_transaksi = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, idTransaksi);  
            return ps.executeUpdate() > 0; // Mengembalikan true jika berhasil  
        } catch (SQLException e) {  
            System.err.println("SQL Error: " + e.getMessage());  
            throw e; // Rethrow the exception after logging  
        }  
    }  
  
    // Method to update an existing TransaksiBeliBibit  
    public boolean updateTransaksiBeliBibit(TransaksiBeliBibit transaksi) throws SQLException {  
        String query = "UPDATE transaksi_beli_bibit SET id_supply_bibit = ?, jumlah = ?, total_harga = ?, status_tanam = ?, tanggal_supply = ? WHERE id_transaksi = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, transaksi.getIdSupplyBibit());  
            ps.setInt(2, transaksi.getJumlah());  
            ps.setDouble(3, transaksi.getTotalHarga());  
            ps.setString(4, transaksi.getStatusTanam());  
            ps.setString(5, transaksi.getTanggalSupply());  
            ps.setInt(6, transaksi.getIdTransaksi());  
            return ps.executeUpdate() > 0; // Return true if successful  
        }  
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
  
    public boolean updateStatusTanam(int idTransaksi, String newStatus) throws SQLException {  
        String query = "UPDATE transaksi_beli_bibit SET status_tanam = ? WHERE id_transaksi = ?";  
        try (PreparedStatement stmt = connection.prepareStatement(query)) {  
            stmt.setString(1, newStatus);  
            stmt.setInt(2, idTransaksi);  
            return stmt.executeUpdate() > 0; // Mengembalikan true jika pembaruan berhasil  
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
  
    public boolean isBibitExistsInProduk(int idBibit) throws SQLException {  
        String query = "SELECT COUNT(*) FROM produk WHERE id_bibit = ?";  
        try (PreparedStatement stmt = connection.prepareStatement(query)) {  
            stmt.setInt(1, idBibit); // Menggunakan ID bibit  
            ResultSet rs = stmt.executeQuery();  
            if (rs.next()) {  
                return rs.getInt(1) > 0; // Mengembalikan true jika bibit ada  
            }  
        }  
        return false; // Mengembalikan false jika bibit tidak ada  
    }  
  
    public boolean hapusTransaksiTanamPanen(int idProduk) throws SQLException {  
        String query = "DELETE FROM produk WHERE id_produk = ?";  
        try (PreparedStatement stmt = connection.prepareStatement(query)) {  
            stmt.setInt(1, idProduk);  
            return stmt.executeUpdate() > 0; // Mengembalikan true jika penghapusan berhasil  
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
                p.id_bibit,   
                b.nama AS bibit   
            FROM produk p  
            JOIN bibit b ON p.id_bibit = b.id_bibit  
            WHERE p.id_petani = ?  
        """;  
  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, idPetani);  
            try (ResultSet rs = ps.executeQuery()) {  
                while (rs.next()) {  
                    int idProduk = rs.getInt("id_produk");  
                    int idBibit = rs.getInt("id_bibit");  
                    String bibit = rs.getString("bibit"); // Pastikan ini mengambil nama bibit yang benar  
  
                    // Create a new Produk object and add it to the list  
                    Produk produk = new Produk(idProduk, idPetani, idBibit, bibit);  
                    produkList.add(produk);  
                }  
            }  
        } catch (SQLException e) {  
            System.err.println("Error fetching products for petani ID " + idPetani + ": " + e.getMessage());  
            throw e; // Rethrow the exception for further handling  
        }  
  
        return produkList;  
    }  
  
    public List<Bibit> getBibitByProduk(int idProduk) throws SQLException {  
        List<Bibit> bibitList = new ArrayList<>();  
        String query = """  
            SELECT b.id_bibit, b.nama   
            FROM bibit b  
            JOIN produk p ON b.id_bibit = p.id_bibit  
            WHERE p.id_produk = ?  
        """;  
  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, idProduk);  
            try (ResultSet rs = ps.executeQuery()) {  
                while (rs.next()) {  
                    Bibit bibit = new Bibit(  
                        rs.getInt("id_bibit"),  
                        rs.getString("nama")  
                    );  
                    bibitList.add(bibit);  
                }  
            }  
        } catch (SQLException e) {  
            System.err.println("Error fetching bibit by produk: " + e.getMessage());  
            throw e; // Rethrow the exception for further handling  
        }  
        return bibitList; // Return the list of Bibit  
    }  
  
    public double getHargaByProduct(int idProduk) {  
        String query = "SELECT harga_produk FROM produk WHERE id_produk = ?"; // Pastikan nama kolom sesuai  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, idProduk);  
            try (ResultSet rs = ps.executeQuery()) {  
                if (rs.next()) {  
                    return rs.getDouble("harga_produk"); // Pastikan nama kolom sesuai  
                }  
            }  
        } catch (SQLException e) {  
            System.err.println("Error fetching harga produk: " + e.getMessage());  
        }  
        return 0.0; // Kembalikan 0 jika tidak ditemukan  
    }  
  
    public boolean updateStokProduk(int idProduk, int jumlah) throws SQLException {  
        String query = "UPDATE produk SET stok = stok + ? WHERE id_produk = ?";  
        try (PreparedStatement stmt = connection.prepareStatement(query)) {  
            stmt.setInt(1, jumlah); // Jumlah yang akan ditambahkan atau dikurangi  
            stmt.setInt(2, idProduk); // ID produk yang akan diperbarui  
            return stmt.executeUpdate() > 0; // Mengembalikan true jika berhasil  
        } catch (SQLException e) {  
            System.err.println("SQL Error: " + e.getMessage());  
            throw e; // Rethrow the exception after logging  
        }  
    }  
  
    public boolean ModelPenjualan(Penjualan penjualan) {  
        // Ambil harga_produk dari database berdasarkan id_produk  
        double hargaProduk = getHargaByProduct(penjualan.getProdukId());  
        if (hargaProduk <= 0) {  
            System.err.println("Harga produk tidak valid untuk ID: " + penjualan.getProdukId());  
            return false; // Jika harga tidak valid, kembalikan false  
        }  
  
        double totalHarga = penjualan.getJumlah() * hargaProduk; // Hitung total harga  
  
        String query = "INSERT INTO penjualan (id_produk, jumlah, total, id_petani) VALUES (?, ?, ?, ?)"; // Ganti total_harga dengan total  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, penjualan.getProdukId());  
            ps.setInt(2, penjualan.getJumlah());  
            ps.setDouble(3, totalHarga); // Gunakan totalHarga yang sudah dihitung  
            ps.setInt(4, penjualan.getIdPetani());  
  
            int rowsAffected = ps.executeUpdate();  
  
            // Update stok produk setelah penjualan berhasil ditambahkan  
            if (rowsAffected > 0) {  
                updateStokProduk(penjualan.getProdukId(), penjualan.getJumlah());  
                return true; // Return true if the insertion was successful  
            }  
        } catch (SQLException e) {  
            System.err.println("Error inserting penjualan: " + e.getMessage());  
            return false;  
        }  
        return false; // Return false if the insertion failed  
    }  
  
    // Metode untuk mendapatkan harga produk berdasarkan id_produk  
    private double getHargaProdukById(int idProduk) {  
        String query = "SELECT harga_produk FROM produk WHERE id_produk = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, idProduk);  
            try (ResultSet rs = ps.executeQuery()) {  
                if (rs.next()) {  
                    return rs.getDouble("harga_produk");  
                }  
            }  
        } catch (SQLException e) {  
            System.err.println("Error fetching harga produk: " + e.getMessage());  
        }  
        return 0.0; // Kembalikan 0 jika tidak ditemukan  
    }  
  
    public ObservableList<Penjualan> getPenjualanData(int idPetani) {  
        Connection conn = getConnection(); // Mendapatkan koneksi ke database  
        ObservableList<Penjualan> penjualanList = FXCollections.observableArrayList(); // Membuat list observable untuk penjualan  
  
        String query = """  
            SELECT   
                p.id_penjualan,  
                b.nama AS bibit,  -- Ambil nama bibit dari tabel bibit  
                p.jumlah,   
                p.total,   
                pr.id_produk,  
                b.id_bibit  
            FROM penjualan p   
            JOIN produk pr ON p.id_produk = pr.id_produk  -- Join dengan tabel produk  
            JOIN bibit b ON pr.id_bibit = b.id_bibit  -- Join dengan tabel bibit untuk mendapatkan nama bibit  
            WHERE p.id_petani = ?  
        """;  
  
        try (PreparedStatement ps = conn.prepareStatement(query)) {  
            ps.setInt(1, idPetani);  
            try (ResultSet rs = ps.executeQuery()) {  
                int no = 1;  
                while (rs.next()) {  
                    Penjualan penjualan = new Penjualan(  
                        no++,  
                        rs.getInt("id_penjualan"),  
                        rs.getString("bibit"),  
                        rs.getInt("jumlah"),  
                        rs.getDouble("total"),  
                        rs.getInt("id_produk"),  
                        rs.getInt("id_bibit")  
                    );  
                    penjualanList.add(penjualan); // Tambahkan objek penjualan ke list  
                }  
            }  
        } catch (SQLException e) {  
            System.err.println("Error fetching penjualan data:");  
            e.printStackTrace();  
        }  
  
        return penjualanList; // Kembalikan list penjualan  
    }  
  
    // Mengambil jumlah produk berdasarkan ID produk  
    public int getJumlahByProduct(int idProduk) throws SQLException {  
        String query = "SELECT jumlah FROM produk WHERE id_produk = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, idProduk);  
            try (ResultSet rs = ps.executeQuery()) {  
                if (rs.next()) {  
                    return rs.getInt("jumlah");  
                }  
            }  
        } catch (SQLException e) {  
            System.err.println("Error fetching jumlah produk: " + e.getMessage());  
        }  
        return 0; // Kembalikan 0 jika tidak ditemukan  
    }  
  
    public double getHargaPerUnit(int produkId) throws SQLException {  
        String query = "SELECT harga_produk FROM produk WHERE id_produk = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, produkId);  
            try (ResultSet rs = ps.executeQuery()) {  
                if (rs.next()) {  
                    return rs.getDouble("harga_produk");  
                }  
            }  
        }  
        throw new SQLException("Produk tidak ditemukan dengan ID: " + produkId);  
    }  
  
    public boolean updatePenjualan(Penjualan penjualan) throws SQLException {  
        String query = "UPDATE penjualan SET jumlah = ?, total = ? WHERE id_penjualan = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, penjualan.getJumlah());  
            ps.setDouble(2, penjualan.getTotalHarga()); // Anda mungkin ingin menghitung ulang total harga di sini  
            ps.setInt(3, penjualan.getIdPenjualan());  
            return ps.executeUpdate() > 0; // Mengembalikan true jika berhasil  
        } catch (SQLException e) {  
            System.err.println("SQL Error: " + e.getMessage());  
            throw e; // Rethrow the exception after logging  
        }  
    }  
  
    public boolean hapusPenjualan(int idPenjualan, int jumlah) throws SQLException {  
        String query = "DELETE FROM penjualan WHERE id_penjualan = ? LIMIT ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, idPenjualan);  
            ps.setInt(2, jumlah); // Menghapus sesuai dengan jumlah yang diberikan  
            return ps.executeUpdate() > 0; // Mengembalikan true jika berhasil  
        } catch (SQLException e) {  
            System.err.println("SQL Error: " + e.getMessage());  
            throw e; // Rethrow the exception after logging  
        }  
    }  
    
    public int getTotalPenjualan(int idPetani) throws SQLException {  
    String query = "SELECT SUM(jumlah) FROM penjualan WHERE id_petani = ?";  
    try (PreparedStatement stmt = connection.prepareStatement(query)) {  
        stmt.setInt(1, idPetani);  
        ResultSet rs = stmt.executeQuery();  
        return rs.next() ? rs.getInt(1) : 0; // Return 0 if no sales found  
    }  
}  

  
    public int getTotalProduk(int idPetani) throws SQLException {  
    String query = "SELECT SUM(stok) FROM produk WHERE id_petani = ?";  
    try (PreparedStatement stmt = connection.prepareStatement(query)) {  
        stmt.setInt(1, idPetani);  
        ResultSet rs = stmt.executeQuery();  
        return rs.next() ? rs.getInt(1) : 0; // Return 0 if no products found  
    }  
}  
  
    public int getTotalBibit(int idPetani) throws SQLException {  
    String query = "SELECT SUM(jumlah) FROM transaksi_beli_bibit WHERE id_petani = ?";  
    try (PreparedStatement stmt = connection.prepareStatement(query)) {  
        stmt.setInt(1, idPetani);  
        ResultSet rs = stmt.executeQuery();  
        return rs.next() ? rs.getInt(1) : 0; // Return 0 if no seeds found  
    }  
}  
    public double getTotalHargaBeli(int idPetani) throws SQLException {  
    String query = "SELECT SUM(total_harga) FROM transaksi_beli_bibit WHERE id_petani = ?";  
    try (PreparedStatement stmt = connection.prepareStatement(query)) {  
        stmt.setInt(1, idPetani);  
        ResultSet rs = stmt.executeQuery();  
        return rs.next() ? rs.getDouble(1) : 0.0; // Return 0.0 if no purchases found  
    }  
}  
  
public double getTotalHargaJual(int idPetani) throws SQLException {  
    String query = "SELECT SUM(total) FROM penjualan WHERE id_petani = ?";  
    try (PreparedStatement stmt = connection.prepareStatement(query)) {  
        stmt.setInt(1, idPetani);  
        ResultSet rs = stmt.executeQuery();  
        return rs.next() ? rs.getDouble(1) : 0.0; // Return 0.0 if no sales found  
    }  
}  

   
}  
