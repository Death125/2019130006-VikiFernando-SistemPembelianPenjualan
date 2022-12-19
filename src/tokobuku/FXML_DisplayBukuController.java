/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tokobuku;

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
import sistempembelianpenjualan.FXML_MenuController;
import sistemlogin.DB_Login;
import sistemlogin.LoginMenuController;
import sistempembelianpenjualan.FXML_DisplayBarangController;
import tokoelektronik.FXML_DisplayBarang_elektronikController;
import transaksi.FXML_InputKeranjangController;
import transaksi.FXML_KeranjangController;
import transaksi.FXML_TransaksiController;

/**
 * FXML Controller class
 *
 *
 */
public class FXML_DisplayBukuController implements Initializable {

    final DB_Login ldb = new DB_Login();

    public static boolean tokoBuku;
    public static boolean isJualBuku = false;
    public static boolean batalBeli = false;
    public static boolean sudahBeliJual = false;

    @FXML
    private Label title;
    @FXML
    private TextField searchData;
    @FXML
    private TableView<BukuModel> tbvBuku;
    @FXML
    private Button btnKeluar;
    @FXML
    private Button AddButton;
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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButtonStyle();
        checkLogin();

        validationIsBukuStore();
        jualanBarang();

        showData();
    }

    void addButtonStyle() {
        btnBeli.getStyleClass().add("buttonStyle2");
        btnJual.getStyleClass().add("buttonStyle2");
        btnKeluar.getStyleClass().add("buttonStyle2");
        btnTambahBarang.getStyleClass().add("buttonStyle2");
    }

    /*Validasi*/
    void validationIsBukuStore() {
        tokoBuku = true;
        FXML_DisplayBarang_elektronikController.tokoElektronik = false;
    }

    public void checkQuantityOfItems() {
        if (FXML_MenuController.dtBuku.getQuantity() <= 0) {
            FXML_MenuController.dtBuku.deleteBarangBasedOnQuantity();
        }
    }

    public void jualanBarang() {
        if (FXML_DisplayBarangController.customer == true && isJualBuku == true) {
            AddButton.setVisible(false);
            UpdateButton.setVisible(false);
            DeleteButton.setVisible(false);
            btnTambahBarang.setVisible(true);
            btnTambahBarang.setText("Jual");
        }
    }

    public void checkLogin() {
        if (ldb.getUser(LoginMenuController.Username).equals("buku")) {
            AddButton.setVisible(true);
            UpdateButton.setVisible(true);
            DeleteButton.setVisible(true);
            btnBeli.setVisible(false);
            btnJual.setVisible(false);
            btnTambahBarang.setVisible(false);

        } else if (ldb.getUser(LoginMenuController.Username).equals("elektronik")) {
            AddButton.setVisible(false);
            UpdateButton.setVisible(false);
            DeleteButton.setVisible(false);
            btnBeli.setVisible(true);
            btnJual.setVisible(true);
            title.setText("Toko Buku");
            btnTambahBarang.setVisible(false);

        } else {
            AddButton.setVisible(false);
            UpdateButton.setVisible(false);
            DeleteButton.setVisible(false);
            btnBeli.setVisible(true);
            btnJual.setVisible(true);
            title.setText("Toko Buku");
            btnTambahBarang.setVisible(false);
        }
    }

    /**/
    public void showData() {
        ObservableList<BukuModel> data = FXML_MenuController.dtBuku.Load();
        if (data != null) {
            tbvBuku.getColumns().clear();
            tbvBuku.getItems().clear();

            TableColumn col = new TableColumn("Kode Barang");
            col.setCellValueFactory(new PropertyValueFactory<BukuModel, String>("kodebrg"));
            tbvBuku.getColumns().addAll(col);

            col = new TableColumn("List Buku");
            col.setCellValueFactory(new PropertyValueFactory<BukuModel, String>("namabrg"));
            tbvBuku.getColumns().addAll(col);

            col = new TableColumn("jenis");
            col.setCellValueFactory(new PropertyValueFactory<BukuModel, String>("jenis"));
            tbvBuku.getColumns().addAll(col);

            col = new TableColumn("harga");
            col.setCellValueFactory(new PropertyValueFactory<BukuModel, Double>("harga"));
            tbvBuku.getColumns().addAll(col);

            col = new TableColumn("jumlah");
            col.setCellValueFactory(new PropertyValueFactory<BukuModel, Integer>("jumlah"));
            tbvBuku.getColumns().addAll(col);

            tbvBuku.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();

            tbvBuku.getScene().getWindow().hide();
        }
    }

    @FXML
    private void cariData(KeyEvent event) {
        BukuModel bum = new BukuModel();
        String key = searchData.getText();

        if (key != "") {
            ObservableList<BukuModel> data = FXML_MenuController.dtBuku.CariBrg(key, key);
            if (data != null) {
                tbvBuku.getColumns().clear();
                tbvBuku.getItems().clear();

                TableColumn col = new TableColumn("kodebrg");
                col.setCellValueFactory(new PropertyValueFactory<BukuModel, String>("kodebrg"));
                tbvBuku.getColumns().addAll(col);

                col = new TableColumn("namabrg");
                col.setCellValueFactory(new PropertyValueFactory<BukuModel, String>("namabrg"));
                tbvBuku.getColumns().addAll(col);

                col = new TableColumn("jenis");
                col.setCellValueFactory(new PropertyValueFactory<BukuModel, String>("jenis"));
                tbvBuku.getColumns().addAll(col);

                col = new TableColumn("harga");
                col.setCellValueFactory(new PropertyValueFactory<BukuModel, Double>("harga"));
                tbvBuku.getColumns().addAll(col);

                col = new TableColumn("jumlah");
                col.setCellValueFactory(new PropertyValueFactory<BukuModel, Integer>("jumlah"));
                tbvBuku.getColumns().addAll(col);

                tbvBuku.setItems(data);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
                a.showAndWait();

                tbvBuku.getScene().getWindow().hide();;
            }
        } else {
            showData();
        }
    }

    @FXML
    private void tambahKlik(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_InputBuku.fxml"));
            Parent root = (Parent) loader.load();

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
    }

    @FXML
    private void updateKlik(ActionEvent event) {
        BukuModel bum = new BukuModel();
        bum = tbvBuku.getSelectionModel().getSelectedItem();

        if (bum != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_InputBuku.fxml"));
                Parent root = (Parent) loader.load();

                FXML_InputBukuController isidt = (FXML_InputBukuController) loader.getController();
                isidt.execute(bum);
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
        BukuModel bum = new BukuModel();
        bum = tbvBuku.getSelectionModel().getSelectedItem();

        if (bum != null) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus?", ButtonType.YES, ButtonType.NO);
            a.showAndWait();

            if (a.getResult() == ButtonType.YES) {
                if (FXML_MenuController.dtBuku.delete(bum.getKodebrg())) {
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
            Alert b = new Alert(Alert.AlertType.WARNING, "Pilih dahulu barang yang ingin anda hapus", ButtonType.OK);
            b.showAndWait();
        }

    }

    @FXML
    private void beliKlik(ActionEvent event) {
        batalBeli = true;

        FXML_KeranjangController.isBuy = true;

        FXML_MenuController.isCustomer = false;
        FXML_MenuController.isBuku = true;

        FXML_DisplayBarangController.jualBarang = false;
        FXML_InputKeranjangController.isUpdateJual = false;
        FXML_TransaksiController.tambahKeKeranjangJ = false;

        BukuModel bum = new BukuModel();
        bum = tbvBuku.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transaksi/FXML_Transaksi.fxml"));
            Parent root = (Parent) loader.load();
            if (bum == null) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Pilih Barang yang ingin anda beli terlebih dahulu");
                a.showAndWait();
            } else {
                FXML_TransaksiController isidt = (FXML_TransaksiController) loader.getController();
                isidt.tampilkanBukuYangDibeli(bum);

                Scene scene = new Scene(root);
                Stage stg = new Stage();
                stg.setTitle("Transaksi");
                stg.initModality(Modality.APPLICATION_MODAL);
                stg.setIconified(false);
                stg.setScene(scene);

                String css = this.getClass().getResource("/Css/style.css").toExternalForm();
                scene.getStylesheets().add(css);

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
    private void jualKlik(ActionEvent event) throws IOException {

        FXML_DisplayBarangController.jualBarang = true;
        FXML_InputKeranjangController.isUpdateJual = true;
        FXML_KeranjangController.isBuy = false;

        FXML_MenuController.isCustomer = true;
        FXML_MenuController.isBuku = false;

        FXML_TransaksiController.tambahKeKeranjangJ = true;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistempembelianpenjualan/FXML_DisplayBarang.fxml"));
        Parent root = (Parent) loader.load();

        Scene scene = new Scene(root);
        Stage stg = new Stage();
        stg.initModality(Modality.APPLICATION_MODAL);
        stg.setIconified(false);
        stg.setScene(scene);

        String css = this.getClass().getResource("/Css/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        String css2 = this.getClass().getResource("/Css/tablestyle.css").toExternalForm();
        scene.getStylesheets().add(css2);

        stg.showAndWait();

        checkQuantityOfItems();
        showData();
        awalKlik(event);
    }

    @FXML
    private void tambahBarangKlik(ActionEvent event) {
        batalBeli = false;

        BukuModel bum = new BukuModel();
        bum = tbvBuku.getSelectionModel().getSelectedItem();

        if (bum != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/transaksi/FXML_Transaksi.fxml"));
                Parent root = (Parent) loader.load();
                if (bum == null) {
                    Alert a = new Alert(Alert.AlertType.WARNING, "Pilih Barang yang ingin anda beli terlebih dahulu");
                    a.showAndWait();
                } else {
                    FXML_TransaksiController isidt = (FXML_TransaksiController) loader.getController();
                    isidt.tampilkanBukuYangDibeli(bum);

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
                    } else if (isJualBuku == true) {
                        if (sudahBeliJual == true) {
                            sudahBeliJual = false;
                            btnKeluar.getScene().getWindow().hide();
                        }
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
    private void sebelumKlik(ActionEvent event) {
        tbvBuku.getSelectionModel().selectAboveCell();
        tbvBuku.requestFocus();
    }

    @FXML
    private void sesudahKlik(ActionEvent event) {
        tbvBuku.getSelectionModel().selectBelowCell();
        tbvBuku.requestFocus();
    }

    @FXML
    private void awalKlik(ActionEvent event) {
        tbvBuku.getSelectionModel().selectFirst();
        tbvBuku.requestFocus();
    }

    @FXML
    private void akhirKlik(ActionEvent event) {
        tbvBuku.getSelectionModel().selectLast();
        tbvBuku.requestFocus();
    }

    @FXML
    private void keluarKlik(ActionEvent event) {
        FXML_DisplayBarangController.jualBarang = false;
        isJualBuku = false;
        FXML_MenuController.dtTransaksi.deleteAll();

        btnKeluar.getScene().getWindow().hide();
    }
}
