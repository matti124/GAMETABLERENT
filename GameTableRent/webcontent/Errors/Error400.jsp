<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Azione non valida - Errore 400</title>
   <link rel="stylesheet" href="CSS/Error.css">
</head>
<body>
    <div class="container">
        <h1>Errore 400</h1>
        <p>Azione non valida :(</p>
        <p>
            <a href="<%= request.getContextPath() %>">Torna alla home page</a>
        </p>
    </div>
</body>
</html>
