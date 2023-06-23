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
	
	public CreditCardHistoryCreateDto(Long cardId,String userId,String franchisee,Long payment,Long balance,
			Integer isSuccess,LocalDateTime date,Long fCategory,Integer isIns,int insMonth,String cardType)
	{
		this.cardId=cardId;
		this.userId=userId;
		this.franchisee=franchisee;
		this.payment=payment;
		this.balance=balance;
		this.isSuccess=isSuccess;
		this.date=date;
		this.fCategory=fCategory;
		this.isIns=isIns;
		this.insMonth=insMonth;
		this.cardType=cardType;
	}
	
	
	public CreditCardHistory toEntity() {
	
		return CreditCardHistory.builder()
				.id(null)
				.cardId(this.cardId)
				.userId(this.userId)
				.franchisee(this.franchisee)
				.payment(this.payment)
				.balance(this.balance)
				.isSuccess(this.isSuccess)
				.date(this.date)
				.fCategory(this.fCategory)
				.isIns(this.isIns)
				.insMonth(this.insMonth)
				.cardType(this.cardType)
				.build();
	}
}