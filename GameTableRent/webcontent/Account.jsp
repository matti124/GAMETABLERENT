<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
<%@ include file="Header.jsp" %>
<%
    UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("user");

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="CSS/RegLog.css">
    <title>User Profile</title>
    <script>
        function enableEditing() {
            document.getElementById("editButton").style.display = "none";
            document.getElementById("submitButton").style.display = "inline";
            var inputs = document.getElementsByTagName("input");
            for (var i = 0; i < inputs.length; i++) {
                inputs[i].readOnly = false;
                inputs[i].classList.add('editable');
            }
        }
    </script>
</head>
<body>
    <div class="main-content">
        <div class="container">
            <h2>INFORMAZIONI UTENTE:</h2>
            <form action="UserControl?action=Update" method="post">
                <label for="nome">Nome:</label>
                <input type="text" name="nome" value="<%= user.getNome() %>" readonly /><br/>

                <label for="cognome">Cognome:</label>
                <input type="text" name="cognome" value="<%= user.getCognome() %>" readonly /><br/>

                <label for="indirizzo">Indirizzo:</label>
                <input type="text" name="indirizzo" value="<%= user.getIndirizzo() %>" readonly /><br/>

                <label for="email">Email:</label>
                <input type="email" name="email" value="<%= user.getEmail() %>" readonly /><br/>

                <div class="buttons">
                    <button type="button" id="editButton" onclick="enableEditing()">Edit</button>
                    <button type="submit" id="submitButton" style="display:none;">Submit</button>
                    <a href="UserControl?action=Ordini"><button type="button">Orders</button></a>
                    <a href="UserControl?action=Logout"><button type="button">Logout</button></a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
