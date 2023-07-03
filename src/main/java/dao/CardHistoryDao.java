package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;


import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import db.DBUtil;
import domain.Account;
import domain.Card;
import domain.CardHistory;
import dto.AccountDto;
import dto.CheckCardDaoToServiceDto;
import dto.CheckCardHistoryDto;
import dto.CreditCardHistoryCreateDto;
import dto.CreditCardHistoryDto;
import dto.CardHistoryResponseDto;

import exception.BusinessException;
import exception.ErrorCode;

public class CardHistoryDao {
	static DBUtil dbUtil = DBUtil.getInstance();
	
	public ArrayList<CardHistoryResponseDto> showPayCardList() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CardHistoryResponseDto> cardPayList = new ArrayList<>();
        CardHistory cardHistory = new CardHistory();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select id, card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history order by date asc;");

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	cardHistory = CardHistory.builder()
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
                cardPayList.add(CardHistoryResponseDto.of(cardHistory));
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return cardPayList;
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
					
					while (rs.next()) {
						CardHistory creditCardHistory = CardHistory.builder()
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
			
			
			// 신용카드 DB에 카드 결제 내역 저장
			public void insertCreditCardHistory(CreditCardHistoryCreateDto creditCardHistoryCreateDto) throws SQLException {
				Connection conn = null;
				PreparedStatement pstmt = null;
				
				CardHistory creditCardHistory = creditCardHistoryCreateDto.toEntity();
				
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
					pstmt = conn.prepareStatement("select * from card_history where card_type like '신용%';");
					rs = pstmt.executeQuery();//select실행
					
					while(rs.next()) {
						CardHistory creditCardHistory = CardHistory.builder()
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
	
	
	public ArrayList<CardHistoryResponseDto> showSearchUidCardList(String keyword) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        ArrayList<CardHistoryResponseDto> searchUidCardPayList = new ArrayList<>();
        CardHistory cardHistory = new CardHistory();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select id, card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where user_id = ? order by date desc;");
            pstmt.setString(1, keyword);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	
            	cardHistory = CardHistory.builder()
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
                searchUidCardPayList.add(CardHistoryResponseDto.of(cardHistory));

            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return searchUidCardPayList;
    }
	
		public ArrayList<CardHistoryResponseDto> showSearchUserCardList(String userId, long keyword) throws SQLException {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        
	        ArrayList<CardHistoryResponseDto> searchUserCardPayList = new ArrayList<>();
	        CardHistory cardHistory = new CardHistory();
	        try {
	            conn = dbUtil.getConnection();
	            pstmt = conn.prepareStatement("select id, card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where user_id = ? and card_id = ? order by date desc;");
	            pstmt.setString(1, userId);
	            pstmt.setLong(2, keyword);
	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	            	cardHistory = CardHistory.builder()
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
//	            	CardHistoryResponseDto data = new CardHistoryResponseDto();
//	            	data.setId(rs.getLong("id"));
//	            	data.setCardId(rs.getLong("card_id"));
//					data.setUserId(rs.getString("user_id"));
//					data.setFranchisee(rs.getString("franchisee"));
//					data.setPayment(rs.getInt("payment"));
//					data.setBalance(rs.getInt("balance"));
//					data.setDate(rs.getString("date"));
//					data.setFCategory(rs.getInt("f_category"));
//					data.setIsIns(rs.getInt("is_ins")); 
//					data.setInsMonth(rs.getInt("ins_month"));
//					data.setCardType(rs.getString("card_type"));
////					
					searchUserCardPayList.add(CardHistoryResponseDto.of(cardHistory));
//					searchUserCardPayList.add(data);
	            }
	        } catch(Exception e) {
	            e.printStackTrace();
	        } finally {
	            dbUtil.close(rs, pstmt, conn);
	        }
	        return searchUserCardPayList;
	    }
		
		
	public ArrayList<CardHistoryResponseDto> showSearchCardList(long keyword) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        long searchKeyword = keyword;
        
        ArrayList<CardHistoryResponseDto> searchCardPayList = new ArrayList<>();
        CardHistory cardHistory = new CardHistory();
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select id, card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where card_id = "+ searchKeyword +" order by date desc;");

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	cardHistory = CardHistory.builder()
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
            	
                searchCardPayList.add(CardHistoryResponseDto.of(cardHistory));
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return searchCardPayList;
    }
	

		public ArrayList<CardHistoryResponseDto> showMonthlyCardList(String option) throws SQLException {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        ArrayList<CardHistoryResponseDto> monthlyCardPayList = new ArrayList<>();
	        CardHistory cardHistory = new CardHistory();
	        if (!option.isEmpty()) { 
		        int searchPeriod = -(Integer.parseInt(option));
		        
		        try {
		            conn = dbUtil.getConnection();
		            pstmt = conn.prepareStatement("select id, card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where date between date_add(now(),interval "+searchPeriod +" month) and now() order by date desc;");
	
		            rs = pstmt.executeQuery();
		            while (rs.next()) {
		            	cardHistory = CardHistory.builder()
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
						monthlyCardPayList.add(CardHistoryResponseDto.of(cardHistory));

		            }
		        } catch(Exception e) {
		            e.printStackTrace();
		        } finally {
		            dbUtil.close(rs, pstmt, conn);	
		        }
		    }
	        return monthlyCardPayList;
		}
		


	public void insertCheckCardHistory(CheckCardHistoryDto data) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		  try {
			    System.out.println("Loading driver...");
			    Class.forName("com.mysql.cj.jdbc.Driver");
			    System.out.println("Driver loaded!");
			  } catch (ClassNotFoundException e) {
			    throw new RuntimeException("Cannot find the driver in the classpath!", e);
			  }

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

	

	

}