<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*, java.net.URLEncoder"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carrello</title>
    <link rel="stylesheet" type="text/css" href="CSS/Carrello.css">
    <script type="text/javascript">
    function checkOut(userID, cartSize) {
        if (!userID) {
            alert("Utente non trovato. Effettua il login.");
            return;
        }

        if (cartSize === 0) {
            alert("Il carrello è vuoto. Aggiungi dei prodotti prima di procedere all'ordine.");
            return;
        }

        window.location.href = "OrdineControl?action=newOrdine";
    }

    function UpdateQuantityCart(productId, quantity, days, sign, maxQuantity) {
    	let richiesta = new XMLHttpRequest();
        richiesta.open("POST", "CartControl?action=UpdateCart", true);
        richiesta.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        richiesta.onreadystatechange = function() {
            if (richiesta.readyState === 4 && richiesta.status === 200) {
                console.log("Response from server: " + richiesta.responseText);
                location.reload();
            }
        };

        // Aggiorna la quantità basata sul segno
        if (sign === '-') {
            if (quantity > 0) {
                quantity=quantity-1;
            } else {
                alert("La quantità non può essere inferiore a 0.");
                return;
            }
        } else if (sign === '+') {
            if (quantity < maxQuantity) {
                quantity=quantity+1;
            } else {
                alert("La quantità non può essere maggiore di quella in magazzino.");
                return;
            }
        }

        // Invia la richiesta
        let params = "codice_prod=" + productId + "&quantity=" + quantity + "&days=" + days;
        console.log("Sending params: " + params);
        richiesta.send(params);
    }


    function UpdateDaysCart(productId, quantity, days, sign) {
        let richiesta = new XMLHttpRequest();
        richiesta.open("POST", "CartControl?action=UpdateCart", true);
        richiesta.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        richiesta.onreadystatechange = function() {
            if (richiesta.readyState === 4 && richiesta.status === 200) {
                location.reload();
            }
        };

        // Aggiorna i giorni basati sul segno
        if (sign === '-') {
        	if(days===0){
        		alert("il tempo minimo di nolleggio è 5 giorni!");
        		return;
        	}
        		
            days -= 5;
        } else if (sign === '+') {
        	if(days===60){
        		alert("Hai raggiunto il tempo massimo!");
        	return;}
            days += 5;
        }
	
        // Invia la richiesta
        let params = "codice_prod=" + productId + "&quantity=" + quantity + "&days=" + days;
        richiesta.send(params);
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
       ProdottoDAO prodDAO=new ProdottoDAO();
       ProdottoDTO prod=prodDAO.doRetrieveByKey(x.getId_prodotto());
       int maxQuantity=prod.getQuantity();
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
                <h5>Quantità: <button onclick="UpdateQuantityCart(<%=x.getId_prodotto()%>,<%=x.getQuantita()%>, 0, '-', <%=maxQuantity%>)"> - </button> <span id="quantity"> <%=x.getQuantita()%></span> <button onclick="UpdateQuantityCart(<%=x.getId_prodotto()%>,<%=x.getQuantita()%>, 0, '+', <%=maxQuantity%>)">  + </button></h5>
                <% if (tipo.equals("Nolleggio")) { %>
                <h5>Giorni: <button onclick="UpdateDaysCart(<%=x.getId_prodotto()%>,1, <%=x.getGiorni()%>, '-')"> - </button> <%=x.getGiorni()%>  <button onclick="UpdateDaysCart(<%=x.getId_prodotto()%>,1, <%=x.getGiorni()%>, '+')"> + </button></h5>
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
