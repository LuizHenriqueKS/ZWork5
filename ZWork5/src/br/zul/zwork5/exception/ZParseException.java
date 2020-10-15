package br.zul.zwork5.exception;

/**
 *
 * @author luiz.silva
 */
public class ZParseException extends ZException {

    public ZParseException() {
    }

    public ZParseException(String message, Object... args) {
        super(message, args);
    }

    public ZParseException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ZParseException(Throwable cause) {
        super(cause);
    }

    public ZParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
