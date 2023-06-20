package domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckCardHistory {
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
	
	public CheckCardHistory(Long cardId,String userId,String franchisee,Long payment,Long balance,
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
}