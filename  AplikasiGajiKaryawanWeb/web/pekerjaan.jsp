<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Data Pekerjaan</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<div id="header"><h1>Aplikasi Gaji Karyawan</h1></div>
<div id="menu">
    <a href="index.jsp">Home</a>
    <a href="KaryawanController">Karyawan</a>
    <a href="PekerjaanController">Pekerjaan</a>
    <a href="LoginController?action=logout">Logout</a>
</div>
<div id="content">
    <h2>Data Pekerjaan</h2>
    <% String pesan = (String) request.getAttribute("pesan");
       if(pesan != null) { %>
        <p style="color:green;"><%= pesan %></p>
    <% } %>

    <h3>Tambah / Edit Pekerjaan</h3>
    <form action="PekerjaanController?action=simpan" method="post">
        Kode: <input type="text" name="kodepekerjaan"/><br/><br/>
        Nama: <input type="text" name="namapekerjaan"/><br/><br/>
        Jumlah Tugas: <input type="text" name="jumlahtugas"/><br/><br/>
        <input type="submit" value="Simpan"/>
    </form>

    <h3>Daftar Pekerjaan</h3>
    <table>
        <tr>
            <th>Kode</th>
            <th>Nama Pekerjaan</th>
            <th>Jumlah Tugas</th>
            <th>Aksi</th>
        </tr>
        <%
            Object[][] list = (Object[][]) request.getAttribute("listPekerjaan");
            if(list != null) {
                for(Object[] row : list) {
        %>
        <tr>
            <td><%= row[0] %></td>
            <td><%= row[1] %></td>
            <td><%= row[2] %></td>
            <td>
                <a href="PekerjaanController?action=hapus&kode=<%= row[0] %>"
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