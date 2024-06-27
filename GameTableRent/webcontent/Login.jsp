<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
  
        .login-container {
            position: relative;
            border: 1px dotted green;
          	top:190px;
            max-width: 300px;
        }
        .login-container h1 {
            text-align: center;
        }
        .login-container form {
            display: flex;
            flex-direction: column;
        }
        .login-container input {
            margin-bottom: 10px;
            padding: 8px;
        }
        .login-container button {
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        .login-container button:hover {
            background-color: #45a049;
        }
        .error-message {
            color: red;
            text-align: center;
            position: relative;
            top: 0px;
        }
    </style>
</head>
<body>

    <div class="login-container">
        <h1>Login</h1>
        <form action="LoginServlet" method="post">
            <input type="text" name="email" placeholder="Email" required>
            <input type="password" name="psw" placeholder="Password" required>
            <button type="submit">Login</button>
        </form>
        <% if (request.getAttribute("ValueLogin") != null) { %>
    <div class="error-message">Login fallito. Per favore riprova.</div>
<% } else if (request.getAttribute("ValueReg") != null) { %>
    <div class="error-message">Email già registrata.</div>
<% } %> 
    </div>
</body>
</html>
