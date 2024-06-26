<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettagli Prodotto</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0; /* Reset margin for full screen centering */
          display:flex;
          justify-content: center;
          
        }
        .product-details {
        	margin-top:10%;
        	margin-bottom:10%;
            border: 1px solid #ccc;
            padding: 20px;
            max-width: 600px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Optional: Add a box-shadow for better visibility */
        }
        .product-details img {
            max-width: 100%;
            height: auto;
        }
  
    </style>
</head>
<body>
    <div class="product-details">
        <h2>Dettagli Prodotto</h2>
        <% model.ProdottoDTO prodotto = (model.ProdottoDTO) request.getAttribute("prodotto"); %>
        <% if (prodotto != null) { %>
            <h3><%= prodotto.getNome() %></h3>
            <p><strong>Descrizione:</strong> <%= prodotto.getDescrizione() %></p>
            <p><strong>Prezzo:</strong> <%= prodotto.getPrezzo() %> Euro</p>
            <p><strong>Prezzo per giorni:</strong> <%= prodotto.getPrezzoXDay() %> Euro</p>
            <p><strong>Quantità disponibile:</strong> <%= prodotto.getQuantity() %></p>
            <% if (prodotto.getImmagine() != null) { %>
                <img class="immagineProd" alt="Immagine" src="<%= prodotto.getImmagine() %>">
            <% } else { %>
                <img class="immagineProd" alt="Immagine" src="Pictures/defaultImage.png">
            <% } %>
        <% } else { %>
            <p>Prodotto non trovato.</p>
        <% } %>
        <p><a href="<%= request.getContextPath() %>/ProductControl?action=mostraProdotti">Torna al catalogo</a></p>
        
    <div class="form_content">
        <h2>Aggiungi al Carrello</h2>
        <input type="hidden" id="productId" value=<%=prodotto.getID_Prod() %>>
        
        <!-- Menu a tendina per selezionare Compra o Affitta -->
        <label for="sceltaTipo">Scegli l'opzione:</label>
        <select id="sceltaTipo" onchange="toggleInput()">
            <option value="compra">Compra</option>
            <option value="affitta">Affitta</option>
        </select>
        
        <!-- Input per la quantità (mostrato quando Compra è selezionato) -->
        <div id="compraInput" style="display: block;">
            Quantità: <input type="number" id="quantita" min="1" max=<%=prodotto.getQuantity() %> required>
        </div>
        
        <!-- Input per i giorni (mostrato quando Affitta è selezionato) -->
        <div id="affittaInput" style="display: none;">
            Giorni: <input type="number" id="giorni" min="0" max="60" step="5" required>
        </div>
        
        <button onclick="aggiungiAlCarrello()">Aggiungi</button>
    </div>
        </div>


<script>
   
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
                alert('Prodotto aggiunto al carrello con successo!');
                chiudiFormAggiunta(); 
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
