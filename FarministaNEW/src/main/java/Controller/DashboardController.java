package Controller;

import DAO.DashboardDAO;
import DAO.DatabaseConnection;
import Model.Bibit;
import Model.StatusTanam;
import Model.Supplier;
import Model.TanamPanen;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
@FXML
private TextField stokfieldp;
@FXML
private TextField hargafield;
@FXML
private TextField jumlahjualfield;
private TextField hargapenjualanfield;

// DatePickers
@FXML
private DatePicker tanggaltanamfield;
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
private TableView<TanamPanen> tabletanampanen;
@FXML
private TableColumn<TanamPanen, Integer> colnotp;
@FXML
private TableColumn<TanamPanen, String> colbibittp;
@FXML
private TableColumn<TanamPanen, String> colsuppliertp;
@FXML
private TableColumn<TanamPanen, String> coltanggalsupplytp;
@FXML
private TableColumn<TanamPanen, String> coltanggaltanamtp;
@FXML
private TableColumn<TanamPanen, String> coltanggalpanentp;

@FXML
private TableView<Penjualan> tabelpenjualan; // Tabel untuk menampilkan data penjualan
@FXML
private TableColumn<Penjualan, Integer> colnjual; // Kolom untuk ID penjualan
@FXML
private TableColumn<Penjualan, Integer> colProduk; // Kolom untuk ID produk
@FXML
private TableColumn<Penjualan, Integer> colJumlahprodukjual; // Kolom untuk jumlah
@FXML
private TableColumn<Penjualan, Double> colTotalHarga; // Kolom untuk total harga


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
private Button hapustpbtn;

// CheckBox
@FXML
private CheckBox statuscekbox;

// Other Fields
private DashboardDAO dashboardDAO;
private String username;
private int loggedInIdPetani; // Add this field to store the logged-in id_petani
    @FXML
    private Button updatebibitbtn;
    @FXML
    private Button hapusbibitbtn;
    @FXML
    private Label totalpenjualan;
    @FXML
    private Label totalproduk;
    @FXML
    private Label totalbibit;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private BarChart<String, Number> totalkeseluruhan;


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

        // Update totals and profit graph when showing the home form  
        updateTotalPenjualan();  
        updateTotalProduk();  
        updateTotalBibit();  
        updateBarChart();

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
    try {
            initializeTanamPanenTable(loggedInIdPetani);
        } catch (SQLException e) {
            System.err.println("Error initializing tables: " + e.getMessage());
            showAlert("Error", "Failed to load data for the Produk table.");
        }
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

       // Refresh the tables and ComboBox whenever this form is shown
       try {
           initializePenjualanTable(loggedInIdPetani); // Inisialisasi tabel penjualan
           initializeLihatProdukComboBox();
       } catch (SQLException e) {
           System.err.println("Error initializing tables: " + e.getMessage());
           showAlert("Error", "Failed to load data for the Penjualan table.");
       }
   }
    
    private void updateTotalPenjualan() {  
    try {  
        int total = dashboardDAO.getTotalPenjualan(loggedInIdPetani);  
        totalpenjualan.setText(String.valueOf(total)); // Set total sales  
    } catch (SQLException e) {  
        showAlert("Error", "Failed to fetch total sales: " + e.getMessage());  
        e.printStackTrace();  
    }  
}  
  
    private void updateTotalProduk() {  
        try {  
            int total = dashboardDAO.getTotalProduk(loggedInIdPetani);  
            totalproduk.setText(String.valueOf(total)); // Set total products  
        } catch (SQLException e) {  
            showAlert("Error", "Failed to fetch total products: " + e.getMessage());  
            e.printStackTrace();  
        }  
    }  

    private void updateTotalBibit() {  
        try {  
            int total = dashboardDAO.getTotalBibit(loggedInIdPetani);  
            totalbibit.setText(String.valueOf(total)); // Set total seeds  
        } catch (SQLException e) {  
            showAlert("Error", "Failed to fetch total seeds: " + e.getMessage());  
            e.printStackTrace();  
        }  
    }  

        // Method to update the BarChart  
    private void updateBarChart() {  
    try {  
        // Fetch total expenses and income  
        double totalHargaBeli = dashboardDAO.getTotalHargaBeli(loggedInIdPetani);  
        double totalHargaJual = dashboardDAO.getTotalHargaJual(loggedInIdPetani);  
  
        // Create a series for expenses  
        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();  
        expenseSeries.setName("Total Pengeluaran");  
  
        // Create a series for income  
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();  
        incomeSeries.setName("Total Pemasukan");  
  
        // Add data points for expenses and income  
        XYChart.Data<String, Number> expenseData = new XYChart.Data<>("Pengeluaran", totalHargaBeli);  
        XYChart.Data<String, Number> incomeData = new XYChart.Data<>("Pemasukan", totalHargaJual);  
  
        // Set the color for the expense bar (blue)  
        expenseData.nodeProperty().addListener((obs, oldNode, newNode) -> {  
            if (newNode != null) {  
                newNode.setStyle("-fx-bar-fill: blue;"); // Set the color to blue  
            }  
        });  
  
        // Set the color for the income bar (green)  
        incomeData.nodeProperty().addListener((obs, oldNode, newNode) -> {  
            if (newNode != null) {  
                newNode.setStyle("-fx-bar-fill: green;"); // Set the color to green  
            }  
        });  
  
        // Add data points to the respective series  
        expenseSeries.getData().add(expenseData);  
        incomeSeries.getData().add(incomeData);  
  
        // Clear previous data and add the new series  
        totalkeseluruhan.getData().clear();  
        totalkeseluruhan.getData().add(expenseSeries);  
        totalkeseluruhan.getData().add(incomeSeries);  
  
        // Adjust the bar width and spacing  
        for (XYChart.Data<String, Number> data : expenseSeries.getData()) {  
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {  
                if (newNode != null) {  
                    newNode.setStyle("-fx-bar-fill: blue; -fx-pref-width: 40;"); // Set width for expense bars  
                }  
            });  
        }  
  
        for (XYChart.Data<String, Number> data : incomeSeries.getData()) {  
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {  
                if (newNode != null) {  
                    newNode.setStyle("-fx-bar-fill: green; -fx-pref-width: 40;"); // Set width for income bars  
                }  
            });  
        }  
  
        // Set the spacing between bars  
        totalkeseluruhan.setCategoryGap(10); // Adjust this value to change the gap between categories  
  
    } catch (SQLException e) {  
        showAlert("Error", "Failed to fetch total data: " + e.getMessage());  
        e.printStackTrace();  
    }  
}  

    
    private void initializeLihatProdukComboBox() {
    try {
        List<Produk> produkList = dashboardDAO.getProdukByPetani(loggedInIdPetani);
        ObservableList<Produk> observableProdukList = FXCollections.observableArrayList(produkList);
        lihatprodukcmb.setItems(observableProdukList);
        
        // Debugging: Check if produkList is empty
        if (produkList.isEmpty()) {
            System.out.println("No products found for id_petani: " + loggedInIdPetani);
            showAlert("Info", "Tidak ada produk ditemukan untuk petani ini.");
        } else {
            System.out.println("Products found: " + produkList.size());
        }
    } catch (SQLException e) {
        showAlert("Error", "Gagal mengambil data produk: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        showAlert("Error", "Terjadi kesalahan yang tidak terduga: " + e.getMessage());
        e.printStackTrace();
    }
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

    System.out.println("Selected Bibit ID: " + (selectedBibit != null ? selectedBibit.getIdBibit() : "None"));
    System.out.println("Selected Bibit Name: " + (selectedBibit != null ? selectedBibit.getNama() : "None"));
    System.out.println("Selected Supplier: " + (selectedSupplier != null ? selectedSupplier.getIdSupplier() : "None"));
    System.out.println("Tanggal Tanam: " + tanggal);

    try {
        if (selectedBibit == null || selectedSupplier == null || jumlahText.isEmpty() || tanggal.isEmpty()) {
            showAlert("Error", "Please fill out all fields.");
            return;
        }

        int jumlah = Integer.parseInt(jumlahText);
        if (jumlah <= 0) {
            showAlert("Error", "Number of seeds must be greater than zero.");
            return;
        }

        double harga = dashboardDAO.getHargaBySupplyBibit(selectedBibit.getIdBibit(), selectedSupplier.getIdSupplier());
        if (harga <= 0) {
            showAlert("Error", "Invalid seed price.");
            return;
        }

        double totalHarga = harga * jumlah;

        int idPetani = dashboardDAO.getIdPetaniByUsername(username);
        if (idPetani == 0) {
            showAlert("Error", "Failed to identify the farmer.");
            return;
        }

        int idSupplyBibit = dashboardDAO.getSupplyBibitId(selectedBibit.getIdBibit(), selectedSupplier.getIdSupplier());
        System.out.println("Retrieved id_supply_bibit: " + idSupplyBibit);
        if (idSupplyBibit == 0) {
            showAlert("Error", "No matching supply_bibit record found for the selected Bibit and Supplier.");
            return;
        }

        // Create the TransaksiBeliBibit object
        TransaksiBeliBibit newTransaksi = new TransaksiBeliBibit(
            idSupplyBibit,
            selectedSupplier.getIdSupplier(),
            idPetani,
            jumlah,
            totalHarga,
            "Pending",
            tanggal
        );

        boolean success = dashboardDAO.insertTransaksi(newTransaksi);
        if (success) {
            showAlert("Success", "Transaction added successfully!");
            clearForm();

            List<TransaksiBeliBibit> transaksiList = dashboardDAO.getTransaksiBeliBibitData(idPetani);
            tabelbibit.setItems(FXCollections.observableArrayList(transaksiList));
            tabelbibit.refresh();
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

    @FXML
private void hapusbibit(ActionEvent event) {
    // Ambil data yang dipilih dari tabel bibit
    TransaksiBeliBibit selectedTransaksi = tabelbibit.getSelectionModel().getSelectedItem();

    if (selectedTransaksi != null) {
        // Konfirmasi penghapusan
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText("Anda yakin ingin menghapus data ini?");
        alert.setContentText("Data yang dipilih: " + selectedTransaksi.getBibit());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Hapus data dari database
                boolean success = dashboardDAO.hapusTransaksiBeliBibit(selectedTransaksi.getIdTransaksi());
                if (success) {
                    // Refresh tabel bibit
                    initializeBibitTable(loggedInIdPetani);
                    showAlert("Success", "Data bibit berhasil dihapus!");
                } else {
                    showAlert("Error", "Gagal menghapus data bibit dari database.");
                }
            } catch (SQLException e) {
                showAlert("Error", "Gagal menghapus data bibit dari database: " + e.getMessage());
                e.printStackTrace();
            }
        }
    } else {
        showAlert("Error", "Silakan pilih data bibit yang ingin dihapus.");
    }
}

@FXML
    private void updatebibit(ActionEvent event) {
        // Ambil data yang dipilih dari tabel bibit
        TransaksiBeliBibit selectedTransaksi = tabelbibit.getSelectionModel().getSelectedItem();

        if (selectedTransaksi != null) {
            System.out.println("Selected Transaksi ID: " + selectedTransaksi.getIdTransaksi()); // Debugging output

            // Tampilkan dialog untuk mengedit data
            Dialog<TransaksiBeliBibit> dialog = new Dialog<>();
            dialog.setTitle("Update Bibit");
            dialog.setHeaderText("Update Data Bibit");

            // Set the button types.
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

            // Create fields for input
            ComboBox<Bibit> pilihbibitcb = new ComboBox<>();
            ComboBox<Supplier> pilihsuppliercb = new ComboBox<>();
            TextField jumlahField = new TextField(String.valueOf(selectedTransaksi.getJumlah()));
            jumlahField.setPromptText("Jumlah");
            DatePicker tanggaltanamField = new DatePicker();
            tanggaltanamField.setValue(LocalDate.parse(selectedTransaksi.getTanggalSupply())); // Set tanggal yang ada

            // Load bibit into ComboBox
            try {
                List<Bibit> bibitList = dashboardDAO.getAllBibit();
                pilihbibitcb.getItems().addAll(bibitList);
                pilihbibitcb.getSelectionModel().select(selectedTransaksi.getIdBibit()); // Select the current bibit

                // Load suppliers based on the selected bibit
                loadSuppliersForSelectedBibit(selectedTransaksi.getIdBibit(), pilihsuppliercb);
            } catch (SQLException e) {
                showAlert("Error", "Failed to load bibit data: " + e.getMessage());
                return;
            }

            // Add listener to update suppliers when a new bibit is selected
            pilihbibitcb.setOnAction(event1 -> {
                Bibit selectedBibit = pilihbibitcb.getSelectionModel().getSelectedItem();
                if (selectedBibit != null) {
                    loadSuppliersForSelectedBibit(selectedBibit.getIdBibit(), pilihsuppliercb);
                }
            });

            // Create a VBox to hold the fields
            VBox vbox = new VBox(10); // Add spacing between elements
            vbox.getChildren().addAll(
                new Label("Pilih Bibit:"), 
                pilihbibitcb, 
                new Label("Pilih Supplier:"), 
                pilihsuppliercb, 
                new Label("Jumlah:"), 
                jumlahField, 
                new Label("Tanggal Tanam:"), 
                tanggaltanamField
            );
            dialog.getDialogPane().setContent(vbox);

            // Convert the result to a TransaksiBeliBibit when the OK button is pressed.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButton) {
                    try {
                        int jumlahBaru = Integer.parseInt(jumlahField.getText());
                        if (jumlahBaru <= 0) {
                            showAlert("Error", "Jumlah harus lebih besar dari nol.");
                            return null;
                        }

                        Bibit selectedBibit = pilihbibitcb.getSelectionModel().getSelectedItem();
                        Supplier selectedSupplier = pilihsuppliercb.getSelectionModel().getSelectedItem();

                        if (selectedBibit == null || selectedSupplier == null) {
                            showAlert("Error", "Silakan pilih bibit dan supplier.");
                            return null;
                        }

                        LocalDate selectedDate = tanggaltanamField.getValue();
                        if (selectedDate == null) {
                            showAlert("Error", "Tanggal tanam tidak boleh kosong.");
                            return null;
                        }

                        // Retrieve the supply bibit ID
                        int idSupplyBibit = dashboardDAO.getSupplyBibitId(selectedBibit.getIdBibit(), selectedSupplier.getIdSupplier());
                        if (idSupplyBibit == -1) {
                            showAlert("Error", "ID Supply Bibit tidak ditemukan.");
                            return null;
                        }

                        // Create the TransaksiBeliBibit object
                        return new TransaksiBeliBibit(
                            selectedTransaksi.getIdTransaksi(), // ID transaksi
                            idSupplyBibit, // ID supply bibit
                            selectedTransaksi.getIdPetani(), // ID petani
                            jumlahBaru, // Jumlah baru
                            selectedTransaksi.getHarga(), // Harga tetap
                            "Pending", // Status tetap
                            selectedDate.toString() // Tanggal baru
                        );
                    } catch (NumberFormatException e) {
                        showAlert("Error", "Jumlah harus berupa angka.");
                    }
                }
                return null;
            });

            // Show the dialog and wait for the result
            Optional<TransaksiBeliBibit> result = dialog.showAndWait();
            result.ifPresent(updatedTransaksi -> {
                try {
                    // Update the transaction in the database
                    boolean success = dashboardDAO.updateTransaksiBeliBibit(updatedTransaksi);
                    if (success) {
                        // Refresh the table or perform any other necessary actions
                        initializeBibitTable(selectedTransaksi.getIdPetani());
                        showAlert("Success", "Data bibit berhasil diperbarui!");
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

// Method to load suppliers based on selected bibit
private void loadSuppliersForSelectedBibit(int bibitId, ComboBox<Supplier> supplierComboBox) {
    try {
        List<Supplier> supplierList = dashboardDAO.getSuppliersByBibit(bibitId);
        supplierComboBox.getItems().clear(); // Clear previous items
        supplierComboBox.getItems().addAll(supplierList);
        if (!supplierList.isEmpty()) {
            supplierComboBox.getSelectionModel().selectFirst(); // Optionally select the first supplier
        }
    } catch (SQLException e) {
        showAlert("Error", "Failed to load suppliers: " + e.getMessage());
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
        statuscekbox.setSelected(false);
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

        // Periksa apakah bibit sudah ada di tabel produk berdasarkan id_bibit
        if (dashboardDAO.isBibitExistsInProduk(selectedBibit.getIdBibit())) {
            showAlert("Warning", "Bibit " + selectedBibit.getNama() + " sudah ada pada tabel. Silakan perbarui data.");
            return; // Keluar dari metode jika bibit sudah ada
        }

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
        // Tampilkan dialog konfirmasi sebelum memperbarui status
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Konfirmasi Pembaruan");
        confirmationAlert.setHeaderText("Anda yakin ingin memperbarui status?");
        confirmationAlert.setContentText("Status akan diubah menjadi 'Selesai'.");

        Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
        if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {
            String newStatus = "Selesai"; // Ubah status menjadi "Selesai"

            // Lakukan update di database
            try {
                boolean success = dashboardDAO.updateStatusTanam(
                    selectedStatus.getIdTransaksi(), // ID Transaksi
                    newStatus // Status baru
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
        }
    } else {
        showAlert("Error", "Silakan pilih data yang ingin diperbarui.");
    }
}


   @FXML
private void hapusData1(ActionEvent event) {
    // Ambil data yang dipilih dari tabel produk
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
            try {
                boolean success = dashboardDAO.hapusTransaksiTanamPanen(selectedTransaksi.getIdProduk()); // Pastikan Anda memiliki metode ini di DAO
                if (success) {
                    // Refresh tabel produk
                    initializeProdukTable(loggedInIdPetani);
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


    
//penjualan
    private void loadBibitComboBox(int idProduk) {
    try {
        // Ambil daftar bibit berdasarkan produk yang dipilih
        List<Bibit> bibitList = dashboardDAO.getBibitByProduk(idProduk);
        pilihbibitcbp.getItems().clear(); // Kosongkan ComboBox bibit
        pilihbibitcbp.getItems().addAll(bibitList); // Tambahkan bibit yang baru diambil
        pilihbibitcbp.getSelectionModel().clearSelection(); // Reset pilihan
    } catch (SQLException e) {
        showAlert("Error", "Failed to load bibit: " + e.getMessage());
    }
}

    private void loadProdukComboBox() {
        try {
            // Ambil daftar produk dari database dan tambahkan ke lihatprodukcmb
            List<Produk> produkList = dashboardDAO.getProdukByPetani(loggedInIdPetani); // Ganti dengan ID petani yang sesuai
            lihatprodukcmb.getItems().addAll(produkList); // Pastikan ini menambahkan Produk

            // Tambahkan listener untuk ComboBox produk
            lihatprodukcmb.setOnAction(event -> {
                Produk selectedProduk = lihatprodukcmb.getSelectionModel().getSelectedItem();
                if (selectedProduk != null) {
                    loadBibitComboBox(selectedProduk.getIdProduk());
                }
            });
        } catch (SQLException e) {
            showAlert("Error", "Failed to load products: " + e.getMessage());
        }
    }



    @FXML
    private void pilihprodukjual(ActionEvent event) {
        // Mengambil produk yang dipilih dari ComboBox lihatprodukcmb
        Produk selectedProduk = lihatprodukcmb.getSelectionModel().getSelectedItem();

        // Memeriksa apakah ada produk yang dipilih
        if (selectedProduk != null) {
            try {
                // Ambil informasi produk berdasarkan ID produk yang dipilih
                int idProduk = selectedProduk.getIdProduk();
                double harga = dashboardDAO.getHargaByProduct(idProduk); // Mengambil harga produk
                int jumlah = dashboardDAO.getJumlahByProduct(idProduk); // Mengambil jumlah produk

                // Tampilkan informasi di TextField atau Label
                hargapenjualanfield.setText(String.valueOf(harga));
                jumlahjualfield.setText(String.valueOf(jumlah)); // Ganti stokfieldp dengan jumlahfieldp

                System.out.println("Produk dipilih: " + selectedProduk.getBibit() + ", Harga: " + harga + ", Jumlah: " + jumlah);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Gagal mengambil data produk: " + e.getMessage());
            }
        } else {
            System.out.println("Tidak ada produk yang dipilih.");
        }
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

   private void hargajual(ActionEvent event) {
       System.out.println("Event hargajual triggered.");
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

   @FXML
private void tambahpenjualan(ActionEvent event) {
    // Ambil data dari form
    Produk selectedProduk = lihatprodukcmb.getSelectionModel().getSelectedItem();
    String jumlahText = jumlahjualfield.getText();

    try {
        // Validasi input
        if (selectedProduk == null) {
            showAlert("Error", "Silakan pilih produk yang valid.");
            return;
        }

        if (jumlahText.isEmpty()) {
            showAlert("Error", "Silakan masukkan jumlah.");
            return;
        }

        int jumlah = Integer.parseInt(jumlahText);

        // Ambil harga produk dari database
        double hargaProduk = dashboardDAO.getHargaByProduct(selectedProduk.getIdProduk());
        if (hargaProduk <= 0) {
            showAlert("Error", "Harga produk tidak valid.");
            return;
        }

        // Hitung total harga berdasarkan jumlah dan harga produk
        double totalHarga = jumlah * hargaProduk; // Hitung total harga

        // Buat objek Penjualan
        Penjualan penjualan = new Penjualan(
            0, // ID penjualan (akan diatur di database jika auto-increment)
            selectedProduk.getIdProduk(), // ID produk
            jumlah, // Jumlah
            totalHarga, // Total harga
            loggedInIdPetani // ID petani
        );

        // Insert into database
        boolean success = dashboardDAO.ModelPenjualan(penjualan);
        if (success) {
            // Refresh tabel penjualan
            initializePenjualanTable(loggedInIdPetani); // Memanggil metode untuk memperbarui tabel penjualan

            // Show success message
            showAlert("Success", "Data penjualan berhasil ditambahkan!");

            // Clear form
            clearFormPenjualan();
        } else {
            showAlert("Error", "Gagal menambahkan data penjualan ke database.");
        }

    } catch (NumberFormatException e) {
        showAlert("Error", "Silakan masukkan angka yang valid untuk jumlah.");
    } catch (SQLException e) {
        showAlert("Error", "Gagal menambahkan data penjualan ke database: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        showAlert("Error", "Terjadi kesalahan yang tidak terduga: " + e.getMessage());
        e.printStackTrace();
    }
}



@FXML
private void updatePenjualan(ActionEvent event) {
    // Ambil data yang dipilih dari tabel penjualan
    Penjualan selectedPenjualan = tabelpenjualan.getSelectionModel().getSelectedItem();

    if (selectedPenjualan != null) {
        // Tampilkan dialog untuk mengedit data
        Dialog<Penjualan> dialog = new Dialog<>();
        dialog.setTitle("Update Penjualan");
        dialog.setHeaderText("Update Jumlah Penjualan");

        // Set the button types.
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        // Create fields for input
        TextField jumlahField = new TextField(String.valueOf(selectedPenjualan.getJumlah()));
        jumlahField.setPromptText("Jumlah");

        // Create a VBox to hold the fields
        VBox vbox = new VBox(10); // Add spacing between elements
        vbox.getChildren().addAll(new Label("Jumlah:"), jumlahField);
        dialog.getDialogPane().setContent(vbox);

        // Convert the result to a Penjualan when the OK button is pressed.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                try {
                    int jumlahBaru = Integer.parseInt(jumlahField.getText());
                    if (jumlahBaru < 0) {
                        showAlert("Error", "Jumlah tidak boleh negatif.");
                        return null; // Menghentikan proses jika jumlah negatif
                    }
                    return new Penjualan(
                        selectedPenjualan.getIdPenjualan(), // ID penjualan
                        selectedPenjualan.getProdukId(), // ID produk
                        jumlahBaru, // Jumlah baru
                        0.0, // Total harga akan dihitung nanti
                        selectedPenjualan.getIdPetani() // ID petani
                    );
                } catch (NumberFormatException e) {
                    showAlert("Error", "Jumlah harus berupa angka.");
                }
            }
            return null;
        });

        Optional<Penjualan> result = dialog.showAndWait();
        result.ifPresent(updatedPenjualan -> {
            try {
                // Dapatkan harga per unit dari produk
                double hargaPerUnit = dashboardDAO.getHargaPerUnit(updatedPenjualan.getProdukId());
                
                // Hitung total harga baru
                double totalHargaBaru = updatedPenjualan.getJumlah() * hargaPerUnit;
                updatedPenjualan.setTotalHarga(totalHargaBaru); // Perbarui total harga

                // Hitung selisih jumlah
                int selisih = updatedPenjualan.getJumlah() - selectedPenjualan.getJumlah();
                
                // Update penjualan di database
                boolean success = dashboardDAO.updatePenjualan(updatedPenjualan);
                if (success) {
                    // Update stok produk
                    if (selisih != 0) {
                        dashboardDAO.updateStokProduk(selectedPenjualan.getProdukId(), -selisih);
                    }
                    // Refresh tabel penjualan
                    initializePenjualanTable(loggedInIdPetani);
                    showAlert("Success", "Data penjualan berhasil diperbarui!");
                } else {
                    showAlert("Error", "Gagal memperbarui data penjualan di database.");
                }
            } catch (SQLException e) {
                showAlert("Error", "Gagal memperbarui data penjualan di database: " + e.getMessage());
                e.printStackTrace();
            }
        });
    } else {
        showAlert("Error", "Silakan pilih data penjualan yang ingin diperbarui.");
    }
}

@FXML
private void deletepenjualan(ActionEvent event) {
    // Ambil data yang dipilih dari tabel penjualan
    Penjualan selectedPenjualan = tabelpenjualan.getSelectionModel().getSelectedItem();

    if (selectedPenjualan != null) {
        // Konfirmasi penghapusan
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText("Anda yakin ingin menghapus penjualan ini?");
        alert.setContentText("Data yang dipilih: " + selectedPenjualan.getNamaBibit() + " dengan jumlah: " + selectedPenjualan.getJumlah());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Hitung jumlah yang akan dihapus
                int jumlahYangDihapus = selectedPenjualan.getJumlah();

                // Hapus penjualan dari database
                boolean success = dashboardDAO.hapusPenjualan(selectedPenjualan.getIdPenjualan(), jumlahYangDihapus);
                if (success) {
                    // Refresh tabel penjualan
                    initializePenjualanTable(loggedInIdPetani);
                    showAlert("Success", "Data penjualan berhasil dihapus!");
                } else {
                    showAlert("Error", "Gagal menghapus data penjualan dari database.");
                }
            } catch (SQLException e) {
                showAlert("Error", "Gagal menghapus data penjualan dari database: " + e.getMessage());
                e.printStackTrace();
            }
        }
    } else {
        showAlert("Error", "Silakan pilih data penjualan yang ingin dihapus.");
    }
}



private void clearFormPenjualan() {
    lihatprodukcmb.getSelectionModel().clearSelection();
    jumlahjualfield.clear();
}


//TanamPanen
@FXML  
private void updatetp(ActionEvent event) {  
    // Ambil data yang dipilih dari tabel  
    TanamPanen selectedTanamPanen = tabletanampanen.getSelectionModel().getSelectedItem();  
  
    if (selectedTanamPanen != null) {  
        // Tampilkan dialog untuk mengedit data  
        Dialog<TanamPanen> dialog = new Dialog<>();  
        dialog.setTitle("Update Tanam Panen");  
        dialog.setHeaderText("Update Waktu Tanam dan Waktu Panen");  
  
        // Set the button types.  
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);  
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);  
  
        // Create fields for input  
        DatePicker waktuTanamPicker = new DatePicker();  
        if (selectedTanamPanen.getTanggalTanam() != null) {  
            waktuTanamPicker.setValue(LocalDate.parse(selectedTanamPanen.getTanggalTanam()));  
        }  
        waktuTanamPicker.setPromptText("Waktu Tanam");  
  
        DatePicker waktuPanenPicker = new DatePicker();  
        if (selectedTanamPanen.getTanggalPanen() != null) {  
            waktuPanenPicker.setValue(LocalDate.parse(selectedTanamPanen.getTanggalPanen()));  
        }  
        waktuPanenPicker.setPromptText("Waktu Panen");  
  
        // Create a VBox to hold the fields  
        VBox vbox = new VBox(10); // Add spacing between elements  
        vbox.getChildren().addAll(new Label("Waktu Tanam:"), waktuTanamPicker,  
                new Label("Waktu Panen:"), waktuPanenPicker);  
        dialog.getDialogPane().setContent(vbox);  
  
        // Convert the result to a TanamPanen when the OK button is pressed.  
        dialog.setResultConverter(dialogButton -> {  
            if (dialogButton == okButton) {  
                LocalDate waktuTanam = waktuTanamPicker.getValue();  
                LocalDate waktuPanen = waktuPanenPicker.getValue();  
  
                // Validasi waktu tanam  
                if (waktuTanam == null && waktuPanen != null) {  
                    showAlert("Error", "Waktu Tanam tidak boleh kosong jika Waktu Panen diisi.");  
                    return null; // Menghentikan proses jika waktu tanam kosong dan waktu panen diisi  
                }  
  
                // Kembalikan objek TanamPanen dengan nilai yang diperbarui  
                return new TanamPanen(  
                    selectedTanamPanen.getNo(), // Nomor urut  
                    selectedTanamPanen.getBibit(), // Bibit  
                    selectedTanamPanen.getSupplier(), // Supplier  
                    selectedTanamPanen.getTanggalSupply(), // Tanggal supply  
                    (waktuTanam != null) ? waktuTanam.toString() : selectedTanamPanen.getTanggalTanam(), // Waktu tanam baru  
                    (waktuPanen != null) ? waktuPanen.toString() : selectedTanamPanen.getTanggalPanen(), // Waktu panen baru  
                    selectedTanamPanen.getIdTanamPanen(), // ID tanam panen  
                    selectedTanamPanen.getIdTransaksi() // ID transaksi  
                );  
            }  
            return null;  
        });  
  
        Optional<TanamPanen> result = dialog.showAndWait();  
        result.ifPresent(updatedTanamPanen -> {  
            try {  
                // Simpan data tanam dan panen yang diperbarui ke database  
                boolean success = dashboardDAO.updateTransaksiTanamPanen(updatedTanamPanen);  
                if (success) {  
                    // Refresh tabel atau lakukan tindakan lain yang diperlukan  
                    initializeTanamPanenTable(loggedInIdPetani); // Misalkan Anda memiliki metode ini untuk refresh  
                    showAlert("Success", "Data tanam dan panen berhasil diperbarui!");  
                } else {  
                    showAlert("Error", "Gagal memperbarui data tanam dan panen di database.");  
                }  
            } catch (SQLException e) {  
                showAlert("Error", "Gagal memperbarui data tanam dan panen di database: " + e.getMessage());  
                e.printStackTrace();  
            }  
        });  
    } else {  
        showAlert("Error", "Silakan pilih data tanam dan panen yang ingin diperbarui.");  
    }  
}  


@FXML  
private void hapustp(ActionEvent event) {  
    // Ambil data yang dipilih dari tabel  
    TanamPanen selectedTanamPanen = tabletanampanen.getSelectionModel().getSelectedItem();  
  
    if (selectedTanamPanen != null) {  
        // Konfirmasi penghapusan  
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);  
        alert.setTitle("Konfirmasi Hapus");  
        alert.setHeaderText("Anda yakin ingin menghapus waktu tanam dan panen ini?");  
        alert.setContentText("Data yang dipilih: " + selectedTanamPanen.getBibit() +   
                             " dengan waktu tanam: " + selectedTanamPanen.getTanggalTanam() +   
                             " dan waktu panen: " + selectedTanamPanen.getTanggalPanen());  
  
        Optional<ButtonType> result = alert.showAndWait();  
        if (result.isPresent() && result.get() == ButtonType.OK) {  
            try {  
                // Hapus waktu tanam dan waktu panen dari database  
                selectedTanamPanen.setTanggalTanam(null); // Kosongkan waktu tanam  
                selectedTanamPanen.setTanggalPanen(null); // Kosongkan waktu panen  
  
                // Simpan perubahan ke database  
                boolean success = dashboardDAO.updateTransaksiTanamPanen(selectedTanamPanen);  
                if (success) {  
                    // Refresh tabel tanam panen  
                    initializeTanamPanenTable(loggedInIdPetani);  
                    showAlert("Success", "Waktu tanam dan panen berhasil dihapus!");  
                } else {  
                    showAlert("Error", "Gagal menghapus waktu tanam dan panen dari database.");  
                }  
            } catch (SQLException e) {  
                showAlert("Error", "Gagal menghapus waktu tanam dan panen dari database: " + e.getMessage());  
                e.printStackTrace();  
            }  
        }  
    } else {  
        showAlert("Error", "Silakan pilih data tanam panen yang ingin dihapus.");  
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
        initializeTanamPanenTable(loggedInIdPetani);
        initializeProdukTable(loggedInIdPetani);
        initializePenjualanTable(loggedInIdPetani);
        
        loadProdukComboBox();

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

    private void initializeTanamPanenTable(int idPetani) throws SQLException {
    List<TanamPanen> tanampanenList = dashboardDAO.getTanamPanen(idPetani);
    ObservableList<TanamPanen> observableTanamPanenList = FXCollections.observableArrayList(tanampanenList);
    tabletanampanen.setItems(observableTanamPanenList);
    
    // Set up the cell value factories for the table columns
    colnotp.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(tabletanampanen.getItems().indexOf(param.getValue()) + 1));
    colbibittp.setCellValueFactory(new PropertyValueFactory<>("bibit"));
    colsuppliertp.setCellValueFactory(new PropertyValueFactory<>("supplier"));
    coltanggalsupplytp.setCellValueFactory(new PropertyValueFactory<>("tanggalSupply"));
    coltanggaltanamtp.setCellValueFactory(new PropertyValueFactory<>("tanggalTanam"));
    coltanggalpanentp.setCellValueFactory(new PropertyValueFactory<>("tanggalPanen"));
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
    
    private void initializePenjualanTable(int idPetani) throws SQLException {
    // Mengambil data penjualan berdasarkan ID petani
    List<Penjualan> penjualanList = dashboardDAO.getPenjualanData(idPetani);
    ObservableList<Penjualan> observablePenjualanList = FXCollections.observableArrayList(penjualanList);
    tabelpenjualan.setItems(observablePenjualanList);
    
    // Mengatur cell value factories untuk kolom tabel
    colnjual.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(tabelpenjualan.getItems().indexOf(param.getValue()) + 1));
    colProduk.setCellValueFactory(new PropertyValueFactory<>("namaBibit")); // ID produk
    colJumlahprodukjual.setCellValueFactory(new PropertyValueFactory<>("jumlah")); // Jumlah
    colTotalHarga.setCellValueFactory(new PropertyValueFactory<>("totalHarga")); // Total harga
    }
}


