package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBUtil;
import domain.Card;
import dto.CardDto;
import dto.CreditCardDaoToServiceDto;
import exception.BusinessException;
import exception.ErrorCode;

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
					"select * from card where id=?");
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
	
	// 정지되지 않은 모든 신용카드 발급 내역 조회
		public ArrayList<CardDto> selectAllNonStopedCreditCard() {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			ArrayList<CardDto> cardList = new ArrayList<>();
			try {
				conn = dbUtil.getConnection();
				pstmt = conn.prepareStatement ("select * from card where is_stopped=0 and card_type like '신용%' order by id");
				rs = pstmt.executeQuery();
				
				
				
				while (rs.next()) {
					Card card = Card.builder()
							.id(rs.getLong("id"))
							.productId(rs.getLong("product_id"))
							.accountId(rs.getLong("account_id"))
							.issuedDate(rs.getString("issued_date"))
							.cardType(rs.getString("card_type"))
							.validity(rs.getString("validity"))
							.agency(rs.getString("agency"))
							.issuer(rs.getString("issuer"))
							.isStopped(rs.getInt("is_stopped"))
							.cardNum(rs.getString("card_num"))
							.totalPayment(rs.getLong("total_payment"))	
							.build();
					
					cardList.add(new CardDto(card));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("카드 정보가 존재하지 않음.");
			} finally {
				try {
					dbUtil.close(rs);
					dbUtil.close(pstmt);
					dbUtil.close(conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			return cardList;
		}
	
	// 카드의 현재 월 총 결제 액수 업데이트
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
	
	// 카드의 정지 업데이트
	public void updateIsStoppedByCardId(Long cardId, int isStopped) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		try {
			conn = dbUtil.getConnection();
			pstmt.getConnection().prepareStatement("update card set is_stopped=? where id=?");
			pstmt.setLong(1, isStopped);
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
	
	// 제휴관련 데이터까지 가져오는 카드정보 조회
	public CreditCardDaoToServiceDto selectCreditCardByProductId(Long productId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Card card = null;
		CreditCardDaoToServiceDto dto = null;
		ResultSet rs = null;
		  try {
			    Class.forName("com.mysql.cj.jdbc.Driver");
			  } catch (ClassNotFoundException e) {
			    throw new RuntimeException("Cannot find the driver in the classpath!", e);
			  }

		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(
					"SELECT c.id,c.product_id,c.card_type,c.validity,c.agency,c.issuer,c.is_stopped,c.card_num, c.account_id,sc.discount, p.category_id FROM card c JOIN product p ON c.product_id = p.id JOIN sale_category sc ON p.category_id = sc.id WHERE c.product_id =?;");
			pstmt.setLong(1, productId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				card = new Card().builder()
						.id(rs.getLong("id"))
						.productId(rs.getLong("product_id"))
						.cardNum(rs.getString("card_num"))
						.cardType(rs.getString("card_type"))
						.validity(rs.getString("validity"))
						.agency(rs.getString("agency"))
						.issuer(rs.getString("issuer"))
						.isStopped(rs.getInt("is_stopped"))
						.build();

				dto = new CreditCardDaoToServiceDto(card, rs.getLong("account_id"), rs.getLong("discount"), rs.getLong("category_id"));

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
	
	// 해당 계좌에 연결된 모든 카드 목록 조회
	public ArrayList<CardDto> selectAllCardByAccountId(Long accountId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<CardDto> cardList = new ArrayList<>();
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement ("select * from card where accountId=?;");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Card card = Card.builder()
						.id(rs.getLong("id"))
						.productId(rs.getLong("product_id"))
						.accountId(rs.getLong("accpimt_id"))
						.issuedDate(rs.getString("issued_date"))
						.cardType(rs.getString("card_type"))
						.validity(rs.getString("validity"))
						.agency(rs.getString("agency"))
						.issuer(rs.getString("issuer"))
						.isStopped(rs.getInt("is_stopped"))
						.cardNum(rs.getString("card_num"))
						.totalPayment(rs.getLong("total_payment"))	
						.build();
						
				cardList.add(new CardDto(card));
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("카드 정보가 존재하지 않음.");
		} finally {
			try {
				dbUtil.close(rs);
				dbUtil.close(pstmt);
				dbUtil.close(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cardList;
	}
}