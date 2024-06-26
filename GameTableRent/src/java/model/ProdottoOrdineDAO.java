package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdottoOrdineDAO implements ProdGenericDAOInterfaccia<ProdottoOrdineDTO> {

   

    @Override
    public boolean doSave(ProdottoOrdineDTO prod) {
        String query = "INSERT INTO PRODOTTI_ORDINE (ID_ORDINE, ID_PRODOTTO, PREZZO, PREZZOXDAYS, GIORNI, QUANTITY, PICTURE, NOME) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        
        try (	Connection connection=DriverManagerConnectionPool.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, prod.getId_ordine());
            statement.setInt(2, prod.getId_prodotto());
            statement.setDouble(3, prod.getPrezzo());
            statement.setDouble(4, prod.getPrezzoXdays());
            statement.setInt(5, prod.getGiorni());
            statement.setInt(6, prod.getQuantity());
            statement.setBytes(7, prod.getImage());
            statement.setString(8, prod.getName());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento del prodotto nel carrello: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean doDelete(ProdottoOrdineDTO prod) {
        String query = "DELETE FROM PRODOTTI_ORDINE WHERE ID_ORDINE = ? AND ID_PRODOTTO = ?";
        
        try (	Connection connection=DriverManagerConnectionPool.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setInt(1, prod.getId_ordine());
            statement.setInt(2, prod.getId_prodotto());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Errore durante la cancellazione del prodotto dal carrello: " + e.getMessage());
            return false;
        }
    }

    public boolean doUpdate(ProdottoOrdineDTO prod) {
        String query = "UPDATE PRODOTTI_ORDINE SET PREZZO = ?, PREZZOXDAYS = ?, GIORNI = ?, QUANTITY = ? WHERE ID_ORDINE = ? AND ID_PRODOTTO = ?";
        
        try (	Connection connection=DriverManagerConnectionPool.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setDouble(1, prod.getPrezzo());
            statement.setDouble(2, prod.getPrezzoXdays());
            statement.setInt(3, prod.getGiorni());
            statement.setInt(4, prod.getQuantity());
            statement.setInt(5, prod.getId_ordine());
            statement.setInt(6, prod.getId_prodotto());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento del prodotto nel carrello: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ProdottoOrdineDTO doRetrieveByKey(int id_ord, int id_prod) {
        String query = "SELECT * FROM PRODOTTI_ORDINE WHERE ID_CARRELLO = ? AND ID_PRODOTTO = ?";
        
        try (	Connection connection=DriverManagerConnectionPool.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setInt(1, id_ord);
            statement.setInt(2, id_prod);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int quantity = resultSet.getInt("QUANTITY");
                    int giorni = resultSet.getInt("GIORNI");
                    double prezzo=resultSet.getDouble("PREZZO");
                    double prezzoxdays=resultSet.getDouble("PREZZOXDAYS");
                    byte[] picture=resultSet.getBytes("PICTURE");
                    String nome=resultSet.getString("Nome");


                    return new ProdottoOrdineDTO(id_ord, id_prod,prezzo, prezzoxdays, quantity, giorni, picture, nome);

                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del prodotto nel carrello: " + e.getMessage());
        }
        return null;
    }

    
    public ArrayList<ProdottoOrdineDTO> doRetrieveAll(int id_ord) {
        String query = "SELECT * FROM PRODOTTI_ORDINE WHERE ID_ORDINE = ?";
        ArrayList<ProdottoOrdineDTO> prodotti = new ArrayList<>();
        
        try (	Connection connection=DriverManagerConnectionPool.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setInt(1, id_ord);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id_prodotto = resultSet.getInt("ID_PRODOTTO");
                    int quantity = resultSet.getInt("QUANTITY");
                    int giorni = resultSet.getInt("GIORNI");
                    double prezzo=resultSet.getDouble("PREZZO");
                    double prezzoxdays=resultSet.getDouble("PREZZOXDAYS");
                    byte[] picture=resultSet.getBytes("PICTURE");
                    String nome=resultSet.getString("Nome");


                    ProdottoOrdineDTO prodotto = new ProdottoOrdineDTO(id_ord, id_prodotto,  prezzo, prezzoxdays, giorni, quantity, picture, nome);
                    prodotti.add(prodotto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei prodotti nel carrello: " + e.getMessage());
        }
        return prodotti;
    }
    
    
    
    

    public ArrayList<ProdottoOrdineDTO> doRetrieveByProd(int id_prod) {
        String query = "SELECT * FROM PRODOTTI_ORDINE WHERE ID_PRODOTTO = ?";
        ArrayList<ProdottoOrdineDTO> ordini = new ArrayList<>();
        
        try (	Connection connection=DriverManagerConnectionPool.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setInt(1, id_prod);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id_ord = resultSet.getInt("ID_ORDINE");
                    int quantity = resultSet.getInt("QUANTITY");
                    int giorni = resultSet.getInt("GIORNI");
                    double prezzo=resultSet.getDouble("PREZZO");
                    double prezzoxdays=resultSet.getDouble("PREZZOXDAYS");
                    byte[] picture=resultSet.getBytes("PICTURE");
                    String nome=resultSet.getString("Nome");


                    ProdottoOrdineDTO prodotto = new ProdottoOrdineDTO(id_ord, id_prod, prezzo, prezzoxdays, giorni, quantity, picture, nome);

                    ordini.add(prodotto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei carrelli per il prodotto: " + e.getMessage());
        }
        return ordini;
    }

	




}
