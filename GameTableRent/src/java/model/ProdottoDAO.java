package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdottoDAO implements ProdottoDAOInterfaccia {
	private static final String RetrieveByKeySQL="SELECT * FROM PRODOTTO WHERE ID_Prod= ?";
	private static final String doSaveSQL="INSERT INTO PRODOTTO(ID_Prod, Nome, Descrizione, Prezzo, PrezzoXDays, Quantity, In_Cat, Picture) VALUES(?, ?, ?, ?, ?, ?, ?, ?";
	private static final String doDeleteSQL="DELETE FROM PRODOTTI WHERE ID_Prod= ?";
	private static final String doRetrieveAllSQL="SELECT * FROM PRODOTTO";
   private static final String doUpdateSQL = "UPDATE PRODOTTO SET Nome = ?, Descrizione = ?, Prezzo = ?, PrezzoXDays = ?, Quantity = ?, In_Cat = ? , Picture = ? WHERE ID_Prod = ?";
	
   
   
   @Override
	public ProdottoDTO doRetrieveByKey(int id_prod) {
	   try(Connection con=DriverManagerConnectionPool.getConnection();
			   PreparedStatement ps=con.prepareStatement(RetrieveByKeySQL)){
		   ps.setInt(1, id_prod);
		   ResultSet rs=ps.executeQuery();
		   if(rs.next()) {
			   return(new ProdottoDTO(
					   				rs.getInt("ID_Prod"), 
					   				rs.getString("Nome"), 
					   				rs.getString("Descrizione"), 
					   				rs.getDouble("Prezzo"), 
					   				rs.getDouble("PrezzoXDays"), 
					   				rs.getInt("Quantity"), 
					   				rs.getInt("In_Cat"),
					   				rs.getBytes("Picture")));
		   }
		   
	   } catch (SQLException e) {
		e.printStackTrace();
	}
	   return null;
	}

	@Override
	  public boolean doSave(ProdottoDTO prod) throws SQLException {
        try (Connection con = DriverManagerConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(doSaveSQL)) {
            ps.setInt(1, prod.getID_Prod());
            ps.setString(2, prod.getNome());
            ps.setString(3, prod.getDescrizione()); 
            ps.setDouble(4, prod.getPrezzo());
            ps.setDouble(5, prod.getPrezzoXDay());
            ps.setInt(6, prod.getQuantity());
            ps.setInt(7, prod.getIN_CAT());
            if(prod.getImmagine()!=null)
            ps.setBytes(8, prod.getImmagine());
            else ps.setBytes(8, null);
            int rs = ps.executeUpdate();
            return rs > 0;
        }
    }

	@Override
	public boolean doDeleteByKey(int id) throws SQLException {
		try(Connection con=DriverManagerConnectionPool.getConnection();
				   PreparedStatement ps=con.prepareStatement(doDeleteSQL)){
			ps.setInt(1, id);
			int rs=ps.executeUpdate();
			return rs>0;}
	}
	

	@Override
	public ArrayList<ProdottoDTO> doRetrieveAll() {
		try(Connection con=DriverManagerConnectionPool.getConnection();
				   PreparedStatement ps=con.prepareStatement(doRetrieveAllSQL)){
			ArrayList<ProdottoDTO> listaProd=new ArrayList<>();
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				listaProd.add(new ProdottoDTO(
											rs.getInt("ID_Prod"), 
											rs.getString("Nome"), 
											rs.getString("Descrizione"), 
											rs.getDouble("Prezzo"), 
											rs.getDouble("PrezzoXDays"), 
											rs.getInt("Quantity"), 
											rs.getInt("In_Cat"), 
											rs.getBytes("Picture")));
			}
			return listaProd;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean doUpdate(ProdottoDTO prodotto) {	
		try(Connection con=DriverManagerConnectionPool.getConnection();
				   PreparedStatement ps=con.prepareStatement(doUpdateSQL)){
			System.out.println("******AGGIORNO PRODOTTO NEL MAGAZZINO****** \nquantità= "+ prodotto.getQuantity());
			ps.setString(1, prodotto.getNome());
			ps.setString(2, prodotto.getDescrizione());
			ps.setDouble(3, prodotto.getPrezzo());
			ps.setDouble(4, prodotto.getPrezzoXDay());
			ps.setInt(5, prodotto.getQuantity());
			ps.setInt(6, prodotto.getIN_CAT());
			ps.setBytes(7, prodotto.getImmagine());
			ps.setInt(8, prodotto.getID_Prod());
			int rs=ps.executeUpdate();
			return rs>0;
			
	} catch (SQLException e) {
		e.printStackTrace();
	}
		return false; 
	}
}

