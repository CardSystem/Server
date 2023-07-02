package exception;

import com.sun.net.httpserver.Authenticator.Success;

public enum ErrorCode {
	
	SUCCESS(200,"OK","성공"),
	INSUFFICIENT_BALANCE(500,"Internal Server Error","잔액이 부족합니다"),
    STOPPED_ACCOUNT(500,"Internal Server Error","정지된 계좌입니다."),
	STOPPED_CARD(500,"Internal Server Error","정지된 카드입니다.."),
	UNABLE_CARDNUM(500,"Internal Server Error","존재하지 않는 카드번호입니다."),
	UNABLE_PRODUCTNUM(500, "Internal Server Error", "존재하지 않는 상품입니다."),
	TOO_MANY_CARDS(500,"Internal Server Error","한 계좌에 카드는 3개까지 등록할 수 있습니다."),
	DUPLICATE_PRODUCT(500, "Internal Server Error", "상품명이 중복된 상품입니다."),
	NOT_FOUND_PRODUCTLIST(500, "Internal Server Error", "상품 리스트를 찾을 수 없습니다."),
	WORNG_PASSWORD(500,"Internal Server Error","비밀번호가 일치하지 않습니다."),
	UNABLE_USER(500,"Internal Server Error","존재하지 않는 사용자입니다."),
	LOGIN_ERROR(500,"Internal Server Error","로그인 에러가 발생했습니다."),
	WRONG_AMOUNT(500,"Internal Server Error","잘못된 숫자 형식입니다."),
	DATABASE_ERROR(500,"Internal Server Error","데이터베이스 오류가 발생했습니다."),
	NO_AMOUNT(500,"Internal Server Error","금액을 입력해주세요");
	
	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	private String status; //NOT_FOUNT
	private final String message; //占쌩몌옙占쏙옙 占쏙옙청占쌉니댐옙
    private final int statusCode; //404
    
    ErrorCode(int statusCode,String status,String message) {
    	

    	this.message=message;
    	this.status=status;
    	this.statusCode=statusCode;
    	
    }
	

}