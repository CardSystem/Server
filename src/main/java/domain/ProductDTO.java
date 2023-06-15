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
		private String cardName;
		private String cardType;
		private Long cardLimit;
		private Long categoryId;
	}

}
