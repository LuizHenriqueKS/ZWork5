package br.zul.zwork5.exception;

import br.zul.zwork5.util.ZStrUtils;

/**
 *
 * @author luiz.silva
 */
public class ZRuntimeException extends RuntimeException {
    
    public ZRuntimeException() {
    }

    public ZRuntimeException(String message, Object... args) {
        super(ZStrUtils.format(message, args));
    }

    public ZRuntimeException(Throwable cause, String message, Object... args) {
        super(message, cause);
    }

    public ZRuntimeException(Throwable cause) {
        super(cause);
    }

    public ZRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
