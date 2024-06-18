package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarrelloDAO implements CarrelloDAOInterfaccia {

    private Connection connection;

    public CarrelloDAO() {
        try {
			this.connection = DriverManagerConnectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    

    @Override
    public boolean doSave(CarrelloDTO cart) { //inserimento nuovo carrello
        String query = "INSERT INTO CARRELLO (ID_U) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cart.getID_Utente());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento del carrello: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean doFreeSpace(int id_cart) { //svuotare il carrello
        String query = "DELETE FROM PRODOTTI_CARRELLO WHERE ID_CARRELLO = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_cart);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Errore durante la pulizia dello spazio del carrello: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean doDeleteByKey(int id_cart) { //eliminare un certo carrello
        String query = "DELETE FROM CARRELLO WHERE ID_CARRELLO = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_cart);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Errore durante la cancellazione del carrello: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean doUpdateCart(CarrelloDTO cart) { //aggiornare carrello
        String query = "UPDATE CARRELLO SET ID_U = ? WHERE ID_CARRELLO = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cart.getID_Utente());
            statement.setInt(2, cart.getID_Carrello());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento del carrello: " + e.getMessage());
            return false;
        }
    }

    @Override
    public CarrelloDTO doRetrieveById(int id_cart) {  //ritornare un carrello 
        String query = "SELECT * FROM CARRELLO WHERE ID_CARRELLO = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_cart);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id_U = resultSet.getInt("ID_U");
                    ProdottoCarrelloDAO prod=new ProdottoCarrelloDAO();
                    ArrayList<ProdottoCarrelloDTO> prodotti_Cart=new ArrayList<>();
                    prodotti_Cart=prod.doRetrieveAll(id_cart);

                    return new CarrelloDTO(id_cart, prodotti_Cart, id_U);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del carrello con ID " + id_cart + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<CarrelloDTO> doRetrieveAll() { //ritorna tutti i carrelli presenti nel db
        String query = "SELECT * FROM CARRELLO";
        ArrayList<CarrelloDTO> carrelli = new ArrayList<>();
        

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id_cart = resultSet.getInt("ID_CARRELLO");
                int id_U = resultSet.getInt("ID_U");
                //per ogni carrello che trovo vado a trovare tutti i prodotti al suo interno sfruttando il DAO di ProdottoCarrello
                ProdottoCarrelloDAO prod=new ProdottoCarrelloDAO();
                ArrayList<ProdottoCarrelloDTO> prodotti_Cart=new ArrayList<>();
                prodotti_Cart=prod.doRetrieveAll(id_cart);
                CarrelloDTO cart = new CarrelloDTO(id_cart, prodotti_Cart, id_U);
                
                carrelli.add(cart);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero di tutti i carrelli: " + e.getMessage());
        }
        return carrelli;
    }
}
