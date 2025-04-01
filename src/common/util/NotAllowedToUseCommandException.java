package common.util;

public class NotAllowedToUseCommandException extends Exception {
    public NotAllowedToUseCommandException(String message) {
        super(message);
    }
}