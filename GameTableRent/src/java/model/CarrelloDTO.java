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
	//costruttore per nuovo carrello senza prodotti associati
	public CarrelloDTO(int iD_Carrello,  int id_ut) {
		super();
		this.ID_Carrello = iD_Carrello;
		this.Cart = new ArrayList<>();
		this.ID_Utente=id_ut;
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


	
	
	public ProdottoCarrelloDTO retrieveById(int id_c , int id_p) {	//in caso un elemento già fosse nel carrello lo ritorniamo
		for(ProdottoCarrelloDTO x : Cart) {
			if(x.getId_prodotto()==id_p && x.getId_carrello()==id_c)
				return x;
		}
		return null;
	}
	
	
	public void addProduct(ProdottoCarrelloDTO x) { //se un elemento non è nel carrello lo aggiungo oppure ne aumento la quantità
		if(this.retrieveById(x.getId_prodotto(), x.getId_carrello())==null)
			this.Cart.add(x);
		else x.addQuantity();
	}
	
	
	
	public void decreaseProduct(ProdottoCarrelloDTO x) {
		ProdottoCarrelloDTO prod=this.retrieveById(x.getId_prodotto(), x.getId_carrello());
		if(prod!=null)
			if(prod.getQuantita()==1) 
				this.Cart.remove(prod);
			else prod.decreaseQuantity();
	}
	
	public double getTotalPrice() {
		double tot=0;
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
	
	
	public ArrayList<ProdottoOrdineDTO> CheckOut(int id_ord){
		  		
		 ArrayList<ProdottoOrdineDTO> prodottiOut=new ArrayList<>();
		 
		 Iterator<ProdottoCarrelloDTO> iterator = this.Cart.iterator();
		 while (iterator.hasNext()) {
		     ProdottoCarrelloDTO x = iterator.next();
		     ProdottoOrdineDTO prodOut = new ProdottoOrdineDTO(id_ord, x.getId_prodotto(), x.getPrezzo(), x.getPrezzoXdays(), x.getGiorni(), x.getQuantita());
		     prodottiOut.add(prodOut);
		     iterator.remove(); 
		 }

		 return prodottiOut;
			 
			 
			
		}
	}
	


