package utilities;


public class CreateUniqueNumber {
	int accountNo = 0;
	int transactionId = 0;	

	public int getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	
	public int generateUniqueAccNum() {
		int uniqueAccNum = this.getAccountNo();
		this.setAccountNo(++uniqueAccNum);
		
		return uniqueAccNum;
	}
	
	public int generateUniqueTransactionId() {
		int uniqueTransactionId = this.getTransactionId();
		this.setTransactionId(++uniqueTransactionId);
		
		return uniqueTransactionId;
	}
}
