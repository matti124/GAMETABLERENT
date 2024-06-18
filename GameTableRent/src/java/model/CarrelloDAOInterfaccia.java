package model;

import java.util.ArrayList;

public interface CarrelloDAOInterfaccia {
	
	public boolean doSave(CarrelloDTO cart);
	
	public boolean doFreeSpace(int id_cart); //libera spazio per un carrello
	
	public boolean doDeleteByKey(int id_cart);
	
	public boolean doUpdateCart(CarrelloDTO cart); //prende un carrello aggiornato e lo aggiorna nel DB
	
	public CarrelloDTO doRetrieveById(int id_cart);
	
	public ArrayList<CarrelloDTO> doRetrieveAll();

}
