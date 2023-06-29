package dto;

import domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDto {
	private String id;
	private String userName;
	private String userBirth;
	private int credit;
	private Integer isBlocked;
	private String gender;
	
	//도메인 -> dto
	public static UserResponseDto of(User user) {
		return UserResponseDto.builder()
				.id(user.getId())
				.userName(user.getUserName())
				.userBirth(user.getUserBirth())
				.credit(user.getCredit())
				.isBlocked(user.getIsBlocked())
				.gender(user.getGender())
				.build();
	}
}
