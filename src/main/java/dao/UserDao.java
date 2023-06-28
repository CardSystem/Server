package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBUtil;
import dto.UserDto;

public class UserDao {
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

	public static ArrayList<UserDto> showUserList() throws SQLException {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    ArrayList<UserDto> userList = new ArrayList<>();

	    try {
	        conn = dbUtil.getConnection();
	        pstmt = conn.prepareStatement("SELECT * FROM user");
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            String id = rs.getString("id");
	            if (!id.contains("Admin")) {
	                UserDto user = new UserDto();
	                user.setId(id);
	                user.setUserName(rs.getString("user_name"));
	                user.setUserBirth(rs.getString("user_birth"));
	                user.setCredit(rs.getInt("credit"));
	                user.setIsBlocked(rs.getInt("is_blocked"));
	                user.setGender(rs.getString("gender"));

	                userList.add(user);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        dbUtil.close(rs, pstmt, conn);
	    }

	    return userList;
	}
	
	public static void changeIsBlock(int isBlocked, String id) throws SQLException {
	    Connection conn = null;
	    PreparedStatement pstmt1 = null;
	    PreparedStatement pstmt2 = null;

	    try {
	        conn = dbUtil.getConnection();

	        // 첫 번째 pstmt 실행
	        pstmt1 = conn.prepareStatement("update user set is_blocked=? where id=?");
	        pstmt1.setInt(1, isBlocked);
	        pstmt1.setString(2, id);
	        pstmt1.executeUpdate();

	        // 두 번째 pstmt 실행
	        pstmt2 = conn.prepareStatement("UPDATE ACCOUNT A , CARD B SET B.IS_STOPPED = B.IS_STOPPED + 1 WHERE A.USER_ID = ? AND B.ACCOUNT_ID = A.ID");
	        pstmt2.setString(1, id);
	        pstmt2.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        dbUtil.close(pstmt2);
	        dbUtil.close(pstmt1, conn);
	    }
	}
	
	public static void changeIsCancel(int isBlocked, String id) throws SQLException {
	    Connection conn = null;
	    PreparedStatement pstmt1 = null;
	    PreparedStatement pstmt2 = null;

	    try {
	        conn = dbUtil.getConnection();

	        // 첫 번째 pstmt 실행
	        pstmt1 = conn.prepareStatement("update user set is_blocked=? where id=?");
	        pstmt1.setInt(1, isBlocked);
	        pstmt1.setString(2, id);
	        pstmt1.executeUpdate();

	        // 두 번째 pstmt 실행
	        pstmt2 = conn.prepareStatement("UPDATE ACCOUNT A , CARD B SET B.IS_STOPPED = B.IS_STOPPED - 1 WHERE A.USER_ID = ? AND B.ACCOUNT_ID = A.ID");
	        pstmt2.setString(1, id);
	        pstmt2.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        dbUtil.close(pstmt2);
	        dbUtil.close(pstmt1, conn);
	    }
	}
	
	public static int checkUserBlock(String id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int isBlocked = 0;

	    try {
	        conn = dbUtil.getConnection();
	        pstmt = conn.prepareStatement("SELECT is_blocked FROM user WHERE id = ?");
	        pstmt.setString(1, id);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            isBlocked = rs.getInt("is_blocked");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        dbUtil.close(rs, pstmt, conn);
	    }

	    return isBlocked;
	}
}
