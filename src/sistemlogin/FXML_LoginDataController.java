/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemlogin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 *
 */
public class FXML_LoginDataController implements Initializable {

    @FXML
    private TableView<LoginDataModel> tbvLogin;
    @FXML
    private Button UpdateButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button btnKeluar;
    @FXML
    private TextField searchData;
    @FXML
    private Label title;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButtonStyle();
        showData();
    }

    void addButtonStyle() {
        btnKeluar.getStyleClass().add("buttonStyle3");
    }

    public void showData() {
        ObservableList<LoginDataModel> data = FXML_AdminController.dtLogin.Load();
        if (data != null) {
            tbvLogin.getColumns().clear();
            tbvLogin.getItems().clear();

            TableColumn col = new TableColumn("Username");
            col.setCellValueFactory(new PropertyValueFactory<LoginDataModel, String>("Username"));
            tbvLogin.getColumns().addAll(col);

            col = new TableColumn("No Telepon");
            col.setCellValueFactory(new PropertyValueFactory<LoginDataModel, String>("notelp"));
            tbvLogin.getColumns().addAll(col);

            col = new TableColumn("Email");
            col.setCellValueFactory(new PropertyValueFactory<LoginDataModel, String>("Email"));
            tbvLogin.getColumns().addAll(col);

            col = new TableColumn("Password");
            col.setCellValueFactory(new PropertyValueFactory<LoginDataModel, String>("Password"));
            tbvLogin.getColumns().addAll(col);

            tbvLogin.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();

            tbvLogin.getScene().getWindow().hide();
        }
    }

    @FXML
    private void sebelumKlik(ActionEvent event) {
        tbvLogin.getSelectionModel().selectAboveCell();
        tbvLogin.requestFocus();
    }

    @FXML
    private void awalKlik(ActionEvent event) {
        tbvLogin.getSelectionModel().selectFirst();
        tbvLogin.requestFocus();
    }

    @FXML
    private void sesudahKlik(ActionEvent event) {
        tbvLogin.getSelectionModel().selectBelowCell();
        tbvLogin.requestFocus();
    }

    @FXML
    private void akhirKlik(ActionEvent event) {
        tbvLogin.getSelectionModel().selectLast();
        tbvLogin.requestFocus();
    }

    @FXML
    private void updateKlik(ActionEvent event) {
        LoginDataModel ldm = new LoginDataModel();
        ldm = tbvLogin.getSelectionModel().getSelectedItem();

        if (ldm != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_InputLoginData.fxml"));
                Parent root = (Parent) loader.load();

                FXML_InputLoginDataController isidt = (FXML_InputLoginDataController) loader.getController();
                isidt.execute(ldm);
                Scene scene = new Scene(root);
                Stage stg = new Stage();
                stg.initModality(Modality.APPLICATION_MODAL);
                stg.setIconified(false);
                stg.setScene(scene);

                String css = this.getClass().getResource("/Css/style.css").toExternalForm();
                scene.getStylesheets().add(css);

                stg.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }

            showData();
            awalKlik(event);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Pilih dahulu barang yang ingin update", ButtonType.YES, ButtonType.NO);
            a.showAndWait();
        }
    }

    @FXML
    private void hapusKlik(ActionEvent event) {
        LoginDataModel ldm = new LoginDataModel();
        ldm = tbvLogin.getSelectionModel().getSelectedItem();

        if (ldm.getUsername().toLowerCase().equals("buku") || ldm.getUsername().toLowerCase().equals("elektronik")) {
            Alert b = new Alert(Alert.AlertType.WARNING, "Akun ini tidak bisa anda hapus", ButtonType.OK);
            b.showAndWait();
        } else if (ldm != null && !ldm.getUsername().toLowerCase().equals("buku") || !ldm.getUsername().toLowerCase().equals("elektronik")) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus?", ButtonType.YES, ButtonType.NO);
            a.showAndWait();

            if (a.getResult() == ButtonType.YES) {
                if (FXML_AdminController.dtLogin.delete(ldm.getUsername().toLowerCase())) {
                    if (FXML_AdminController.dtLogin.deleteTable(ldm.getUsername().toLowerCase())) {
                        if (FXML_AdminController.dtLogin.deleteDetil(ldm.getUsername().toLowerCase())) {
                            FXML_AdminController.dtLogin.deletePenjualan(ldm.getUsername().toLowerCase());
                            Alert b = new Alert(Alert.AlertType.INFORMATION, "Data berhasil dihapus", ButtonType.OK);
                            b.showAndWait();
                        }
                    }
                } else {
                    Alert b = new Alert(Alert.AlertType.ERROR, "Data gagal dihapus", ButtonType.OK);
                    b.showAndWait();
                }

                showData();
                awalKlik(event);
            }
        } else {
            Alert b = new Alert(Alert.AlertType.WARNING, "Pilih data yang ingin anda hapus", ButtonType.OK);
            b.showAndWait();
        }
    }

    @FXML
    private void keluarKlik(ActionEvent event) {
        btnKeluar.getScene().getWindow().hide();
    }

    @FXML
    private void cariData(KeyEvent event) {
        LoginDataModel ldm = new LoginDataModel();
        String key = searchData.getText();

        if (key != "") {
            ObservableList<LoginDataModel> data = FXML_AdminController.dtLogin.CariBrg(key, key);
            if (data != null) {
                tbvLogin.getColumns().clear();
                tbvLogin.getItems().clear();

                TableColumn col = new TableColumn("Username");
                col.setCellValueFactory(new PropertyValueFactory<LoginDataModel, String>("Username"));
                tbvLogin.getColumns().addAll(col);

                col = new TableColumn("No Telepon");
                col.setCellValueFactory(new PropertyValueFactory<LoginDataModel, String>("notelp"));
                tbvLogin.getColumns().addAll(col);

                col = new TableColumn("Email");
                col.setCellValueFactory(new PropertyValueFactory<LoginDataModel, String>("Email"));
                tbvLogin.getColumns().addAll(col);

                col = new TableColumn("Password");
                col.setCellValueFactory(new PropertyValueFactory<LoginDataModel, String>("Password"));
                tbvLogin.getColumns().addAll(col);

                tbvLogin.setItems(data);

            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
                a.showAndWait();

                tbvLogin.getScene().getWindow().hide();;
            }
        } else {
            showData();
        }
    }

}
