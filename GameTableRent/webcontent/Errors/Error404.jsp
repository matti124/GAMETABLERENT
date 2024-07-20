<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pagina non trovata - Errore 404</title>
   <link rel="stylesheet" href="CSS/Error.css">
</head>
<body>
    <div class="container">
        <h1>Errore 404</h1>
        <p>Cosa stai cercando...? <br>Questa pagina non esiste.</p>
        <p>
            <a href="<%= request.getContextPath() %>">Torna alla home page</a>
        </p>
    </div>
</body>
</html>
