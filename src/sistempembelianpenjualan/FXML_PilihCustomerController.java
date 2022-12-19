/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistempembelianpenjualan;

import sistemlogin.DB_Login;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 *
 */
public class FXML_PilihCustomerController implements Initializable {

    final DB_Login ldb = new DB_Login();
    public static String user = "";

    @FXML
    private TableView<CustomerModel> tbvPilihCustomer;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnSesudah;
    @FXML
    private Button btnSebelum;
    @FXML
    private Button btnAkhir;
    @FXML
    private Button btnAwal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButtonStyle();
        showData();
    }

    void addButtonStyle() {
        btnConfirm.getStyleClass().add("buttonStyle");
        btnSesudah.getStyleClass().add("buttonStyle");
        btnSebelum.getStyleClass().add("buttonStyle");
        btnAwal.getStyleClass().add("buttonStyle");
        btnAkhir.getStyleClass().add("buttonStyle");
    }

    public void showData() {
        ObservableList<CustomerModel> data = FXML_MenuController.dtCustomer.Load();

        if (data != null) {
            tbvPilihCustomer.getColumns().clear();
            tbvPilihCustomer.getItems().clear();

            TableColumn col = new TableColumn("List Toko");
            col.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("Username"));
            tbvPilihCustomer.getColumns().addAll(col);

            tbvPilihCustomer.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();
            tbvPilihCustomer.getScene().getWindow().hide();;
        }
    }

    public void execute(CustomerModel cm) {
        user = cm.getUsername();
    }

    @FXML
    private void confirmKlik(ActionEvent event) throws IOException {
        CustomerModel cm = new CustomerModel();
        cm = tbvPilihCustomer.getSelectionModel().getSelectedItem();

        execute(cm);

        Parent root = FXMLLoader.load(getClass().getResource("FXML_Menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menu");

        String css = this.getClass().getResource("/Css/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.show();
        stage.centerOnScreen();
        JOptionPane.showMessageDialog(null, "Selamat Datang di Toko " + ldb.getUser(FXML_PilihCustomerController.user));
    }

    @FXML
    private void sebelumKlik(ActionEvent event) {
        tbvPilihCustomer.getSelectionModel().selectAboveCell();
        tbvPilihCustomer.requestFocus();
    }

    @FXML
    private void sesudahKlik(ActionEvent event) {
        tbvPilihCustomer.getSelectionModel().selectBelowCell();
        tbvPilihCustomer.requestFocus();
    }

    @FXML
    private void awalKlik(ActionEvent event) {
        tbvPilihCustomer.getSelectionModel().selectFirst();
        tbvPilihCustomer.requestFocus();
    }

    @FXML
    private void akhirKlik(ActionEvent event) {
        tbvPilihCustomer.getSelectionModel().selectLast();
        tbvPilihCustomer.requestFocus();
    }

}
