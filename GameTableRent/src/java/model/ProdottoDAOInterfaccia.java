package model;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProdottoDAOInterfaccia {
	
	public ProdottoDTO doRetrieveByKey(int id_prod);
	
	public boolean doSave (ProdottoDTO prod) throws SQLException;
	
	public boolean doDeleteByKey (int id) throws SQLException;
	
	public ArrayList<ProdottoDTO> doRetrieveAll();
	
	
	public boolean doUpdate(ProdottoDTO prodotto);

}
