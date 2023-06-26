package dto;

import domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ProductDto {
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request{
		private Long id;
		private String cardName;
		private String cardType;
		private Long cardLimit;
		private Long categoryId;
		
		//dto -> Entity화 빌더 메소드
		public Product toEntity() {
			return Product.builder()
					.cardName(cardName)
					.cardLimit(cardLimit)
					.cardType(cardType)
					.categoryId(categoryId)
					.build();
		}	
	}
}
