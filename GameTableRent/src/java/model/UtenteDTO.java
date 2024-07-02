package model;

public class UtenteDTO {
	private final int ID;
	private  String Nome;
	private  String Cognome;
	private  String Indirizzo;
	private  String email;
	private  String psw;
	private  int isAdmin=0;
	
	public UtenteDTO(int iD, String nome, String cognome, String indirizzo, String email, String psw, int admin) {
		super();
		this.ID = iD;
		this.Nome = nome;
		this.Cognome = cognome;
		this.Indirizzo = indirizzo;
		this.email = email;
		this.psw = psw;
		isAdmin=admin;
	}

		
	
	
	
	@Override
	public String toString() {
		return "UtenteDTO [ID=" + ID + ", Nome=" + Nome + ", Cognome=" + Cognome + ", Indirizzo=" + Indirizzo
				+ ", email=" + email + ", psw=" + psw + ", isAdmin=" + isAdmin + "]";
	}





	public void setNome(String nome) {
		Nome = nome;
	}





	public void setCognome(String cognome) {
		Cognome = cognome;
	}





	public void setIndirizzo(String indirizzo) {
		Indirizzo = indirizzo;
	}





	public void setEmail(String email) {
		this.email = email;
	}





	public void setPsw(String psw) {
		this.psw = psw;
	}





	public void setIsAdmin(int isAdmin) {
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
	