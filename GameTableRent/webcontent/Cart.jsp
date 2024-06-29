<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*, java.net.URLEncoder"%>
<%
    UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("user");
    boolean valido = (user != null);
    CarrelloDTO cart = (CarrelloDTO) request.getSession().getAttribute("cart");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carrello</title>
    <link rel="stylesheet" type="text/css" href="CSS/Carrello.css">
<script src="script/Cart.js"></script>
</head>
<body>
    <% if (user != null) { %>
        <div class="title"><span>Ciao <%=user.getNome()%></span></div>
    <% } %>
    <% if (cart.getCart().isEmpty()) { %>
        <h2 style="text-align: center">Il tuo carrello è vuoto</h2> 
    <% } else { %>
        <h2 style="text-align: center">ECCO IL TUO CARRELLO:</h2>
    <% } %>
    <div class="carrello-container">
        <% for (ProdottoCarrelloDTO x : cart.getCart()) {
            ProdottoDAO prodDAO = new ProdottoDAO();
            ProdottoDTO prod = prodDAO.doRetrieveByKey(x.getId_prodotto());
            int maxQuantity = prod.getQuantity();
        %>
            <div class="prodotto">
                <% String tipo = (x.getGiorni() == 0) ? "Acquisto" : "Nolleggio"; %>
                <div class="prodotto-info">
                    <h3>Nome: <%=x.getName()%></h3>
                    <h5>Tipo: <%=tipo%></h5>
                    <h5>Prezzo: <%=x.getPrezzo()%></h5>
                    <h5>Prezzo al Giorno: <%=x.getPrezzoXdays()%></h5>
                    <h5>Quantità: 
                        <button onclick="updateQuantityCart(<%=x.getId_prodotto()%>,'-', <%=maxQuantity%>)"> - </button>
                        <span id="quantity_of_<%=x.getId_prodotto()%>"><%=x.getQuantita()%></span>
                        <button onclick="updateQuantityCart(<%=x.getId_prodotto()%>,'+', <%=maxQuantity%>)"> + </button>
                    </h5>
                    <% if (tipo.equals("Nolleggio")) { %>
                        <h5>Giorni: 
                            <button onclick="UpdateDaysCart(<%=x.getId_prodotto()%>,'-')"> - </button>
                           <span id="daysOf<%=x.getId_prodotto()%>"> <%=x.getGiorni()%> </span>
                            <button onclick="UpdateDaysCart(<%=x.getId_prodotto()%>,'+')"> + </button>
                        </h5>
                    <% } %>
                </div>
                <div class="prodotto-img">
                    <% if (x.getImage() != null) { %>
                        <img class="immagineProd" alt="Immagine" src="<%=x.getImage()%>">
                    <% } else { %>
                        <img class="immagineProd" alt="Immagine" src="Pictures/defaultImage.png">
                    <% } %>
                    <a href="ProductControl?action=dettaglio&codice=<%=x.getId_prodotto()%>">
                        <button>Dettagli Prodotto</button>
                    </a>
                </div>
            </div>
        <% } %>
    </div>
    <div class="checkout-button">
        <button onclick="checkOut(<%=valido%>, <%=cart.getCart().size()%>)">Effettua Ordine, Tot: <%=cart.getTotalPrice()%>$</button>
    </div>
</body>
</html>
