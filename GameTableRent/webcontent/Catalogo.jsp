<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, model.ProdottoDTO" %>
    <%@ include file="Header.jsp" %>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogo</title>
    <link rel="stylesheet" href="CSS/Catalogo.css">
        <script src="script/AggiuntaAlCarrello.js"></script>
    
</head>
<body>
<% ArrayList<ProdottoDTO> catalogo = (ArrayList<ProdottoDTO>) request.getAttribute("ListaProdotti"); 
	UtenteDTO user=(UtenteDTO)request.getSession().getAttribute("user");
%>

<h1>Catalogo:</h1>


<div class="catalogo">
    <% if (catalogo != null && !catalogo.isEmpty()) { 
        for (ProdottoDTO x : catalogo) { %>
            <div class="prodotto">
                <div class="prodotto-info">
                    <h3>Nome: <%= x.getNome() %></h3>
                    <h5>Descrizione: <%= x.getDescrizione() %></h5>
                    <h5>Prezzo: <%= x.getPrezzo() %></h5>
                    <h5>Prezzo al Giorno: <%= x.getPrezzoXDay() %></h5>
                    <h5>Quantità nel magazzino: <%=x.getQuantity() %></h5>
                </div>
                <div class="prodotto-img">
                    <% if (x.getImmagine() != null) { %>
                        <img class="immagineProd" alt="Immagine" src="<%= request.getContextPath() %>/ProductControl?action=image&id=<%=x.getID_Prod()%>">
">
                    <% } else { %>
                        <img class="immagineProd" alt="Immagine" src="Pictures/defaultImage.png">
                    <% } %>
                </div>
                
                <div class="buttonContainer">
                    <div class="aggiungi">
					<button onclick="mostraFormAggiungi('<%= x.getID_Prod() %>', <%= x.getQuantity() %>)">Aggiungi</button>
                    </div>
                    <div class="mostra">
                        <button onclick="location.href='ProductControl?action=dettaglio&codice=<%=x.getID_Prod()%>'">Dettaglio</button>
                    </div>
                    <%if(user!=null){ %>
                    
                  <%if(user.getIsAdmin()>0){ %>
                    <button onclick="location.href='admin/AddProductForm.jsp'">Aggiungi Prodotto</button> 
                     <button onclick="location.href='ProductControl?action=elimina&id=<%=x.getID_Prod()%>'"> Elimina Prodotto </button>   
                                  
                                  
                                  <%} }%>
                  
                </div>
                
            </div>
        <% }
    } else { %>
        <p>Nessun prodotto disponibile.</p>
    <% } %>
</div>

<!-- Form di aggiunta al carrello -->
<div id="formAggiunta" class="form" style="display: none;">
    <div class="form_content">
        <span class="close" onclick="chiudiFormAggiunta()">&times;</span>
        <h2>Aggiungi al Carrello</h2>
        <input type="hidden" id="productId">
        
        <!-- Menu a tendina per selezionare Compra o Affitta -->
        <label for="sceltaTipo">Scegli l'opzione:</label>
        <select id="sceltaTipo" onchange="toggleInput()">
            <option value="compra">Compra</option>
            <option value="affitta">Affitta</option>
        </select>
        
        <!-- Input per la quantità (mostrato quando Compra è selezionato) -->
        <div id="compraInput" style="display: block;">
            Quantità: <input type="number" id="quantita" min="1" required>
        </div>
        
        <!-- Input per i giorni (mostrato quando Affitta è selezionato) -->
        <div id="affittaInput" style="display: none;">
            Giorni: <input type="number" id="giorni" min="0" max="60" step="5" required>
        </div>
        
        <button onclick="aggiungiAlCarrello()">Aggiungi</button>
    </div>
</div>



</body>
</html>
