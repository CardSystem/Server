package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBUtil;
import domain.Account;
import dto.AccountResponseDto;

public class AccountDao {
    static DBUtil dbUtil = DBUtil.getInstance();
    private static AccountDao dao = new AccountDao();
    
    public static AccountDao getInstance() {
    	return dao;
    }

    public List<AccountResponseDto> selectAccount(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<AccountResponseDto> accountList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM account WHERE user_id = ?");
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Account account = Account.builder()
                		.id(rs.getLong("id"))
                		.userId(rs.getString("user_id"))
                		.accountNum(rs.getString("account_num"))
                		.balance(rs.getLong("balance"))
                		.bankName(rs.getString("bank_name"))
                		.isStopped(rs.getInt("is_stopped"))
                		.build();
                accountList.add(AccountResponseDto.of(account));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return accountList;
    }
    
    public void depositAccount(String accountNum, long balance) throws SQLException {
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
    
    public void changeIsStopped(int isStopped, String id) throws SQLException {
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

	public String getUserIdByAccountId(long account_id) {
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