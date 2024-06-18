<!DOCTYPE html>
<html>
<head>
    <title>Registrazione</title>
</head>
<body>
    <h2>Registrazione</h2>
    <form action="Registrazione" method="post">
    	<label for="nome">Nome:</label>
    	        <input type="text" id="nome" name="nome" required><br>
    	
    	<label for="cognome">Cognome: </label>
    	        <input type="text" id="cognome" name="cognome" required><br>
    	
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" required><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="psw" required><br>
        <label for="indirizzo">indirizzo:</label>
        <input type="text" id="indirizzo" name="indirizzo" required><br>
        <button type="submit">Registrati</button>
    </form>

    <%
        String errorMessage = (String) request.getAttribute("JustRegistered");
        if (errorMessage != null) {
    %>
        <p style="color:red;"><%= errorMessage %></p>
    <%
        }
    %>
</body>
</html>
