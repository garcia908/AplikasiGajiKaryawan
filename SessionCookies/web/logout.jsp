<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Logout</title></head>
<body>
<%
    session.invalidate();
%>
<h1>Anda telah logout!</h1>
<a href="login.jsp">Login lagi</a>
</body>
</html>