package exception;

public enum ErrorCode {

	SUCCESS(200, "OK", "성공"), INSUFFICIENT_BALANCE(500, "Internal Server Error", "잔액이 부족합니다"),
	STOPPED_ACCOUNT(500, "Internal Server Error", "정지된 계좌입니다."),
	STOPPED_CARD(500, "Internal Server Error", "정지된 카드입니다."),
	UNABLE_CARDNUM(500, "Internal Server Error", "존재하지 않는 카드번호입니다."),
	UNABLE_PRODUCTNUM(500, "Internal Server Error", "존재하지 않는 상품입니다."),
	DUPLICATE_PRODUCT(500, "Internal Server Error", "상품명이 중복된 상품입니다."),
	NOT_FOUND_PRODUCTLIST(500, "Internal Server Error", "상품 리스트를 찾을 수 없습니다.");
	
	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	private String status; // NOT_FOUNT
	private final String message; // �߸��� ��û�Դϴ�
	private final int statusCode; // 404

	ErrorCode(int statusCode, String status, String message) {

		this.message = message;
		this.status = status;
		this.statusCode = statusCode;

	}

}