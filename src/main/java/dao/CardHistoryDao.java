package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;



import db.DBUtil;
import dto.CardHistoryDto;


public class CardHistoryDao {
	static DBUtil dbUtil = DBUtil.getInstance();
	
	public static ArrayList<CardHistoryDto> showPayCardList() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CardHistoryDto> cardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select id, card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history order by date asc;");

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryDto data = new CardHistoryDto();
            	data.setId(rs.getLong("id"));
            	data.setCardId(rs.getLong("card_id"));
				data.setUserId(rs.getString("user_id"));
				data.setFranchisee(rs.getString("franchisee"));
				data.setPayment(rs.getInt("payment"));
				data.setBalance(rs.getInt("balance"));
				data.setDate(rs.getString("date"));
				data.setFCategory(rs.getInt("f_category"));
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
	
	
	public static ArrayList<CardHistoryDto> showSearchUidCardList(String keyword) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        ArrayList<CardHistoryDto> searchUidCardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select id, card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where user_id = ? order by date desc;");
            pstmt.setString(1, keyword);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryDto data = new CardHistoryDto();
            	data.setId(rs.getLong("id"));
            	data.setCardId(rs.getLong("card_id"));
				data.setUserId(rs.getString("user_id"));
				data.setFranchisee(rs.getString("franchisee"));
				data.setPayment(rs.getInt("payment"));
				data.setBalance(rs.getInt("balance"));
				data.setDate(rs.getString("date"));
				data.setFCategory(rs.getInt("f_category"));
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
	
		public static ArrayList<CardHistoryDto> showSearchUserCardList(String userId, long keyword) throws SQLException {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        
	        ArrayList<CardHistoryDto> searchUserCardPayList = new ArrayList<>();
	        
	        try {
	            conn = dbUtil.getConnection();
	            pstmt = conn.prepareStatement("select id, card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where user_id = ? and card_id = ? order by date desc;");
	            pstmt.setString(1, userId);
	            pstmt.setLong(2, keyword);
	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	            	CardHistoryDto data = new CardHistoryDto();
	            	data.setId(rs.getLong("id"));
	            	data.setCardId(rs.getLong("card_id"));
					data.setUserId(rs.getString("user_id"));
					data.setFranchisee(rs.getString("franchisee"));
					data.setPayment(rs.getInt("payment"));
					data.setBalance(rs.getInt("balance"));
					data.setDate(rs.getString("date"));
					data.setFCategory(rs.getInt("f_category"));
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
		
		
	public static ArrayList<CardHistoryDto> showSearchCardList(long keyword) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        long searchKeyword = keyword;
        
        ArrayList<CardHistoryDto> searchCardPayList = new ArrayList<>();
        
        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement("select id, card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where card_id = "+ searchKeyword +" order by date desc;");

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CardHistoryDto data = new CardHistoryDto();
            	data.setId(rs.getLong("id"));
            	data.setCardId(rs.getLong("card_id"));
				data.setUserId(rs.getString("user_id"));
				data.setFranchisee(rs.getString("franchisee"));
				data.setPayment(rs.getInt("payment"));
				data.setBalance(rs.getInt("balance"));
				data.setDate(rs.getString("date"));
				data.setFCategory(rs.getInt("f_category"));
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
	

		public static ArrayList<CardHistoryDto> showMonthlyCardList(String option) throws SQLException {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        ArrayList<CardHistoryDto> monthlyCardPayList = new ArrayList<>();
	        
	        if (!option.isEmpty()) { 
		        int searchPeriod = -(Integer.parseInt(option));
		        
		        try {
		            conn = dbUtil.getConnection();
		            pstmt = conn.prepareStatement("select id, card_id, user_id, franchisee, payment, balance ,date, f_category, is_ins,ins_month,card_type from card_history where date between date_add(now(),interval "+searchPeriod +" month) and now() order by date desc;");
	
		            rs = pstmt.executeQuery();
		            while (rs.next()) {
		            	CardHistoryDto data = new CardHistoryDto();
		            	data.setId(rs.getLong("id"));
		            	data.setCardId(rs.getLong("card_id"));
						data.setUserId(rs.getString("user_id"));
						data.setFranchisee(rs.getString("franchisee"));
						data.setPayment(rs.getInt("payment"));
						data.setBalance(rs.getInt("balance"));
						data.setDate(rs.getString("date"));
						data.setFCategory(rs.getInt("f_category"));
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
		    }
	        return monthlyCardPayList;
		}
		


}