package Controller;  
  
import DAO.DatabaseConnection;  
import Model.Editbibit;  
import Model.Edituser;  
import dao.Admin_dashboardDAO;  
import javafx.beans.property.ReadOnlyObjectWrapper;  
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList;  
import javafx.event.ActionEvent;  
import javafx.fxml.FXML;  
import javafx.fxml.FXMLLoader;  
import javafx.fxml.Initializable;  
import javafx.scene.Parent;  
import javafx.scene.Scene;  
import javafx.scene.control.*;  
import javafx.scene.layout.AnchorPane;  
import javafx.scene.layout.StackPane;  
import javafx.stage.Stage;  
import javafx.stage.StageStyle;  
  
import java.io.File;  
import java.io.IOException;  
import java.net.URL;  
import java.sql.Connection;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;  
import java.time.LocalDate;  
import java.util.List;  
import java.util.Optional;  
import java.util.ResourceBundle;  
import javafx.scene.control.cell.PropertyValueFactory;  
import javafx.scene.layout.VBox;  
  
public class Admin_dashboardController implements Initializable {  
  
    @FXML  
    private StackPane top_form;  
    @FXML  
    private Button homebtn;  
    @FXML  
    private Button editdatabtn;  
    @FXML  
    private Button lihatdatabtn;  
    @FXML  
    private Button logoutbtn;  
    @FXML  
    private AnchorPane homeform;  
    @FXML  
    private AnchorPane editdata;  
    @FXML  
    private AnchorPane lihatdata;  
    @FXML  
    private TableView<Editbibit> editdatabibit;  
    @FXML  
    private TableColumn<Editbibit, Integer> nobibit;  
    @FXML  
    private TableColumn<Editbibit, String> bibitedit;  
    @FXML  
    private TableColumn<Editbibit, String> editsupplier;  
    @FXML  
    private TableColumn<Editbibit, Double> harga;  
  
    private Admin_dashboardDAO admin_dashboardDAO;  
    private String username;  
    private int loggedInIdPetani; // Field to store the logged-in id_petani  
    @FXML  
    private TableView<Edituser> edituserdata;  
    @FXML  
    private TableColumn<Edituser, Integer> no;  
    @FXML  
    private TableColumn<Edituser, String> usernameedit;  
    @FXML  
    private TableColumn<Edituser, String> pwedit;  
    @FXML  
    private TableColumn<Edituser, String> role;  
    @FXML  
    private Button hapususer;  
    @FXML  
    private Button adduser;  
    @FXML  
    private TextField addpw;  
    @FXML  
    private TextField addusn;  
    @FXML  
    private Button addbibit;  
    @FXML  
    private Button hapusdatabibit;  
    @FXML  
    private TextField addsupplier;  
    @FXML  
    private TextField addharga;  
    @FXML  
    private TextField addbibitt;  
    @FXML  
    private ComboBox<String> pilihusercbp;  
    @FXML  
    private Button updateuser;  
    @FXML  
    private Button updatebbt;  
  
    public void setUsername(String username) {  
        this.username = username;  
        // Fetch the id_petani based on the username  
        this.loggedInIdPetani = admin_dashboardDAO.getIdPetaniByUsername(username);  
        System.out.println("Logged in as: " + username + " with id_petani: " + loggedInIdPetani);  
    }  
  
    private void showAlert(String title, String content) {  
        Alert alert = new Alert(Alert.AlertType.INFORMATION);  
        alert.setTitle(title);  
        alert.setHeaderText(null);  
        alert.setContentText(content);  
        alert.showAndWait();  
    }  
  
    @FXML  
    private void gantiform(ActionEvent event) throws SQLException {  
        if (event.getSource() == homebtn) {  
            showHome();  
        } else if (event.getSource() == editdatabtn) {  
            showEditData();  
        } else if (event.getSource() == lihatdatabtn) {  
            showLihatData();  
        }  
    }  
  
    private void showHome() {  
        homeform.setVisible(true);  
        editdata.setVisible(false);  
        lihatdata.setVisible(false);  
    }  
  
    private void showEditData() {  
        homeform.setVisible(false);  
        editdata.setVisible(true);  
        lihatdata.setVisible(false);  
        try {  
            initializeUserTable();  
        } catch (SQLException e) {  
            System.err.println("Error initializing tables: " + e.getMessage());  
            showAlert("Error", "Failed to load data for the User table.");  
        }  
    }  
  
    private void showLihatData() {  
        homeform.setVisible(false);  
        editdata.setVisible(false);  
        lihatdata.setVisible(true);  
        try {  
            initializeBibitTable();  
        } catch (SQLException e) {  
            System.err.println("Error initializing tables: " + e.getMessage());  
            showAlert("Error", "Failed to load data for the Bibit table.");  
        }  
    }  
  
    @FXML  
    private void logout(ActionEvent event) {  
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);  
        alert.setTitle("Confirmation Message");  
        alert.setHeaderText(null);  
        alert.setContentText("Are you sure you want to log out?");  
        Optional<ButtonType> option = alert.showAndWait();  
  
        if (option.isPresent() && option.get() == ButtonType.OK) {  
            try {  
                URL url = new File("src/main/java/View/loginpage.fxml").toURI().toURL();  
                FXMLLoader loader = new FXMLLoader(url);  
                Parent root = loader.load();  
  
                Stage currentStage = (Stage) logoutbtn.getScene().getWindow();  
                currentStage.close();  
  
                Stage loginStage = new Stage();  
                loginStage.setScene(new Scene(root));  
                loginStage.initStyle(StageStyle.DECORATED);  
                loginStage.show();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    @FXML  
    private void hapusUserAdmin(ActionEvent event) {  
        Edituser selectedUser = edituserdata.getSelectionModel().getSelectedItem();  
  
        if (selectedUser != null) {  
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);  
            alert.setTitle("Konfirmasi Hapus");  
            alert.setHeaderText("Anda yakin ingin menghapus data ini?");  
            alert.setContentText("Data yang dipilih: " + selectedUser.getUsername());  
  
            Optional<ButtonType> result = alert.showAndWait();  
            if (result.isPresent() && result.get() == ButtonType.OK) {  
                try {  
                    boolean success = admin_dashboardDAO.hapusEdituser(selectedUser.getUsername());  
                    if (success) {  
                        initializeUserTable(); // Refresh the user table  
                        showAlert("Success", "Data berhasil dihapus!");  
                    } else {  
                        showAlert("Error", "Gagal menghapus data dari database.");  
                    }  
                } catch (SQLException e) {  
                    showAlert("Error", "Gagal menghapus data dari database: " + e.getMessage());  
                    e.printStackTrace();  
                }  
            }  
        } else {  
            showAlert("Error", "Silakan pilih data yang ingin dihapus.");  
        }  
    }  
  
    @FXML  
    private void hapusBibit(ActionEvent event) {  
        Editbibit selectedBibit = editdatabibit.getSelectionModel().getSelectedItem();  
  
        if (selectedBibit != null) {  
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);  
            alert.setTitle("Konfirmasi Hapus");  
            alert.setHeaderText("Anda yakin ingin menghapus data ini?");  
            alert.setContentText("Data yang dipilih: " + selectedBibit.getBibit());  
  
            Optional<ButtonType> result = alert.showAndWait();  
            if (result.isPresent() && result.get() == ButtonType.OK) {  
                try {  
                    boolean success = admin_dashboardDAO.hapusEditBibit(selectedBibit.getIdSupplyBibit());  
                    if (success) {  
                        initializeBibitTable(); // Refresh tabel setelah penghapusan  
                        showAlert("Success", "Data berhasil dihapus!");  
                    } else {  
                        showAlert("Error", "Gagal menghapus data dari database.");  
                    }  
                } catch (SQLException e) {  
                    showAlert("Error", "Kesalahan saat menghapus data: " + e.getMessage());  
                    e.printStackTrace();  
                }  
            }  
        } else {  
            showAlert("Error", "Silakan pilih data yang ingin dihapus.");  
        }  
    }  
  
    @Override  
    public void initialize(URL url, ResourceBundle rb) {  
        try {  
            System.out.println("Inisialisasi dimulai...");  
            Connection connection = DatabaseConnection.getConnection();  
            admin_dashboardDAO = new Admin_dashboardDAO(connection);  
            System.out.println("Koneksi database berhasil!");  
  
            initializeBibitTable();  
            initializeUserTable();  
            System.out.println("Tabel berhasil diinisialisasi!");  
  
        } catch (SQLException e) {  
            System.err.println("Kesalahan database saat inisialisasi: " + e.getMessage());  
            showAlert("Kesalahan Database", "Terjadi kesalahan saat mengakses database.");  
        } catch (Exception e) {  
            System.err.println("Kesalahan tak terduga saat inisialisasi: " + e.getMessage());  
            showAlert("Kesalahan", "Terjadi kesalahan tak terduga: " + e.getMessage());  
        }  
        pilihusercbp.getItems().addAll("admin", "pengguna");  
    }  
  
    private void initializeBibitTable() throws SQLException {  
        List<Editbibit> editBibitList = admin_dashboardDAO.getEditbibit();  
        ObservableList<Editbibit> observableTransaksiList = FXCollections.observableArrayList(editBibitList);  
        editdatabibit.setItems(observableTransaksiList);  
  
        // Binding data ke tabel  
        nobibit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(editdatabibit.getItems().indexOf(param.getValue()) + 1));  
        bibitedit.setCellValueFactory(new PropertyValueFactory<>("bibit"));  
        editsupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));  
        harga.setCellValueFactory(new PropertyValueFactory<>("harga"));  
    }  
  
    private void initializeUserTable() throws SQLException {  
        List<Edituser> editUserList = admin_dashboardDAO.getEdituser(); // Ambil semua pengguna dari database  
        ObservableList<Edituser> observableUserEditList = FXCollections.observableArrayList(editUserList);  
        edituserdata.setItems(observableUserEditList);  
  
        // Binding data ke tabel  
        no.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(edituserdata.getItems().indexOf(param.getValue()) + 1));  
        usernameedit.setCellValueFactory(new PropertyValueFactory<>("username"));  
        pwedit.setCellValueFactory(new PropertyValueFactory<>("password"));  
        role.setCellValueFactory(new PropertyValueFactory<>("role"));  
    }  
  
    @FXML  
    private void tambahBibitAdmin(ActionEvent event) throws SQLException {  
        String bibitName = addbibitt.getText().trim(); // Get the bibit name  
        String supplierName = addsupplier.getText().trim(); // Get the supplier name  
        String hargaText = addharga.getText().trim(); // Get the price  
  
        // Validate input  
        if (bibitName.isEmpty() || supplierName.isEmpty() || hargaText.isEmpty()) {  
            showAlert("Error", "Semua kolom harus diisi.");  
            return;  
        }  
  
        double harga;  
        try {  
            harga = Double.parseDouble(hargaText);  
            if (harga <= 0) {  
                showAlert("Error", "Harga harus lebih besar dari nol.");  
                return;  
            }  
        } catch (NumberFormatException e) {  
            showAlert("Error", "Harga harus berupa angka yang valid.");  
            return;  
        }  
  
        // Cek apakah supplier sudah ada di tabel supplier  
        int idSupplier = admin_dashboardDAO.getIdSupplierByName(supplierName); // Ambil ID supplier berdasarkan nama  
  
        if (idSupplier == 0) {  
            // Jika supplier tidak ada, tambahkan supplier baru  
            boolean supplierAdded = admin_dashboardDAO.addSupplier(supplierName); // Pastikan Anda memiliki metode ini  
            if (!supplierAdded) {  
                showAlert("Error", "Gagal menambahkan supplier baru.");  
                return;  
            }  
            // Ambil kembali ID supplier setelah ditambahkan  
            idSupplier = admin_dashboardDAO.getIdSupplierByName(supplierName);  
        }  
  
        // Jika supplier ada, tambahkan ke tabel supply_bibit  
        Editbibit newBibit = new Editbibit(0, bibitName, supplierName, harga); // Gunakan ID yang valid  
        try {  
            boolean success = admin_dashboardDAO.insertSupplyBibit(newBibit); // Panggil metode insert  
            if (success) {  
                showAlert("Success", "Bibit ditambahkan dengan sukses!");  
                initializeBibitTable(); // Segarkan tabel untuk menampilkan entri baru  
            } else {  
                showAlert("Error", "Gagal menambahkan bibit ke supply.");  
            }  
        } catch (SQLException e) {  
            showAlert("Error", "Kesalahan database: " + e.getMessage());  
            e.printStackTrace();  
        }  
    }  
  
    @FXML  
    private void pilihUser(ActionEvent event) {  
        // Ambil peran yang dipilih dari ComboBox pilihusercbp  
        String selectedRole = pilihusercbp.getSelectionModel().getSelectedItem();  
        if (selectedRole != null) {  
            // Hanya mencetak peran yang dipilih tanpa melakukan update  
            System.out.println("Selected role: " + selectedRole);  
        } else {  
            System.out.println("No role selected in ComboBox.");  
        }  
    }  
  
    @FXML  
    private void addUser(ActionEvent event) {  
        String user = addusn.getText();  
        String password = addpw.getText();  
        String selectedRole = pilihusercbp.getSelectionModel().getSelectedItem();  
  
        if (user.isEmpty() || password.isEmpty() || selectedRole == null) {  
            showAlert("Error", "Silakan isi semua kolom.");  
            return;  
        }  
  
        // Menggunakan DAO untuk menambahkan pengguna  
        boolean isAdded = admin_dashboardDAO.addUser(user, password, selectedRole);  
        if (isAdded) {  
            System.out.println("User added successfully.");  
            showAlert("Success", "User added successfully.");  
              
            // Refresh the user table to show the newly added user  
            try {  
                initializeUserTable(); // Refresh the user table  
            } catch (SQLException e) {  
                showAlert("Error", "Gagal memuat data pengguna: " + e.getMessage());  
                e.printStackTrace();  
            }  
  
            // Clear input fields  
            addusn.clear();  
            addpw.clear();  
            pilihusercbp.getSelectionModel().clearSelection();  
        } else {  
            showAlert("Error", "Gagal menambahkan pengguna.");  
        }  
    }  
  
    @FXML      
    private void updateuseradmin(ActionEvent event) {      
        Edituser selectedItem = edituserdata.getSelectionModel().getSelectedItem();      
        if (selectedItem != null) {      
            System.out.println("Selected User: " + selectedItem.getIdPetani());      
      
            // Tampilkan dialog untuk mengedit data      
            Dialog<Edituser> dialog = new Dialog<>();      
            dialog.setTitle("Update User");      
            dialog.setHeaderText("Update Data User");      
      
            // Set the button types.      
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);      
            dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);      
      
            // Create fields for input      
            TextField usernameField = new TextField(selectedItem.getUsername());      
            usernameField.setPromptText("Username");      
            TextField passwordField = new TextField(selectedItem.getPassword());      
            passwordField.setPromptText("Password");      
            ComboBox<String> roleComboBox = new ComboBox<>();      
            roleComboBox.getItems().addAll("admin", "pengguna"); // Contoh role      
            roleComboBox.setValue(selectedItem.getRole()); // Set role yang ada      
      
            // Create a VBox to hold the fields      
            VBox vbox = new VBox(10); // Add spacing between elements      
            vbox.getChildren().addAll(      
                new Label("Username:"),       
                usernameField,       
                new Label("Password:"),       
                passwordField,       
                new Label("Role:"),       
                roleComboBox      
            );      
            dialog.getDialogPane().setContent(vbox);      
      
            // Convert the result to an Edituser when the OK button is pressed.      
            dialog.setResultConverter(dialogButton -> {      
                if (dialogButton == okButton) {      
                    String newUsername = usernameField.getText();      
                    String newPassword = passwordField.getText();      
                    String newRole = roleComboBox.getValue();      
      
                    if (newUsername.isEmpty() || newPassword.isEmpty() || newRole == null) {      
                        showAlert("Error", "Semua field harus diisi.");      
                        return null;      
                    }      
      
                    // Create the Edituser object with updated values      
                    return new Edituser(      
                        selectedItem.getIdPetani(), // ID petani yang sama      
                        newUsername, // Username baru      
                        newPassword, // Password baru      
                        newRole // Role baru      
                    );      
                }      
                return null;      
            });      
      
            // Show the dialog and wait for the result      
            Optional<Edituser> result = dialog.showAndWait();      
            result.ifPresent(updatedUser -> {      
                try {      
                    // Update the user in the database      
                    boolean success = admin_dashboardDAO.updateuseradmin(updatedUser.getIdPetani(), updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.getRole());      
                    if (success) {      
                        // Refresh the table or perform any other necessary actions      
                        initializeUserTable(); // Pastikan Anda memiliki metode ini untuk menyegarkan tabel      
                        showAlert("Success", "Data user berhasil diperbarui!");      
                    } else {      
                        showAlert("Error", "Gagal memperbarui data user di database.");      
                    }      
                } catch (SQLException e) {      
                    showAlert("Error", "Gagal memperbarui data user di database: " + e.getMessage());      
                    e.printStackTrace();      
                }      
            });      
        } else {      
            showAlert("Error", "Silakan pilih data user yang ingin diperbarui.");      
        }      
    }    
  @FXML      
private void updatebibitadmin(ActionEvent event) {      
    // Ambil data yang dipilih dari tabel bibit          
    Editbibit selectedTransaksi = editdatabibit.getSelectionModel().getSelectedItem();          
  
    if (selectedTransaksi != null) {          
        System.out.println("Selected Transaksi ID: " + selectedTransaksi.getIdSupplyBibit()); // Debugging output          
  
        // Tampilkan dialog untuk mengedit data          
        Dialog<Editbibit> dialog = new Dialog<>();          
        dialog.setTitle("Update Bibit");          
        dialog.setHeaderText("Update Data Bibit");          
  
        // Set the button types.          
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);          
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);          
  
        // Create fields for input          
        TextField bibitField = new TextField(selectedTransaksi.getBibit());          
        bibitField.setPromptText("Nama Bibit");          
  
        TextField supplierField = new TextField(selectedTransaksi.getSupplier());          
        supplierField.setPromptText("Nama Supplier");          
  
        TextField hargaField = new TextField(String.valueOf(selectedTransaksi.getHarga()));          
        hargaField.setPromptText("Harga");          
  
        // Menambahkan field ke dialog secara langsung      
        dialog.getDialogPane().setContent(new VBox(10,            
            new Label("Nama Bibit:"), bibitField,            
            new Label("Nama Supplier:"), supplierField,            
            new Label("Harga:"), hargaField            
        ));            
  
        // Convert the result to an Editbibit when the OK button is pressed.            
        dialog.setResultConverter(dialogButton -> {            
            if (dialogButton == okButton) {            
                String bibitName = bibitField.getText().trim();            
                String supplierName = supplierField.getText().trim();            
                String hargaText = hargaField.getText().trim();            

                // Validate input    
                if (bibitName.isEmpty() || supplierName.isEmpty() || hargaText.isEmpty()) {    
                    showAlert("Error", "Semua kolom harus diisi.");    
                    return null;    
                }    

                double harga;    
                try {    
                    harga = Double.parseDouble(hargaText);    
                    if (harga <= 0) {    
                        showAlert("Error", "Harga harus lebih besar dari nol.");    
                        return null;    
                    }    
                } catch (NumberFormatException e) {    
                    showAlert("Error", "Harga harus berupa angka yang valid.");    
                    return null;    
                }    
  
                int idBibit = admin_dashboardDAO.getIdBibitByName(bibitName); // Ambil ID supplier berdasarkan nama    
  
                if (idBibit == 0) {    
                    // Jika supplier tidak ada, tambahkan supplier baru    
                    boolean bibitAdded = admin_dashboardDAO.addBibit(bibitName); // Pastikan Anda memiliki metode ini    
                    if (!bibitAdded) {    
                        showAlert("Error", "Gagal menambahkan bibit baru.");    
                        return null;    
                    }    
                    // Ambil kembali ID supplier setelah ditambahkan    
                    idBibit = admin_dashboardDAO.getIdBibitByName(bibitName);    
                }    
                
                // Cek apakah supplier sudah ada di tabel supplier    
                int idSupplier = admin_dashboardDAO.getIdSupplierByName(supplierName); // Ambil ID supplier berdasarkan nama    
  
                if (idSupplier == 0) {    
                    // Jika supplier tidak ada, tambahkan supplier baru    
                    boolean supplierAdded = admin_dashboardDAO.addSupplier(supplierName); // Pastikan Anda memiliki metode ini    
                    if (!supplierAdded) {    
                        showAlert("Error", "Gagal menambahkan supplier baru.");    
                        return null;    
                    }    
                    // Ambil kembali ID supplier setelah ditambahkan    
                    idSupplier = admin_dashboardDAO.getIdSupplierByName(supplierName);    
                }    
  
                // Debugging output for IDs  
                System.out.println("ID Bibit: " + selectedTransaksi.getIdBibit());  
                System.out.println("ID Supplier: " + idSupplier);  
  
                // Create the Editbibit object using existing IDs  
                return new Editbibit(            
                    selectedTransaksi.getIdSupplyBibit(), // ID supply bibit            
                    bibitName, // Nama bibit            
                    supplierName, // Nama supplier            
                    harga, // Harga baru            
                    idBibit, // Use existing ID Bibit  
                    idSupplier // Use the ID Supplier  
                );            
            }            
            return null;            
        });            
  
        // Show the dialog and wait for the result            
        Optional<Editbibit> result = dialog.showAndWait();            
        result.ifPresent(updatedBibit -> {            
            try {            
                // Update the bibit in the database            
                boolean success = admin_dashboardDAO.updateBibit(updatedBibit);            
                if (success) {            
                    // Refresh the table or perform any other necessary actions            
                    initializeBibitTable();            
                    showAlert("Success", "Data bibit dan supplier berhasil diperbarui!");            
                } else {            
                    showAlert("Error", "Gagal memperbarui data bibit di database.");            
                }            
            } catch (SQLException e) {            
                showAlert("Error", "Gagal memperbarui data bibit di database: " + e.getMessage());            
                e.printStackTrace();            
            }            
        });            
    } else {            
        showAlert("Error", "Silakan pilih data bibit yang ingin diperbarui.");            
    }            
}  


    private void loadSuppliersForSelectedBibit(int bibitId, ComboBox<String> supplierComboBox) {  
    supplierComboBox.getItems().clear(); // Kosongkan ComboBox sebelum mengisi  
    String query = "SELECT s.nama FROM supplier s " +  
                   "JOIN supply_bibit sb ON s.id_supplier = sb.id_supplier " +  
                   "WHERE sb.id_bibit = ?";  
  
    try (PreparedStatement ps = admin_dashboardDAO.getConnection().prepareStatement(query)) {  
        ps.setInt(1, bibitId);  
        try (ResultSet rs = ps.executeQuery()) {  
            while (rs.next()) {  
                String supplierName = rs.getString("nama");  
                supplierComboBox.getItems().add(supplierName); // Tambahkan nama supplier ke ComboBox  
            }  
            if (!supplierComboBox.getItems().isEmpty()) {  
                supplierComboBox.getSelectionModel().selectFirst(); // Pilih supplier pertama jika ada  
            }  
        }  
    } catch (SQLException e) {  
        showAlert("Error", "Failed to load suppliers: " + e.getMessage());  
    }  
}  }
