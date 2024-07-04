<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
<%@ include file="../Header.jsp" %>
<%
ArrayList<OrdineDTO> ordini = (ArrayList<OrdineDTO>) request.getAttribute("ordini");
UtenteDAO dao = new UtenteDAO();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="CSS/RegLog.css">
    <link rel="stylesheet" href="CSS/Orders.css">
    <title>Utenti</title>
</head>
<body>
    <div class="main-content">
        <div class="container">
            <h2>Ordini Registrati:</h2>
            <% if (ordini != null && !ordini.isEmpty()) { %>
                <table id="tabella">
                    <thead>
                        <tr>
                            <th>Data</th>
                            <th>Prezzo</th>
                            <th>Numero Articoli</th>
                            <th>Acquirente</th>
                            <th>Dettagli</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (OrdineDTO x : ordini) { 
                            UtenteDTO UserAssociated = dao.doRetrieveByKey(x.getIdUtente());
                        %>
                            <tr>
                                <td><%= x.getData() %></td>
                                <td><%= x.getTotalPrice() %></td>
                                <td><%= x.getProdotti().size() %></td>
                                <td><%= UserAssociated.getEmail() %></td>
                                <td>
                                    <form action="AdminControl" method="get">
                                        <input type="hidden" name="action" value="AccountDetails">
                                        <input type="hidden" name="id_utente" value="<%= UserAssociated.getID() %>">
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
