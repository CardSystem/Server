package domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
	private LocalDate startDate;
	private LocalDate endDate;
	private String title;

}