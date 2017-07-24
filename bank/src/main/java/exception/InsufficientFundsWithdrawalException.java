package exception;

public class InsufficientFundsWithdrawalException extends Exception{

	private static final long serialVersionUID = 8167520041622102224L;

	public InsufficientFundsWithdrawalException() {
		super();
	}
	
	public InsufficientFundsWithdrawalException(String message)  {
		super(message);
	}
}
