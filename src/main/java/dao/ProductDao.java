package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBUtil;
import domain.Account;
import domain.Product;
import dto.AccountDto;
import dto.ProductCreateDto;
import dto.ProductDto;

public class ProductDao {
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
	
	public static ProductDto selectProductByProductId(Long productId) throws SQLException {
		Product product=null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("select * from product where id=?");
			pstmt.setLong(1,productId);
			rs = pstmt.executeQuery();//select실행
			
			while(rs.next()) {
				product = Product.builder()
						.id(rs.getLong("id"))
						.cardName(rs.getString("card_name"))
						.cardType(rs.getString("card_type"))
						.cardLimit(rs.getLong("card_limit"))
						.categoryId(rs.getLong("category_id"))
						.build();
			
			}
		}finally {
			dbUtil.close(rs, pstmt, conn);
		}
		
		return new ProductDto(product);
	}
	
	public static void insertData(ProductCreateDto productCreateDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
        	//3. sql문 실행
			conn = dbUtil.getConnection();
			StringBuilder d = new StringBuilder();
			d.append("insert into product(card_name,card_type,card_limit,category_id) \n");
			d.append("values(?,?,?,?)");
			pstmt = conn.prepareStatement(d.toString());
			pstmt.setString(1,productCreateDto.getCardName());
			pstmt.setString(2, productCreateDto.getCardType());
			pstmt.setLong(3, productCreateDto.getCardLimit());
			pstmt.setLong(4,productCreateDto.getCategoryId());

			pstmt.executeUpdate();//insert실행
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	public static ArrayList<ProductDto> selectAllData() throws SQLException {
		ArrayList<ProductDto> productList = new ArrayList<>();
		System.out.println("dao 진입!");

		Product product=null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("select * from product");
			rs = pstmt.executeQuery();//select실행
			
			while(rs.next()) {
				product = Product.builder()
						.id(rs.getLong("id"))
						.cardName(rs.getString("card_name"))
						.cardType(rs.getString("card_type"))
						.cardLimit(rs.getLong("card_limit"))
						.categoryId(rs.getLong("category_id"))
						.build();
				

				productList.add(new ProductDto(product));
			}
		}finally {
			dbUtil.close(rs, pstmt, conn);
		}
		System.out.println("길이:"+productList.size());
		
		return productList;
	}
}
