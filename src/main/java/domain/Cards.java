package domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cards {

	private Long cardId;

	private Long id;

	private Account account;
	private String issuedDate;
	private String cardType;
	private String validity;
	private String agency;
	private String issuer;
	private Integer isStopped;
	private String cardNum;


}