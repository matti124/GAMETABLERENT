<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
	<link rel="stylesheet" href="CSS/RegLog.css">
</head>
<body>
<%@include file= "Header.jsp" %>
<div class="main-content">
    <div class="container" style="margin-top:10%">
        <h2>Login</h2>
        <form action="LoginServlet" method="post">
            <input type="email" name="email" placeholder="Email" required> <br>
            <input type="password" name="psw" placeholder="Password" required> <br>
            <button type="submit">Login</button>
        </form>
        <% if (request.getAttribute("ValueLogin") != null) { %>
            <div class="error-message">Login fallito. Per favore riprova.</div>
        <%} %>
        
        <%if (request.getAttribute("ValueReg")!=null){ %>
        <div class="error-message">Email gi� registrata nel sito</div>
        <%} %>
    </div>
    </div>

</body>
</html>
