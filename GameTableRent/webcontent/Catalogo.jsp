<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, model.ProdottoDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogo</title>
    <link rel="stylesheet" href="Catalogo.css">
</head>
<body>
<% ArrayList<ProdottoDTO> catalogo = (ArrayList<ProdottoDTO>) request.getAttribute("ListaProdotti"); %>

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
                </div>
                <div class="prodotto-img">
                    <% if (x.getImmagine() != null) { %>
                        <img class="immagineProd" alt="Immagine" src="<%= x.getImmagine() %>">
                    <% } else { %>
                        <img class="immagineProd" alt="Immagine" src="Pictures/defaultImage.png">
                    <% } %>
                </div>
                <div class="aggiungi">
                    <button onclick="mostraFormAggiungi('<%= x.getID_Prod() %>')">Aggiungi</button>
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
        Quantità: <input type="number" id="quantita" min="1" required>
        Giorni:  <input type="number" id="giorni" min="1" max="60" step="5" required>
        <button onclick="aggiungiAlCarrello()">Aggiungi</button>
    </div>
</div>

<script>
    // Mostra il form di aggiunta al carrello
    function mostraFormAggiungi(productId) {
        document.getElementById('productId').value = productId; // Imposta l'id del prodotto nel campo nascosto
        document.getElementById('formAggiunta').style.display = 'block';
    }

    // Funzione per chiudere il form di aggiunta al carrello (modal)
    function chiudiFormAggiunta() {
        document.getElementById('formAggiunta').style.display = 'none';
    }

    // Funzione per aggiungere il prodotto al carrello in modo asincrono
    function aggiungiAlCarrello(productId) {
        var id_prod = document.getElementById('productId').value;
        var quantity = document.getElementById('quantita').value;
        var giorni = document.getElementById('giorni').value;
        var params = 'action=addToCart&codice_prod=' + id_prod
                     + '&quantity=' + quantity
                     + '&days=' + giorni;
        console.log('Valori del form: \n' +
        		'Id: '+ id_prod+ '\n'+
                'Quantità: ' + quantity + '\n' +
                'Giorni: ' + giorni);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'CartControl', true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

        xhr.onload = function() {
            if (xhr.status === 200) {
                // Aggiunta al carrello completata con successo
                // Puoi gestire qui eventuali aggiornamenti UI o messaggi all'utente
                alert('Prodotto aggiunto al carrello con successo!');
                chiudiFormAggiunta(); // Chiudi il form dopo l'aggiunta
            } else {
                // Gestisci qui eventuali errori o messaggi di errore
                alert('Si è verificato un errore durante l\'aggiunta al carrello.');
            }
        };

        xhr.onerror = function() {
            // Gestisci qui errori di rete o altre problematiche
            alert('Si è verificato un errore di rete.');
        };

        xhr.send(params);
    }
</script>

</body>
</html>
