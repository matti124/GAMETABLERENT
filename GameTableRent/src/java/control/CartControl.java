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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		  default:{
              response.sendError(HttpServletResponse.SC_BAD_REQUEST);
              return;}
		  }
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		switch (action) {

		case "addToCart":
			this.addToCart(request, response);
			break;
		case "UpdateCart":
			this.updateCart(request, response);
			break;

		case "UpdateDaysCart":
			this.UpdateDaysCart(request, response);
			break;
			
		case "removeFromCart":
			this.removeProductFromCart(request, response);;

		}

	}

	private void viewCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { // azione che avviene quando si preme sul tasto per andare al
													// carrello

		UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("utente");
		CarrelloDTO cart = carrelloDAO.doRetrieveById(user.getID());

		if (cart != null) {
			request.setAttribute("cart", cart);
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		} else {
			// Handle case where cart is not found
			response.getWriter().println("Cart not found.");
		}
	}

	private void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("utente");

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

	private void deleteCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id_exUtente = (int) request.getAttribute("id_exUt");
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

		int id_prod = Integer.parseInt(request.getParameter("codice_prod"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		int giorni = Integer.parseInt(request.getParameter("days"));

		System.out.println("Quantità passata: " + quantity);

		UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("user");

		ProdottoCarrelloDAO prodDAO = new ProdottoCarrelloDAO();
		ProdottoCarrelloDTO prod;
		CarrelloDTO cart = (CarrelloDTO) request.getSession().getAttribute("cart");
		System.out.println(cart.getID_Carrello());

		// Verifica se l'utente è non registrato
		if (user == null) {
			System.out.println("non sono utente");
			// Controlla se il prodotto è già nel carrello
			boolean found = false;
			for (ProdottoCarrelloDTO x : cart.getCart()) {
				if (x.getId_prodotto() == id_prod && x.getGiorni() == giorni) {
					System.out.println("Contengo già il prodotto con stessi giorni"); // Se il prodotto è già presente,
																						// aggiorna la quantità o i
																						// giorni di noleggio
					x.setQuantity(x.getQuantita() + quantity);
					found = true;
					break;
				}
			}

			// Se il prodotto non è stato trovato nel carrello, aggiungilo
			if (!found) {
				ProdottoDAO dao = new ProdottoDAO();
				ProdottoDTO dto = dao.doRetrieveByKey(id_prod);

				// Crea un nuovo oggetto ProdottoCarrelloDTO e aggiungilo al carrello
				System.out.println("Non Contengo già il prodotto con stessi giorni"); // Se il prodotto è già presente,
																						// aggiorna la quantità o i
																						// giorni di noleggio

				ProdottoCarrelloDTO prodCarrello = new ProdottoCarrelloDTO(cart.getID_Carrello(), dto.getID_Prod(),
						dto.getPrezzo(), dto.getPrezzoXDay(), 0, giorni, dto.getImmagine(), dto.getNome());
				for(int i=0;i<quantity;i++)
				cart.addProduct(prodCarrello);
				return;
			}
			return;
		}

		else { // SONO UN UTENTE

			// AGGIORNO QUANTITA' DEL PRODOTTO NEL DATABASE SOLO SE E' UN UTENTE REGISTRATO
			// A SALVARE GLI OGGETTI
			ProdottoDAO prodottoDAO = new ProdottoDAO();
			ProdottoDTO prodotto = prodottoDAO.doRetrieveByKey(id_prod);
			for (int i = 0; i < quantity; i++)
				prodotto.decreaseQuantity();
			prodottoDAO.doUpdate(prodotto);

			if (cart.contains(id_prod)) { // se il carrello contiene già un elemento di quel tipo

				prod = prodDAO.doRetrieveByKey(cart.getID_Carrello(), id_prod);

				// SE STO INSERENDO NEL CARRELLO PRODOTTO CON STESSA QUANTITA' DI QUELLO TROVATO
				// ALLORA AGGIORNO QUANTITA'
				if (prod.getGiorni() == giorni) {
					System.out.println("Sto inserendo prodotto già presente in carrello" + giorni);
					for (int i = 0; i < quantity; i++) {
						cart.addProduct(prod);
					}
					prodDAO.doUpdate(prod);
				}

				// SE E' LO STESSO PRODOTTO MA DIFFERNTE NUMERO DI GIORNI VUOL DIRE CHE LO STO
				// AFFITTANDO E LO SALVO COME NUOVO PRODOTTO NEL CARRELLO
				else {
					System.out.println("Sto inserendo un prodotto già presente in carrello ma con giorni differenti");
					ProdottoCarrelloDTO prodottoAffittato = new ProdottoCarrelloDTO(cart.getID_Carrello(), id_prod,
							prod.getPrezzo(), prod.getPrezzoXdays(), quantity-1, giorni, prod.getImage(), prod.getName()); //soluzione banale per problema con consistenza di dati in frontend
					prodDAO.doSave(prodottoAffittato);
					cart.addProduct(prodottoAffittato);
				}
			}

			// se l'elemento non è già nel carrello devo ritrovare nel db il prezzo e
			// prezzoxdays di esso e poi aggiungerlo al carrello e crearne una riga in
			// ProdottoCarrello
			else {
				System.out.println("NON contengo l'elemento");

				prod = new ProdottoCarrelloDTO(cart.getID_Carrello(), prodotto.getID_Prod(), prodotto.getPrezzo(),
						prodotto.getPrezzoXDay(), 0, giorni, prodotto.getImmagine(), prodotto.getNome());
				for (int i = 0; i < quantity; i++) {
					System.out.println("Aumento quantittà prodotto in carrello: " + i);
					cart.addProduct(prod);
				}
				prodDAO.doSave(prod);
			}

			
			request.getSession().setAttribute("cart", cart);
			try {
				response.sendRedirect(request.getContextPath() + "/ProductControl?action=mostraProdotti");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/*
	 * QUANDO AGGIORNIAMO IL CARRELLO CON UNA NUOVA QUANTITA' DI UN PRODOTTO
	 * DOBBIAMO ASSICURARCI DI AGGIORNARE ANCHE LA QUANTITA' DEL PRODOTTO IN
	 * GENERALE OVVERO SE AUMENTO I PEZZI DI UN PRODOTTO NEL CARRELLO ALLORA LA
	 * QUANTITA' NEL MAGAZZINO DIMINUISCE DI QUEI PEZZI
	 * 
	 * 
	 * 
	 */

	private void updateCart(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    int id_prod = Integer.parseInt(request.getParameter("codice_prod"));
	    int quantity = Integer.parseInt(request.getParameter("quantity"));
	    int days = Integer.parseInt(request.getParameter("days"));
	    String sign = request.getParameter("sign");
	    ProdottoDAO dao = new ProdottoDAO();
	    ProdottoDTO GeneralProd = dao.doRetrieveByKey(id_prod);

	    UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("user");
	    if (user == null) {
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write("{\"error\": \"User not logged in\"}");
	        return;
	    }
	    CarrelloDTO cart = carrelloDAO.doRetrieveById(user.getID());
	    if (cart == null) {
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write("{\"error\": \"Cart not found\"}");
	        return;
	    }

	    ProdottoCarrelloDAO prodDAO = new ProdottoCarrelloDAO();
	    System.out.println("Sto cercando: prodotto con id prodotto: " + id_prod +  "e giorni: "+ days );
	    ProdottoCarrelloDTO prod = prodDAO.doRetrieveByKeyAndDays(cart.getID_Carrello(), id_prod, days);
	    if (prod == null) {
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write("{\"error\": \"Product not found in cart\"}");
	        return;
	    }

	    try {
	        // Se raggiungiamo quantità pari a 0 eliminiamo il prodotto dal carrello,
	        // prima di tutto lo eliminiamo dal carrello della sessione,
	        // poi aggiorniamo quello del db, per poi aumentare il prodotto nel magazzino che abbiamo appena tolto
	        if (quantity == 0) {
	            // Eliminiamo dal carrello della sessione
	            cart.delete(prod);
	            // Aggiorniamo il carrello della sessione nel DB
	            carrelloDAO.doUpdateCart(cart);
	            // Incrementiamo di 1 la quantità di quel prodotto nel DB
	            GeneralProd.increaseQuantity();
	            // Lo salviamo nel DB
	            dao.doUpdate(GeneralProd);
	            // Cancelliamo dai prodotti nel carrello del DB quel prodotto
	            prodDAO.doDelete(prod);
	            // Aggiorniamo il carrello nella sessione
	            request.getSession().setAttribute("cart", cart);
	        } else {
	            if (sign.equalsIgnoreCase("-")) {
	                // Incremento i prodotti nel magazzino
	                GeneralProd.increaseQuantity();
	                // Diminuisco il prodotto nel carrello della sessione
	                cart.decreaseProduct(prod);
	            } else {
	                // Decremento i prodotti nel magazzino
	                GeneralProd.decreaseQuantity();
	                // Aggiungo il prodotto al carrello della sessione
	                cart.addProduct(prod);
	            }
	            // Aggiorno il prodotto nel carrello nel DB
	            prodDAO.doUpdate(cart.retrieve(prod));
	            // Aggiorno il prodotto nel magazzino del DB
	            dao.doUpdate(GeneralProd);
	            // Aggiorno il carrello nella sessione
	            request.getSession().setAttribute("cart", cart);
	        }

	        // Risposta JSON con il prezzo totale aggiornato
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write("{\"totalPrice\": " + cart.GetTotalPrice() + "}");
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
	    }
	}
	
	
	
	private void UpdateDaysCart(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    int id_prod = Integer.parseInt(request.getParameter("codice_prod"));
	    int newDays = Integer.parseInt(request.getParameter("days"));
	    int oldDays = Integer.parseInt(request.getParameter("oldDays"));

	    CarrelloDTO cart = (CarrelloDTO) request.getSession().getAttribute("cart");
	    ProdottoCarrelloDAO prodDAO = new ProdottoCarrelloDAO();
	    ProdottoCarrelloDTO prod = prodDAO.doRetrieveByKeyAndDays(cart.getID_Carrello(), id_prod, oldDays);
	    System.out.println("PRODOTTO PRIMA DI AGGIORNAMENTO GIORNI:"+ prod);

	    if (prod == null) {
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write("{\"error\": \"Product not found in cart\"}");
	        return;
	    }

	    // Check if a product with the new days already exists in the cart
	    ProdottoCarrelloDTO existingProdWithNewDays = prodDAO.doRetrieveByKeyAndDays(cart.getID_Carrello(), id_prod, newDays);
	    if (existingProdWithNewDays != null) {
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write("{\"error\": \"Product with the new days already exists in cart\"}");
	        return;
	    }
	    
	    // Update cart in session
	    ProdottoCarrelloDTO prodSes=cart.retrieve(prod);
	    cart.getCart().remove(prod);
	    prodSes.setDays(newDays);
	    cart.getCart().add(prodSes);
	    
	    prodDAO.doUpdateDays(prod, newDays);

	    System.out.println("PRODOTTO DOPO DI AGGIORNAMENTO GIORNI:"+ cart.retrieve(prodSes));


	

	    request.getSession().setAttribute("cart", cart);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write("{\"totalPrice\": " + cart.GetTotalPrice() + "}");
	}


	
	
	
	
	//DA IMPLEMENTARE PER NON UTENTI SOLO NELLA SESSIONE, PER ALTRI IN DB ANCHE

    private void removeProductFromCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id_prod = Integer.parseInt(request.getParameter("codice_prod"));
        int giorni_prod=Integer.parseInt(request.getParameter("giorni"));
        UtenteDTO user = (UtenteDTO) request.getSession().getAttribute("user");
        CarrelloDTO cart = (CarrelloDTO) request.getSession().getAttribute("cart");

        if (cart != null) {
            if (user != null) {
                // Utente registrato
                ProdottoCarrelloDAO prodDAO = new ProdottoCarrelloDAO();
                ProdottoCarrelloDTO prod = prodDAO.doRetrieveByKeyAndDays(cart.getID_Carrello(), id_prod, giorni_prod);
                ProdottoDAO daoMag=new ProdottoDAO();
                ProdottoDTO prodMag=daoMag.doRetrieveByKey(id_prod);

                if (prod != null) {
                    cart.delete(prod);
                    carrelloDAO.doUpdateCart(cart);
                    prodDAO.doDelete(prod);
                    prodMag.increaseQuantity();
                    daoMag.doUpdate(prodMag);
                } else {
                    response.getWriter().write("Product not found in cart.");
                }
            } else {
                // Utente non registrato
            	System.out.println("Utente non registrato rimuove prodotto da carrello");
                ProdottoCarrelloDTO prodToRemove = null;
                for (ProdottoCarrelloDTO p : cart.getCart()) {
                    if (p.getId_prodotto() == id_prod&& p.getGiorni()==giorni_prod) {
                    	System.out.println("Trovato prodotto da rimuovere");
                        prodToRemove = p;
                        break;
                    }
                }

                if (prodToRemove != null) {
                    cart.delete(prodToRemove);
                    request.getSession().setAttribute("cart", cart);
                } else {
                    response.getWriter().write("Product not found in cart.");
                }
            }
        } else {
            response.getWriter().write("Cart not found.");
   }

		request.getSession().setAttribute("cart", cart);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write("{\"totalPrice\": " + cart.GetTotalPrice() + "}");

    }
    }