/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistempembelianpenjualan;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 *
 */
public class DB_Customer {

    private CustomerModel dt = new CustomerModel();

    public CustomerModel getCustomerModel() {
        return (dt);
    }

    public void setCustomerModel(CustomerModel cm) {
        dt = cm;
    }

    public ObservableList<CustomerModel> Load() {
        try {
            ObservableList<CustomerModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select Username from login_data"
                    + " WHERE NOT Username='buku' AND NOT Username='elektronik' ORDER BY Username ASC; ");

            int i = 1;
            while (rs.next()) {
                CustomerModel d = new CustomerModel();
                d.setUsername(rs.getString("Username"));

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
}
