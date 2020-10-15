package br.zul.zwork5.exception;

import br.zul.zwork4.exception.*;

/**
 *
 * @author luizh
 */
public class ZJsonException extends ZException {

    public ZJsonException(Throwable cause) {
        super(cause);
    }

    public ZJsonException(String message, Object... args) {
        super(message, args);
    }

    public ZJsonException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
    
}
