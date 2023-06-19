package service;

import dao.CreditCardHistoryDao;
import dto.CardDto;
import dto.CreditCardPaymentDto;

public class CreditCardService {
	
	CardDto cardDto;
	
	
	public final CreditCardHistoryDao creditCardHistoryDao = new CreditCardHistoryDao();
	
	
	
	
	
	public Boolean isStoppedCard(Long cardId) {
		
		CardDto cardDto = creditCardHistoryDao.selectCardByCardId(cardId);
		
		if(cardDto.getIsStopped()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	
	public void payCreditCard(CreditCardPaymentDto creditCardPaymentDto) throws Exception {
		
		
		if(!isStoppedCard(creditCardPaymentDto.getCardId())) {
			throw new Exception("정지된 카드입니다.");
		}
		
		System.out.println("서비스 진입");
		System.out.println("카드이름:"+ cardDto.getCardName());
		
	}
	
	
}