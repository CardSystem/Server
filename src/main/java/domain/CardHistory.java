package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardHistory {
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
}
