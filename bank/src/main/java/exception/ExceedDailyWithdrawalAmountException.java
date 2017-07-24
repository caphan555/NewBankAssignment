package exception;

public class ExceedDailyWithdrawalAmountException extends Exception{
	
	private static final long serialVersionUID = -4084488827079592715L;

	public ExceedDailyWithdrawalAmountException() {
		super();
	}
	
	public ExceedDailyWithdrawalAmountException(String message) {
		super(message);
	}
}
