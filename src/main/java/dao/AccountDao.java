package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBUtil;
import dto.AccountDto;

public class AccountDao {
    static DBUtil dbUtil = DBUtil.getInstance();

    public static ArrayList<AccountDto> selectAccount(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<AccountDto> accountList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM account WHERE user_id = ?");
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                AccountDto ad = new AccountDto();
                ad.setId(rs.getLong("id"));
                ad.setUserId(rs.getString("user_id"));
                ad.setAccountNum(rs.getString("account_num"));
                ad.setBalance(rs.getLong("balance"));
                ad.setBankName(rs.getString("bank_name"));
                ad.setIsStopped(rs.getInt("is_stopped"));
                accountList.add(ad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return accountList;
    }
    
    public static void depositAccount(String accountNum, long balance) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("UPDATE account SET balance = balance + ? WHERE account_num = ?");
            pstmt.setLong(1, balance);
            pstmt.setString(2, accountNum);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(pstmt, conn);
        }
    }
    
    public static void changeIsStopped(int isStopped, String id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("update account set is_stopped=? where user_id=?");
            // 이부분 처리 애매함 -> boolean인지 int인지
			pstmt.setInt(1, isStopped);
			pstmt.setString(2, id);
			pstmt.executeUpdate();//insert실행
		} catch(Exception e) {
	         e.printStackTrace();
	    } finally {
			dbUtil.close(pstmt, conn);
		}
	}

	public static String getUserIdByAccountId(long account_id) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String userId = null;
	    try {
	        conn = dbUtil.getConnection();
	        pstmt = conn.prepareStatement("SELECT user_id FROM account WHERE id = ?");
	        pstmt.setLong(1, account_id);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	        	userId = rs.getString("user_id");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        dbUtil.close(rs, pstmt, conn);
	    }
		return userId;
	}
}