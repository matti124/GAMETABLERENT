package control;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductControl
 */
@WebServlet("/ProductControl")
public class ProductControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		
	}
	
	
	
	
	
	
	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String azione=request.getParameter("action");
	UtenteDTO utente=(UtenteDTO)request.getSession().getAttribute("utente");

		
		ProdottoDAO prodDao= new ProdottoDAO();
		
		switch (azione) {
		
		
		case "update": {
			//quando verrà cliccato il tasto per modificare l'oggetto verrà passato anche l'id del prodotto cliccato
			if(utente.getIsAdmin()==1) {
			int id_prod=Integer.parseInt(request.getParameter("id"));
			String nome=request.getParameter("nome");
			String Descrizione= request.getParameter("descrizione");
			Double Prezzo=Double.parseDouble(request.getParameter("Prezzo"));
			Double PrezzoXDays=Double.parseDouble(request.getParameter("PrezzoXDays"));
			int inCat=Integer.parseInt(request.getParameter("incatalogo"));
			int quantity=Integer.parseInt(request.getParameter("quantità"));
			 String pictureString = request.getParameter("picture");
             byte[] pic = null;
             if (pictureString != null) {
                 pic = java.util.Base64.getDecoder().decode(pictureString);
             }	
             
             ProdottoDTO updateProd=prodDao.doRetrieveByKey(id_prod);
             
             updateProd.setNome(nome);
             updateProd.setDescrizione(Descrizione);
             updateProd.setPrezzo(Prezzo);
             updateProd.setPrezzoXDay(PrezzoXDays);
             updateProd.setQuantity(quantity);
             updateProd.setIN_CAT(inCat);
             updateProd.setImmagine(pic);
             
             prodDao.doUpdate(updateProd);
             
             response.sendRedirect(request.getContextPath()+"/Catalogo.jsp");
             return;}
			else response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Azione non valida");
 
             
             
		
		}
		
		
		case "dettaglio" :
		{
			int codice=Integer.parseInt(request.getParameter("codice"));
			ProdottoDTO prod=prodDao.doRetrieveByKey(codice);
			request.setAttribute("prodotto", prod);
			request.getRequestDispatcher("/ProductDetails.jsp").forward(request, response);
			return;
		}
		
		
		case "mostraProdotti" :
		{
			ArrayList<ProdottoDTO> listaProds=new ArrayList<>();
			listaProds=prodDao.doRetrieveAll();
			
			  ArrayList<ProdottoDTO> listaProdsInCat = new ArrayList<>();
		        for (ProdottoDTO prod : listaProds) {
		            if (prod.getIN_CAT() != 0) {
		                listaProdsInCat.add(prod);
		            }
		        }			
		        
		    request.setAttribute("ListaProdotti", listaProdsInCat);
			request.getRequestDispatcher("/Catalogo.jsp").forward(request, response);
			return;
		}
		
		case "elimina": //azione possibile solo se si è admin 
		{
			
			if(utente.getIsAdmin()==1) {
			int id_prod=Integer.parseInt(request.getParameter("id"));
			ProdottoDTO prod=prodDao.doRetrieveByKey(id_prod);
			prod.setIN_CAT(0);
			prodDao.doUpdate(prod);
			response.sendRedirect(request.getContextPath()+"/Catalogo.jsp");
			return;}
			else response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Azione non valida");
			
		}
		
		default: 
		{
			 response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Azione non valida");
		        return;	}}

}
} 
