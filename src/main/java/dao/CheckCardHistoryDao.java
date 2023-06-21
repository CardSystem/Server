package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import db.DBUtil;
import domain.Account;
import domain.Cards;
import dto.AccountDto;
import dto.CheckCardDaoToServiceDto;
import dto.CheckCardHistoryDto;
import exception.BusinessException;
import exception.ErrorCode;

public class CheckCardHistoryDao {
	static DBUtil dbUtil = DBUtil.getInstance();

	public static Cards card;

	public void insertData(CheckCardHistoryDto data) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder d = new StringBuilder();
			d.append(
					"insert into card_history(card_id,user_id,franchisee,payment,balance,is_success,date,f_category,is_ins,ins_month,card_type) \n");
			d.append("values(?,?,?,?,?,?,?,?,?,?,?)");
			pstmt = conn.prepareStatement(d.toString());
			pstmt.setLong(1, data.getCardId());
			pstmt.setString(2, data.getUserId());
			pstmt.setString(3, data.getFranchisee());
			pstmt.setLong(4, data.getPayment());
			pstmt.setLong(5, data.getBalance());
			pstmt.setInt(6, data.getIsSuccess());
			pstmt.setTimestamp(7, Timestamp.valueOf(data.getDate()));
			pstmt.setLong(8, data.getFCategory());
			pstmt.setInt(9, data.getIsIns());
			pstmt.setInt(10, data.getInsMonth());
			pstmt.setString(11, data.getCardType());

			pstmt.executeUpdate();
			System.out.println("insert 실행");

		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	public void updateBalance(Long accountId, Long money) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Account account = null;
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

	// 카드아이디로 계좌찾기
	public AccountDto selectAccountByCardId(Long cardId) {
		System.out.println("dao 진입");
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

	// 카드아이디로 카드, 팔인율 찾기
	public CheckCardDaoToServiceDto selectCardByCardId(Long cardId) {
		System.out.println("카드찾기 dao 진입");
		Connection conn = null;
		PreparedStatement pstmt = null;
		Cards card = null;
		CheckCardDaoToServiceDto dto = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(
					"SELECT c.id,c.card_id,c.card_type,c.validity,c.agency,c.issuer,c.is_stopped,c.card_num, c.account_id,sc.discount FROM cards c JOIN product p ON c.card_id = p.id JOIN sale_category sc ON p.category_id = sc.id WHERE c.card_id =?;");
			pstmt.setLong(1, cardId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				card = new Cards();
				card.setId(rs.getLong("id"));
				card.setCardId(rs.getLong("card_id"));
				card.setCardType(rs.getString("card_type"));
				card.setValidity(rs.getString("validity"));
				card.setAgency(rs.getString("agency"));
				card.setIssuer(rs.getString("issuer"));
				card.setIsStopped(rs.getInt("is_stopped"));
				card.setCardNum(rs.getString("card_num"));

				dto = new CheckCardDaoToServiceDto(card, rs.getLong("account_id"), rs.getLong("discount"));

			} else {
				throw new BusinessException(ErrorCode.UNABLE_CARDNUM, "존재하지 않는 카드번호입니다.");
			}

		} catch (BusinessException | SQLException e) {
			System.out.println("찾을 수 없는 카드거나 계좌입니다: " + e.getMessage());
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