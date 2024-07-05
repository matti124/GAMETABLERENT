package control;
import model.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
AZIONI CHE DEVE GESTIRE LA SERVLET ORDINE CONTROL:


1)CREAZIONE DI UN NUOVO ORDINE (Comporta pulizia del carrello)
2)DETTAGLI DI UN PRECISO ORDINE

3)VISTA DI TUTTI GLI ORDINI IN UN CERTO LASSO DI TEMPO (SOLO ADMIN)-> LA INSERIAMO IN ADMIN CONTROL
 [MODIFICA E ELIMINAZIONE DI ORDINI SONO OPERAZIONI NON FATTIBILI IN UN E-COMMERCE POICHE' POTREBBE COMPROMETTERE LA SICUREZZA DEI DATI]

 *
 *
 */
@WebServlet("/OrdineControl")
public class OrdineControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdineControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtenteDTO user=(UtenteDTO) request.getSession().getAttribute("user");
		
		
		if(user==null) {
			request.setAttribute("NonRegisteredUser", 1);
			request.getRequestDispatcher("/Registration.jsp").forward(request, response);}
		
		String action= request.getParameter("action");
		
		switch (action) {
		
		
		case "newOrdine":
				this.checkout(request, response);
				break;
		
		case "OrderDetails":
				this.ViewOrder(request, response);
				break;
				
				
		case "AllOrdersByDate":
				this.AllOrdersByDateForUser(request, response, user.getID());
				
				break;
				
		
			
		}
		
	}

	


	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	
	
	private void checkout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		/*Un ordine viene creato ogni qualvolta un utente effetua il checkout, quindi preme sul tasto effettua ordine. 
		 * Il click del mouse indirizza l'utente alla servlet OrdineControl con azione createOrdine quindi sarà /OrdineControl?action=checkout, 
		 * la servlet va a creare un nuovo ordine relativo a quell'utente, quindi avrà:
		 *  il campo id_ordine compilato automaticamente dal db, 
		 *  il campo id_utente settato da noi prendendolo dalla sessione
		 *  il campo prezzo che sarà settato a 0 poichè non abbiamo ancora immesso nel DB i prodotti dell'ordine
		 *  
		 *  Una volta creato l'ordine, vado a prelevare tutti i prodotti del carrello tramite la funzione checkout che prende come parametro l'id dell'ordine
		 *  per poter inizializzare in maniera corretta i vari Prodotto_Ordine e ritorno una lista di questo tipo
		 *  Per ogni oggetto Prodotto_Ordine, che presenterà id_ordine quello dell'ordine appena creato in precedenza e id_utente quello dell'utente attuale, 
		 *  lo andremo ad aggiungere alla lista dei prodotti dell'ordine attributo dell'oggetto ordineDTO e in più andremo a salvare nel DB ogni singolo ProdottoOrdine
		 *  Infine liberiamo lo spazio del carrello 
		 * 
		 * 
		 * 
		 */
		OrdineDAO ordDAO=new OrdineDAO();
		ProdottoOrdineDAO prodDAO=new ProdottoOrdineDAO();
		CarrelloDTO cart=(CarrelloDTO)request.getSession().getAttribute("cart"); //controllo front end che il carrello non sia vuoto (sul click del bottone)
		UtenteDTO user=(UtenteDTO)request.getSession().getAttribute("user");

		OrdineDTO ordine=new OrdineDTO(0, user.getID(), null, new ArrayList<ProdottoOrdineDTO>());
		int keyIdOrd=ordDAO.doSave(ordine);
		System.out.println("Ho creato ordine vuoto e prelevo il suo id: "+ keyIdOrd);
		ArrayList<ProdottoOrdineDTO> prodottiInCart=new ArrayList<>();
	
		prodottiInCart=cart.CheckOut(keyIdOrd);
		//controllo quantità
		for(ProdottoOrdineDTO x: prodottiInCart) {
			ordine.getProdotti().add(x);
			
			prodDAO.doSave(x);
		}
		System.out.println("Aggiorno l'ordine coi prodotti acquistati: "+ ordine.getTotalPrice());
		ordine.setId_Ordine(keyIdOrd); //ASSEGNO ALL'ORDINE SALVATO VUOTO IL SUO ID RECUPERATO
		ordDAO.doUpdate(ordine);
		CarrelloDAO cartDAO=new CarrelloDAO();
		cartDAO.doFreeSpace(cart.getID_Carrello());
		response.sendRedirect(request.getContextPath()+"/UserHome.jsp");
		
		
	}
	
	
	

	private void ViewOrder(HttpServletRequest request, HttpServletResponse response) {
		int id_ordine=Integer.parseInt(request.getParameter("id_ord"));
		OrdineDAO ordDAO=new OrdineDAO();
		OrdineDTO ordine=ordDAO.doRetrieveByKey(id_ordine);
		request.setAttribute("ordine", ordine);
		try {
			request.getRequestDispatcher("/OrderDetail.jsp").forward(request, response); //pagina per vedere dettagli dell'ordine 
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void AllOrdersByDateForUser(HttpServletRequest request, HttpServletResponse response, int id_u) throws ServletException, IOException {
		 String startDateStr = request.getParameter("Start");
	        String endDateStr = request.getParameter("end");
	        
	        Timestamp start = Timestamp.valueOf(startDateStr + " 00:00:00");
	        Timestamp end = Timestamp.valueOf(endDateStr + " 23:59:59");

	       
		OrdineDAO dao=new OrdineDAO();
		ArrayList<OrdineDTO> ordini=dao.doRetrieveByDateForUser(start, end, id_u);
		request.setAttribute("listaOrdini", ordini);
		request.getRequestDispatcher("/Orders.jsp").forward(request,response); //pagina per vedere tutti gli ordini in uno specifico arco di tempo
	}
	
	

	

}
