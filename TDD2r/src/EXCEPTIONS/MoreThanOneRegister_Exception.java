package EXCEPTIONS;

public class MoreThanOneRegister_Exception extends Exception{

    public MoreThanOneRegister_Exception() {
    }

    public MoreThanOneRegister_Exception(String message) {
        super(message);
    }

    public MoreThanOneRegister_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public MoreThanOneRegister_Exception(Throwable cause) {
        super(cause);
    }

    public MoreThanOneRegister_Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
