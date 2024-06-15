package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Test {

	public static void main(String[] args) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String selectSQL= null;
		selectSQL="DELETE FROM USER WHERE ID= ?";
		connection=DriverManagerConnectionPool.getConnection();
		 preparedStatement = connection.prepareStatement(selectSQL);
		 preparedStatement.setString(1, "12345");
		 preparedStatement.executeUpdate();
		

		System.out.println("dati inseriti");
		preparedStatement.close(); // Close the prepared statement

	}

}
