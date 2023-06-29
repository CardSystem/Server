package domain;

import dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

	private Long id;
	private String accountNum;
	private Long balance;
	private String bankName;
	private Integer isStopped;

	public void makeBalance(Long payment) {
		this.balance += payment;
	}

	public Account(AccountDto dto)
	{
		this.id=dto.getId();
		this.accountNum=dto.getAccountNum();
		this.balance=dto.getBalance();
		this.bankName=dto.getBankName();
		this.isStopped=dto.getIsStopped();
	}


}