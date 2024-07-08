<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
<% 
    UtenteDTO userCheck = (UtenteDTO) request.getAttribute("utenteDettagli");
    CarrelloDAO dao = new CarrelloDAO();
    OrdineDAO ordineDAO = new OrdineDAO();
    ArrayList<OrdineDTO> ordini = ordineDAO.doRetrieveAllbyId(userCheck.getID());
    request.setAttribute("listaOrdini", ordini);
    double tot=ordini.stream().mapToDouble(a->a.getTotalPrice()).sum();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dettagli Utente</title>
<link rel="stylesheet" href="CSS/RegLog.css">
<link rel="stylesheet" href="CSS/Orders.css">

<style>
    span {
        font-family: monospace;
        font-style: oblique;
        font-weight: bold;
        background-color: #fff7d7;
        color: olive;
        padding: 5px 10px;
        border-radius: 10px;
    }
   
</style>
</head>
<%@include file="../Header.jsp" %>
<body>
<div class="main-content">
    <div class="container">
        <h2>INFORMAZIONI UTENTE</h2>
        <br>
        <form>
            <label>Nome: <span><%= userCheck.getNome() %></span></label>
            <label>Cognome: <span><%= userCheck.getCognome() %></span></label>
            <label>Indirizzo: <span><%= userCheck.getIndirizzo() %></span></label>
            <label>Email: <span><%= userCheck.getEmail() %></span></label>
            <label>Elementi nel carrello: <span><%= dao.doRetrieveById(userCheck.getID()).getCart().size() %></span></label>
            <label>Spesa Totale: <span><%=tot %></span></label>
        </form>
    </div>
</div>


<!-- RIESCO AD INCLUDERE DIRETTAMENTE LA PAGINA DEGLI ORDINI POICHE' INIZIALMENTE HO SETTATO L'ATTRIBUTO "listaOrdini" USATO IN ORDERS.JSP PER LEGGERE GLI ORDINI CON GLI ORDINI DELL'UTENTE DA CHECKARE -->
<jsp:include page="../Orders.jsp" />
</body>
</html>
