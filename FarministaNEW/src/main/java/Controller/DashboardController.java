package Controller;

import DAO.DashboardDAO;
import DAO.DatabaseConnection;
import Model.Bibit;
import Model.StatusTanam;
import Model.Supplier;
import Model.TransaksiBeliBibit;
import Model.TransaksiTanamPanen;
import java.io.File;
import java.io.IOException;
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
import java.net.URL;
import javafx.scene.control.CheckBox;
import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import model.Penjualan;
import model.Produk;

public class DashboardController implements Initializable {

    // Navigation Buttons
@FXML
private Button homebtn;
@FXML
private Button bibitbtn;
@FXML
private Button tanampanenbtn;
@FXML
private Button produkbtn;
@FXML
private Button penjualanbtn;
@FXML
private Button logoutbtn;

// Forms
@FXML
private AnchorPane homeform;
@FXML
private AnchorPane bibitform;
@FXML
private AnchorPane produkform;
@FXML
private AnchorPane penjualanform;
@FXML
private AnchorPane tanampanenform;
@FXML
private StackPane top_form;

// ComboBoxes
@FXML
private ComboBox<Bibit> pilihbibitcb;
@FXML
private ComboBox<Supplier> pilihsuppliercb;
@FXML
private ComboBox<Supplier> pilihsuppliercbp;
@FXML
private ComboBox<Bibit> pilihbibitcbp;
@FXML
private ComboBox<Bibit> pilihbibitcbp1; 
@FXML
private ComboBox<Produk> lihatprodukcmb;

// TextFields
@FXML
private TextField jumlahbibitfield;
private TextField jumlahpanenfield;
@FXML
private TextField stokfieldp;
@FXML
private TextField hargafield;
@FXML
private TextField jumlahjualfield;
@FXML
private TextField hargapenjualanfield;

// DatePickers
@FXML
private DatePicker tanggaltanamfield;
private DatePicker tanggalpanenfield;
@FXML
private DatePicker tanggalsupplyp;

// Tables
@FXML
private TableView<TransaksiBeliBibit> tabelbibit;
@FXML
private TableColumn<TransaksiBeliBibit, Integer> colNo;
@FXML
private TableColumn<TransaksiBeliBibit, String> colBibit;
@FXML
private TableColumn<TransaksiBeliBibit, String> colSupplier;
@FXML
private TableColumn<TransaksiBeliBibit, Double> colHarga;
@FXML
private TableColumn<TransaksiBeliBibit, String> colTanggal;
@FXML
private TableColumn<TransaksiBeliBibit, Integer> colJumlah;

@FXML
private TableView<TransaksiTanamPanen> tabelproduk;
@FXML
private TableColumn<TransaksiTanamPanen, Integer> colnop;
@FXML
private TableColumn<TransaksiTanamPanen, String> colbibitp;
@FXML
private TableColumn<TransaksiTanamPanen, Integer> stokp;
@FXML
private TableColumn<TransaksiTanamPanen, Double> hargap;

@FXML
private TableView<StatusTanam> tabelstatus;
@FXML
private TableColumn<StatusTanam, Integer> colnos;
@FXML
private TableColumn<StatusTanam, String> colbibits;
@FXML
private TableColumn<StatusTanam, String> colsuppliers;
@FXML
private TableColumn<StatusTanam, String> colstatus;
@FXML
private TableColumn<StatusTanam, String> coltanggalsupplys;

@FXML
private TableView<?> tabletanampanen;
@FXML
private TableColumn<?, ?> colnotp;
@FXML
private TableColumn<?, ?> colbibittp;
@FXML
private TableColumn<?, ?> colsuppliertp;
@FXML
private TableColumn<?, ?> coltanggalsupplytp;
@FXML
private TableColumn<?, ?> coltanggaltanamtp;
@FXML
private TableColumn<?, ?> coltanggalpanentp;

// Action Buttons
@FXML
private Button tambahbibitbtn;
@FXML
private Button tambahbtnp;
@FXML
private Button updatebtnp;
@FXML
private Button hapusbtnp;
@FXML
private Button tambahbtnp1;
@FXML
private Button updatebtnp1;
@FXML
private Button hapusbtnp1;
@FXML
private Button updatejualbtn;
@FXML
private Button tambahjualbtn;
@FXML
private Button deletebtn;
@FXML
private Button updatetpbtn;
@FXML
private Button tambahtpbtn;
@FXML
private Button hapustpbtn;

// CheckBox
@FXML
private CheckBox statuscekbox;

// Other Fields
private DashboardDAO dashboardDAO;
private String username;
private int loggedInIdPetani; // Add this field to store the logged-in id_petani


    public void setUsername(String username) {
        this.username = username;
        // Fetch the id_petani based on the username
        this.loggedInIdPetani = dashboardDAO.getIdPetaniByUsername(username);
        System.out.println("Logged in as: " + username + " with id_petani: " + loggedInIdPetani);
    }


    @FXML
    public void gantiform(ActionEvent event) {
        if (event.getSource() == homebtn) {
            showHome();
        } else if (event.getSource() == bibitbtn) {
            showBibit();
        } else if (event.getSource()== tanampanenbtn){
            showTanamPanen();
        } else if (event.getSource() == produkbtn) {
            showProduk();
        } else if (event.getSource() == penjualanbtn) {
            showPenjualan();
        }
    }

    private void showHome() {
        homeform.setVisible(true);
        bibitform.setVisible(false);
        tanampanenform.setVisible(false);
        produkform.setVisible(false);
        penjualanform.setVisible(false);
    }

    private void showBibit() {
        homeform.setVisible(false);
        bibitform.setVisible(true);
        tanampanenform.setVisible(false);
        produkform.setVisible(false);
        penjualanform.setVisible(false);
        
        try {
            initializeBibitTable(loggedInIdPetani);
        } catch (SQLException e) {
            System.err.println("Error initializing tables: " + e.getMessage());
            showAlert("Error", "Failed to load data for the Bibit table.");
        }
    }
    
    private void showTanamPanen() {
        homeform.setVisible(false);
        bibitform.setVisible(false);
        tanampanenform.setVisible(true);
        produkform.setVisible(false);
        penjualanform.setVisible(false);
    }
    private void showProduk() {
        homeform.setVisible(false);
        bibitform.setVisible(false);
        tanampanenform.setVisible(false);
        produkform.setVisible(true);
        penjualanform.setVisible(false);

        // Refresh the tables whenever this form is shown
        try {
            initializeStatusTable(loggedInIdPetani); // Refresh the status table
            initializeProdukTable(loggedInIdPetani); // Refresh the produk table with the logged-in id_petani
        } catch (SQLException e) {
            System.err.println("Error initializing tables: " + e.getMessage());
            showAlert("Error", "Failed to load data for the Produk table.");
        }
    }


    private void showPenjualan() {
        homeform.setVisible(false);
        bibitform.setVisible(false);
        tanampanenform.setVisible(false);
        produkform.setVisible(false);
        penjualanform.setVisible(true);
    }
    
    @FXML
    private void pilihbibit(ActionEvent event) {
        Bibit selectedBibit = pilihbibitcb.getSelectionModel().getSelectedItem();
        if (selectedBibit != null) {
            try {
                List<Supplier> suppliers = dashboardDAO.getSuppliersByBibit(selectedBibit.getIdBibit());
                pilihsuppliercb.getItems().clear();
                pilihsuppliercb.getItems().addAll(suppliers);
                pilihsuppliercb.getSelectionModel().clearSelection();
                System.out.println("Supplier ComboBox updated for Bibit: " + selectedBibit.getNama());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    private void pilihsupplier (ActionEvent event) {
        Supplier selectedSupplier = pilihsuppliercb.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            System.out.println("Selected Supplier: " + selectedSupplier.getNama());
        }
    }

    @FXML
    private void jumlahbibit(ActionEvent event) {
        try {
            int jumlah = Integer.parseInt(jumlahbibitfield.getText());
            if (jumlah <= 0) {
                showAlert("Invalid Input", "The number of seeds must be greater than zero.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid number.");
        }
    }

    @FXML
    private void tambahbibit(ActionEvent event) {
        Bibit selectedBibit = pilihbibitcb.getSelectionModel().getSelectedItem();
        Supplier selectedSupplier = pilihsuppliercb.getSelectionModel().getSelectedItem();
        String jumlahText = jumlahbibitfield.getText();
        String tanggal = tanggaltanamfield.getValue() != null ? tanggaltanamfield.getValue().toString() : "";
        
        System.out.println("Selected Bibit ID: " + selectedBibit.getIdBibit());
        System.out.println("Selected Bibit Name: " + selectedBibit.getNama());
        
        try {
            // Check if required fields are selected or filled
            if (selectedBibit == null || selectedSupplier == null || jumlahText.isEmpty() || tanggal.isEmpty()) {
                showAlert("Error", "Please fill out all fields.");
                return;
            }

            // Parse jumlah and check if it's valid
            int jumlah = Integer.parseInt(jumlahText);
            if (jumlah <= 0) {
                showAlert("Error", "Number of seeds must be greater than zero.");
                return;
            }

            // Fetch harga from the supply_bibit table based on the selected Bibit and Supplier
            double harga = dashboardDAO.getHargaBySupplyBibit(selectedBibit.getIdBibit(), selectedSupplier.getIdSupplier());
            if (harga <= 0) {
                showAlert("Error", "Invalid seed price.");
                return;
            }

            // Calculate total price
            double totalHarga = harga * jumlah;

            // Fetch id_petani based on username
            int idPetani = dashboardDAO.getIdPetaniByUsername(username);
            if (idPetani == 0) {
                showAlert("Error", "Failed to identify the farmer.");
                return;
            }
            
            // Insert transaction to the database
            int idSupplyBibit = dashboardDAO.getSupplyBibitId(selectedBibit.getIdBibit(), selectedSupplier.getIdSupplier());
            System.out.println("Retrieved id_supply_bibit: " + idSupplyBibit);
            if (idSupplyBibit == 0) {
                showAlert("Error", "No matching supply_bibit record found for the selected Bibit and Supplier.");
                return;
            }

            // Insert transaction using idSupplyBibit
            boolean success = dashboardDAO.insertTransaksi(
                new TransaksiBeliBibit(
                    idSupplyBibit, // Correct id_supply_bibit
                    selectedSupplier.getIdSupplier(),
                    idPetani,
                    jumlah,
                    totalHarga,
                    "Pending",
                    tanggal
                )
            );


            // Handle success or failure of the transaction insertion
            if (success) {
                showAlert("Success", "Transaction added successfully!");
                clearForm();

                // Refresh the table data
                List<TransaksiBeliBibit> transaksiList = DashboardDAO.getTransaksiBeliBibitData(1);
                tabelbibit.setItems(FXCollections.observableArrayList(transaksiList));
                tabelbibit.refresh(); // Ensure the table updates immediately
            } else {
                showAlert("Error", "Failed to add the transaction.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number for the quantity.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An unexpected error occurred.");
        }
    }

    private void clearForm() {
        pilihbibitcb.getSelectionModel().clearSelection();
        pilihsuppliercb.getItems().clear();
        jumlahbibitfield.clear();
        tanggaltanamfield.setValue(null);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @FXML
    private void tanggaltanam(ActionEvent event) {
        if (tanggaltanamfield.getValue() != null) {
            String selectedDate = tanggaltanamfield.getValue().toString();
            System.out.println("Tanggal tanam yang dipilih: " + selectedDate);
        } else {
            System.out.println("Tidak ada tanggal yang dipilih.");
        }
    }
    

    @FXML
    public void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            try {
                URL url = new File("src/main/java/View/loginpage.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);
                
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
    
//BAGIAN PRODUK 
    @FXML
    private void pilihbibitp(ActionEvent event) {
    // Ambil bibit yang dipilih dari ComboBox pilihbibitcbp
    Bibit selectedBibit = pilihbibitcbp.getSelectionModel().getSelectedItem();
    if (selectedBibit != null) {
        try {
            // Ambil daftar supplier berdasarkan bibit yang dipilih
            List<Supplier> suppliers = dashboardDAO.getSuppliersByBibit(selectedBibit.getIdBibit());

            // Kosongkan dan isi ulang ComboBox pilihsuppliercbp
            pilihsuppliercbp.getItems().clear();
            pilihsuppliercbp.getItems().addAll(suppliers);

            // Reset pilihan di ComboBox pilihsuppliercbp
            pilihsuppliercbp.getSelectionModel().clearSelection();

            System.out.println("Supplier ComboBox updated for Bibit: " + selectedBibit.getNama());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to fetch suppliers for the selected Bibit.");
        }
    } else {
        System.out.println("No Bibit selected in ComboBox.");
    }
}

    @FXML
    private void pilihsupplierp(ActionEvent event) {
    // Ambil supplier yang dipilih dari ComboBox pilihsuppliercbp
    Supplier selectedSupplier = pilihsuppliercbp.getSelectionModel().getSelectedItem();
    if (selectedSupplier != null) {
        System.out.println("Selected Supplier: " + selectedSupplier.getNama());
    } else {
        System.out.println("No Supplier selected in ComboBox.");
    }
}

    private void clearFormProduk() {
        pilihbibitcbp.getSelectionModel().clearSelection();
        pilihsuppliercbp.getItems().clear();
        jumlahpanenfield.clear();
        statuscekbox.setSelected(false);
        tanggalpanenfield.setValue(null);
    }

    private void tanggalsupplyp(ActionEvent event) {
    if (tanggalsupplyp.getValue() != null) {
        String selectedDate = tanggalsupplyp.getValue().toString();
        System.out.println("Tanggal panen yang dipilih: " + selectedDate);
    } else {
        System.out.println("Tidak ada tanggal yang dipilih.");
    }
}

    @FXML
    private void statuscb(ActionEvent event) {
    boolean isPanen = statuscekbox.isSelected();
    System.out.println("Status: " + (isPanen ? "Selesai" : "Pending"));
    }
    

    @FXML
    private void tambahp(ActionEvent event) {
    // Ambil data dari form
    Bibit selectedBibit = pilihbibitcbp.getSelectionModel().getSelectedItem();
    Supplier selectedSupplier = pilihsuppliercbp.getSelectionModel().getSelectedItem();
    boolean isPanen = statuscekbox.isSelected();
    String status = isPanen ? "Selesai" : "Pending";
    String tanggal = tanggalsupplyp.getValue() != null ? tanggalsupplyp.getValue().toString() : "";

    try {
        // Validasi input
        if (!validateInput(selectedBibit, selectedSupplier)) {
            return; // Jika validasi gagal, keluar dari metode
        }

        // Fetch id_supply_bibit using id_bibit and id_supplier
        int idSupplyBibit = dashboardDAO.getSupplyBibitId(selectedBibit.getIdBibit(), selectedSupplier.getIdSupplier());
        if (idSupplyBibit == -1) {
            showAlert("Error", "No matching supply_bibit found for the selected Bibit and Supplier.");
            return;
        }

        // Buat objek StatusTanam untuk memperbarui
        StatusTanam statusTanam = new StatusTanam(
            0, // no, jika tidak diperlukan
            0, // idTransaksi, jika tidak diperlukan
            selectedBibit.getIdBibit(), // bibit
            selectedSupplier.getIdSupplier(), // supplier
            status, // status baru
            tanggal,
            idSupplyBibit // Use the fetched idSupplyBibit
        );

        // Log statusTanam sebelum memperbarui
        System.out.println("Memperbarui StatusTanam: " + statusTanam);

        // Memperbarui ke database
        boolean isSuccess = dashboardDAO.tambahStatusTanam(statusTanam);
        System.out.println("Hasil pembaruan: " + isSuccess);
        
        if (isSuccess) {
            // Refresh tabel status
            initializeStatusTable(loggedInIdPetani);

            // Tampilkan pesan sukses
            showAlert("Success", "Status berhasil diperbarui!");

            // Kosongkan form
            clearFormProduk();
        } else {
            showAlert("Error", "Gagal memperbarui status di database.");
        }

    } catch (SQLException e) {
        showAlert("Error", "Gagal memperbarui status di database: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        showAlert("Error", "Terjadi kesalahan yang tidak terduga: " + e.getMessage());
        e.printStackTrace();
    }
}

// Metode untuk memvalidasi input
private boolean validateInput(Bibit selectedBibit, Supplier selectedSupplier) {
    if (selectedBibit == null) {
        showAlert("Error", "Silakan pilih Bibit yang valid.");
        return false;
    }

    if (selectedSupplier == null) {
        showAlert("Error", "Silakan pilih Supplier yang valid.");
        return false;
    }

    return true; // Semua validasi berhasil
}

    @FXML
    private void stokproduk(ActionEvent event) {
    System.out.println("Event stokproduk triggered.");
    try {
        int jumlah = Integer.parseInt(stokfieldp.getText());
        System.out.println("Jumlah stok: " + jumlah);
        if (jumlah <= 0) {
            showAlert("Invalid Input", "Jumlah stok harus lebih besar dari nol.");
        }
    } catch (NumberFormatException e) {
        showAlert("Invalid Input", "Silakan masukkan angka yang valid.");
    }
}

    @FXML
    private void hargap(ActionEvent event) {
    System.out.println("Event hargap triggered.");
    try {
        double harga = Double.parseDouble(hargafield.getText());
        System.out.println("Harga produk: " + harga);
        if (harga <= 0) {
            showAlert("Invalid Input", "Harga produk harus lebih besar dari nol.");
        }
    } catch (NumberFormatException e) {
        showAlert("Invalid Input", "Silakan masukkan angka yang valid.");
    }
}
    @FXML
    private void tambahp1(ActionEvent event) {
        Bibit selectedBibit = pilihbibitcbp1.getSelectionModel().getSelectedItem();
        String stokText = stokfieldp.getText();
        String hargaText = hargafield.getText();

        try {
            // Validate input
            if (selectedBibit == null) {
                showAlert("Error", "Silakan pilih Bibit yang valid.");
                return;
            }

            if (stokText.isEmpty() || hargaText.isEmpty()) {
                showAlert("Error", "Silakan masukkan stok dan harga.");
                return;
            }

            int stok = Integer.parseInt(stokText);
            double harga = Double.parseDouble(hargaText);

            // Create TransaksiTanamPanen object with id_petani
            TransaksiTanamPanen transaksi = new TransaksiTanamPanen(
                0, // ID (can be set to 0 or handled in the database if auto-increment)
                selectedBibit.getIdBibit(), // Use ID of the selected Bibit
                selectedBibit.getNama(), // Product (bibit)
                stok, // Stock
                harga, // Product Price
                loggedInIdPetani // Pass the logged-in petani ID
            );

            // Insert into database
            boolean success = dashboardDAO.tambahTransaksiTanamPanen(transaksi);
            if (success) {
                // Refresh product table
                initializeProdukTable(loggedInIdPetani); // Call method to refresh product table

                // Show success message
                showAlert("Success", "Data berhasil ditambahkan!");

                // Clear form
                clearFormProduk();
            } else {
                showAlert("Error", "Gagal menambahkan data ke database.");
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Silakan masukkan angka yang valid untuk stok dan harga.");
        } catch (SQLException e) {
            showAlert("Error", "Gagal menambahkan data ke database: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("Error", "Terjadi kesalahan yang tidak terduga: " + e.getMessage());
            e.printStackTrace();
        }
    }


   @FXML
private void hapusData(ActionEvent event) {
    // Ambil data yang dipilih dari tabel status
    StatusTanam selectedStatus = tabelstatus.getSelectionModel().getSelectedItem();
    
    if (selectedStatus != null) {
        // Konfirmasi penghapusan
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText("Anda yakin ingin menghapus data ini?");
        alert.setContentText("Data yang dipilih: " + selectedStatus.getBibit());
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Hapus data dari database
                boolean success = dashboardDAO.hapusStatusTanam(selectedStatus.getIdTransaksi()); // Pastikan Anda memiliki metode ini di DAO
                if (success) {
                    // Refresh tabel status
                    initializeStatusTable(loggedInIdPetani);
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
    private void updateData(ActionEvent event) {
    // Ambil data yang dipilih dari tabel status
    StatusTanam selectedStatus = tabelstatus.getSelectionModel().getSelectedItem();
    
    if (selectedStatus != null) {
        // Tampilkan dialog untuk memilih tanggal
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Update Status");
        dialog.setHeaderText("Pilih Tanggal dan Ubah Status");

        // Set the button types.
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        // Create a ComboBox for available dates
        ComboBox<String> tanggalComboBox = new ComboBox<>();
        tanggalComboBox.getItems().addAll(getAvailableDates()); // Ambil tanggal yang tersedia dari tabel
        tanggalComboBox.setPromptText("Pilih Tanggal");

        // Create a VBox to hold the ComboBox
        VBox vbox = new VBox();
        vbox.getChildren().add(tanggalComboBox);
        dialog.getDialogPane().setContent(vbox);

        // Convert the result to a string when the OK button is pressed.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                return tanggalComboBox.getValue();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(selectedDate -> {
            String newStatus = "Pending"; // Ubah status menjadi "Selesai"

            // Lakukan update di database
            try {
                boolean success = dashboardDAO.updateStatusTanam(
                    selectedStatus.getIdTransaksi(), // ID Transaksi
                    newStatus, // Status baru
                    selectedDate // Tanggal baru
                );
                if (success) {
                    // Refresh tabel status
                    initializeStatusTable(loggedInIdPetani);
                    showAlert("Success", "Status berhasil diperbarui!");
                } else {
                    showAlert("Error", "Gagal memperbarui status di database.");
                }
            } catch (SQLException e) {
                showAlert("Error", "Gagal memperbarui status di database: " + e.getMessage());
                e.printStackTrace();
            }
        });
    } else {
        showAlert("Error", "Silakan pilih data yang ingin diperbarui.");
    }
}

    // Metode untuk mendapatkan tanggal yang tersedia dari tabel
    private List<String> getAvailableDates() {
        List<String> availableDates = new ArrayList<>();
        for (StatusTanam status : tabelstatus.getItems()) {
            if (!availableDates.contains(status.getTanggalSupply())) {
                availableDates.add(status.getTanggalSupply());
            }
        }
        return availableDates;
    }

   @FXML
    private void hapusData1(ActionEvent event) throws SQLException {
        // Ambil data yang dipilih dari tabel transaksi
        TransaksiTanamPanen selectedTransaksi = tabelproduk.getSelectionModel().getSelectedItem();

        if (selectedTransaksi != null) {
            // Konfirmasi penghapusan
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Hapus");
            alert.setHeaderText("Anda yakin ingin menghapus data ini?");
            alert.setContentText("Data yang dipilih: " + selectedTransaksi.getBibit());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Hapus data dari database
                boolean success = dashboardDAO.hapusTransaksiTanamPanen(selectedTransaksi.getIdProduk()); // Pastikan Anda memiliki metode ini di DAO
                if (success) {
                    // Refresh tabel transaksi
                    initializeBibitTable(loggedInIdPetani);
                    showAlert("Success", "Data berhasil dihapus!");
                } else {
                    showAlert("Error", "Gagal menghapus data dari database.");
                }
            }
        } else {
            showAlert("Error", "Silakan pilih data yang ingin dihapus.");
        }
    }

    @FXML
    private void updateData1(ActionEvent event) {
        // Ambil data yang dipilih dari tabel transaksi
        TransaksiTanamPanen selectedTransaksi = tabelproduk.getSelectionModel().getSelectedItem();

        if (selectedTransaksi != null) {
            System.out.println("Selected Transaksi ID: " + selectedTransaksi.getIdProduk()); // Debugging output

            // Tampilkan dialog untuk mengedit data
            Dialog<TransaksiTanamPanen> dialog = new Dialog<>();
            dialog.setTitle("Update Data");
            dialog.setHeaderText("Update Transaksi Tanam Panen");

            // Set the button types.
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

            // Create fields for input
            TextField stokField = new TextField(String.valueOf(selectedTransaksi.getStok()));
            stokField.setPromptText("Stok");

            TextField hargaField = new TextField(String.valueOf(selectedTransaksi.getHargaProduk()));
            hargaField.setPromptText("Harga Produk");

            // Create a VBox to hold the fields
            VBox vbox = new VBox();
            vbox.getChildren().addAll(new Label("Stok:"), stokField, new Label("Harga:"), hargaField);
            dialog.getDialogPane().setContent(vbox);

            // Convert the result to a TransaksiTanamPanen when the OK button is pressed.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButton) {
                    return new TransaksiTanamPanen(
                        selectedTransaksi.getNo(), // No
                        selectedTransaksi.getIdProduk(), // Ensure this is the correct ID
                        selectedTransaksi.getIdBibit(),
                        selectedTransaksi.getBibit(), // Keep the original product name
                        Integer.parseInt(stokField.getText()), // Stok
                        Double.parseDouble(hargaField.getText()) // Harga
                    );
                }
                return null;
            });

            Optional<TransaksiTanamPanen> result = dialog.showAndWait();
            result.ifPresent(updatedTransaksi -> {
                // Lakukan update di database
                try {
                    boolean success = dashboardDAO.updateTransaksiTanamPanen(updatedTransaksi);
                    if (success) {
                        // Refresh tabel transaksi
                        initializeBibitTable(loggedInIdPetani);
                        showAlert("Success", "Data berhasil diperbarui!");
                    } else {
                        showAlert("Error", "Gagal memperbarui data di database.");
                    }
                } catch (SQLException e) {
                    showAlert("Error", "Gagal memperbarui data di database: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        } else {
            showAlert("Error", "Silakan pilih data yang ingin diperbarui.");
        }
    }


    
    @FXML
    private void initializeLihatProdukComboBox(ActionEvent event) throws SQLException {
        // Fetch the list of products for the logged-in farmer
        List<Produk> produkList = dashboardDAO.getProdukByPetani(loggedInIdPetani);
        
        // Debugging: Check if produkList is empty
        if (produkList.isEmpty()) {
            System.out.println("No products found for id_petani: " + loggedInIdPetani);
        } else {
            System.out.println("Products found: " + produkList.size());
        }

        ObservableList<Produk> observableProdukList = FXCollections.observableArrayList(produkList);

        // Set the items in the ComboBox
        lihatprodukcmb.setItems(observableProdukList);
        
        // Implement auto-completion
        FilteredList<Produk> filteredData = new FilteredList<>(observableProdukList, p -> true);

        // Listener for text input in the ComboBox editor
        lihatprodukcmb.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(produk -> {
                // If no input, show all products
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare product name with user input
                String lowerCaseFilter = newValue.toLowerCase();
                return produk.getBibit().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Bind the filtered data to the ComboBox
        lihatprodukcmb.setItems(filteredData);
    }



@FXML
private void jumlahjual(ActionEvent event) {
    System.out.println("Event jumlahjual triggered.");
    try {
        // Get the quantity from the input field
        int jumlah = Integer.parseInt(jumlahjualfield.getText()); // Assuming this is the TextField for quantity
        System.out.println("Jumlah jual: " + jumlah);
        
        // Validate the input
        if (jumlah <= 0) {
            showAlert("Invalid Input", "Jumlah jual harus lebih besar dari nol.");
            return; // Exit the method if the input is invalid
        }

    } catch (NumberFormatException e) {
        showAlert("Invalid Input", "Silakan masukkan angka yang valid.");
    }
}

private void hargaJual(ActionEvent event) {
    System.out.println("Event hargaJual triggered.");
    try {
        // Get the selling price from the input field
        double harga = Double.parseDouble(hargapenjualanfield.getText()); // Assuming this is the TextField for selling price
        System.out.println("Harga jual: " + harga);
        
        // Validate the input
        if (harga <= 0) {
            showAlert("Invalid Input", "Harga jual harus lebih besar dari nol.");
            return; // Exit the method if the input is invalid
        }

    } catch (NumberFormatException e) {
        showAlert("Invalid Input", "Silakan masukkan angka yang valid untuk harga.");
    }
}



    
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
        Connection connection = DatabaseConnection.getConnection();
        dashboardDAO = new DashboardDAO(connection);

        // Initialize various components
        initializeFormData();
        initializeBibitTable(loggedInIdPetani);
        initializeStatusTable(loggedInIdPetani);
        initializeProdukTable(loggedInIdPetani);
        

        // Initialize checkbox state
        if (statuscekbox != null) {
            statuscekbox.setSelected(false); // Default not checked
        }

    } catch (SQLException e) {
        System.err.println("Database error during initialization: " + e.getMessage());
        showAlert("Database Error", "An error occurred while accessing the database.");
    } catch (Exception e) {
        System.err.println("Unexpected error during initialization: " + e.getMessage());
        showAlert("Error", "An unexpected error occurred: " + e.getMessage());
    }
}



    // Metode untuk inisialisasi data form
    private void initializeFormData() throws SQLException {
    List<Bibit> bibitList = dashboardDAO.getAllBibit();
    pilihbibitcb.getItems().addAll(bibitList);
    pilihbibitcbp.getItems().addAll(bibitList);
    pilihbibitcbp1.getItems().addAll(bibitList);

    // Binding tombol tambah bibit
    tambahbibitbtn.disableProperty().bind(
        pilihbibitcb.valueProperty().isNull()
            .or(pilihsuppliercb.valueProperty().isNull())
            .or(jumlahbibitfield.textProperty().isEmpty())
            .or(tanggaltanamfield.valueProperty().isNull())
    );
}
    
    
    // Metode untuk inisialisasi tabel Bibit
       private void initializeBibitTable(int idPetani) throws SQLException {
       List<TransaksiBeliBibit> transaksiList = dashboardDAO.getTransaksiBeliBibitData(idPetani);
       ObservableList<TransaksiBeliBibit> observableTransaksiList = FXCollections.observableArrayList(transaksiList);
       tabelbibit.setItems(observableTransaksiList);

       colNo.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(tabelbibit.getItems().indexOf(param.getValue()) + 1));
       colBibit.setCellValueFactory(new PropertyValueFactory<>("bibit"));
       colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
       colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
       colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalSupply"));
       colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
   }
 
    private void initializeStatusTable(int idPetani) throws SQLException {
    List<StatusTanam> statusList = dashboardDAO.getProdukByStatus("Pending", idPetani);
    ObservableList<StatusTanam> observableStatusList = FXCollections.observableArrayList(statusList);
    tabelstatus.setItems(observableStatusList);

    colnos.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(tabelstatus.getItems().indexOf(param.getValue()) + 1));
    colbibits.setCellValueFactory(new PropertyValueFactory<>("bibit"));
    colsuppliers.setCellValueFactory(new PropertyValueFactory<>("supplier"));
    colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    coltanggalsupplys.setCellValueFactory(new PropertyValueFactory<>("tanggalSupply"));
    }


   
    private void initializeProdukTable(int idPetani) throws SQLException {
    List<TransaksiTanamPanen> transaksiList = dashboardDAO.getTransaksiTanamPanen(idPetani);
    ObservableList<TransaksiTanamPanen> observableTransaksiList = FXCollections.observableArrayList(transaksiList);
    tabelproduk.setItems(observableTransaksiList);
    
    // Set up the cell value factories for the table columns
    colnop.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(tabelproduk.getItems().indexOf(param.getValue()) + 1));
    colbibitp.setCellValueFactory(new PropertyValueFactory<>("bibit"));
    stokp.setCellValueFactory(new PropertyValueFactory<>("stok"));
    hargap.setCellValueFactory(new PropertyValueFactory<>("hargaProduk"));
}

    @FXML
    private void hargajual(ActionEvent event) {
    }

    @FXML
    private void updatepenjualan(ActionEvent event) {
    }

    @FXML
    private void tambahpenjualan(ActionEvent event) {
    }

    @FXML
    private void deletepenjualan(ActionEvent event) {
    }

    @FXML
    private void updatetp(ActionEvent event) {
    }

    @FXML
    private void tambahtp(ActionEvent event) {
    }

    @FXML
    private void hapustp(ActionEvent event) {
    }

    
    
}


