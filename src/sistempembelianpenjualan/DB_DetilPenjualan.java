/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistempembelianpenjualan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import sistemlogin.DB_Login;
import sistemlogin.LoginMenuController;

/**
 *
 *
 */
public class DB_DetilPenjualan {

    private DetilPenjualanModel dt = new DetilPenjualanModel();

    public DetilPenjualanModel DetilPenjualanModel() {
        return (dt);
    }

    public void DetilPenjualanModel(DetilPenjualanModel dpm) {
        dt = dpm;
    }

    public ObservableList<DetilPenjualanModel> Load(String kode, String customerName) {
        try {
            ObservableList<DetilPenjualanModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select j.nojual,b.kodebrg,b.namabrg, b.jenis, b.harga, b.jumlah"
                    + " from detil_penjualan_" + customerName.toLowerCase() + " b join penjualan_" + customerName.toLowerCase() + " j ON(b.nojual = j.nojual) WHERE j.nojual = '" + kode + "'");

            int i = 1;
            while (rs.next()) {
                DetilPenjualanModel d = new DetilPenjualanModel();
                d.setNojual(rs.getString("nojual"));
                d.setKodebrg(rs.getString("kodebrg"));
                d.setNamabrg(rs.getString("namabrg"));
                d.setJenis(rs.getString("jenis"));
                d.setHarga(rs.getDouble("harga"));
                d.setJumlah(rs.getInt("jumlah"));

                float total = 0;
                int jumlah = rs.getInt("jumlah");
                double harga = rs.getDouble("harga");

                total = jumlah * (float) harga;
                d.setTotal(total);
                tableData.add(d);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int validasi(String nomor, String customerName) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from detil_penjualan_" + customerName.toLowerCase() + " where nojual = '" + nomor + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public boolean insertIntoDetil(String customerName) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("INSERT into detil_penjualan_" + customerName.toLowerCase() + " (nojual, kodebrg, namabrg, jenis, harga,jumlah) \n"
                    + "SELECT nojual, kodebrg, namabrg, jenis, harga, jumlah \n"
                    + "FROM keranjang_data;"
            );

            con.preparedStatement.execute();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            berhasil = false;
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean delete(String nomor, String kode, String customerName) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();;
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from detil_penjualan_" + customerName.toLowerCase() + " where nojual  = ? and kodebrg = ?");
            con.preparedStatement.setString(1, nomor);
            con.preparedStatement.setString(2, kode);
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean deleteall(String nomor, String customerName) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();;
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from detil_penjualan_" + customerName.toLowerCase() + " where nojual  = ? ");
            con.preparedStatement.setString(1, nomor);
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public ObservableList<DetilPenjualanModel> CariDetil(String kode, String customerName) {
        try {
            ObservableList<DetilPenjualanModel> tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = (Statement) con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select j.nojual,b.kodebrg,b.namabrg, b.jenis, b.harga, b.jumlah"
                    + " from detil_penjualan_" + customerName.toLowerCase() + " b join penjualan_" + customerName.toLowerCase() + " j ON(b.nojual = j.nojual) WHERE j.nojual = '" + kode + "'");

            int i = 1;
            while (rs.next()) {
                DetilPenjualanModel d = new DetilPenjualanModel();
                d.setNojual(rs.getString("nojual"));
                d.setKodebrg(rs.getString("kodebrg"));
                d.setNamabrg(rs.getString("namabrg"));
                d.setJenis(rs.getString("jenis"));
                d.setHarga(rs.getDouble("harga"));
                d.setJumlah(rs.getInt("jumlah"));

                float total = 0;
                int jumlah = rs.getInt("jumlah");
                double harga = rs.getDouble("harga");

                total = jumlah * (float) harga;
                d.setTotal(total);
                tableData.add(d);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    final DB_Login ldb = new DB_Login();

    public void print() {
        Koneksi con = new Koneksi();
        String is = "./src/sistempembelianpenjualan/laporanPenjualan.jrxml";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("getTable", ldb.getUser(LoginMenuController.Username).toLowerCase());

        con.bukaKoneksi();
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(is);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, con.dbKoneksi);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        con.tutupKoneksi();
    }

}
