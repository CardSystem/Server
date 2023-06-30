package dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckCardResponseDto {
	private int statusCode;
	private String statusMsg;

	// cardHistory에서 받아오기
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

	public CheckCardResponseDto(CheckCardHistoryDto history, int statusCode, String statusMsg) {
		this.cardId = history.getCardId();
		this.userId = history.getUserId();
		this.franchisee = history.getFranchisee();
		this.payment = history.getPayment();
		this.balance = history.getBalance();
		this.isSuccess = history.getIsSuccess();
		this.date = history.getDate();
		this.fCategory = history.getFCategory();
		this.isIns = history.getIsIns();
		this.insMonth = history.getInsMonth();
		this.cardType = history.getCardType();
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;

	}

}
