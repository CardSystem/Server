package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import dto.CreditCardHistoryDto;
import domain.Account;
import domain.Card;
import domain.CreditCardHistory;
import dto.CardDto;
import dto.CreditCardHistoryCreateDto;
import db.DBUtil;

public class CreditCardHistoryDao {

	static DBUtil dbUtil = DBUtil.getInstance();
	
	//카드아이디로 계좌 찾기
	public Account selectAccountByCardId(Long cardId)
	{
		System.out.println("신용카드 계좌찾기 dao 진입");
		Connection conn = null;
		PreparedStatement pstmt = null;
		Account account=null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("select * from account where id in (select account_id from cards where account_id=?);");
			pstmt.setLong(1,cardId);
			
	
			rs=pstmt.executeQuery();
			if(rs.next()) {
				account = new Account();
				
				account.builder()
				.id(rs.getLong("id"))
				.accountNum(rs.getString("account_num"))
				.balance(rs.getLong("balance"))
				.bankName(rs.getString("bank_name"))
				.isStopped(rs.getInt("is_stopped"))
				.build();
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("계좌가 존재하지 않습니다");
		}
				
		finally {
			try {
				dbUtil.close(rs);
				dbUtil.close(pstmt);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return account;
	}
	
	
	// 신용카드 결제내역 반환
		public ArrayList<CreditCardHistoryDto> selectAllNonInstallmentCrditCardHistoryByCardId(Long cardId) throws SQLException {
			ArrayList<CreditCardHistoryDto> list = new ArrayList<>();
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = dbUtil.getConnection();
				// 해당 월 명세서는 다음달로 넘어가는 자정에 생성하므로
				// 조건에 맞는 이전달 모든 결제 목록을 가져온다.
				pstmt = conn.prepareStatement("SELECT * FROM card_history WHERE is_success=1 and card_id=? and is_ins=0 and DATE_FORMAT(date, '%Y-%m') = CASE WHEN MONTH(CURDATE()) = 1 THEN CONCAT(YEAR(CURDATE()) - 1, '-12') ELSE DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m') END;");
				pstmt.setLong(1,cardId);
				rs = pstmt.executeQuery();//select실행
				
				if (rs.next()) {
					CreditCardHistory creditCardHistory = CreditCardHistory.builder()
							.id(rs.getLong("id"))
							.cardId(rs.getLong("card_id"))
							.userId(rs.getString("user_id"))
							.franchisee(rs.getString("franchisee"))
							.payment(rs.getLong("payment"))
							.balance(rs.getLong("balance"))
							.isSuccess(rs.getInt("is_success"))
							.date(rs.getTimestamp("date").toLocalDateTime())
							.fCategory(rs.getLong("f_category"))
							.isIns(rs.getInt("is_ins"))
							.insMonth(rs.getInt("ins_month"))
							.cardType(rs.getString("card_type"))
							.build();
					
					
					
					list.add(new CreditCardHistoryDto(creditCardHistory));
					
				}
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println("카드 사용내역 데이터가 존재하지 않습니다.");
			}finally {
				try {
					dbUtil.close(rs, pstmt, conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		}
	
	/*
	// 카드id로 카드 존재 여부 확인
	public CardDto selectCardByCardId(Long cardId) {
		System.out.println("카드 존재 여부 확인 dao 진입");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		Card card = null;
		ResultSet rs = null;
		
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("select * from cards where id=?;");
			pstmt.setLong(1, cardId);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				card = new Card();
				
				card.builder()
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
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("카드가 존재하지 않습니다.");
		}finally {
			try {
				dbUtil.close(rs, pstmt, conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return new CardDto(card);
	}
	*/
	
	// 신용카드 DB에 카드 결제 내역 저장
	public void insertCreditCardHistory(CreditCardHistoryCreateDto creditCardHistoryCreateDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		CreditCardHistory creditCardHistory = creditCardHistoryCreateDto.toEntity();
		
		try {
        	//3. sql문 실행
			conn = dbUtil.getConnection();
			StringBuilder d = new StringBuilder();
			d.append("insert into card_history (card_id, user_id, franchisee, payment, balance, is_success, date, f_category, is_ins, ins_month, card_type) \n");
			d.append("values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt = conn.prepareStatement(d.toString());
			pstmt.setLong(1, creditCardHistory.getCardId());
			pstmt.setString(2, creditCardHistory.getUserId());
			pstmt.setString(3, creditCardHistory.getFranchisee());
			pstmt.setLong(4, creditCardHistory.getPayment());
			pstmt.setLong(5, creditCardHistory.getBalance());
			pstmt.setInt(6, creditCardHistory.getIsSuccess());
			pstmt.setTimestamp(7, Timestamp.valueOf(creditCardHistory.getDate()));
			pstmt.setLong(8, creditCardHistory.getFCategory());
			pstmt.setInt(9, creditCardHistory.getIsIns());
			pstmt.setInt(10, creditCardHistory.getInsMonth());
			pstmt.setString(11, creditCardHistory.getCardType());
			
			pstmt.executeUpdate();//insert실행
			System.out.println("신용카드 사용내역 insert 실행");
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}
	
	// 신용카드 결제내역 반환
	public ArrayList<CreditCardHistoryDto> selectAllCrditCardHistory() throws SQLException {
		ArrayList<CreditCardHistoryDto> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("select * from card_history");
			rs = pstmt.executeQuery();//select실행
			
			while(rs.next()) {
				CreditCardHistory creditCardHistory = CreditCardHistory.builder()
						.id(rs.getLong("id"))
						.cardId(rs.getLong("card_id"))
						.userId(rs.getString("user_id"))
						.franchisee(rs.getString("franchisee"))
						.payment(rs.getLong("payment"))
						.balance(rs.getLong("balance"))
						.isSuccess(rs.getInt("is_success"))
						.date(rs.getTimestamp("date").toLocalDateTime())
						.fCategory(rs.getLong("f_category"))
						.isIns(rs.getInt("is_ins"))
						.insMonth(rs.getInt("ins_month"))
						.cardType(rs.getString("card_type"))
						.build();
				
				
				
				list.add(new CreditCardHistoryDto(creditCardHistory));
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("데이터가 존재하지 않습니다.");
		}finally {
			try {
				dbUtil.close(rs, pstmt, conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}