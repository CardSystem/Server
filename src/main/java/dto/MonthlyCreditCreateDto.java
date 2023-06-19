package dto;

import domain.MonthlyCredit;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MonthlyCreditCreateDto {

	private String userId;
	private Long cardId;
	private Long discount;
	private Long total;
	private Long pay;
	private Integer isPayed;
	private int delayDays;
	private Long delayPrice;
	
	public MonthlyCredit toEntity() {
		
		MonthlyCredit monthlyCredit = new MonthlyCredit();

		monthlyCredit.setUserId(this.userId);
		monthlyCredit.setCardId(this.cardId);
		monthlyCredit.setDiscount(this.discount);
		monthlyCredit.setTotal(this.total);
		monthlyCredit.setPay(this.pay);
		monthlyCredit.setIsPayed(this.isPayed);
		monthlyCredit.setDelayDays(this.delayDays);
		monthlyCredit.setDelayPrice(this.delayPrice);
		
		return monthlyCredit;
	}
}