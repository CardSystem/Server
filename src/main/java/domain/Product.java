package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Product {

	private Long id;
	private String cardName;
	private String cardType;
	private Long cardLimit;
	private Long categoryId;
//	private String issuedDate;
//	private String cardType;
//	private String validity;
//	private String agency;
//	private String issuer;
//	private Integer isStopped;
//	private String cardNum;
//	private Long accountId;
	private String categoryName;
	private Long discount;
	
	@Builder
	public Product(Long id, String cardName, String cardType, Long cardLimit, Long categoryId, String categoryName, Long discount) {
		this.id = id;
		this.cardName = cardName;
		this.cardType = cardType;
		this.cardLimit = cardLimit;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.discount = discount;
		
	}
}
