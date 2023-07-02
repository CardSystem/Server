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
	private Integer adminBlock;
	private Integer delayBlock;
	private String gender;

	public static UserResponseDto of(User user) {
		return UserResponseDto.builder()
				.id(user.getId())
				.userName(user.getUserName())
				.userBirth(user.getUserBirth())
				.credit(user.getCredit())
				.adminBlock(user.getAdminBlock())
				.delayBlock(user.getDelayBlock())
				.gender(user.getGender())
				.build();
	}
}
