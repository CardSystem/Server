package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
	private long id;
	private String userId;
	private String accountNum;
	private long balance;
	private String bankName;
	private Integer isStopped;
	
	public AccountDto() {}
}
