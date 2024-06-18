package model;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UtenteDAOInterfaccia {
	public boolean doSave (UtenteDTO user) throws SQLException;
	
	public UtenteDTO doRetrieveByKey (int id) throws SQLException;
	
	public boolean doDeleteByKey(int id) throws SQLException;
	
	public void doUpdate(UtenteDTO user) throws SQLException;
	
	
	public ArrayList<UtenteDTO> doRetrieveAll() throws SQLException;
	
	public boolean doRetrieveByEmail(String email);

}
