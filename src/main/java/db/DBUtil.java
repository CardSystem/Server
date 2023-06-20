package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	private final String driverName = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://127.0.0.1:3306/card_sys?serverTimezone=UTC&characterEncoding=UTF-8";
	private final String user = "root";
	private final String pass = "aa262622!";
	
	private static DBUtil instance = new DBUtil();
	
	private DBUtil(){
		//1. DB Driver�� �ε�
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
		//2. DB(MySQL)�� �����ϱ�
		return DriverManager.getConnection(url, user, pass);
	}
	
    //DB�۾� ��, ���� Ŭ���� close�� �� ���
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
	
	public static void close(Connection con) {
		try {
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs) {
		try {
			rs.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void commit(Connection con) {
	
		try{
			con.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void rollback(Connection con) {
		
		try{
			con.rollback();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}