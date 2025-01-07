package dao;  
  
import Model.Editbibit;  
import Model.Edituser;  
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList;  
  
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
  
public class Admin_dashboardDAO {  
  
    private Connection connection;  
  
    public Admin_dashboardDAO(Connection connection) {  
        this.connection = connection;  
    }  
  
    public ObservableList<Editbibit> getEditbibit() {  
        ObservableList<Editbibit> editBibitList = FXCollections.observableArrayList();  
        String query = """  
            SELECT   
                sb.id_supply_bibit,   
                b.nama AS bibit,   
                s.nama AS supplier,   
                sb.harga  
            FROM supply_bibit sb  
            JOIN bibit b ON sb.id_bibit = b.id_bibit  
            JOIN supplier s ON sb.id_supplier = s.id_supplier  
        """;  
  
        try (PreparedStatement ps = connection.prepareStatement(query);  
             ResultSet rs = ps.executeQuery()) {  
            while (rs.next()) {  
                Editbibit editBibit = new Editbibit(  
                    rs.getInt("id_supply_bibit"),  
                    rs.getString("bibit"),  
                    rs.getString("supplier"),  
                    rs.getDouble("harga")  
                );  
                editBibitList.add(editBibit);  
            }  
        } catch (SQLException e) {  
            System.err.println("Error fetching Editbibit data:");  
            e.printStackTrace();  
        }  
  
        return editBibitList;  
    }  
  
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
  
    public ObservableList<Edituser> getEdituser() {  
        ObservableList<Edituser> editUserList = FXCollections.observableArrayList();  
        String query = """  
            SELECT   
                p.id_petani,   
                p.username,   
                p.password,  
                p.role  
            FROM petani p  
        """;  
  
        try (PreparedStatement ps = connection.prepareStatement(query);  
             ResultSet rs = ps.executeQuery()) {  
            while (rs.next()) {  
                Edituser user = new Edituser(  
                    rs.getInt("id_petani"),  
                    rs.getString("username"),  
                    rs.getString("password"),  
                    rs.getString("role")  
                );  
                editUserList.add(user);  
            }  
        } catch (SQLException e) {  
            System.err.println("Error fetching Edituser data:");  
            e.printStackTrace();  
        }  
  
        return editUserList;  
    }  
  
    public boolean hapusEdituser(String username) throws SQLException {  
        String query = "DELETE FROM petani WHERE username = ?";  
        if (connection == null || connection.isClosed()) {  
            throw new SQLException("Connection is closed or not initialized.");  
        }  
  
        try (PreparedStatement stmt = connection.prepareStatement(query)) {  
            stmt.setString(1, username);  
            return stmt.executeUpdate() > 0; // Returns true if deletion was successful  
        } catch (SQLException e) {  
            System.err.println("Error deleting user: " + e.getMessage());  
            return false;  
        }  
    }  
  
    public boolean hapusEditBibit(int idSupplyBibit) {  
        // Hapus data terkait di tabel transaksi_beli_bibit  
        String deleteTransaksiQuery = "DELETE FROM transaksi_beli_bibit WHERE id_supply_bibit = ?";  
        try (PreparedStatement stmt = connection.prepareStatement(deleteTransaksiQuery)) {  
            stmt.setInt(1, idSupplyBibit);  
            stmt.executeUpdate(); // Hapus data terkait  
        } catch (SQLException e) {  
            System.err.println("Error deleting related transactions: " + e.getMessage());  
        }  
  
        // Hapus data dari tabel supply_bibit  
        String query = "DELETE FROM supply_bibit WHERE id_supply_bibit = ?";  
        try (PreparedStatement stmt = connection.prepareStatement(query)) {  
            stmt.setInt(1, idSupplyBibit);  
            return stmt.executeUpdate() > 0; // Mengembalikan true jika penghapusan berhasil  
        } catch (SQLException e) {  
            System.err.println("Error deleting supply bibit: " + e.getMessage());  
            return false;  
        }  
    }  
  
    public boolean insertSupplyBibit(Editbibit newBibit) {  
        // Cek apakah supplier ada, jika tidak, tambahkan  
        int supplierId = getIdSupplierByName(newBibit.getSupplier());  
        if (supplierId == 0) {  
            // Jika supplier tidak ada, tambahkan supplier baru  
            if (!addSupplier(newBibit.getSupplier())) {  
                System.err.println("Gagal menambahkan supplier baru.");  
                return false; // Mengembalikan false jika gagal menambahkan supplier  
            }  
            // Ambil kembali ID supplier setelah ditambahkan  
            supplierId = getIdSupplierByName(newBibit.getSupplier());  
        }  
  
        // Cek apakah bibit ada, jika tidak, tambahkan  
        int bibitId = getIdBibitByName(newBibit.getBibit());  
        if (bibitId == 0) {  
            // Jika bibit tidak ada, tambahkan bibit baru  
            if (!addBibit(newBibit.getBibit())) {  
                System.err.println("Gagal menambahkan bibit baru.");  
                return false; // Mengembalikan false jika gagal menambahkan bibit  
            }  
            // Ambil kembali ID bibit setelah ditambahkan  
            bibitId = getIdBibitByName(newBibit.getBibit());  
        }  
  
        // Sekarang kita bisa menyisipkan supply bibit  
        String query = "INSERT INTO supply_bibit (id_bibit, id_supplier, harga) VALUES (?, ?, ?)";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, bibitId); // Gunakan ID bibit yang valid  
            ps.setInt(2, supplierId); // Gunakan ID supplier yang valid  
            ps.setDouble(3, newBibit.getHarga());  
            return ps.executeUpdate() > 0; // Mengembalikan true jika penyisipan berhasil  
        } catch (SQLException e) {  
            System.err.println("Error inserting supply bibit: " + e.getMessage());  
            return false;  
        }  
    }  
  
    public boolean isBibitExists(int idBibit) {  
        String query = "SELECT COUNT(*) FROM bibit WHERE id_bibit = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, idBibit);  
            try (ResultSet rs = ps.executeQuery()) {  
                if (rs.next()) {  
                    return rs.getInt(1) > 0; // Mengembalikan true jika bibit ada  
                }  
            }  
        } catch (SQLException e) {  
            System.err.println("Error checking if bibit exists: " + e.getMessage());  
        }  
        return false; // Mengembalikan false jika bibit tidak ada  
    }  
  
    public boolean isSupplierExists(int idSupplier) {  
        String query = "SELECT COUNT(*) FROM supplier WHERE id_supplier = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, idSupplier);  
            try (ResultSet rs = ps.executeQuery()) {  
                if (rs.next()) {  
                    return rs.getInt(1) > 0; // Mengembalikan true jika supplier ada  
                }  
            }  
        } catch (SQLException e) {  
            System.err.println("Error checking if supplier exists: " + e.getMessage());  
        }  
        return false; // Mengembalikan false jika supplier tidak ada  
    }  
  
    public int getIdBibitByName(String bibitName) {  
        String query = "SELECT id_bibit FROM bibit WHERE nama = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setString(1, bibitName);  
            try (ResultSet rs = ps.executeQuery()) {  
                if (rs.next()) {  
                    return rs.getInt("id_bibit");  
                }  
            }  
        } catch (SQLException e) {  
            System.err.println("Error fetching bibit ID: " + e.getMessage());  
        }  
        return 0; // Mengembalikan 0 jika bibit tidak ditemukan  
    }  
  
    public int getIdSupplierByName(String supplierName) {  
        String query = "SELECT id_supplier FROM supplier WHERE nama = ?";  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setString(1, supplierName);  
            try (ResultSet rs = ps.executeQuery()) {  
                if (rs.next()) {  
                    return rs.getInt("id_supplier");  
                }  
            }  
        } catch (SQLException e) {  
            System.err.println("Error fetching supplier ID: " + e.getMessage());  
        }  
        return 0; // Mengembalikan 0 jika supplier tidak ditemukan  
    }  
  
    public boolean addSupplier(String supplierName) {  
        String query = "INSERT INTO supplier (nama) VALUES (?)";  
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
            preparedStatement.setString(1, supplierName);  
            int rowsAffected = preparedStatement.executeUpdate();  
            return rowsAffected > 0; // Mengembalikan true jika penyisipan berhasil  
        } catch (SQLException e) {  
            System.err.println("Error adding supplier: " + e.getMessage());  
            return false; // Mengembalikan false jika terjadi kesalahan  
        }  
    }  
  
    public boolean addBibit(String bibitName) {  
        String query = "INSERT INTO bibit (nama) VALUES (?)"; // Pastikan tabel bibit memiliki kolom nama  
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
            preparedStatement.setString(1, bibitName);  
            int rowsAffected = preparedStatement.executeUpdate();  
            return rowsAffected > 0; // Mengembalikan true jika penyisipan berhasil  
        } catch (SQLException e) {  
            System.err.println("Error adding bibit: " + e.getMessage());  
            return false; // Mengembalikan false jika terjadi kesalahan  
        }  
    }  
  
    // Metode untuk menambahkan pengguna baru  
    public boolean addUser(String user, String password, String role) {  
        String query = "INSERT INTO petani (username, password, role) VALUES (?, ?, ?)";  
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
            preparedStatement.setString(1, user);  
            preparedStatement.setString(2, password);  
            preparedStatement.setString(3, role);  
            int rowsAffected = preparedStatement.executeUpdate();  
            return rowsAffected > 0; // Mengembalikan true jika penyisipan berhasil  
        } catch (SQLException e) {  
            System.err.println("Error adding user: " + e.getMessage());  
            return false; // Mengembalikan false jika terjadi kesalahan  
        }  
    }  
      
    public boolean updateuseradmin(int idPetani, String username, String password, String role) throws SQLException {    
        String query = "UPDATE petani SET username = ?, password = ?, role = ? WHERE id_petani = ?";    
        try (PreparedStatement ps = connection.prepareStatement(query)) {    
            ps.setString(1, username);    
            ps.setString(2, password);    
            ps.setString(3, role);    
            ps.setInt(4, idPetani); // Menggunakan ID petani untuk menentukan pengguna yang akan diperbarui    
            int rowsAffected = ps.executeUpdate();    
            return rowsAffected > 0; // Mengembalikan true jika ada baris yang terpengaruh    
        } catch (SQLException e) {    
            System.err.println("Error updating user: " + e.getMessage());    
            throw e; // Rethrow the exception if needed    
        }    
    }    
      
public boolean updateBibit(Editbibit updatedBibit) throws SQLException {  
    // Validate input  
    if (updatedBibit == null) {  
        System.err.println("Updated bibit cannot be null.");  
        return false; // Return false if updatedBibit is null  
    }  
  
    // Check if the bibit and supplier exist before performing the update  
    if (!isBibitExists(updatedBibit.getIdBibit())) {  
        System.err.println("Bibit with ID " + updatedBibit.getIdBibit() + " does not exist.");  
        return false; // Return false if bibit does not exist  
    }  
  
    if (!isSupplierExists(updatedBibit.getIdSupplier())) {  
        System.err.println("Supplier with ID " + updatedBibit.getIdSupplier() + " does not exist.");  
        return false; // Return false if supplier does not exist  
    }  
  
    // Validate harga  
    if (updatedBibit.getHarga() <= 0) {  
        System.err.println("Harga must be greater than zero.");  
        return false; // Return false if harga is invalid  
    }  
  
    String query = "UPDATE supply_bibit SET id_bibit = ?, id_supplier = ?, harga = ? WHERE id_supply_bibit = ?";  
    try (PreparedStatement ps = connection.prepareStatement(query)) {  
        ps.setInt(1, updatedBibit.getIdBibit());  
        ps.setInt(2, updatedBibit.getIdSupplier());  
        ps.setDouble(3, updatedBibit.getHarga());  
        ps.setInt(4, updatedBibit.getIdSupplyBibit());  
          
        int rowsAffected = ps.executeUpdate();  
        return rowsAffected > 0; // Return true if the update was successful  
    } catch (SQLException e) {  
        System.err.println("Error updating bibit: " + e.getMessage());  
        throw e; // Rethrow the exception for further handling  
    }  
}  

    
     // Metode untuk mendapatkan koneksi  
    public Connection getConnection() {  
        return this.connection;  
    }  
  
    // Metode untuk mendapatkan supplier berdasarkan ID bibit  
    public ObservableList<String> getSuppliersByBibitId(int bibitId) {  
        ObservableList<String> suppliers = FXCollections.observableArrayList();  
        String query = "SELECT s.nama FROM supplier s " +  
                       "JOIN supply_bibit sb ON s.id_supplier = sb.id_supplier " +  
                       "WHERE sb.id_bibit = ?";  
  
        try (PreparedStatement ps = connection.prepareStatement(query)) {  
            ps.setInt(1, bibitId);  
            try (ResultSet rs = ps.executeQuery()) {  
                while (rs.next()) {  
                    suppliers.add(rs.getString("nama")); // Tambahkan nama supplier ke daftar  
                }  
            }  
        } catch (SQLException e) {  
            System.err.println("Error loading suppliers for bibit ID " + bibitId + ": " + e.getMessage());  
        }  
        return suppliers; // Kembalikan daftar supplier  
    }  
}  
