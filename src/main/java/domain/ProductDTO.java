package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProductDTO {

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RequestProduct {
		private Long productId;
		private String cardName;
		private String cardType;
		private Long cardLimit;
		private Long categoryId;
	
	public Product toEntity() {
		return Product.builder()
				.cardName(cardName)
				.cardLimit(cardLimit)
				.cardType(cardType)
				.categoryId(categoryId)
				.build();
		}
	}
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UpdateProduct{
		private Long productId;
		private String cardName;
		private String cardType;
		private Long cardLimit;
		private Long categoryId;
	}
}
