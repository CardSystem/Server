package dto;


import domain.Card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckCardDaoToServiceDto {

	
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
	
	public CheckCardDaoToServiceDto(Card checkcard,Long accountId,Long discount)
	{
		this.cardId=checkcard.getId();
		this.cardType=checkcard.getCardType();
		this.validity=checkcard.getValidity();
		this.agency=checkcard.getAgency();
		this.issuer=checkcard.getIssuer();
		this.isStopped=checkcard.getIsStopped();
		this.cardNum=checkcard.getCardNum();
		this.accountId=accountId;
		this.discount=discount;
	}
	

}