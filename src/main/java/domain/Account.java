package domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {




	private Long id;
	private Cards card;
	private String accountNum;
	private Long balance;
	private String bankName;
	private Integer isStopped;
	

		public void makeBalance(Long payment)
	{
		this.balance+=payment;
	}
}