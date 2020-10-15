package br.zul.zwork5.exception;

import br.zul.zwork5.util.ZStrUtils;

/**
 *
 * @author luiz.silva
 */
public class ZException extends Exception {

    public ZException() {
    }

    public ZException(String message, Object... args) {
        super(ZStrUtils.format(message, args));
    }

    public ZException(Throwable cause, String message, Object... args) {
        super(message, cause);
    }

    public ZException(Throwable cause) {
        super(cause);
    }

    public ZException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
