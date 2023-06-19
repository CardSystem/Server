package domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

	private Long id;
	private String cardName;
	private String cardType;
	private Long cardLimit;
	private Long categoryId;
	
}