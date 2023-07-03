package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.DBUtil;
import domain.Account;
import domain.Product;
import dto.AccountDto;
import dto.ProductCreateDto;
import dto.ProductDto;
import dto.ProductRequestDto;
import dto.ProductResponseDto;
import exception.BusinessException;
import exception.ErrorCode;

public class ProductDao {
	private static DBUtil dbUtil = DBUtil.getInstance();
	private static ProductDao dao = new ProductDao();
	private Connection conn = null;
	private PreparedStatement ps = null;
	
	public static ProductDao getInstance() {
		return dao;
	}


	public Long selectOneProduct(final Long idx) throws SQLException{
		Long result = null;
		try {
			conn = dbUtil.getConnection();
			final String query = "SELECT id FROM product WHERE id = ?";
			ps = conn.prepareStatement(query);
			ps.setLong(1, idx);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				result = rs.getLong("id");
			} else
				throw new BusinessException(ErrorCode.UNABLE_PRODUCTNUM, "일치하는 정보가 없습니다.");

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbUtil.close(ps, conn);
		}
		return result;
	}
	
	public Optional<String> selectProductCardName(final String cardName) {
		String str = null;
		try {
			conn = dbUtil.getConnection();
			final String query = "SELECT card_name FROM product WHERE card_name = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, cardName);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				str = rs.getString("card_name");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			dbUtil.close(ps,conn);
		}
		return Optional.ofNullable(str);
	}
	
	//Card 테이블에서 조인된 테이블 내 데이터 가공을 위한 dao
	public List<ProductResponseDto> selectProductOfCard() {
		List<ProductResponseDto> searchProduct = new ArrayList<>();
		try {
			conn = dbUtil.getConnection();
			final String query = "SELECT p.id AS product_id, p.card_name, p.card_limit, p.card_type, "
					+ "s.id AS category_id, s.category_name, s.discount AS category_id, s.category_name, s.discount "
					+ "FROM product p "
					+ "JOIN sale_category s ON p.category_id = s.id"
					+ "JOIN card c ON p.id = c.product_id";
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ProductResponseDto dto = ProductResponseDto.builder()
						.id(rs.getLong("product_id"))
						.cardName(rs.getString("card_name"))
						.cardType(rs.getString("card_type"))
						.cardLimit(rs.getLong("card_limit"))
						.categoryId(rs.getLong("category_id"))
						.build();
				searchProduct.add(dto);
			}
		}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				dbUtil.close(ps, conn);
			}
			return searchProduct;
		
	}

	public Optional<List<ProductResponseDto>> getProductList(){
	      List<ProductResponseDto> list = new ArrayList<>();
	      
	      try {
	         conn = dbUtil.getConnection();
	         final String query = "SELECT p.id AS product_id, p.card_name, p.card_limit, p.card_type, s.id AS category_id, s.category_name, s.discount FROM product p JOIN sale_category s ON p.category_id = s.id ORDER BY p.id DESC";
	         ps = conn.prepareStatement(query);
	         ResultSet rs = ps.executeQuery();
	         while (rs.next()) {
	            Product product = Product.builder()
	                  .id(rs.getLong("product_id"))
	                  .cardName(rs.getString("card_name"))
	                  .cardType(rs.getString("card_type"))
	                  .cardLimit(rs.getLong("card_limit"))
	                  .categoryId(rs.getLong("category_id"))
	                  .build();
	            
	            String str = rs.getString("category_name");
	            Long num = rs.getLong("discount");
	            
	            
	            list.add(new ProductResponseDto(product.getId(), product.getCardName(), product.getCardType(), product.getCardLimit(), product.getCategoryId(), str, num));
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         dbUtil.close(ps, conn);
	      }
	      return Optional.ofNullable(list);
	   }

	public void registerProduct(final ProductRequestDto dto) throws SQLException {
		
		
		try {
			
			try {
			    Class.forName("com.mysql.cj.jdbc.Driver");
			  } catch (ClassNotFoundException e) {
			    throw new RuntimeException("Cannot find the driver in the classpath!", e);
			  }
			conn = dbUtil.getConnection();
			final String query = "INSERT INTO product(card_name, card_type, card_limit, category_id) VALUES (?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, dto.getCardName());
			ps.setString(2, dto.getCardType());
			ps.setLong(3, dto.getCardLimit());
			ps.setLong(4, dto.getCategoryId());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dto.toEntity();
			dbUtil.close(ps, conn);
		}

	}

	public void updateProduct(final Long idx, final ProductRequestDto dto) throws SQLException {
		try {
			conn = dbUtil.getConnection();
			final String query = "UPDATE product SET card_name=?, card_type=?, card_limit=?, category_id=? where id=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, dto.getCardName());
			ps.setString(2, dto.getCardType());
			ps.setLong(3, dto.getCardLimit());
			ps.setLong(4, dto.getCategoryId());
			ps.setLong(5, idx);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dto.toEntity();
			dbUtil.close(ps, conn);
		}
	}

	public void deleteProduct(final Long idx) throws SQLException{
		try {
			conn = dbUtil.getConnection();
			ps = conn.prepareStatement("DELETE FROM product WHERE id = ?");
			ps.setLong(1, idx);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(ps, conn);
		}
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