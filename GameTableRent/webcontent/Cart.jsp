<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*, java.net.URLEncoder"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carrello</title>
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

            // Proceed to checkout (example URL, change it to your actual checkout URL)
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
    <h1>Ciao <%=user.getNome()%></h1>
    <%} else {%>
    <h1>Ciao Guest</h1>
    <% } %>

    <h2 style="text-align: center">ECCO IL TUO CARRELLO:</h2>
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

    <button onclick="checkOut(<%=valido%>, <%=cart.getCart().size()%>)">Effettua Ordine</button>

</body>
</html>
