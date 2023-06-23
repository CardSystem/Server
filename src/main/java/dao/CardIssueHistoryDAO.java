package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBUtil;
import dto.CardIssueHistoryDTO;

public class CardIssueHistoryDAO {
	static DBUtil dbUtil = DBUtil.getInstance();

	public static ArrayList<CardIssueHistoryDTO> showCardList() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CardIssueHistoryDTO> cardList = new ArrayList<>();

        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM cards");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CardIssueHistoryDTO card = new CardIssueHistoryDTO();
                card.setId(rs.getLong("id"));
                card.setCardId(rs.getString("card_id"));
                card.setCardNum(rs.getString("card_num"));
                card.setIssuedDate(rs.getString("issued_date"));
                card.setAgency(rs.getString("agency"));
                card.setIssuer(rs.getString("issuer"));
                card.setCardType(rs.getString("card_type"));
                card.setValidity(rs.getString("validity"));
                card.setIsStopped(rs.getInt("is_stopped"));

                cardList.add(card);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }

        return cardList;
    }
	
	public static void changeIsStopped(int isStopped, long id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("update cards set is_stopped=? where id=?");
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
}
