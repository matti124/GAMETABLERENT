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

        .logo img {
            max-height: 60px;
            transition: transform 0.3s;
        }

        .logo img:hover {
            transform: scale(1.1);
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
        @media screen and (max-width: 400px) {
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
            <% Boolean valid=null;
               UtenteDTO utente = (UtenteDTO) request.getSession().getAttribute("user");
            	if(utente==null)
            		valid=null;
            	else
               if(utente.getIsAdmin()==0){
            		valid=false;}
            	else if(utente.getIsAdmin()==1)
            		valid=true;
            	
               if (valid == null) { %>
                   <a href="Home.jsp">Home</a>
                   <a href="Login.jsp">Login</a>
                   <a href="ProductControl?action=mostraProdotti">Catalogo</a>
                   <a href="Cart.jsp">Carrello</a>
                   <%} else if(valid) {%>
                   <a href="admin/AdminHome.jsp"> Home</a>
                   <a href="Account.jsp"> Account</a>
            <a href="<%=request.getContextPath()%>/AdminControl?action=allOrders">Orders</a>
                    <a href="ProductControl?action=mostraProdotti">Catalogo</a>
            
                   
                   
                   <%} else if(!valid){ %>
                   <a href="UserHome.jsp">Home</a>
                   <a href="Account.jsp">Account</a>
                   <a href="ProductControl?action=mostraProdotti">Catalogo</a>
                   <a href="Cart.jsp">Carrello</a>

                   
                   <%} %>         
        </nav>
    </header>
</body>
</html>
