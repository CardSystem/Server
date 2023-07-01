package dto;

import domain.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardRequestDto {
	private long id;
	private long productId;
	private long accountId;
	private String issuedDate;
	private String cardType;
	private String validity;
	private String agency;
	private String issuer;
	private Integer isStopped;
	private String cardNum;
	private long totalPayment;
	
	public Card toEntity() {
		return Card.builder()
				.id(id)
				.productId(productId)
				.accountId(accountId)
				.issuedDate(issuedDate)
				.cardType(cardType)
				.validity(validity)
				.agency(agency)
				.issuer(issuer)
				.isStopped(isStopped)
				.cardNum(cardNum)
				.totalPayment(totalPayment)
				.build();
	}
}
