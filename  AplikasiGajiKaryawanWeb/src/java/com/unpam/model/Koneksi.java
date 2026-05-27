package com.unpam.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String database = "jdbc:mysql://localhost/dbaplikasigajikaryawan";
    private static final String user = "root";
    private static final String password = "";
    private String pesanKesalahan;

    public String getPesanKesalahan() { return pesanKesalahan; }

    public Connection getConnection() {
        Connection connection = null;
        pesanKesalahan = "";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            pesanKesalahan = "Driver tidak ditemukan\n" + ex;
        }
        if (pesanKesalahan.equals("")) {
            try {
                connection = DriverManager.getConnection(
                    database + "?user=" + user + "&password=" + password);
            } catch (SQLException ex) {
                pesanKesalahan = "Koneksi gagal\n" + ex;
            }
        }
        return connection;
    }
}