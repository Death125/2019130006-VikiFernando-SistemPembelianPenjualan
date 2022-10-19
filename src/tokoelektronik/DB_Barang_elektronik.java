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
public class DB_Barang_elektronik {

    /*Barang Elektronik Model*/
    private BarangElektronikModel dt = new BarangElektronikModel();

    public BarangElektronikModel getBarangElektronikModel() {
        return (dt);
    }

    public void setBarangElektronikModel(BarangElektronikModel bem) {
        dt = bem;
    }

    /**/

 /*Load Barang Elektronik*/
    public ObservableList<BarangElektronikModel> Load() {
        try {
            ObservableList<BarangElektronikModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select kodebrg, namabrg, jenis, harga, jumlah from toko_elektronik");

            int i = 1;
            while (rs.next()) {
                BarangElektronikModel d = new BarangElektronikModel();
                d.setKodebrg(rs.getString("kodebrg"));
                d.setNamabrg(rs.getString("namabrg"));
                d.setJenis(rs.getString("jenis"));
                d.setHarga(rs.getDouble("harga"));
                d.setJumlah(rs.getInt("jumlah"));

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

    /**/

 /*Validasi Barang Elektronik*/
    public int validasi(String nomor) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from toko_elektronik WHERE kodebrg = '" + nomor + "';");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public int validasiIfItemsExist() {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT count(*) kodebrg \n"
                    + "FROM keranjang_data \n"
                    + "WHERE kodebrg NOT IN \n"
                    + "    (SELECT kodebrg \n"
                    + "     FROM toko_elektronik);");
            while (rs.next()) {
                val = rs.getInt("kodebrg");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    /**/

 /*Cari Data Barang Elektronik*/
    public String cariNamaBarang(String kodeBrg) {
        String nama = "";
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select namabrg from toko_elektronik WHERE kodebrg = '" + kodeBrg + "';");
            while (rs.next()) {
                nama = rs.getString("namabrg");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nama;
    }

    public String cariJenis(String kodeBrg) {
        String jenis = "";
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select jenis from toko_elektronik WHERE kodebrg = '" + kodeBrg + "';");
            while (rs.next()) {
                jenis = rs.getString("jenis");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jenis;
    }

    public ObservableList<BarangElektronikModel> CariBrg(String kode, String nama) {
        try {
            ObservableList<BarangElektronikModel> tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = (Statement) con.dbKoneksi.createStatement();
            ResultSet rs = (ResultSet) con.statement.executeQuery("select * from toko_elektronik WHERE kodebrg LIKE '" + kode + "%' OR namabrg LIKE '" + nama + "%'");
            int i = 1;
            while (rs.next()) {
                BarangElektronikModel d = new BarangElektronikModel();
                d.setKodebrg(rs.getString("kodebrg"));
                d.setNamabrg(rs.getString("namabrg"));
                d.setJenis(rs.getString("jenis"));
                d.setHarga(rs.getDouble("harga"));
                d.setJumlah(rs.getInt("jumlah"));

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

    /**/

 /*Insert, Update, Delete Barang Elektronik*/
    public boolean insert() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("insert into toko_elektronik(kodebrg,namabrg, jenis, harga, jumlah) values (?,?,?,?,?)");
            con.preparedStatement.setString(1, getBarangElektronikModel().getKodebrg());
            con.preparedStatement.setString(2, getBarangElektronikModel().getNamabrg());
            con.preparedStatement.setString(3, getBarangElektronikModel().getJenis());
            con.preparedStatement.setDouble(4, getBarangElektronikModel().getHarga());
            con.preparedStatement.setInt(5, getBarangElektronikModel().getJumlah());
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

    public boolean addItem() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(" INSERT into toko_elektronik (kodebrg,namabrg, jenis,harga,jumlah) \n"
                    + "SELECT kodebrg,namabrg,jenis,harga,jumlah \n"
                    + "FROM keranjang_data \n"
                    + "WHERE kodebrg NOT IN \n"
                    + "    (SELECT kodebrg \n"
                    + "     FROM toko_elektronik);"
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

    public boolean delete(String nomor) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();;
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from toko_elektronik where kodebrg = ? ");
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

    public boolean deleteBarangBasedOnQuantity() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from toko_elektronik where jumlah <= 0 ");

            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean update() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("UPDATE toko_elektronik SET namabrg = ?, jenis =?, harga = ?, jumlah=? WHERE kodebrg = ? ;");
            con.preparedStatement.setString(1, getBarangElektronikModel().getNamabrg());
            con.preparedStatement.setString(2, getBarangElektronikModel().getJenis());
            con.preparedStatement.setDouble(3, getBarangElektronikModel().getHarga());
            con.preparedStatement.setInt(4, getBarangElektronikModel().getJumlah());
            con.preparedStatement.setString(5, getBarangElektronikModel().getKodebrg());
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

    /**/

 /*Add, Remove, Update Quantity*/
    public boolean addQuantity() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(" UPDATE toko_elektronik as a \n"
                    + "INNER JOIN keranjang_data as b \n"
                    + "ON a.kodebrg= b.kodebrg \n"
                    + "SET a.jumlah= a.jumlah + b.jumlah"
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

    public boolean reduceQuantity() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(" UPDATE toko_elektronik as a \n"
                    + "INNER JOIN keranjang_data as b \n"
                    + "ON a.kodebrg= b.kodebrg \n"
                    + "SET a.jumlah= a.jumlah - b.jumlah"
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

    public int getQuantity() {
        int jumlah = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select jumlah from toko_elektronik WHERE jumlah <= 0 ;");
            while (rs.next()) {
                jumlah = rs.getInt("jumlah");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jumlah;
    }
    /**/
}
