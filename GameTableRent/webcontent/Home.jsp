<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.*,java.util.*"%>
    
    <%
	if (request.getSession().getAttribute("carrello") == null) {
		CarrelloDTO carrello = new CarrelloDTO();
		request.getSession().setAttribute("carrello", carrello);
	}
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
</head>
<body>

    <h1>Benvenuto alla Home Page</h1>
    <button onclick="location.href='Registrazione.jsp'">Vai a Pagina Registrazione</button>
    <button onclick="location.href='Login.jsp'">Vai a Pagina Login</button>
    <button onclick="location.href='ProductControl?action=mostraProdotti'">Vai a Catalogo</button>
</body>
</html>
