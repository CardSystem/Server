package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardIssueHistoryDTO {
	private long id;
	private long accountId;
	private String cardId;
	private String issuedDate;
	private String cardType;
	private String validity;
	private String agency;
	private String issuer;
	private int isStopped;
	private String cardNum;

	public CardIssueHistoryDTO() {}

}
