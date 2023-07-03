package dto;

import java.time.LocalDate;

import domain.MonthlyCredit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyCreditCreateDto {

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
	
	public MonthlyCredit toEntity() {
		
		return MonthlyCredit.builder()
				.id(null)
				.userId(this.userId)
				.cardId(this.cardId)
				.discount(this.discount)
				.total(this.total)
				.pay(this.pay)
				.isPayed(this.isPayed)
				.delayDays(this.delayDays)
				.delayPrice(this.delayPrice)
				.startDate(this.startDate)
				.endDate(this.endDate)
				.title(this.title)
				.build();
	}
}