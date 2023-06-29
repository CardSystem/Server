package dto;

import domain.Account;
import domain.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

	private Long id;
	private String accountNum;
	private Long balance;
	private String bankName;
	private Integer isStopped;

	public void makeBalance(Long payment) {
		this.balance += payment;
	}
	@Builder
	public AccountDto(Account account) {
		this.id = account.getId();
		this.accountNum = account.getAccountNum();
		this.balance = account.getBalance();
		this.bankName = account.getBankName();
		this.isStopped = account.getIsStopped();
	}



}