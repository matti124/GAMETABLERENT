package model;

public class ProdottoCarrelloDTO {
	private final int id_carrello;
	private  int id_prodotto;
	private  int quantita;
	private  int giorni;
	private double prezzo;
	private double prezzoXdays;
	private byte[] image;
	private final String name;
	
	
	public ProdottoCarrelloDTO(int id_carrello, int id_prodotto, double prezzo,
			double prezzoXdays, int quantita, int giorni, byte[] bs, String nome ) {

		super();
		this.id_carrello = id_carrello;
		this.id_prodotto = id_prodotto;
		this.quantita = quantita;
		this.giorni = giorni;
		this.prezzo = prezzo;
		this.prezzoXdays = prezzoXdays;
		this.setImage(bs);
		this.name=nome;
		
	}


	
	
	@Override
	public String toString() {
		return "ProdottoCarrelloDTO [id_carrello=" + id_carrello + ", id_prodotto=" + id_prodotto + ", quantita="
				+ quantita + ", giorni=" + giorni + ", prezzo=" + prezzo + ", prezzoXdays=" + prezzoXdays + "]";
	}




	public double getPrezzo() {
		return prezzo;
	}




	public double getPrezzoXdays() {
		return prezzoXdays;
	}




	public int getId_carrello() {
		return id_carrello;
	}
	
	public int getId_prodotto() {
		return id_prodotto;
	}
	
	public int getQuantita() {
		return quantita;
	}
	
	public int getGiorni() {
		return giorni;
	}
	

	public void addQuantity() {
		this.quantita++;
	}
	public void decreaseQuantity() {
		System.out.println("Diminuisco quantità prodotto carrello da "+ this.quantita+ "ad ");
		this.quantita--;
		System.out.println(this.quantita);
	}
	
	public void setQuantity(int x) {
		this.quantita=x;
	}


	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    ProdottoCarrelloDTO that = (ProdottoCarrelloDTO) obj;
	    return id_prodotto == that.id_prodotto &&
	           id_carrello == that.id_carrello &&
	           giorni == that.giorni;
	}




	public void setDays(int giorni) {
		this.giorni=giorni;
	}




	public byte[] getImage() {
		return image;
	}




	public void setImage(byte[] image) {
		this.image = image;
	}




	public String getName() {
		return name;
	}
	
	
	
}
