package dto;

import java.time.LocalDateTime;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckCardRequestDto {

	private LocalDateTime date;
	private Long cardId;
	private String userId;
	private String franchisee;
	private Long payment;
	private Long fCategory;

	
	public CheckCardRequestDto(Long cardId,String userId,String franchisee,Long payment,Long fCategory,LocalDateTime date)
	{
		this.cardId=cardId;
		this.fCategory=fCategory;
		this.franchisee=franchisee;
		this.payment=payment;
		this.userId=userId;
		this.date=date;
	}

}