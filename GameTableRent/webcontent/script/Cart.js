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

     
        
      
       function updateQuantityCart(productId,  sign, maxQuantity) {
            // Recupera la quantità corrente dal DOM
            let quantityElem = document.getElementById("quantity_of_" + productId);
            
            let quantity = parseInt(quantityElem.textContent);

            // Aggiorna la quantità basata sul segno
            if (sign === '-') {
                if (quantity <= 0) {
                    alert("La quantità non può essere inferiore a 0.");
                    return;
                }
                console.log("Vecchia quantità: " + quantity);
                quantity = quantity - 1;
                console.log("Nuova quantità: " + quantity);
            } else if (sign === '+') {
                if (quantity > maxQuantity) {
                    alert("La quantità non può essere maggiore di quella in magazzino.");
                    return;
                }
                console.log("Vecchia quantità: " + quantity);
                quantity = quantity + 1;
                console.log("Nuova quantità: " + quantity);
            }

            // Aggiorna il DOM con il nuovo valore di quantità
            quantityElem.textContent = quantity;


            // Crea una richiesta asincrona al server
            let richiesta = new XMLHttpRequest();
            richiesta.open("POST", "CartControl?action=UpdateCart", true);
            richiesta.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

            richiesta.onreadystatechange = function() {
                if (richiesta.readyState === 4 && richiesta.status === 200) {
                    console.log("Risposta dal server: " + richiesta.responseText);
					if(quantity===0){
						document.getElementById("quantity_of_" + productId).remove;
						window.location.reload();
					}
						
                }
            };

            // Invia la richiesta
            let params = "codice_prod=" + productId + "&quantity=" + quantity
            console.log("Invio dei parametri: " + params);
            richiesta.send(params);
        }

       
       
       
       
     function UpdateDaysCart(productId, sign) {
    let daysElement = document.getElementById("daysOf" + productId);
    let days = parseInt(daysElement.textContent);

    if (isNaN(days)) {
        alert("Il numero di giorni non è valido.");
        return;
    }

    if (sign === '-') {
        if (days - 5 < 5) {
            alert("Impossibile noleggiare il prodotto per meno di 5 giorni");
            return;
        } else {
            days -= 5;
        }
    } else if (sign === '+') {
        if (days + 5 > 60) {
            alert("Impossibile noleggiare il prodotto per più di 60 giorni");
            return;
        } else {
            days += 5;
        }
    } else {
        alert("Segno non valido.");
        return;
    }

    console.log("Giorni risultanti: ", days);
    daysElement.textContent = days;

    let richiesta = new XMLHttpRequest();
    richiesta.open("POST", "CartControl?action=UpdateDaysCart", true);
    richiesta.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    richiesta.onreadystatechange = function() {
        if (richiesta.readyState === 4 && richiesta.status === 200) {
            console.log("Risposta dal server: " + richiesta.responseText);
        }
    };

    let params = "codice_prod=" + productId + "&days=" + days;
    console.log("Invio dei parametri: " + params);
    richiesta.send(params);
}

       
       
       /* le funzione di aggiornamento trattano principalmente l'aggiornamento in front end dei numeri, 
       difatti in backend viene chiamata la funzione di cartcontrol che andrà ad aggiornare la quantità e i giorni 
       del prodotto su cui stiamo cliccando*/
       
