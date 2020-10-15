package br.zul.zwork5.exception;

/**
 *
 * @author Luiz Henrique
 */
public class ZInvalidValueException extends ZRuntimeException {

    public ZInvalidValueException(String message, Object... args) {
        super(message, args);
    }

    public ZInvalidValueException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ZInvalidValueException(Throwable cause) {
        super(cause);
    }
    
}
