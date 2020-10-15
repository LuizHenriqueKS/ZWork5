package br.zul.zwork5.reflection;

import br.zul.zwork5.exception.ZException;

/**
 *
 * @author luizh
 */
public class ZLambdaException extends ZException {

    public ZLambdaException() {
    
    }

    public ZLambdaException(Throwable cause) {
        super(cause);
    }

    public ZLambdaException(String message, Object... args) {
        super(message, args);
    }
    
}
