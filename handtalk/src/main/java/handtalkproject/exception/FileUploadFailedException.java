package handtalkproject.exception;

public class FileUploadFailedException extends RuntimeException{
    public FileUploadFailedException(String message) {
        super(message);
    }
}
