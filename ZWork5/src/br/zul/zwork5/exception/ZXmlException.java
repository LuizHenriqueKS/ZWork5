package br.zul.zwork5.exception;

/**
 *
 * @author luiz.silva
 */
public class ZXmlException extends ZException{

    public ZXmlException() {
    }

    public ZXmlException(String message, Object... args) {
        super(message, args);
    }

    public ZXmlException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ZXmlException(Throwable cause) {
        super(cause);
    }

    public ZXmlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
