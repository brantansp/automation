package Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresDB {

	/**
	 * To get the value  using query from postgresDB
	 * Usage: |getDataFromPostgresDb | url|query|
	 * @param url
	 * @param query
	 * @return data
	 */
	public static String getDataFromPostgresDb(String url,String query) throws IOException {
		String data = "";
		try (Connection conn = DriverManager.getConnection(url)) {

			if (conn != null) {
				System.out.println("Connected to the postgres database!");
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					data = rs.getString(1);
				}
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return data;
	}


	/**
	 * To update value using query in postgresDB
	 * Usage: |updateDataInPostgresDb | url|query|
	 * @param url
	 * @param query
	 * @return data
	 */
	public static int updateDataInPostgresDb(String url,String query) throws IOException {
		int data = 0;
		try (Connection conn = DriverManager.getConnection(url)) {

			if (conn != null) {
				//System.out.println("Connected to the postgres database!");
				Statement st = conn.createStatement();
				data=st.executeUpdate(query);
				 System.out.println(data);
				}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return data;
	}
	
	
}
