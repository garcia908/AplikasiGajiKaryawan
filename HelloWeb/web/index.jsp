<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hello World JSP</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
            String nama = "Jeon M.G";
            out.println("<p>Halo " + nama + "! Selamat datang di JSP!</p>");
        %>
    </body>
</html>