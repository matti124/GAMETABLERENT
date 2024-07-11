<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dettagli Prodotto</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/ProductDetail.css">
</head>
<body>
    <div class="main-content">
        <div class="container">
                        <h2>Dettagli Prodotto</h2>
        
            <div class="product-details">
                <% model.ProdottoDTO prodotto = (model.ProdottoDTO) request.getAttribute("prodotto"); %>
                <% if (prodotto != null) { %>
                    <div class="details">
                        <h2><%= prodotto.getNome() %></h2>
                        <label><strong>Descrizione:</strong> <%= prodotto.getDescrizione() %></label>
                        <label><strong>Prezzo:</strong> <%= prodotto.getPrezzo() %> Euro</label>
                        <label><strong>Prezzo per giorni:</strong> <%= prodotto.getPrezzoXDay() %> Euro</label>
                        <label><strong>Quantità disponibile:</strong> <%= prodotto.getQuantity() %></label>
                        <% if (prodotto.getImmagine() != null) { %>
                            <img class="immagineProd" alt="Immagine" src="<%= request.getContextPath() %>/ProductControl?action=image&id=<%= prodotto.getID_Prod() %>">
                        <% } else { %>
                            <img class="immagineProd" alt="Immagine" src="Pictures/defaultImage.png">
                        <% } %>
                    </div>
                <% } else { %>
                    <label>Prodotto non trovato.</label>
                <% } %>
                <label><a href="<%= request.getContextPath() %>/ProductControl?action=mostraProdotti">Torna al catalogo</a></label>
            </div>

            <div class="form_content">
                <h2>Aggiungi al Carrello</h2>
                <input type="hidden" id="productId" value="<%= prodotto != null ? prodotto.getID_Prod() : "" %>">
                
                <label for="sceltaTipo">Scegli l'opzione:</label>
                <select id="sceltaTipo" onchange="toggleInput()">
                    <option value="compra">Compra</option>
                    <option value="affitta">Affitta</option>
                </select>
                
                <div id="compraInput" style="display: block;">
                    Quantità: <input type="number" id="quantita" min="1" max="<%= prodotto != null ? prodotto.getQuantity() : "" %>" required>
                </div>
                
                <div id="affittaInput" style="display: none;">
                    Giorni: <input type="number" id="giorni" min="0" max="60" step="5" required>
                </div>
                
                <button onclick="aggiungiAlCarrello()">Aggiungi</button>
            </div>
        </div>
    </div>

    <script>
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
