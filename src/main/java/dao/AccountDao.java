package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBUtil;
import domain.Account;
import dto.AccountDto;

public class AccountDao {
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
						"select * from account join card on account.id=card.account_id where card.id=?;");
				pstmt.setLong(1, cardId);

				rs = pstmt.executeQuery();
				if (rs.next()) {
					account=new Account().builder()
							.id(rs.getLong("id"))
							.accountNum(rs.getString("account_num"))
							.balance(rs.getLong("balance"))
							.bankName(rs.getString("bank_name"))
							.isStopped(rs.getInt("is_stopped"))
							.build();

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
		
		
		public void updateBalance(Long accountId, Long money) throws SQLException {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int rs = 0;
			try {
				conn = dbUtil.getConnection();
				pstmt = conn.prepareStatement("update account set balance=? where id=?;");
				pstmt.setLong(1, money);
				pstmt.setLong(2, accountId);

				rs = pstmt.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("존재하지않음");
			}

			finally {
				try {
					pstmt.close();
					dbUtil.close(pstmt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}


}
