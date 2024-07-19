<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
<%@ include file="../Header.jsp" %>
<%
ArrayList<OrdineDTO> ordini = (ArrayList<OrdineDTO>) request.getAttribute("ordini");
UtenteDAO dao = new UtenteDAO();
double[]totals= (double[])request.getAttribute("profitti");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="CSS/RegLog.css">
    <link rel="stylesheet" href="CSS/Orders.css">
    <title>Ordini</title>
    <style>
    label{
    display:block;}
    
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
    <script src="./script/ValidateDateForm.js"></script>
</head>
<body>
    <div class="main-content">
        <div class="container">
            <h2>Ordini Registrati:</h2>
            <% if (ordini != null && !ordini.isEmpty()) { %>
                <table id="tabella">
                    <thead>
                        <tr>
                            <th>Data</th>
                            <th>Prezzo</th>
                            <th>Numero Articoli</th>
                            <th>Acquirente</th>
                            <th>Dettagli</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (OrdineDTO x : ordini) { 
                            UtenteDTO UserAssociated = dao.doRetrieveByKey(x.getIdUtente());
                        %>
                            <tr>
                                <td><%= x.getData() %></td>
                                <td><%= x.getTotalPrice() %> $</td>
                                <td><%= x.getProdotti().size() %></td>
                                <td><%= UserAssociated.getEmail() %></td>
                                <td>
                                    <form action="AdminControl" method="get">
                                        <input type="hidden" name="action" value="AccountDetails">
                                        <input type="hidden" name="id_utente" value="<%= UserAssociated.getID() %>">
                                        <button type="submit">Vedi User</button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <h2 style="display:block; margin-top:20px;">Nessun ordine registrato</h2>
            <% } %>
        </div>
    </div>
    <div class="aggiunte">
    <div class="date-form" style="margin-bottom:auto">
                <h2> Ricerca Ordini per data:</h2>
                <form method="get" action="AdminControl" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="allOrdersByDate">
                <label> Data inizio: </label>
                <input type="date" name="Start">
                 <label> Data fine: </label>
                <input type="date" name="end">
                <button type="submit"> invia</button>
                </form>
                <a href="AdminControl?action=allOrders" style="text-decoration:none"><button>Mostra tutti</button></a>
              
                </div>
                
           <div class="riepilogo">
                   <h2 style="font-size:medium">Vendite negli ultimi 12 mesi:</h2>
       <label>Gennaio: <span><%= totals[1] %>$</span> <a href="AdminControl?action=allOrdersByDate&Start=2024-01-01&end=2024-01-31"><button type="button">Dettagli</button></a></label>
<label>Febbraio: <span><%= totals[2] %>$</span> <a href="AdminControl?action=allOrdersByDate&Start=2024-02-01&end=2024-02-28"><button type="button">Dettagli</button></a></label>
<label>Marzo: <span><%= totals[3] %>$</span> <a href="AdminControl?action=allOrdersByDate&Start=2024-03-01&end=2024-03-31"><button type="button">Dettagli</button></a></label>
<label>Aprile: <span><%= totals[4] %>$</span> <a href="AdminControl?action=allOrdersByDate&Start=2024-04-01&end=2024-04-30"><button type="button">Dettagli</button></a></label>
<label>Maggio: <span><%= totals[5] %>$</span> <a href="AdminControl?action=allOrdersByDate&Start=2024-05-01&end=2024-05-31"><button type="button">Dettagli</button></a></label>
<label>Giugno: <span><%= totals[6] %>$</span> <a href="AdminControl?action=allOrdersByDate&Start=2024-06-01&end=2024-06-30"><button type="button">Dettagli</button></a></label>
<label>Luglio: <span><%= totals[7] %>$</span> <a href="AdminControl?action=allOrdersByDate&Start=2024-07-01&end=2024-07-31"><button type="button">Dettagli</button></a></label>
<label>Agosto: <span><%= totals[8] %>$</span> <a href="AdminControl?action=allOrdersByDate&Start=2024-08-01&end=2024-08-31"><button type="button">Dettagli</button></a></label>
<label>Settembre: <span><%= totals[9] %>$</span> <a href="AdminControl?action=allOrdersByDate&Start=2024-09-01&end=2024-09-30"><button type="button">Dettagli</button></a></label>
<label>Ottobre: <span><%= totals[10] %>$</span> <a href="AdminControl?action=allOrdersByDate&Start=2024-10-01&end=2024-10-31"><button type="button">Dettagli</button></a></label>
<label>Novembre: <span><%= totals[11] %>$</span> <a href="AdminControl?action=allOrdersByDate&Start=2024-11-01&end=2024-11-30"><button type="button">Dettagli</button></a></label>
<label>Dicembre: <span><%= totals[12] %>$</span> <a href="AdminControl?action=allOrdersByDate&Start=2024-12-01&end=2024-12-31"><button type="button">Dettagli</button></a></label>

           
           </div>
    
    
    
    </div>
</body>
</html>
