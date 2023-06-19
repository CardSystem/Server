package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.MonthlyCreditDto;
import domain.MonthlyCredit;
import dto.MonthlyCreditCreateDto;
import db.DBUtil;

public class MonthlyCreditDao {

	static DBUtil dbUtil = DBUtil.getInstance();
	
	public static void insertMonthlyCredit(MonthlyCreditCreateDto monthlyCreditCreateDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		MonthlyCredit monthlyCredit = monthlyCreditCreateDto.toEntity();
		
		try {
        	//3. sql문 실행
			conn = dbUtil.getConnection();
			StringBuilder d = new StringBuilder();
			d.append("insert into MonthlyCredit (user_id, card_id, discount, total, pay, is_payed, delay_days, delay_price) \n");
			d.append("values(?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt = conn.prepareStatement(d.toString());
			pstmt.setString(1, monthlyCredit.getUserId());
			pstmt.setLong(2, monthlyCredit.getCardId());
			pstmt.setLong(3, monthlyCredit.getDiscount());
			pstmt.setLong(4, monthlyCredit.getTotal());
			pstmt.setLong(5, monthlyCredit.getPay());
			pstmt.setInt(6, monthlyCredit.getIsPayed());
			pstmt.setInt(7, monthlyCredit.getDelayDays());
			pstmt.setLong(8, monthlyCredit.getDelayPrice());
			pstmt.executeUpdate();//insert실행
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	public static ArrayList<MonthlyCreditDto> selectAllMonthlyCrdit() throws SQLException {
		ArrayList<MonthlyCreditDto> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("select * from monthlycredit");
			rs = pstmt.executeQuery();//select실행
			
			while(rs.next()) {
				MonthlyCredit monthlyCredit = new MonthlyCredit();
				monthlyCredit.setId(rs.getLong("id"));
				monthlyCredit.setUserId(rs.getString("user_id"));
				monthlyCredit.setCardId(rs.getLong("card_id"));
				monthlyCredit.setDiscount(rs.getLong("discount"));
				monthlyCredit.setTotal(rs.getLong("total"));
				monthlyCredit.setPay(rs.getLong("pay"));
				monthlyCredit.setIsPayed(rs.getInt("is_payed"));
				monthlyCredit.setDelayDays(rs.getInt("delay_days"));
				monthlyCredit.setDelayPrice(rs.getLong("delay_price"));
				list.add(new MonthlyCreditDto(monthlyCredit));
			}
		}finally {
			dbUtil.close(rs, pstmt, conn);
		}
		
		return list;
	}
}