package Controller;

import DAO.UserDAO;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class loginpageController implements Initializable {

    @FXML
    private AnchorPane main_form;
    @FXML
    private TextField usernamelogin;
    @FXML
    private PasswordField passwordlogin;
    @FXML
    private Button loginbtn;
    @FXML
    private Button close;
    @FXML
    private Button registerbtn;
    @FXML
    private AnchorPane loginform;
    @FXML
    private Button login;
    @FXML
    private AnchorPane registerform;
    @FXML
    private TextField usernamergs;
    @FXML
    private PasswordField passwordrgs;
    @FXML
    private PasswordField confirmpassword;
    @FXML
    private Button register;
    
    
    private double x = 0;
    private double y = 0;

    private final UserDAO userDAO = new UserDAO();

@FXML
public void loginAdmin(ActionEvent event) {
    String username = usernamelogin.getText();
    String password = passwordlogin.getText();

    Alert alert;

    if (username.isEmpty() || password.isEmpty()) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Tolong Penuhi Data yang Kosong");
        alert.showAndWait();
        return;
    }

    String role = userDAO.getUserRole(username, password);

    if (role != null) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Login Berhasil sebagai " + role);
        alert.showAndWait();

        try {
            // Tentukan file FXML berdasarkan peran pengguna
            String fxmlFile = role.equals("admin") ? "src/main/java/View/admin_dashboard.fxml" : "src/main/java/View/dashboard.fxml";

            // Load file FXML
            URL url = new File(fxmlFile).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            // Kirim data ke controller jika diperlukan
            if (role.equals("admin")) {
                Admin_dashboardController adminController = loader.getController();
                adminController.setUsername(username); // Kirim username ke AdminDashboardController
            } else {
                DashboardController userController = loader.getController();
                userController.setUsername(username); // Kirim username ke DashboardController
            }

            // Buat dan tampilkan stage baru
            Stage dashboardStage = new Stage();
            dashboardStage.setScene(new Scene(root));
            dashboardStage.initStyle(StageStyle.DECORATED);
            dashboardStage.show();

            // Tutup stage login
            Stage currentStage = (Stage) loginbtn.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    } else {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Username/Password Salah");
        alert.showAndWait();
    }
}


    @FXML
    public void switchToRegisterForm(ActionEvent event) {
        loginform.setVisible(false);
        registerform.setVisible(true);
    }

    @FXML
    public void switchToLoginForm(ActionEvent event) {
        registerform.setVisible(false);
        loginform.setVisible(true);
    }

    @FXML
    public void registerUser(ActionEvent event) {
        String username = usernamergs.getText();
        String password = passwordrgs.getText();
        String confirmPassword = confirmpassword.getText();

        Alert alert;

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Tolong isi semua data!");
            alert.showAndWait();
            return;
        }

        if (!password.equals(confirmPassword)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Password dan Konfirmasi Password tidak cocok!");
            alert.showAndWait();
            return;
        }

        boolean success = userDAO.registerUser(username, password, "pengguna");
        if (success) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Pendaftaran berhasil! Silakan login.");
            alert.showAndWait();

            switchToLoginForm(event);
            // Switch back to the login form
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Pendaftaran gagal! Username mungkin sudah terdaftar.");
            alert.showAndWait();
        }
    }

    @FXML
    public void closeApp(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginform.setVisible(true);
        registerform.setVisible(false);
    }
    
}

