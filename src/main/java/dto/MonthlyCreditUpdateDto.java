package dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyCreditUpdateDto{
	
	Long id;
	Integer isPayed;
	int delayDays;
	Long delayPrice;
	LocalDate endDate;
	
}