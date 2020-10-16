package br.zul.zwork5.exception;

/**
 *
 * @author luiz.silva
 */
public class ZKeyStoreException extends ZException {

    public ZKeyStoreException() {
    }

    public ZKeyStoreException(String message, Object... args) {
        super(message, args);
    }

    public ZKeyStoreException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ZKeyStoreException(Throwable cause) {
        super(cause);
    }

    public ZKeyStoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
