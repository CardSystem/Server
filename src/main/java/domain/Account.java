package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	private Long id;
	private Cards card;
	private String accountNum;
	private Long balance;
	private String bankName;
	private Integer isStopped;

	public synchronized void makeBalance(Long payment) {
		this.balance += payment;
	}

}