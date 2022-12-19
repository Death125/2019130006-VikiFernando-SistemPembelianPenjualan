/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemlogin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 *
 */
public class FXML_InputLoginDataController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtNoTelepon;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnSimpan;
    @FXML
    private Button btnBatal;
    @FXML
    private Button btnKeluar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButtonStyle();
    }

    void addButtonStyle() {
        btnSimpan.getStyleClass().add("buttonStyle2");
        btnBatal.getStyleClass().add("buttonStyle2");
        btnKeluar.getStyleClass().add("buttonStyle2");
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

    public void execute(LoginDataModel ldm) {
        if (!ldm.getUsername().isEmpty()) {
            txtUsername.setText(ldm.getUsername());
            txtEmail.setText(ldm.getEmail());
            txtNoTelepon.setText(ldm.getNotelp());
            txtPassword.setText(ldm.getPassword());

            txtUsername.setEditable(false);
            txtEmail.setEditable(false);
            txtNoTelepon.requestFocus();
        }
    }

    @FXML
    private void simpanKlik(ActionEvent event) {
        LoginDataModel ldm = new LoginDataModel();
        ldm.setUsername(txtUsername.getText());
        ldm.setEmail(txtEmail.getText());
        ldm.setNotelp(txtNoTelepon.getText());
        ldm.setPassword(txtPassword.getText());

        FXML_AdminController.dtLogin.setLoginDataModel(ldm);

        if (FXML_AdminController.dtLogin.update()) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan", ButtonType.OK);
            a.showAndWait();

            batalKlik(event);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal disimpan", ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void batalKlik(ActionEvent event) {
        txtUsername.setText("");
        txtEmail.setText("");
        txtNoTelepon.setText("");
        txtPassword.setText("");
        txtNoTelepon.requestFocus();
    }

    @FXML
    private void keluarKlik(ActionEvent event) {
        btnKeluar.getScene().getWindow().hide();
    }

}
