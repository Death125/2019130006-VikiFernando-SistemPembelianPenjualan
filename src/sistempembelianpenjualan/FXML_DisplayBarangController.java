/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistempembelianpenjualan;

import transaksi.FXML_TransaksiController;
import sistemlogin.DB_Login;
import sistemlogin.LoginMenuController;
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
public class FXML_DisplayBarangController implements Initializable {

    private final DB_Login ldb = new DB_Login();
    private ObservableList<BarangModel> data;

    public static boolean batalBeli = false;

    @FXML
    private TableView<BarangModel> tbvBarang;
    @FXML
    private Label title;
    @FXML
    private Button btnKeluar;
    @FXML
    private TextField searchData;
    @FXML
    private Button addButton;
    @FXML
    private Button UpdateButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button btnBeli;
    @FXML
    private Button btnJual;
    @FXML
    private Button btnTambahBarang;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnBeli.getStyleClass().add("buttonStyle2");
        btnJual.getStyleClass().add("buttonStyle2");
        btnKeluar.getStyleClass().add("buttonStyle3");
        btnTambahBarang.getStyleClass().add("buttonStyle2");

        checkLogin();
        showData();
    }

    public void checkQuantityOfItems() {
        switch (DB_Login.getUser(LoginMenuController.Username)) {
            case "buku":
                if (FXML_MenuController.dtBarang.getQuantity(ldb.getUser(FXML_PilihCustomerController.user)) <= 0) {
                    FXML_MenuController.dtBarang.deleteBarangBasedOnQuantity(ldb.getUser(FXML_PilihCustomerController.user));
                }
                break;
            case "elektronik":
                if (FXML_MenuController.dtBarang.getQuantity(ldb.getUser(FXML_PilihCustomerController.user)) <= 0) {
                    FXML_MenuController.dtBarang.deleteBarangBasedOnQuantity(ldb.getUser(FXML_PilihCustomerController.user));
                }
                break;
            default:
                if (FXML_MenuController.dtBarang.getQuantity(ldb.getUser(LoginMenuController.Username)) <= 0) {
                    FXML_MenuController.dtBarang.deleteBarangBasedOnQuantity(ldb.getUser(LoginMenuController.Username));
                }
                break;
        }
    }

    public void checkLogin() {
        switch (DB_Login.getUser(LoginMenuController.Username)) {
            case "buku":
                addButton.setVisible(false);
                UpdateButton.setVisible(false);
                DeleteButton.setVisible(false);
                btnBeli.setVisible(true);
                btnJual.setVisible(true);
                btnTambahBarang.setVisible(false);
                title.setText("Toko " + DB_Login.getUser(FXML_PilihCustomerController.user));

                break;
            case "elektronik":
                addButton.setVisible(false);
                UpdateButton.setVisible(false);
                DeleteButton.setVisible(false);
                btnBeli.setVisible(true);
                btnJual.setVisible(true);
                btnTambahBarang.setVisible(false);
                title.setText("Toko " + DB_Login.getUser(FXML_PilihCustomerController.user));

                break;
            default:
                addButton.setVisible(true);
                UpdateButton.setVisible(true);
                DeleteButton.setVisible(true);
                btnBeli.setVisible(false);
                btnJual.setVisible(false);
                btnTambahBarang.setVisible(false);

                break;
        }
    }

    public void showData() {
        switch (ldb.getUser(LoginMenuController.Username)) {
            case "buku":
                data = FXML_MenuController.dtBarang.LoadBuku(ldb.getUser(FXML_PilihCustomerController.user));
                break;
            case "elektronik":
                data = FXML_MenuController.dtBarang.LoadElektronik(ldb.getUser(FXML_PilihCustomerController.user));
                break;
            default:
                data = FXML_MenuController.dtBarang.Load(ldb.getUser(LoginMenuController.Username));
                break;
        }

        if (data != null) {
            tbvBarang.getColumns().clear();
            tbvBarang.getItems().clear();

            TableColumn col = new TableColumn("Kode Barang");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, String>("kodebrg"));
            tbvBarang.getColumns().addAll(col);

            col = new TableColumn("List Barang");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, String>("namabrg"));
            tbvBarang.getColumns().addAll(col);

            col = new TableColumn("jenis");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, String>("jenis"));
            tbvBarang.getColumns().addAll(col);

            col = new TableColumn("harga");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, Double>("harga"));
            tbvBarang.getColumns().addAll(col);

            col = new TableColumn("jumlah");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, Integer>("jumlah"));
            tbvBarang.getColumns().addAll(col);

            tbvBarang.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();
            tbvBarang.getScene().getWindow().hide();
        }
    }

    @FXML
    private void tambahKlik(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_InputBarang.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showData();
        awalKlik(event);
    }

    @FXML
    private void updateKlik(ActionEvent event) {
        BarangModel bm = new BarangModel();
        bm = tbvBarang.getSelectionModel().getSelectedItem();

        if (bm != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_InputBarang.fxml"));
                Parent root = (Parent) loader.load();
                FXML_InputBarangController isidt = (FXML_InputBarangController) loader.getController();
                isidt.execute(bm);
                Scene scene = new Scene(root);
                Stage stg = new Stage();
                stg.initModality(Modality.APPLICATION_MODAL);
                stg.setIconified(false);
                stg.setScene(scene);
                stg.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }

            showData();
            awalKlik(event);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Pilih dahulu barang yang ingin anda update", ButtonType.YES, ButtonType.NO);
            a.showAndWait();
        }
    }

    @FXML
    private void hapusKlik(ActionEvent event) {
        BarangModel bm = new BarangModel();
        bm = tbvBarang.getSelectionModel().getSelectedItem();

        if (bm != null) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus?", ButtonType.YES, ButtonType.NO);
            a.showAndWait();

            if (a.getResult() == ButtonType.YES) {
                if (FXML_MenuController.dtBarang.delete(bm.getKodebrg(), ldb.getUser(LoginMenuController.Username))) {
                    Alert b = new Alert(Alert.AlertType.INFORMATION, "Data berhasil dihapus", ButtonType.OK);
                    b.showAndWait();
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
    private void beliKlik(ActionEvent event) {
        BarangModel bm = new BarangModel();
        bm = tbvBarang.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transaksi/FXML_Transaksi.fxml"));
            Parent root = (Parent) loader.load();
            if (bm == null) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Pilih Barang yang ingin anda beli terlebih dahulu");
                a.showAndWait();
            } else {
                FXML_TransaksiController isidt = (FXML_TransaksiController) loader.getController();
                isidt.tampilkanBarangYangDibeli(bm);

                Scene scene = new Scene(root);
                Stage stg = new Stage();
                stg.setTitle("Transaksi");
                stg.initModality(Modality.APPLICATION_MODAL);
                stg.setIconified(false);
                stg.setScene(scene);
                String css = this.getClass().getResource("/Css/style.css").toExternalForm();
                scene.getStylesheets().add(css);
                stg.centerOnScreen();
                stg.showAndWait();
                btnBeli.setVisible(false);
                btnJual.setVisible(false);
                btnTambahBarang.setVisible(true);

                if (batalBeli == true) {
                    btnBeli.setVisible(true);
                    btnJual.setVisible(true);
                    btnTambahBarang.setVisible(false);
                    batalBeli = false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        checkQuantityOfItems();
        showData();
        awalKlik(event);
    }

    @FXML
    private void jualKlik(ActionEvent event) {

    }

    @FXML
    private void tambahBarangKlik(ActionEvent event) {
        BarangModel bm = new BarangModel();
        bm = tbvBarang.getSelectionModel().getSelectedItem();

        if (bm != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/transaksi/FXML_Transaksi.fxml"));
                Parent root = (Parent) loader.load();
                if (bm == null) {
                    Alert a = new Alert(Alert.AlertType.WARNING, "Pilih Barang yang ingin anda beli terlebih dahulu");
                    a.showAndWait();
                } else {
                    FXML_TransaksiController isidt = (FXML_TransaksiController) loader.getController();
                    isidt.tampilkanBarangYangDibeli(bm);

                    Scene scene = new Scene(root);
                    Stage stg = new Stage();
                    stg.setTitle("Transaksi");
                    stg.initModality(Modality.APPLICATION_MODAL);
                    stg.setIconified(false);
                    stg.setScene(scene);
                    String css = this.getClass().getResource("/Css/style.css").toExternalForm();
                    scene.getStylesheets().add(css);
                    stg.centerOnScreen();
                    stg.showAndWait();
                    btnBeli.setVisible(false);
                    btnJual.setVisible(false);
                    btnTambahBarang.setVisible(true);

                    if (batalBeli == true) {
                        btnBeli.setVisible(true);
                        btnJual.setVisible(true);
                        btnTambahBarang.setVisible(false);
                        batalBeli = false;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            checkQuantityOfItems();
            showData();
            awalKlik(event);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Pilih Barang yang ingin anda beli terlebih dahulu");
            a.showAndWait();
        }

    }

    @FXML
    private void awalKlik(ActionEvent event) {
        tbvBarang.getSelectionModel().selectFirst();
        tbvBarang.requestFocus();
    }

    @FXML
    private void akhirKlik(ActionEvent event) {
        tbvBarang.getSelectionModel().selectLast();
        tbvBarang.requestFocus();
    }

    @FXML
    private void sebelumKlik(ActionEvent event) {
        tbvBarang.getSelectionModel().selectAboveCell();
        tbvBarang.requestFocus();
    }

    @FXML
    private void sesudahKlik(ActionEvent event) {
        tbvBarang.getSelectionModel().selectBelowCell();
        tbvBarang.requestFocus();
    }

    @FXML
    private void keluarKlik(ActionEvent event) {
        btnKeluar.getScene().getWindow().hide();
        FXML_MenuController.dtTransaksi.deleteAll();
    }

    @FXML
    private void cariData(KeyEvent event) {
        BarangModel bm = new BarangModel();
        String key = searchData.getText();
        if (key != "") {

            switch (ldb.getUser(LoginMenuController.Username)) {
                case "buku":
                    data = FXML_MenuController.dtBarang.CariBrg(key, key, ldb.getUser(FXML_PilihCustomerController.user));
                    break;
                case "elektronik":
                    data = FXML_MenuController.dtBarang.CariBrg(key, key, ldb.getUser(FXML_PilihCustomerController.user));
                    break;
                default:
                    data = FXML_MenuController.dtBarang.CariBrg(key, key, ldb.getUser(LoginMenuController.Username));
                    break;
            }
            if (data != null) {
                tbvBarang.getColumns().clear();
                tbvBarang.getItems().clear();

                TableColumn col = new TableColumn("kodebrg");
                col.setCellValueFactory(new PropertyValueFactory<BarangModel, String>("kodebrg"));
                tbvBarang.getColumns().addAll(col);

                col = new TableColumn("namabrg");
                col.setCellValueFactory(new PropertyValueFactory<BarangModel, String>("namabrg"));
                tbvBarang.getColumns().addAll(col);

                col = new TableColumn("jenis");
                col.setCellValueFactory(new PropertyValueFactory<BarangModel, String>("jenis"));
                tbvBarang.getColumns().addAll(col);

                col = new TableColumn("harga");
                col.setCellValueFactory(new PropertyValueFactory<BarangModel, Double>("harga"));
                tbvBarang.getColumns().addAll(col);

                col = new TableColumn("jumlah");
                col.setCellValueFactory(new PropertyValueFactory<BarangModel, Integer>("jumlah"));
                tbvBarang.getColumns().addAll(col);

                tbvBarang.setItems(data);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
                a.showAndWait();
                tbvBarang.getScene().getWindow().hide();;
            }
        } else {
            showData();
        }
    }

}
