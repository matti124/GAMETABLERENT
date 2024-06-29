<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
<%
    UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("user");
%>
<html>
<head>
    <title>User Profile</title>
    <script>
        function enableEditing() {
            document.getElementById("editButton").style.display = "none";
            document.getElementById("submitButton").style.display = "inline";
            var inputs = document.getElementsByTagName("input");
            for (var i = 0; i < inputs.length; i++) {
                inputs[i].readOnly = false;
            }
        }
    </script>
</head>
<body>
    <form action="UserControl?action=Update" method="post">
        <label for="nome">Nome:</label>
        <input type="text" name="nome" value="<%= user.getNome() %>" readonly /><br/>

        <label for="cognome">Cognome:</label>
        <input type="text" name="cognome" value="<%= user.getCognome() %>" readonly /><br/>

        <label for="indirizzo">Indirizzo:</label>
        <input type="text" name="indirizzo" value="<%= user.getIndirizzo() %>" readonly /><br/>

        <label for="email">Email:</label>
        <input type="email" name="email" value="<%= user.getEmail() %>" readonly /><br/>

       

        <button type="button" id="editButton" onclick="enableEditing()">Edit</button>
        <button type="submit" id="submitButton" style="display:none;">Submit</button>
<a href="UserControl?action=Logout">Logout</a>
    </form>
</body>
</html>
