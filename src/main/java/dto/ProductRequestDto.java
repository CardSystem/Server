package dto;

import domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
	private Long id;
	private String cardName;
	private String cardType;
	private Long cardLimit;
	private Long categoryId;
	private String categoryName;
	private Long discount;
	
	public Product toEntity() {
		return Product.builder()
				.id(id)
				.cardName(cardName)
				.cardLimit(cardLimit)
				.cardType(cardType)
				.categoryId(categoryId)
				.categoryName(categoryName)
				.discount(discount)
				.build();
	}	
}
