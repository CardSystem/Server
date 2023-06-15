package dbutil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private final String driverName = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/card_system?serverTimezone=UTC";//database이름 = testdb
	private final String user = "root";//자신의 workbench 아이디
	private final String pass = "1234";//자신의 workbench 비번
	
	private static DBUtil instance = new DBUtil();
	
	private DBUtil(){
		//1. DB Driver를 로딩
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
		//2. DB(MySQL)에 접속하기
		return DriverManager.getConnection(url, user, pass);
	}
	
    //DB작업 후, 여러 클래스 close할 때 사용
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