package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class UserControl
 */
@WebServlet("/UserControl")
public class UserControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String azione=request.getParameter("action");
		UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("user");
       UtenteDAO userDAO=new UtenteDAO();
		switch (azione) {
		
		
		
		
		case "Ordini": //ci sarà un tasto apposito che quando premuto carica gli ordini
			 {
            ArrayList<OrdineDTO> ordini = this.getInformazioni(user.getID());
            request.setAttribute("listaOrdini", ordini);
            request.getRequestDispatcher("Orders.jsp").forward(request, response);
            System.out.println("Ho trovato e settato gli ordini");
            break;
           }
     
			 
			 
        
		case "Update": //sulla pagina ci sarà un tasto "modifica" che permette di modificare i campi delle informazioni dell'utente, una volta premuto submit viene chiamata questa pagina
		{
		
		String nome=request.getParameter("nome");
		String cognome=request.getParameter("cognome");
		String email=request.getParameter("email");
		String indirizzo=request.getParameter("indirizzo");

	
		
		user.setNome(nome);
		user.setCognome(cognome);
		user.setIndirizzo(indirizzo);
		user.setEmail(email);
		
		userDAO.doUpdate(user);
		request.getSession().setAttribute("user", user);
		request.getRequestDispatcher("Account.jsp").forward(request, response);
		return;
		}
		
		
		
		
		case "Logout":  //una volta premuto il tasto invalido la sessione e mando l'utente alla pagina principale
		{
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath()+"/Home.jsp");
			return;
		}
		
		case "Delete":
		{
			userDAO.doDeleteByKey(user.getID());
			response.sendRedirect(request.getContextPath()+"/Home.jsp");
			return;
		}
		
		
		
		
			
		
			
			
		
		
		
		default:
	        // Azione non riconosciuta, gestione dell'errore
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Azione non valida");
	        return;
		
		
		}
		
        }

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	
	//ritorna tutti gli ordini effettuati da un utente
	private ArrayList<OrdineDTO> getInformazioni(int id){
		OrdineDAO ordDAO= new OrdineDAO();
		ArrayList<OrdineDTO> ordini=new ArrayList<>();
		ordini=ordDAO.doRetrieveAllbyId(id);
		return ordini;
	}

}
