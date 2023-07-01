package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBUtil;
import domain.User;
import dto.UserRequestDto;
import dto.UserResponseDto;

public class UserDao {
	private static DBUtil dbUtil = DBUtil.getInstance();
	private static UserDao dao = new UserDao();

	public static UserDao getInstance() {
		return dao;
	}

	public int login(String id, String userBirth) throws SQLException {
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

	public List<UserResponseDto> showUserList() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<UserResponseDto> userList = new ArrayList<>();

		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM user");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String userId = rs.getString("id");
				if (!userId.contains("Admin")) {
					User user = User.builder()
							.id(userId)
							.userName(rs.getString("user_name"))
							.userBirth(rs.getString("user_birth"))
							.credit(rs.getInt("credit"))
							.adminBlock(rs.getInt("admin_block"))
							.delayBlock(rs.getInt("delay_block"))
							.gender(rs.getString("gender"))
							.build();
					userList.add(UserResponseDto.of(user));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}

		return userList;
	}

	public void changeIsBlock(int isBlocked, String id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;

		try {
			conn = dbUtil.getConnection();

			// 첫 번째 pstmt 실행
			pstmt1 = conn.prepareStatement("update user set admin_block=? where id=?");
			pstmt1.setInt(1, isBlocked);
			pstmt1.setString(2, id);
			pstmt1.executeUpdate();

			// 두 번째 pstmt 실행
			pstmt2 = conn.prepareStatement(
					"UPDATE ACCOUNT A , CARD B SET B.IS_STOPPED = B.IS_STOPPED + 1 WHERE A.USER_ID = ? AND B.ACCOUNT_ID = A.ID");
			pstmt2.setString(1, id);
			pstmt2.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(pstmt2);
			dbUtil.close(pstmt1, conn);
		}
	}

	public void changeIsCancel(int isBlocked, String id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;

		try {
			conn = dbUtil.getConnection();

			// 첫 번째 pstmt 실행
			pstmt1 = conn.prepareStatement("update user set admin_block=? where id=?");
			pstmt1.setInt(1, isBlocked);
			pstmt1.setString(2, id);
			pstmt1.executeUpdate();

			// 두 번째 pstmt 실행
			pstmt2 = conn.prepareStatement(
					"UPDATE ACCOUNT A , CARD B SET B.IS_STOPPED = B.IS_STOPPED - 1 WHERE A.USER_ID = ? AND B.ACCOUNT_ID = A.ID");
			pstmt2.setString(1, id);
			pstmt2.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(pstmt2);
			dbUtil.close(pstmt1, conn);
		}
	}

	public int checkUserBlock(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int isBlocked = 0;

		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("SELECT admin_block FROM user WHERE id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				isBlocked = rs.getInt("admin_block");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}

		return isBlocked;
	}
}
