package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	
	private Long id;
	private String userId;
	private String accountNum;
	private Long balance;
	private String bankName;
	private Integer isStopped;

	public void makeBalance(Long payment) {
		this.balance += payment;
	}
}