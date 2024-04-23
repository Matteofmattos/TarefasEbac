package EXCEPTIONS;

public class KeyNotFound_Exception extends Exception{

    public KeyNotFound_Exception() {
    }

    public KeyNotFound_Exception(String message) {
        super(message);
    }

    public KeyNotFound_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyNotFound_Exception(Throwable cause) {
        super(cause);
    }

    public KeyNotFound_Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
