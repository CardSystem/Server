package dto;

import domain.Card;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardResponseDto {
	private long id;
	private long productId;
	private long accountId;
	private String cardId;
	private String issuedDate;
	private String cardType;
	private String validity;
	private String agency;
	private String issuer;
	private Integer isStopped;
	private String cardNum;

	public static CardResponseDto of(Card card) {
		return CardResponseDto.builder()
				.id(card.getId())
				.productId(card.getProductId())
				.accountId(card.getAccountId())
				.cardId(card.getCardId())
				.issuedDate(card.getCardId())
				.cardType(card.getCardType())
				.validity(card.getValidity())
				.agency(card.getAgency())
				.issuer(card.getIssuer())
				.isStopped(card.getIsStopped())
				.cardNum(card.getCardNum())
				.build();
	}

}
