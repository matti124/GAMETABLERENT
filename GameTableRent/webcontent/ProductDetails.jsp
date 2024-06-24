<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettagli Prodotto</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 20px;
        }
        .product-details {
            border: 1px solid #ccc;
            padding: 20px;
            margin-bottom: 20px;
            max-width: 600px;
        }
        .product-details img {
            max-width: 100%;
            height: auto;
        }
    </style>
</head>
<body>
    <div class="product-details">
        <h2>Dettagli Prodotto</h2>
        <% model.ProdottoDTO prodotto = (model.ProdottoDTO) request.getAttribute("prodotto"); %>
        <% if (prodotto != null) { %>
            <h3><%= prodotto.getNome() %></h3>
            <p><strong>Descrizione:</strong> <%= prodotto.getDescrizione() %></p>
            <p><strong>Prezzo:</strong> <%= prodotto.getPrezzo() %> Euro</p>
            <p><strong>Prezzo per giorni:</strong> <%= prodotto.getPrezzoXDay() %> Euro</p>
            <p><strong>Quantità disponibile:</strong> <%= prodotto.getQuantity() %></p>
            <% if (prodotto.getImmagine() != null && prodotto.getImmagine().length > 0) { %>
                <img src="data:image/jpeg;base64,<%= new String(org.apache.tomcat.util.codec.binary.Base64.encodeBase64(prodotto.getImmagine())) %>">
            <% } %>
        <% } else { %>
            <p>Prodotto non trovato.</p>
        <% } %>
        <p><a href="<%= request.getContextPath() %>/ProductControl?action=mostraProdotti">Torna al catalogo</a></p>
    </div>
</body>
</html>
