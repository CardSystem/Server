package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import db.DBUtil;
import domain.Account;
import domain.Card;
import dto.AccountDto;
import dto.CheckCardDaoToServiceDto;
import dto.CheckCardHistoryDto;
import exception.BusinessException;
import exception.ErrorCode;

public class CardHistoryDao {
	static DBUtil dbUtil = DBUtil.getInstance();


	public void insertCheckCardHistory(CheckCardHistoryDto data) throws SQLException {
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

	




	// Cards
	

}