package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Product {
	private Long productId;
	private String cardName;
	private String cardType;
	private Long cardLimit;
	private Long categoryId;

	@Builder
	public Product(Long productId, String cardName, String cardType, Long cardLimit, Long categoryId) {
		//super(); /// 부모 클래스 생성자 호출
		this.productId = productId;
		this.cardName = cardName;
		this.cardType = cardType;
		this.cardLimit = cardLimit;
		this.categoryId = categoryId;
	}
}
