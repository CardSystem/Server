package service;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import dao.AccountDao;
import dao.CardDao;
import dao.CardHistoryDao;
import domain.Account;
import dto.AccountDto;
import dto.CheckCardDaoToServiceDto;
import dto.CheckCardHistoryDto;
import dto.CheckCardRequestDto;
import dto.CheckCardResponseDto;
import exception.BusinessException;
import exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import redis.RedisLockUtil;

@AllArgsConstructor
public class CheckCardService {

	public final CardHistoryDao checkcardDao;
	public final AccountDao accountDao;
	public final CardDao cardDao;

	public CheckCardResponseDto checkCardPayment(CheckCardRequestDto dto) throws Exception {
		CheckCardHistoryDto historydto = null;
		Long cardId = dto.getCardId();

		AccountDto accountdto = accountDao.selectAccountByCardId(cardId);
		CheckCardDaoToServiceDto carddto = cardDao.selectCardByCardId(cardId);
		AccountDto responseDto=null;
		
		
		int statusCode = 0;
		String statusMsg = null;

		
		try {
			Long payment = dto.getPayment();
			if (carddto.getIsStopped() == 1) {
				throw new BusinessException(ErrorCode.STOPPED_CARD, "정지된 카드입니다");
			}
			if (accountdto.getIsStopped() == 1) {
				throw new BusinessException(ErrorCode.STOPPED_ACCOUNT, "정지된 계좌입니다");
			}
			if (accountdto.getBalance() < payment) {
				throw new BusinessException(ErrorCode.INSUFFICIENT_BALANCE, "잔액이 부족합니다.");
			}

			Double paymentReal = dto.getPayment() * (-1) * ((100 - carddto.getDiscount()) * 0.01);

			accountdto.makeBalance((new Double(paymentReal)).longValue());

			accountDao.updateBalance(accountdto.getId(), accountdto.getBalance());

			historydto = new CheckCardHistoryDto(dto.getCardId(), dto.getUserId(), dto.getFranchisee(),
					dto.getPayment(), accountdto.getBalance(), 1, dto.getDate(), dto.getFCategory(), 0, 0,
					carddto.getCardType());
			checkcardDao.insertCheckCardHistory(historydto);
			statusCode = 200;
			statusMsg = "결제성공";

		} catch (BusinessException e) {
			System.out.println("에러발생: " + e.getMessage());
			historydto = new CheckCardHistoryDto(dto.getCardId(), dto.getUserId(), dto.getFranchisee(),
					dto.getPayment(), accountdto.getBalance(), 0, dto.getDate(), dto.getFCategory(), 0, 0,
					carddto.getCardType());
			checkcardDao.insertCheckCardHistory(historydto);
			statusCode = e.getErrorCode().getStatusCode();
			statusMsg = e.getMessage();
		}

		return new CheckCardResponseDto(historydto, statusCode, statusMsg);

	}


	


}
