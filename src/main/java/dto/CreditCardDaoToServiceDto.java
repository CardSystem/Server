package dto;


import domain.Card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardDaoToServiceDto {

	
	private Long id;
	private Long cardId;
	private String cardType;
	private String validity;
	private String agency;
	private String issuer;
	private Integer isStopped;
	private String cardNum;
	private Long accountId;
	private Long discount;
	private Long categoryId;
	
	public CreditCardDaoToServiceDto(Card creditCard,Long accountId,Long discount, Long categoryId)
	{
		this.cardId=creditCard.getId();
		this.cardType=creditCard.getCardType();
		this.validity=creditCard.getValidity();
		this.agency=creditCard.getAgency();
		this.issuer=creditCard.getIssuer();
		this.isStopped=creditCard.getIsStopped();
		this.cardNum=creditCard.getCardNum();
		this.accountId=accountId;
		this.discount=discount;
		this.categoryId = categoryId;
	}
	

}