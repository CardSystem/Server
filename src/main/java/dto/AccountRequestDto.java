package dto;

import domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {
	private long id;
	private String userId;
	private String accountNum;
	private long balance;
	private String bankName;
	private Integer isStopped;
	
	public Account toEntity() {
		return Account.builder()
				.id(id)
				.userId(userId)
				.accountNum(accountNum)
				.balance(balance)
				.bankName(bankName)
				.isStopped(isStopped)
				.build();
	}
}
