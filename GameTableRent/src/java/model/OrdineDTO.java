package model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class OrdineDTO {
	
	private final int id_Ordine;
	private final int id_Carrello;
	private final Timestamp data;
	private final ArrayList<ProdottoOrdineDTO> prodotti;
	
	
	
	public OrdineDTO(int id_Ordine, int id_Carrello, Timestamp data_ordine,  ArrayList<ProdottoOrdineDTO> prods) {
		super();
		this.id_Ordine = id_Ordine;
		this.id_Carrello = id_Carrello;
		this.data = data_ordine;
		this.prodotti=prods;
	}


	
	public int getId_Ordine() {
		return id_Ordine;
	}



	public int getId_Carrello() {
		return id_Carrello;
	}



	public Timestamp getData() {
		return data;
	}





	public ArrayList<ProdottoOrdineDTO> getProdotti(){
		return this.prodotti;
	}
	
	public double getTotalPrice() {
		double tot=0;
		for(ProdottoOrdineDTO x: this.prodotti) {
			if(x.getGiorni()>0)
				tot+=x.getPrezzoXdays()*x.getQuantity()*x.getGiorni();
			else 
				tot+=x.getPrezzo()*x.getQuantity();
		}
		return tot;
	}
}
