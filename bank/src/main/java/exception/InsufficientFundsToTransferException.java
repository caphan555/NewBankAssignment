package exception;

public class InsufficientFundsToTransferException extends Exception {

	private static final long serialVersionUID = -5050565616867335038L;

	public InsufficientFundsToTransferException() {
		super();
	}
	
	public InsufficientFundsToTransferException(String message) {
		super(message);
	}
}
