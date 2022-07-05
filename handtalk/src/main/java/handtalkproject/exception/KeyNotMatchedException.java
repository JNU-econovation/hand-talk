package handtalkproject.exception;

public class KeyNotMatchedException extends RuntimeException {

    public KeyNotMatchedException(String message) {
        System.out.println(message);
    }
}