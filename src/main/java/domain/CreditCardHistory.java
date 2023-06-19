package domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardHistory {
	private Long id;
	private Long cardId;
	private String userId;
	private String franchisee;
	private Long payment;
	private Long balance;
	private Integer isSuccess;
	private LocalDateTime date=LocalDateTime.now();
	private Long fCategory;
	private Integer isIns;
	private int insMonth;
	private String cardType;
}