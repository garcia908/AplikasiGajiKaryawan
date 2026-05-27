<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
    <title>Data Karyawan</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<div id="header"><h1>Aplikasi Gaji Karyawan</h1></div>
<div id="menu">
    <a href="GajiController">Gaji</a>
    <a href="index.jsp">Home</a>
    <a href="KaryawanController">Karyawan</a>
    <a href="PekerjaanController">Pekerjaan</a>
    <a href="LoginController?action=logout">Logout</a>
</div>
<div id="content">
    <h2>Data Karyawan</h2>
    <% String pesan = (String) request.getAttribute("pesan");
       if(pesan != null) { %>
        <p style="color:green;"><%= pesan %></p>
    <% } %>

    <h3>Tambah / Edit Karyawan</h3>
    <form action="KaryawanController?action=simpan" method="post">
        KTP: <input type="text" name="ktp"/><br/><br/>
        Nama: <input type="text" name="nama"/><br/><br/>
        Ruang: <input type="text" name="ruang"/><br/><br/>
        Password: <input type="password" name="password"/><br/><br/>
        <input type="submit" value="Simpan"/>
    </form>

    <h3>Daftar Karyawan</h3>
    <table>
        <tr>
            <th>KTP</th>
            <th>Nama</th>
            <th>Ruang</th>
            <th>Aksi</th>
        </tr>
        <%
            Object[][] list = (Object[][]) request.getAttribute("listKaryawan");
            if(list != null) {
                for(Object[] row : list) {
        %>
        <tr>
            <td><%= row[0] %></td>
            <td><%= row[1] %></td>
            <td><%= row[2] %></td>
            <td>
                <a href="KaryawanController?action=hapus&ktp=<%= row[0] %>"
                   onclick="return confirm('Hapus data ini?')">Hapus</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
</body>
</html>