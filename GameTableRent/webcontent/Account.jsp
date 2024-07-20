<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
<%@ include file="Header.jsp" %>
<%
    UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("user");
if(user==null)
    response.sendError(HttpServletResponse.SC_FORBIDDEN);


%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Profilo Utente</title>
    <link rel="stylesheet" href="CSS/RegLog.css">
    <script src="script/ValidatingForms.js"></script>
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

        function confirmDeletion() {
            var result = confirm("Sei sicuro di voler eliminare l'account? Questa azione non può essere annullata.");
            return result;
        }
    </script>
</head>
<body>
    <div class="main-content">
        <div class="container">
            <h2>INFORMAZIONI UTENTE:</h2>
            <form id="updateForm" action="UserControl?action=Update" method="post" onsubmit="return validateFormUpdate(event)">
                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" value="<%= user.getNome() %>" readonly /><br/>
                <label id="nomeError" class="error-message"></label>

                <label for="cognome">Cognome:</label>
                <input type="text" id="cognome" name="cognome" value="<%= user.getCognome() %>" readonly /><br/>
                <label id="cognomeError" class="error-message"></label>

                <label for="indirizzo">Indirizzo:</label>
                <input type="text" id="indirizzo" name="indirizzo" value="<%= user.getIndirizzo() %>" readonly /><br/>
                <label id="indirizzoError" class="error-message"></label>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<%= user.getEmail() %>" readonly /><br/>
                <label id="emailError" class="error-message"></label>

                <div class="buttons">
                    <button type="button" id="editButton" onclick="enableEditing()">Edit</button>
                    <button type="submit" id="submitButton" style="display:none;">Submit</button>
                    <% if (user.getIsAdmin() == 0) { %>
                        <a href="UserControl?action=Ordini"><button type="button">Orders</button></a>
                    <% } %>
                    <a href="UserControl?action=Logout"><button type="button">Logout</button></a>
                    <a href="UserControl?action=Delete" onclick="return confirmDeletion();">
                        <button type="button">Elimina</button>
                    </a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
