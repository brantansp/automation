package environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class CsvQuery {
	
	static Connection conn;
	
	/**
	 * 
	 * Connection With CSV file
	 * Usage: | getConnection| filePath| seperator|
	 * @param filePath
	 * @param seperator
	 * @return 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void getConnection(String filePath,String seperator) throws SQLException, ClassNotFoundException {
		
		Class.forName("org.relique.jdbc.csv.CsvDriver");

		String url = "jdbc:relique:csv:" + filePath + "?"
				+ "separator="+seperator+ "&" + "fileExtension=.csv";

		Properties props = new Properties();

		props.put("suppressHeaders", "false");

		conn = DriverManager.getConnection(url, props);

	}
	
	/**
	 * Execute sql query on CSV file
	 * Usage: | executeQuery| query| column|
	 * @param conn
	 * @param query
	 * @param column
	 * @return String
	 * @throws SQLException
	 */
	public static String executeQuery(String query, String column) throws SQLException {
		
		Statement stmt = conn.createStatement();

		ResultSet results = stmt.executeQuery(query);
		
		ArrayList <String> result = new ArrayList<String>();
		
		while (results.next()) {
			result.add(results.getString(column));
		}
		
		return result.toString().replaceAll("\\[", "").replaceAll("\\]", "");
	}
	
	/**
	 * Usage: | closeConnection|
	 * @throws SQLException
	 */
	public static void closeConnection() throws SQLException {
		conn.close();
	}
	
}
