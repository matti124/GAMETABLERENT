<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
<% UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("user");
   ArrayList<OrdineDTO> ordini = (ArrayList<OrdineDTO>) request.getAttribute("listaOrdini");
   if(user==null){
       response.sendError(HttpServletResponse.SC_FORBIDDEN);
       return;}

%>
<% if (user.getIsAdmin() == 0) { %>
    <%@ include file="Header.jsp" %>
<% } %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="CSS/RegLog.css">
    <link rel="stylesheet" href="CSS/Orders.css">
<script src="script/ValidateDateForm.js"></script>
    
    <title>Orders</title>

</head>
<body>
    <div class="main-content">
        <div class="container">
            <% if (user.getIsAdmin() > 0) { %>
                <h2>Ordini</h2>
            <% } else { %>
                <h2>I tuoi ordini</h2>
            <% } %>
            
            <% if (ordini != null && !ordini.isEmpty()) { %>
                <table>
                    <thead>
                        <tr>
                            <th>Data</th>
                            <th>Prezzo</th>
                            <th>Numero Articoli</th>
                            <th>Azioni</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (OrdineDTO ordine : ordini) { %>
                            <tr>
                                <td><%= ordine.getData() %></td>
                                <td><%= ordine.getTotalPrice() %>$</td>
                                <td><%= ordine.getProdotti().size() %></td>
                                <td>
                                    <form action="OrdineControl" method="get">
                                    	<input type="hidden" name="action" value="OrderDetails">
                                        <input type="hidden" name="id_ord" value="<%= ordine.getId_Ordine() %>">
                                        <button type="submit">View Order</button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <h2 style="display:block; margin-top:20px;">Nessun ordine effettuato</h2>
            <% } %>
          
        
        <% if (user.getIsAdmin() == 0) { %>
            <div id="Userdate-form">
    <h2>Ricerca Ordini per data:</h2>
    <form method="get" action="OrdineControl" onsubmit="return validateForm()">
        <input type="hidden" name="action" value="AllOrdersByDate">
        <label>Data inizio:</label>
        <input type="date" name="Start">
        <label>Data fine:</label>
        <input type="date" name="end">
        <button type="submit">invia</button>
    </form>
    <a href="UserControl?action=Ordini" style="text-decoration:none"><button>Mostra tutti</button></a>
</div>

        <% } %>
                </div>
        
    </div>
</body>
</html>
