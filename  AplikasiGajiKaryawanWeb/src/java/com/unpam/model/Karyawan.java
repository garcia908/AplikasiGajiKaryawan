package com.unpam.model;

import java.sql.*;

public class Karyawan {
    private String ktp, nama, password;
    private int ruang;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();

    public String getKtp() { return ktp; }
    public void setKtp(String ktp) { this.ktp = ktp; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public int getRuang() { return ruang; }
    public void setRuang(int ruang) { this.ruang = ruang; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPesan() { return pesan; }
    public Object[][] getList() { return list; }

    public boolean baca(String ktp) {
        boolean adaKesalahan = false;
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "select * from tbkaryawan where ktp=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, ktp);
                ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getRow() > 0) {
                    this.ktp = rs.getString("ktp");
                    this.nama = rs.getString("nama");
                    this.ruang = rs.getInt("ruang");
                    this.password = rs.getString("password");
                } else {
                    adaKesalahan = true;
                    pesan = "KTP tidak ditemukan";
                }
                ps.close(); rs.close(); connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Error: " + ex.getMessage();
            }
        } else {
            adaKesalahan = true;
            pesan = koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }

    public boolean bacaData() {
        boolean adaKesalahan = false;
        Connection connection;
        list = new Object[0][0];
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "select ktp, nama, ruang from tbkaryawan";
                PreparedStatement ps = connection.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = ps.executeQuery();
                rs.last();
                list = new Object[rs.getRow()][3];
                rs.first();
                int i = 0;
                do {
                    list[i++] = new Object[]{
                        rs.getString("ktp"),
                        rs.getString("nama"),
                        rs.getInt("ruang")
                    };
                } while (rs.next());
                ps.close(); rs.close(); connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Error: " + ex.getMessage();
            }
        } else {
            adaKesalahan = true;
            pesan = koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }

    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sqlCek = "select * from tbkaryawan where ktp=?";
                PreparedStatement ps = connection.prepareStatement(sqlCek);
                ps.setString(1, ktp);
                ResultSet rs = ps.executeQuery();
                rs.next();
                String sql;
                if (rs.getRow() > 0) {
                    sql = "update tbkaryawan set nama=?, ruang=?, password=? where ktp=?";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, nama);
                    ps.setInt(2, ruang);
                    ps.setString(3, password);
                    ps.setString(4, ktp);
                } else {
                    sql = "insert into tbkaryawan values(?,?,?,?)";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, ktp);
                    ps.setString(2, nama);
                    ps.setInt(3, ruang);
                    ps.setString(4, password);
                }
                ps.executeUpdate();
                ps.close(); rs.close(); connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Error: " + ex.getMessage();
            }
        } else {
            adaKesalahan = true;
            pesan = koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }

    public boolean hapus(String ktp) {
        boolean adaKesalahan = false;
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "delete from tbkaryawan where ktp=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, ktp);
                int n = ps.executeUpdate();
                if (n < 1) { adaKesalahan = true; pesan = "Data tidak ditemukan"; }
                ps.close(); connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Error: " + ex.getMessage();
            }
        } else {
            adaKesalahan = true;
            pesan = koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
}