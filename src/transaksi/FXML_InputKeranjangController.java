/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package transaksi;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sistemlogin.DB_Login;
import sistemlogin.LoginMenuController;
import sistempembelianpenjualan.FXML_MenuController;
import sistempembelianpenjualan.FXML_PilihCustomerController;

/**
 * FXML Controller class
 *
 *
 *
 */
public class FXML_InputKeranjangController implements Initializable {

    public static boolean isUpdateJual = false;

    @FXML
    private TextField txtNamaBrg;
    @FXML
    private TextField txtHargaBarang;
    @FXML
    private TextField txtJumlah;
    @FXML
    private Button btnConfirm;
    @FXML
    private Label lblKodeBrg;
    @FXML
    private Button btnConfirmJ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButtonStyle();
        isUpdateJual();
    }

    void addButtonStyle() {
        btnConfirm.getStyleClass().add("buttonStyle2");
        btnConfirmJ.getStyleClass().add("buttonStyle2");
    }

    /*Validasi*/
    public void isUpdateJual() {
        if (isUpdateJual != true) {
            btnConfirm.setVisible(true);
            btnConfirmJ.setVisible(false);
        } else {
            btnConfirm.setVisible(false);
            btnConfirmJ.setVisible(true);
        }
    }

    public void isUpdateJumlah() {
        if (FXML_MenuController.dtTransaksi.update()) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Jumlah barang telah diupdate");
            a.showAndWait();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Jumlah barang gagal diupdate");
            a.showAndWait();
        }
    }

    /**/
    public void execute(TransaksiModel tm) {
        if (!tm.getNamabrg().isEmpty()) {
            lblKodeBrg.setText(tm.getKodebrg());
            txtNamaBrg.setText(tm.getNamabrg());
            txtHargaBarang.setText(String.valueOf(tm.getHarga()));
            txtJumlah.setText(String.valueOf(tm.getJumlah()));

            txtNamaBrg.setEditable(false);
            txtHargaBarang.setEditable(false);
            txtNamaBrg.requestFocus();
        }
    }

    //Confirm Beli
    @FXML
    private void confirmKlik(ActionEvent event) {
        TransaksiModel tm = new TransaksiModel();

        tm.setKodebrg(lblKodeBrg.getText());
        tm.setNamabrg(txtNamaBrg.getText());
        tm.setHarga(Double.parseDouble(txtHargaBarang.getText()));
        tm.setJumlah(Integer.parseInt(txtJumlah.getText()));

        FXML_MenuController.dtTransaksi.setTransaksiModel(tm);

        if (FXML_MenuController.isBuku) {
            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBuku(lblKodeBrg.getText())) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki", ButtonType.OK);
                a.showAndWait();
            } else {
                isUpdateJumlah();
                btnConfirm.getScene().getWindow().hide();
            }
        } else if (FXML_MenuController.isElektronik) {
            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBarangElektronik(lblKodeBrg.getText())) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki", ButtonType.OK);
                a.showAndWait();
            } else {
                isUpdateJumlah();
                btnConfirm.getScene().getWindow().hide();
            }
        } else if (FXML_MenuController.isCustomer) {
            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBarang(lblKodeBrg.getText(), DB_Login.getUser(FXML_PilihCustomerController.user))) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki", ButtonType.OK);
                a.showAndWait();
            } else {
                isUpdateJumlah();
                btnConfirm.getScene().getWindow().hide();
            }
        }
    }

    //Confirm Jual
    @FXML
    private void confirmKlikJ(ActionEvent event) {
        TransaksiModel tm = new TransaksiModel();

        tm.setKodebrg(lblKodeBrg.getText());
        tm.setNamabrg(txtNamaBrg.getText());
        tm.setHarga(Double.parseDouble(txtHargaBarang.getText()));
        tm.setJumlah(Integer.parseInt(txtJumlah.getText()));

        FXML_MenuController.dtTransaksi.setTransaksiModel(tm);

        if (FXML_MenuController.isBuku) {
            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBuku(lblKodeBrg.getText())) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki", ButtonType.OK);
                a.showAndWait();
            } else {
                isUpdateJumlah();
                btnConfirm.getScene().getWindow().hide();
            }
        } else if (FXML_MenuController.isElektronik) {
            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBarangElektronik(lblKodeBrg.getText())) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki", ButtonType.OK);
                a.showAndWait();
            } else {
                isUpdateJumlah();
                btnConfirm.getScene().getWindow().hide();
            }
        } else if (FXML_MenuController.isCustomer) {
            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBarang(lblKodeBrg.getText(), DB_Login.getUser(LoginMenuController.Username))) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki", ButtonType.OK);
                a.showAndWait();
            } else {
                isUpdateJumlah();
                btnConfirm.getScene().getWindow().hide();
            }
        }
    }

}
