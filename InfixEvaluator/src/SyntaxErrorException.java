
/**
 * Class to report a syntax error.
 */
public class SyntaxErrorException extends RuntimeException {

	/**
	 * Construct a SyntaxErrorException with the specified
	 * message.
	 * @param message the error message.
	 */
	public SyntaxErrorException(String message) {
		super(message);
		
	}
}
