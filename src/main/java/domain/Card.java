package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {
	private Long id;
	private Account account;
	private String cardName;
	private String issuedDate;
	private String cardType;
	private String validity;
	private String agency;
	private String issuer;
	private Boolean isStopped;
	private String cardNum;
}