/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemlogin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import sistempembelianpenjualan.Koneksi;

/**
 *
 *
 */
public class DB_Register {

    String[] randomGoods = {
        "('E0031','Kipas Angin', 'Elektronik', 140000, 1)",
        "('N0031','Harry Potter', 'Novel', 34000, 1)",
        "('BP0032','Sejarah', 'Buku Pelajaran', 44000, 1)",
        "('BP0033','Kimia', 'Buku Pelajaran', 74000, 1)",
        "('E0032','Kulkas', 'Elektronik', 1200000, 1)",
        "('K0034','Naruto', 'Komik', 34000, 1)",
        "('E0033','Dispenser', 'Elektronik', 72000, 1)"
    };

    public String getRandomGoods() {
        Random rand = new Random();
        int index = rand.nextInt(randomGoods.length);

        return randomGoods[index];
    }

//    public void checkGoods(){
//        //
//        if(){
//            
//        }else{
//            
//        }
//    }
    public static int validasiUsername(String username) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT count(*) as jml FROM login_data WHERE username = BINARY '" + username + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public static int validasiEmail(String email) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT count(*) as jml FROM login_data WHERE email = BINARY '" + email + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public boolean CreateNewGoodsData(String customerName) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(" CREATE TABLE " + customerName + " (\n"
                    + "    kodebrg VARCHAR (20) NOT NULL ,\n "
                    + "    namabrg VARCHAR (50) NOT NULL,\n "
                    + "    jenis VARCHAR (35) NOT NULL,\n "
                    + "    harga DOUBLE NOT NULL,\n"
                    + "    jumlah INT(5) NOT NULL,\n"
                    + "    PRIMARY KEY (kodebrg)\n"
                    + ");");
            berhasil = true;
            con.preparedStatement.executeUpdate();

            if (berhasil == true) {
                con.preparedStatement = con.dbKoneksi.prepareStatement(
                        "INSERT INTO " + customerName
                        + " VALUES " + getRandomGoods() + ";"
                );
                con.preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            berhasil = false;
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }
}
