package handtalkproject.exception;

public class NoAuthenticationException extends RuntimeException{
    public NoAuthenticationException(String message) {
        super(message);
    }
}
