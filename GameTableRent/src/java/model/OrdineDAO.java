package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OrdineDAO implements OrdineDAOInterfaccia {

    @Override
    public boolean doSave(OrdineDTO ordine) { //salvo un nuovo ordine
        String query = "INSERT INTO ordine (id_utente, data_ordine, prezzo) VALUES (?, CURRENT_TIMESTAMP(), ?)";
        
        try (Connection connection = DriverManagerConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ordine.getIdUtente());
            statement.setDouble(2, ordine.getTotalPrice());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento dell'ordine: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean doDeleteByKey(int id_ord) { //elimino un ordine in base all'id
        String query = "DELETE FROM ordine WHERE id_ordine = ?";
        
        try (Connection connection = DriverManagerConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_ord);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Errore durante la cancellazione dell'ordine con ID " + id_ord + ": " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean doUpdate(OrdineDTO ordine) { //aggiorno l'ordine in base all'id, il prezzo lo ricavo tramite funzione di OrdineDTO
        String query = "UPDATE ordine SET id_utente = ?, prezzo = ? WHERE id_ordine = ?";
        
        try (Connection connection = DriverManagerConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ordine.getIdUtente());
            statement.setDouble(2, ordine.getTotalPrice());
            statement.setInt(3, ordine.getId_Ordine());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'ordine con ID " + ordine.getId_Ordine() + ": " + e.getMessage());
            return false;
        }
    }

    @Override
    public OrdineDTO doRetrieveByKey(int id_ord) { //ritorno tutti i dati riguardanti un ordine (anche la lista dei prodotti acquistati)
        String query = "SELECT * FROM ordine WHERE id_ordine = ?";
        
        try (Connection connection = DriverManagerConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_ord);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id_utente = resultSet.getInt("id_utente");
                    Timestamp data_ordine = resultSet.getTimestamp("data_ordine");
                    ProdottoOrdineDAO prodotti=new ProdottoOrdineDAO();
                    ArrayList<ProdottoOrdineDTO> prods=prodotti.doRetrieveAll(id_ord);
                    return new OrdineDTO(id_ord, id_utente, data_ordine, prods);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dell'ordine con ID " + id_ord + ": " + e.getMessage());
        }
        return null;
    }
    
    
    public ArrayList<OrdineDTO> doRetrieveAllbyId(int id_utente){ //tutti gli ordini relativi ad un utente
    	String query="SELECT * FROM ORDINE WHERE ID_CARRELLO = ?";
    	ArrayList<OrdineDTO> ordini= new ArrayList<>();
    	 
    	try(Connection connection=DriverManagerConnectionPool.getConnection();
    			PreparedStatement ps= connection.prepareStatement(query);
    			){
    		ps.setInt(1, id_utente);
    		ResultSet resultSet=ps.executeQuery();
    		 while (resultSet.next()) {
                 int id_ordine = resultSet.getInt("id_ordine");
                 Timestamp data_ordine = resultSet.getTimestamp("data_ordine");
                 ProdottoOrdineDAO x=new ProdottoOrdineDAO();
                 ArrayList<ProdottoOrdineDTO> prods=x.doRetrieveAll(id_ordine);
                 OrdineDTO ordine = new OrdineDTO(id_ordine, id_utente, data_ordine, prods);
                 ordini.add(ordine);
             }
    	} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Errore durante recupero degli ordini per data: "+ e.getMessage());
		}
		return ordini;
    	
    }

    @Override
    public ArrayList<OrdineDTO> doRetrieveAll() { //ritorno tutti gli ordini presenti nel db con i relativi prodotti acquistati
        String query = "SELECT * FROM ordine";
        ArrayList<OrdineDTO> ordini = new ArrayList<>();
        
        try (Connection connection = DriverManagerConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id_ordine = resultSet.getInt("id_ordine");
                int id_utente = resultSet.getInt("id_carrello");
                Timestamp data_ordine = resultSet.getTimestamp("data_ordine");
                ProdottoOrdineDAO prodotti=new ProdottoOrdineDAO();
                ArrayList<ProdottoOrdineDTO> prods=prodotti.doRetrieveAll(id_ordine);
                OrdineDTO ordine = new OrdineDTO(id_ordine, id_utente, data_ordine, prods);
                ordini.add(ordine);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero degli ordini: " + e.getMessage());
        }
        return ordini;
    }
    
    public ArrayList<OrdineDTO> doRetrieveByDate(Timestamp start, Timestamp end){ //ritorno tutti gli ordini effettuati nell'arco di un preciso lasso di tempo
    	ArrayList<OrdineDTO> ordini= new ArrayList<>();
    	String query="SELECT * FROM ORDINE WHERE DATA_ORDINE BETWEEN (? AND ?);";
    	try(Connection connection=DriverManagerConnectionPool.getConnection();
    			PreparedStatement ps= connection.prepareStatement(query);
    			){
    		ps.setTimestamp(1, start);
    		ps.setTimestamp(2, end);
    		ResultSet resultSet=ps.executeQuery();
    		 while (resultSet.next()) {
                 int id_ordine = resultSet.getInt("id_ordine");
                 int id_utente = resultSet.getInt("id_utente");
                 Timestamp data_ordine = resultSet.getTimestamp("data_ordine");
                 ProdottoOrdineDAO x=new ProdottoOrdineDAO();
                 ArrayList<ProdottoOrdineDTO> prods=x.doRetrieveAll(id_ordine);
                 OrdineDTO ordine = new OrdineDTO(id_ordine, id_utente, data_ordine, prods);
                 ordini.add(ordine);
             }
    	} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Errore durante recupero degli ordini per data: "+ e.getMessage());
		}
		return ordini;
    	
    }

	
    
    
}
