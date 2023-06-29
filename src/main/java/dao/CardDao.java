package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBUtil;
import dto.CardResponseDto;

public class CardDao {
	static DBUtil dbUtil = DBUtil.getInstance();
	private static CardDao dao = new CardDao();
	
	public static CardDao getInstance() {
		return dao;
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
                CardResponseDto dto = CardResponseDto.builder()
                		.id(rs.getLong("id"))
                		.cardId(rs.getString("card_id"))
                		.cardNum(rs.getString("card_num"))
                		.issuedDate(rs.getString("issued_date"))
                		.agency(rs.getString("agency"))
                		.issuer(rs.getString("issuer"))
                		.cardType(rs.getString("card_type"))
                		.validity(rs.getString("validity"))
                		.isStopped(rs.getInt("is_stopped"))
                		.build();

                cardList.add(dto);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }

        return cardList;
    }
	
	public void changeIsStopped(int isStopped, long id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("update card set is_stopped=? where id=?");
            // 이부분 처리 애매함 -> boolean인지 int인지
			pstmt.setInt(1, isStopped);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();//insert실행
		} catch(Exception e) {
	         e.printStackTrace();
	    } finally {
			dbUtil.close(pstmt, conn);
		}
	}

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
