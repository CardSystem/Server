package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {

	private Long id;
	private Long productId; //productId
	private Long accountId;
	private String issuedDate;
	private String cardType;
	private String validity;
	private String agency;
	private String issuer;
	private Integer isStopped;
	private String cardNum;
	private Long totalPayment;
	
//	
//	public Card(Long id, Long productId, Long accountId, String issuedDate, String cardType, String validity, String agency, String issuer, Integer isStopped, String cardNum, Long totalPayment) {
//		this.id = id;
//		this.productId = productId;
//		this.accountId = accountId;
//		this.issuedDate = issuedDate;
//		this.cardType = cardType;
//		this.validity = validity;
//		this.agency = agency;
//		this.issuer = issuer;
//		this.isStopped = isStopped;
//		this.cardNum = cardNum;
//		this.totalPayment = totalPayment;
//	}

}