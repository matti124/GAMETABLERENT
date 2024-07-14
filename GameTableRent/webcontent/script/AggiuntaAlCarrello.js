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
    
    
    
    
    
  