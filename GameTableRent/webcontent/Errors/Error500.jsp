<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Errore del Server, 500</title>
   <link rel="stylesheet" href="CSS/Error.css">
</head>
<body>
    <div class="container">
        <h1>Errore 500</h1>
        <p>Qualcosa è andato storto :(</p>
        <p>
            <a href="<%= request.getContextPath() %>">Torna alla home page</a>
        </p>
    </div>
</body>
</html>
