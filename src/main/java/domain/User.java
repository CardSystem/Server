package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {
	private String id;
	private String userName;
	private String userBirth;
	private int credit;
	private Integer adminBlock;
	private Integer delayBlock;
	private String gender;
	
	@Builder
	public User(String id, String userName, String userBirth, int credit, Integer adminBlock, Integer delayBlock, String gender) {
		this.id = id;
		this.userName = userName;
		this.userBirth = userBirth;
		this.credit = credit;
		this.adminBlock = adminBlock;
		this.delayBlock = delayBlock;
		this.gender = gender;
	}
}
