package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	/** JDBC connect url. */
	private final String driverName = "com.mysql.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost:3306/card_system?serverTimezone=UTC";
	private final String DB_USERNAME = "root";
	private final String DB_PASSWORD = "1248";
	//Connection connection = null;

	private static DBUtil instance = new DBUtil();

	// constructor 드라이버 생성
	private DBUtil() {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

//    public jdbcUtils(final String driver, final String url) {
	// this.driver = driver;
	// this.url = url;
//    }

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

}
