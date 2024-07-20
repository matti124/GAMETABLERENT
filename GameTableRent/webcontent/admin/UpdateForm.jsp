<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Form di aggiornamento</title>
<link rel="stylesheet" type="text/css" href="CSS/RegLog.css">


</head>

<%ProdottoDTO prod=(ProdottoDTO)request.getAttribute("prodotto"); %>
<body>
<div class="main-content">
		<div class="container">
			<h2>Aggiorna Prodotto</h2>
			<form action="<%=request.getContextPath()%>/ProductControl" enctype="multipart/form-data"
				method="post">
				<input type="hidden" name="action" value="update">
				<input type="hidden" name="id" value="<%= ((ProdottoDTO)request.getAttribute("prodotto")).getID_Prod() %>">
				<label for="nome">Nome:</label> 
				<input type="text" id="nome" name="nome" required 
				       value="<%= ((ProdottoDTO)request.getAttribute("prodotto")).getNome() %>"> 
				       
				<label for="descrizione">Descrizione:</label>
				<textarea id="descrizione" name="descrizione" required style="height: 200px">
					<%= ((ProdottoDTO)request.getAttribute("prodotto")).getDescrizione() %>
				</textarea>

				<div class="form-group-inline">
					<label for="prezzo">Prezzo:</label> 
					<input type="number" step="0.01" min=1.00 id="prezzo" name="prezzo"  required
					       value="<%= ((ProdottoDTO)request.getAttribute("prodotto")).getPrezzo() %>">
				</div>
				<div class="form-group-inline">
					<label for="prezzoXDays">Prezzo per Giorno:</label> 
					<input type="number" min=1.00 step="0.01" id="prezzoXDays" name="prezzoXDays" required 
					       value="<%= ((ProdottoDTO)request.getAttribute("prodotto")).getPrezzoXDay() %>">
				</div>

				<div class="form-group-inline">
					<label for="quantita">Quantità:</label> 
					<input type="number" id="quantita" name="quantita" required min="0"
					       value="<%= ((ProdottoDTO)request.getAttribute("prodotto")).getQuantity() %>">
				</div>
				
				<div class="form-group-inline">
					<label for="inCatalogo">In Catalogo:</label> 
					<select id="inCatalogo" name="inCatalogo">
						<option value="1" <%= ((ProdottoDTO)request.getAttribute("prodotto")).getIN_CAT()==1 ? "selected" : "" %>>Si</option>
						<option value="0" <%= ((ProdottoDTO)request.getAttribute("prodotto")).getIN_CAT()==0 ? "selected" : "" %>>No</option>
					</select>
				</div>
				
				<label for="immagine">Immagine:</label> 
				 <% if (((ProdottoDTO)request.getAttribute("prodotto")).getImmagine() != null) { %>
                            <img class="immagineProd" alt="Immagine" src="<%= request.getContextPath() %>/ProductControl?action=image&id=<%= ((ProdottoDTO)request.getAttribute("prodotto")).getID_Prod() %>">
                        <% } else { %>
                            <img class="immagineProd" alt="Immagine" src="Pictures/defaultImage.png">
                        <% } %>
				<input type="file" id="immagine" name="immagine" accept="image/*">
				
				<button type="submit">Aggiorna Prodotto</button>
			</form>
			<div class="error-message">
				<%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>
			</div>
		</div>
	</div>

</body>
</html>