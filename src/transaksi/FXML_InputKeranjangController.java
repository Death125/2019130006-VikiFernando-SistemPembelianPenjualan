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
import sistempembelianpenjualan.FXML_MenuController;
import sistempembelianpenjualan.FXML_PilihCustomerController;
import sun.awt.PlatformFont;

/**
 * FXML Controller class
 *
 *
 *
 */
public class FXML_InputKeranjangController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnConfirm.getStyleClass().add("buttonStyle2");
    }

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
                updateJumlah();
                btnConfirm.getScene().getWindow().hide();
            }
        } else if (FXML_MenuController.isElektronik) {
            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBarangElektronik(lblKodeBrg.getText())) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki", ButtonType.OK);
                a.showAndWait();
            } else {
                updateJumlah();
                btnConfirm.getScene().getWindow().hide();
            }
        } else {
            if (Integer.parseInt(txtJumlah.getText()) > FXML_MenuController.dtTransaksi.validasiJumlahBarang(lblKodeBrg.getText(), DB_Login.getUser(FXML_PilihCustomerController.user))) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang yang dibeli melebihi stok yang kami miliki", ButtonType.OK);
                a.showAndWait();
            } else {
                updateJumlah();
                btnConfirm.getScene().getWindow().hide();
            }
        }
    }

    public void updateJumlah() {
        if (FXML_MenuController.dtTransaksi.update()) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Jumlah barang telah diupdate");
            a.showAndWait();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Jumlah barang gagal diupdate");
            a.showAndWait();
        }
    }

}
