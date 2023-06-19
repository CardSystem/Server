package dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import domain.CreditCardHistory;

@Getter
@Setter
public class CreditCardPaymentDto {
	private Long cardId;
	private String userId;
	private String franchisee;
	private Long payment;
	private Long fCategory;
	private int insMonth;
	
	
	public CreditCardPaymentDto(Long cardId,String userId,String franchisee,Long payment,Long fCategory, int insMonth) {
	
		this.cardId = cardId;
		this.userId = userId;
		this.franchisee = franchisee;
		this.payment = payment;
		this.fCategory = fCategory;	
		this.insMonth = insMonth;
	}
}