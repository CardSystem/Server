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
	private Cards card;
	private String accountNum;
	private Long balance;
	private String bankName;
	private Integer isStopped;
}