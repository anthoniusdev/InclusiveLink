<%--
  Created by IntelliJ IDEA.
  User: antho
  Date: 10/01/2024
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.io.IOException" %>
<%
    HttpSession httpSession = request.getSession(false);
    if (httpSession == null || httpSession.getAttribute("authenticated") == null){
        response.sendRedirect("index.html");
        return;
    }
%>
<html lang="pt-BR">
<head>
    <title>Inclusive link</title>
</head>
<body>
    <h1>Página Inicial</h1>
</body>
</html>
