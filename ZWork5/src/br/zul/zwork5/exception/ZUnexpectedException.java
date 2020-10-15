package br.zul.zwork5.exception;

/**
 *
 * @author luiz.silva
 */
public class ZUnexpectedException extends ZRuntimeException {

    public ZUnexpectedException() {
    }

    public ZUnexpectedException(String message, Object... args) {
        super(message, args);
    }

    public ZUnexpectedException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ZUnexpectedException(Throwable cause) {
        super(cause);
    }

    public ZUnexpectedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
