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
<<<<<<< HEAD
 6)AGGIORNAMENTO CARRELLO 

 */
@WebServlet("/CartControl")
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
                
            
            	
            	
            
            	
            	
            default:
                response.sendRedirect(request.getContextPath());
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String action=request.getParameter("action");
      
       switch(action) {
       
       case "addToCart":
    	   this.addToCart(request, response);
    	   break;
       case "UpdateCart":
    	   	this.updateCart(request, response);
       
       
       }
       
    
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
    
    
    
	private void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
    	
    	int id_prod=Integer.parseInt(request.getParameter("codice_prod"));
    	int quantity=Integer.parseInt(request.getParameter("quantity"));
    	int giorni=Integer.parseInt(request.getParameter("days"));
    	  
    	
           
           
    	

    	UtenteDTO user=(UtenteDTO) request.getSession().getAttribute("user");
    	
    	ProdottoCarrelloDAO prodDAO= new ProdottoCarrelloDAO();
    	ProdottoCarrelloDTO prod;
        CarrelloDTO cart=(CarrelloDTO) request.getSession().getAttribute("cart");
        System.out.println(cart.getID_Carrello());

     
     // Verifica se l'utente è non registrato
        if (user == null) {
        	System.out.println("non sono utente");
                      // Controlla se il prodotto è già nel carrello
            boolean found = false;
            for (ProdottoCarrelloDTO x : cart.getCart()) {
                if (x.getId_prodotto() == id_prod && x.getGiorni() == giorni) {
                    // Se il prodotto è già presente, aggiorna la quantità o i giorni di noleggio
                    x.setQuantity(x.getQuantita() + quantity);
                    found = true;
                    break;
                }
            }

            // Se il prodotto non è stato trovato nel carrello, aggiungilo
            if (!found) {
            	ProdottoDAO dao=new ProdottoDAO();
                ProdottoDTO dto = dao.doRetrieveByKey(id_prod);
                
                // Crea un nuovo oggetto ProdottoCarrelloDTO e aggiungilo al carrello
                ProdottoCarrelloDTO prodCarrello = new ProdottoCarrelloDTO(cart.getID_Carrello(), dto.getID_Prod(), dto.getPrezzo(), dto.getPrezzoXDay(), quantity, giorni, dto.getImmagine(), dto.getNome());
                cart.addProduct(prodCarrello);
                return;
            }
            return;
        }

        
        
        
        else { //SONO UN UTENTE
        	
        	
        	//AGGIORNO QUANTITA' DEL PRODOTTO NEL DATABASE SOLO SE E' UN UTENTE REGISTRATO A SALVARE GLI OGGETTI
        	ProdottoDAO prodottoDAO= new ProdottoDAO();
              ProdottoDTO prodotto=prodottoDAO.doRetrieveByKey(id_prod);
               for(int i=0;i<quantity;i++)
               prodotto.decreaseQuantity();
               prodottoDAO.doUpdate(prodotto);
               
               
        if(cart.contains(id_prod)) { //se il carrello contiene già un elemento di quel tipo 
      
    		prod=prodDAO.doRetrieveByKey(cart.getID_Carrello(), id_prod);
    		
    		
    		//SE STO INSERENDO NEL CARRELLO PRODOTTO CON STESSA QUANTITA' DI QUELLO TROVATO ALLORA AGGIORNO QUANTITA'
    		if(prod.getGiorni()==giorni) {
    			System.out.println("Sto inserendo prodotto già presente in carrello"+ giorni);
    			for(int i=0;i<quantity; i++) {
    	    		cart.addProduct(prod);}
    		prodDAO.doUpdate(prod);}
    		
    		//SE E' LO STESSO PRODOTTO MA DIFFERNTE NUMERO DI GIORNI VUOL DIRE CHE LO STO AFFITTANDO E LO SALVO COME NUOVO PRODOTTO NEL CARRELLO
    		else {
    			System.out.println("Sto inserendo un prodotto già presente in carrello ma con giorni differenti");
    			ProdottoCarrelloDTO prodottoAffittato=new ProdottoCarrelloDTO(cart.getID_Carrello(), id_prod, prod.getPrezzo(), prod.getPrezzoXdays(), quantity, giorni, prod.getImage(), prod.getName());
    			prodDAO.doSave(prodottoAffittato);
    			cart.addProduct(prodottoAffittato);
        }}
        
      
 //se l'elemento non è già nel carrello devo ritrovare nel db il prezzo e prezzoxdays di esso e poi aggiungerlo al carrello e crearne una riga in ProdottoCarrello
        else {
        	System.out.println("NON contengo l'elemento");

        	prod=new ProdottoCarrelloDTO(cart.getID_Carrello(),prodotto.getID_Prod(),  prodotto.getPrezzo(), prodotto.getPrezzoXDay(),quantity, giorni, prodotto.getImmagine(), prodotto.getNome());
    		for(int i=0;i<quantity; i++) {
        	cart.addProduct(prod);}
        	prodDAO.doSave(prod);}
        
        try {
			response.sendRedirect(request.getContextPath() + "/ProductControl?action=mostraProdotti");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }

    	}
    
    

    
    
    private void updateCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_prod = Integer.parseInt(request.getParameter("codice_prod"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int giorni = Integer.parseInt(request.getParameter("days"));

        UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("utente");
        CarrelloDTO cart = carrelloDAO.doRetrieveById(user.getID());
        ProdottoCarrelloDAO prodDAO = new ProdottoCarrelloDAO();
        ProdottoCarrelloDTO prod;

        if (cart != null) {
            prod = prodDAO.doRetrieveByKey(cart.getID_Carrello(), id_prod);
            if (prod != null) {
                if (quantity == 0) {
                    cart.decreaseProduct(prod);
                    carrelloDAO.doUpdateCart(cart);
                    prodDAO.doDelete(prod);
                } else {
                    prod.setQuantity(quantity);
                    prod.setDays(giorni);
                    prodDAO.doUpdate(prod);
                    carrelloDAO.doUpdateCart(cart);
                }
        response.getWriter().write("Success");
    }
        }
    	
    }
}

