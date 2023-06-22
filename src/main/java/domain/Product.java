package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {

	private Long id;
	private String cardName;
	private String cardType;
	private Long cardLimit;
	private Long categoryId;
//	private String issuedDate;
//	private String cardType;
//	private String validity;
//	private String agency;
//	private String issuer;
//	private Integer isStopped;
//	private String cardNum;
//	private Long accountId;

}
