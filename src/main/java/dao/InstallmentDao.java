package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import db.DBUtil;
import domain.Installment;
import dto.InstallmentCreateDto;
import dto.InstallmentDto;
import dto.InstallmentUpdateDto;

public class InstallmentDao {
	static DBUtil dbUtil = DBUtil.getInstance();
	
	// 할부 결제 추가
	public void insertInstallment(InstallmentCreateDto data) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dbUtil.getConnection();
			StringBuilder d = new StringBuilder();
			
			d.append(
					"insert into installment(user_id, card_id, ins_month, remain_month, payment, is_inspayed, payment_date) \n");
			d.append("?,?,?,?,?,?,?");
			
			pstmt.getConnection().prepareStatement(d.toString());
			pstmt.setString(1,data.getUserId());
			pstmt.setLong(2,data.getCardId());
			pstmt.setInt(3,data.getInsMonth());
			pstmt.setInt(4,data.getRemainMonth());
			pstmt.setLong(5,data.getPayment());
			pstmt.setInt(6,data.getIsInspayed());
			pstmt.setTimestamp(7,Timestamp.valueOf(data.getPaymentDate().atStartOfDay()));
			
			pstmt.executeUpdate();
			System.out.println("할부 테이블에 insert실행하였습니다.");
		} finally {
			dbUtil.close(pstmt,conn);
		}
	}
	
	// 할부 내역 업데이트 (매월 정기 결제일에 진행)
	public void updateInstallment(InstallmentUpdateDto installmentUpdateDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("update installment set reamin_month = ?, is_inspayed=? where id=?");
			pstmt.setInt(1, installmentUpdateDto.getRemainMonth());
			pstmt.setInt(2, installmentUpdateDto.getIsInspayed());
			pstmt.setLong(3, installmentUpdateDto.getId());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("존재하지 않는 할부 내역입니다.");
		} finally {
			try {
				dbUtil.close(pstmt,conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// cardId로 해당카드로 결제한 모든 할부내역 반환
	public ArrayList<InstallmentDto> selectAllNonIsInspayedInstallmentByCardId(Long cardId) throws SQLException {
		ArrayList<InstallmentDto> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement("select * from installment where id=?, remain_month >0");
			pstmt.setLong(1, cardId);
			rs = pstmt.executeQuery();//select실행
			
			while(rs.next()) {
				Installment installment = Installment.builder()
						.id(rs.getLong("id"))
						.userId(rs.getString("user_id"))
						.cardID(rs.getLong("card_id"))
						.insMonth(rs.getInt("ins_month"))
						.remainMonth(rs.getInt("remain_month"))
						.payment(rs.getLong("payment"))
						.isInspayed(rs.getInt("is_inspayed"))
						.paymentDate(rs.getTimestamp("payment_date").toLocalDateTime().toLocalDate())
						.build();
				
				list.add(new InstallmentDto(installment));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("cardId :"  + cardId + " 에 해당하는 데이터가 존재하지 않습니다.");
		} finally {
			try {
				dbUtil.close(rs, pstmt, conn);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		return list;
	}
	
	
}