package model;

public class ProdottoDTO {
	private final int ID_Prod;
	private  String nome;
	private  String descrizione;
	private  Double Prezzo;
	private  Double PrezzoXDay;
	private  int IN_CAT;
	private  int quantity;
	private byte[] immagine;
	
	
	public ProdottoDTO(int iD_Prod, String nome, String descrizione, Double prezzo, Double prezzoXDay, int quantity,
			int incat, byte[] img) {
		super();
		this.ID_Prod = iD_Prod;
		this.nome = nome;
		this.descrizione = descrizione;
		this.Prezzo = prezzo;
		this.PrezzoXDay = prezzoXDay;
		this.quantity = quantity;
		this.IN_CAT = incat;

		this.immagine=img;
	}


	public int getID_Prod() {
		return ID_Prod;
	}


	public String getNome() {
		return nome;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public Double getPrezzo() {
		return Prezzo;
	}


	public Double getPrezzoXDay() {
		return PrezzoXDay;
	}


	public int getIN_CAT() {
		return IN_CAT;
	}


	public int getQuantity() {
		return quantity;
	}
	
	public byte[] getImmagine() {
		return immagine;
	}


	public void setIN_CAT(int x) {
		this.IN_CAT=x; //Modificando questo parametro l'admin può eliminare o inserire prodotti nel catalogo
	}
	
	
	public void decreaseQuantity() {
		System.out.println("Diminuisco la quantità in magazzino da "+ this.quantity);
		this.quantity--;
		System.out.println("a "+ this.quantity);
		if(this.quantity==0)
			this.setIN_CAT(0); //se sono finite le scorte di quel prodotto lo setto fuori dal catalogo
	}

	public void increaseQuantity() {
		this.quantity++;
	}
	
	@Override
	public boolean equals(Object obj) {
		ProdottoDTO x=(ProdottoDTO) obj;
		return(this.ID_Prod==x.ID_Prod);
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public void setPrezzo(Double prezzo) {
		Prezzo = prezzo;
	}


	public void setPrezzoXDay(Double prezzoXDay) {
		PrezzoXDay = prezzoXDay;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public void setImmagine(byte[] immagine) {
		this.immagine = immagine;
	}
	
}
