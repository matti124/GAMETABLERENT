<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, model.ProdottoDTO" %>
    <%@ include file="Header.jsp" %>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogo</title>
    <link rel="stylesheet" href="CSS/Catalogo.css">
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

<script>
    // Mostra il form di aggiunta al carrello
    function mostraFormAggiungi(productId, quantity) {
        document.getElementById('productId').value = productId; // Imposta l'id del prodotto nel campo nascosto
        document.getElementById('formAggiunta').style.display = 'block';
        console.log("Quantità elemento selezionato:", Number(quantity))
        document.getElementById('quantita').setAttribute("max", Number(quantity));
        
        // Imposta di default Compra quando si apre il form
        document.getElementById('sceltaTipo').value = 'compra';
        toggleInput(); // Chiama la funzione per mostrare/nascondere gli input corretti in base alla scelta
    }

    // Funzione per chiudere il form di aggiunta al carrello (modal)
    function chiudiFormAggiunta() {
        document.getElementById('formAggiunta').style.display = 'none';
    }

    // Funzione per mostrare/nascondere gli input di quantità o giorni in base alla scelta
    function toggleInput() {
        var scelta = document.getElementById("sceltaTipo").value;
        if (scelta === "compra") {
            document.getElementById("compraInput").style.display = "block";
            document.getElementById("affittaInput").style.display = "none";
        } else if (scelta === "affitta") {
            document.getElementById("compraInput").style.display = "none";
            document.getElementById("affittaInput").style.display = "block";
        }
    }

    // Funzione per aggiungere il prodotto al carrello in modo asincrono
    function aggiungiAlCarrello() {
        var id_prod = document.getElementById('productId').value;
        var tipo = document.getElementById('sceltaTipo').value;
        var quantity = tipo === "compra" ? document.getElementById('quantita').value : 1;
        var giorni = tipo === "affitta" ? document.getElementById('giorni').value : 0;
       
        var params = 'action=addToCart&codice_prod=' + id_prod
                     + '&quantity=' + quantity
                     + '&days=' + giorni;
       
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'CartControl', true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

        xhr.onload = function() {
            if (xhr.status === 200) {
                chiudiFormAggiunta(); 

                alert('Prodotto aggiunto al carrello con successo!');
            } else {
                alert('Si è verificato un errore durante l\'aggiunta al carrello.');
            }
        };

        xhr.onerror = function() {
            alert('Si è verificato un errore di rete.');
        };

        xhr.send(params);
    }
</script>

</body>
</html>
