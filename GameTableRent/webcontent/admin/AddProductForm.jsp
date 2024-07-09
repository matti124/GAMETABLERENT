<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Aggiungi Prodotto</title>
<link rel="stylesheet" type="text/css" href="../CSS/RegLog.css">
</head>
<body>
	<div class="main-content">
		<div class="container">
			<h2>Aggiungi un Nuovo Prodotto</h2>
			<form action="<%=request.getContextPath()%>/ProductControl" enctype="multipart/form-data"
				method="post">
				<input type="hidden" name="action" value="aggiungi"> 
				<label for="nome">Nome:</label> <input type="text" id="nome" name="nome"
					required> 
					<label for="descrizione">Descrizione:</label>
				<textarea id="descrizione" name="descrizione" required
					style="height: 200px"></textarea>

				<div class="form-group-inline">
					<label for="prezzo">Prezzo:</label> <input type="number"
						step="0.01" min=1 id="prezzo" name="prezzo" required>
				</div>
				<div class="form-group-inline">
					<label for="prezzoXDays">Prezzo per Giorno:</label> <input
						type="number" min=1 step="0.01" id="prezzoXDays" name="prezzoXDays"
						required>
				</div>

				<div class="form-group-inline">
					<label for="inCatalogo">In Catalogo:</label> <select
						id="inCatalogo" name="inCatalogo">
						<option value="1">Si</option>
						<option value="0">No</option>
					</select>

				</div>

				<div class="form-group-inline">
					<label for="quantita">Quantità:</label> <input type="number"
						id="quantita" name="quantita" required min="0">
				</div>

				<label for="immagine">Immagine:</label> <input type="file"
					id="immagine" name="immagine" accept="image/*">

				<button type="submit">Aggiungi Prodotto</button>
			</form>
			<div class="error-message">
				<%=request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : ""%>
			</div>
		</div>
	</div>
</body>
</html>
