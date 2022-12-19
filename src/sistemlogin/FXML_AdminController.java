/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemlogin;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import sistempembelianpenjualan.FXML_DisplayBarangController;
import static sistempembelianpenjualan.FXML_MenuController.isBuku;
import static sistempembelianpenjualan.FXML_MenuController.isCustomer;
import static sistempembelianpenjualan.FXML_MenuController.isElektronik;

/**
 * FXML Controller class
 *
 *
 */
public class FXML_AdminController implements Initializable {

    @FXML
    private Button logoutButton;

    //Scene & JFrame
    JFrame f;
    private Stage stage;
    private Scene scene;

    public static DB_LoginData dtLogin = new DB_LoginData();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void loginDataKlik(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_LoginData.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Login Data");
            stg.initModality(Modality.APPLICATION_MODAL);
            // stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);

            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            String css2 = this.getClass().getResource("/Css/tablestyle.css").toExternalForm();
            scene.getStylesheets().add(css2);

            stg.centerOnScreen();
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logoutKlik(ActionEvent event) throws IOException {
        int jawab = JOptionPane.showOptionDialog(f,
                "Logout From This Account ?",
                "Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (jawab == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(f, "Logout berhasil");
            Parent root = FXMLLoader.load(getClass().getResource("/sistemlogin/LoginMenu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.centerOnScreen();
            stage.show();

            isBuku = false;
            isElektronik = false;
            isCustomer = false;
            FXML_DisplayBarangController.customer = true;
        }
    }

}
