/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package transaksi;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import sistemlogin.DB_Login;
import sistemlogin.LoginMenuController;
import sistempembelianpenjualan.FXML_DisplayBarangController;
import sistempembelianpenjualan.FXML_MenuController;
import sistempembelianpenjualan.FXML_PilihCustomerController;
import sistempembelianpenjualan.PenjualanModel;
import tokobuku.FXML_DisplayBukuController;
import tokobuku.PenjualanBukuModel;
import tokoelektronik.FXML_DisplayBarang_elektronikController;
import tokoelektronik.PenjualanBarangElektronikModel;

/**
 * FXML Controller class
 *
 *
 */
public class FXML_KeranjangController implements Initializable {

    public static boolean isBuy;

    private DB_Login ldb = new DB_Login();

    @FXML
    private TableView<TransaksiModel> tbvKeranjang;
    @FXML
    private Button btnBeli;
    @FXML
    private Button btnKeluar;
    @FXML
    private Button btnJual;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButtonStyle();

        validasiBeliJual();
        showData();
    }

    void addButtonStyle() {
        btnBeli.getStyleClass().add("buttonStyle2");
        btnJual.getStyleClass().add("buttonStyle2");
        btnKeluar.getStyleClass().add("buttonStyle3");
    }

    /*Validasi */
    public void validasiBeliJual() {
        if (isBuy == true) {
            btnBeli.setVisible(true);
            btnJual.setVisible(false);
        } else {
            btnJual.setVisible(true);
            btnBeli.setVisible(false);
        }
    }/**/

    public void showData() {
        ObservableList<TransaksiModel> data = FXML_MenuController.dtTransaksi.Load();

        if (data != null) {
            tbvKeranjang.getColumns().clear();
            tbvKeranjang.getItems().clear();

            TableColumn col = new TableColumn("List Barang");
            col.setCellValueFactory(new PropertyValueFactory<TransaksiModel, String>("namabrg"));
            tbvKeranjang.getColumns().addAll(col);

            col = new TableColumn("hargabrg");
            col.setCellValueFactory(new PropertyValueFactory<TransaksiModel, Double>("harga"));
            tbvKeranjang.getColumns().addAll(col);

            col = new TableColumn("jumlah");
            col.setCellValueFactory(new PropertyValueFactory<TransaksiModel, Integer>("jumlah"));
            tbvKeranjang.getColumns().addAll(col);

            tbvKeranjang.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();
            tbvKeranjang.getScene().getWindow().hide();
        }
    }

    @FXML
    private void updateKlik(ActionEvent event) {
        TransaksiModel tm = new TransaksiModel();
        tm = tbvKeranjang.getSelectionModel().getSelectedItem();

        if (tm != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_InputKeranjang.fxml"));
                Parent root = (Parent) loader.load();
                FXML_InputKeranjangController isidt = (FXML_InputKeranjangController) loader.getController();
                isidt.execute(tm);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            showData();
            awalKlik(event);

        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Pilih dahulu barang yang ingin anda rubah jumlah", ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void hapusKlik(ActionEvent event) {
        TransaksiModel tm = new TransaksiModel();
        tm = tbvKeranjang.getSelectionModel().getSelectedItem();

        if (tm != null) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau hapus barang ini dari keranjang?", ButtonType.YES, ButtonType.NO);
            a.showAndWait();

            if (a.getResult() == ButtonType.YES) {
                if (FXML_MenuController.dtTransaksi.delete(tm.getNamabrg())) {
                    Alert b = new Alert(Alert.AlertType.INFORMATION, "Barang tersebut telah dihapus dari keranjang", ButtonType.OK);
                    b.showAndWait();
                    if (FXML_MenuController.dtTransaksi.validasiKeranjang() == 0) {
                        Alert c = new Alert(Alert.AlertType.WARNING, "Keranjang Kosong , silahkan tambahkan barang anda ke keranjang dahulu", ButtonType.OK);
                        c.showAndWait();

                        btnKeluar.getScene().getWindow().hide();
                    } else {
                        showData();
                        awalKlik(event);
                    }
                } else {
                    Alert b = new Alert(Alert.AlertType.ERROR, "Barang dari keranjang gagal dihapus", ButtonType.OK);
                    b.showAndWait();
                }
            }
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Pilih dahulu barang yang ingin anda hapus", ButtonType.OK);
            a.showAndWait();
        }

    }

    @FXML
    private void beliKlik(ActionEvent event) {
        PenjualanModel pm = new PenjualanModel();
        PenjualanBukuModel pbum = new PenjualanBukuModel();
        PenjualanBarangElektronikModel pbem = new PenjualanBarangElektronikModel();

        String r = FXML_MenuController.dtPenjualan.getPenjualanModel().getNojual();
        String r1 = FXML_MenuController.dtPenjualanBuku.getPenjualanBukuModel().getNojual();
        String r2 = FXML_MenuController.dtPenjualanBarangElektronik.getPenjualanBarangElektronikModel().getNojual();

        LocalDate date = LocalDate.now();

        if (ldb.getUser(LoginMenuController.Username).equals("buku")) {
            if (FXML_MenuController.dtBuku.validasiIfItemsExist() == 0) {
                if (FXML_MenuController.dtBuku.addQuantity()) {
                    FXML_MenuController.dtBarang.reduceQuantity(ldb.getUser(FXML_PilihCustomerController.user));

                    //Insert into detil
                    pm.setNojual(r);
                    pm.setTanggal(Date.valueOf(date));
                    pm.setUsername("buku");
                    FXML_MenuController.dtPenjualan.setPenjualanModel(pm);

                    if (FXML_MenuController.dtPenjualan.insert(ldb.getUser(FXML_PilihCustomerController.user))) {
                        FXML_MenuController.dtDetilPenjualan.insertIntoDetil(ldb.getUser(FXML_PilihCustomerController.user));
                    }

                    FXML_MenuController.dtTransaksi.deleteAll();

                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                    a.showAndWait();

                    FXML_DisplayBarangController.batalBeli = true;
                    FXML_DisplayBukuController.batalBeli = true;
                    FXML_DisplayBarang_elektronikController.batalBeli = true;

                    FXML_TransaksiController.sudahBeliJual = true;
                    FXML_TransaksiController.getDate = false;

                    btnKeluar.getScene().getWindow().hide();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                    a.showAndWait();
                }
            } else {
                if (FXML_MenuController.dtBuku.addQuantity()) {
                    FXML_MenuController.dtBuku.addItem();
                    FXML_MenuController.dtBarang.reduceQuantity(ldb.getUser(FXML_PilihCustomerController.user));

                    //Insert into detil
                    pm.setNojual(r);
                    pm.setTanggal(Date.valueOf(date));
                    pm.setUsername("buku");
                    FXML_MenuController.dtPenjualan.setPenjualanModel(pm);

                    if (FXML_MenuController.dtPenjualan.insert(ldb.getUser(FXML_PilihCustomerController.user))) {
                        FXML_MenuController.dtDetilPenjualan.insertIntoDetil(ldb.getUser(FXML_PilihCustomerController.user));
                    }

                    FXML_MenuController.dtTransaksi.deleteAll();

                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                    a.showAndWait();

                    FXML_DisplayBarangController.batalBeli = true;
                    FXML_DisplayBukuController.batalBeli = true;
                    FXML_DisplayBarang_elektronikController.batalBeli = true;

                    FXML_TransaksiController.sudahBeliJual = true;
                    FXML_TransaksiController.getDate = false;

                    btnKeluar.getScene().getWindow().hide();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                    a.showAndWait();
                }
            }
        } else if (ldb.getUser(LoginMenuController.Username).equals("elektronik")) {
            if (FXML_MenuController.dtElektronik.validasiIfItemsExist() == 0) {
                if (FXML_MenuController.dtElektronik.addQuantity()) {
                    FXML_MenuController.dtBarang.reduceQuantity(ldb.getUser(FXML_PilihCustomerController.user));

                    //Insert into detil
                    pm.setNojual(r);
                    pm.setTanggal(Date.valueOf(date));
                    pm.setUsername("elektronik");
                    FXML_MenuController.dtPenjualan.setPenjualanModel(pm);

                    if (FXML_MenuController.dtPenjualan.insert(ldb.getUser(FXML_PilihCustomerController.user))) {
                        FXML_MenuController.dtDetilPenjualan.insertIntoDetil(ldb.getUser(FXML_PilihCustomerController.user));
                    }

                    FXML_MenuController.dtTransaksi.deleteAll();

                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                    a.showAndWait();

                    FXML_DisplayBarangController.batalBeli = true;
                    FXML_DisplayBukuController.batalBeli = true;
                    FXML_DisplayBarang_elektronikController.batalBeli = true;

                    FXML_TransaksiController.sudahBeliJual = true;
                    FXML_TransaksiController.getDate = false;

                    btnKeluar.getScene().getWindow().hide();

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                    a.showAndWait();
                }
            } else {
                if (FXML_MenuController.dtElektronik.addQuantity()) {
                    FXML_MenuController.dtElektronik.addItem();
                    FXML_MenuController.dtBarang.reduceQuantity(ldb.getUser(FXML_PilihCustomerController.user));

                    //Insert into detil
                    pm.setNojual(r);
                    pm.setTanggal(Date.valueOf(date));
                    pm.setUsername("elektronik");
                    FXML_MenuController.dtPenjualan.setPenjualanModel(pm);

                    if (FXML_MenuController.dtPenjualan.insert(ldb.getUser(FXML_PilihCustomerController.user))) {
                        FXML_MenuController.dtDetilPenjualan.insertIntoDetil(ldb.getUser(FXML_PilihCustomerController.user));
                    }

                    FXML_MenuController.dtTransaksi.deleteAll();

                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                    a.showAndWait();

                    FXML_DisplayBarangController.batalBeli = true;
                    FXML_DisplayBukuController.batalBeli = true;
                    FXML_DisplayBarang_elektronikController.batalBeli = true;

                    FXML_TransaksiController.sudahBeliJual = true;
                    FXML_TransaksiController.getDate = false;

                    btnKeluar.getScene().getWindow().hide();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                    a.showAndWait();
                }
                showData();
            }
        } else {
            if (FXML_MenuController.isBuku == true) {
                if (FXML_MenuController.dtBarang.validasiIfItemsExist(ldb.getUser(LoginMenuController.Username)) == 0) {
                    if (FXML_MenuController.dtBarang.addQuantity(ldb.getUser(LoginMenuController.Username))) {
                        FXML_MenuController.dtBuku.reduceQuantity();

                        //Insert into detil
                        pbum.setNojual(r1);
                        pbum.setTanggal(Date.valueOf(date));
                        pbum.setUsername(ldb.getUser(LoginMenuController.Username));
                        FXML_MenuController.dtPenjualanBuku.setPenjualanBukuModel(pbum);

                        if (FXML_MenuController.dtPenjualanBuku.insert()) {
                            FXML_MenuController.dtDetilPenjualanBuku.insertIntoDetil();
                        }

                        FXML_MenuController.dtTransaksi.deleteAll();

                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                        a.showAndWait();

                        FXML_DisplayBarangController.batalBeli = true;
                        FXML_DisplayBukuController.batalBeli = true;
                        FXML_DisplayBarang_elektronikController.batalBeli = true;

                        FXML_TransaksiController.sudahBeliJual = true;
                        FXML_TransaksiController.getDate = false;

                        btnKeluar.getScene().getWindow().hide();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                        a.showAndWait();
                    }
                    showData();
                } else {
                    if (FXML_MenuController.dtBarang.addQuantity(ldb.getUser(LoginMenuController.Username))) {
                        FXML_MenuController.dtBarang.addItem(ldb.getUser(LoginMenuController.Username));
                        FXML_MenuController.dtBuku.reduceQuantity();

                        //Insert into detil
                        pbum.setNojual(r1);
                        pbum.setTanggal(Date.valueOf(date));
                        pbum.setUsername(ldb.getUser(LoginMenuController.Username));
                        FXML_MenuController.dtPenjualanBuku.setPenjualanBukuModel(pbum);

                        if (FXML_MenuController.dtPenjualanBuku.insert()) {
                            FXML_MenuController.dtDetilPenjualanBuku.insertIntoDetil();
                        }

                        FXML_MenuController.dtTransaksi.deleteAll();

                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                        a.showAndWait();

                        FXML_TransaksiController.sudahBeliJual = true;
                        FXML_TransaksiController.getDate = false;

                        btnKeluar.getScene().getWindow().hide();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                        a.showAndWait();
                    }
                    showData();
                }
            } else if (FXML_MenuController.isElektronik == true) {
                if (FXML_MenuController.dtBarang.validasiIfItemsExist(ldb.getUser(LoginMenuController.Username)) == 0) {
                    if (FXML_MenuController.dtBarang.addQuantity(ldb.getUser(LoginMenuController.Username))) {
                        FXML_MenuController.dtElektronik.reduceQuantity();

                        //Insert into detil
                        pbem.setNojual(r2);
                        pbem.setTanggal(Date.valueOf(date));
                        pbem.setUsername(ldb.getUser(LoginMenuController.Username));
                        FXML_MenuController.dtPenjualanBarangElektronik.setPenjualanBarangElektronikModel(pbem);

                        if (FXML_MenuController.dtPenjualanBarangElektronik.insert()) {
                            FXML_MenuController.dtDetilPenjualanBarangElektronik.insertIntoDetil();
                        }

                        FXML_MenuController.dtTransaksi.deleteAll();

                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                        a.showAndWait();

                        FXML_DisplayBarangController.batalBeli = true;
                        FXML_DisplayBukuController.batalBeli = true;
                        FXML_DisplayBarang_elektronikController.batalBeli = true;

                        FXML_TransaksiController.sudahBeliJual = true;
                        FXML_TransaksiController.getDate = false;

                        btnKeluar.getScene().getWindow().hide();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                        a.showAndWait();
                    }
                } else {
                    if (FXML_MenuController.dtBarang.addQuantity(ldb.getUser(LoginMenuController.Username))) {
                        FXML_MenuController.dtBarang.addItem(ldb.getUser(LoginMenuController.Username));
                        FXML_MenuController.dtElektronik.reduceQuantity();

                        //Insert into detil
                        pbem.setNojual(r2);
                        pbem.setTanggal(Date.valueOf(date));
                        pbem.setUsername(ldb.getUser(LoginMenuController.Username));
                        FXML_MenuController.dtPenjualanBarangElektronik.setPenjualanBarangElektronikModel(pbem);

                        if (FXML_MenuController.dtPenjualanBarangElektronik.insert()) {
                            FXML_MenuController.dtDetilPenjualanBarangElektronik.insertIntoDetil();
                        }

                        FXML_MenuController.dtTransaksi.deleteAll();

                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                        a.showAndWait();

                        FXML_DisplayBarangController.batalBeli = true;
                        FXML_DisplayBukuController.batalBeli = true;
                        FXML_DisplayBarang_elektronikController.batalBeli = true;

                        FXML_TransaksiController.sudahBeliJual = true;
                        FXML_TransaksiController.getDate = false;

                        btnKeluar.getScene().getWindow().hide();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                        a.showAndWait();
                    }
                }
            }
        }
    }

    @FXML
    private void jualKlik(ActionEvent event) {
        PenjualanModel pm = new PenjualanModel();
        PenjualanBukuModel pbum = new PenjualanBukuModel();
        PenjualanBarangElektronikModel pbem = new PenjualanBarangElektronikModel();

        String r = FXML_MenuController.dtPenjualan.getPenjualanModel().getNojual();
        String r1 = FXML_MenuController.dtPenjualanBuku.getPenjualanBukuModel().getNojual();
        String r2 = FXML_MenuController.dtPenjualanBarangElektronik.getPenjualanBarangElektronikModel().getNojual();

        LocalDate date = LocalDate.now();
        if (ldb.getUser(LoginMenuController.Username).equals("buku")) {
            if (FXML_MenuController.dtBarang.validasiIfItemsExist(ldb.getUser(FXML_PilihCustomerController.user)) == 0) {
                if (FXML_MenuController.dtBarang.addQuantity(ldb.getUser(FXML_PilihCustomerController.user))) {
                    FXML_MenuController.dtBuku.reduceQuantity();

                    //Insert into detil
                    pbum.setNojual(r1);
                    pbum.setTanggal(Date.valueOf(date));
                    pbum.setUsername(ldb.getUser(FXML_PilihCustomerController.user));
                    FXML_MenuController.dtPenjualanBuku.setPenjualanBukuModel(pbum);

                    if (FXML_MenuController.dtPenjualanBuku.insert()) {
                        FXML_MenuController.dtDetilPenjualanBuku.insertIntoDetil();
                    }

                    FXML_MenuController.dtTransaksi.deleteAll();

                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Penjualan Berhasil", ButtonType.OK);
                    a.showAndWait();

                    FXML_TransaksiController.sudahBeliJual = true;
                    FXML_DisplayBukuController.sudahBeliJual = true;
                    FXML_TransaksiController.getDate = false;

                    btnKeluar.getScene().getWindow().hide();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Penjualan gagal", ButtonType.OK);
                    a.showAndWait();
                }
            } else {
                if (FXML_MenuController.dtBarang.addQuantity(ldb.getUser(FXML_PilihCustomerController.user))) {
                    FXML_MenuController.dtBarang.addItem(ldb.getUser(FXML_PilihCustomerController.user));
                    FXML_MenuController.dtBuku.reduceQuantity();

                    //Insert into detil
                    pbum.setNojual(r1);
                    pbum.setTanggal(Date.valueOf(date));
                    pbum.setUsername(ldb.getUser(FXML_PilihCustomerController.user));
                    FXML_MenuController.dtPenjualanBuku.setPenjualanBukuModel(pbum);

                    if (FXML_MenuController.dtPenjualanBuku.insert()) {
                        FXML_MenuController.dtDetilPenjualanBuku.insertIntoDetil();
                    }

                    FXML_MenuController.dtTransaksi.deleteAll();

                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Penjualan Berhasil", ButtonType.OK);
                    a.showAndWait();

                    FXML_TransaksiController.sudahBeliJual = true;
                    FXML_DisplayBukuController.sudahBeliJual = true;

                    FXML_TransaksiController.getDate = false;

                    btnKeluar.getScene().getWindow().hide();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Penjualan gagal", ButtonType.OK);
                    a.showAndWait();
                }
            }
        } else if (ldb.getUser(LoginMenuController.Username).equals("elektronik")) {
            if (FXML_MenuController.dtBarang.validasiIfItemsExist(ldb.getUser(FXML_PilihCustomerController.user)) == 0) {
                if (FXML_MenuController.dtBarang.addQuantity(ldb.getUser(FXML_PilihCustomerController.user))) {
                    FXML_MenuController.dtElektronik.reduceQuantity();

                    //Insert into detil
                    pbem.setNojual(r2);
                    pbem.setTanggal(Date.valueOf(date));
                    pbem.setUsername(ldb.getUser(FXML_PilihCustomerController.user));
                    FXML_MenuController.dtPenjualanBarangElektronik.setPenjualanBarangElektronikModel(pbem);

                    if (FXML_MenuController.dtPenjualanBarangElektronik.insert()) {
                        FXML_MenuController.dtDetilPenjualanBarangElektronik.insertIntoDetil();
                    }

                    FXML_MenuController.dtTransaksi.deleteAll();

                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Penjualan Berhasil", ButtonType.OK);
                    a.showAndWait();

                    FXML_TransaksiController.sudahBeliJual = true;
                    FXML_DisplayBarang_elektronikController.sudahBeliJual = true;

                    FXML_TransaksiController.getDate = false;

                    btnKeluar.getScene().getWindow().hide();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Penjualan gagal", ButtonType.OK);
                    a.showAndWait();
                }
            } else {
                if (FXML_MenuController.dtBarang.addQuantity(ldb.getUser(FXML_PilihCustomerController.user))) {
                    FXML_MenuController.dtBarang.addItem(ldb.getUser(FXML_PilihCustomerController.user));
                    FXML_MenuController.dtElektronik.reduceQuantity();

                    //Insert into detil
                    pbem.setNojual(r2);
                    pbem.setTanggal(Date.valueOf(date));
                    pbem.setUsername(ldb.getUser(FXML_PilihCustomerController.user));
                    FXML_MenuController.dtPenjualanBarangElektronik.setPenjualanBarangElektronikModel(pbem);

                    if (FXML_MenuController.dtPenjualanBarangElektronik.insert()) {
                        FXML_MenuController.dtDetilPenjualanBarangElektronik.insertIntoDetil();
                    }

                    FXML_MenuController.dtTransaksi.deleteAll();

                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Penjualan Berhasil", ButtonType.OK);
                    a.showAndWait();

                    FXML_TransaksiController.sudahBeliJual = true;
                    FXML_DisplayBarang_elektronikController.sudahBeliJual = true;

                    FXML_TransaksiController.getDate = false;

                    btnKeluar.getScene().getWindow().hide();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Penjualan gagal", ButtonType.OK);
                    a.showAndWait();
                }
                showData();
            }
        } else {
            if (FXML_DisplayBukuController.tokoBuku == true) {
                if (FXML_MenuController.dtBuku.validasiIfItemsExist() == 0) {
                    if (FXML_MenuController.dtBuku.addQuantity()) {
                        FXML_MenuController.dtBarang.reduceQuantity(ldb.getUser(LoginMenuController.Username));

                        //Insert into detil
                        pm.setNojual(r);
                        pm.setTanggal(Date.valueOf(date));
                        pm.setUsername("buku");
                        FXML_MenuController.dtPenjualan.setPenjualanModel(pm);

                        if (FXML_MenuController.dtPenjualan.insert(ldb.getUser(ldb.getUser(LoginMenuController.Username)))) {
                            FXML_MenuController.dtDetilPenjualan.insertIntoDetil(ldb.getUser(LoginMenuController.Username));
                        }

                        FXML_MenuController.dtTransaksi.deleteAll();

                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Penjualan Berhasil", ButtonType.OK);
                        a.showAndWait();

                        FXML_TransaksiController.sudahBeliJual = true;
                        FXML_DisplayBarangController.sudahBeliJual = true;

                        FXML_TransaksiController.getDate = false;

                        btnKeluar.getScene().getWindow().hide();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Penjualan gagal", ButtonType.OK);
                        a.showAndWait();
                    }
                    showData();
                } else {
                    if (FXML_MenuController.dtBuku.addQuantity()) {
                        FXML_MenuController.dtBuku.addItem();
                        FXML_MenuController.dtBarang.reduceQuantity(ldb.getUser(LoginMenuController.Username));

                        //Insert into detil
                        pm.setNojual(r);
                        pm.setTanggal(Date.valueOf(date));
                        pm.setUsername("buku");
                        FXML_MenuController.dtPenjualan.setPenjualanModel(pm);

                        if (FXML_MenuController.dtPenjualan.insert(ldb.getUser(ldb.getUser(LoginMenuController.Username)))) {
                            FXML_MenuController.dtDetilPenjualan.insertIntoDetil(ldb.getUser(LoginMenuController.Username));
                        }

                        FXML_MenuController.dtTransaksi.deleteAll();

                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Penjualan Berhasil", ButtonType.OK);
                        a.showAndWait();

                        FXML_TransaksiController.sudahBeliJual = true;
                        FXML_DisplayBarangController.sudahBeliJual = true;

                        FXML_TransaksiController.getDate = false;

                        btnKeluar.getScene().getWindow().hide();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Penjualan gagal", ButtonType.OK);
                        a.showAndWait();
                    }
                    showData();
                }
            } else if (FXML_DisplayBarang_elektronikController.tokoElektronik == true) {
                if (FXML_MenuController.dtElektronik.validasiIfItemsExist() == 0) {
                    if (FXML_MenuController.dtElektronik.addQuantity()) {
                        FXML_MenuController.dtBarang.reduceQuantity(ldb.getUser(LoginMenuController.Username));

                        //Insert into detil
                        pm.setNojual(r);
                        pm.setTanggal(Date.valueOf(date));
                        pm.setUsername("elektronik");
                        FXML_MenuController.dtPenjualan.setPenjualanModel(pm);

                        if (FXML_MenuController.dtPenjualan.insert(ldb.getUser(LoginMenuController.Username))) {
                            FXML_MenuController.dtDetilPenjualan.insertIntoDetil(ldb.getUser(LoginMenuController.Username));
                        }

                        FXML_MenuController.dtTransaksi.deleteAll();

                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Penjualan Berhasil", ButtonType.OK);
                        a.showAndWait();

                        FXML_TransaksiController.sudahBeliJual = true;
                        FXML_DisplayBarangController.sudahBeliJual = true;

                        FXML_TransaksiController.getDate = false;

                        btnKeluar.getScene().getWindow().hide();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Penjualan gagal", ButtonType.OK);
                        a.showAndWait();
                    }
                } else {
                    if (FXML_MenuController.dtElektronik.addQuantity()) {
                        FXML_MenuController.dtElektronik.addItem();
                        FXML_MenuController.dtBarang.reduceQuantity(ldb.getUser(LoginMenuController.Username));

                        //Insert into detil
                        pm.setNojual(r);
                        pm.setTanggal(Date.valueOf(date));
                        pm.setUsername("elektronik");
                        FXML_MenuController.dtPenjualan.setPenjualanModel(pm);

                        if (FXML_MenuController.dtPenjualan.insert(ldb.getUser(LoginMenuController.Username))) {
                            FXML_MenuController.dtDetilPenjualan.insertIntoDetil(ldb.getUser(LoginMenuController.Username));
                        }

                        FXML_MenuController.dtTransaksi.deleteAll();

                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Penjualan Berhasil", ButtonType.OK);
                        a.showAndWait();

                        FXML_TransaksiController.sudahBeliJual = true;
                        FXML_DisplayBarangController.sudahBeliJual = true;

                        FXML_TransaksiController.getDate = false;

                        btnKeluar.getScene().getWindow().hide();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Penjualan gagal", ButtonType.OK);
                        a.showAndWait();
                    }
                    showData();
                }
            }
        }
    }

    @FXML
    private void sebelumKlik(ActionEvent event) {
        tbvKeranjang.getSelectionModel().selectAboveCell();
        tbvKeranjang.requestFocus();
    }

    @FXML
    private void sesudahKlik(ActionEvent event) {
        tbvKeranjang.getSelectionModel().selectBelowCell();
        tbvKeranjang.requestFocus();
    }

    @FXML
    private void awalKlik(ActionEvent event) {
        tbvKeranjang.getSelectionModel().selectFirst();
        tbvKeranjang.requestFocus();
    }

    @FXML
    private void akhirKlik(ActionEvent event) {
        tbvKeranjang.getSelectionModel().selectLast();
        tbvKeranjang.requestFocus();
    }

    @FXML
    private void keluarKlik(ActionEvent event) {
        btnKeluar.getScene().getWindow().hide();
    }
}
