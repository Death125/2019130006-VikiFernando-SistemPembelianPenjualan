/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemlogin;

import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sistempembelianpenjualan.Koneksi;

/**
 *
 *
 */
public class DB_LoginData {

    /*Getter Setter Barang Model*/
    private LoginDataModel dt = new LoginDataModel();

    public LoginDataModel getLoginDataModel() {
        return (dt);
    }

    public void setLoginDataModel(LoginDataModel ldm) {
        dt = ldm;
    }

    public ObservableList<LoginDataModel> Load() {
        try {
            ObservableList<LoginDataModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT * FROM login_data WHERE Username NOT IN ('admin')");

            int i = 1;
            while (rs.next()) {
                LoginDataModel d = new LoginDataModel();
                d.setUsername(rs.getString("Username"));
                d.setNotelp(rs.getString("notelp"));
                d.setEmail(rs.getString("Email"));
                d.setPassword(rs.getString("Password"));

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

    public boolean update() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("UPDATE login_data SET notelp =?, Email = ?, Password = ? WHERE Username = ? ;");
            con.preparedStatement.setString(1, getLoginDataModel().getNotelp());
            con.preparedStatement.setString(2, getLoginDataModel().getEmail());
            con.preparedStatement.setString(3, getLoginDataModel().getPassword());
            con.preparedStatement.setString(4, getLoginDataModel().getUsername());
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
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from login_data where Username = ? ");
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

    public boolean deleteTable(String tableName) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("DROP table " + tableName + ";");

            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean deleteDetil(String tableName) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();;
            con.preparedStatement = con.dbKoneksi.prepareStatement("DROP table detil_penjualan_" + tableName + ";");

            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean deletePenjualan(String tableName) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();;
            con.preparedStatement = con.dbKoneksi.prepareStatement("DROP table penjualan_" + tableName + ";");

            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public ObservableList<LoginDataModel> CariBrg(String username, String email) {
        try {
            ObservableList<LoginDataModel> tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = (Statement) con.dbKoneksi.createStatement();
            ResultSet rs = (ResultSet) con.statement.executeQuery("select * from login_data WHERE Username LIKE '" + username + "%' OR email LIKE '" + email + "%'");
            int i = 1;
            while (rs.next()) {
                LoginDataModel d = new LoginDataModel();
                d.setUsername(rs.getString("Username"));
                d.setEmail(rs.getString("Email"));
                d.setNotelp(rs.getString("notelp"));
                d.setPassword(rs.getString("Password"));

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
