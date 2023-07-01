package exception;

import com.sun.net.httpserver.Authenticator.Success;

public enum ErrorCode {
	
	SUCCESS(200,"OK","����"),
	INSUFFICIENT_BALANCE(500,"Internal Server Error","�ܾ��� �����մϴ�"),
    STOPPED_ACCOUNT(500,"Internal Server Error","������ �����Դϴ�."),
	STOPPED_CARD(500,"Internal Server Error","������ ī���Դϴ�.."),
	UNABLE_CARDNUM(500,"Internal Server Error","�������� �ʴ� ī���ȣ�Դϴ�."),
	UNABLE_PRODUCTNUM(500, "Internal Server Error", "�������� �ʴ� ��ǰ�Դϴ�."),
	TOO_MANY_CARDS(500,"Internal Server Error","�� ���¿� ī��� 3������ ����� �� �ֽ��ϴ�."),
	DUPLICATE_PRODUCT(500, "Internal Server Error", "��ǰ���� �ߺ��� ��ǰ�Դϴ�."),
	NOT_FOUND_PRODUCTLIST(500, "Internal Server Error", "��ǰ ����Ʈ�� ã�� �� �����ϴ�."),
	WORNG_PASSWORD(500,"Internal Server Error","��й�ȣ�� ��ġ���� �ʽ��ϴ�."),
	UNABLE_USER(500,"Internal Server Error","�������� �ʴ� ������Դϴ�."),
	LOGIN_ERROR(500,"Internal Server Error","�α��� ������ �߻��߽��ϴ�."),
	WRONG_AMOUNT(500,"Internal Server Error","�߸��� ���� �����Դϴ�."),
	DATABASE_ERROR(500,"Internal Server Error","�����ͺ��̽� ������ �߻��߽��ϴ�."),
	NO_AMOUNT(500,"Internal Server Error","�ݾ��� �Է����ּ���");
	
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
	private final String message; //�߸��� ��û�Դϴ�
    private final int statusCode; //404
    
    ErrorCode(int statusCode,String status,String message) {
    	

    	this.message=message;
    	this.status=status;
    	this.statusCode=statusCode;
    	
    }
	

}