<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html>
<head>
    <meta charset="UTF-8">
    <title>Pagina non trovata - Errore 404</title>
   <link rel="stylesheet" href="CSS/Error.css">
</head>
<body>
    <div class="container">
        <h1>Errore 403</h1>
        <p>Accesso negato. Non hai i permessi per accedere a questa pagina.</p>
        <p>
            <a href="<%= request.getContextPath() %>">Torna alla home page</a>
        </p>
    </div>
</body>
</html>
