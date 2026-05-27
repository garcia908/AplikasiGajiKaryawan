<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Aplikasi Gaji Karyawan</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<div id="header">
    <h1>Aplikasi Gaji Karyawan</h1>
</div>
<div id="menu">
    <a href="index.jsp">Home</a>
    <a href="LoginController">Login</a>
    <a href="KaryawanController">Karyawan</a>
    <a href="PekerjaanController">Pekerjaan</a>
</div>
<div id="content">
    <%
        String user = (String) session.getAttribute("userLogin");
        if(user != null) {
    %>
        <h2>Selamat Datang, <%= user %>!</h2>
        <p>Silakan gunakan menu di atas untuk mengelola data.</p>
        <a href="LoginController?action=logout">Logout</a>
    <%
        } else {
    %>
        <h2>Selamat Datang!</h2>
        <p>Silakan <a href="LoginController">Login</a> untuk mengakses aplikasi.</p>
    <%
        }
    %>
</div>
</body>
</html>