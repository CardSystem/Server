package dto;

public class CardHistoryDTO{
//	private long id;
	private long cardId;
	private String userId;
	private String franchisee;
	private long payment;
	private long balance;
	private int isSuccess;
	private String date;
	private long fCategory;
	private int isIns;
	private int insMonth;
	private String cardType;
	
	public CardHistoryDTO() {
		
	}
	
	public long getCardId() {
		return cardId;
	}
	public void setCardId(long cardId) {
		this.cardId = cardId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getFranchisee() {
		return franchisee;
	}
	public void setFranchisee(String franchisee) {
		this.franchisee = franchisee;
	}
	
	public long getPayment() {
		return payment;
	}
	public void setPayment(long payment) {
		this.payment = payment;
	}
	
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	
	public int getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public long getfCategory() {
		return fCategory;
	}
	public void setfCategory(long fCategory) {
		this.fCategory = fCategory;
	}
	
	public int getIsIns() {
		return isIns;
	}
	public void setIsIns(int isIns) {
		this.isIns = isIns;
	}
	
	public int getInsMonth() {
		return insMonth;
	}
	public void setInsMonth(int insMonth) {
		this.insMonth = insMonth;
	}
	
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	


	
}