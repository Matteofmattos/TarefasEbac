package EXCEPTIONS;

public class ElementTypeNotFound_Exception extends Exception {

    public ElementTypeNotFound_Exception() {
    }

    public ElementTypeNotFound_Exception(String message) {
        super(message);
    }

    public ElementTypeNotFound_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public ElementTypeNotFound_Exception(Throwable cause) {
        super(cause);
    }

    public ElementTypeNotFound_Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
