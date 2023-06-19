package domain;

import lombok.Data;

@Data
public class ProductResponseDTO {
	private Long productId;
	private String cardName;
	private String cardType;
	private Long cardLimit;
	private Long categoryId;
}
