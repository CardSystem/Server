package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private String id;
	private String userName;
	private String userBirth;
	private int credit;
	private Integer adminBlock;
	private Integer delayBlock;
	private String gender;
	
}
