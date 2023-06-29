package dto;

import domain.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
public class AccountResponseDto {
	private long id;
	private String userId;
	private String accountNum;
	private long balance;
	private String bankName;
	private Integer isStopped;
	
	public static AccountResponseDto of(Account account) {
		return AccountResponseDto.builder()
				.id(account.getId())
				.userId(account.getUserId())
				.accountNum(account.getAccountNum())
				.balance(account.getBalance())
				.bankName(account.getBankName())
				.isStopped(account.getIsStopped())
				.build();
	}
}
