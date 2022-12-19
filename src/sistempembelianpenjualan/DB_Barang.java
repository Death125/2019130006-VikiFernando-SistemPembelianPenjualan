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
public class DB_Barang {

    /*Getter Setter Barang Model*/
    private BarangModel dt = new BarangModel();

    public BarangModel getBarangModel() {
        return (dt);
    }

    public void setBarangModel(BarangModel bm) {
        dt = bm;
    }

    /**/

 /*Load Barang Model*/
    public ObservableList<BarangModel> Load(String customerName) {
        try {
            ObservableList<BarangModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select kodebrg, namabrg, jenis, harga, jumlah from "
                    + customerName.toLowerCase() + ";");

            int i = 1;
            while (rs.next()) {
                BarangModel d = new BarangModel();
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

    public ObservableList<BarangModel> LoadBuku(String customerName) {
        try {
            ObservableList<BarangModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select kodebrg, namabrg, jenis, harga, jumlah from "
                    + customerName.toLowerCase() + " WHERE jenis = 'Komik' OR jenis = 'Novel' OR jenis = 'Majalah' "
                    + "OR jenis = 'Manga' OR jenis = 'Ensiklopedia' OR jenis = 'Buku Pelajaran';");

            int i = 1;
            while (rs.next()) {
                BarangModel d = new BarangModel();
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

    public ObservableList<BarangModel> LoadElektronik(String customerName) {
        try {
            ObservableList<BarangModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select kodebrg, namabrg, jenis, harga, jumlah from "
                    + customerName.toLowerCase() + " WHERE jenis = 'Elektronik' OR jenis = 'Peralatan Listrik';");

            int i = 1;
            while (rs.next()) {
                BarangModel d = new BarangModel();
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

 /*Validasi Barang*/
    public int validasi(String nomor, String namaCustomer) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from " + namaCustomer.toLowerCase() + " WHERE kodebrg = '" + nomor + "';");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public int validasiData(String namaCustomer) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from " + namaCustomer.toLowerCase() + ";");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public int validasiIfItemsExist(String namaCustomer) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT count(*) kodebrg \n"
                    + "FROM keranjang_data \n"
                    + "WHERE kodebrg NOT IN \n"
                    + "    (SELECT kodebrg \n"
                    + "     FROM " + namaCustomer + ");");
            while (rs.next()) {
                val = rs.getInt("kodebrg");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public int validasiJualBuku(String namaCustomer) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT count(*) namabrg from " + namaCustomer + " WHERE jenis "
                    + "IN('Komik','Novel','Majalah','Manga','Ensiklopedia','Buku Pelajaran') ;");
            while (rs.next()) {
                val = rs.getInt("namabrg");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public int validasiJualBarangElektronik(String namaCustomer) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT count(*) namabrg from " + namaCustomer + " WHERE jenis "
                    + "IN('Elektronik','Peralatan Listrik') ;");
            while (rs.next()) {
                val = rs.getInt("namabrg");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    /**/

 /*Cari Data*/
    public String cariNamaBarang(String kodeBrg, String namaCustomer) {
        String nama = "";
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select namabrg from " + namaCustomer.toLowerCase() + " WHERE kodebrg = '" + kodeBrg + "';");
            while (rs.next()) {
                nama = rs.getString("namabrg");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nama;
    }

    public String cariJenis(String kodeBrg, String namaCustomer) {
        String jenis = "";
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select jenis from " + namaCustomer.toLowerCase() + " WHERE kodebrg = '" + kodeBrg + "';");
            while (rs.next()) {
                jenis = rs.getString("jenis");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jenis;
    }

    public ObservableList<BarangModel> CariBrg(String kode, String nama, String namaCustomer) {
        try {
            ObservableList<BarangModel> tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = (Statement) con.dbKoneksi.createStatement();
            ResultSet rs = (ResultSet) con.statement.executeQuery("select * from " + namaCustomer.toLowerCase() + " WHERE kodebrg LIKE '" + kode + "%' OR namabrg LIKE '" + nama + "%'");
            int i = 1;
            while (rs.next()) {
                BarangModel d = new BarangModel();
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

 /*Insert Update Delete barang*/
    public boolean insert(String namaCustomer) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("insert into " + namaCustomer.toLowerCase() + "(kodebrg,namabrg, jenis, harga, jumlah) values (?,?,?,?,?);");
            con.preparedStatement.setString(1, getBarangModel().getKodebrg());
            con.preparedStatement.setString(2, getBarangModel().getNamabrg());
            con.preparedStatement.setString(3, getBarangModel().getJenis());
            con.preparedStatement.setDouble(4, getBarangModel().getHarga());
            con.preparedStatement.setInt(5, getBarangModel().getJumlah());
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

    public boolean addItem(String namaCustomer) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(" INSERT into " + namaCustomer + " (kodebrg,namabrg, jenis,harga,jumlah) \n"
                    + "SELECT kodebrg,namabrg,jenis,harga,jumlah \n"
                    + "FROM keranjang_data \n"
                    + "WHERE kodebrg NOT IN \n"
                    + "    (SELECT kodebrg \n"
                    + "     FROM " + namaCustomer + ");"
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

    public boolean delete(String nomor, String namaCustomer) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from " + namaCustomer.toLowerCase() + " where kodebrg = ? ");
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

    public boolean deleteBarangBasedOnQuantity(String namaCustomer) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from " + namaCustomer.toLowerCase() + " where jumlah <= 0 ");

            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean update(String namaCustomer) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("UPDATE " + namaCustomer.toLowerCase() + " SET namabrg = ?, jenis =?, harga = ?, jumlah=? WHERE kodebrg = ? ;");
            con.preparedStatement.setString(1, getBarangModel().getNamabrg());
            con.preparedStatement.setString(2, getBarangModel().getJenis());
            con.preparedStatement.setDouble(3, getBarangModel().getHarga());
            con.preparedStatement.setInt(4, getBarangModel().getJumlah());
            con.preparedStatement.setString(5, getBarangModel().getKodebrg());
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

 /*Add, Reduce, Update Quantity(jumlah)*/
    public boolean addQuantity(String namaCustomer) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(" UPDATE " + namaCustomer + " as a \n"
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

    public boolean reduceQuantity(String namaCustomer) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(" UPDATE " + namaCustomer + " as a \n"
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

    public int getQuantity(String namaCustomer) {
        int jumlah = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select jumlah from " + namaCustomer.toLowerCase() + " WHERE jumlah <= 0 ;");
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
    final DB_Login ldb = new DB_Login();

    public void print() {
        Koneksi con = new Koneksi();
        String is = "./src/sistempembelianpenjualan/laporanBarang.jrxml";

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
