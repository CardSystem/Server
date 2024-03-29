package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	String driverName = "com.mysql.cj.jdbc.Driver";

	
    private static final String DB_URL = "jdbc:mysql://cardsystemdatabase.cdvfrfzlq3nb.us-east-1.rds.amazonaws.com:3306/cardsystemdatabase";
    private static final String DB_USERNAME = "admin";
    private static final String DB_PASSWORD = "12341234";
//	  String dbName = System.getProperty("cardsystemdatabase");
//	  String userName = System.getProperty("admin");
//	  String password = System.getProperty("12341234");
//	  String hostname = System.getProperty("cardsystemdatabase.cdvfrfzlq3nb.us-east-1.rds.amazonaws.com");
//	  String port = System.getProperty("3306");
//	  String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
//	    port + "/" + dbName + "?user=" + userName + "&password=" + password;
//	

	private static DBUtil instance = new DBUtil();

	private DBUtil() {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	


	public static DBUtil getInstance() {
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

	public void close(AutoCloseable... closeables) {
		for (AutoCloseable c : closeables) {
			if (c != null) {
				try {
					c.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement stmt) {
		try {
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void commit(Connection con) {

		try {
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void rollback(Connection con) {

		try {
			con.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}