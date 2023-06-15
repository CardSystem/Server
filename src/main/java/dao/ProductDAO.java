package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DBUtil;
import domain.Product;

public class ProductDAO {
	private static DBUtil dbUtil = DBUtil.getInstance();

	public void insertProduct(Product product) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dbUtil.getConnection();
			String query = "insert into product(card_name, card_type, card_limit, category_id) values(?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, product.getCardName());
			ps.setString(2, product.getCardType());
			ps.setLong(3, product.getCardLimit());
			ps.setLong(4, product.getCategoryId());
			ps.executeUpdate();

		} finally {
			dbUtil.close(ps, conn);
		}
	}
}
