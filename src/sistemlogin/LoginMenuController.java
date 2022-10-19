/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemlogin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
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
public class LoginMenuController implements Initializable {

    private DB_Login ldb = new DB_Login();

    private Stage stage;
    private Scene scene;
    public static String Username;
    public static String Email;

    @FXML
    private TextField txtUsernameOrEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
    @FXML
    private Label gradient1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnLogin.getStyleClass().add("buttonStyle");
        btnRegister.getStyleClass().add("buttonStyle");
    }

    @FXML
    private void loginKlik(ActionEvent event) {
        String Password = txtPassword.getText();
        ResultSet rs;

        try {
            if (txtUsernameOrEmail.getText().equals("buku") || txtUsernameOrEmail.getText().equals("elektronik")
                    || txtUsernameOrEmail.getText().equals("buku33@gmail.com") || txtUsernameOrEmail.getText().equals("elektronik33@gmail.com")) {
                Username = txtUsernameOrEmail.getText();
                Email = txtUsernameOrEmail.getText();

                Koneksi con = new Koneksi();
                con.bukaKoneksi();
                con.statement = con.dbKoneksi.createStatement();
                con.preparedStatement = con.dbKoneksi.prepareStatement("Select *from login_data WHERE( username= BINARY ? OR email = BINARY ?) AND password = BINARY?");

                con.preparedStatement.setString(1, Username);
                con.preparedStatement.setString(2, Email);
                con.preparedStatement.setString(3, Password);

                rs = con.preparedStatement.executeQuery();

                if (rs.next()) {

                    JOptionPane.showMessageDialog(null, "Login Success");

                    Parent root = FXMLLoader.load(getClass().getResource("/sistempembelianpenjualan/FXML_PilihCustomer.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Pilih Customer");
                    String css = this.getClass().getResource("/Css/style.css").toExternalForm();
                    scene.getStylesheets().add(css);
                    String css2 = this.getClass().getResource("/Css/tablestyle.css").toExternalForm();
                    scene.getStylesheets().add(css2);
                    stage.centerOnScreen();
                    stage.show();

                    //Saving Email & Password When Login
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("src\\\\SavedLogin\\\\LoginData.txt"));
                        writer.write(Username + "\n");
                        writer.write(Password);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, "Welcome " + ldb.getUser(Username));

                } else if (Username.equals("") && Password.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill in your username and password");
                } else if (ldb.validasiUsername(Username) <= 0) {
                    JOptionPane.showMessageDialog(null, "Username is not registered");
                } else if (ldb.validasiPassword(Password) <= 0) {
                    JOptionPane.showMessageDialog(null, "Your password is WRONG !");
                }
                con.tutupKoneksi();
            } else {
                Username = txtUsernameOrEmail.getText();
                Email = txtUsernameOrEmail.getText();

                Koneksi con = new Koneksi();
                con.bukaKoneksi();
                con.statement = con.dbKoneksi.createStatement();
                con.preparedStatement = con.dbKoneksi.prepareStatement("Select *from login_data WHERE( username= BINARY ? OR email = BINARY ?) AND password = BINARY?");

                con.preparedStatement.setString(1, Username);
                con.preparedStatement.setString(2, Email);
                con.preparedStatement.setString(3, Password);

                rs = con.preparedStatement.executeQuery();

                if (rs.next()) {

                    JOptionPane.showMessageDialog(null, "Login Success");

                    Parent root = FXMLLoader.load(getClass().getResource("/sistempembelianpenjualan/FXML_Menu.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Menu");
                    String css = this.getClass().getResource("/Css/style.css").toExternalForm();
                    scene.getStylesheets().add(css);
                    String css2 = this.getClass().getResource("/Css/tablestyle.css").toExternalForm();
                    scene.getStylesheets().add(css2);

                    stage.centerOnScreen();
                    stage.show();

                    //Saving Email & Password When Login
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("src\\\\SavedLogin\\\\LoginData.txt"));
                        writer.write(Username + "\n");
                        writer.write(Password);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, "Welcome " + ldb.getUser(Username));

                } else if (Username.equals("") && Password.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill in your username and password");
                } else if (ldb.validasiUsername(Username) <= 0) {
                    JOptionPane.showMessageDialog(null, "Username is not registered");
                } else if (ldb.validasiPassword(Password) <= 0) {
                    JOptionPane.showMessageDialog(null, "Your password is WRONG !");
                }
                con.tutupKoneksi();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void registerKlik(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("RegisterMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Register");
        String css = this.getClass().getResource("/Css/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.centerOnScreen();
        stage.show();
    }

}
