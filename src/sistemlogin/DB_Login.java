/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemlogin;

import java.sql.ResultSet;
import java.sql.SQLException;
import sistempembelianpenjualan.Koneksi;

/**
 *
 *
 */
public class DB_Login {

    /*Validasi Data*/
    public static int validasiUsername(String username) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT count(*) as jml FROM login_data WHERE (username = BINARY '" + username + "' OR email = BINARY '" + username + "')");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public static int validasiPassword(String password) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT count(*) as jml FROM login_data WHERE password = BINARY '" + password + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    /**/

 /*Get User Data*/
    public static String getUser(String username) {
        String user = "";
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT username FROM login_data WHERE username = BINARY '" + username + "'"
                    + "OR email = BINARY '" + username + "'");
            while (rs.next()) {
                user = rs.getString("username");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static String adminBuku() {
        String val = "";
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT username FROM login_data WHERE email = BINARY 'buku33@gmail.com'");
            while (rs.next()) {
                val = rs.getString("username");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public static String adminElektronik() {
        String val = "";
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT username FROM login_data WHERE email = BINARY 'elektronik33@gmail.com'");
            while (rs.next()) {
                val = rs.getString("username");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }
    /**/
}
