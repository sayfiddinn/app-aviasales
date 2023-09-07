package uz.pdp.appaviauz.exception;

public class ContentTypeException extends RuntimeException {
    public ContentTypeException(String message) {
        super(message);
    }

    public ContentTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentTypeException() {
        super();
    }

    public ContentTypeException(Throwable cause) {
        super(cause);
    }

    protected ContentTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

