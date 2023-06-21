package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DBUtil;
import domain.Account;
import dto.AccountDto;

public class ProductDao {
	static DBUtil dbUtil = DBUtil.getInstance();

	public AccountDto selectAccountByCardId(Long cardId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Account account = null;
		AccountDto dto = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(
					"select * from account join cards on account.id=cards.account_id where cards.id=?;");
			pstmt.setLong(1, cardId);

			rs = pstmt.executeQuery();
			if (rs.next()) {

				account = new Account();
				account.setId(rs.getLong("id"));
				account.setAccountNum(rs.getString("account_num"));
				account.setBalance(rs.getLong("balance"));
				account.setBankName(rs.getString("bank_name"));
				account.setIsStopped(rs.getInt("is_stopped"));
				System.out.println("계좌번호:" + account.getAccountNum());
				dto = new AccountDto(account);

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("존재하지않음");
		}

		finally {
			try {
				dbUtil.close(rs);
				dbUtil.close(pstmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
}
