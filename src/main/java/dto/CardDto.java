package dto;

import domain.Account;
import domain.Cards;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CardDto {
	
	private Long cardId;
	
	private Account account;
	private String issuedDate;
	private String cardType;
	private String validity;
	private String agency;
	private String issuer;
	private Integer isStopped;
	private String cardNum;
	private Long totalPayment;
	
	public CardDto(Cards card) {
		this.cardId = card.getCardId();
		this.account = card.getAccount();
		this.issuedDate = card.getIssuedDate();
		this.cardType = card.getCardType();
		this.validity = card.getValidity();
		this.agency = card.getValidity();
		this.issuer = card.getIssuer();
		this.isStopped = card.getIsStopped();
		this.cardNum = card.getCardNum();
		this.totalPayment = card.getTotalPayment();
	}
}