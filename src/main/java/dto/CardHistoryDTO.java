package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardHistoryDto{
	private long id;
	private long cardId;
	private String userId;
	private String franchisee;
	private long payment;
	private long balance;
	private int isSuccess;
	private String date;
	private long fCategory;
	private int isIns;
	private int insMonth;
	private String cardType;
	
	public CardHistoryDto() {
		
	}

}