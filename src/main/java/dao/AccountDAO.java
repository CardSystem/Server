package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBUtil;
import dto.AccountDTO;

public class AccountDAO {
    static DBUtil dbUtil = DBUtil.getInstance();

    public static ArrayList<AccountDTO> selectAccount(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<AccountDTO> accountList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM account WHERE user_id = ?");
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                AccountDTO ad = new AccountDTO();
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
}