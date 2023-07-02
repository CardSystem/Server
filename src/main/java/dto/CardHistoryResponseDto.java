package dto;

import domain.CardHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardHistoryResponseDto{
	private long id;
	private long cardId;
	private String userId;
	private String franchisee;
	private long payment;
	private long balance;
	private int isSuccess;
	private String date;
	private long fCategory;
	private int isIns;
	private int insMonth;
	private String cardType;
	
	 public static CardHistoryResponseDto of(CardHistory cardHistory) {
	      return CardHistoryResponseDto.builder()
	            .id(cardHistory.getId())
	            .cardId(cardHistory.getCardId())
	            .userId(cardHistory.getUserId())
	            .franchisee(cardHistory.getFranchisee())
	            .payment(cardHistory.getPayment())
	            .balance(cardHistory.getBalance())
	            .isSuccess(cardHistory.getIsSuccess())
	            .date(cardHistory.getDate())
	            .fCategory(cardHistory.getFCategory())
	            .isIns(cardHistory.getIsIns())
	            .insMonth(cardHistory.getInsMonth())
	            .cardType(cardHistory.getCardType())
	            .build();
	      }

}