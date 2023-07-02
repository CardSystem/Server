package dto;

import domain.Account;
import domain.Card;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CardDto {
	
	private Long id;
	private Long productId;
	private Long accountId;
	
	private String issuedDate;
	private String cardType;
	private String validity;
	private String agency;
	private String issuer;
	private Integer isStopped;
	private String cardNum;
	private Long totalPayment;
	
	public CardDto(Card card) {
		this.id = card.getId();
		this.productId = card.getProductId();
		this.accountId = card.getAccountId();
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