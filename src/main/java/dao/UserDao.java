package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBUtil;
import domain.User;
import dto.UserDto;
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
					return 1; // �α��� ����
				} else {
					return 0; // ��й�ȣ ����ġ
				}
			} else {
				return -1; // ����ڰ� �������� ����
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return -2; // ���� �߻�
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

			// ù ��° pstmt ����
			pstmt1 = conn.prepareStatement("update user set admin_block=? where id=?");
			pstmt1.setInt(1, isBlocked);
			pstmt1.setString(2, id);
			pstmt1.executeUpdate();

			// �� ��° pstmt ����
			pstmt2 = conn.prepareStatement(
					"update account A , card B set B.is_stopped = B.is_stopped + 1 WHERE A.user_id = ? and B.account_id = A.id");
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

			// ù ��° pstmt ����
			pstmt1 = conn.prepareStatement("update user set admin_block=? where id=?");
			pstmt1.setInt(1, isBlocked);
			pstmt1.setString(2, id);
			pstmt1.executeUpdate();

			// �� ��° pstmt ����
			pstmt2 = conn.prepareStatement(
					"update account A , card B set B.is_stopped = B.is_stopped - 1 WHERE A.user_id = ? and B.account_id = A.id");
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
	
	
	// 카드id로 카드 존재 여부 확인
		public UserDto selectUserByUserId(Long userId) throws SQLException {
			System.out.println("카드 존재 여부 확인 dao 진입");
			
			User user = null;
				
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
				
				
			try {
				conn = dbUtil.getConnection();
				pstmt = conn.prepareStatement("select * from user where id=?;");
				pstmt.setLong(1, userId);
				
				rs = pstmt.executeQuery();
				if (rs.next()) {
					user = User.builder()
							.id(rs.getString("user_name"))
							.userBirth(rs.getString("user_birth"))
							.credit(rs.getInt("credit"))
							.adminBlock(rs.getInt("admin_block"))
							.delayBlock(rs.getInt("delay_block"))
							.gender(rs.getString("gender"))
							.build();
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("유저가 존재하지 않습니다.");
			}finally {
				try {
					dbUtil.close(rs, pstmt, conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			return new UserDto(user);
		}
		
		// 모든 고객 목록 반환
		public ArrayList<UserDto> selectAllUser() throws SQLException {
			ArrayList<UserDto> userList = new ArrayList<>();
			
			User user = null;
				
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
				
				
			try {
				conn = dbUtil.getConnection();
				
				pstmt = conn.prepareStatement("select * from user;");
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					user = User.builder()
							.id(rs.getString("user_name"))
							.userBirth(rs.getString("user_birth"))
							.credit(rs.getInt("credit"))
							.adminBlock(rs.getInt("admin_block"))
							.delayBlock(rs.getInt("delay_block"))
							.gender(rs.getString("gender"))
							.build();
					
					userList.add(new UserDto(user));
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("유저가 존재하지 않습니다.");
			}finally {
				try {
					dbUtil.close(rs, pstmt, conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			return userList;
		}
		
		// 연체로 브랙리스트처리된 모든 고객목록 반환
		public ArrayList<UserDto> selectAllNonBlockUser() throws SQLException {
			ArrayList<UserDto> userList = new ArrayList<>();
			
			User user = null;
				
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
				
				
			try {
				conn = dbUtil.getConnection();
				
				pstmt = conn.prepareStatement("select * from user where delay_block=1;");
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					user = User.builder()
							.id(rs.getString("user_name"))
							.userBirth(rs.getString("user_birth"))
							.credit(rs.getInt("credit"))
							.adminBlock(rs.getInt("admin_block"))
							.delayBlock(rs.getInt("delay_block"))
							.gender(rs.getString("gender"))
							.build();
					
					userList.add(new UserDto(user));
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("유저가 존재하지 않습니다.");
			}finally {
				try {
					dbUtil.close(rs, pstmt, conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			return userList;
		}
		
		public void updateDelayBlockUserByUserId(String userId, int delayBlock) throws SQLException {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        try {
	            conn = dbUtil.getConnection();
	            pstmt = conn.prepareStatement("UPDATE user SET delay_block = ? WHERE id = ?");
	            pstmt.setInt(1, delayBlock);
	            pstmt.setString(2, userId);
	            pstmt.executeUpdate();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            dbUtil.close(pstmt, conn);
	        }
	    }
}
