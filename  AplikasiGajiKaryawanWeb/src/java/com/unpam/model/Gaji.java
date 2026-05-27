package com.unpam.model;

import java.sql.*;

public class Gaji {
    private String ktp, kodePekerjaan;
    private double gajiBersih, gajiKotor, tunjangan;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();

    public String getKtp() { return ktp; }
    public void setKtp(String ktp) { this.ktp = ktp; }
    public String getKodePekerjaan() { return kodePekerjaan; }
    public void setKodePekerjaan(String k) { this.kodePekerjaan = k; }
    public double getGajiBersih() { return gajiBersih; }
    public void setGajiBersih(double g) { this.gajiBersih = g; }
    public double getGajiKotor() { return gajiKotor; }
    public void setGajiKotor(double g) { this.gajiKotor = g; }
    public double getTunjangan() { return tunjangan; }
    public void setTunjangan(double t) { this.tunjangan = t; }
    public String getPesan() { return pesan; }
    public Object[][] getList() { return list; }

    public boolean bacaData(String ktp) {
        boolean adaKesalahan = false;
        Connection connection;
        list = new Object[0][0];
        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "select g.*, p.namapekerjaan from tbgaji g "
                           + "join tbpekerjaan p on g.kodepekerjaan=p.kodepekerjaan "
                           + "where g.ktp=?";
                PreparedStatement ps = connection.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, ktp);
                ResultSet rs = ps.executeQuery();
                rs.last();
                list = new Object[rs.getRow()][6];
                if (rs.getRow() > 0) {
                    rs.first();
                    int i = 0;
                    do {
                        list[i++] = new Object[]{
                            rs.getString("kodepekerjaan"),
                            rs.getString("namapekerjaan"),
                            rs.getDouble("gajibersih"),
                            rs.getDouble("gajikotor"),
                            rs.getDouble("tunjangan")
                        };
                    } while (rs.next());
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

    public boolean simpan(String ktp, String[] kodes,
            String[] bersih, String[] kotor, String[] tunjangans) {
        boolean adaKesalahan = false;
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            try {
                PreparedStatement ps = connection.prepareStatement(
                    "delete from tbgaji where ktp=?");
                ps.setString(1, ktp);
                ps.executeUpdate();

                for (int i = 0; i < kodes.length; i++) {
                    if (!kodes[i].equals("")) {
                        ps = connection.prepareStatement(
                            "insert into tbgaji(ktp,kodepekerjaan,gajibersih,gajikotor,tunjangan) values(?,?,?,?,?)");
                        ps.setString(1, ktp);
                        ps.setString(2, kodes[i]);
                        ps.setDouble(3, bersih[i].equals("") ? 0 : Double.parseDouble(bersih[i]));
                        ps.setDouble(4, kotor[i].equals("") ? 0 : Double.parseDouble(kotor[i]));
                        ps.setDouble(5, tunjangans[i].equals("") ? 0 : Double.parseDouble(tunjangans[i]));
                        ps.executeUpdate();
                    }
                }
                connection.close();
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