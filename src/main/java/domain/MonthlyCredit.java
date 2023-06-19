package domain;

import lombok.Data;

@Data
public class MonthlyCredit {
	private Long id;
	private String userId;
	private Long cardId;
	private Long discount;
	private Long total;
	private Long pay;
	private Integer isPayed;
	private int delayDays;
	private Long delayPrice;
	

}