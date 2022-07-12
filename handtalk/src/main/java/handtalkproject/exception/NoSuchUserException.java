package handtalkproject.exception;

import java.util.NoSuchElementException;

public class NoSuchUserException extends NoSuchElementException {
    private static final String NO_SUCH_USER_MESSAGE = "존재하지 않는 사용자입니다.";

    public NoSuchUserException() {
        super(NO_SUCH_USER_MESSAGE);
    }
}
