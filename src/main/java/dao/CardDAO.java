package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbutil.DBUtil;
import dto.CardHistoryDTO;

public class CardDAO {
	static DBUtil dbUtil = DBUtil.getInstance();
	public static ArrayList<CardHistoryDTO> showPayCardList() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CardHistoryDTO> cardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history order by date asc;");

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryDTO data = new CardHistoryDTO();
            	data.setCardId(rs.getLong("card_id"));
				data.setUserId(rs.getString("user_id"));
				data.setFranchisee(rs.getString("franchisee"));
				data.setPayment(rs.getInt("payment"));
				data.setBalance(rs.getInt("balance"));
				data.setDate(rs.getString("date"));
				data.setfCategory(rs.getInt("f_category"));
				data.setIsIns(rs.getInt("is_ins")); 
				data.setInsMonth(rs.getInt("ins_month"));
				data.setCardType(rs.getString("card_type"));
               
                cardPayList.add(data);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return cardPayList;
    }
	
	// 최근 결제순 
	public static ArrayList<CardHistoryDTO> showRecentPayCardList() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CardHistoryDTO> recentCardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history order by date desc;");

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryDTO data = new CardHistoryDTO();
            	data.setCardId(rs.getLong("card_id"));
				data.setUserId(rs.getString("user_id"));
				data.setFranchisee(rs.getString("franchisee"));
				data.setPayment(rs.getInt("payment"));
				data.setBalance(rs.getInt("balance"));
				data.setDate(rs.getString("date"));
				data.setfCategory(rs.getInt("f_category"));
				data.setIsIns(rs.getInt("is_ins"));
				data.setInsMonth(rs.getInt("ins_month"));
				data.setCardType(rs.getString("card_type"));
               
                recentCardPayList.add(data);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return recentCardPayList;
    }
	
	//한 user가 소유한카드 결제내역조회
	public static ArrayList<CardHistoryDTO> showSearchUidCardList(String keyword) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        ArrayList<CardHistoryDTO> searchUidCardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where user_id = ? order by date desc;");
            pstmt.setString(1, keyword);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryDTO data = new CardHistoryDTO();
            	data.setCardId(rs.getLong("card_id"));
				data.setUserId(rs.getString("user_id"));
				data.setFranchisee(rs.getString("franchisee"));
				data.setPayment(rs.getInt("payment"));
				data.setBalance(rs.getInt("balance"));
				data.setDate(rs.getString("date"));
				data.setfCategory(rs.getInt("f_category"));
				data.setIsIns(rs.getInt("is_ins"));
				data.setInsMonth(rs.getInt("ins_month"));
				data.setCardType(rs.getString("card_type"));
               
                searchUidCardPayList.add(data);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return searchUidCardPayList;
    }
	
	//한 user가 소유한카드 결제내역조회
		public static ArrayList<CardHistoryDTO> showSearchUserCardList(String userId, String keyword) throws SQLException {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        
	        ArrayList<CardHistoryDTO> searchUserCardPayList = new ArrayList<>();
	        
	        try {
	            conn = dbUtil.getConnection();
	            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where user_id = ? and card_id = ? order by date desc;");
	            pstmt.setString(1, userId);
	            pstmt.setString(2, keyword);
	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	            	CardHistoryDTO data = new CardHistoryDTO();
	            	data.setCardId(rs.getLong("card_id"));
					data.setUserId(rs.getString("user_id"));
					data.setFranchisee(rs.getString("franchisee"));
					data.setPayment(rs.getInt("payment"));
					data.setBalance(rs.getInt("balance"));
					data.setDate(rs.getString("date"));
					data.setfCategory(rs.getInt("f_category"));
					data.setIsIns(rs.getInt("is_ins"));
					data.setInsMonth(rs.getInt("ins_month"));
					data.setCardType(rs.getString("card_type"));
	               
					searchUserCardPayList.add(data);
	            }
	        } catch(Exception e) {
	            e.printStackTrace();
	        } finally {
	            dbUtil.close(rs, pstmt, conn);
	        }
	        return searchUserCardPayList;
	    }
		
		
	//카드상품별 결제내역조회
	public static ArrayList<CardHistoryDTO> showSearchCardList(long keyword) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        long searchKeyword = keyword;
        
        ArrayList<CardHistoryDTO> searchCardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where card_id = "+ searchKeyword +" order by date desc;");

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryDTO data = new CardHistoryDTO();
            	data.setCardId(rs.getLong("card_id"));
				data.setUserId(rs.getString("user_id"));
				data.setFranchisee(rs.getString("franchisee"));
				data.setPayment(rs.getInt("payment"));
				data.setBalance(rs.getInt("balance"));
				data.setDate(rs.getString("date"));
				data.setfCategory(rs.getInt("f_category"));
				data.setIsIns(rs.getInt("is_ins"));
				data.setInsMonth(rs.getInt("ins_month"));
				data.setCardType(rs.getString("card_type"));
               
                searchCardPayList.add(data);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }
        return searchCardPayList;
    }
	

	
	//월별 결제내역조회(1개월,3개월,직접입력)
		public static ArrayList<CardHistoryDTO> showMonthlyCardList(String option) throws SQLException {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        
	        String searchPeriod = option;
	        
	        ArrayList<CardHistoryDTO> monthlyCardPayList = new ArrayList<>();
	        
	        try {
	            conn = dbUtil.getConnection();
	            pstmt = conn.prepareStatement("select card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where date <= date_add(now(),interval '"+ searchPeriod + "' month) order by date desc;");

	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	            	CardHistoryDTO data = new CardHistoryDTO();
	            	data.setCardId(rs.getLong("card_id"));
					data.setUserId(rs.getString("user_id"));
					data.setFranchisee(rs.getString("franchisee"));
					data.setPayment(rs.getInt("payment"));
					data.setBalance(rs.getInt("balance"));
					data.setDate(rs.getString("date"));
					data.setfCategory(rs.getInt("f_category"));
					data.setIsIns(rs.getInt("is_ins"));
					data.setInsMonth(rs.getInt("ins_month"));
					data.setCardType(rs.getString("card_type"));
	               
					monthlyCardPayList.add(data);
	            }
	        } catch(Exception e) {
	            e.printStackTrace();
	        } finally {
	            dbUtil.close(rs, pstmt, conn);
	        }
	        return monthlyCardPayList;
	    }
	
}
