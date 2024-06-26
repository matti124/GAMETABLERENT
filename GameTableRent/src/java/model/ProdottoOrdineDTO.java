package model;

import java.util.Arrays;

public class ProdottoOrdineDTO {
	private final int id_ordine;
	private final int id_prodotto;
	private final double prezzo;
	private final int giorni;
	private final double prezzoXdays;
	private final int quantity;
	private byte[] image;
	private final String name;
	
	



	public ProdottoOrdineDTO(int id_ordine, int id_prodotto, double prezzo, double prezzodays, int giorni, int quantity, byte[]img, String name) {
		super();
		this.id_ordine = id_ordine;
		this.id_prodotto = id_prodotto;
		this.prezzo = prezzo;
		this.giorni = giorni;
		this.prezzoXdays = prezzodays;
		this.quantity = quantity;
		this.name = name ;
		this.setImage(img);
	}
	
	
	
	
	@Override
	public String toString() {
		return "ProdottoOrdineDTO [id_ordine=" + id_ordine + ", id_prodotto=" + id_prodotto + ", prezzo=" + prezzo
				+ ", giorni=" + giorni + ", prezzoXdays=" + prezzoXdays + ", quantity=" + quantity + ", image="
				+ Arrays.toString(image) + ", name=" + name + "]";
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




	public String getName() {
		return name;
	}




	public byte[] getImage() {
		return image;
	}




	public void setImage(byte[] image) {
		this.image = image;
	}

}
