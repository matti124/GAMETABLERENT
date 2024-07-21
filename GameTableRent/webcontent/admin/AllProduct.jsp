<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
<%@ include file="../Header.jsp" %>
<%
ArrayList<ProdottoDTO> prodotti = (ArrayList<ProdottoDTO>) request.getAttribute("prodotti");
UtenteDAO dao = new UtenteDAO();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="CSS/RegLog.css">
    <link rel="stylesheet" href="CSS/Orders.css">
    <title>Prodotti</title>
    <style>
    label {
        display: block;
    }

    span {
        font-family: monospace;
        font-style: oblique;
        font-weight: bold;
        background-color: #fff7d7;
        color: olive;
        padding: 5px 10px;
        border-radius: 10px;
    }
    
    /* Style for the button */
    .add-product-button {
        margin-top: 20px;
        padding: 10px 20px;
        background-color: #4CAF50;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .add-product-button:hover {
        background-color: #45a049;
    }
    </style>
</head>
<body>
    <div class="main-content">
        <div class="container">
            <h2>Prodotti:</h2>
            <% if (prodotti != null && !prodotti.isEmpty()) { %>
                <table id="tabella">
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Prezzo</th>
                            <th>Prezzo Giornaliero</th>
                            <th>Quantità</th>
                            <th>Catalogo</th>
                            <th>Dettagli</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (ProdottoDTO x : prodotti) { %>
                            <tr>
                                <td><%= x.getNome() %></td>
                                <td><%= x.getPrezzo() %> $</td>
                                <td><%= x.getPrezzoXDay()%> $</td>
                                <td><%= x.getQuantity() %></td>
                                <td><%= x.getIN_CAT() %></td>
                                <td>
                                    <form action="ProductControl" method="get">
                                        <input type="hidden" name="action" value="dettaglio">
                                        <input type="hidden" name="codice" value="<%= x.getID_Prod() %>">
                                        <button type="submit">Vedi Prodotto</button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <h2 style="display:block; margin-top:20px;">Nessun prodotto registrato</h2>
            <% } %>
            
            <a href="admin/AddProductForm.jsp">
                <button class="add-product-button">Aggiungi Prodotto</button>
            </a>
        </div>
    </div>
</body>
</html>
