package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBUtil;
import domain.Product;
public class ProductDao {
static DBUtil dbUtil = DBUtil.getInstance();

public static void insertData(Product product) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
        	//3. sql문 실행
			conn = dbUtil.getConnection();
			StringBuilder d = new StringBuilder();
			d.append("insert into product(card_name,card_type,card_limit,category_id) \n");
			d.append("values(?,?,?,?)");
			pstmt = conn.prepareStatement(d.toString());
			pstmt.setString(1,product.getCardName());
			pstmt.setString(2, product.getCardType());
			pstmt.setLong(3, product.getCardLimit());
			pstmt.setLong(4,product.getCategoryId());

			pstmt.executeUpdate();//insert실행
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	public static ArrayList<Product> selectAllData() throws SQLException {
		ArrayList<Product> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("select * from product");
			rs = pstmt.executeQuery();//select실행
			
			while(rs.next()) {
				Product data = new Product();
				data.setId(rs.getLong("id"));
				data.setCardName(rs.getString("category_name"));
				data.setCardType(rs.getString("card_type"));
				data.setCardLimit(rs.getLong("card_limit"));
				data.setCategoryId(rs.getLong("category_id"));

				list.add(data);
			}
		}finally {
			dbUtil.close(rs, pstmt, conn);
		}
		
		return list;
	}
}
