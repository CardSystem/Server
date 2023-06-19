package dto;

import domain.Account;
import domain.Card;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CardDto {
	
	private Account account;
	private String cardName;
	private String issuedDate;
	private String cardType;
	private String validity;
	private String agency;
	private String issuer;
	private Boolean isStopped;
	private String cardNum;
	
	public CardDto(Card card) {
		this.account = card.getAccount();
		this.cardName = card.getCardName();
		this.issuedDate = card.getIssuedDate();
		this.cardType = card.getCardType();
		this.validity = card.getValidity();
		this.agency = card.getValidity();
		this.issuer = card.getIssuer();
		this.isStopped = card.getIsStopped();
		this.cardNum = card.getCardNum();
	}
}