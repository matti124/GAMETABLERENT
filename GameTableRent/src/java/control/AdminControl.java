package control;
import model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/*
 * AZIONI GESTITE DALLA SERVLET ADMIN CONTROL:
 * 1) VEDERE TUTTI GLI ORDINI EFFETTUATI IN UN CERTO LASSO DI TEMPO
 * 2) ELIMINARE UN PRODOTTO 
 * 3) MODIFICARE UN PRODOTTO
 * 4) AGGIUNGERE UN PRODOTTO
 * 5) VEDERE TUTTI GLI UTENTI REGISTRATI NEL SITO
 * 
 * 
 */
@WebServlet("/AdminControl")
public class AdminControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String azione=request.getParameter("action");
		
		switch (azione) {
		
		
		case "allOrdersByDate":{ 
			Timestamp start=Timestamp.valueOf(request.getParameter("Start")); //sarà necessaria una regular expression di javascriprt per essere sicuro che sia corretta la data
			Timestamp end=Timestamp.valueOf(request.getParameter("End"));
			request.setAttribute("start", start);
			request.setAttribute("end", end);
			request.getRequestDispatcher("/OrdineControl?action=allOrdersByDate").forward(request, response);;
			break;}
		
		
		
		case "allOrders":
			response.sendRedirect(request.getContextPath()+"/OrdineControl?action=allOrders");
			break;
			
			
			
			
			
		case "addProduct":{ //mi indirizza alla pagina in cui compilerò il form per il nuovo elemento la quale passerà i dati a ProductControl?action=aggiungi
				response.sendRedirect(request.getContextPath()+"/NewProduct.jsp");
				break;}
			
			
			
		case "updateProd":{//mi indirizza alla pagina form dove potrò aggiornare il prodotto e i dati modificati verranno inoltrati a ProuctControl?action=updateProduct
			int id_prod=Integer.parseInt(request.getParameter("id_prod"));
			request.setAttribute("id_prod", id_prod);
			request.getRequestDispatcher("UpdateProductForm.jsp").forward(request, response); 
			break;}
			
		case "removeProd":{
			int id_prod=Integer.parseInt(request.getParameter("id_prod"));
			request.getRequestDispatcher("ProductControl?action=delete&id_prod="+id_prod).forward(request, response); 			
			break;}
		
		
		case "seeAllUsers":{
			ArrayList<UtenteDTO> listaUtenti= new ArrayList<>();
			UtenteDAO userDAO=new UtenteDAO();
			try {
				listaUtenti=userDAO.doRetrieveAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("listaUtenti", listaUtenti);
			request.getRequestDispatcher("/ListaUtenti.jsp").forward(request, response);
			
		}
		
		case "DetailsUser":
			OrdineDAO ordDAO=new OrdineDAO();
			UtenteDAO userDAO=new UtenteDAO();
			int id_user=Integer.parseInt(request.getParameter("id_user"));
			try {
				UtenteDTO userWanted=userDAO.doRetrieveByKey(id_user);
				ArrayList<OrdineDTO> ordiniUtente=ordDAO.doRetrieveAllbyId(id_user);
				request.setAttribute("ordiniUtente", ordiniUtente);
				request.setAttribute("dettagliUtente", userWanted);
				request.getRequestDispatcher("/UserDetails.jsp").forward(request, response);
			} catch (SQLException e) {
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Utente non trovato");
			}
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	



}
