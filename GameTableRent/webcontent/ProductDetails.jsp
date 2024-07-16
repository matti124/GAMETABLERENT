
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
<%@ include file="Header.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dettagli Prodotto</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/ProductDetail.css">
        <script src="script/AggiuntaAlCarrello.js"></script>
    
</head>
<body>
<%UtenteDTO user=(UtenteDTO) request.getSession().getAttribute("user"); %>


    <div class="main-content">
        <div class="container">
                        <h2>Dettagli Prodotto</h2>
        
            <div class="product-details">
                <% model.ProdottoDTO prodotto = (model.ProdottoDTO) request.getAttribute("prodotto"); %>
                <% if (prodotto != null) { %>
                    <div class="details">
                        <h2><%= prodotto.getNome() %></h2>
                        <label><strong>Descrizione:</strong> <%= prodotto.getDescrizione() %></label>
                        <label><strong>Prezzo:</strong> <%= prodotto.getPrezzo() %> Euro</label>
                        <label><strong>Prezzo per giorni:</strong> <%= prodotto.getPrezzoXDay() %> Euro</label>
                        <label><strong>Quantità disponibile:</strong> <%= prodotto.getQuantity() %></label>
                        <% if (prodotto.getImmagine() != null) { %>
                            <img class="immagineProd" alt="Immagine" src="<%= request.getContextPath() %>/ProductControl?action=image&id=<%= prodotto.getID_Prod() %>">
                        <% } else { %>
                            <img class="immagineProd" alt="Immagine" src="Pictures/defaultImage.png">
                        <% } %>
                    </div>
                <% } else { %>
                    <label>Prodotto non trovato.</label>
                <% } %>
                <label><a href="<%= request.getContextPath() %>/ProductControl?action=mostraProdotti">Torna al catalogo</a></label>
            </div>


<%if(user.getIsAdmin()==0||user==null){ %>
            <div class="form_content">
                <h2>Aggiungi al Carrello</h2>
                <input type="hidden" id="productId" value="<%= prodotto != null ? prodotto.getID_Prod() : "" %>">
                
                <label for="sceltaTipo">Scegli l'opzione:</label>
                <select id="sceltaTipo" onchange="toggleInput()">
                    <option value="compra">Compra</option>
                    <option value="affitta">Affitta</option>
                </select>
                
                <div id="compraInput" style="display: block;">
                    Quantità: <input type="number" id="quantita" min="1" max="<%= prodotto != null ? prodotto.getQuantity() : "" %>" required>
                </div>
                
                <div id="affittaInput" style="display: none;">
                    Giorni: <input type="number" id="giorni" min="0" max="60" step="5" required>
                </div>
                
                <button onclick="aggiungiAlCarrello()">Aggiungi</button>
            </div>
            <%} else if(user.getIsAdmin()>0) {%>
            <div class="form-content">
			<a href="AdminControl?action=updateProduct&id=<%=prodotto.getID_Prod()%>"><button>Modifica</button></a>
			<a href="AdminControl?action=deleteProduct&id=<%=prodotto.getID_Prod()%>"><button onclick=alertSicurezza()>Elimina</button></a>
			
       
            
            </div>
            <%} %>
        </div>
    </div>

</body>
</html>
