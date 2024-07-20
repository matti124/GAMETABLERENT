<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
    <%OrdineDTO ordine=(OrdineDTO)request.getAttribute("ordine"); 
    UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("user");
    if(user==null)
        response.sendError(HttpServletResponse.SC_FORBIDDEN);

    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order Detail</title>
<link rel="stylesheet" href="CSS/Carrello.css">
</head>
<%@include file="Header.jsp" %>
<body>
<h2 class="title"><span>PRODOTTI DELL'ORDINE:</span></h2>
<div class="container">
<%for(ProdottoOrdineDTO x: ordine.getProdotti()) {%>
<div class="prodotto">
                <% String tipo = (x.getGiorni() == 0) ? "Acquisto" : "Nolleggio"; %>
                <div class="prodotto-info">
                    <h3>Nome: <%=x.getName()%></h3> <br>
                    <h5>Tipo: <%=tipo%></h5><br>
                    <h5>Prezzo: <%=x.getPrezzo()%></h5> <br>
                    <h5>Prezzo al Giorno: <%=x.getPrezzoXdays()%></h5> <br>
                    <h5>Quantità: <%=x.getQuantity() %></h5> <br>
                     <% if (tipo.equals("Nolleggio")) { %>
                        <h5>Giorni: <%=x.getGiorni() %></h5> <br> <%} %>
                                                </div>
                        
                        <div class="prodotto-img">
                    <% if (x.getImage() != null) { %>
                        <img class="immagineProd" alt="Immagine" src="<%= request.getContextPath() %>/ProductControl?action=image&id=<%= x.getId_prodotto() %>">
                    <% } else { %>
                        <img class="immagineProd" alt="Immagine" src="Pictures/defaultImage.png">
                    <% } %>
                    <a href="ProductControl?action=dettaglio&codice=<%=x.getId_prodotto()%>">
                        <button>Dettagli Prodotto</button> </a>
                        </div>

</div>

<%} %>


</div>

</body>
</html>