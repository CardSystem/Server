package dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardRequestDto {
	private Long cardId;
	private String userId;
	private String franchisee;
	private Long payment;
	private Long fCategory;
	private int insMonth;
	private LocalDateTime date;
}