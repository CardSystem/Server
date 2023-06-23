package service;

import dao.CreditCardHistoryDao;
import dto.CardDto;
import dto.CreditCardHistoryCreateDto;
import dto.CreditCardHistoryDto;
import dto.CreditCardRequestDto;
import dto.CreditCardResponseDto;
import exception.BusinessException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreditCardService {
	

	public final CreditCardHistoryDao creditCardHistoryDao;
	
	
	// 신용 카드 결제 플로우
	// 할부 로직 처리 
	public CreditCardResponseDto payCreditCard(CreditCardRequestDto creditCardRequestDto) throws Exception {
		
		CreditCardHistoryCreateDto creditCardHistoryCreateDto = null;
		Long cardId = creditCardRequestDto.getCardId();
		
		CardDto cardDto = creditCardHistoryDao.selectCardByCardId(cardId);
		
		int statusCode = 0;
		String statusMsg = null;
		int insMonth;
		
		
		try {
			
			insMonth = creditCardRequestDto.getInsMonth();
			
			if(cardDto.getIsStopped() == 1) {
				throw new Exception("정지된 카드입니다.");
			}
			for(int i = 0; i < insMonth;)
			
			creditCardHistoryCreateDto = new CreditCardHistoryCreateDto(creditCardRequestDto.getCardId(), creditCardRequestDto.getUserId(), creditCardRequestDto.getFranchisee(),
					creditCardRequestDto.getPayment(), null, 1, creditCardRequestDto.getDate(), creditCardRequestDto.getFCategory(), 1, 0,
					cardDto.getCardType());
			creditCardHistoryDao.insertCreditCardHistory(creditCardHistoryCreateDto);
			
			statusCode = 200;
			statusMsg = "결제성공";
			
		} catch (BusinessException e) {
			System.out.println("에러발생: " + e.getMessage());
			creditCardHistoryCreateDto = new CreditCardHistoryCreateDto(creditCardRequestDto.getCardId(), creditCardRequestDto.getUserId(), creditCardRequestDto.getFranchisee(),
					creditCardRequestDto.getPayment(), null, 0, creditCardRequestDto.getDate(), creditCardRequestDto.getFCategory(), 1, 0,
					cardDto.getCardType());
			creditCardHistoryDao.insertCreditCardHistory(creditCardHistoryCreateDto);
			statusCode = e.getErrorCode().getStatusCode();
			statusMsg = e.getMessage();
		}
		
		
		System.out.println("사용 가능한 카드입니다.");
		System.out.println("서비스 진입");
		//System.out.println("카드이름:"+ cardDto.getCardName());
		
		return new CreditCardResponseDto(creditCardHistoryCreateDto, statusCode, statusMsg);
	}
	
}