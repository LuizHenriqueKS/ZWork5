package br.zul.zwork5.exception;

/**
 *
 * @author luiz.silva
 */
public class ZMethodNotFoundException extends ZException {

    public ZMethodNotFoundException() {
    }

    public ZMethodNotFoundException(String message, Object... args) {
        super(message, args);
    }

    public ZMethodNotFoundException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ZMethodNotFoundException(Throwable cause) {
        super(cause);
    }

    public ZMethodNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
