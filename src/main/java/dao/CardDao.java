package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBUtil;
import domain.Card;
import domain.Product;
import dto.CardHistoryDto;
import dto.CardInsertDto;
import dto.CheckCardDaoToServiceDto;
import dto.ProductDto;
import exception.BusinessException;
import exception.ErrorCode;

public class CardDao {
	static DBUtil dbUtil = DBUtil.getInstance();

	public static ArrayList<CardHistoryDto> showPayCardList() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CardHistoryDto> cardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history order by date asc;");

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryDto data = new CardHistoryDto();
            	data.setCardId(rs.getLong("card_id"));
				data.setUserId(rs.getString("user_id"));
				data.setFranchisee(rs.getString("franchisee"));
				data.setPayment(rs.getInt("payment"));
				data.setBalance(rs.getInt("balance"));
				data.setDate(rs.getString("date"));
				data.setFCategory(rs.getInt("f_category"));
				data.setIsIns(rs.getInt("is_ins")); 
				data.setInsMonth(rs.getInt("ins_month"));
				data.setCardType(rs.getString("card_type"));
               
                cardPayList.add(data);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return cardPayList;
    }
	
	public static ArrayList<CardHistoryDto> showRecentPayCardList() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CardHistoryDto> recentCardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history order by date desc;");

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryDto data = new CardHistoryDto();
            	data.setCardId(rs.getLong("card_id"));
				data.setUserId(rs.getString("user_id"));
				data.setFranchisee(rs.getString("franchisee"));
				data.setPayment(rs.getInt("payment"));
				data.setBalance(rs.getInt("balance"));
				data.setDate(rs.getString("date"));
				data.setFCategory(rs.getInt("f_category"));
				data.setIsIns(rs.getInt("is_ins"));
				data.setInsMonth(rs.getInt("ins_month"));
				data.setCardType(rs.getString("card_type"));
               
                recentCardPayList.add(data);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return recentCardPayList;
    }
	
	public static ArrayList<CardHistoryDto> showSearchUidCardList(String keyword) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        ArrayList<CardHistoryDto> searchUidCardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where user_id = ? order by date desc;");
            pstmt.setString(1, keyword);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryDto data = new CardHistoryDto();
            	data.setCardId(rs.getLong("card_id"));
				data.setUserId(rs.getString("user_id"));
				data.setFranchisee(rs.getString("franchisee"));
				data.setPayment(rs.getInt("payment"));
				data.setBalance(rs.getInt("balance"));
				data.setDate(rs.getString("date"));
				data.setFCategory(rs.getInt("f_category"));
				data.setIsIns(rs.getInt("is_ins"));
				data.setInsMonth(rs.getInt("ins_month"));
				data.setCardType(rs.getString("card_type"));
               
                searchUidCardPayList.add(data);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return searchUidCardPayList;
    }
	
	//�븳 user媛� �냼�쑀�븳移대뱶 寃곗젣�궡�뿭議고쉶
		public static ArrayList<CardHistoryDto> showSearchUserCardList(String userId, String keyword) throws SQLException {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        
	        ArrayList<CardHistoryDto> searchUserCardPayList = new ArrayList<>();
	        
	        try {
	            conn = dbUtil.getConnection();
	            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where user_id = ? and card_id = ? order by date desc;");
	            pstmt.setString(1, userId);
	            pstmt.setString(2, keyword);
	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	            	CardHistoryDto data = new CardHistoryDto();
	            	data.setCardId(rs.getLong("card_id"));
					data.setUserId(rs.getString("user_id"));
					data.setFranchisee(rs.getString("franchisee"));
					data.setPayment(rs.getInt("payment"));
					data.setBalance(rs.getInt("balance"));
					data.setDate(rs.getString("date"));
					data.setFCategory(rs.getInt("f_category"));
					data.setIsIns(rs.getInt("is_ins"));
					data.setInsMonth(rs.getInt("ins_month"));
					data.setCardType(rs.getString("card_type"));
	               
					searchUserCardPayList.add(data);
	            }
	        } catch(Exception e) {
	            e.printStackTrace();
	        } finally {
	            dbUtil.close(rs, pstmt, conn);
	        }
	        return searchUserCardPayList;
	    }
		
		
	//移대뱶�긽�뭹蹂� 寃곗젣�궡�뿭議고쉶
	public static ArrayList<CardHistoryDto> showSearchCardList(long keyword) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        long searchKeyword = keyword;
        
        ArrayList<CardHistoryDto> searchCardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where card_id = "+ searchKeyword +" order by date desc;");

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryDto data = new CardHistoryDto();
            	data.setCardId(rs.getLong("card_id"));
				data.setUserId(rs.getString("user_id"));
				data.setFranchisee(rs.getString("franchisee"));
				data.setPayment(rs.getInt("payment"));
				data.setBalance(rs.getInt("balance"));
				data.setDate(rs.getString("date"));
				data.setFCategory(rs.getInt("f_category"));
				data.setIsIns(rs.getInt("is_ins"));
				data.setInsMonth(rs.getInt("ins_month"));
				data.setCardType(rs.getString("card_type"));
               
                searchCardPayList.add(data);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return searchCardPayList;
    }
	

	
	//�썡蹂� 寃곗젣�궡�뿭議고쉶(1媛쒖썡,3媛쒖썡,吏곸젒�엯�젰)
		public static ArrayList<CardHistoryDto> showMonthlyCardList(String option) throws SQLException {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        
	        int searchPeriod = -(Integer.parseInt(option));
	        
	        ArrayList<CardHistoryDto> monthlyCardPayList = new ArrayList<>();
	        
	        try {
	            conn = dbUtil.getConnection();
	            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where date <= date_add(now(),interval "+ searchPeriod +" month) order by date desc;");

	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	            	CardHistoryDto data = new CardHistoryDto();
	            	data.setCardId(rs.getLong("card_id"));
					data.setUserId(rs.getString("user_id"));
					data.setFranchisee(rs.getString("franchisee"));
					data.setPayment(rs.getInt("payment"));
					data.setBalance(rs.getInt("balance"));
					data.setDate(rs.getString("date"));
					data.setFCategory(rs.getInt("f_category"));
					data.setIsIns(rs.getInt("is_ins"));
					data.setInsMonth(rs.getInt("ins_month"));
					data.setCardType(rs.getString("card_type"));
	               
					monthlyCardPayList.add(data);
	            }
	        } catch(Exception e) {
	            e.printStackTrace();
	        } finally {
	            dbUtil.close(rs, pstmt, conn);
	        }
	        return monthlyCardPayList;
	    }
		

		public String selectCardType(Long productId) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			Product product = null;
			ProductDto dto = null;
			ResultSet rs = null;
			String cardType = null;
			  try {
				    System.out.println("Loading driver...");
				    Class.forName("com.mysql.cj.jdbc.Driver");
				    System.out.println("Driver loaded!");
				  } catch (ClassNotFoundException e) {
				    throw new RuntimeException("Cannot find the driver in the classpath!", e);
				  }

			try {
				conn = dbUtil.getConnection();
				pstmt = conn.prepareStatement("select card_type from product where id=?;");
				pstmt.setLong(1, productId);

				rs = pstmt.executeQuery();
				if (rs.next()) {

					cardType = rs.getString("card_type");

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
			return cardType;
		}

		public Long countCardNum(Long accountId) throws SQLException {
			Connection conn = null;
			PreparedStatement pstmt = null;

			ResultSet rs = null;
			Long cardNum = 0L;
			  try {
				    System.out.println("Loading driver...");
				    Class.forName("com.mysql.cj.jdbc.Driver");
				    System.out.println("Driver loaded!");
				  } catch (ClassNotFoundException e) {
				    throw new RuntimeException("Cannot find the driver in the classpath!", e);
				  }


			try {
				System.out.println("insert 시작");
				conn = dbUtil.getConnection();
				pstmt = conn.prepareStatement("select count(account_id) from card where account_id=?;");
				pstmt.setLong(1, accountId);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					cardNum=rs.getLong("count(account_id)");
				}
				System.out.println("카드 갯수:"+cardNum);
				
			}  catch (Exception e) {
				e.printStackTrace();
				
				System.out.println("존재하지않는 계좌 아이디입니다.");
			}finally {
				try {
					dbUtil.close(rs);
					dbUtil.close(pstmt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return cardNum;
		}
		
		public void insertData(CardInsertDto data) throws SQLException {
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
				System.out.println("insert 시작");
				conn = dbUtil.getConnection();
				StringBuilder d = new StringBuilder();
				d.append("insert into card(product_id,issued_date,card_type,validity,agency,issuer,is_stopped,card_num,account_id) \n");
				d.append("values(?,?,?,?,?,?,?,?,?)");
				pstmt = conn.prepareStatement(d.toString());
				pstmt.setLong(1, data.getProductId());
				pstmt.setString(2, data.getIssuedDate());
				pstmt.setString(3, data.getCardType());
				pstmt.setString(4, data.getValidity());
				pstmt.setString(5, data.getAgency());
				pstmt.setString(6, data.getIssuer());
				pstmt.setInt(7, data.getIsStopped());
				pstmt.setString(8, data.getCardNum());
				pstmt.setLong(9, data.getAccountId());

				pstmt.executeUpdate();
				System.out.println("insert 실행");

			} finally {
				dbUtil.close(pstmt, conn);
			}
		}
		
		
		public CheckCardDaoToServiceDto selectCardByCardId(Long cardId) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			Card card = null;
			CheckCardDaoToServiceDto dto = null;
			ResultSet rs = null;
			  try {
				    Class.forName("com.mysql.cj.jdbc.Driver");
				  } catch (ClassNotFoundException e) {
				    throw new RuntimeException("Cannot find the driver in the classpath!", e);
				  }

			try {
				conn = dbUtil.getConnection();
				pstmt = conn.prepareStatement(
						"SELECT c.id,c.product_id,c.card_type,c.validity,c.agency,c.issuer,c.is_stopped,c.card_num, c.account_id,sc.discount FROM card c "
						+ "JOIN product p ON c.product_id = p.id "
						+ "JOIN sale_category sc ON p.category_id = sc.id "
						+ "WHERE c.product_id =?;");
				pstmt.setLong(1, cardId);

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
