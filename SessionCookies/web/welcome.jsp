<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Welcome</title></head>
<body>
<%
    String username = request.getParameter("username");
    if(username != null && !username.equals("")) {
        session.setAttribute("userLogin", username);
        session.setMaxInactiveInterval(60*60);
    }
    String user = (String) session.getAttribute("userLogin");
    if(user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<h1>Welcome, <%= user %>!</h1>
<p>Session aktif selama 1 jam.</p>
<a href="logout.jsp">Logout</a>
</body>
</html>