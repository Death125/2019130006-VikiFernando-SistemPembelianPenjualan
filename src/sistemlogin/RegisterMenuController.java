/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemlogin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import sistempembelianpenjualan.Koneksi;

/**
 * FXML Controller class
 *
 *
 */
public class RegisterMenuController implements Initializable {

    private DB_Register rdb = new DB_Register();

    private Stage stage;
    private Scene scene;

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtRePass;
    @FXML
    private Label gradient1;
    @FXML
    private TextField txtNoTelepon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRegister.getStyleClass().add("buttonStyle");
        btnBack.getStyleClass().add("buttonStyle");
        txtUsername.requestFocus();

        restrictTxtNoTelp();
    }

    private void restrictTxtNoTelp() {
        // force the field to be numeric only
        txtNoTelepon.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtNoTelepon.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void registerKlik(ActionEvent event) throws IOException {
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String noTelp = txtNoTelepon.getText();
        String password = txtPassword.getText();
        String rePass = txtRePass.getText();

        boolean berhasil = false;
        Koneksi con = new Koneksi();

        //Cek jika username ataupun password kosong
        if (username.equals("") || email.equals("") || noTelp.equals("") || password.equals("") || rePass.equals("")) {
            Alert b = new Alert(Alert.AlertType.INFORMATION, "Please Fill In All Your Data First", ButtonType.OK);
            b.showAndWait();
        } else if (!(Pattern.matches("^[a-zA-Z0-9]+[@]{1}+[a-zA-Z0-9]+[.]{1}+[a-zA-Z0-9]+$", txtEmail.getText()))) {
            JOptionPane.showMessageDialog(null, "Please enter a valid email", "Error", JOptionPane.ERROR_MESSAGE); //validasi email
        } else if (!password.equals(rePass)) {
            Alert b = new Alert(Alert.AlertType.INFORMATION, "Your Password Doesn't Match", ButtonType.OK); //validasi password
            b.showAndWait();
        } else {
            //Menyimpan username dan password kedalam database
            try {
                if (rdb.validasiUsername(username) > 0) {
                    JOptionPane.showMessageDialog(null, "This username has been taken, please use another username");
                } else if (rdb.validasiEmail(email) > 0) {
                    JOptionPane.showMessageDialog(null, "This email has been used before, please use another email");
                } else {

                    con.bukaKoneksi();
                    con.preparedStatement = con.dbKoneksi.prepareStatement("INSERT INTO login_data(username, notelp, email, password) VALUES (?,?,?,?)");
                    con.preparedStatement.setString(1, username);
                    con.preparedStatement.setString(2, noTelp);
                    con.preparedStatement.setString(3, email);
                    con.preparedStatement.setString(4, password);

                    con.preparedStatement.executeUpdate();
                    berhasil = true;

                    rdb.CreateNewGoodsData(username);
                    Alert b = new Alert(Alert.AlertType.INFORMATION, "Your Account Has Been Created", ButtonType.OK);
                    b.showAndWait();

                    txtUsername.clear();
                    txtEmail.clear();
                    txtNoTelepon.clear();
                    txtPassword.clear();
                    txtRePass.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
                berhasil = false;
                Alert b = new Alert(Alert.AlertType.INFORMATION, "Register Failed", ButtonType.OK);
                b.showAndWait();
            }
        }
    }

    @FXML
    private void backKlik(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        String css = this.getClass().getResource("/Css/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.show();
    }

}
