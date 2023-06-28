package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import dto.MonthlyCreditDto;
import dto.MonthlyCreditUpdateDto;
import domain.MonthlyCredit;
import dto.MonthlyCreditCreateDto;
import db.DBUtil;

public class MonthlyCreditDao {

	static DBUtil dbUtil = DBUtil.getInstance();
	
	public void insertMonthlyCredit(MonthlyCreditCreateDto monthlyCreditCreateDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		MonthlyCredit monthlyCredit = monthlyCreditCreateDto.toEntity();
		
		try {
        	//3. sql문 실행
			conn = dbUtil.getConnection();
			StringBuilder d = new StringBuilder();
			d.append("insert into MonthlyCredit (user_id, card_id, discount, total, pay, is_payed, delay_days, delay_price, start_date, end_date, title) \n");
			d.append("values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt = conn.prepareStatement(d.toString());
			pstmt.setString(1, monthlyCredit.getUserId());
			pstmt.setLong(2, monthlyCredit.getCardId());
			pstmt.setLong(3, monthlyCredit.getDiscount());
			pstmt.setLong(4, monthlyCredit.getTotal());
			pstmt.setLong(5, monthlyCredit.getPay());
			pstmt.setInt(6, monthlyCredit.getIsPayed());
			pstmt.setInt(7, monthlyCredit.getDelayDays());
			pstmt.setLong(8, monthlyCredit.getDelayPrice());
			pstmt.setTimestamp(9, Timestamp.valueOf(monthlyCredit.getStartDate().atStartOfDay()));
			pstmt.setTimestamp(10, Timestamp.valueOf(monthlyCredit.getEndDate().atStartOfDay()));
			pstmt.setString(11, monthlyCredit.getTitle());
			pstmt.executeUpdate();//insert실행
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	public MonthlyCreditDto selectMonthlyCrditByMonthlyCreditId(Long monthlyCreditId) throws SQLException {
		//ArrayList<MonthlyCreditDto> monthlyCreditList = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		MonthlyCredit monthlyCredit = null;
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("select * from monthly_credit where id=?");
			pstmt.setLong(1,monthlyCreditId);
			rs = pstmt.executeQuery();//select실행

			if(rs.next()) {				
				monthlyCredit = MonthlyCredit.builder()
						.id(rs.getLong("id"))
						.userId(rs.getString("user_id"))
						.cardId(rs.getLong("card_id"))
						.discount(rs.getLong("discount"))
						.total(rs.getLong("total"))
						.pay(rs.getLong("pay"))
						.isPayed(rs.getInt("is_payed"))
						.delayDays(rs.getInt("delay_days"))
						.delayPrice(rs.getLong("delay_price"))
						.startDate(rs.getTimestamp("start_date").toLocalDateTime().toLocalDate())
						.endDate(rs.getTimestamp("end_date").toLocalDateTime().toLocalDate())
						.title(rs.getString("title"))
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("존재하지않음");
		}

		finally {
			try {
				pstmt.close();
				dbUtil.close(pstmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//return monthlyCreditList;
		return new MonthlyCreditDto(monthlyCredit);
	}
	
	public ArrayList<MonthlyCreditDto> selectAllMonthlyCreditByCardId(Long cardId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		MonthlyCredit monthlyCredit = null;
		ArrayList<MonthlyCreditDto> monthlyCreditList = new ArrayList<>();
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("select * from monthly_credit where id=?");
			pstmt.setLong(1,cardId);
			rs = pstmt.executeQuery();//select실행

			while(rs.next()) {
				monthlyCredit = MonthlyCredit.builder()
						.id(rs.getLong("id"))
						.userId(rs.getString("user_id"))
						.cardId(rs.getLong("card_id"))
						.discount(rs.getLong("discount"))
						.total(rs.getLong("total"))
						.pay(rs.getLong("pay"))
						.isPayed(rs.getInt("is_payed"))
						.delayDays(rs.getInt("delay_days"))
						.delayPrice(rs.getLong("delay_price"))
						.startDate(rs.getTimestamp("start_date").toLocalDateTime().toLocalDate())
						.endDate(rs.getTimestamp("end_date").toLocalDateTime().toLocalDate())
						.title(rs.getString("title"))
						.build();
				
				monthlyCreditList.add(new MonthlyCreditDto(monthlyCredit));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("존재하지않음");
		}

		finally {
			try {
				pstmt.close();
				dbUtil.close(pstmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//return monthlyCreditList;
		return monthlyCreditList;
	}
	
	// 명세서 정산 업데이트
	public void updateMonthlyCreditByMonthlyCreditId(MonthlyCreditUpdateDto monthlyCreditUpdateDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("update monthly_credit set "
					+ "is_payed=?, delay_days=?, delay_price=?, end_date=? where id=?");
			pstmt.setInt(1,monthlyCreditUpdateDto.getIsPayed());
			pstmt.setInt(1,monthlyCreditUpdateDto.getDelayDays());
			pstmt.setLong(1,monthlyCreditUpdateDto.getDelayPrice());
			pstmt.setTimestamp(1,Timestamp.valueOf(monthlyCreditUpdateDto.getEndDate().atStartOfDay()));
			pstmt.setLong(1,monthlyCreditUpdateDto.getId());
			rs = pstmt.executeQuery();//select실행

			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("해당 명세서가 존재하지 않습니다.");
		}

		finally {
			try {
				pstmt.close();
				dbUtil.close(pstmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}