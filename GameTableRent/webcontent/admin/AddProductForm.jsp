<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Aggiungi Prodotto</title>
<link rel="stylesheet" type="text/css" href="../CSS/RegLog.css">
<script src="../script/ValidatingForms.js"></script>
</head>
<body>
<div class="main-content">
    <div class="container">
        <h2>Aggiungi un Nuovo Prodotto</h2>
        <form id="productForm" action="<%=request.getContextPath()%>/ProductControl" enctype="multipart/form-data" method="post" onsubmit="return validateFormProd(event)">
            <input type="hidden" name="action" value="aggiungi">

            <label for="nome">Nome:</label> 
            <input type="text" id="nome" name="nome" required placeholder="Nome Prodotto">
            <label id="nomeError" class="error-message"></label> <br><br>

            <label for="descrizione">Descrizione:</label>
            <textarea id="descrizione" name="descrizione" required placeholder="Descrizione del prodotto" style="height: 200px"></textarea>
            <label id="descrizioneError" class="error-message"></label><br><br>

            <div class="form-group-inline">
                <label for="prezzo">Prezzo:</label> 
                <input type="number" step="0.01" min=1 id="prezzo" name="prezzo" required placeholder="Prezzo in Euro">
                <label id="prezzoError" class="error-message"></label>
            </div>

            <div class="form-group-inline">
                <label for="prezzoXDays">Prezzo per Giorno:</label> 
                <input type="number" min=1 step="0.01" id="prezzoXDays" name="prezzoXDays" required placeholder="Prezzo per giorno">
                <label id="prezzoXDaysError" class="error-message"></label>
            </div>

            <div class="form-group-inline">
                <label for="quantita">Quantità:</label> 
                <input type="number" id="quantita" name="quantita" required min="0" placeholder="Quantità disponibile">
                <label id="quantitaError" class="error-message"></label>
            </div>

            <div class="form-group-inline">
                <label for="inCatalogo">In Catalogo:</label> 
                <label>
                    <input type="radio" name="inCatalogo" value="1"> Si
                </label>
                <label>
                    <input type="radio" name="inCatalogo" value="0"> No
                </label>
                <label id="inCatalogoError" class="error-message"></label>
            </div>

            <label for="immagine">Immagine:</label> 
            <input type="file" id="immagine" name="immagine" accept="image/*">
            <label id="immagineError" class="error-message"></label>

            <button type="submit">Aggiungi Prodotto</button>
        </form>
       
    </div>
</div>

</body>
</html>
