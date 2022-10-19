/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package transaksi;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistemlogin.DB_Login;
import sistemlogin.LoginMenuController;
import sistempembelianpenjualan.FXML_DisplayBarangController;
import sistempembelianpenjualan.FXML_MenuController;
import sistempembelianpenjualan.FXML_PilihCustomerController;
import tokobuku.FXML_DisplayBukuController;
import tokoelektronik.FXML_DisplayBarang_elektronikController;

/**
 * FXML Controller class
 *
 *
 */
public class FXML_KeranjangController implements Initializable {

    public static boolean beli = false;
    public static boolean jual = false;
    private DB_Login ldb = new DB_Login();

    @FXML
    private TableView<TransaksiModel> tbvKeranjang;
    @FXML
    private Label title;
    @FXML
    private Button btnBeli;
    @FXML
    private Button btnKeluar;
    @FXML
    private Button UpdateButton;
    @FXML
    private Button DeleteButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnBeli.getStyleClass().add("buttonStyle2");
        btnKeluar.getStyleClass().add("buttonStyle3");

        showData();
    }

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
    private void beliKlik(ActionEvent event) {
        if (ldb.getUser(LoginMenuController.Username).equals("buku")) {
            if (FXML_MenuController.dtBuku.validasiIfItemsExist() == 0) {
                if (FXML_MenuController.dtBuku.addQuantity()) {
                    FXML_MenuController.dtBarang.reduceQuantity(ldb.getUser(FXML_PilihCustomerController.user));
                    FXML_MenuController.dtTransaksi.deleteAll();
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                    a.showAndWait();
                    btnKeluar.getScene().getWindow().hide();
                    FXML_TransaksiController.sudahBeli = true;
                    FXML_DisplayBarangController.batalBeli = true;
                    FXML_DisplayBukuController.batalBeli = true;
                    FXML_DisplayBarang_elektronikController.batalBeli = true;

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                    a.showAndWait();
                }
            } else {
                if (FXML_MenuController.dtBuku.addQuantity()) {
                    FXML_MenuController.dtBuku.addItem();
                    FXML_MenuController.dtBarang.reduceQuantity(ldb.getUser(FXML_PilihCustomerController.user));
                    FXML_MenuController.dtTransaksi.deleteAll();
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                    a.showAndWait();
                    btnKeluar.getScene().getWindow().hide();
                    FXML_TransaksiController.sudahBeli = true;
                    FXML_DisplayBarangController.batalBeli = true;
                    FXML_DisplayBukuController.batalBeli = true;
                    FXML_DisplayBarang_elektronikController.batalBeli = true;

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                    a.showAndWait();
                }
            }
        } else if (ldb.getUser(LoginMenuController.Username).equals("elektronik")) {
            if (FXML_MenuController.dtElektronik.validasiIfItemsExist() == 0) {
                if (FXML_MenuController.dtElektronik.addQuantity()) {
                    FXML_MenuController.dtBarang.reduceQuantity(ldb.getUser(FXML_PilihCustomerController.user));
                    FXML_MenuController.dtTransaksi.deleteAll();
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                    a.showAndWait();
                    btnKeluar.getScene().getWindow().hide();
                    FXML_TransaksiController.sudahBeli = true;
                    FXML_DisplayBarangController.batalBeli = true;
                    FXML_DisplayBukuController.batalBeli = true;
                    FXML_DisplayBarang_elektronikController.batalBeli = true;

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                    a.showAndWait();
                }
            } else {
                if (FXML_MenuController.dtElektronik.addQuantity()) {
                    FXML_MenuController.dtElektronik.addItem();
                    FXML_MenuController.dtBarang.reduceQuantity(ldb.getUser(FXML_PilihCustomerController.user));
                    FXML_MenuController.dtTransaksi.deleteAll();
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                    a.showAndWait();
                    btnKeluar.getScene().getWindow().hide();
                    FXML_TransaksiController.sudahBeli = true;
                    FXML_DisplayBarangController.batalBeli = true;
                    FXML_DisplayBukuController.batalBeli = true;
                    FXML_DisplayBarang_elektronikController.batalBeli = true;

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
                        FXML_MenuController.dtTransaksi.deleteAll();
                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                        a.showAndWait();
                        btnKeluar.getScene().getWindow().hide();
                        FXML_TransaksiController.sudahBeli = true;
                        FXML_DisplayBarangController.batalBeli = true;
                        FXML_DisplayBukuController.batalBeli = true;
                        FXML_DisplayBarang_elektronikController.batalBeli = true;

                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                        a.showAndWait();
                    }
                    showData();
                } else {
                    if (FXML_MenuController.dtBarang.addQuantity(ldb.getUser(LoginMenuController.Username))) {
                        FXML_MenuController.dtBarang.addItem(ldb.getUser(LoginMenuController.Username));
                        FXML_MenuController.dtBuku.reduceQuantity();
                        FXML_MenuController.dtTransaksi.deleteAll();
                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                        a.showAndWait();
                        btnKeluar.getScene().getWindow().hide();
                        FXML_TransaksiController.sudahBeli = true;
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
                        FXML_MenuController.dtTransaksi.deleteAll();
                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                        a.showAndWait();
                        btnKeluar.getScene().getWindow().hide();
                        FXML_TransaksiController.sudahBeli = true;
                        FXML_DisplayBarangController.batalBeli = true;
                        FXML_DisplayBukuController.batalBeli = true;
                        FXML_DisplayBarang_elektronikController.batalBeli = true;

                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                        a.showAndWait();
                    }
                } else {
                    if (FXML_MenuController.dtBarang.addQuantity(ldb.getUser(LoginMenuController.Username))) {
                        FXML_MenuController.dtBarang.addItem(ldb.getUser(LoginMenuController.Username));
                        FXML_MenuController.dtElektronik.reduceQuantity();
                        FXML_MenuController.dtTransaksi.deleteAll();
                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Pembelian Berhasil", ButtonType.OK);
                        a.showAndWait();
                        btnKeluar.getScene().getWindow().hide();
                        FXML_TransaksiController.sudahBeli = true;
                        FXML_DisplayBarangController.batalBeli = true;
                        FXML_DisplayBukuController.batalBeli = true;
                        FXML_DisplayBarang_elektronikController.batalBeli = true;

                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Pembelian gagal", ButtonType.OK);
                        a.showAndWait();
                    }
                }
            }
        }
    }

    @FXML
    private void keluarKlik(ActionEvent event) {
        btnKeluar.getScene().getWindow().hide();
    }

    @FXML
    private void sebelumKlik(ActionEvent event) {
        tbvKeranjang.getSelectionModel().selectAboveCell();
        tbvKeranjang.requestFocus();
    }

    @FXML
    private void awalKlik(ActionEvent event) {
        tbvKeranjang.getSelectionModel().selectFirst();
        tbvKeranjang.requestFocus();
    }

    @FXML
    private void sesudahKlik(ActionEvent event) {
        tbvKeranjang.getSelectionModel().selectBelowCell();
        tbvKeranjang.requestFocus();
    }

    @FXML
    private void akhirKlik(ActionEvent event) {
        tbvKeranjang.getSelectionModel().selectLast();
        tbvKeranjang.requestFocus();
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
                        Alert c = new Alert(Alert.AlertType.WARNING, "Keranjang Kosong , silahkan tambahkan barang yang ingin anda beli", ButtonType.OK);
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

}
