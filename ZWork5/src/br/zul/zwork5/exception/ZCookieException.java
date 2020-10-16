package br.zul.zwork5.exception;

/**
 *
 * @author luiz.silva
 */
public class ZCookieException extends ZException{

    public ZCookieException() {
    }

    public ZCookieException(String message, Object... args) {
        super(message, args);
    }

    public ZCookieException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ZCookieException(Throwable cause) {
        super(cause);
    }

    public ZCookieException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
