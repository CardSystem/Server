package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBUtil;
import domain.Product;
import dto.CardInsertDto;
import dto.ProductDto;

public class InsertCardDao {
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

	public void insertData(CardInsertDto data) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			System.out.println("insert 시작");
			conn = dbUtil.getConnection();
			StringBuilder d = new StringBuilder();
			d.append(
					"insert into cards(card_id,issued_date,card_type,validity,agency,issuer,is_stopped,card_num,account_id) \n");
			d.append("values(?,?,?,?,?,?,?,?,?)");
			pstmt = conn.prepareStatement(d.toString());
			pstmt.setLong(1, data.getCardId());
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

}
