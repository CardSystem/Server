package dto;

import domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

	private Long id;
	private String userId;
	private String accountNum;
	private Long balance;
	private String bankName;
	private Integer isStopped;

	public AccountDto(Account account) {
		this.id = account.getId();
		this.userId = account.getUserId();
		this.accountNum = account.getAccountNum();
		this.balance = account.getBalance();
		this.bankName = account.getBankName();
		this.isStopped = account.getIsStopped();
	}
	
	public void makeBalance(Long payment) {
		this.balance += payment;
	}
}