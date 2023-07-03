package domain;

import java.time.LocalDate;


import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Installment {
	private Long id;
	private String userId;
	private Long cardID;
	private int insMonth;
	private int remainMonth;
	private Long payment;
	private Integer isInspayed;
	private LocalDate paymentDate;
}