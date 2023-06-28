package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBUtil;
import domain.Card;
import dto.CardDto;

public class CardDao {
	static DBUtil dbUtil = DBUtil.getInstance();
	
	// 하나의 카드 발급 내역 조회
	public CardDto selectCardByCardId(Long cardId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Card card = null;
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement (
					"select * from cards where id=?");
			pstmt.setLong(1, cardId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				card = Card.builder()
						.id(rs.getLong("id"))
						.productId(rs.getLong("product_id"))
						.accountId(rs.getLong(""))
						.issuedDate(rs.getString("issued_date"))
						.cardType(rs.getString("card_type"))
						.validity(rs.getString("validity"))
						.agency(rs.getString("agency"))
						.issuer(rs.getString("issuer"))
						.isStopped(rs.getInt("is_stopped"))
						.cardNum(rs.getString("card_num"))
						.totalPayment(rs.getLong("total_payment"))	
						.build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("카드 정보가 존재하지 않음.");
		} finally {
			try {
				dbUtil.close(rs);
				dbUtil.close(pstmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return new CardDto(card);
	}
	
	// 현재 달의 총 결제 액수 업데이트
	public void updateTotalPaymentByCardId(Long totalPayment, Long cardId) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		try {
			conn = dbUtil.getConnection();
			pstmt.getConnection().prepareStatement("update card set total_payment=? where id=?");
			pstmt.setLong(1,totalPayment);
			pstmt.setLong(2, cardId);
			
			rs = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("해당 카드의 데이터가 존재하지않습니다.");
		} finally {
			try {
				pstmt.close();
				dbUtil.close(pstmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}