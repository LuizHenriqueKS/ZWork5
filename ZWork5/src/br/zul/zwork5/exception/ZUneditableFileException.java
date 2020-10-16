package br.zul.zwork5.exception;

/**
 *
 * @author Luiz Henrique
 */
public class ZUneditableFileException extends ZException{

    public ZUneditableFileException(String pathFile) {
        super("Não é possível editar o arquivo: {0}", pathFile);
    }

    public ZUneditableFileException(Throwable cause) {
        super(cause);
    }
    
}
