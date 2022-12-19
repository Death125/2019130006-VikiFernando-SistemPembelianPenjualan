/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transaksi;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sistempembelianpenjualan.Koneksi;

/**
 *
 *
 */
public class DB_Transaksi {

    /*Transaksi Model*/
    private TransaksiModel dt = new TransaksiModel();

    public TransaksiModel getTransaksiModel() {
        return (dt);
    }

    public void setTransaksiModel(TransaksiModel tm) {
        dt = tm;
    }

    /**/

 /*Load Keranjang */
    public ObservableList<TransaksiModel> Load() {
        try {
            ObservableList<TransaksiModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select kodebrg, namabrg, harga, jumlah from keranjang_data");
            int i = 1;
            while (rs.next()) {
                TransaksiModel d = new TransaksiModel();
                d.setKodebrg(rs.getString("kodebrg"));
                d.setNamabrg(rs.getString("namabrg"));
                d.setHarga(rs.getDouble("harga"));
                d.setJumlah(rs.getInt("jumlah"));

                tableData.add(d);
                i++;
            }

            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**/

 /*Insert,Update, Delete Keranjang*/
    public boolean insert() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("insert into keranjang_data(nojual, kodebrg, namabrg ,jenis ,harga, jumlah) values (?,?,?,?,?,?);");
            con.preparedStatement.setString(1, getTransaksiModel().getNojual());
            con.preparedStatement.setString(2, getTransaksiModel().getKodebrg());
            con.preparedStatement.setString(3, getTransaksiModel().getNamabrg());
            con.preparedStatement.setString(4, getTransaksiModel().getJenis());
            con.preparedStatement.setDouble(5, getTransaksiModel().getHarga());
            con.preparedStatement.setInt(6, getTransaksiModel().getJumlah());

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

    public boolean deleteAll() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from keranjang_data ");

            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean delete(String namaBarang) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();;
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from keranjang_data where namabrg = ? ");
            con.preparedStatement.setString(1, namaBarang);

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
            con.preparedStatement = con.dbKoneksi.prepareStatement("UPDATE keranjang_data SET jumlah=? WHERE kodebrg = ? ;");
            con.preparedStatement.setInt(1, getTransaksiModel().getJumlah());
            con.preparedStatement.setString(2, getTransaksiModel().getKodebrg());

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

 /*Validasi Keranjang*/
    public int validasiKeranjang() {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from keranjang_data");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public int validasiNamaBarang(String kodeBarang) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from keranjang_data WHERE kodebrg = '" + kodeBarang + "';");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public int validasiJumlahBarang(String kodeBarang, String namaCustomer) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select jumlah from " + namaCustomer + " WHERE kodebrg = '" + kodeBarang + "' ;");
            while (rs.next()) {
                val = rs.getInt("jumlah");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public int validasiJumlahBuku(String kodeBarang) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select jumlah from toko_buku WHERE kodebrg = '" + kodeBarang + "' ;");
            while (rs.next()) {
                val = rs.getInt("jumlah");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public int validasiJumlahBarangElektronik(String kodeBarang) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select jumlah from toko_elektronik WHERE kodebrg = '" + kodeBarang + "' ;");
            while (rs.next()) {
                val = rs.getInt("jumlah");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public int validasiQuantityKeranjang(String kodeBarang) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select jumlah from keranjang_data WHERE kodebrg = '" + kodeBarang + "';");
            while (rs.next()) {
                val = rs.getInt("jumlah");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    /**/

 /*Add Quantity Keranjang*/
    public boolean addQuantity(String kodeBarang, int quantity) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("UPDATE keranjang_data SET jumlah = jumlah + " + quantity
                    + " WHERE kodebrg = '" + kodeBarang + "' ;");

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
}
