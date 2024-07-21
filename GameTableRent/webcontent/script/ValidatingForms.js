// FUNZIONE REGISTRAZIONE UTENTE
function validateFormReg(event) {
    var isValid = true;

    var nome = document.getElementById('nome').value.trim();
    var cognome = document.getElementById('cognome').value.trim();
    var email = document.getElementById('email').value.trim();
    var password = document.getElementById('password').value.trim();
    var indirizzo = document.getElementById('indirizzo').value.trim();

    document.getElementById('nomeError').textContent = '';
    document.getElementById('cognomeError').textContent = '';
    document.getElementById('emailError').textContent = '';
    document.getElementById('passwordError').textContent = '';
    document.getElementById('indirizzoError').textContent = '';

    // Validazione Nome
    if (nome === '' || !/^[a-zA-Z]+$/.test(nome)) {
        document.getElementById('nome').style.border = "1px dotted red";
        document.getElementById('nome').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('nomeError').textContent = 'Il nome deve contenere solo lettere.';
        isValid = false;
    } else {
        document.getElementById('nome').style.border = "";
        document.getElementById('nome').style.backgroundColor = "";
    }

    // Validazione Cognome
    if (cognome === '' || !/^[a-zA-Z]+$/.test(cognome)) {
        document.getElementById('cognome').style.border = "1px dotted red";
        document.getElementById('cognome').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('cognomeError').textContent = 'Il cognome deve contenere solo lettere.';
        isValid = false;
    } else {
        document.getElementById('cognome').style.border = "";
        document.getElementById('cognome').style.backgroundColor = "";
    }

    // Validazione Email
    if (email === '' || !/^\S+@\S+\.\S+$/.test(email)) {
        document.getElementById('email').style.border = "1px dotted red";
        document.getElementById('email').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('emailError').textContent = 'L\'email deve essere in un formato valido.';
        isValid = false;
    } else {
        document.getElementById('email').style.border = "";
        document.getElementById('email').style.backgroundColor = "";
    }

    // Validazione Password
    if (password === '' || password.length < 8) {
        document.getElementById('password').style.border = "1px dotted red";
        document.getElementById('password').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('passwordError').textContent = 'La password deve avere almeno 8 caratteri.';
        isValid = false;
    } else {
        document.getElementById('password').style.border = "";
        document.getElementById('password').style.backgroundColor = "";
    }

    // Validazione Indirizzo
    if (indirizzo === '' || !/^Via [a-zA-Z]+(?: [a-zA-Z]+)* N\.\d+$/.test(indirizzo)) {
        document.getElementById('indirizzo').style.border = "1px dotted red";
        document.getElementById('indirizzo').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('indirizzoError').textContent = 'L\'indirizzo necessita del numero civico.';
        isValid = false;
    } else {
        document.getElementById('indirizzo').style.border = "";
        document.getElementById('indirizzo').style.backgroundColor = "";
    }

    if (!isValid) {
        event.preventDefault();
    }
    return isValid;
}

// FUNZIONE AGGIORNAMENTO UTENTE
function validateFormUpdate(event) {
    var isValid = true;

    var nome = document.getElementById('nome').value.trim();
    var cognome = document.getElementById('cognome').value.trim();
    var email = document.getElementById('email').value.trim();
    var indirizzo = document.getElementById('indirizzo').value.trim();

    document.getElementById('nomeError').textContent = '';
    document.getElementById('cognomeError').textContent = '';
    document.getElementById('emailError').textContent = '';
    document.getElementById('indirizzoError').textContent = '';

    // Validazione Nome
    if (nome === '' || !/^[a-zA-Z]+$/.test(nome)) {
        document.getElementById('nome').style.border = "1px dotted red";
        document.getElementById('nome').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('nomeError').textContent = 'Il nome deve contenere solo lettere.';
        isValid = false;
    } else {
        document.getElementById('nome').style.border = "";
        document.getElementById('nome').style.backgroundColor = "";
    }

    // Validazione Cognome
    if (cognome === '' || !/^[a-zA-Z]+$/.test(cognome)) {
        document.getElementById('cognome').style.border = "1px dotted red";
        document.getElementById('cognome').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('cognomeError').textContent = 'Il cognome deve contenere solo lettere.';
        isValid = false;
    } else {
        document.getElementById('cognome').style.border = "";
        document.getElementById('cognome').style.backgroundColor = "";
    }

    // Validazione Email
    if (email === '' || !/^\S+@\S+\.\S+$/.test(email)) {
        document.getElementById('email').style.border = "1px dotted red";
        document.getElementById('email').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('emailError').textContent = 'L\'email deve essere in un formato valido.';
        isValid = false;
    } else {
        document.getElementById('email').style.border = "";
        document.getElementById('email').style.backgroundColor = "";
    }

    // Validazione Indirizzo
    if (indirizzo === '' || !/^Via [a-zA-Z]+(?: [a-zA-Z]+)* N\.\d+$/.test(indirizzo)) {
        document.getElementById('indirizzo').style.border = "1px dotted red";
        document.getElementById('indirizzo').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('indirizzoError').textContent = 'L\'indirizzo necessita del numero civico.';
        isValid = false;
    } else {
        document.getElementById('indirizzo').style.border = "";
        document.getElementById('indirizzo').style.backgroundColor = "";
    }

    if (!isValid) {
        event.preventDefault();
    }
    return isValid;
}




// FUNZIONE AGGIORNAMENTO PRODOTTO
function validateFormProd(event) {
    var isValid = true;

    // Ottieni i valori dei campi di input
    var nome = document.getElementById('nome').value.trim();
    var descrizione = document.getElementById('descrizione').value.trim();
    var prezzo = document.getElementById('prezzo').value.trim();
    var prezzoXDays = document.getElementById('prezzoXDays').value.trim();
    var quantita = document.getElementById('quantita').value.trim();
    var inCatalogo = document.querySelector('input[name="inCatalogo"]:checked');
    var immagine = document.getElementById('immagine').files.length;

    // Svuota i messaggi di errore
    document.getElementById('nomeError').textContent = '';
    document.getElementById('descrizioneError').textContent = '';
    document.getElementById('prezzoError').textContent = '';
    document.getElementById('prezzoXDaysError').textContent = '';
    document.getElementById('quantitaError').textContent = '';
    document.getElementById('inCatalogoError').textContent = '';
    document.getElementById('immagineError').textContent = '';

    // Validazione Nome
    if (nome === '' || !/^(?![\d\s]+$)[a-zA-Z0-9\s]+$/.test(nome)) {
        document.getElementById('nome').style.border = "1px dotted red";
        document.getElementById('nome').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('nomeError').textContent = 'Il nome e\' obbligatorio e non puo\' contenere solo numeri.';
        isValid = false;
    } else {
        document.getElementById('nome').style.border = "";
        document.getElementById('nome').style.backgroundColor = "";
    }

    // Validazione Descrizione
    if (descrizione === '') {
        document.getElementById('descrizione').style.border = "1px dotted red";
        document.getElementById('descrizione').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('descrizioneError').textContent = 'La descrizione e\' obbligatoria.';
        isValid = false;
    } else {
        document.getElementById('descrizione').style.border = "";
        document.getElementById('descrizione').style.backgroundColor = "";
    }

    // Validazione Prezzo
    if (prezzo === '' || parseFloat(prezzo) < 1) {
        document.getElementById('prezzo').style.border = "1px dotted red";
        document.getElementById('prezzo').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('prezzoError').textContent = 'Il prezzo e\' obbligatorio e deve essere almeno 1.';
        isValid = false;
    } else {
        document.getElementById('prezzo').style.border = "";
        document.getElementById('prezzo').style.backgroundColor = "";
    }

    // Validazione PrezzoXDays
    if (prezzoXDays === '' || parseFloat(prezzoXDays) < 1) {
        document.getElementById('prezzoXDays').style.border = "1px dotted red";
        document.getElementById('prezzoXDays').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('prezzoXDaysError').textContent = 'Il prezzo per giorno e\' obbligatorio e deve essere almeno 1.';
        isValid = false;
    } else {
        document.getElementById('prezzoXDays').style.border = "";
        document.getElementById('prezzoXDays').style.backgroundColor = "";
    }

    // Validazione Quantità
    if (quantita === '' || parseInt(quantita) < 0) {
        document.getElementById('quantita').style.border = "1px dotted red";
        document.getElementById('quantita').style.backgroundColor = "#fff4e6"; // Colore arancione chiaro
        document.getElementById('quantitaError').textContent = 'La quantita\' e\' obbligatoria e non puo\' essere negativa.';
        isValid = false;
    } else {
        document.getElementById('quantita').style.border = "";
        document.getElementById('quantita').style.backgroundColor = "";
    }

    // Validazione InCatalogo
    if (!inCatalogo) {
        document.getElementById('inCatalogoError').textContent = 'Seleziona se il prodotto e\' in catalogo.';
        isValid = false;
    }

  

    if (!isValid) {
        event.preventDefault();
    }
    return isValid;
}
