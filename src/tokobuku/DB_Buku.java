/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokobuku;

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
public class DB_Buku {

    /*Buku Model*/
    private BukuModel dt = new BukuModel();

    public BukuModel getBukuModel() {
        return (dt);
    }

    public void setBukuModel(BukuModel bum) {
        dt = bum;
    }

    /**/

 /*Load Buku*/
    public ObservableList<BukuModel> Load() {
        try {
            ObservableList<BukuModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select kodebrg, namabrg, jenis, harga, jumlah from toko_buku");

            int i = 1;
            while (rs.next()) {
                BukuModel d = new BukuModel();
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

 /*validasi buku*/
    public int validasi(String nomor) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from toko_buku WHERE kodebrg = '" + nomor + "';");
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
                    + "     FROM toko_buku);");
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

 /*Cari data Buku*/
    public String cariNamaBarang(String kodeBrg) {
        String nama = "";
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select namabrg from toko_buku WHERE kodebrg = '" + kodeBrg + "';");
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
            ResultSet rs = con.statement.executeQuery("select jenis from toko_buku WHERE kodebrg = '" + kodeBrg + "';");
            while (rs.next()) {
                jenis = rs.getString("jenis");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jenis;
    }

    public ObservableList<BukuModel> CariBrg(String kode, String nama) {
        try {
            ObservableList<BukuModel> tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = (Statement) con.dbKoneksi.createStatement();
            ResultSet rs = (ResultSet) con.statement.executeQuery("select * from toko_buku WHERE kodebrg LIKE '" + kode + "%' OR namabrg LIKE '" + nama + "%'");
            int i = 1;
            while (rs.next()) {
                BukuModel d = new BukuModel();
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

 /*Insert, update, delete buku*/
    public boolean insert() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("insert into toko_buku(kodebrg,namabrg, jenis, harga, jumlah) values (?,?,?,?,?)");
            con.preparedStatement.setString(1, getBukuModel().getKodebrg());
            con.preparedStatement.setString(2, getBukuModel().getNamabrg());
            con.preparedStatement.setString(3, getBukuModel().getJenis());
            con.preparedStatement.setDouble(4, getBukuModel().getHarga());
            con.preparedStatement.setInt(5, getBukuModel().getJumlah());
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
            con.preparedStatement = con.dbKoneksi.prepareStatement(" INSERT into toko_buku (kodebrg,namabrg, jenis,harga,jumlah) \n"
                    + "SELECT kodebrg,namabrg,jenis,harga,jumlah \n"
                    + "FROM keranjang_data \n"
                    + "WHERE kodebrg NOT IN \n"
                    + "    (SELECT kodebrg \n"
                    + "     FROM toko_buku);"
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
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from toko_buku where kodebrg = ? ");
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
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from toko_buku where jumlah <= 0 ");

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
            con.preparedStatement = con.dbKoneksi.prepareStatement("UPDATE toko_buku SET namabrg = ?, jenis =?, harga = ?, jumlah=? WHERE kodebrg = ? ;");
            con.preparedStatement.setString(1, getBukuModel().getNamabrg());
            con.preparedStatement.setString(2, getBukuModel().getJenis());
            con.preparedStatement.setDouble(3, getBukuModel().getHarga());
            con.preparedStatement.setInt(4, getBukuModel().getJumlah());
            con.preparedStatement.setString(5, getBukuModel().getKodebrg());
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

 /*Add, Reduce, Update Quantity*/
    public boolean addQuantity() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(" UPDATE toko_buku as a \n"
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
            con.preparedStatement = con.dbKoneksi.prepareStatement(" UPDATE toko_buku as a \n"
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
            ResultSet rs = con.statement.executeQuery("select jumlah from toko_buku WHERE jumlah <= 0 ;");
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
    public void print() {
        Koneksi con = new Koneksi();
        String is = "./src/tokobuku/laporanBuku.jasper";

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
