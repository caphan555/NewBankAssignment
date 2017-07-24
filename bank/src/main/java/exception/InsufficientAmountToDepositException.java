package exception;

public class InsufficientAmountToDepositException extends Exception {
	

	private static final long serialVersionUID = 7895255310725343572L;

	public InsufficientAmountToDepositException() {
		super();
	}
	
	public InsufficientAmountToDepositException(String message) {
		super(message);
	}
}
