package com.unpam.model;

import java.sql.*;

public class Pekerjaan {
    private String kodePekerjaan, namaPekerjaan;
    private int jumlahTugas;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();

    public String getKodePekerjaan() { return kodePekerjaan; }
    public void setKodePekerjaan(String k) { this.kodePekerjaan = k; }
    public String getNamaPekerjaan() { return namaPekerjaan; }
    public void setNamaPekerjaan(String n) { this.namaPekerjaan = n; }
    public int getJumlahTugas() { return jumlahTugas; }
    public void setJumlahTugas(int j) { this.jumlahTugas = j; }
    public String getPesan() { return pesan; }
    public Object[][] getList() { return list; }

    public boolean baca(String kode) {
        boolean adaKesalahan = false;
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "select * from tbpekerjaan where kodepekerjaan=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, kode);
                ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getRow() > 0) {
                    this.kodePekerjaan = rs.getString("kodepekerjaan");
                    this.namaPekerjaan = rs.getString("namapekerjaan");
                    this.jumlahTugas = rs.getInt("jumlahtugas");
                } else {
                    adaKesalahan = true;
                    pesan = "Kode pekerjaan tidak ditemukan";
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
                String sql = "select * from tbpekerjaan";
                PreparedStatement ps = connection.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = ps.executeQuery();
                rs.last();
                list = new Object[rs.getRow()][3];
                rs.first();
                int i = 0;
                do {
                    list[i++] = new Object[]{
                        rs.getString("kodepekerjaan"),
                        rs.getString("namapekerjaan"),
                        rs.getInt("jumlahtugas")
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
                String sqlCek = "select * from tbpekerjaan where kodepekerjaan=?";
                PreparedStatement ps = connection.prepareStatement(sqlCek);
                ps.setString(1, kodePekerjaan);
                ResultSet rs = ps.executeQuery();
                rs.next();
                String sql;
                if (rs.getRow() > 0) {
                    sql = "update tbpekerjaan set namapekerjaan=?, jumlahtugas=? where kodepekerjaan=?";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, namaPekerjaan);
                    ps.setInt(2, jumlahTugas);
                    ps.setString(3, kodePekerjaan);
                } else {
                    sql = "insert into tbpekerjaan values(?,?,?)";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, kodePekerjaan);
                    ps.setString(2, namaPekerjaan);
                    ps.setInt(3, jumlahTugas);
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

    public boolean hapus(String kode) {
        boolean adaKesalahan = false;
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "delete from tbpekerjaan where kodepekerjaan=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, kode);
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