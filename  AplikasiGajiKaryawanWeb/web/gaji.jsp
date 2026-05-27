<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.unpam.model.Karyawan"%>
<!DOCTYPE html>
<html>
<head>
    <title>Transaksi Gaji</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<div id="header"><h1>Aplikasi Gaji Karyawan</h1></div>
<div id="menu">
    <a href="index.jsp">Home</a>
    <a href="LoginController">Login</a>
    <a href="KaryawanController">Karyawan</a>
    <a href="PekerjaanController">Pekerjaan</a>
    <a href="GajiController">Gaji</a>
</div>
<div id="content">
    <h2>Transaksi Gaji</h2>
    <% String pesan = (String) request.getAttribute("pesan");
       if(pesan != null) { %>
        <p style="color:green;"><%= pesan %></p>
    <% } %>

    <h3>Cari Karyawan</h3>
    <form action="GajiController" method="get">
        KTP: <input type="text" name="ktp"/>
        <input type="submit" value="Cari"/>
    </form>

    <%
        Karyawan k = (Karyawan) request.getAttribute("karyawan");
        if(k != null && k.getNama() != null) {
    %>
    <h3>Karyawan: <%= k.getNama() %> | Ruang: <%= k.getRuang() %></h3>

    <form action="GajiController?action=simpan" method="post">
        <input type="hidden" name="ktp" value="<%= k.getKtp() %>"/>
        <table>
            <tr>
                <th>Kode Pekerjaan</th>
                <th>Nama Pekerjaan</th>
                <th>Gaji Bersih</th>
                <th>Gaji Kotor</th>
                <th>Tunjangan</th>
            </tr>
            <%
                Object[][] listGaji = (Object[][]) request.getAttribute("listGaji");
                Object[][] listPekerjaan = (Object[][]) request.getAttribute("listPekerjaan");
                int jumlahBaris = 3;
                for(int i = 0; i < jumlahBaris; i++) {
                    String kode = "", nama = "", bersih = "", kotor = "", tunj = "";
                    if(listGaji != null && i < listGaji.length && listGaji[i] != null) {
                        kode = listGaji[i][0] != null ? listGaji[i][0].toString() : "";
                        nama = listGaji[i][1] != null ? listGaji[i][1].toString() : "";
                        bersih = listGaji[i][2] != null ? listGaji[i][2].toString() : "";
                        kotor = listGaji[i][3] != null ? listGaji[i][3].toString() : "";
                        tunj = listGaji[i][4] != null ? listGaji[i][4].toString() : "";
                    }
            %>
            <tr>
                <td>
                    <select name="kodepekerjaan">
                        <option value="">-- Pilih --</option>
                        <% if(listPekerjaan != null) {
                            for(Object[] p : listPekerjaan) { %>
                        <option value="<%= p[0] %>"
                            <%= p[0].toString().equals(kode) ? "selected" : "" %>>
                            <%= p[0] %>
                        </option>
                        <% }} %>
                    </select>
                </td>
                <td><%= nama %></td>
                <td><input type="text" name="gajibersih" value="<%= bersih %>" size="10"/></td>
                <td><input type="text" name="gajikotor" value="<%= kotor %>" size="10"/></td>
                <td><input type="text" name="tunjangan" value="<%= tunj %>" size="10"/></td>
            </tr>
            <% } %>
        </table>
        <br/>
        <input type="submit" value="Simpan Gaji"/>
    </form>
    <% } %>
</div>
</body>
</html>