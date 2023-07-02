package dto;

import domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	private String id;
	private String userName;
	private String userBirth;
	private int credit;
	private Integer adminBlock;
	private Integer delayBlock;
	private String gender;
	
	public UserDto(User user) {
		this.id = id;
		this.userName = userName;
		this.userBirth = userBirth;
		this.credit = credit;
		this.adminBlock = adminBlock;
		this.delayBlock = delayBlock;
		this.gender = gender;
	}
}