/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistempembelianpenjualan;

import sistemlogin.DB_Login;
import sistemlogin.LoginMenuController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import tokoelektronik.DB_Barang_elektronik;
import tokobuku.DB_Buku;
import tokobuku.DB_DetilPenjualanBuku;
import tokobuku.DB_PenjualanBuku;
import tokobuku.FXML_DisplayBukuController;
import tokoelektronik.DB_DetilPenjualanBarangElektronik;
import tokoelektronik.DB_PenjualanBarangElektronik;
import tokoelektronik.FXML_DisplayBarang_elektronikController;
import transaksi.DB_Transaksi;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXML_MenuController implements Initializable {

    //Scene & JFrame
    JFrame f;
    private Stage stage;
    private Scene scene;

    //Database
    final DB_Login ldb = new DB_Login();
    public static DB_Barang dtBarang = new DB_Barang();
    public static DB_Barang_elektronik dtElektronik = new DB_Barang_elektronik();
    public static DB_Buku dtBuku = new DB_Buku();
    public static DB_Customer dtCustomer = new DB_Customer();
    public static DB_Transaksi dtTransaksi = new DB_Transaksi();

    public static DB_Penjualan dtPenjualan = new DB_Penjualan();
    public static DB_PenjualanBuku dtPenjualanBuku = new DB_PenjualanBuku();
    public static DB_PenjualanBarangElektronik dtPenjualanBarangElektronik = new DB_PenjualanBarangElektronik();

    public static DB_DetilPenjualan dtDetilPenjualan = new DB_DetilPenjualan();
    public static DB_DetilPenjualanBuku dtDetilPenjualanBuku = new DB_DetilPenjualanBuku();
    public static DB_DetilPenjualanBarangElektronik dtDetilPenjualanBarangElektronik = new DB_DetilPenjualanBarangElektronik();

    //Loader
    public FXMLLoader loaderTokoBuku;
    public FXMLLoader loaderTokoElektronik;
    public FXMLLoader loaderTokoCustomer;

    public static boolean isBuku = false;
    public static boolean isElektronik = false;
    public static boolean isCustomer = false;

    @FXML
    private Menu dataCust;
    @FXML
    private MenuItem miInputBarang;
    @FXML
    private MenuItem miInputBuku;
    @FXML
    private MenuItem miInputElektronik;
    @FXML
    private MenuItem miDetilPenjualan;
    @FXML
    private MenuItem tokoElektronik;
    @FXML
    private MenuItem tokoBuku;
    @FXML
    private MenuItem barangCustomer;
    @FXML
    private Menu toko;
    @FXML
    private Button belanjaKeTokoLain;
    @FXML
    private Button logoutButton;
    @FXML
    private MenuItem miDetilPenjualanTokoBuku;
    @FXML
    private MenuItem miDetilPenjualanTokoElektronik;
    @FXML
    private MenuItem lblLaporanBarang;
    @FXML
    private MenuItem lblLaporanBuku;
    @FXML
    private MenuItem lblLaporanElektronik;
    @FXML
    private MenuItem lblLaporanPenjualan;
    @FXML
    private MenuItem lblLaporanPenjualanTokoBuku;
    @FXML
    private MenuItem lblLaporanPenjualanTokoElektronik;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButtonStyle();

        deleteAllTransaction();
        checkLogin();
    }

    void addButtonStyle() {
        belanjaKeTokoLain.getStyleClass().add("buttonStyle2");
        logoutButton.getStyleClass().add("buttonStyle3");
    }

    void deleteAllTransaction() {
        FXML_MenuController.dtTransaksi.deleteAll();
    }

    public void checkLogin() {
        switch (ldb.getUser(LoginMenuController.Username)) {
            case "buku":
                belanjaKeTokoLain.setVisible(true);
                lblLaporanBuku.setVisible(true);
                lblLaporanPenjualanTokoBuku.setVisible(true);

                miInputBarang.setVisible(false);
                miInputElektronik.setVisible(false);
                miDetilPenjualan.setVisible(false);
                miDetilPenjualanTokoElektronik.setVisible(false);
                tokoElektronik.setVisible(false);
                lblLaporanBarang.setVisible(false);
                lblLaporanElektronik.setVisible(false);
                lblLaporanPenjualan.setVisible(false);
                lblLaporanPenjualanTokoElektronik.setVisible(false);

                dataCust.setText("Toko");
                toko.setText("Buku");
                tokoBuku.setText("Data buku");
                barangCustomer.setText("Toko " + ldb.getUser(FXML_PilihCustomerController.user).toLowerCase());
                break;
            case "elektronik":
                belanjaKeTokoLain.setVisible(true);
                lblLaporanElektronik.setVisible(true);
                lblLaporanPenjualanTokoElektronik.setVisible(true);

                miInputBarang.setVisible(false);
                miInputBuku.setVisible(false);
                miDetilPenjualan.setVisible(false);
                miDetilPenjualanTokoBuku.setVisible(false);
                tokoBuku.setVisible(false);
                lblLaporanBarang.setVisible(false);
                lblLaporanBuku.setVisible(false);
                lblLaporanPenjualan.setVisible(false);
                lblLaporanPenjualanTokoBuku.setVisible(false);

                dataCust.setText("Toko");
                toko.setText("Elektronik");
                tokoElektronik.setText("Data barang elektronik");
                barangCustomer.setText("Toko " + ldb.getUser(FXML_PilihCustomerController.user).toLowerCase());
                break;
            default:
                miInputBarang.setVisible(true);
                miDetilPenjualan.setVisible(true);
                lblLaporanBarang.setVisible(true);
                lblLaporanPenjualan.setVisible(true);

                miInputBuku.setVisible(false);
                miDetilPenjualanTokoElektronik.setVisible(false);
                miDetilPenjualanTokoBuku.setVisible(false);
                miInputElektronik.setVisible(false);
                belanjaKeTokoLain.setVisible(false);
                lblLaporanBuku.setVisible(false);
                lblLaporanElektronik.setVisible(false);
                lblLaporanPenjualanTokoBuku.setVisible(false);
                lblLaporanPenjualanTokoElektronik.setVisible(false);

                miInputBarang.setText("Input barang " + ldb.getUser(LoginMenuController.Username).toLowerCase());
                miDetilPenjualan.setText("Data penjualan " + ldb.getUser(LoginMenuController.Username).toLowerCase());
                dataCust.setText("Data " + ldb.getUser(LoginMenuController.Username).toLowerCase());
                lblLaporanBarang.setText("Laporan barang " + ldb.getUser(LoginMenuController.Username).toLowerCase());
                break;
        }
    }

    @FXML
    private void inputBarangKlik(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_InputBarang.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Input Barang");
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setIconified(false);
            stg.setScene(scene);

            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);

            stg.centerOnScreen();
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void inputBukuKlik(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tokobuku/FXML_InputBuku.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Input Buku");
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setIconified(false);
            stg.setScene(scene);

            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);

            stg.centerOnScreen();
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void inputElektronikKlik(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tokoelektronik/FXML_InputBarangElektronik.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Input Barang Elektronik");
            stg.initModality(Modality.APPLICATION_MODAL);
            // stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stg.centerOnScreen();
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void dataBarangKlik(ActionEvent event) {
        try {
            isCustomer = true;
            isBuku = false;
            isElektronik = false;

            Stage stg = new Stage();

            switch (ldb.getUser(LoginMenuController.Username)) {
                case "buku":
                    stg.setTitle("Toko " + ldb.getUser(FXML_PilihCustomerController.user));
                    FXML_DisplayBukuController.tokoBuku = true;
                    FXML_DisplayBarang_elektronikController.tokoElektronik = false;
                    loaderTokoCustomer = new FXMLLoader(getClass().getResource("FXML_CustomerHome.fxml"));
                    break;
                case "elektronik":
                    stg.setTitle("Toko " + ldb.getUser(FXML_PilihCustomerController.user));
                    FXML_DisplayBarang_elektronikController.tokoElektronik = true;
                    FXML_DisplayBukuController.tokoBuku = false;
                    loaderTokoCustomer = new FXMLLoader(getClass().getResource("FXML_CustomerHome.fxml"));
                    break;
                default:
                    stg.setTitle("Data Barang " + ldb.getUser(LoginMenuController.Username));
                    loaderTokoCustomer = new FXMLLoader(getClass().getResource("FXML_DisplayBarang.fxml"));
                    break;
            }

            Parent root = (Parent) loaderTokoCustomer.load();
            Scene scene = new Scene(root);
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setIconified(false);
            stg.setScene(scene);

            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            String css2 = this.getClass().getResource("/Css/tablestyle.css").toExternalForm();
            scene.getStylesheets().add(css2);

            stg.centerOnScreen();
            stg.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void tokoElektronikKlik(ActionEvent event) {
        try {
            isElektronik = true;
            isBuku = false;
            isCustomer = false;

            Stage stg = new Stage();
            if (ldb.getUser(LoginMenuController.Username).equals("elektronik")) {
                stg.setTitle("Data Elektronik");
                loaderTokoElektronik = new FXMLLoader(getClass().getResource("/tokoelektronik/FXML_DisplayBarang_elektronik.fxml"));
            } else {
                stg.setTitle("Toko Elektronik");
                loaderTokoElektronik = new FXMLLoader(getClass().getResource("/tokoelektronik/FXML_ElektronikHome.fxml"));
            }

            Parent root = (Parent) loaderTokoElektronik.load();
            Scene scene = new Scene(root);
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setIconified(false);
            stg.setScene(scene);

            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            String css2 = this.getClass().getResource("/Css/tablestyle.css").toExternalForm();
            scene.getStylesheets().add(css2);

            stg.centerOnScreen();
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void tokoBukuKlik(ActionEvent event) {
        try {
            isBuku = true;
            isElektronik = false;
            isCustomer = false;

            Stage stg = new Stage();

            if (ldb.getUser(LoginMenuController.Username).equals("buku")) {
                stg.setTitle("Data Buku");
                loaderTokoBuku = new FXMLLoader(getClass().getResource("/tokobuku/FXML_DisplayBuku.fxml"));
            } else {
                stg.setTitle("Toko Buku");
                loaderTokoBuku = new FXMLLoader(getClass().getResource("/tokobuku/FXML_BukuHome.fxml"));
            }

            Parent root = (Parent) loaderTokoBuku.load();
            Scene scene = new Scene(root);
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setIconified(false);
            stg.setScene(scene);

            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            String css2 = this.getClass().getResource("/Css/tablestyle.css").toExternalForm();
            scene.getStylesheets().add(css2);

            stg.centerOnScreen();
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void detilPenjualanKlik(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_DataPenjualan.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Input Barang Elektronik");
            stg.initModality(Modality.APPLICATION_MODAL);
            // stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stg.centerOnScreen();
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void belanjaKeTokoLainKlik(ActionEvent event) throws IOException {
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/shopIcon.png"));
        Object[] options = {"Ya, pergi ke toko lain",
            "Tidak, tetap disini"};

        int jawab = JOptionPane.showOptionDialog(f,
                "Mau Pergi ke toko lain ?",
                "Pergi",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, icon, options, options[1]);

        if (jawab == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(f, "Silahkan Pilih Toko yang ingin anda tuju :) ");
            Parent root = FXMLLoader.load(getClass().getResource("FXML_PilihCustomer.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Pilih Customer");
            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.centerOnScreen();
            stage.show();
        }
    }

    @FXML
    private void logoutKlik(ActionEvent event) throws IOException {
        int jawab = JOptionPane.showOptionDialog(f,
                "Logout From This Account ?",
                "Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (jawab == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(f, "Logout berhasil");
            Parent root = FXMLLoader.load(getClass().getResource("/sistemlogin/LoginMenu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.centerOnScreen();
            stage.show();

            isBuku = false;
            isElektronik = false;
            isCustomer = false;
            FXML_DisplayBarangController.customer = true;
        }
    }

    @FXML
    private void detilPenjualanTokoBukuKlik(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tokobuku/FXML_DataPenjualanBuku.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Input Barang Elektronik");
            stg.initModality(Modality.APPLICATION_MODAL);
            // stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stg.centerOnScreen();
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void detilPenjualanTokoElektronikKlik(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tokoelektronik/FXML_DataPenjualanBarangElektronik.fxml"));

            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Input Barang Elektronik");
            stg.initModality(Modality.APPLICATION_MODAL);
            // stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            String css = this.getClass().getResource("/Css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stg.centerOnScreen();
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void laporanBarangKlik(ActionEvent event) {
        dtBarang.print();
    }

    @FXML
    private void laporanBukuKlik(ActionEvent event) {
        dtBuku.print();
    }

    @FXML
    private void laporanElektronikKlik(ActionEvent event) {
        dtElektronik.print();
    }

    @FXML
    private void laporanPenjualanKlik(ActionEvent event) {
        dtDetilPenjualan.print();
    }

    @FXML
    private void laporanPenjualanTokoBukuKlik(ActionEvent event) {
        dtDetilPenjualanBuku.print();
    }

    @FXML
    private void laporanPenjualanTokoElektronikKlik(ActionEvent event) {
        dtDetilPenjualanBarangElektronik.print();
    }
}
