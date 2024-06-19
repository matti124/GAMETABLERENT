package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdottoCarrelloDAO implements ProdGenericDAOInterfaccia<ProdottoCarrelloDTO> {

   

    @Override
    public boolean doSave(ProdottoCarrelloDTO prod) {
        String query = "INSERT INTO PRODOTTI_CARRELLO (ID_CARRELLO, ID_PRODOTTO, QUANTITY, GIORNI) VALUES (?, ?, ?, ?)";
        
        try (	Connection connection=DriverManagerConnectionPool.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, prod.getId_carrello());
            statement.setInt(2, prod.getId_prodotto());
            statement.setInt(3, prod.getQuantita());
            statement.setInt(4, prod.getGiorni());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento del prodotto nel carrello: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean doDelete(ProdottoCarrelloDTO prod) {
        String query = "DELETE FROM PRODOTTI_CARRELLO WHERE ID_CARRELLO = ? AND ID_PRODOTTO = ?";
        
        try (	Connection connection=DriverManagerConnectionPool.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setInt(1, prod.getId_carrello());
            statement.setInt(2, prod.getId_prodotto());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Errore durante la cancellazione del prodotto dal carrello: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean doUpdate(ProdottoCarrelloDTO prod) {
        String query = "UPDATE PRODOTTI_CARRELLO SET QUANTITY = ?, GIORNI = ? WHERE ID_CARRELLO = ? AND ID_PRODOTTO = ?";
        
        try (	Connection connection=DriverManagerConnectionPool.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setInt(1, prod.getQuantita());
            statement.setInt(2, prod.getGiorni());
            statement.setInt(3, prod.getId_carrello());
            statement.setInt(4, prod.getId_prodotto());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento del prodotto nel carrello: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ProdottoCarrelloDTO doRetrieveByKey(int id_cart, int id_prod) {
        String query = "SELECT * FROM PRODOTTI_CARRELLO WHERE ID_CARRELLO = ? AND ID_PRODOTTO = ?";
        
        try (	Connection connection=DriverManagerConnectionPool.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setInt(1, id_cart);
            statement.setInt(2, id_prod);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int quantity = resultSet.getInt("QUANTITY");
                    int giorni = resultSet.getInt("GIORNI");
                    double prezzo=resultSet.getDouble("PREZZO");
                    double prezzoxdays=resultSet.getDouble("PREZZOXDAYS");

                    return new ProdottoCarrelloDTO(id_cart, id_prod, prezzo, prezzoxdays, quantity, giorni);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del prodotto nel carrello: " + e.getMessage());
        }
        return null;
    }

    
    
    public ArrayList<ProdottoCarrelloDTO> doRetrieveAll(int id_cart) { //ritorna tutti i prodotti in quel carrello
        String query = "SELECT * FROM PRODOTTI_CARRELLO WHERE ID_CARRELLO = ?";
        ArrayList<ProdottoCarrelloDTO> prodotti = new ArrayList<>();
        
        try (	Connection connection=DriverManagerConnectionPool.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setInt(1, id_cart);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id_prodotto = resultSet.getInt("ID_PRODOTTO");
                    int quantity = resultSet.getInt("QUANTITY");
                    int giorni = resultSet.getInt("GIORNI");
                    double prezzo=resultSet.getDouble("PREZZO");
                    double prezzoxdays=resultSet.getDouble("PREZZOXDAYS");

                    ProdottoCarrelloDTO prodotto = new ProdottoCarrelloDTO(id_cart, id_prodotto, prezzo, prezzoxdays, quantity, giorni);
                    prodotti.add(prodotto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei prodotti nel carrello: " + e.getMessage());
        }
        return prodotti;
    }

    public ArrayList<ProdottoCarrelloDTO> doRetrieveByProd(int id_prod) { //ritorna tutti i carrelli che presentano quel prodotto in carrello
        String query = "SELECT * FROM PRODOTTI_CARRELLO WHERE ID_PRODOTTO = ?";
        ArrayList<ProdottoCarrelloDTO> carrelli = new ArrayList<>();
        
        try (	Connection connection=DriverManagerConnectionPool.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setInt(1, id_prod);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id_cart = resultSet.getInt("ID_CARRELLO");
                    int quantity = resultSet.getInt("QUANTITY");
                    int giorni = resultSet.getInt("GIORNI");
                    double prezzo=resultSet.getDouble("PREZZO");
                    double prezzoxdays=resultSet.getDouble("PREZZOXDAYS");

                    ProdottoCarrelloDTO prodotto = new ProdottoCarrelloDTO(id_cart, id_prod, prezzo, prezzoxdays, quantity, giorni);
                    carrelli.add(prodotto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei carrelli per il prodotto: " + e.getMessage());
        }
        return carrelli;
    }

	
}
