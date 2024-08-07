package model;

import java.util.ArrayList;
import java.util.Iterator;

public class CarrelloDTO {
	private final int ID_Carrello;
	private ArrayList <ProdottoCarrelloDTO> Cart;
	private int ID_Utente;
	
	//costruttore che presenta anche prodotti associati
	public CarrelloDTO(int iD_Carrello, ArrayList<ProdottoCarrelloDTO> prodottiInCart, int id_ut) {
		super();
		this.ID_Carrello = iD_Carrello;
		this.Cart = prodottiInCart;
		this.ID_Utente=id_ut;
	}
	//costruttore per nuovo carrello senza prodotti associati (Carrello per guests)
	public CarrelloDTO() {
		super();
		this.ID_Carrello = 0;
		this.Cart = new ArrayList<>();
	
	}

	public int getID_Utente() {
		return ID_Utente;
	}




	public ArrayList<ProdottoCarrelloDTO> getCart() {
		return Cart;
	}


	public int getID_Carrello() {
		return ID_Carrello;
	}


	
	
	public ProdottoCarrelloDTO retrieveById(int id_p, int giorni) {	//in caso un elemento già fosse nel carrello lo ritorniamo
		for(ProdottoCarrelloDTO x : this.Cart) {
			if(x.getId_prodotto()==id_p&& x.getGiorni()==giorni)
				return x;
		}
		return null;
	}
	
	
	public void addProduct(ProdottoCarrelloDTO x) { //se un elemento non è nel carrello lo aggiungo oppure ne aumento la quantità
	    if (x != null) {
	    	ProdottoCarrelloDTO prod=this.retrieveById(x.getId_prodotto(), x.getGiorni());
	    	if(prod==null) {
	    		System.out.println("PRODOTTO NON ERA IN CARRELLO, LO AGGIUNGO");
	    		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+x +"\n\n\n");
	    		x.addQuantity();
				this.Cart.add(x);
				
				}
			else {
	    		System.out.println("PRODOTTO  ERA IN CARRELLO, LO AUMENTO");
				this.Cart.remove(prod);
				prod.addQuantity();
				this.Cart.add(prod);
			}
	    } else {
	        // Gestione appropriata nel caso prod sia null
	        System.out.println("Tentativo di aggiungere un prodotto null al carrello.");
	    }
		
	    System.out.println("HO AGGIUNTO "+ x+ "AL CARRELLO DELLA SESSIONE");
		
	}
	
	
	
	public void decreaseProduct(ProdottoCarrelloDTO x) {
		ProdottoCarrelloDTO prod=this.retrieveById(x.getId_prodotto(), x.getGiorni());
		if(prod!=null)
			if(prod.getQuantita()==0) 
				this.Cart.remove(prod);
			else prod.decreaseQuantity();
	}
	
	public float GetTotalPrice() {
		float tot=0;
		for(ProdottoCarrelloDTO x: this.Cart) {
			if(x.getGiorni()>0) {
				tot+=x.getPrezzoXdays()*x.getQuantita()*x.getGiorni();
			}
			else tot+=x.getPrezzo()*x.getQuantita();
		}
		return tot;
	}
	
	
	public boolean contains(int id) {
		for(ProdottoCarrelloDTO x: this.Cart) {
			if(x.getId_prodotto()==id)
				return true;
		}
		return false;
	}
	
	public ProdottoCarrelloDTO retrieve (ProdottoCarrelloDTO x) {
		for(ProdottoCarrelloDTO prod: this.Cart) {
			if(prod.equals(x))
				return prod;
		}
		return null;
		
		
	}
	
	public ArrayList<ProdottoOrdineDTO> CheckOut(int id_ord){
		  		
		 ArrayList<ProdottoOrdineDTO> prodottiOut=new ArrayList<>();
		 
		 Iterator<ProdottoCarrelloDTO> iterator = this.Cart.iterator();
		 while (iterator.hasNext()) {
		     ProdottoCarrelloDTO x = iterator.next();
		     ProdottoOrdineDTO prodOut = new ProdottoOrdineDTO(id_ord, x.getId_prodotto(), x.getPrezzo(), x.getPrezzoXdays(), x.getGiorni(), x.getQuantita(), x.getImage(), x.getName());

		     prodottiOut.add(prodOut);
		     iterator.remove(); 
		 }

		 return prodottiOut;
			 
			 
			
		}
	
	public void delete(ProdottoCarrelloDTO x) {
		ProdottoCarrelloDTO delete=this.retrieveById(x.getId_prodotto(), x.getGiorni());
		this.Cart.remove(delete);
	}
	}
	


