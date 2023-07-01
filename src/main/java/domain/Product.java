package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
	private Long id;
	private String cardName;
	private String cardType;
	private Long cardLimit;
	private Long categoryId;
	private String categoryName;
	private Long discount;
}
