<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*, java.net.URLEncoder"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carrello</title>
    <link rel="stylesheet" type="text/css" href="CSS/Carrello.css">
    <script type="text/javascript">
        function checkOut(userID, cart) {
            if (!userID) {
                alert("Utente non trovato. Effettua il login.");
                return;
            }

            if (!cart || cart.length === 0) {
                alert("Il carrello è vuoto. Aggiungi dei prodotti prima di procedere all'ordine.");
                return;
            }

            window.location.href = "OrdineControl?action=newOrdine";
        }
    </script>
</head>
<body>
    <%
    UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("user");
    boolean valido = (user != null);
    CarrelloDTO cart = (CarrelloDTO) request.getSession().getAttribute("cart");
    %>

    <%
    if (user != null) {
    %>
    <div class="title"><span>Ciao <%=user.getNome()%></span>
    <%}%>
<%if (cart.getCart().isEmpty()){ %>
    <h2 style="text-align: center"> <span>Il tuo carrello è vuoto </span></h2> 
    <%} else{ %>

    <h2 style="text-align: center"> <span>ECCO IL TUO CARRELLO: </span></h2> <%} %></div> 
    <div class="carrello-container">
        <%
        for (ProdottoCarrelloDTO x : cart.getCart()) {
        %>
        <div class="prodotto">
            <%
            String tipo = (x.getGiorni() == 0) ? "Acquisto" : "Nolleggio";
            %>
            <div class="prodotto-info">
                <h3>Nome: <%=x.getName()%></h3>
                <h5>Tipo: <%=tipo%></h5>
                <h5>Prezzo: <%=x.getPrezzo()%></h5>
                <h5>Prezzo al Giorno: <%=x.getPrezzoXdays()%></h5>
                <h5>Quantità: <%=x.getQuantita()%></h5>
                <% if (tipo.equals("Nolleggio")) { %>
                <h5>Giorni: <%=x.getGiorni()%></h5>
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
        <button onclick="checkOut(<%=valido%>, <%=cart.getCart().size()%>)">Effettua Ordine, Tot: <%=cart.getTotalPrice() %>$</button>
    </div>
</body>
</html>
