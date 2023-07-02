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
import dto.CreditCardDaoToServiceDto;
import dto.CreditCardHistoryDto;
import dto.InstallmentDto;
import dto.InstallmentUpdateDto;
import dto.MonthlyCreditCreateDto;
import dto.MonthlyCreditDto;
import dto.MonthlyCreditUpdateDto;
import dto.UserDto;
import exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class MonthlyCreditService {
	
	public final MonthlyCreditDao monthlyCreditDao;
	public final AccountDao accountDao;
	public final UserDao userDao;
	public final CardDao cardDao;
	public final CreditCardHistoryDao creditCardHistoryDao;
	public final InstallmentDao installmentDao;
	
	public void MonthlyCalculation() throws Exception {
		
		AccountDto accountDto = null;
		AccountDto stopAccountDto = null;
		ArrayList<CardDto> cardList = null;
		ArrayList<MonthlyCreditDto> monthlyCreditList = null;
		ArrayList<CreditCardHistoryDto> creditCardHistoryList = null;
		ArrayList<InstallmentDto> installmentList = null;
		
		CreditCardHistoryDto creditCardHistoryDto = null;
		InstallmentDto installmentDto = null;
		CreditCardDaoToServiceDto creditCardDaoToServiceDto = null;
		MonthlyCreditCreateDto monthlyCreditCreateDto = null;
		MonthlyCreditUpdateDto monthlyCreditUpdateDto = null;
		InstallmentUpdateDto installmentUpdateDto = null;
		
		LocalDate date = LocalDateTime.now().toLocalDate();
		String month = date.getMonth().toString();
		
		try {
			// 정지되지 않은 신용카드 목록을 가져온다.
			cardList = cardDao.selectAllNonStopedCreditCard();
			
			// 각 카드의 월별 명세서 생성하여 신용카드 월별명세서에 추가
			for (int i=0; i<cardList.size(); i++) {
				// 지불할 금액 초기화
				Long realPay = 0L;
				// 할인 총 금액
				Long discount = 0L;
				// 해당 카드의 결제에 필요한 데이터를 가진 dto
				creditCardDaoToServiceDto = cardDao.selectCreditCardByProductId(cardList.get(i).getProductId());
				// 
				
				// 해당 카드의 할부를 진행하지 않은 카드 사용내역리스트 get
				creditCardHistoryList = creditCardHistoryDao.selectAllNonInstallmentCrditCardHistoryByCardId(cardList.get(i).getId());
				// 해당 카드의 할부를 진행하지 않은 카드 사용내역 항목마다 카드의 제휴와 같다면 할인적용, 아니면 전액을 총 금액에 더한다.
				for(int j = 0; j<creditCardHistoryList.size(); j++) {
					Long money = creditCardHistoryList.get(j).getPayment();
					// 결제한 가맹점과 카드의 제휴 카테고리가 일치하면
					if (creditCardHistoryList.get(j).getFCategory() == creditCardDaoToServiceDto.getCategoryId()) {
						// 해당 결제금액 - 할인금액
						money = money - Math.round(money * (0.01 * creditCardDaoToServiceDto.getDiscount()));
					}
					// 카드 월 사용금액에 결제금액 추가
					realPay = realPay + money;
				}
				// 해당 카드의 납부완료되지 않은 할부리스트 get
				installmentList = installmentDao.selectAllNonIsInspayedInstallmentByCardId(cardList.get(i).getId());
				// 지불할 금액에 해당 월에 정산할 할부액 추가
				for(int j = 0; j<installmentList.size(); j++) {
					// 할부를 모두 합한 총 결제액
					Long delayTotalMoney = installmentList.get(j).getPayment();
					int insMonth = installmentList.get(j).getInsMonth();
					// 이번달 지불해야할 할부액
					Long money = delayTotalMoney / insMonth;
					// 남은 할부개월수
					int remainMonth = installmentList.get(j).getRemainMonth();
					// 남은 할부개월수 == 1 이면 남은 금액을 할부액으로 납부
					if (remainMonth == 1) {
						money = delayTotalMoney - ((insMonth - 1)*money);
					}
					// 월 지불금액에 할부액 추가
					realPay = realPay + money;
				}
				// 총 할인금액 계산
				discount = cardList.get(i).getTotalPayment() - realPay;
				// 신용카드 월별명세서 테이블에 데이터 추가
				monthlyCreditCreateDto = new MonthlyCreditCreateDto(creditCardHistoryList.get(0).getUserId(), creditCardHistoryList.get(0).getCardId(), discount, cardList.get(i).getTotalPayment(), realPay, 0, 0, 0L, LocalDateTime.now().toLocalDate(), null, month+"월 명세서");
				monthlyCreditDao.insertMonthlyCredit(monthlyCreditCreateDto);
			}
			
			// 연제되지 않고 완납되지 않은 모든 신용카드 월별명세서 데이터 get
			monthlyCreditList = monthlyCreditDao.selectAllNonDelayMonthlyCredit();
			
			// 연제되지 않고 완납되지 않은 모든 신용카드 월별명세서 정산
			for (int i=0; i<monthlyCreditList.size(); i++) {
				
				// 해당 카드의 계좌데이터 get
				accountDto = accountDao.selectAccountByUserId(monthlyCreditList.get(i).getUserId());
				// 해당 카드의 완납되지 않은 할부내역을 모두 get
				installmentList = installmentDao.selectAllNonIsInspayedInstallmentByCardId(monthlyCreditList.get(i).getCardId());
				
				// 계좌의 잔액이 월 정산할 금액 이상일경우 (완전히 정산 가능할 때)
				if (accountDto.getBalance() >= monthlyCreditList.get(i).getPay()) {
					// 계좌에서 월 명세서 지불 처리
					accountDao.updateAccountByAccountId(accountDto.getId(), accountDto.getBalance() - monthlyCreditList.get(i).getPay());
					// 해당 카드의 월 명세서에 납부 완료 처리
					monthlyCreditUpdateDto = new MonthlyCreditUpdateDto(monthlyCreditList.get(i).getId(), 1, 0, 0L, date);
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
					// 해당 카드의 총 결제 0으로 초기화
					cardDao.updateTotalPaymentByCardId(0L, monthlyCreditList.get(i).getCardId());
				}
				// 정산 불가능할 경우
				else {
					// 해당 카드의 월 명세서 연체처리
					monthlyCreditUpdateDto = new MonthlyCreditUpdateDto(monthlyCreditList.get(i).getId(), 0, 1, monthlyCreditList.get(i).getPay(), null);
					monthlyCreditDao.updateMonthlyCreditByMonthlyCreditId(monthlyCreditUpdateDto);
					// 해당 유저 블랙리스트 처리
					userDao.updateDelayBlockUserByUserId(monthlyCreditList.get(i).getUserId(), 1);
					// 해당 유저의 계좌정보를 불러와 발급받은 카드 목록을 가져온다.
					stopAccountDto = accountDao.selectAccountByUserId(monthlyCreditList.get(i).getUserId());
					// 해당 유저의 모든 카드 정지처리
					cardList = cardDao.selectAllCardByAccountId(stopAccountDto.getId());
					for (int j = 0; j<cardList.size(); j++) {
						cardDao.updateIsStoppedByCardId(cardList.get(j).getId(), 1);
					}
				}
			}
		} catch (BusinessException e) {
			System.out.println("에러발생: " + e.getMessage());
		}
	}
}