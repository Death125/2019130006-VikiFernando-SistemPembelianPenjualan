/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tokobuku;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import sistempembelianpenjualan.FXML_MenuController;

/**
 * FXML Controller class
 *
 *
 */
public class FXML_DataPenjualanBukuController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private TableView<PenjualanBukuModel> tbvPenjualan;
    @FXML
    private TableView<DetilPenjualanBukuModel> tbvDetilPenjualan;
    @FXML
    private Button btnKembali;
    @FXML
    private TextField searchData;
    @FXML
    private TextField txtNoJual;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ShowDataPenjualanBuku();
        tbvPenjualan.getSelectionModel().selectFirst();
        setData();
    }

    public void ShowDataDetilPenjualan() {
        ObservableList<DetilPenjualanBukuModel> data = FXML_MenuController.dtDetilPenjualanBuku.Load(tbvPenjualan.getSelectionModel().getSelectedItem().nojual);
        if (data != null) {
            tbvDetilPenjualan.getColumns().clear();
            tbvDetilPenjualan.getItems().clear();

            TableColumn col = new TableColumn("nojual");
            col.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, String>("nojual"));
            tbvDetilPenjualan.getColumns().addAll(col);

            col = new TableColumn("kodebrg");
            col.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, String>("kodebrg"));
            tbvDetilPenjualan.getColumns().addAll(col);

            col = new TableColumn("namabrg");
            col.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, String>("namabrg"));
            tbvDetilPenjualan.getColumns().addAll(col);

            col = new TableColumn("jenis");
            col.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, String>("jenis"));
            tbvDetilPenjualan.getColumns().addAll(col);

            col = new TableColumn("Harga");
            col.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, Double>("harga"));
            tbvDetilPenjualan.getColumns().addAll(col);

            col = new TableColumn("jumlah");
            col.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, Integer>("jumlah"));
            tbvDetilPenjualan.getColumns().addAll(col);

            col = new TableColumn("total");
            col.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, Float>("total"));
            tbvDetilPenjualan.getColumns().addAll(col);
            tbvDetilPenjualan.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();
            tbvPenjualan.getScene().getWindow().hide();
        }
    }

    public void ShowDataPenjualanBuku() {
        ObservableList<PenjualanBukuModel> data = FXML_MenuController.dtPenjualanBuku.Load();
        if (data != null) {
            tbvPenjualan.getColumns().clear();
            tbvPenjualan.getItems().clear();
            TableColumn col = new TableColumn("nojual");
            col.setCellValueFactory(new PropertyValueFactory<PenjualanBukuModel, String>("nojual"));
            tbvPenjualan.getColumns().addAll(col);
            col = new TableColumn("tanggal");
            col.setCellValueFactory(new PropertyValueFactory<PenjualanBukuModel, String>("tanggal"));
            tbvPenjualan.getColumns().addAll(col);
            col = new TableColumn("username");
            col.setCellValueFactory(new PropertyValueFactory<PenjualanBukuModel, String>("username"));
            tbvPenjualan.getColumns().addAll(col);

            tbvPenjualan.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();
            tbvPenjualan.getScene().getWindow().hide();
        }
    }

    @FXML
    private void sebelumKlikPenjualan(ActionEvent event) {
        tbvPenjualan.getSelectionModel().selectAboveCell();
        tbvPenjualan.requestFocus();
        setData();
    }

    @FXML
    private void awalKlikPenjualan(ActionEvent event) {
        tbvPenjualan.getSelectionModel().selectFirst();
        tbvPenjualan.requestFocus();
        setData();
    }

    @FXML
    private void sesudahKlikPenjualan(ActionEvent event) {
        tbvPenjualan.getSelectionModel().selectBelowCell();
        tbvPenjualan.requestFocus();
        setData();
    }

    @FXML
    private void akhirKlikPenjualan(ActionEvent event) {
        tbvPenjualan.getSelectionModel().selectLast();
        tbvPenjualan.requestFocus();
        setData();
    }

    @FXML
    private void sebelumKlikDetilPenjualan(ActionEvent event) {
        tbvDetilPenjualan.getSelectionModel().selectAboveCell();
        tbvDetilPenjualan.requestFocus();
    }

    @FXML
    private void awalKlikDetilPenjualan(ActionEvent event) {
        tbvDetilPenjualan.getSelectionModel().selectFirst();
        tbvDetilPenjualan.requestFocus();
    }

    @FXML
    private void sesudahKlikDetilPenjualan(ActionEvent event) {
        tbvDetilPenjualan.getSelectionModel().selectBelowCell();
        tbvDetilPenjualan.requestFocus();
    }

    @FXML
    private void akhirKlikDetilPenjualan(ActionEvent event) {
        tbvDetilPenjualan.getSelectionModel().selectLast();
        tbvDetilPenjualan.requestFocus();
    }

    @FXML
    private void kembaliKlik(ActionEvent event) {
        btnKembali.getScene().getWindow().hide();
    }

    @FXML
    private void cariData(KeyEvent event) {
        PenjualanBukuModel s = new PenjualanBukuModel();
        String key = searchData.getText();
        if (key != "") {
            ObservableList<PenjualanBukuModel> data = FXML_MenuController.dtPenjualanBuku.CariJual(key);
            ObservableList<DetilPenjualanBukuModel> data2 = FXML_MenuController.dtDetilPenjualanBuku.CariDetil(key);
            if (data != null) {
                tbvPenjualan.getColumns().clear();
                tbvPenjualan.getItems().clear();
                TableColumn col = new TableColumn("nojual");
                col.setCellValueFactory(new PropertyValueFactory<PenjualanBukuModel, String>("nojual"));
                tbvPenjualan.getColumns().addAll(col);
                col = new TableColumn("tanggal");
                col.setCellValueFactory(new PropertyValueFactory<PenjualanBukuModel, String>("tanggal"));
                tbvPenjualan.getColumns().addAll(col);
                col = new TableColumn("username");
                col.setCellValueFactory(new PropertyValueFactory<PenjualanBukuModel, String>("username"));
                tbvPenjualan.getColumns().addAll(col);

                tbvPenjualan.setItems(data);

                tbvDetilPenjualan.getColumns().clear();
                tbvDetilPenjualan.getItems().clear();
                TableColumn col2 = new TableColumn("nojual");
                col2.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, String>("nojual"));
                tbvDetilPenjualan.getColumns().addAll(col2);

                col2 = new TableColumn("kodebrg");
                col2.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, String>("kodebrg"));
                tbvDetilPenjualan.getColumns().addAll(col2);

                col2 = new TableColumn("namabrg");
                col2.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, String>("namabrg"));
                tbvDetilPenjualan.getColumns().addAll(col2);

                col2 = new TableColumn("jenis");
                col2.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, String>("jenis"));
                tbvDetilPenjualan.getColumns().addAll(col2);

                col2 = new TableColumn("Harga");
                col2.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, Double>("harga"));
                tbvDetilPenjualan.getColumns().addAll(col2);

                col2 = new TableColumn("jumlah");
                col2.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, Integer>("jumlah"));
                tbvDetilPenjualan.getColumns().addAll(col2);

                col2 = new TableColumn("total");
                col2.setCellValueFactory(new PropertyValueFactory<DetilPenjualanBukuModel, Float>("total"));
                tbvDetilPenjualan.getColumns().addAll(col2);

                tbvDetilPenjualan.setItems(data2);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
                a.showAndWait();
                tbvPenjualan.getScene().getWindow().hide();
            }
        } else {
            ShowDataPenjualanBuku();
        }
    }

    @FXML
    public void setData() {
        ShowDataDetilPenjualan();
        txtNoJual.setText(tbvPenjualan.getSelectionModel().getSelectedItem().getNojual());
    }

    @FXML
    private void hapusDataPenjualanKlik(ActionEvent event) {
        PenjualanBukuModel pbum = new PenjualanBukuModel();
        pbum = tbvPenjualan.getSelectionModel().getSelectedItem();

        if (pbum != null) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Apakah anda mau menghapus data penjualan beserta detilnya ?", ButtonType.YES, ButtonType.NO);
            a.showAndWait();

            if (a.getResult() == ButtonType.YES) {
                if (FXML_MenuController.dtDetilPenjualanBuku.deleteall(txtNoJual.getText())) {
                    FXML_MenuController.dtPenjualanBuku.delete(txtNoJual.getText());
                    Alert b = new Alert(Alert.AlertType.INFORMATION, "Data berhasil dihapus", ButtonType.OK);
                    b.showAndWait();
                } else {
                    Alert b = new Alert(Alert.AlertType.ERROR, "Data gagal dihapus", ButtonType.OK);
                    b.showAndWait();
                }

                ShowDataPenjualanBuku();
                tbvPenjualan.getSelectionModel().selectFirst();
                setData();
            }
        } else {
            Alert b = new Alert(Alert.AlertType.WARNING, "Pilih data yang ingin anda hapus", ButtonType.OK);
            b.showAndWait();
        }
    }

}
