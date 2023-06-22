package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private final String driverName = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/card_system?serverTimezone=UTC";//database�씠由� = testdb
	private final String user = "root";
	private final String pass = "1234";
	
	private static DBUtil instance = new DBUtil();
	
	private DBUtil(){
		
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
		
		return DriverManager.getConnection(url, user, pass);
	}
	
    
	public void close(AutoCloseable... closeables) {
		for(AutoCloseable c : closeables) {
			if(c!=null) {
				try {
					c.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}