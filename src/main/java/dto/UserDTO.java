package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private Long id;
	private String userName;
	private String userBirth;
	private int credit;
	private Integer isBlocked;
	private String gender;
	
}
