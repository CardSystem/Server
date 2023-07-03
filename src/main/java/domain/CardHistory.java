package domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardHistory {
	private Long id;
	private Long cardId;
	private String userId;
	private String franchisee;
	private Long payment;
	private Long balance;
	private Integer isSuccess;
	private LocalDateTime date;
	private Long fCategory;
	private Integer isIns;
	private int insMonth;
	private String cardType;
}
