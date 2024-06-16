package model;

public class UtenteDTO {
	private final int ID;
	private final String Nome;
	private final String Cognome;
	private final String Indirizzo;
	private final String email;
	private final String psw;
	private final int isAdmin;
	
	public UtenteDTO(int iD, String nome, String cognome, String indirizzo, String email, String psw, int isAdmin) {
		super();
		this.ID = iD;
		this.Nome = nome;
		this.Cognome = cognome;
		this.Indirizzo = indirizzo;
		this.email = email;
		this.psw = psw;
		this.isAdmin = isAdmin;
	}

		
	
	
	
	public int getID() {
		return ID;
	}
	public String getNome() {
		return Nome;
	}
	public String getCognome() {
		return Cognome;
	}
	public String getIndirizzo() {
		return Indirizzo;
	}
	public String getEmail() {
		return email;
	}
	public String getPsw() {
		return psw;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
}
	