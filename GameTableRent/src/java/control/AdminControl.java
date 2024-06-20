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

import model.OrdineDAO;
import model.OrdineDTO;

/*
 * AZIONI GESTITE DALLA SERVLET ADMIN CONTROL:
 * 1) VEDERE TUTTI GLI ORDINI EFFETTUATI IN UN CERTO LASSO DI TEMPO
 * 2) ELIMINARE UN PRODOTTO 
 * 3) MODIFICARE UN PRODOTTO
 * 4) AGGIUNGERE UN PRODOTTO
 * 
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
			this.AllOrders(request, response);
			break;}
			
		case "addProduct":{ //mi indirizza alla pagina in cui compilerò il form per il nuovo elemento la quale passerà i dati a ProductControl?action=aggiungi
			response.sendRedirect(request.getContextPath()+"/NewProduct.jsp");
			break;}
			
			
			
		case "updateProd":{
			response.sendRedirect(request.getContextPath()+"/UpdateProduct.jsp"); //mi indirizza alla pagina form dove potrò aggiornare il prodotto e i dati modificati verranno inoltrati a ProuctControl?action=updateProduct
			break;}
			
		case "removeProd":{
			response.sendRedirect(request.getContextPath()+"/ProductoControl?action=elimina");
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
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	

	private void AllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Timestamp start=Timestamp.valueOf(request.getParameter("Start")); //sarà necessaria una regular expression di javascriprt per essere sicuro che sia corretta la data
		Timestamp end=Timestamp.valueOf(request.getParameter("End"));
		OrdineDAO dao=new OrdineDAO();
		ArrayList<OrdineDTO> ordini=dao.doRetrieveByDate(start, end);
		request.setAttribute("listaOrdini", ordini);
		request.getRequestDispatcher("/AllOrders.jsp").forward(request,response); //pagina per vedere tutti gli ordini in uno specifico arco di tempo
	}


}
