package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DBUtil;
import domain.Product;

public class ProductDAO {
	private static DBUtil dbUtil = DBUtil.getInstance();
	private static ProductDAO dao = new ProductDAO();
	private Connection conn = null;
	private PreparedStatement ps = null;

	public static ProductDAO getInstance() {
		return dao;
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

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			dbUtil.close(ps, conn);
		}
	}

//	public void update(ProductDTO.RequestProduct dto)
//	public void delete(Product product)
}
