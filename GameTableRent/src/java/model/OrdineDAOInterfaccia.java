package model;

import java.util.ArrayList;

public interface OrdineDAOInterfaccia {
	
	public int doSave(OrdineDTO x);
	
	public boolean doDeleteByKey(int id_ord);
	
	public boolean doUpdate(OrdineDTO x);
	
	public OrdineDTO doRetrieveByKey(int id_ord);
	
	public ArrayList<OrdineDTO> doRetrieveAll();
	

}
