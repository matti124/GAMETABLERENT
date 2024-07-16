<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Registrazione</title>
    <link rel="stylesheet" href="CSS/RegLog.css">
    
    <script src="script/ValidatingForms.js"></script>
</head>
<body>
<div class="main-content">
    <div class="container">
        <h2>Registrazione</h2>
        <form id="registrationForm" action="Registrazione" method="post" onsubmit="return validateFormReg(event)">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" placeholder="Nome" required><br>
            <label id="nomeError" class="error-message"></label>
            <label for="cognome">Cognome:</label>
            <input type="text" id="cognome" name="cognome" placeholder="Cognome" required><br>
            <label id="cognomeError" class="error-message"></label>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" placeholder="Email@Email.com" required><br>
            <label id="emailError" class="error-message"></label>
            <label for="password">Password:</label>
            <input type="password" id="password" name="psw" placeholder="Password(8 caratteri)" required><br>
            <label id="passwordError" class="error-message"></label>
            <label for="indirizzo">Indirizzo:</label>
            <input type="text" id="indirizzo" name="indirizzo" placeholder="Via Indirizzo N." required><br>
            <label id="indirizzoError" class="error-message"></label>
            <button type="submit">Registrati</button>
        </form>
    </div>
</div>
</body>
</html>
