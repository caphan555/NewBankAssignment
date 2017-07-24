package exception;

public class InvalidStartAccountAmountException extends Exception {

	private static final long serialVersionUID = 6030843389464907655L;

	public InvalidStartAccountAmountException() {
		super();
	}
	
	public InvalidStartAccountAmountException(String message) {
		super(message);
	}
	
}
