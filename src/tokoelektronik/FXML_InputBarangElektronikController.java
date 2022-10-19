/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tokoelektronik;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sistempembelianpenjualan.FXML_MenuController;
import sistempembelianpenjualan.DB_Customer;
import sistempembelianpenjualan.FXML_PilihCustomerController;

/**
 * FXML Controller class
 *
 *
 */
public class FXML_InputBarangElektronikController implements Initializable {

    private DB_Customer ldc = new DB_Customer();
    private boolean editData = false;

    @FXML
    private TextField txtKodeBrg;
    @FXML
    private TextField txtNamaBrg;
    @FXML
    private TextField txtHarga;
    @FXML
    private TextField txtJumlah;
    @FXML
    private Button btnKeluar;
    @FXML
    private ComboBox<String> chbJenis;
    @FXML
    private Button btnSimpan;
    @FXML
    private Button btnBatal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chbJenis.setItems(FXCollections.observableArrayList("Elektronik", "Peralatan Listrik"));
        restrictTxtHarga();
        restrictTxtJumlah();

        btnSimpan.getStyleClass().add("buttonStyle2");
        btnBatal.getStyleClass().add("buttonStyle2");
        btnKeluar.getStyleClass().add("buttonStyle2");
    }

    private void restrictTxtHarga() {
        // force the field to be numeric only
        txtHarga.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtHarga.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
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

    public void execute(BarangElektronikModel bem) {
        if (!bem.getKodebrg().isEmpty()) {
            editData = true;
            txtKodeBrg.setText(bem.getKodebrg());
            txtNamaBrg.setText(bem.getNamabrg());
            chbJenis.setValue(bem.getJenis());
            txtHarga.setText(String.valueOf(bem.getHarga()));
            txtJumlah.setText(String.valueOf(bem.getJumlah()));

            txtKodeBrg.setEditable(false);
            txtNamaBrg.requestFocus();
        }
    }

    @FXML
    private void simpanKlik(ActionEvent event) {
        BarangElektronikModel bem = new BarangElektronikModel();
        bem.setKodebrg(txtKodeBrg.getText());
        bem.setNamabrg(txtNamaBrg.getText());
        bem.setJenis(chbJenis.getSelectionModel().getSelectedItem());
        bem.setHarga(Double.parseDouble(txtHarga.getText()));
        bem.setJumlah(Integer.parseInt(txtJumlah.getText()));

        FXML_MenuController.dtElektronik.setBarangElektronikModel(bem);
        double harga = Double.parseDouble(txtHarga.getText());
        Integer jumlah = Integer.parseInt(txtJumlah.getText());

        if (editData) {
            //cek jumlah dan harga tidak boleh 0
            if (harga <= 0) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Harga tidak boleh 0", ButtonType.OK);
                a.showAndWait();
            } else if (jumlah <= 0) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang tidak boleh 0", ButtonType.OK);
                a.showAndWait();
            } // Jika kodebrg ada di table customer dan nama serta jenisnya sudah sesuai,
            // maka barang bisa diupdate
            else if (FXML_MenuController.dtBarang.validasi(bem
                    .getKodebrg(), ldc.getUser(FXML_PilihCustomerController.user)) > 0
                    && (txtNamaBrg.getText().equals(FXML_MenuController.dtBarang.cariNamaBarang(bem
                            .getKodebrg(), ldc.getUser(FXML_PilihCustomerController.user)))
                    && chbJenis.getSelectionModel().getSelectedItem().equals(FXML_MenuController.dtBarang.cariJenis((bem.kodebrg), ldc.getUser(FXML_PilihCustomerController.user))))) {
                if (FXML_MenuController.dtElektronik.update()) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan", ButtonType.OK);
                    a.showAndWait();
                    batalKlik(event);
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal disimpan", ButtonType.OK);
                    a.showAndWait();
                }
            } //Tapi Jika kodebrg ada di table customer maka barang tidak bisa diupdate
            // akan disesuaikan dahulu secara otomatis nama barang dan jenisnya ,
            //Lalu setelah sama baru bisa disimpan (tidak boleh dirubah nama barang serta jenisnya)
            else if (FXML_MenuController.dtBarang.validasi(bem
                    .getKodebrg(), ldc.getUser(FXML_PilihCustomerController.user)) > 0) {
                txtNamaBrg.setText(FXML_MenuController.dtBarang.cariNamaBarang(bem
                        .getKodebrg(), ldc.getUser(FXML_PilihCustomerController.user)));
                chbJenis.setValue(FXML_MenuController.dtBarang.cariJenis(bem
                        .getKodebrg(), ldc.getUser(FXML_PilihCustomerController.user)));
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Kodebrg sudah ada pada table lain tidak boleh mengganti namabrg dan jenisnya . . . ", ButtonType.OK);
                a.showAndWait();
                Alert b = new Alert(Alert.AlertType.INFORMATION, "Silahkan Simpan lagi", ButtonType.OK);
                b.showAndWait();
                txtNamaBrg.requestFocus();
            } //Jika kode barang tidak ada ditabel lain bisa langsung diupdate
            else if (FXML_MenuController.dtElektronik.update()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil diubah", ButtonType.OK);
                a.showAndWait();
                txtKodeBrg.setEditable(true);
                batalKlik(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal diubah", ButtonType.OK);
                a.showAndWait();
            }
        } //Jika bukan edit data dan kodebrg sudah ada pada tabel ini maka akan 
        // menampilkan pesan error bahwa data sudah ada
        else {
            if (FXML_MenuController.dtElektronik.validasi(bem
                    .getKodebrg()) > 0) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data sudah ada", ButtonType.OK);
                a.showAndWait();
                txtKodeBrg.requestFocus();
            } //cek jumlah dan harga tidak boleh 0
            else if (harga <= 0) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Harga tidak boleh 0", ButtonType.OK);
                a.showAndWait();
            } else if (jumlah <= 0) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang tidak boleh 0", ButtonType.OK);
                a.showAndWait();
            } //namun jika kode barang tidak ada pada table ini dan table customer maka
            //bisa menambahkan data    
            else if (FXML_MenuController.dtBarang.validasi(bem
                    .getKodebrg(), ldc.getUser(FXML_PilihCustomerController.user)) <= 0
                    && FXML_MenuController.dtElektronik.validasi(bem
                            .getKodebrg()) <= 0) {
                if (FXML_MenuController.dtElektronik.insert()) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan", ButtonType.OK);
                    a.showAndWait();
                    batalKlik(event);
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal disimpan", ButtonType.OK);
                    a.showAndWait();
                }
            } //Jika kode barang ada pada table customer dan namabarang serta jenisnya sudah sama,
            // sama maka data bisa disimpan.
            else if (FXML_MenuController.dtBarang.validasi(bem
                    .getKodebrg(), ldc.getUser(FXML_PilihCustomerController.user)) > 0
                    && txtNamaBrg.getText().equals(FXML_MenuController.dtBarang.cariNamaBarang(bem
                            .getKodebrg(), ldc.getUser(FXML_PilihCustomerController.user)))
                    && chbJenis.getSelectionModel().getSelectedItem().equals(FXML_MenuController.dtBarang.cariJenis(bem
                            .getKodebrg(), ldc.getUser(FXML_PilihCustomerController.user)))) {
                if (FXML_MenuController.dtElektronik.insert()) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan", ButtonType.OK);
                    a.showAndWait();
                    batalKlik(event);
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal disimpan", ButtonType.OK);
                    a.showAndWait();
                }
            } //Jika kode barang ada pada table customer dan namabarang serta jenisnya tidak sama,
            //maka akan secara otomatis diload namabarang dan juga jenis dari tabel customer,
            else if (FXML_MenuController.dtBarang.validasi(bem
                    .getKodebrg(), ldc.getUser(FXML_PilihCustomerController.user)) > 0) {
                txtNamaBrg.setText(FXML_MenuController.dtBarang.cariNamaBarang(bem
                        .getKodebrg(), ldc.getUser(FXML_PilihCustomerController.user)));
                chbJenis.setValue(FXML_MenuController.dtBarang.cariJenis(bem
                        .getKodebrg(), ldc.getUser(FXML_PilihCustomerController.user)));
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Kodebrg sudah ada pada table lain, reload nama barang dan jenis . . . ", ButtonType.OK);
                a.showAndWait();
                Alert b = new Alert(Alert.AlertType.INFORMATION, "Silahkan Simpan lagi", ButtonType.OK);
                b.showAndWait();
                txtKodeBrg.requestFocus();
            }
        }
    }

    @FXML
    private void batalKlik(ActionEvent event) {
        txtKodeBrg.setText("");
        txtNamaBrg.setText("");
        chbJenis.getSelectionModel().select(0);
        txtHarga.setText("");
        txtJumlah.setText("");

        txtNamaBrg.requestFocus();
    }

    @FXML
    private void keluarKlik(ActionEvent event) {
        btnKeluar.getScene().getWindow().hide();
    }

}
