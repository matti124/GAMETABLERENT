package model;

import java.sql.Date;

public class OrdineDTO {
	
	private final int id_Ordine;
	private final int id_Carrello;
	private final Date data;
	private final double prezzo;
	
	
	
	public OrdineDTO(int id_Ordine, int id_Carrello, Date data, double prezzo) {
		super();
		this.id_Ordine = id_Ordine;
		this.id_Carrello = id_Carrello;
		this.data = data;
		this.prezzo = prezzo;
	}



	public int getId_Ordine() {
		return id_Ordine;
	}



	public int getId_Carrello() {
		return id_Carrello;
	}



	public Date getData() {
		return data;
	}



	public double getPrezzo() {
		return prezzo;
	}

}
