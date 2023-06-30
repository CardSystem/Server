package dto;

import domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private Long id;
	private String cardName;
	private String cardType;
	private Long cardLimit;
	private Long categoryId;
	
	
	
	public ProductDto(Product product) {
		this.id = product.getId();
		this.cardName = product.getCardName();
		this.cardType = product.getCardType();
		this.cardLimit = product.getCardLimit();
		this.categoryId = product.getCategoryId();
	}
}