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
<%@ include file="Header.jsp" %>
<body>
    <% if (user != null) { %>
        <div class="title"><span>Ciao <%=user.getNome()%></span></div>
    <% } %>
    <% if (cart.getCart().isEmpty()) { %>
        <h2 class="title" style="text-align: center"><span>Il tuo carrello è vuoto</span></h2> 
    <% } else { %>
        <h2 class="title" style="text-align: center"><span>PRODOTTI IN CARRELLO</span></h2> 
    <% } %>
    <div class="container">
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
                    
                    <%if(valido){ %>
                        <button onclick="updateQuantityCart(<%=x.getId_prodotto()%>,'-', <%=maxQuantity%>)"> - </button>
                        <%} %>
                        
                        <span id="quantity_of_<%=x.getId_prodotto()%>"><%=x.getQuantita()%></span>
                        
                        <% if(valido){%>
                        <button onclick="updateQuantityCart(<%=x.getId_prodotto()%>,'+', <%=maxQuantity%>)"> + </button>
                        <%} %>
                        
                    </h5>
                    <% if (tipo.equals("Nolleggio")) { %>
                        <h5>Giorni: 
                        <%if (valido){ %>
                            <button onclick="UpdateDaysCart(<%=x.getId_prodotto()%>,'-')"> - </button>
                            <%} %>
                            <span id="daysOf<%=x.getId_prodotto()%>"> <%=x.getGiorni()%> </span>
                            <%if(valido){ %>
                            <button onclick="UpdateDaysCart(<%=x.getId_prodotto()%>,'+')"> + </button>
                            <%} %>
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
        <% if (valido) { %>
            <button id="TotalPrice" onclick="checkOut(<%=valido%>, <%=cart.getCart().size()%>)">Effettua Ordine, Tot: <%=cart.getTotalPrice()%>$</button>
        <% } else { %>
            <p>Per procedere con l'acquisto, effettua il <a href="Login.jsp">login</a> o <a href="Registrazione.jsp">registrati</a>.</p>
        <% } %>
    </div>
</body>
</html>
