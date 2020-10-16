package br.zul.zwork5.exception;

/**
 *
 * @author Luiz Henrique
 */
public class ZFileAlreadyOpenException extends ZException {

    public ZFileAlreadyOpenException() {
    
    }

    public ZFileAlreadyOpenException(String filePath) {
        super("O arquivo já está sendo editado: {0}", filePath);
    }
    
}
