package dto;

import domain.Account;
import domain.Cards;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {

	private Long id;
	private Cards card;
	private String accountNum;
	private Long balance;
	private String bankName;
	private Integer isStopped;

	public void makeBalance(Long payment) {
		try {
			Thread.sleep(2000);
			this.balance += payment;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 2초 딜레이 추가

	}

	public AccountDto(Account account) {
		this.id = account.getId();
		this.card = account.getCard();
		this.accountNum = account.getAccountNum();
		this.balance = account.getBalance();
		this.bankName = account.getBankName();
		this.isStopped = account.getIsStopped();
	}

}