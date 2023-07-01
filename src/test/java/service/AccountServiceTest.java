package service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dao.AccountDao;
import domain.Account;
import dto.AccountRequestDto;
import dto.AccountResponseDto;

class AccountServiceTest {
	@Mock
	private AccountDao accountDao;

	@InjectMocks
	private AccountService accountService;

	private Account account;
	private AccountRequestDto request;
	private AccountResponseDto response;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		request = AccountRequestDto
				.builder()
				.id(1L)
				.userId("qwer1234")
				.accountNum("123123")
				.balance(110000L)
				.bankName("하나")
				.isStopped(0)
				.build();
		account = request.toEntity();
		response = AccountResponseDto.of(account);
	}

	@Test
	@DisplayName("계좌 조회 테스트 - 성공")
	void test() throws SQLException {
		List<AccountResponseDto> mockAccountList = new ArrayList<>();
		mockAccountList.add(response);
		
		when(accountDao.selectAccount("qwer1234")).thenReturn(mockAccountList);
		List<AccountResponseDto> dto = accountService.selectAccount("qwer1234");
		
		assertNotNull(mockAccountList);
		assertTrue(!dto.isEmpty(), "계좌 리스트가 비어있지 않아야 테스트 통과");
		assertNotNull(dto);
		
		for(AccountResponseDto element : dto) {
			System.out.println(element);
		}
	}
	
	@Test
	@DisplayName("계좌 조회 테스트 - 실패(등록된 계좌가 없을 때)")
	void getAccountListFailedTest() throws SQLException{
		String userId = "qwer";
		accountDao.selectAccount(userId);
		
		when(accountDao.selectAccount(userId)).thenReturn(null);
		
	}

}
