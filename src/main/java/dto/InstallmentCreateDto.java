package dto;

import java.time.LocalDate;

import domain.Installment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InstallmentCreateDto {
	
	String userId;
	Long cardId;
	int insMonth;
	int remainMonth;
	Long payment;
	Integer isInspayed;
	LocalDate paymentDate;
	
	
	public Installment toEntity() {
		return Installment.builder()
				.userId(this.userId)
				.cardID(this.cardId)
				.insMonth(this.insMonth)
				.remainMonth(this.remainMonth)
				.payment(this.payment)
				.isInspayed(this.isInspayed)
				.payment(this.payment)
				.build();
	}
	
}