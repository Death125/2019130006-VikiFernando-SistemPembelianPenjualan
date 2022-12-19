/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistempembelianpenjualan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 *
 */
public class DB_Penjualan {

    private PenjualanModel dt = new PenjualanModel();

    public PenjualanModel getPenjualanModel() {
        return (dt);
    }

    public void setPenjualanModel(PenjualanModel pbm) {
        dt = pbm;
    }

    public ObservableList<PenjualanModel> Load(String customerName) {
        try {
            ObservableList<PenjualanModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();

            ResultSet rs = con.statement.executeQuery("Select nojual, tanggal, username from penjualan_" + customerName.toLowerCase() + ";");

            int i = 1;
            while (rs.next()) {
                PenjualanModel d = new PenjualanModel();
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

    public boolean insert(String customerName) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("insert into penjualan_" + customerName.toLowerCase() + " (nojual, tanggal,username) values (?,?,?)");
            con.preparedStatement.setString(1, getPenjualanModel().getNojual());
            con.preparedStatement.setDate(2, getPenjualanModel().getTanggal());
            con.preparedStatement.setString(3, getPenjualanModel().getUsername());
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

    public int validasi(String nomor, String customerName) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from penjualan_" + customerName.toLowerCase() + " where nojual = '" + nomor + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public boolean delete(String nomor, String customerName) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();;
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from penjualan_" + customerName.toLowerCase() + " where nojual  = ? ");
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

    public ObservableList<PenjualanModel> CariJual(String kode, String customerName) {
        try {
            ObservableList<PenjualanModel> tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = (Statement) con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select nojual, tanggal, username from penjualan_" + customerName.toLowerCase() + " WHERE nojual LIKE '" + kode + "%'");

            int i = 1;
            while (rs.next()) {
                PenjualanModel d = new PenjualanModel();
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
