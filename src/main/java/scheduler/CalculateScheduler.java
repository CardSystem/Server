package scheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import dao.AccountDao;
import dao.CardDao;
import dao.CreditCardHistoryDao;
import dao.InstallmentDao;
import dao.MonthlyCreditDao;
import dao.UserDao;
import exception.BusinessException;
import service.DelayPaymentService;
import service.MonthlyCreditService;

//import service.MonthlyCreditService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

// 방법1. 월 스케줄링을 열고 연체가 발생할때마다 일 스케줄링을 생성한다.
// 방법2. 월 스케줄링과 일 스케줄링을 서버시작과 함께 시작하도록 한다.  (이 방법으로 선택)


public class CalculateScheduler implements ServletContextListener {
	// volatile 형식으로 설정하여 main memory에 저장하고 읽어오도록함.
	private volatile ScheduledExecutorService executor;
	private volatile ScheduledExecutorService executor2;
	private volatile int test_num = 100;

	// 애플리케이션이 시작될 때 호출되는 메서드
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("서버 켜지면 작동되는 메서드 입성!!!");
		// 일정 시간 뒤 혹은 주기적으로 실행되어야 하는 작업을 위한 쓰레드 풀을 생성함
		// ScheduledExecutorService 인터페이스를 구현한 ScheduledThreadPoolExecutor 객체가 생성됨
		executor = Executors.newScheduledThreadPool(10);
		// scheduleAtFixedRate(Runnable, Delay, Period, Timeunit)
		// runnable에 있는 코드들을 delay 이후 처음 작업실행하고
		// period(특정시간)마다 작업을 실행시킨다.
		System.out.println("월 스케줄러 시작!!!");
		// 1분마다 월 명세서 정산 실행
		executor.scheduleAtFixedRate(run1, 0, 1, TimeUnit.MINUTES);
		System.out.println("월 스케쥴러 실행 완료!!!");
		
		System.out.println("연체 일정산 시작!");
		// 10초마다 연체 일정산 실행
		executor2.scheduleAtFixedRate(run2, 0,10,TimeUnit.SECONDS);
		System.out.println("연체 일정산 완료!");
	}

	// 애플리케이션이 중지될 때 호출되는 메서드
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("서버 꺼지면 작동되는 메서드 입성!!!");
		final ScheduledExecutorService executor = this.executor;
		final ScheduledExecutorService executor2 = this.executor2;

		if (executor != null) {
			executor.shutdown();
			this.executor = null;
		}
		System.out.println("월 정산 스케줄러 종료!");
		if (executor2 != null) {
			executor2.shutdown();
			this.executor2 = null;
		}
		System.out.println("연체 일 정산 스케줄러 종료!");
	}

	// 월정산스케줄->연체->먀일정산스케줄->정산완료시 매일정산스케줄 탈출
	Runnable run1 = new Runnable() {
		public MonthlyCreditDao monthlyCreditDao = new MonthlyCreditDao();
		public AccountDao accountDao = new AccountDao();
		public UserDao userDao = new UserDao();
		public CardDao cardDao = new CardDao();
		public CreditCardHistoryDao creditCardHistoryDao = new CreditCardHistoryDao();
		public InstallmentDao installmentDao = new InstallmentDao();
		public MonthlyCreditService monthlyCreditService = new MonthlyCreditService(monthlyCreditDao, accountDao, userDao, cardDao, creditCardHistoryDao, installmentDao);
		
		@Override
		public void run() throws RuntimeException {
			
			try {
				monthlyCreditService.MonthlyCalculation();
			} catch (Exception e) {
				System.out.println("월정산 스케줄링에서 에러발생: " + e.getMessage());
				e.printStackTrace();
				//throw new RuntimeException("NPE를 RuntimeException으로 감싸서 던지기", e);
			}
			
			
			/*
			if (test_num > 50) {
				test_num -= 1;
				System.out.println("run1의 run() 메서드 의 if문 입장");
				System.out.println(test_num);
			}
			else if(test_num >=1) {
				executor2 = Executors.newScheduledThreadPool(10);
				executor2.scheduleAtFixedRate(run2, 0, 100, TimeUnit.MILLISECONDS);
				System.out.println("run1의 run() 메서드 의 else if문 입장");
				System.out.println(test_num);
			}
			else {
				System.out.println("run1의 run() 메서드 의 else문 입장");
				System.out.println(test_num);
			}
			
			*/
			
			//LocalDateTime now = LocalDateTime.now();

			//System.out.println("TimeStamp1 : " + new SimpleDateFormat("MM-dd-yyyy / hh:mm:ss").format(now));
		}
	};
	
	Runnable run2 = new Runnable() {
		
		public MonthlyCreditDao monthlyCreditDao = new MonthlyCreditDao();
		public AccountDao accountDao = new AccountDao();
		public UserDao userDao = new UserDao();
		public CardDao cardDao = new CardDao();
		public CreditCardHistoryDao creditCardHistoryDao = new CreditCardHistoryDao();
		public InstallmentDao installmentDao = new InstallmentDao();
		
		public DelayPaymentService delayPaymentService = new DelayPaymentService(monthlyCreditDao, accountDao, userDao, cardDao, creditCardHistoryDao, installmentDao);
		
		@Override
		public void run() throws RuntimeException {
			
			try {
				delayPaymentService.DelayCalculation();
			} catch (Exception e) {
				System.out.println("연체 일정산 스케줄링에서 에러발생: " + e.getMessage());
				e.printStackTrace();
				//throw new RuntimeException("NPE를 RuntimeException으로 감싸서 던지기", e);
			}
			/*
			if (test_num >= 1) {
				test_num -= 1;
				System.out.println("run2의 run() 메서드 의 if문 입장");
				System.out.println(test_num);
			}
			else {
				executor2.shutdown();
				executor2 = null;
				System.out.println("run2의 run() 메서드 의 else문 입장");
			}
			
			*/
			
			
			//LocalDateTime now = LocalDateTime.now();

			//System.out.println("TimeStamp1 : " + new SimpleDateFormat("MM-dd-yyyy / hh:mm:ss").format(now));
		}
	};
}