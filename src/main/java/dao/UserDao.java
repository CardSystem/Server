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
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
//			pstmt = conn.prepareStatement("update user set is_blocked=? where id=?");
			pstmt = conn.prepareStatement("UPDATE ACCOUNT A , CARD B SET A.IS_STOPPED = 1, B.IS_STOPPED = 1 WHERE A.USER_ID = ? AND B.ACCOUNT_ID = A.ID");
//			pstmt.setInt(1, isBlocked);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			System.out.println(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}
}
