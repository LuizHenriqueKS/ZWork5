package br.zul.zwork5.exception;

/**
 *
 * @author luizh
 */
public class ZMethodException extends ZException {

    public ZMethodException(Throwable cause) {
        super(cause);
    }

    public ZMethodException(String message, Object... args) {
        super(message, args);
    }
    
}
