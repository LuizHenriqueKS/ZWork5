package br.zul.zwork5.exception;

/**
 *
 * @author Luiz Henrique
 */
public class ZRequiredContentException extends ZRuntimeException{

    public ZRequiredContentException() {
    }

    public ZRequiredContentException(String message, Object... args) {
        super(message, args);
    }

    public ZRequiredContentException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ZRequiredContentException(Throwable cause) {
        super(cause);
    }

    public ZRequiredContentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
