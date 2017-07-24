package exception;

public class AccountDoesNotExistException extends Exception {
	
	private static final long serialVersionUID = -5487810482503010991L;

	public AccountDoesNotExistException() {
		super();
	}
	
	public AccountDoesNotExistException(String message) {
		super(message);
	}
}
