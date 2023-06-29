package dto;

import domain.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardRequestDto {
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
	
	public Card toEntity() {
		return Card.builder()
				.id(id)
				.productId(productId)
				.accountId(accountId)
				.cardId(cardId)
				.issuedDate(issuedDate)
				.cardType(cardType)
				.validity(validity)
				.agency(agency)
				.issuer(issuer)
				.isStopped(isStopped)
				.cardNum(cardNum)
				.build();
	}
}
