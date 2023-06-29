package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBUtil;
import domain.Card;
import domain.Product;
import dto.CardHistoryDTO;
import dto.CardInsertDto;
import dto.CheckCardDaoToServiceDto;
import dto.ProductDto;
import exception.BusinessException;
import exception.ErrorCode;

public class CardDao {
	static DBUtil dbUtil = DBUtil.getInstance();
	

		public String selectCardType(Long productId) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			Product product = null;
			ProductDto dto = null;
			ResultSet rs = null;
			String cardType = null;
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
		
		public CheckCardDaoToServiceDto selectCardByproductId(Long productId) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			Card card = null;
			CheckCardDaoToServiceDto dto = null;
			ResultSet rs = null;
			try {
				conn = dbUtil.getConnection();
				pstmt = conn.prepareStatement(
						"SELECT c.id,c.product_id,c.card_type,c.validity,c.agency,c.issuer,c.is_stopped,c.card_num, c.account_id,sc.discount FROM card c JOIN product p ON c.product_id = p.id JOIN sale_category sc ON p.category_id = sc.id WHERE c.product_id =?;");
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
		
		public CheckCardDaoToServiceDto selectCardByCardId(Long cardId) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			Card card = null;
			CheckCardDaoToServiceDto dto = null;
			ResultSet rs = null;
			try {
				conn = dbUtil.getConnection();
				pstmt = conn.prepareStatement(
						"SELECT c.id,c.product_id,c.card_type,c.validity,c.agency,c.issuer,c.is_stopped,c.card_num, c.account_id,sc.discount FROM card c JOIN product p ON c.product_id = p.id JOIN sale_category sc ON p.category_id = sc.id WHERE c.product_id =?;");
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
