package service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import dao.CheckCardHistoryDao;
import domain.Account;
import domain.Cards;
import dto.AccountDto;
import dto.CheckCardDaoToServiceDto;
import dto.CheckCardRequestDto;
import dto.CheckCardResponseDto;
import exception.BusinessException;

class CheckCardServiceTest {

	@Mock
	CheckCardHistoryDao dao;

	@InjectMocks
	CheckCardService service;

	public static Long id1;
	public static Long cardId2;
	public static Long id2;
	public static Long cardId1;
	public static Long payment1;
	public static Long payment2;

	public static String userId;
	public static String franchisee;
	public static Long accountId;
	public static Long balance;
	public static Integer isStopped;
	public static String cardType;
	public static String validity;
	public static String agency;
	public static String issuer;
	public static String cardNum;
	public static Long discount;
	public static Long fCategory;
	static Cards card1;
	static Cards card2;
	static Account account;
	static CheckCardDaoToServiceDto cardDtoMock1;
	static CheckCardDaoToServiceDto cardDtoMock2;

	static AccountDto accountDtoMock;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		id1 = 1L;
		cardId2 = 2L;
		id2 = 2L;
		cardId1 = 1L;
		userId = "user123";
		franchisee = "Franchise A";
		accountId = 1L;
		balance = 100000L;
		isStopped = 0;
		cardType = "체크";
		validity = "25-06";
		agency = "하나은행 여의도점";
		issuer = "홍길동";
		cardNum = "1234123412341234";
		discount = 15L;
		fCategory = 1L;
		card1 = new Cards(cardId1, id1, null, "2023-06-21", cardType, validity, agency, issuer, isStopped, cardNum);
		card2 = new Cards(cardId2, id2, null, "2023-06-21", cardType, validity, agency, issuer, isStopped, cardNum);

		account = new Account(id1, null, "11111111", balance, "하나은행", isStopped);
		cardDtoMock1 = new CheckCardDaoToServiceDto(card1, accountId, discount);
		cardDtoMock2 = new CheckCardDaoToServiceDto(card2, accountId, discount);

		accountDtoMock = new AccountDto(account);
	}

	@Test
	@DisplayName("체크카드 결제 성공")
	void 체크카드결제성공() throws Exception {

		// given
		payment1 = 1000L;
		CheckCardRequestDto request = new CheckCardRequestDto(cardId1, userId, franchisee, payment1, fCategory,
				LocalDateTime.now());
		Double paymentReal = payment1 * (-1) * ((100 - discount) * 0.01);
		Long totalBalance = balance + (new Double(paymentReal)).longValue();

		// when
		when(dao.selectCardByCardId(cardId1)).thenReturn(cardDtoMock1);
		when(dao.selectAccountByCardId(cardId1)).thenReturn(accountDtoMock);

		// then
		CheckCardResponseDto responseDto = service.checkCardPayment(request);
		assertNotNull(responseDto);
		assertEquals(responseDto.getBalance(), totalBalance);
	}

	
	
	@Test
	@DisplayName("체크카드 결제 실패 - 잔액부족")
	void 잔액부족실패() throws Exception {

		// given
		payment1 = 1000L;
		CheckCardRequestDto request = new CheckCardRequestDto(cardId1, userId, franchisee, payment1, fCategory,
				LocalDateTime.now());
		Double paymentReal = payment1 * (-1) * ((100 - discount) * 0.01);
		Long totalBalance = balance + (new Double(paymentReal)).longValue();

		// when
		when(dao.selectCardByCardId(cardId1)).thenReturn(cardDtoMock1);
		when(dao.selectAccountByCardId(cardId1)).thenReturn(accountDtoMock);

		// then
		try {
			CheckCardResponseDto responseDto = service.checkCardPayment(request);
		} catch (BusinessException e) {
			System.out.println(e.getMessage() + " " + e.getErrorCode());

			assertEquals("잔액이 부족합니다", e.getMessage());
		}

	}
	
	
	@Test
	@DisplayName("체크카드 결제 실패 - 정지된 카드")
	void 정지된카드() throws Exception {

		// given
		isStopped = 1;
		payment1 = 1000L;
		CheckCardRequestDto request = new CheckCardRequestDto(cardId1, userId, franchisee, payment1, fCategory,
				LocalDateTime.now());
		Double paymentReal = payment1 * (-1) * ((100 - discount) * 0.01);
		Long totalBalance = balance + (new Double(paymentReal)).longValue();
		Cards card = new Cards(cardId1, id1, null, "2023-06-21", cardType, validity, agency, issuer, isStopped, cardNum);
		CheckCardDaoToServiceDto cardDtoMock1 = new CheckCardDaoToServiceDto(card1, accountId, discount);

		// when
		when(dao.selectCardByCardId(cardId1)).thenReturn(cardDtoMock1);
		when(dao.selectAccountByCardId(cardId1)).thenReturn(accountDtoMock);

		// then
		try {
			CheckCardResponseDto responseDto = service.checkCardPayment(request);
		} catch (BusinessException e) {
			System.out.println(e.getMessage() + " " + e.getErrorCode());

			assertEquals("정지된 카드입니다.", e.getMessage());
		}

	}
	
	
	
	
	@Test
	@DisplayName("체크카드 결제 실패 - 정지된 계좌")
	void 정지된계좌() throws Exception {

		// given
		isStopped = 1;
		payment1 = 1000L;
		CheckCardRequestDto request = new CheckCardRequestDto(cardId1, userId, franchisee, payment1, fCategory,
				LocalDateTime.now());
		Double paymentReal = payment1 * (-1) * ((100 - discount) * 0.01);
		Long totalBalance = balance + (new Double(paymentReal)).longValue();
		
		Account account= new Account(id1, null, "11111111", balance, "하나은행", 1);
		
		AccountDto accountmock= new AccountDto(account);

		// when
		when(dao.selectCardByCardId(cardId1)).thenReturn(cardDtoMock1);
		when(dao.selectAccountByCardId(cardId1)).thenReturn(accountmock);

		
		// then
		try {
			CheckCardResponseDto responseDto = service.checkCardPayment(request);
		} catch (BusinessException e) {
			System.out.println(e.getMessage() + " " + e.getErrorCode());

			assertEquals("정지된 계좌입니다", e.getMessage());
		}

	}
	
	

	@Test
	@DisplayName("락 걸지 않았을 때 동시성 테스트")
	void 동시성실패() throws Exception {

    	// given
		payment1 = 1000L;
		payment2 = 3000L;
		Double paymentReal1 = payment1 * (-1) * ((100 - discount) * 0.01);
		Double paymentReal2 = payment2 * (-1) * ((100 - discount) * 0.01);
		Double paymentReal3 = 2000L * (-1) * ((100 - discount) * 0.01);

		System.out.println("1000원 결제 시 결제금액 : " + paymentReal1);
		System.out.println("3000원 결제 시 결제금액 : " + paymentReal2);
		System.out.println("2000원 결제 시 결제금액 : " + paymentReal3);



//		Long totalBalance1 = balance + (new Double(paymentReal1)).longValue();
//		Long totalBalance2 = balance + (new Double(paymentReal2)).longValue();

		// when
		when(dao.selectCardByCardId(cardId2)).thenReturn(cardDtoMock1);
		when(dao.selectAccountByCardId(cardId2)).thenReturn(accountDtoMock);
		when(dao.selectCardByCardId(cardId1)).thenReturn(cardDtoMock2);
		when(dao.selectAccountByCardId(cardId1)).thenReturn(accountDtoMock);

		CheckCardRequestDto request1 = new CheckCardRequestDto(cardId1, userId, franchisee, payment1, fCategory,
				LocalDateTime.now());
		CheckCardRequestDto request2 = new CheckCardRequestDto(cardId2, userId, franchisee, payment2, fCategory,
				LocalDateTime.now());
		CheckCardRequestDto request3 = new CheckCardRequestDto(cardId2, userId, franchisee, 2000L, fCategory,
				LocalDateTime.now());
		CompletableFuture<Void> a = CompletableFuture.runAsync(() -> {

			try {
				service.checkCardPayment(request1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		CompletableFuture<Void> b = CompletableFuture.runAsync(() -> {
			try {
				service.checkCardPayment(request2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		CompletableFuture<Void> c = CompletableFuture.runAsync(() -> {
			try {
				service.checkCardPayment(request3);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// then
		CompletableFuture.allOf(a, b,c).join();
		System.out.println("남은금액: " + dao.selectAccountByCardId(cardId1).getBalance());

	}
	
	

}