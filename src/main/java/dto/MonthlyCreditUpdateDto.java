package dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyCreditUpdateDto{
	
	Long id;
	Integer isPayed;
	int delayDays;
	Long delayPrice;
	LocalDate endDate;
	
}