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

//	@Builder
//	public Account(Long id, String userId, String accountNum, Long balance, String bankName, Integer isStopped) {
//		this.id = id;
//		this.userId = userId;
//		this.accountNum = accountNum;
//		this.balance = balance;
//		this.bankName = bankName;
//		this.isStopped = isStopped;
//	}
}