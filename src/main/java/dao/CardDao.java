package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBUtil;
import domain.Card;
import domain.Product;
import dto.CardDto;
import dto.CardResponseDto;
import dto.CardHistoryResponseDto;
import dto.CardInsertDto;
import dto.CheckCardDaoToServiceDto;
import dto.CreditCardDaoToServiceDto;
import dto.ProductDto;
import exception.BusinessException;
import exception.ErrorCode;

public class CardDao {
	static DBUtil dbUtil = DBUtil.getInstance();
private static CardDao dao = new CardDao();
	
	public static CardDao getInstance() {
		return dao;
	}
	
	
	// 하나의 카드 발급 내역 조회
		public CardDto selectOneCardByCardId(Long cardId) {
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
				
	public static ArrayList<CardHistoryResponseDto> showPayCardList() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CardHistoryResponseDto> cardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history order by date asc;");

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryResponseDto data = new CardHistoryResponseDto();
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
	
	public static ArrayList<CardHistoryResponseDto> showRecentPayCardList() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CardHistoryResponseDto> recentCardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history order by date desc;");

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryResponseDto data = new CardHistoryResponseDto();
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
	
	public static ArrayList<CardHistoryResponseDto> showSearchUidCardList(String keyword) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        ArrayList<CardHistoryResponseDto> searchUidCardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where user_id = ? order by date desc;");
            pstmt.setString(1, keyword);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryResponseDto data = new CardHistoryResponseDto();
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
	
	//占쎈립 user揶쏉옙 占쎈꺖占쎌�占쎈립燁삳�諭� 野껉퀣�젫占쎄땀占쎈열鈺곌퀬�돳
		public static ArrayList<CardHistoryResponseDto> showSearchUserCardList(String userId, String keyword) throws SQLException {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        
	        ArrayList<CardHistoryResponseDto> searchUserCardPayList = new ArrayList<>();
	        
	        try {
	            conn = dbUtil.getConnection();
	            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where user_id = ? and card_id = ? order by date desc;");
	            pstmt.setString(1, userId);
	            pstmt.setString(2, keyword);
	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	            	CardHistoryResponseDto data = new CardHistoryResponseDto();
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
		
		
	//燁삳�諭띰옙湲쏙옙萸배퉪占� 野껉퀣�젫占쎄땀占쎈열鈺곌퀬�돳
	public static ArrayList<CardHistoryResponseDto> showSearchCardList(long keyword) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        long searchKeyword = keyword;
        
        ArrayList<CardHistoryResponseDto> searchCardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where card_id = "+ searchKeyword +" order by date desc;");

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryResponseDto data = new CardHistoryResponseDto();
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
	

	
	//占쎌뜞癰귨옙 野껉퀣�젫占쎄땀占쎈열鈺곌퀬�돳(1揶쏆뮇�뜞,3揶쏆뮇�뜞,筌욊낯�젔占쎌뿯占쎌젾)
		public static ArrayList<CardHistoryResponseDto> showMonthlyCardList(String option) throws SQLException {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        
	        int searchPeriod = -(Integer.parseInt(option));
	        
	        ArrayList<CardHistoryResponseDto> monthlyCardPayList = new ArrayList<>();
	        
	        try {
	            conn = dbUtil.getConnection();
	            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where date <= date_add(now(),interval "+ searchPeriod +" month) order by date desc;");

	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	            	CardHistoryResponseDto data = new CardHistoryResponseDto();
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
				System.out.println("議댁옱�븯吏��븡�쓬");
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
				System.out.println("insert �떆�옉");
				conn = dbUtil.getConnection();
				pstmt = conn.prepareStatement("select count(account_id) from card where account_id=?;");
				pstmt.setLong(1, accountId);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					cardNum=rs.getLong("count(account_id)");
				}
				System.out.println("移대뱶 媛��닔:"+cardNum);
				
			}  catch (Exception e) {
				e.printStackTrace();
				
				System.out.println("議댁옱�븯吏��븡�뒗 怨꾩쥖 �븘�씠�뵒�엯�땲�떎.");
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
				System.out.println("insert �떆�옉");
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
				System.out.println("insert �떎�뻾");

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
					throw new BusinessException(ErrorCode.UNABLE_CARDNUM, "議댁옱�븯吏� �븡�뒗 移대뱶踰덊샇�엯�땲�떎.");
				}

			} catch (BusinessException | SQLException e) {
				System.out.println("李얠쓣 �닔 �뾾�뒗 移대뱶嫄곕굹 怨꾩쥖�엯�땲�떎: " + e.getMessage());
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

		public List<CardResponseDto> showCardList() throws SQLException {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        List<CardResponseDto> cardList = new ArrayList<>();

	        try {
	            conn = dbUtil.getConnection();
	            pstmt = conn.prepareStatement("SELECT * FROM card");
	            rs = pstmt.executeQuery();

	            while (rs.next()) {
	            	Card card = Card.builder()
	                		.id(rs.getLong("id"))
	                		.productId(rs.getLong("product_id"))
	                		.accountId(rs.getLong("account_id"))
	                		.cardNum(rs.getString("card_num"))
	                		.issuedDate(rs.getString("issued_date"))
	                		.agency(rs.getString("agency"))
	                		.issuer(rs.getString("issuer"))
	                		.cardType(rs.getString("card_type"))
	                		.validity(rs.getString("validity"))
	                		.isStopped(rs.getInt("is_stopped"))
	                		.totalPayment(rs.getLong("total_payment"))
	                		.build();

	                cardList.add(CardResponseDto.of(card));
	            }
	        } catch(Exception e) {
	            e.printStackTrace();
	        } finally {
	            dbUtil.close(rs, pstmt, conn);
	        }

	        return cardList;
	    }
		
//		public void changeIsStopped(int isStopped, long id) throws SQLException {
//			Connection conn = null;
//			PreparedStatement pstmt = null;
//			try {
//				conn = dbUtil.getConnection();
//	            pstmt = conn.prepareStatement("update card set is_stopped=? where id=?");
//	            // 이부분 처리 애매함 -> boolean인지 int인지
//				pstmt.setInt(1, isStopped);
//				pstmt.setLong(2, id);
//				pstmt.executeUpdate();//insert실행
//			} catch(Exception e) {
//		         e.printStackTrace();
//		    } finally {
//				dbUtil.close(pstmt, conn);
//			}
//		}

		public long getAccountIdByCardId(long id) {
			Connection conn = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    long accountId = 0;
		    try {
		        conn = dbUtil.getConnection();
		        pstmt = conn.prepareStatement("SELECT account_id FROM card WHERE id = ?");
		        pstmt.setLong(1, id);
		        rs = pstmt.executeQuery();

		        if (rs.next()) {
		            accountId = rs.getLong("account_id");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        dbUtil.close(rs, pstmt, conn);
		    }
			return accountId;
		}
		
		
	
}
