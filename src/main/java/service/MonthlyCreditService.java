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
		

		CreditCardDaoToServiceDto creditCardDaoToServiceDto = null;
		MonthlyCreditCreateDto monthlyCreditCreateDto = null;
		MonthlyCreditUpdateDto monthlyCreditUpdateDto = null;
		InstallmentUpdateDto installmentUpdateDto = null;
		
		LocalDate date = LocalDateTime.now().toLocalDate();
		String month = date.getMonth().toString();
		
		System.out.println("월 명세서 작성 함수 입장!!");
		try {
			// 정지되지 않은 신용카드 목록을 가져온다.
			cardList = cardDao.selectAllNonStopedCreditCard();
			//System.out.println("try 입장!!");
			// 각 카드의 월별 명세서 생성하여 신용카드 월별명세서에 추가
			for (int i=0; i<cardList.size(); i++) {
				// 총 결제 금액 초기화
				Long totalPay = 0L;
				// 총 할인 금액
				Long discount = 0L;
				// 실제 납부할 금액
				Long realPay = 0L;
				// 해당 카드의 결제에 필요한 데이터를 가진 dto
				creditCardDaoToServiceDto = cardDao.selectCreditCardByProductId(cardList.get(i).getProductId());

				// 해당 카드의 할부를 진행하지 않은 카드 사용내역리스트 get
				creditCardHistoryList = creditCardHistoryDao.selectAllNonInstallmentCrditCardHistoryByCardId(cardList.get(i).getId());
				//System.out.println("할부가 아닌 결제 리스트의 개수 = " + creditCardHistoryList.size());
				//System.out.println("해당 사용내역리스트의 id값 = " + creditCardHistoryList.get(0).getId());
				// 해당 카드의 할부를 진행하지 않은 카드 사용내역 항목마다 카드의 제휴와 같다면 할인적용, 아니면 전액을 총 금액에 더한다.
				for(int j = 0; j<creditCardHistoryList.size(); j++) {
					Long money = creditCardHistoryList.get(j).getPayment();
					// 결제한 가맹점과 카드의 제휴 카테고리가 일치하면
					if (creditCardHistoryList.get(j).getFCategory() == creditCardDaoToServiceDto.getCategoryId()) {
						// 총 할인금액 업데이트
						discount = discount + Math.round(money * (0.01 * creditCardDaoToServiceDto.getDiscount()));
					}
					// 카드 총 결제금액에 할부아닌 결제금액 추가
					totalPay = totalPay + money;
				}
				// 해당 카드의 납부완료되지 않은 할부리스트 get
				installmentList = installmentDao.selectAllNonIsInspayedInstallmentByCardId(cardList.get(i).getId());
				//System.out.println("할부 리스트의 수 = " + installmentList.size());
				// 지불할 금액에 해당 월에 정산할 할부액 추가
				for(int j = 0; j<installmentList.size(); j++) {
					// 총 결제액
					Long delayTotalMoney = installmentList.get(j).getPayment();
					// 할부 개월 수
					int insMonth = installmentList.get(j).getInsMonth();
					// 이번달 지불해야할 할부액
					Long money = delayTotalMoney / insMonth;
					// 남은 할부개월수
					int remainMonth = installmentList.get(j).getRemainMonth();
					// 남은 할부개월수 == 1 이면 남은 금액을 할부액으로 납부
					if (remainMonth == 1) {
						money = delayTotalMoney - ((insMonth - 1)*money);
					}
					else {
						// 월 지불금액에 할부액 추가
						totalPay = totalPay + money;
					}
				}
				// 실제 납부할 금액 계산
				realPay = totalPay - discount;
				// 신용카드 월별명세서 테이블에 데이터 추가
				monthlyCreditCreateDto = new MonthlyCreditCreateDto(creditCardHistoryList.get(0).getUserId(), creditCardHistoryList.get(0).getCardId(), discount, totalPay, realPay, 0, 0, 0L, LocalDateTime.now().toLocalDate(), LocalDateTime.now().toLocalDate(), month+"월 명세서");
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
				
				//System.out.println("완납되지 않은 할부 개수 = " + installmentList.size());
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
						//System.out.println("해당 월 명세서에 해당하는 할부 결제 업데이트!");
						installmentUpdateDto = new InstallmentUpdateDto(installmentList.get(j).getId(), installmentList.get(j).getRemainMonth()-1, isInspayed);
						installmentDao.updateInstallment(installmentUpdateDto);
					}
					//System.out.println("해당 카드의 총 결제를 0으로 초기화");
					// 해당 카드의 총 결제 0으로 초기화
					cardDao.updateTotalPaymentByCardId(0L, monthlyCreditList.get(i).getCardId());
				}
				// 정산 불가능할 경우
				else {
					//System.out.println("==== 정산 불가로 인한 연체 처리 진행===!!");
					// 해당 카드의 월 명세서 연체처리
					monthlyCreditUpdateDto = new MonthlyCreditUpdateDto(monthlyCreditList.get(i).getId(), 0, 1, monthlyCreditList.get(i).getPay(), null);
					monthlyCreditDao.updateMonthlyCreditByMonthlyCreditId(monthlyCreditUpdateDto);
					System.out.println("해당 유저 블랙리스트 처리!");
					// 해당 유저 블랙리스트 처리
					userDao.updateDelayBlockUserByUserId(monthlyCreditList.get(i).getUserId(), 1);
					// 해당 유저의 계좌정보를 불러와 발급받은 카드 목록을 가져온다.
					stopAccountDto = accountDao.selectAccountByUserId(monthlyCreditList.get(i).getUserId());
					// 해당 유저의 모든 카드 정지처리
					cardList = cardDao.selectAllCardByAccountId(stopAccountDto.getId());
					for (int j = 0; j<cardList.size(); j++) {
						//System.out.println("카드정지처리!!");
						cardDao.updateIsStoppedByCardId(cardList.get(j).getId(), 1);
					}
				}
			}
		} catch (BusinessException e) {
			System.out.println("에러발생: " + e.getMessage());
		}
	}
}