<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<div id="header"><h1>Aplikasi Gaji Karyawan</h1></div>
<div id="content">
    <h2>Login</h2>
    <% String pesan = (String) request.getAttribute("pesan");
       if(pesan != null) { %>
        <p style="color:red;"><%= pesan %></p>
    <% } %>
    <form action="LoginController" method="post">
        KTP: <input type="text" name="ktp"/><br/><br/>
        Password: <input type="password" name="password"/><br/><br/>
        <input type="submit" value="Login"/>
    </form>
</div>
</body>
</html>