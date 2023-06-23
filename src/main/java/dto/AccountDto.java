package dto;

import domain.Account;
import domain.Cards;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountDto {

	private Long id;
	private Cards card;
	private String accountNum;
	private Long balance;
	private String bankName;
	private Integer isStopped;

//	public void makeBalance(Long payment) {
//			this.balance += payment;
//	}
	@Builder
	public AccountDto(Account account) {
		this.id = account.getId();
		this.card = account.getCard();
		this.accountNum = account.getAccountNum();
		this.balance = account.getBalance();
		this.bankName = account.getBankName();
		this.isStopped = account.getIsStopped();
	}



}