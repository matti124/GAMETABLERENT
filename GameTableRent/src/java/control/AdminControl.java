package control;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.OrdineDAO;
import model.OrdineDTO;
import model.UtenteDAO;
import model.UtenteDTO;

/*
 * AZIONI GESTITE DALLA SERVLET ADMIN CONTROL:
 * 1) VEDERE TUTTI GLI ORDINI EFFETTUATI IN UN CERTO LASSO DI TEMPO
 * 2) ELIMINARE UN PRODOTTO 
 * 3) MODIFICARE UN PRODOTTO
 * 4) AGGIUNGERE UN PRODOTTO
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
		
		case "allOrders":{
			this.ProfitXMonth(request, response);
			this.AllOrders(request, response);
			break;
			
		}
		case "allOrdersByDate":{ 
			this.ProfitXMonth(request, response);

			this.AllOrdersByDate(request, response);
			break;}
			
		case "addProduct":{ //mi indirizza alla pagina in cui compilerò il form per il nuovo elemento la quale passerà i dati a ProductControl?action=aggiungi
			response.sendRedirect(request.getContextPath()+"/NewProduct.jsp");
			break;}
			
			
			
		case "updateProd":{
			response.sendRedirect(request.getContextPath()+"/UpdateProduct.jsp"); //mi indirizza alla pagina form dove potrò aggiornare il prodotto e i dati modificati verranno inoltrati a ProuctControl?action=updateProduct
			break;}
			
		case "removeProd":{
			request.getRequestDispatcher("/ProductoControl?action=elimina");
			break;}
		
		
		case "AllAccounts":{
			this.AllAccounts(request, response);
			break;
		}
		
		case "AccountDetails":{
			this.AccountDetails(request, response);
		}
			
		}
		
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	

	private void AllOrdersByDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String startDateStr = request.getParameter("Start");
	        String endDateStr = request.getParameter("end");
	        Timestamp start = Timestamp.valueOf(startDateStr + " 00:00:00");
	        Timestamp end = Timestamp.valueOf(endDateStr + " 23:59:59");
	      System.out.println(start + "\n"+ end);

	       
		OrdineDAO dao=new OrdineDAO();
		ArrayList<OrdineDTO> ordini=dao.doRetrieveByDate(start, end);
		request.setAttribute("ordini", ordini);
		request.getRequestDispatcher("/admin/AllOrders.jsp").forward(request,response); //pagina per vedere tutti gli ordini in uno specifico arco di tempo
	}
	
	
	private void AllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrdineDAO dao= new OrdineDAO();
		ArrayList<OrdineDTO> ordini=dao.doRetrieveAll();
		request.setAttribute("ordini", ordini);
		System.out.println("ti inolotro alla pagina");
		request.getRequestDispatcher("/admin/AllOrders.jsp").forward(request, response);
	}
	
	
	private void AllAccounts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtenteDAO dao=new UtenteDAO();
		ArrayList<UtenteDTO> utenti=dao.doRetrieveAll();
		request.setAttribute("utenti", utenti);
		request.getRequestDispatcher("/admin/Accounts.jsp").forward(request, response);
	}
	private void AccountDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_utente=Integer.parseInt(request.getParameter("id_utente"));
		UtenteDAO userDAO=new UtenteDAO();
		UtenteDTO user=userDAO.doRetrieveByKey(id_utente);
		request.setAttribute("utenteDettagli", user);
		request.getRequestDispatcher("/admin/AccountDetails.jsp").forward(request, response);
	}

	/* FUNZIONE CHIAMATA OGNI VOLTA CHE UN ADMIN VUOLE VISUALIZZARE PANNELLO DEGLI ORDINI IN MODO DA SAPERE I PROFITTI MENSILI SEMPRE AGGIORNATI 
	 * CIO' CHE SI FA E ANDARE A PRELEVARE TRAMITE UN CICLO FOR UNA STRINGA CHE INDICA LA DATA DI PARTENZA DOVE 2024 E 01 SONO FISSI E CAMBIA IL MESE, MENTRE
	 * PER LA DATA DI FINE ABBIAMO UNO SWITCH DOVE SE IL MESE E' DUE ALLORA SARA' 28 OPPURE 30 PER 4,6,9,11
	 * DOPODICHE ANDIAMO A PRELEVARE GLI ORDINI PER OGNI MESE NEL CICLO E INSERIAMO NEL VETTORE TOTALS NELLA POSIZIONE DI QUEL MESE LA SOMMA TOTALE GUADAGNATA
	 * 
	 * 
	 * 
	 */
	private void ProfitXMonth(HttpServletRequest request, HttpServletResponse response) {
	    double[] totals = new double[13]; // Array per 12 mesi + 1 per l'indice 0
	    OrdineDAO dao = new OrdineDAO();
	    
	    for (int i = 1; i < 13; i++) {
	        String startStr = String.format("2024-%02d-01 00:00:00", i);
	        String endStr = String.format("2024-%02d-31 23:59:59", i);
	        
	        switch (i) {
	            case 2:  endStr = "2024-02-29 23:59:59"; break; // Anno bisestile
	            case 4:
	            case 6:
	            case 9:
	            case 11: endStr = String.format("2024-%02d-30 23:59:59", i); break;
	        }
	        
	        Timestamp start = Timestamp.valueOf(startStr);
	        Timestamp end = Timestamp.valueOf(endStr);
	        ArrayList<OrdineDTO> ordini = dao.doRetrieveByDate(start, end);
	        totals[i] = ordini.stream().mapToDouble(ordine -> ordine.getTotalPrice()).sum();
	    }
	    
	    request.setAttribute("profitti", totals);
	}


}
