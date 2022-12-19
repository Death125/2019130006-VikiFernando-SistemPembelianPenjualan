/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokobuku;

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
public class DB_PenjualanBuku {

    private PenjualanBukuModel dt = new PenjualanBukuModel();

    public PenjualanBukuModel getPenjualanBukuModel() {
        return (dt);
    }

    public void setPenjualanBukuModel(PenjualanBukuModel pbum) {
        dt = pbum;
    }

    public ObservableList<PenjualanBukuModel> Load() {
        try {
            ObservableList<PenjualanBukuModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();

            ResultSet rs = con.statement.executeQuery("Select nojual, tanggal, username from penjualan_buku");

            int i = 1;
            while (rs.next()) {
                PenjualanBukuModel d = new PenjualanBukuModel();
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
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from penjualan_buku where nojual = '" + nomor + "'");
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
            con.preparedStatement = con.dbKoneksi.prepareStatement("insert into penjualan_buku (nojual, tanggal,username) values (?,?,?)");
            con.preparedStatement.setString(1, getPenjualanBukuModel().getNojual());
            con.preparedStatement.setDate(2, getPenjualanBukuModel().getTanggal());
            con.preparedStatement.setString(3, getPenjualanBukuModel().getUsername());
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
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from penjualan_buku where nojual  = ? ");
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

    public ObservableList<PenjualanBukuModel> CariJual(String kode) {
        try {
            ObservableList<PenjualanBukuModel> tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = (Statement) con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select nojual, tanggal, username from penjualan_buku WHERE nojual LIKE '" + kode + "%'");

            int i = 1;
            while (rs.next()) {
                PenjualanBukuModel d = new PenjualanBukuModel();
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
