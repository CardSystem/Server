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
	private Integer isBlocked;
	private String gender;
	
	@Builder
	public User(String id, String userName, String userBirth, int credit, Integer isBlocked, String gender) {
		this.id = id;
		this.userName = userName;
		this.userBirth = userBirth;
		this.credit = credit;
		this.isBlocked = isBlocked;
		this.gender = gender;
	}
}
