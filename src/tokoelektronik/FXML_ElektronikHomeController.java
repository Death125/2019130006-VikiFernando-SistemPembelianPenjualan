/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tokoelektronik;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sistemlogin.DB_Login;

/**
 * FXML Controller class
 *
 *
 */
public class FXML_ElektronikHomeController implements Initializable {

    private Stage stage;
    private Scene scene;

    private DB_Login ldb = new DB_Login();

    @FXML
    private Button btnMasuk;
    @FXML
    private Button btnKembali;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButtonStyle();
    }

    void addButtonStyle() {
        btnMasuk.getStyleClass().add("buttonStyle2");
        btnKembali.getStyleClass().add("buttonStyle3");
    }

    @FXML
    private void masukKlik(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXML_DisplayBarang_elektronik.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ringo's Store");
        String css = this.getClass().getResource("/Css/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        String css2 = this.getClass().getResource("/Css/tablestyle.css").toExternalForm();
        scene.getStylesheets().add(css2);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void kembaliKlik(ActionEvent event) throws IOException {
        btnKembali.getScene().getWindow().hide();
    }

}
