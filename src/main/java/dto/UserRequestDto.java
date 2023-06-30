package dto;

import domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
	private String id;
	private String userName;
	private String userBirth;
	private int credit;
	private Integer adminBlock;
	private Integer delayBlock;
	private String gender;
	
	// dto -> 도메인
	public User toEntity() {
		return User.builder()
				.id(id)
				.userName(userName)
				.userBirth(userBirth)
				.credit(credit)
				.adminBlock(adminBlock)
				.delayBlock(delayBlock)
				.gender(gender)
				.build();
	}
}
