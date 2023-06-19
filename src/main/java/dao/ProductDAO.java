package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DBUtil;
import domain.Product;
import domain.ProductDTO;

public class ProductDAO {
	private static DBUtil dbUtil = DBUtil.getInstance();
	private static ProductDAO dao = new ProductDAO();
	private Connection conn = null;
	private PreparedStatement ps = null;

	public static ProductDAO getInstance() {
		return dao;
	}

	public Long selectOneProduct(Long idx) throws SQLException {
		//Long result = null;
		try {
			conn = dbUtil.getConnection();
			final String query = "SELECT id FROM product WHERE id = ?";
			ps = conn.prepareStatement(query);
			ps.setLong(1, idx);
			
			ps.execute();
			//ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idx;
	}

	// 1. 카드 등록 dao
	public void register(Product product) throws SQLException {
		try {
			conn = dbUtil.getConnection();
			final String query = "insert into product(card_name, card_type, card_limit, category_id) values(?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, product.getCardName());
			ps.setString(2, product.getCardType());
			ps.setLong(3, product.getCardLimit());
			ps.setLong(4, product.getCategoryId());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbUtil.close(ps, conn);
		}
	}

	public void update(Long idx, ProductDTO.UpdateProduct dto) throws SQLException {

		try {
			conn = dbUtil.getConnection();
			final String query = "update product set card_name=?, card_type=?, card_limit=?, category_id=? where id=?";
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
			dbUtil.close(ps, conn);
		}

	}

	public void delete(Long idx) {
		try {
			conn = dbUtil.getConnection();
			ps = conn.prepareStatement("delete from product where id = ?");
			ps.setLong(1, idx);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(ps, conn);
		}
	}

}
