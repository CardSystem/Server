package service;

import dao.CheckCardHistoryDao;
import domain.Account;
import dto.AccountDto;
import dto.CheckCardDaoToServiceDto;
import dto.CheckCardHistoryDto;
import dto.CheckCardRequestDto;
import dto.CheckCardResponseDto;
import exception.BusinessException;
import exception.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CheckCardService {

	public final CheckCardHistoryDao checkcarddao;

	public CheckCardResponseDto checkCardPayment(CheckCardRequestDto dto) throws Exception {
		CheckCardHistoryDto historydto = null;
		Long cardId = dto.getCardId();

		// dao에서 받아올 때 dto로 받아오기
		AccountDto accountdto = checkcarddao.selectAccountByCardId(cardId);
		CheckCardDaoToServiceDto carddto = checkcarddao.selectCardByCardId(cardId);
		AccountDto accountResponseDto=null;
		Account account=new Account(accountdto);
//		System.out.println("현재금액: " + accountdto.getBalance());
		int statusCode = 0;
		String statusMsg = null;

		try {
			Long payment = dto.getPayment();
			if (carddto.getIsStopped() == 1) {
				throw new BusinessException(ErrorCode.STOPPED_CARD, "정지된 카드입니다");
			}
			if (account.getIsStopped() == 1) {
				throw new BusinessException(ErrorCode.STOPPED_ACCOUNT, "정지된 계좌입니다");
			}
			if (account.getBalance() < payment) {
				throw new BusinessException(ErrorCode.INSUFFICIENT_BALANCE, "잔액이 부족합니다.");
			}

			Double paymentReal = dto.getPayment() * (-1) * ((100 - carddto.getDiscount()) * 0.01);

			
			//accuontDto를 account 객체로 바꿔서 그 안에서 makebalance 하기
			
			account.makeBalance((new Double(paymentReal)).longValue());

			accountResponseDto=new AccountDto(account);

			checkcarddao.updateBalance(accountResponseDto.getId(), accountResponseDto.getBalance());
			historydto = new CheckCardHistoryDto(dto.getCardId(), dto.getUserId(), dto.getFranchisee(),
					dto.getPayment(), accountResponseDto.getBalance(), 1, dto.getDate(), dto.getFCategory(), 0, 0,
					carddto.getCardType());
			checkcarddao.insertData(historydto);
			System.out.println("잔액: "+accountResponseDto.getBalance());

			statusCode = 200;
			statusMsg = "결제성공";

		} catch (BusinessException e) {
			System.out.println("에러발생: " + e.getMessage());
			historydto = new CheckCardHistoryDto(dto.getCardId(), dto.getUserId(), dto.getFranchisee(),
					dto.getPayment(), accountResponseDto.getBalance(), 0, dto.getDate(), dto.getFCategory(), 1, 0,
					carddto.getCardType());
			checkcarddao.insertData(historydto);
			statusCode = e.getErrorCode().getStatusCode();
			statusMsg = e.getMessage();
		}

		return new CheckCardResponseDto(historydto, statusCode, statusMsg);

	}

}
