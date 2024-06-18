package model;

public class ProdottoOrdineDTO {
	private final int id_ordine;
	private final int id_prodotto;
	private final double prezzo;
	private final int giorni;
	private final double prezzoXdays;
	private final int quantity;
	
	



	public ProdottoOrdineDTO(int id_ordine, int id_prodotto, double prezzo, double prezzodays, int giorni, int quantity) {
		super();
		this.id_ordine = id_ordine;
		this.id_prodotto = id_prodotto;
		this.prezzo = prezzo;
		this.giorni = giorni;
		this.prezzoXdays = prezzodays;
		this.quantity = quantity;
	}
	
	
	
	
	public double getPrezzoXdays() {
		return prezzoXdays;
	}

	public int getId_ordine() {
		return id_ordine;
	}
	public int getId_prodotto() {
		return id_prodotto;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public int getGiorni() {
		return giorni;
	}




	public int getQuantity() {
		return quantity;
	}

}
