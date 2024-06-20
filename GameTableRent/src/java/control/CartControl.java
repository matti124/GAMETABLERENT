package control;

import model.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;




/*
 AZIONI GESTITE DALLA SERVLET CART CONTROL:
 
 1)VISTA DEI PRODOTTI DI UNO SPECIFICO UTENTE
 2)PULIZIA TOTALE DI UN CARRELLO
 3)ELIMINAZIONE CARRELLO[IN CASO UN UTENTE VENISSE ELIMINATO ANCHE IL SUO CARRELLO VERREBBE ELIMINATO]
 4)AGGIUNTA PRODOTTO AL CARRELLO [NECESSITA DI PARAMETRO CODICE DALLA GET]
 5)RIMOZIONE PRODOTTO DAL CARRELLO [NECESSITA DI PARAMETRO CODICE DALLA GET]

 */
@WebServlet(name = "CarrelloServlet", value = "/carrello")
public class CartControl extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
	private CarrelloDAO carrelloDAO;

    public void init() {
        carrelloDAO = new CarrelloDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        switch (action) {
        
            case "view":
                viewCart(request, response);
                break;
                
                
            case "clear":
                clearCart(request, response);
                break;
                
                
            case "delete":
                deleteCart(request, response);
                break;
                
            case "addCart":
            	addToCart(request,response);
            	break;
            	
            	
            case "removeFromCart":
            	removeFromCart(request, response);
            	
            	
            	
            default:
                response.sendRedirect(request.getContextPath());
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void viewCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { //azione che avviene quando si preme sul tasto per andare al carrello
       
    	UtenteDTO user=(UtenteDTO) request.getSession().getAttribute("utente");
        CarrelloDTO cart = carrelloDAO.doRetrieveById(user.getID());

        if (cart != null) {
            request.setAttribute("cart", cart);
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        } else {
            // Handle case where cart is not found
            response.getWriter().println("Cart not found.");
        }
    }

    private void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	UtenteDTO user=(UtenteDTO) request.getSession().getAttribute("utente");

        CarrelloDTO cart = carrelloDAO.doRetrieveById(user.getID());

        if (cart != null) {
            boolean cleared = carrelloDAO.doFreeSpace(cart.getID_Carrello());

            if (cleared) {
                response.sendRedirect(request.getContextPath() + "/carrello?action=view");
            } else {
                response.getWriter().println("Failed to clear cart.");
            }
        } else {
            // Handle case where cart is not found
            response.getWriter().println("Cart not found.");
        }
    }

    
    
    
    
    private void deleteCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int id_exUtente=(int) request.getAttribute("id_exUt");
        CarrelloDTO cart = carrelloDAO.doRetrieveById(id_exUtente);

        if (cart != null) {
            boolean deleted = carrelloDAO.doDeleteByKey(cart.getID_Carrello());

            if (deleted) {
                response.sendRedirect(request.getContextPath() + "/Home.jsp");
            } else {
                response.getWriter().println("Failed to delete cart.");
            }
        } else {
            // Handle case where cart is not found
            response.getWriter().println("Cart not found.");
        }
    }
    
    
    
    private void addToCart(HttpServletRequest request, HttpServletResponse response) {
    	
    	int id_prod=Integer.parseInt(request.getParameter("codice_prod"));
    	int quantity=Integer.parseInt(request.getParameter("quantity"));
    	int giorni=Integer.parseInt(request.getParameter("days"));
    	
    	
    	UtenteDTO user=(UtenteDTO) request.getSession().getAttribute("utente");
    	ProdottoCarrelloDAO prodDAO= new ProdottoCarrelloDAO();
    	ProdottoCarrelloDTO prod;
        CarrelloDTO cart = carrelloDAO.doRetrieveById(user.getID());
        ProdottoDAO prodottoDAO= new ProdottoDAO();
        ProdottoDTO prodotto=null;
        
    	
        if(cart.contains(id_prod)) { //se il carrello contiene già un elemento di quel tipo 
    		prod=prodDAO.doRetrieveByKey(cart.getID_Carrello(), id_prod);
    		cart.addProduct(prod);
    		carrelloDAO.doUpdateCart(cart);}
        
      
 //se l'elemento non è già nel carrello devo ritrovare nel db il prezzo e prezzoxdays di esso e poi aggiungerlo al carrello e crearne una riga in ProdottoCarrello
        else 
        	prodotto=prodottoDAO.doRetrieveByKey(id_prod);
        if(prodotto!=null) {
        	prod=new ProdottoCarrelloDTO(cart.getID_Carrello(),prodotto.getID_Prod(),  prodotto.getPrezzo(), prodotto.getPrezzoXDay(),quantity, giorni);
        	prodDAO.doSave(prod);       	
        	cart.addProduct(prod);
        	carrelloDAO.doSave(cart);}
        
        try {
			response.sendRedirect(request.getContextPath() + "/carrello?action=view");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    	}
    
    
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_prod = Integer.parseInt(request.getParameter("codice_prod"));
        
        UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("utente");
        
        CarrelloDTO cart = carrelloDAO.doRetrieveById(user.getID());
        ProdottoCarrelloDAO prodDAO= new ProdottoCarrelloDAO();
        ProdottoCarrelloDTO prod;
        
        if (cart != null) {
        	prod=prodDAO.doRetrieveByKey(cart.getID_Carrello(), id_prod);
        	cart.decreaseProduct(prod);
        	carrelloDAO.doUpdateCart(cart);
        	if(!cart.getCart().contains(prod))
        		prodDAO.doDelete(prod);
        	
        }
        try {
     			response.sendRedirect(request.getContextPath() + "/carrello?action=view");
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
        	
        	
            
    }

    	
    }

