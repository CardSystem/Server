package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBUtil;
import domain.Card;
import domain.User;
import dto.CardDto;
import dto.UserDto;


public class UserDao {
	private static DBUtil dbUtil = DBUtil.getInstance();

	
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
