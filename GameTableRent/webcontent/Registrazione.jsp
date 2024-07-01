<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Registrazione</title>
    <link rel="stylesheet" href="CSS/RegLog.css">
</head>
<body>
<div class="main-content">
    <div class="container">
        <h2>Registrazione</h2>
        <form action="Registrazione" method="post">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" placeholder="Nome" required><br>
            <label for="cognome">Cognome:</label>
            <input type="text" id="cognome" name="cognome" placeholder="Cognome" required><br>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" placeholder="Email" required><br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="psw" placeholder="Password" required><br>
            <label for="indirizzo">Indirizzo:</label>
            <input type="text" id="indirizzo" name="indirizzo" placeholder="Indirizzo" required><br>
            <button type="submit">Registrati</button>
        </form>
    </div>
    </div>
</body>
</html>
