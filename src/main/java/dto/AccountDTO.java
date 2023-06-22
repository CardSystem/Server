package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
	private long id;
	private String userId;
	private String AccountNum;
	private long balance;
	private String bankName;
	private Integer isStopped;
	
	public AccountDTO() {}
}
