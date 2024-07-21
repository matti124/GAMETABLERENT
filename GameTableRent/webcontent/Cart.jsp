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
                    <h3><span style="font-weight:normal">Nome:</span> <%=x.getName()%></h3><br>
                    <h5><span style="font-weight:normal">Tipo:</span> <%=tipo%></h5><br>
                    <h5><span style="font-weight:normal">Prezzo:</span> <%=x.getPrezzo()%></h5><br>
                    <h5><span style="font-weight:normal">Prezzo al Giorno:</span> <%=x.getPrezzoXdays()%></h5><br>
                    <h5><span style="font-weight:normal">Quantità: </span>
                    
                    <%if(valido){ %>
                        <button onclick="updateQuantityCart(<%=x.getId_prodotto()%>,'-', <%=maxQuantity%>, <%=x.getGiorni()%>)"> - </button>
                  
                        <%} %>
                        <span id="quantity_of_<%=x.getId_prodotto()%>_<%=x.getGiorni()%>"><%=x.getQuantita()%></span>
                        
                        <% if(valido){%>
                        <button onclick="updateQuantityCart(<%=x.getId_prodotto()%>,'+', <%=maxQuantity%>,<%=x.getGiorni()%> )"> + </button>
                        <%} %>
                                    <button onclick="rimuoviDalCarrello(<%=x.getId_prodotto()%>, <%=x.getGiorni()%>)" style="font-weight:bold; color:red ">x</button>
                        
                        
                    </h5><br>
                    <% if (tipo.equals("Nolleggio")) { %>
                        <h5><span style="font-weight:normal">Giorni: </span>
                        <%if (valido){ %>
                            <button onclick="UpdateDaysCart(<%=x.getId_prodotto()%>,'-')"> - </button>
                            <%} %>
                            <span id="daysOf<%=x.getId_prodotto()%>"> <%=x.getGiorni()%> </span>
                            <%if(valido){ %>
                            <button onclick="UpdateDaysCart(<%=x.getId_prodotto()%>,'+')"> + </button>
                            <%} %>
                        </h5><br>
                    <% } %>
                </div>
                <div class="prodotto-img">
                    <% if (x.getImage() != null) { %>
                        <img class="immagineProd" alt="Immagine" src="<%= request.getContextPath() %>/ProductControl?action=image&id=<%= prod.getID_Prod() %>">

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
            <button class="TotalPrice1" id="TotalPrice" onclick="checkOut(<%=valido%>, <%=cart.getCart().size()%>)">Effettua Ordine, Tot: <%=cart.GetTotalPrice()%>$</button>
        <% } else { %>
            <p class="TotalPrice1">Prezzo Totale -> <%=cart.GetTotalPrice() %><br>Per procedere con l'acquisto, effettua il <a href="Login.jsp">login</a> o <a href="Registrazione.jsp">registrati</a>.</p>
        <% } %>
    </div>
</body>
</html>
