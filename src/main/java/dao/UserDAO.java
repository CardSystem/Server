package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbutil.DBUtil;

public class UserDAO {
	static DBUtil dbUtil = DBUtil.getInstance();
	
	public static int login(String id, String userBirth) throws SQLException {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = dbUtil.getConnection();
	        pstmt = conn.prepareStatement("SELECT user_birth FROM user WHERE id = ?");
	        pstmt.setString(1, id);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            String dbUserBirth = rs.getString("user_birth");
	            if (dbUserBirth.equals(userBirth)) {
	                return 1; // 로그인 성공
	            } else {
	                return 0; // 비밀번호 불일치
	            }
	        } else {
	            return -1; // 사용자가 존재하지 않음
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        dbUtil.close(rs, pstmt, conn);
	    }
	    return -2; // 예외 발생
	}
}
