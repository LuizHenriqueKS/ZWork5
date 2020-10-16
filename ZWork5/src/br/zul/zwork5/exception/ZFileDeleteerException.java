package br.zul.zwork5.exception;

/**
 *
 * @author luiz.silva
 */
public class ZFileDeleteerException extends ZException {

    public ZFileDeleteerException() {
    }

    public ZFileDeleteerException(String message, Object... args) {
        super(message, args);
    }

    public ZFileDeleteerException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ZFileDeleteerException(Throwable cause) {
        super(cause);
    }

    public ZFileDeleteerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
