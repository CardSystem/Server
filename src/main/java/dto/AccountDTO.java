package dto;

public class AccountDTO {
	private long id;
	private String userId;
	private String AccountNum;
	private long balance;
	private String bankName;
	private int isStopped;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccountNum() {
		return AccountNum;
	}
	public void setAccountNum(String accountNum) {
		AccountNum = accountNum;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public int getIsStopped() {
		return isStopped;
	}
	public void setIsStopped(int isStopped) {
		this.isStopped = isStopped;
	}
}
