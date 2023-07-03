package service;

import dao.CardDao;
import dao.CardHistoryDao;
import dao.InstallmentDao;
import dao.ProductDao;
import dto.CardDto;
import dto.CreditCardHistoryCreateDto;
import dto.CreditCardHistoryDto;
import dto.CreditCardRequestDto;
import dto.CreditCardResponseDto;
import dto.InstallmentCreateDto;
import dto.ProductDto;
import exception.BusinessException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreditCardService {
	

	public final CardHistoryDao cardHistoryDao;
	public final ProductDao productDao;
	public final InstallmentDao installmentDao;
	public final CardDao cardDao;
	
	// 신용 카드 결제 플로우
	// 할부 로직 처리 
	/*
	 * 카드 정지여부 확인
	 * 할부 개월수에 따른 각 월 한도체크 후 결제 가능여부 확인 -> 현재달만 체크하면된다. why? 이후의 달들이 현재의 달보다 많은 금액이 쌓여있을 수 없다.
	 * 결제하여 카드사용내역에 결제내열 저장
	 * 할부를 했다면 할부에 할부내역 저장
	 */
	public CreditCardResponseDto payCreditCard(CreditCardRequestDto creditCardRequestDto) throws Exception {
		
		CreditCardHistoryCreateDto creditCardHistoryCreateDto = null;
		InstallmentCreateDto installmentCreateDto = null;
		// 결제한 카드 id
		Long cardId = creditCardRequestDto.getCardId();
		// 카드정보 get
		CardDto cardDto = cardDao.selectOneCardByCardId(cardId);
		// 카드상품정보 get
		ProductDto productDto = productDao.selectProductByProductId(cardDto.getProductId());
		
		// 상태코드, 상태 메시지
		int statusCode = 0;
		String statusMsg = null;
		
		// 할부 개월 수
		int insMonth = 0;
		// 이번달 총 결제액 + 결제금액
		Long totalPayment = cardDto.getTotalPayment() + creditCardRequestDto.getPayment();
		
		
		try {
			
			insMonth = creditCardRequestDto.getInsMonth();
			
			// 카드 정지여부 확인
			if (cardDto.getIsStopped() == 1) {
				throw new Exception("정지된 카드입니다.");
			}
			// 카드 한도 확인
			if (totalPayment > productDto.getCardLimit()) {
				throw new Exception("결제 금액이 한도를 초과합니다.");
			}
			
			
			// 카드 결제 내역 Dto로 정리
			creditCardHistoryCreateDto = new CreditCardHistoryCreateDto(creditCardRequestDto.getCardId(), creditCardRequestDto.getUserId(), creditCardRequestDto.getFranchisee(),
					creditCardRequestDto.getPayment(), null, 1, creditCardRequestDto.getDate(), creditCardRequestDto.getFCategory(), 1, 0,
					cardDto.getCardType());
			
			// 카드사용내역 디비 테이블에 저장
			cardHistoryDao.insertCreditCardHistory(creditCardHistoryCreateDto);
			
			// 할부 결제를 진행했을때
			if (insMonth != 0) {
				installmentCreateDto = new InstallmentCreateDto(creditCardRequestDto.getUserId(), creditCardRequestDto.getCardId(), creditCardRequestDto.getInsMonth(),
						creditCardRequestDto.getInsMonth(), creditCardRequestDto.getPayment(), 0, creditCardRequestDto.getDate().toLocalDate());
				// 할부 테이블 디비에 할부내역 저장
				installmentDao.insertInstallment(installmentCreateDto);
			}
			
			// cards 테이블의 total_payment 갱신
			cardDao.updateTotalPaymentByCardId(totalPayment, cardId);
			
			statusCode = 200;
			statusMsg = "결제성공";
			
		} catch (BusinessException e) {
			System.out.println("에러발생: " + e.getMessage());
			creditCardHistoryCreateDto = new CreditCardHistoryCreateDto(creditCardRequestDto.getCardId(), creditCardRequestDto.getUserId(), creditCardRequestDto.getFranchisee(),
					creditCardRequestDto.getPayment(), null, 0, creditCardRequestDto.getDate(), creditCardRequestDto.getFCategory(), 1, 0,
					cardDto.getCardType());
			cardHistoryDao.insertCreditCardHistory(creditCardHistoryCreateDto);
			statusCode = e.getErrorCode().getStatusCode();
			statusMsg = e.getMessage();
		}
		
		return new CreditCardResponseDto(creditCardHistoryCreateDto, statusCode, statusMsg);
	}
	
}