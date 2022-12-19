/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistempembelianpenjualan;

import sistemlogin.DB_Login;
import sistemlogin.LoginMenuController;
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

/**
 * FXML Controller class
 *
 *
 */
public class FXML_InputBarangController implements Initializable {

    boolean editData = false;
    final DB_Login ldb = new DB_Login();

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
        addButtonStyle();

        chbJenis.setItems(FXCollections.observableArrayList("Komik", "Novel", "Majalah", "Manga", "Ensiklopedia", "Buku Pelajaran", "Elektronik", "Peralatan Listrik"));
        restrictTxtHarga();
        restrictTxtJumlah();

    }

    void addButtonStyle() {
        btnSimpan.getStyleClass().add("buttonStyle2");
        btnBatal.getStyleClass().add("buttonStyle2");
        btnKeluar.getStyleClass().add("buttonStyle2");
    }

    /*Validasi*/
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

    /**/
    public void execute(BarangModel bm) {
        if (!bm.getKodebrg().isEmpty()) {
            editData = true;
            txtKodeBrg.setText(bm.getKodebrg());
            txtNamaBrg.setText(bm.getNamabrg());
            chbJenis.setValue(bm.getJenis());
            txtHarga.setText(String.valueOf(bm.getHarga()));
            txtJumlah.setText(String.valueOf(bm.getJumlah()));

            txtKodeBrg.setEditable(false);
            txtNamaBrg.requestFocus();
        }
    }

    @FXML
    private void simpanKlik(ActionEvent event) {
        BarangModel bm = new BarangModel();

        bm.setKodebrg(txtKodeBrg.getText());
        bm.setNamabrg(txtNamaBrg.getText());
        bm.setJenis(chbJenis.getSelectionModel().getSelectedItem());
        bm.setHarga(Double.parseDouble(txtHarga.getText()));
        bm.setJumlah(Integer.parseInt(txtJumlah.getText()));

        FXML_MenuController.dtBarang.setBarangModel(bm);

        double harga = Double.parseDouble(txtHarga.getText());
        Integer jumlah = Integer.parseInt(txtJumlah.getText());

        if (editData) {

            //cek jumlah dan harga tidak boleh 0
            if (jumlah <= 0) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang tidak boleh 0", ButtonType.OK);
                a.showAndWait();
            } else if (harga <= 0) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Harga tidak boleh 0", ButtonType.OK);
                a.showAndWait();
            } // Jika kodebrg ada di table lain dan nama serta jenisnya sudah sesuai,
            // maka barang bisa diupdate
            else if ((FXML_MenuController.dtBuku.validasi(bm.getKodebrg()) > 0
                    || FXML_MenuController.dtElektronik.validasi(bm.getKodebrg()) > 0)
                    && (txtNamaBrg.getText().equals(FXML_MenuController.dtBuku.cariNamaBarang(bm.kodebrg))
                    || txtNamaBrg.getText().equals(FXML_MenuController.dtElektronik.cariNamaBarang(bm.kodebrg)))
                    && (chbJenis.getSelectionModel().getSelectedItem().equals(FXML_MenuController.dtBuku.cariJenis(bm.kodebrg))
                    || (chbJenis.getSelectionModel().getSelectedItem().equals(FXML_MenuController.dtElektronik.cariJenis(bm.kodebrg))))) {

                if (FXML_MenuController.dtBarang.update(ldb.getUser(LoginMenuController.Username))) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan", ButtonType.OK);
                    a.showAndWait();
                    batalKlik(event);
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal disimpan", ButtonType.OK);
                    a.showAndWait();
                }
            } //Tapi Jika kodebrg ada di table lain maka barang tidak bisa diupdate
            // akan disesuaikan dahulu secara otomatis nama barang dan jenisnya ,
            //Lalu setelah sama baru bisa disimpan (tidak boleh dirubah nama barang serta jenisnya)
            else if (FXML_MenuController.dtBuku.validasi(bm.getKodebrg()) > 0) {
                txtNamaBrg.setText(FXML_MenuController.dtBuku.cariNamaBarang(bm.kodebrg));
                chbJenis.setValue(FXML_MenuController.dtBuku.cariJenis(bm.kodebrg));

                Alert a = new Alert(Alert.AlertType.INFORMATION, "Kodebrg sudah ada pada table lain tidak boleh mengganti namabrg dan jenisnya . . . ", ButtonType.OK);
                a.showAndWait();
                Alert b = new Alert(Alert.AlertType.INFORMATION, "Silahkan Simpan lagi", ButtonType.OK);
                b.showAndWait();

                txtNamaBrg.requestFocus();
            } else if (FXML_MenuController.dtElektronik.validasi(bm.getKodebrg()) > 0) {
                txtNamaBrg.setText(FXML_MenuController.dtElektronik.cariNamaBarang(bm.kodebrg));
                chbJenis.setValue(FXML_MenuController.dtElektronik.cariJenis(bm.kodebrg));

                Alert a = new Alert(Alert.AlertType.INFORMATION, "Kodebrg sudah ada pada table lain tidak boleh mengganti namabrg dan jenisnya . . . ", ButtonType.OK);
                a.showAndWait();
                Alert b = new Alert(Alert.AlertType.INFORMATION, "Silahkan Simpan lagi", ButtonType.OK);
                b.showAndWait();

                txtNamaBrg.requestFocus();
            }//Jika kode barang tidak ada ditabel lain bisa langsung diupdate
            else if (FXML_MenuController.dtBarang.update(ldb.getUser(LoginMenuController.Username))) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil diubah", ButtonType.OK);
                a.showAndWait();

                txtKodeBrg.setEditable(true);
                batalKlik(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal diubah", ButtonType.OK);
                a.showAndWait();
            }
        } //Jika nama barang sudah ada pada table customer ini dan tidak ada ditable lain
        //maka akan ditampilkan pesan error bahwa data sudah ada 
        else {
            if (FXML_MenuController.dtBarang.validasi(bm.getKodebrg(), ldb.getUser(LoginMenuController.Username)) > 0) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data sudah ada", ButtonType.OK);
                a.showAndWait();

                txtKodeBrg.requestFocus();
            } //cek jumlah dan harga tidak boleh 0
            else if (jumlah <= 0) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Jumlah barang tidak boleh 0", ButtonType.OK);
                a.showAndWait();
            } else if (harga <= 0) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Harga tidak boleh 0", ButtonType.OK);
                a.showAndWait();
            } //Jika bukan edit data maka
            // Jika kodebarang tidak ada yang sama ditable manapun maka bisa langsung tambah data
            else if (FXML_MenuController.dtBarang.validasi(bm.getKodebrg(), ldb.getUser(LoginMenuController.Username)) <= 0
                    && FXML_MenuController.dtBuku.validasi(bm.getKodebrg()) <= 0
                    && FXML_MenuController.dtElektronik.validasi(bm.getKodebrg()) <= 0) {

                if (FXML_MenuController.dtBarang.insert(ldb.getUser(LoginMenuController.Username))) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan", ButtonType.OK);
                    a.showAndWait();

                    batalKlik(event);
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal disimpan", ButtonType.OK);
                    a.showAndWait();
                }
            } // Tapi Jika kodebrg ada di table lain maka barang tidak bisa ditambah
            // akan disesuaikan dahulu secara otomatis nama barang dan jenisnya ,
            //Lalu setelah sama baru bisa ditambah (nama barang dan jenis akan sama dengan yang ada ditable lain)
            else if ((FXML_MenuController.dtBuku.validasi(bm.getKodebrg()) > 0
                    || FXML_MenuController.dtElektronik.validasi(bm.getKodebrg()) > 0)
                    && (txtNamaBrg.getText().equals(FXML_MenuController.dtBuku.cariNamaBarang(bm.kodebrg))
                    || txtNamaBrg.getText().equals(FXML_MenuController.dtElektronik.cariNamaBarang(bm.kodebrg)))
                    && ((chbJenis.getSelectionModel().getSelectedItem().equals(FXML_MenuController.dtBuku.cariJenis(bm.kodebrg))
                    || (chbJenis.getSelectionModel().getSelectedItem().equals(FXML_MenuController.dtElektronik.cariJenis(bm.kodebrg)))))) {

                if (FXML_MenuController.dtBarang.insert(ldb.getUser(LoginMenuController.Username))) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan", ButtonType.OK);
                    a.showAndWait();

                    batalKlik(event);
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal disimpan", ButtonType.OK);
                    a.showAndWait();
                }
            } else {
                //Jika ada kode barang yang sama dengan tabel buku, maka akan disesuaikan dahulu 
                //nama barang dan jenisnya dengan table buku kemudian baru bisa disimpan.
                if (FXML_MenuController.dtBuku.validasi(bm.getKodebrg()) > 0) {
                    txtNamaBrg.setText(FXML_MenuController.dtBuku.cariNamaBarang(bm.kodebrg));
                    chbJenis.setValue(FXML_MenuController.dtBuku.cariJenis(bm.kodebrg));

                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Kodebrg sudah ada pada table lain, reload nama barang dan jenis . . . ", ButtonType.OK);
                    a.showAndWait();
                    Alert b = new Alert(Alert.AlertType.INFORMATION, "Silahkan Simpan lagi", ButtonType.OK);
                    b.showAndWait();

                    txtKodeBrg.requestFocus();
                } //Jika ada kode barang yang sama dengan tabel Elektronik, maka akan disesuaikan dahulu 
                //nama barang dan jenisnya dengan table elektronik kemudian baru bisa disimpan.
                else if (FXML_MenuController.dtElektronik.validasi(bm.getKodebrg()) > 0) {
                    txtNamaBrg.setText(FXML_MenuController.dtElektronik.cariNamaBarang(bm.kodebrg));
                    chbJenis.setValue(FXML_MenuController.dtElektronik.cariJenis(bm.kodebrg));

                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Kodebrg sudah ada pada table lain, reload nama barang dan jenis . . . ", ButtonType.OK);
                    a.showAndWait();
                    Alert b = new Alert(Alert.AlertType.INFORMATION, "Silahkan Simpan lagi", ButtonType.OK);
                    b.showAndWait();

                    txtKodeBrg.requestFocus();
                }
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
