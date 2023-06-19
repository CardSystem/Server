package dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import domain.CreditCardHistory;

@Getter
@Setter
public class CreditCardHistoryCreateDto {

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
	
	
	public CreditCardHistory toEntity() {
	
		return CreditCardHistory.builder()
				.id(null)
				.cardId(cardId)
				.userId(userId)
				.franchisee(franchisee)
				.payment(payment)
				.balance(balance)
				.isSuccess(isSuccess)
				.date(date)
				.fCategory(fCategory)
				.isIns(isIns)
				.insMonth(insMonth)
				.cardType(cardType)
				.build();
	}
}