<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
<%@ include file="../Header.jsp" %>
<%
ArrayList<UtenteDTO> utenti = (ArrayList<UtenteDTO>) request.getAttribute("utenti");
UtenteDTO me = (UtenteDTO) request.getSession().getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="CSS/RegLog.css">
    <link rel="stylesheet" href="CSS/Orders.css">
    <title>Utenti</title>
    <script>
        function onLoad() {
            let emailMe = '<%=me.getEmail()%>';
            let table = document.getElementById('tabella');
            let rows = table.getElementsByTagName('tr');
            //Vado ad iterare su tutte le righe della tabella 
            for (let i = 1; i < rows.length; i++) {
            	//per ognuna prelevo le varie colonne
                let colonna = rows[i].getElementsByTagName('td');
                if (colonna[3].innerText === emailMe) {
                    rows[i].style.backgroundColor = 'green'; // Evidenzia la riga
                }
            }
        }
        
        window.addEventListener('load', onLoad);
    </script>
</head>
<body>
    <div class="main-content">
        <div class="container">
            <h2>Utenti Registrati:</h2>
            <% if (utenti != null && !utenti.isEmpty()) { %>
                <table id="tabella">
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Cognome</th>
                            <th>Indirizzo</th>
                            <th>e-mail</th>
                            <th>Dettaglio</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (UtenteDTO x : utenti) { %>
                            <tr>
                                <td><%=x.getNome()%></td>
                                <td><%=x.getCognome()%></td>
                                <td><%=x.getIndirizzo()%></td>
                                <td><%=x.getEmail()%></td>
                                <td>
                                    <form action="AdminControl" method="get">
                                    	<input type="hidden" name="action" value="AccountDetails">
                                        <input type="hidden" name="id_utente" value="<%=x.getID() %>">
                                        <button type="submit">Vedi User</button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <h2 style="display:block; margin-top:20px;">Nessun utente registrato</h2>
            <% } %>
        </div>
    </div>
</body>
</html>
