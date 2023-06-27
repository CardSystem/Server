package dto;

import domain.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDto {
	private Long id;
	private String cardName;
	private String cardType;
	private Long cardLimit;
	private Long categoryId;
	private String categoryName;
	private Long discount;
	
	public static ProductResponseDto of(Product product) {
		return ProductResponseDto.builder()
				.id(product.getId())
				.cardName(product.getCardName())
				.cardType(product.getCardType())
				.cardLimit(product.getCardLimit())
				.categoryId(product.getCategoryId())
				.categoryName(product.getCategoryName())
				.discount(product.getDiscount())
				.build();				
	}
}

