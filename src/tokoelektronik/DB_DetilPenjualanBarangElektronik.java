/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokoelektronik;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import sistempembelianpenjualan.Koneksi;

/**
 *
 *
 */
public class DB_DetilPenjualanBarangElektronik {

    private DetilPenjualanBarangElektronikModel dt = new DetilPenjualanBarangElektronikModel();

    public DetilPenjualanBarangElektronikModel DetilPenjualanBarangElektronikModel() {
        return (dt);
    }

    public void DetilPenjualanBarangElektronikModel(DetilPenjualanBarangElektronikModel dpbem) {
        dt = dpbem;
    }

    public ObservableList<DetilPenjualanBarangElektronikModel> Load(String kode) {
        try {
            ObservableList<DetilPenjualanBarangElektronikModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select j.nojual,b.kodebrg,b.namabrg, b.jenis, b.harga, b.jumlah"
                    + " from detil_penjualan_tokoelektronik b join penjualan_elektronik j ON(b.nojual = j.nojual) WHERE j.nojual = '" + kode + "'");

            int i = 1;
            while (rs.next()) {
                DetilPenjualanBarangElektronikModel d = new DetilPenjualanBarangElektronikModel();
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

    public boolean insertIntoDetil() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("INSERT into detil_penjualan_tokoelektronik (nojual, kodebrg, namabrg, jenis, harga,jumlah) \n"
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

    public int validasi(String nomor) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from detil_penjualan_tokoelektronik where nojual = '" + nomor + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public boolean delete(String nomor, String kode) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();;
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from detil_penjualan_tokoelektronik where nojual  = ? and kodebrg = ?");
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

    public boolean deleteall(String nomor) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();;
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from detil_penjualan_tokoelektronik where nojual  = ? ");
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

    public ObservableList<DetilPenjualanBarangElektronikModel> CariDetil(String kode) {
        try {
            ObservableList<DetilPenjualanBarangElektronikModel> tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = (Statement) con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select j.nojual,b.kodebrg,b.namabrg, b.jenis, b.harga, b.jumlah"
                    + " from detil_penjualan_tokoelektronik b join penjualan_elektronik j ON(b.nojual = j.nojual) WHERE j.nojual = '" + kode + "'");
            int i = 1;
            while (rs.next()) {
                DetilPenjualanBarangElektronikModel d = new DetilPenjualanBarangElektronikModel();
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

    public void print() {
        Koneksi con = new Koneksi();
        String is = "./src/tokoelektronik/laporanPenjualanBarangElektronik.jasper";

        Map map = new HashMap();
        map.put("p_periode", "Desember");
        con.bukaKoneksi();
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(is, map, con.dbKoneksi);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        con.tutupKoneksi();
    }
}
