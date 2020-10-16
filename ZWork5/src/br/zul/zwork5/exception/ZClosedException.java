
package br.zul.zwork5.exception;

/**
 *
 * @author Luiz Henrique
 */
public class ZClosedException extends ZException {

    public ZClosedException() {
        
    }

    public ZClosedException(String message, Object... args) {
        super(message, args);
    }

    public ZClosedException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ZClosedException(Throwable cause) {
        super(cause);
    }

    public ZClosedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
