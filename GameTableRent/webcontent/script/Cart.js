function updateQuantityCart(productId, sign, maxQuantity, giorni) {
	let daysElem = document.getElementById("daysOf" + productId);
	let days = parseInt(daysElem ? daysElem.textContent : '0');

	if (giorni === 0) days = 0; //modo utile per diversificare aggiunte per un prodotto che vogliamo acquistare o che vogliamo nolleggiare


//riusciamo a prelevare le quantità del giusto prodotto grazie al gioco tra parametri che ci vengono passati e nome dell'id dell'elemento
	let quantityElem = document.getElementById("quantity_of_" + productId + '_' + days);
	if (!quantityElem) {
		console.error("Elemento con ID 'quantity_of_" + productId + '_' + days + "' non trovato."); 
		return;
	}

	let quantity = parseInt(quantityElem.textContent);

	if (sign === '-') {
		if (quantity <= 0) {
			alert("La quantità non può essere inferiore a 0.");
			return;
		}
		quantity = quantity - 1;
	} else if (sign === '+') {
		if (quantity >= maxQuantity) {
			alert("La quantità non può essere maggiore di quella in magazzino.");
			return;
		}
		quantity = quantity + 1;
	}

	let richiesta = new XMLHttpRequest();
	richiesta.open("POST", "CartControl?action=UpdateCart", true);
	richiesta.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	richiesta.onreadystatechange = function() {
		if (richiesta.readyState === 4 && richiesta.status === 200) {
			let response = JSON.parse(richiesta.responseText);

			if (quantity === 0) { //quantità pari a zero leviamo l'elemento dalla pagina
				quantityElem.remove();
				window.location.reload();
			} else {
				quantityElem.textContent = quantity; //aggiorniamo la quantità
				document.getElementById("TotalPrice").textContent = "Effettua Ordine, Tot: " + response.totalPrice + "$"; //aggiorniamo il prezzo

			}
		}
	};

	let params = "codice_prod=" + productId + "&quantity=" + quantity + "&sign=" + sign + "&days=" + days;
	richiesta.send(params);
}

function UpdateDaysCart(productId, sign) {
	let daysElement = document.getElementById("daysOf" + productId);
	if (!daysElement) {
		alert("Elemento giorni non trovato.");
		return;
	}
	let days = parseInt(daysElement.textContent);
	let newDays;

	if (isNaN(days)) {
		alert("Il numero di giorni non è valido.");
		return;
	}

	if (sign === '-') {
		if (days - 5 < 5) {
			alert("Impossibile noleggiare il prodotto per meno di 5 giorni.");
			return;
		}
		newDays = days - 5;
	} else if (sign === '+') {
		if (days + 5 > 60) {
			alert("Impossibile noleggiare il prodotto per più di 60 giorni.");
			return;
		}
		newDays = days + 5;
	} else {
		alert("Segno non valido.");
		return;
	}

	let richiesta = new XMLHttpRequest();
	richiesta.open("POST", "CartControl?action=UpdateDaysCart", true);
	richiesta.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	richiesta.onreadystatechange = function() {
		 if (richiesta.readyState === 4 && richiesta.status === 200) {
            let response = JSON.parse(richiesta.responseText);
            daysElement.textContent = newDays;

            let oldQuantityElem = document.getElementById("quantity_of_" + productId + '_' + days);
            let newQuantityElem = document.getElementById("quantity_of_" + productId + '_' + newDays);

            if (oldQuantityElem && newQuantityElem) {
                newQuantityElem.textContent = oldQuantityElem.textContent;
                oldQuantityElem.remove();
            } else if (oldQuantityElem) {
                oldQuantityElem.id = "quantity_of_" + productId + '_' + newDays;
            }

            document.getElementById("TotalPrice").textContent = "Effettua Ordine, Tot: " + response.totalPrice + "$";
        }
	};

	let params = "codice_prod=" + productId + "&days=" + newDays + "&oldDays=" + days;
	richiesta.send(params);
}

function rimuoviDalCarrello(productId, giorni) {
	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'CartControl?action=removeFromCart', true);
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.onload = function() {
		if (xhr.status >= 200 && xhr.status < 400) {
			let response = JSON.parse(xhr.responseText);
			document.getElementsByClassName("TotalPrice1")[0].textContent = "Effettua Ordine, Tot: " + response.totalPrice + "$";

			alert('Prodotto rimosso dal carrello!');
			window.location.reload();
		} else {
			alert('Errore nella rimozione del prodotto dal carrello.');
		}
	};

	let params = "codice_prod=" + productId + "&giorni=" + giorni;
	xhr.send(params);
}
