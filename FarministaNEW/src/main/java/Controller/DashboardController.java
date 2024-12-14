package Controller;

import DAO.DashboardDAO;
import DAO.DatabaseConnection;
import Model.Bibit;
import Model.Supplier;
import Model.TransaksiBeliBibit;
import static com.mysql.cj.Messages.getString;
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
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController implements Initializable {

    @FXML
    private Button homebtn;
    @FXML
    private Button bibitbtn;
    @FXML
    private Button produkbtn;
    @FXML
    private Button penjualanbtn;
    @FXML
    private Button logoutbtn;
    @FXML
    private AnchorPane homeform;
    @FXML
    private AnchorPane bibitform;
    @FXML
    private AnchorPane produkform;
    @FXML
    private AnchorPane penjualanform;
    @FXML
    private StackPane top_form;
    @FXML
    private ComboBox<Bibit> pilihbibitcb;
    @FXML
    private ComboBox<Supplier> pilihsuppliercb;
    @FXML
    private TextField jumlahbibitfield;
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
    private Button tambahbibitbtn;
    @FXML
    private DatePicker tanggaltanamfield;

    private DashboardDAO dashboardDAO;
    private String username;

    public void setUsername(String username) {
        this.username = username;
        System.out.println("Logged in as: " + username);
    }

    @FXML
    public void gantiform(ActionEvent event) {
        if (event.getSource() == homebtn) {
            showHome();
        } else if (event.getSource() == bibitbtn) {
            showBibit();
        } else if (event.getSource() == produkbtn) {
            showProduk();
        } else if (event.getSource() == penjualanbtn) {
            showPenjualan();
        }
    }

    private void showHome() {
        homeform.setVisible(true);
        bibitform.setVisible(false);
        produkform.setVisible(false);
        penjualanform.setVisible(false);
    }

    private void showBibit() {
        homeform.setVisible(false);
        bibitform.setVisible(true);
        produkform.setVisible(false);
        penjualanform.setVisible(false);
    }

    private void showProduk() {
        homeform.setVisible(false);
        bibitform.setVisible(false);
        produkform.setVisible(true);
        penjualanform.setVisible(false);
    }

    private void showPenjualan() {
        homeform.setVisible(false);
        bibitform.setVisible(false);
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
    private void pilihsupplier(ActionEvent event) {
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
                List<TransaksiBeliBibit> transaksiList = DashboardDAO.getTransaksiBeliBibitData();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            dashboardDAO = new DashboardDAO(connection);

            List<Bibit> bibitList = dashboardDAO.getAllBibit();
                pilihbibitcb.getItems().addAll(bibitList);

                tambahbibitbtn.disableProperty().bind(
                        pilihbibitcb.valueProperty().isNull()
                                .or(pilihsuppliercb.valueProperty().isNull())
                                .or(jumlahbibitfield.textProperty().isEmpty())
                                .or(tanggaltanamfield.valueProperty().isNull())
                );
            // Fetch the list of TransaksiBeliBibit objects
            List<TransaksiBeliBibit> transaksiList = DashboardDAO.getTransaksiBeliBibitData();

            // Create an ObservableList from the List of TransaksiBeliBibit objects
            ObservableList<TransaksiBeliBibit> observableTransaksiList = FXCollections.observableArrayList(transaksiList);

            // Set the ObservableList as the data for the TableView
            tabelbibit.setItems(observableTransaksiList);

            // Set the cell value factory for the row number column (colNo)
            colNo.setCellValueFactory(param -> 
                new ReadOnlyObjectWrapper<>(tabelbibit.getItems().indexOf(param.getValue()) + 1)
            );

            // Set the cell value factory for other columns
            colBibit.setCellValueFactory(new PropertyValueFactory<>("bibit"));
            colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
            colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
            colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalSupply"));
            colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));

            // Optionally, add other logic or customize the table further
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

