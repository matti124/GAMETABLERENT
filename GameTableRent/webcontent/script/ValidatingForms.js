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
		document.getElementById('nome').style.border="1px dotted red";
        document.getElementById('nomeError').textContent = 'Il nome deve contenere solo lettere.';
        isValid = false;
    }

    // Validazione Cognome
    if (cognome === '' || !/^[a-zA-Z]+$/.test(cognome)) {
				document.getElementById('cognome').style.border="1px dotted red";

        document.getElementById('cognomeError').textContent = 'Il cognome deve contenere solo lettere.';
        isValid = false;
    }

    // Validazione Email
    if (email === '' || !/^\S+@\S+\.\S+$/.test(email)) {
				document.getElementById('email').style.border="1px dotted red";

        document.getElementById('emailError').textContent = 'L\'email deve essere in un formato valido.';
        isValid = false;
    }

    // Validazione Password
    if (password === '' || password.length < 8) {
				document.getElementById('password').style.border="1px dotted red";

        document.getElementById('passwordError').textContent = 'La password deve avere almeno 8 caratteri.';
        isValid = false;
    }

    // Validazione Indirizzo
if (indirizzo === '' || /^Via [a-zA-Z]+(?: [a-zA-Z]+)* N\.\d+$/.test(indirizzo)) {
				document.getElementById('indirizzo').style.border="2px solid red";
								document.getElementById('indirizzo').style.backgroundColor="salmon";


        document.getElementById('indirizzoError').textContent = 'L\'indirizzo necessita del numero civico.';
        isValid = false;
    }

    if (!isValid) {
        event.preventDefault();
    }
    return isValid;
}
