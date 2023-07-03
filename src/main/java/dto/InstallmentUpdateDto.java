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
public class InstallmentUpdateDto {
	Long id;
	int remainMonth;
	Integer isInspayed;
}