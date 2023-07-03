package dto;

import java.time.LocalDate;

import domain.MonthlyCredit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyCreditDto {
	private Long id;
	private String userId;
	private Long cardId;
	private Long discount;
	private Long total;
	private Long pay;
	private Integer isPayed;
	private int delayDays;
	private Long delayPrice;
	private LocalDate startDate;
	private LocalDate endDate;
	private String title;
	
	
	public MonthlyCreditDto(MonthlyCredit entity) {
		this.id = entity.getId();
		this.userId = entity.getUserId();
		this.cardId = entity.getCardId();
		this.discount = entity.getDiscount();
		this.total = entity.getTotal();
		this.pay = entity.getPay();
		this.isPayed = entity.getIsPayed();
		this.delayDays = entity.getDelayDays();
		this.delayPrice = entity.getDelayPrice();
		this.startDate = entity.getStartDate();
		this.endDate = entity.getEndDate();
		this.title = entity.getTitle();
	}
}