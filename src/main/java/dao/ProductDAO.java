package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;



import db.DBUtil;
import domain.Product;
import domain.ProductDTO;
import domain.ProductResponseDTO;

public class ProductDAO {
	private static DBUtil dbUtil = DBUtil.getInstance();
	private static ProductDAO dao = new ProductDAO();
	private Connection conn = null;
	private PreparedStatement ps = null;

	
	public static ProductDAO getInstance() {
		return dao;
	}

	public Long selectOneProduct(final Long idx) throws SQLException, IllegalAccessException {
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
				throw new IllegalAccessException("일치하는 카드를 찾을 수 없슨니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbUtil.close(ps, conn);
		}
		return result;
	}

	public List<ProductResponseDTO> getProdcut() {
		List<ProductResponseDTO> list = new ArrayList<>();
						
		try {
			conn = dbUtil.getConnection();
			final String query = "SELECT * FROM product ORDER BY id DESC";
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			
			while (rs.next()) {
				Product product = Product.builder()
						.productId(rs.getLong("id"))
						.cardName(rs.getString("card_name"))
						.cardLimit(rs.getLong("card_limit"))
						.cardType(rs.getString("card_type"))
						.categoryId(rs.getLong("category_id"))
						.build();
				
				ProductResponseDTO dto = new ProductResponseDTO();
				dto.setProductId(product.getCategoryId());
				dto.setCardName(product.getCardName());
				dto.setCardType(product.getCardType());
				dto.setCardLimit(product.getCardLimit());
				dto.setCategoryId(product.getCategoryId());
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("존재하지 않는 카드 상품");
		} finally {
			dbUtil.close(ps, conn);
		}
		return list;
	}

	
	public void register(ProductDTO.RequestProduct dto) throws SQLException {
		try {
			
			conn = dbUtil.getConnection();
			final String query = "insert into product(card_name, card_type, card_limit, category_id) values(?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, dto.getCardName());
			ps.setString(2, dto.getCardType());
			ps.setLong(3, dto.getCardLimit());
			ps.setLong(4, dto.getCategoryId());
			
			dto.toEntity();
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbUtil.close(ps, conn);
		}
	}

	public void update(Long idx, ProductDTO.RequestProduct dto) throws SQLException {

		try {
			conn = dbUtil.getConnection();
			final String query = "update product set card_name=?, card_type=?, card_limit=?, category_id=? where id=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, dto.getCardName());
			ps.setString(2, dto.getCardType());
			ps.setLong(3, dto.getCardLimit());
			ps.setLong(4, dto.getCategoryId());
			ps.setLong(5, idx);
			
			dto.toEntity();
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(ps, conn);
		}

	}

	public void delete(Long idx) {
		try {
			conn = dbUtil.getConnection();
			ps = conn.prepareStatement("delete from product where id = ?");
			ps.setLong(1, idx);
			
			Product.builder()
					.productId(idx)
					.build();
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(ps, conn);
		}
	}

}
