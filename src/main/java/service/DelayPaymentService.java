package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import dao.AccountDao;
import dao.CardDao;
import dao.CreditCardHistoryDao;
import dao.InstallmentDao;
import dao.MonthlyCreditDao;
import dao.UserDao;
import dto.AccountDto;
import dto.CardDto;
import dto.InstallmentDto;
import dto.InstallmentUpdateDto;
import dto.MonthlyCreditDto;
import dto.MonthlyCreditUpdateDto;
import dto.UserDto;
import exception.BusinessException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DelayPaymentService {
	
	public final MonthlyCreditDao monthlyCreditDao;
	public final AccountDao accountDao;
	public final UserDao userDao;
	public final CardDao cardDao;
	public final CreditCardHistoryDao creditCardHistoryDao;
	public final InstallmentDao installmentDao;
	
	
	public void DelayCalculation() throws Exception {
		
		System.out.println("월 명세서 작성 함수 입장!!");
		try {
			
			AccountDto accountDto = null;
			ArrayList<MonthlyCreditDto> monthlyCreditList = null;
			ArrayList<InstallmentDto> installmentList = null;
			ArrayList<UserDto> userList = null;
			ArrayList<CardDto> cardList = null;

			MonthlyCreditUpdateDto monthlyCreditUpdateDto = null;
			InstallmentUpdateDto installmentUpdateDto = null;
			
			LocalDate date = LocalDateTime.now().toLocalDate();
			String month = date.getMonth().toString();
			
			
			// 신용카드 월별명세서 테이블에서 연체되고 완납되지 않은 데이터들을 가져온다.
			monthlyCreditList = monthlyCreditDao.selectAllDelayMonthlyCredit();
						
			// 연제되고 완납되지 않은 모든 신용카드 월별명세서 정산
			for (int i=0; i<monthlyCreditList.size(); i++) {
				// 해당 카드의 계좌 데이터를 가져온다.
				accountDto = accountDao.selectAccountByUserId(monthlyCreditList.get(i).getUserId());
				// 해당 카드의 할부내역을 모두 가져온다.
				installmentList = installmentDao.selectAllNonIsInspayedInstallmentByCardId(monthlyCreditList.get(i).getCardId());
				// 계좌의 잔액이 월 정산할 금액 이상일경우 (완전히 정산 가능할 때)
				if (accountDto.getBalance() >= monthlyCreditList.get(i).getPay()) {
					// 계좌에서 월 명세서 지불 처리
					accountDao.updateAccountByAccountId(accountDto.getId(), accountDto.getBalance() - monthlyCreditList.get(i).getPay());
					// 해당 카드의 월 명세서에 납부 완료 처리
					monthlyCreditUpdateDto = new MonthlyCreditUpdateDto(monthlyCreditList.get(i).getId(), 1, monthlyCreditList.get(i).getDelayDays(), 0L, date);
					monthlyCreditDao.updateMonthlyCreditByMonthlyCreditId(monthlyCreditUpdateDto);
					// 해당 카드의 할부 목록 업데이트
					for(int j = 0; j<installmentList.size(); j++) {
						int isInspayed = 0;
						if (installmentList.get(j).getRemainMonth()-1 == 0) {
							isInspayed = 1;
						}
						installmentUpdateDto = new InstallmentUpdateDto(installmentList.get(j).getId(), installmentList.get(j).getRemainMonth()-1, isInspayed);
						installmentDao.updateInstallment(installmentUpdateDto);
					}
					// 해당 카드의 카드테이블 총 결제 항목 0으로 초기화
					cardDao.updateTotalPaymentByCardId(0L, monthlyCreditList.get(i).getCardId());
				}
				// 정산 불가능할 경우
				else {
					// 해당 카드의 월 명세서 연체일수+1
					monthlyCreditUpdateDto = new MonthlyCreditUpdateDto(monthlyCreditList.get(i).getId(), 0, monthlyCreditList.get(i).getDelayDays()+1, monthlyCreditList.get(i).getPay(), null);
					monthlyCreditDao.updateMonthlyCreditByMonthlyCreditId(monthlyCreditUpdateDto);
				}
			}
			
			// 연체로 블랙리스트 처리된 유저목록
			userList = userDao.selectAllNonBlockUser();
			// 연체로 블랙리스트 처리된 유저의 모든 연체 납부 확인시 블랙리스트 해제, 카드 정지 해제
			for (int i=0; i<userList.size(); i++) {
				int count = 0;
				// 해당 유저의 카드 리스트를 가져온다.
				cardList = cardDao.selectAllCardByAccountId(accountDao.selectAccountByUserId(userList.get(i).getId()).getId());
				
				for (int j=0; j<cardList.size(); j++) {
					if (cardList.get(j).getTotalPayment() != 0) {
						count = count + 1;
						break;
					}
				}
				
				// 모든 카드의 연체를 납부했을때
				if (count == 0) {
					// 유저의 연체 블랙리스트 해제
					userDao.updateDelayBlockUserByUserId(userList.get(i).getId(), 0);
					for (int j=0; j<cardList.size(); j++) {
						// 해당 카드의 정지여부 -1
						cardDao.updateIsStoppedByCardId(cardList.get(j).getId(), cardList.get(j).getIsStopped() - 1);
					}
				}
			}
			
			
		} catch (BusinessException e) {
			System.out.println("에러발생: " + e.getMessage());
		}
		
	}
}