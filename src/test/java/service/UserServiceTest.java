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

import dao.UserDao;
import domain.User;
import dto.UserRequestDto;
import dto.UserResponseDto;

class UserServiceTest {

	@Mock
	private UserDao userDao;
	
	//목 객체에 의존하는 객체에 대해 초기화
	@InjectMocks
	private UserService userService;
	
	private User user;
	private UserRequestDto request;
	private UserResponseDto response;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		request = UserRequestDto//요청 dto
				.builder()
				.id("qwer1234")
				.userName("홍길동")
				.userBirth("19991212")
				.credit(1)
				.adminBlock(1)
				.delayBlock(1)
				.gender("남")
				.build();
		user = request.toEntity(); //dto를 entity화
		response = UserResponseDto.of(user); //응답 dto로 가공
	}
	
	@Test
	@DisplayName("유저 조회 테스트 - 성공")
	void test() throws SQLException {
		List<UserResponseDto> mockUserList = new ArrayList<>();
		mockUserList.add(response);
		
		when(userDao.showUserList()).thenReturn(mockUserList);
		List<UserResponseDto> dto = userService.showUserList();
		
		assertNotNull(mockUserList);
		assertTrue(!dto.isEmpty(), "유저 리스트가 비어있지 않아야 테스트 통과");
		assertNotNull(dto);
		
		for(UserResponseDto element : dto) {
			System.out.println(element);
		}
	}

}
