/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import dao.Admin_dashboardDAO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.BibitData;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author A S U S
 */
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
    private TableView<BibitData> editdatadbibittabel;
    @FXML
    private TableColumn<BibitData, String> bibit;
    @FXML
    private TableColumn<BibitData, String> supplier;
    @FXML
    private TableColumn<BibitData, Double> harga;
    @FXML
    private Button editdatabibit;
    @FXML
    private AreaChart<?, ?> grafiktotalbibit;
    @FXML   
    private TableView<?> editrekapdatatabel1;
    @FXML
    private TableColumn<?, ?> bibit1;
    @FXML
    private TableColumn<?, ?> jumlahbibit1;
    @FXML
    private TableColumn<?, ?> supplier1;
    @FXML
    private TableColumn<?, ?> tanggaltanam1;
    @FXML
    private TableColumn<?, ?> tanggalpanen1;
    @FXML
    private TableColumn<?, ?> jumlahpanen1;
    @FXML
    private TableColumn<?, ?> hargajual1;
    @FXML
    private TableColumn<?, ?> jumlahterjual1;
    @FXML
    private TableColumn<?, ?> penjualan1;
    @FXML
    private Button editrekapanbibit1;
    
    Admin_dashboardDAO dao = new Admin_dashboardDAO();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    // Menampilkan homeform pertama kali dan menyembunyikan form lainnya
    showHome();
}
    public void loadBibitDataToTable() {
        // Set up columns
        bibit.setCellValueFactory(new PropertyValueFactory<>("bibit"));
        supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        harga.setCellValueFactory(new PropertyValueFactory<>("harga"));

        // Load data
        ObservableList<BibitData> data = dao.getAllBibitData();
        editdatadbibittabel.setItems(data);
    }
    
    private String username;

    public void setUsername(String username) {
        this.username = username;
        System.out.println("Logged in as: " + username);
    }

    @FXML
    private void gantiform(ActionEvent event) {
        if (event.getSource() == homebtn) {
            showHome();
        } else if (event.getSource() == editdatabtn) {
            showEditData();
        } else if (event.getSource() == lihatdatabtn) {
            showLihatData();
        }
    }
    
    // Menampilkan tampilan Home
private void showHome() {
    homeform.setVisible(true);  // Menampilkan form Home
    editdata.setVisible(false); // Menyembunyikan form Edit Data
    lihatdata.setVisible(false); // Menyembunyikan form Lihat Data
}

// Menampilkan tampilan Edit Data
private void showEditData() {
    homeform.setVisible(false); // Menyembunyikan form Home
    editdata.setVisible(true);  // Menampilkan form Edit Data
    lihatdata.setVisible(false); // Menyembunyikan form Lihat Data
}

// Menampilkan tampilan Lihat Data
private void showLihatData() {
    homeform.setVisible(false); // Menyembunyikan form Home
    editdata.setVisible(false); // Menyembunyikan form Edit Data
    lihatdata.setVisible(true); // Menampilkan form Lihat Data
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
                // Load login page
                URL url = new File("src/main/java/View/loginpage.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load(); // Load the FXML and get the root node

                Stage currentStage = (Stage) logoutbtn.getScene().getWindow();
                currentStage.close(); // Close current dashboard stage

                Stage loginStage = new Stage();
                loginStage.setScene(new Scene(root));
                loginStage.initStyle(StageStyle.DECORATED);
                loginStage.show();
            } catch (IOException e) {
            }
        }
    } 
}