package dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import domain.CreditCardHistory;

@Getter
@Setter
public class CreditCardHistoryDto {
	private Long id;
	private Long cardId;
	private String userId;
	private String franchisee;
	private Long payment;
	private Long balance;
	private Integer isSuccess;
	private LocalDateTime date;
	private Long fCategory;
	private Integer isIns;
	private int insMonth;
	private String cardType;
	
	
	public CreditCardHistoryDto(CreditCardHistory creditCardHistory) {
	
		this.id = creditCardHistory.getId();
		this.cardId = creditCardHistory.getCardId();
		this.userId = creditCardHistory.getUserId();
		this.franchisee = creditCardHistory.getFranchisee();
		this.payment = creditCardHistory.getPayment();
		this.balance = creditCardHistory.getBalance();
		this.isSuccess = creditCardHistory.getIsSuccess();
		this.date = creditCardHistory.getDate();
		this.fCategory = creditCardHistory.getFCategory();
		this.isIns = creditCardHistory.getIsIns();
		this.insMonth = creditCardHistory.getInsMonth();
		this.cardType = creditCardHistory.getCardType();
		
		
	}
}