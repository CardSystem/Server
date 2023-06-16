//import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {
	@Builder.Default
	private String id = UUID.randomUUID().toString(); // 응답 uuid
	@Builder.Default
	private Date dateTime = new Date(); //일시
	private String message; //성공, 실패 메시지?
	private Object list; //반환 값
}
