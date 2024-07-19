<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        /* Stile per l'header */
        header {
            background: linear-gradient(to bottom, rgba(255, 255, 0, 0.4), rgba(78, 143, 0, 0.4), rgba(255, 165, 0, 0.4));
            padding: 15px 30px;
            text-align: center;
            border-bottom: 2px solid rgba(78, 143, 0, 0.5); /* Colore bordo verde più visibile */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .logo {
            display: inline-block;
            padding: 10px;
            border-radius: 15px;
            background-color: rgba(255, 255, 255, 0.8); /* Sfondo bianco trasparente */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Aggiunge ombra */
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .logo img {
            max-height: 60px;
            border-radius: 10px;
            transition: transform 0.3s;
        }

        .logo:hover {
            transform: scale(1.1);
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3); /* Ombratura più intensa al passaggio del mouse */
        }

        nav {
            margin-top: 15px;
            display: flex;
            justify-content: center;
            gap: 20px;
        }

        nav a {
            text-decoration: none;
            font-style: italic;
            font-size: large;
            color: #4e8f00; /* Colore testo verde */
            padding: 10px 15px;
            font-weight: bold;
            border-radius: 20px;
            background-color: rgba(255, 255, 255, 0.8); /* Sfondo bianco trasparente */
            transition: background-color 0.3s, transform 0.3s;
        }

        nav a:hover {
            background-color: rgba(212, 251, 120, 0.4); /* Sfondo giallo con maggiore visibilità */
            transform: translateY(-3px);
        }

        /* Stile responsive */
        @media screen and (max-width: 580px) {
            header {
                padding: 10px 20px;
            }

            .logo img {
                max-height: 50px;
            }

            nav {
                flex-direction: column;
                gap: 5px;
                font-size: medium;
            }
        }
    </style>
</head>
<body>

    <header>
        <div class="logo">
            <img src="Pictures/GAME TABLE.png" alt="Logo">
        </div>
        <nav>
            <% 
                UtenteDTO utente = (UtenteDTO) request.getSession().getAttribute("user");
                Boolean valid = null;
                if (utente != null) {
                    valid = utente.getIsAdmin() == 1 ? true : false;
                }

                if (valid == null) { %>
                    <a href="Home.jsp">Home</a>
                    <a href="Registrazione.jsp">Registrati</a>
                    <a href="Login.jsp">Login</a>
                    <a href="ProductControl?action=mostraProdotti">Catalogo</a>
                    <a href="Cart.jsp">Carrello</a>
                <% } else if (valid) { %>
                    <a href="admin/AdminHome.jsp">Home</a>
                    <a href="Account.jsp">Account</a>
                    <a href="<%= request.getContextPath() %>/AdminControl?action=allOrders">Ordini</a>
                    <a href="<%= request.getContextPath() %>/AdminControl?action=allProducts">Prodotti</a>
                    <a href="ProductControl?action=mostraProdotti">Catalogo</a>
                <% } else { %>
                    <a href="UserHome.jsp">Home</a>
                    <a href="Account.jsp">Account</a>
                    <a href="ProductControl?action=mostraProdotti">Catalogo</a>
                    <a href="Cart.jsp">Carrello</a>
                <% } %>         
        </nav>
    </header>
</body>
</html>
