package br.zul.zwork5.exception;

/**
 *
 * @author luiz.silva
 */
public class ZNewInstanceException extends ZException {

    public ZNewInstanceException() {
    }

    public ZNewInstanceException(String message, Object... args) {
        super(message, args);
    }

    public ZNewInstanceException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ZNewInstanceException(Throwable cause) {
        super(cause);
    }

    public ZNewInstanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
