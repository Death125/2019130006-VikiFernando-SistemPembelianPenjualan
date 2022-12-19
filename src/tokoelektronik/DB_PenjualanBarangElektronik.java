/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokoelektronik;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sistempembelianpenjualan.Koneksi;

/**
 *
 *
 */
public class DB_PenjualanBarangElektronik {

    private PenjualanBarangElektronikModel dt = new PenjualanBarangElektronikModel();

    public PenjualanBarangElektronikModel getPenjualanBarangElektronikModel() {
        return (dt);
    }

    public void setPenjualanBarangElektronikModel(PenjualanBarangElektronikModel pbem) {
        dt = pbem;
    }

    public ObservableList<PenjualanBarangElektronikModel> Load() {
        try {
            ObservableList<PenjualanBarangElektronikModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();

            ResultSet rs = con.statement.executeQuery("Select nojual, tanggal, username from penjualan_elektronik");

            int i = 1;
            while (rs.next()) {
                PenjualanBarangElektronikModel d = new PenjualanBarangElektronikModel();
                d.setNojual(rs.getString("nojual"));
                d.setTanggal(rs.getDate("tanggal"));
                d.setUsername(rs.getString("username"));

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

    public int validasi(String nomor) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from penjualan_elektronik where nojual = '" + nomor + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public boolean insert() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("insert into penjualan_elektronik (nojual, tanggal,username) values (?,?,?)");
            con.preparedStatement.setString(1, getPenjualanBarangElektronikModel().getNojual());
            con.preparedStatement.setDate(2, getPenjualanBarangElektronikModel().getTanggal());
            con.preparedStatement.setString(3, getPenjualanBarangElektronikModel().getUsername());
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            berhasil = false;
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean delete(String nomor) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();;
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from penjualan_elektronik where nojual  = ? ");
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

    public ObservableList<PenjualanBarangElektronikModel> CariJual(String kode) {
        try {
            ObservableList<PenjualanBarangElektronikModel> tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = (Statement) con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select nojual, tanggal, username from penjualan_elektronik WHERE nojual LIKE '" + kode + "%'");

            int i = 1;
            while (rs.next()) {
                PenjualanBarangElektronikModel d = new PenjualanBarangElektronikModel();
                d.setNojual(rs.getString("nojual"));
                d.setTanggal(rs.getDate("tanggal"));
                d.setUsername(rs.getString("username"));
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
}
