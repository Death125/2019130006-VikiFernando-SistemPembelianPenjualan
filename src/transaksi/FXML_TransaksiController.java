/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package transaksi;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistemlogin.DB_Login;
import sistemlogin.LoginMenuController;
import sistempembelianpenjualan.BarangModel;
import sistempembelianpenjualan.DB_Barang;
import sistempembelianpenjualan.FXML_DisplayBarangController;
import sistempembelianpenjualan.FXML_MenuController;
import sistempembelianpenjualan.FXML_PilihCustomerController;
import sistempembelianpenjualan.PenjualanModel;
import tokobuku.BukuModel;
import tokobuku.FXML_DisplayBukuController;
import tokobuku.PenjualanBukuModel;
import tokoelektronik.BarangElektronikModel;
import tokoelektronik.FXML_DisplayBarang_elektronikController;
import tokoelektronik.PenjualanBarangElektronikModel;

/**
 * FXML Controller class
 *
 *
 */
public class FXML_TransaksiController implements Initializable {

    private DB_Barang dtBarang = new DB_Barang();
    private DB_Login ldb = new DB_Login();

    public static boolean sudahBeliJual = false;
    public static boolean tambahKeKeranjangJ = false;
    public static boolean getDate = false;
    public static int tempDate = 0;

    @FXML
    private TextField txtJumlah;
    @FXML
    private Button btnBatalBeli;
    @FXML
    private Button btnPergiKeKeranjang;
    @FXML
    private Button btnTambahBarang;
    @FXML
    private Label lblKodeBrg;
    @FXML
    private Button btnTambahKeKeranjang;
    @FXML
    private Label lblBarangYangDibeli;
    @FXML
    private Label lblJenis;
    @FXML
    private Label lblHarga;
    @FXML
    private Button btnTambahKeKeranjangJ;
    @FXML
    private Button btnTambahBarangJ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButtonStyle();

        validasiTambahKeranjang();
        restrictTxtJumlah();
    }

    void addButtonStyle() {
        btnBatalBeli.getStyleClass().add("buttonStyle3");
        btnPergiKeKeranjang.getStyleClass().add("buttonStyle3");
        btnTambahBarang.getStyleClass().add("buttonStyle2");
        btnTambahBarangJ.getStyleClass().add("buttonStyle2");
        btnTambahKeKeranjang.getStyleClass().add("buttonStyle2");
        btnTambahKeKeranjangJ.getStyleClass().add("buttonStyle2");
    }

    /*Validasi */
    public void validasiTambahKeranjang() {
        if (tambahKeKeranjangJ == true) {
            btnTambahKeKeranjangJ.setVisible(true);
            btnTambahBarangJ.setVisible(true);
            btnTambahKeKeranjang.setVisible(false);
        } else {
            btnTambahKeKeranjangJ.setVisible(false);
            btnTambahBarangJ.setVisible(false);
            btnTambahKeKeranjang.setVisible(true);
        }
    }

    private void restrictTxtJumlah() {
        // force the field to be numeric only
        txtJumlah.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtJumlah.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void validasiKeranjang() {
        int i = (int) (new Date().getTime() / 1000);

        TransaksiModel tm = new TransaksiModel();
        FXML_MenuController.dtTransaksi.setTransaksiModel(tm);

        PenjualanModel pm = new PenjualanModel();
        FXML_MenuController.dtPenjualan.setPenjualanModel(pm);

        PenjualanBukuModel pbum = new PenjualanBukuModel();
        FXML_MenuController.dtPenjualanBuku.setPenjualanBukuModel(pbum);

        PenjualanBarangElektronikModel pbem = new PenjualanBarangElektronikModel();
        FXML_MenuController.dtPenjualanBarangElektronik.setPenjualanBarangElektronikModel(pbem);

        if (getDate == false) {
            tempDate = i;
            getDate = true;
        }

        pm.setNojual("J" + tempDate);
        pbum.setNojual("J" + tempDate);
        pbem.setNojual("J" + tempDate);
        tm.setNojual("J" + tempDate);

        tm.setKodebrg(lblKodeBrg.getText());
        tm.setNamabrg(lblBarangYangDibeli.getText());
        tm.setJenis(lblJenis.getText());
        tm.setHarga(Double.parseDouble(lblHarga.getText()));
        tm.setJumlah(Integer.parseInt(txtJumlah.getText()));

        if (FXML_MenuController.dtTransaksi.validasiNamaBarang(lblKodeBrg.getText()) > 0) {
            if (FXML_MenuController.dtTransaksi.addQuantity(lblKodeBrg.getText(), Integer.parseInt(txtJumlah.getText()))) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Jumlah barang tersebut yang ada dikeranjang sudah ditambahkan", ButtonType.OK);
                a.showAndWait();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Yahh terjadi error coba lagi nanti", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            if (FXML_MenuController.dtTransaksi.insert()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Barang berhasil ditambahkan ke keranjang", ButtonType.OK);
                a.showAndWait();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Barang gagal ditambahkan ke keranjang", ButtonType.OK);
                a.showAndWait();
            }
        }
    }

    /**/
    public void tampilkanBarangYangDibeli(BarangModel bm) {
        if (!bm.getKodebrg().isEmpty()) {
            lblKodeBrg.setText(bm.getKodebrg());
            lblBarangYangDibeli.setText(bm.getNamabrg());
            lblHarga.setText(Double.toString(bm.getHarga()));
            lblJenis.setText(bm.getJenis());
        }
    }

    public void tampilkanBukuYangDibeli(BukuModel bum) {
        if (!bum.getKodebrg().isEmpty()) {
            lblKodeBrg.setText(bum.getKodebrg());
            lblBarangYangDibeli.setText(bum.getNamabrg());
            lblHarga.setText(Double.toString(bum.getHarga()));
            lblJenis.setText(bum.getJenis());
        }
    }

    public void tampilkanBarangElektronikYangDibeli(BarangElektronikModel bem) {
        if (!bem.getKodebrg().isEmpty()) {
            lblKodeBrg.setText(bem.getKodebrg());
            lblBarangYangDibeli.setText(bem.getNamabrg());
            lblHarga.setText(Double.toString(bem.getHarga()));
            lblJenis.setText(bem.getJenis());
        }
    }

    //Tambah Barang Belian 
    @FXML
    private void tambahBarangKlik(ActionEvent event) throws IOException {
        btnTambahBarang.getScene().getWindow().hide();
    }

    //Tambah barang Jualan
    @FXML
    private void tambahBarangJKlik(ActionEvent event) {
        btnTambahBarang.getScene().getWindow().hide();
    }

    //Tambah Ke keranjang beli
    @FXML
    private void tambahKeKeranjangKlik(ActionEvent event) {
        System.out.println("buku :" + FXML_MenuController.isBuku);
        System.out.println("Elektronik :" + FXML_MenuController.isElektronik);
        System.out.println("Customer :" + FXML_MenuController.isCustomer);
        if (FXML_MenuController.isBuku) {
            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBuku(lblKodeBrg.getText())
                    || (FXML_MenuController.dtTransaksi.validasiQuantityKeranjang(lblKodeBrg.getText()) + Integer.parseInt(txtJumlah.getText()))
                    > FXML_MenuController.dtTransaksi.validasiJumlahBuku(lblKodeBrg.getText())) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki", ButtonType.OK);
                a.showAndWait();
            } else {
                if (txtJumlah.getText().equals("") || Integer.parseInt(txtJumlah.getText()) == 0) {
                    Alert a = new Alert(Alert.AlertType.WARNING, "Input jumlahnya terlebih dahulu ", ButtonType.OK);
                    a.showAndWait();
                } else {
                    validasiKeranjang();
                }
            }
        } else if (FXML_MenuController.isElektronik) {
            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBarangElektronik(lblKodeBrg.getText())
                    || (FXML_MenuController.dtTransaksi.validasiQuantityKeranjang(lblKodeBrg.getText()) + Integer.parseInt(txtJumlah.getText()))
                    > FXML_MenuController.dtTransaksi.validasiJumlahBarangElektronik(lblKodeBrg.getText())) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki");
                a.showAndWait();
            } else {
                if (txtJumlah.getText().equals("") || Integer.parseInt(txtJumlah.getText()) == 0) {
                    Alert a = new Alert(Alert.AlertType.WARNING, "Input jumlahnya terlebih dahulu ", ButtonType.OK);
                    a.showAndWait();
                } else {
                    validasiKeranjang();
                }
            }
        } else if (FXML_MenuController.isCustomer) {
            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBarang(lblKodeBrg.getText(), ldb.getUser(FXML_PilihCustomerController.user))
                    || (FXML_MenuController.dtTransaksi.validasiQuantityKeranjang(lblKodeBrg.getText()) + Integer.parseInt(txtJumlah.getText()))
                    > FXML_MenuController.dtTransaksi.validasiJumlahBarang(lblKodeBrg.getText(), ldb.getUser(FXML_PilihCustomerController.user))) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki");
                a.showAndWait();
            } else {
                if (txtJumlah.getText().equals("") || Integer.parseInt(txtJumlah.getText()) == 0) {
                    Alert a = new Alert(Alert.AlertType.WARNING, "Input jumlahnya terlebih dahulu ", ButtonType.OK);
                    a.showAndWait();
                } else {
                    validasiKeranjang();
                }
            }
        }
    }

    //Tambah Ke keranjang jual
    @FXML
    private void tambahKeKeranjangJKlik(ActionEvent event) {
        if (FXML_MenuController.isBuku) {
            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBuku(lblKodeBrg.getText())
                    || (FXML_MenuController.dtTransaksi.validasiQuantityKeranjang(lblKodeBrg.getText()) + Integer.parseInt(txtJumlah.getText()))
                    > FXML_MenuController.dtTransaksi.validasiJumlahBuku(lblKodeBrg.getText())) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki", ButtonType.OK);
                a.showAndWait();
            } else {
                if (txtJumlah.getText().equals("") || Integer.parseInt(txtJumlah.getText()) == 0) {
                    Alert a = new Alert(Alert.AlertType.WARNING, "Input jumlahnya terlebih dahulu ", ButtonType.OK);
                    a.showAndWait();
                } else {
                    validasiKeranjang();
                }
            }
        } else if (FXML_MenuController.isElektronik) {
            System.out.println("Customer is " + FXML_MenuController.isCustomer);
            System.out.println("Buku is " + FXML_MenuController.isBuku);
            System.out.println("Elektronik is " + FXML_MenuController.isElektronik);

            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBarangElektronik(lblKodeBrg.getText())
                    || (FXML_MenuController.dtTransaksi.validasiQuantityKeranjang(lblKodeBrg.getText()) + Integer.parseInt(txtJumlah.getText()))
                    > FXML_MenuController.dtTransaksi.validasiJumlahBarangElektronik(lblKodeBrg.getText())) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki");
                a.showAndWait();
            } else {
                if (txtJumlah.getText().equals("") || Integer.parseInt(txtJumlah.getText()) == 0) {
                    Alert a = new Alert(Alert.AlertType.WARNING, "Input jumlahnya terlebih dahulu ", ButtonType.OK);
                    a.showAndWait();
                } else {
                    validasiKeranjang();
                }
            }
        } else if (FXML_MenuController.isCustomer) {

            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBarang(lblKodeBrg.getText(), ldb.getUser(LoginMenuController.Username))
                    || (FXML_MenuController.dtTransaksi.validasiQuantityKeranjang(lblKodeBrg.getText()) + Integer.parseInt(txtJumlah.getText()))
                    > FXML_MenuController.dtTransaksi.validasiJumlahBarang(lblKodeBrg.getText(), ldb.getUser(LoginMenuController.Username))) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki");
                a.showAndWait();
            } else {
                if (txtJumlah.getText().equals("") || Integer.parseInt(txtJumlah.getText()) == 0) {
                    Alert a = new Alert(Alert.AlertType.WARNING, "Input jumlahnya terlebih dahulu ", ButtonType.OK);
                    a.showAndWait();
                } else {
                    validasiKeranjang();
                }
            }
        }
    }

    @FXML
    private void pergiKeKeranjangKlik(ActionEvent event) throws IOException {
        if (FXML_MenuController.dtTransaksi.validasiKeranjang() == 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Yahh keranjang anda kosong, silahkan tambahkan barang anda ke keranjang terlebih dahulu", ButtonType.OK);
            a.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Keranjang.fxml"));
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
            if (sudahBeliJual == true) {
                btnBatalBeli.getScene().getWindow().hide();
                sudahBeliJual = false;
            }
        }

    }

    @FXML
    private void batalBeliKlik(ActionEvent event) {
        if (tambahKeKeranjangJ == false) {
            FXML_DisplayBarangController.batalBeli = true;
            FXML_DisplayBukuController.batalBeli = true;
            FXML_DisplayBarang_elektronikController.batalBeli = true;
            FXML_MenuController.dtTransaksi.deleteAll();
            btnBatalBeli.getScene().getWindow().hide();
        } else {
            FXML_MenuController.dtTransaksi.deleteAll();
            btnBatalBeli.getScene().getWindow().hide();
        }
    }

}
