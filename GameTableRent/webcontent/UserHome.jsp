<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.*,java.util.*"%>
    
    <%
    String name=null;
    UtenteDTO user=(UtenteDTO) request.getSession().getAttribute("user");
    if(user==null){
        response.sendError(HttpServletResponse.SC_FORBIDDEN); return;}

		else name= user.getNome();
	
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homepage con righe</title>
    <link rel="stylesheet" href="CSS/Home.css">
</head>
<body>
<div id="title"><span>Ciao <%=name%></span></div>
    <div class="container">
        <div class="row" id="row1User">
            <a href="Account.jsp"><button>Account</button></a>
        </div>
        <div class="row" id="row2User">
            <a href="Cart.jsp"><button>Carrello</button></a>
        </div>
        <div class="row" id="row3User">
			<a href="ProductControl?action=mostraProdotti"><button>Catalogo</button></a>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var title = document.getElementById('title');
            title.style.opacity = 0;
            title.style.transition = 'opacity 1s ease';

            setTimeout(function() {
                title.style.opacity = 1;
            }, 500); // Delay di 500ms prima di iniziare la transizione
        });
    </script>
</body>
</html>

