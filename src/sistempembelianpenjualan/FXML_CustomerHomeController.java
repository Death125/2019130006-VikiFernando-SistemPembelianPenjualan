/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistempembelianpenjualan;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sistemlogin.DB_Login;
import sistemlogin.LoginMenuController;

/**
 * FXML Controller class
 *
 *
 */
public class FXML_CustomerHomeController implements Initializable {

    private Stage stage;
    private Scene scene;

    private DB_Login ldb = new DB_Login();

    @FXML
    private Button btnMasuk;
    @FXML
    private Button btnKembali;
    @FXML
    private Label customerTitle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButtonStyle();
        validasiUsername();
    }

    void addButtonStyle() {
        btnMasuk.getStyleClass().add("buttonStyle2");
        btnKembali.getStyleClass().add("buttonStyle3");
    }

    void validasiUsername() {
        if (ldb.getUser(LoginMenuController.Username).equals("buku")) {
            customerTitle.setText(ldb.getUser(FXML_PilihCustomerController.user).toUpperCase() + "'S" + " STORE");
        } else if (ldb.getUser(LoginMenuController.Username).equals("elektronik")) {
            customerTitle.setText(ldb.getUser(FXML_PilihCustomerController.user).toUpperCase() + "'S" + " STORE");
        } else {
            customerTitle.setText(ldb.getUser(LoginMenuController.Username.toUpperCase()) + "'S" + " STORE");
        }
    }

    @FXML
    private void masukKlik(ActionEvent event) throws IOException {
        if (ldb.getUser(LoginMenuController.Username).equals("buku") && FXML_MenuController.dtBarang.validasiJualBuku(ldb.getUser(FXML_PilihCustomerController.user)) <= 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Toko ini tidak menjual jenis barang yang anda inginkan");
            a.showAndWait();
        } else if (ldb.getUser(LoginMenuController.Username).equals("elektronik") && FXML_MenuController.dtBarang.validasiJualBarangElektronik(ldb.getUser(FXML_PilihCustomerController.user)) <= 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Toko ini tidak menjual jenis barang yang anda inginkan");
            a.showAndWait();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("FXML_DisplayBarang.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Toko " + ldb.getUser(FXML_PilihCustomerController.user));
            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            String css2 = this.getClass().getResource("/Css/tablestyle.css").toExternalForm();
            scene.getStylesheets().add(css2);
            stage.centerOnScreen();
            stage.show();
        }
    }

    @FXML
    private void kembaliKlik(ActionEvent event) throws IOException {
        btnKembali.getScene().getWindow().hide();
    }

}
