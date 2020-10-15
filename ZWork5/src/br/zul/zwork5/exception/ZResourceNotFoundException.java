package br.zul.zwork5.exception;

/**
 *
 * @author Luiz Henrique
 */
public class ZResourceNotFoundException extends ZFileNotFoundException {
    
    public ZResourceNotFoundException(Throwable cause, String filePath) {
        super(cause, filePath);
    }

    public ZResourceNotFoundException(String filePath) {
        super(filePath);
    }
    
}
