package dto;

import java.time.LocalDate;

import domain.Installment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InstallmentDto {
	private Long id;
	String userId;
	Long cardId;
	int insMonth;
	int remainMonth;
	Long payment;
	Integer isInspayed;
	LocalDate paymentDate;
	
	
	public InstallmentDto(Installment installment) {
		this.id = installment.getId();
		this.userId = installment.getUserId();
		this.cardId = installment.getCardID();
		this.insMonth = installment.getInsMonth();
		this.remainMonth = installment.getRemainMonth();
		this.payment = installment.getPayment();
		this.isInspayed = installment.getIsInspayed();
		this.paymentDate = installment.getPaymentDate();
	}
}