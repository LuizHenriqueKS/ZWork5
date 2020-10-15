package br.zul.zwork5.exception;

/**
 *
 * @author Luiz Henrique
 */
public class ZFileNotFoundException extends ZException {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final String filePath;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZFileNotFoundException(Throwable cause, String filePath) {
        super(cause, "Arquivo não encontrado: {0}", filePath);
        this.filePath = filePath;
    }
    
    public ZFileNotFoundException(String filePath) {
        super("Arquivo não encontrado: {0}", filePath);
        this.filePath = filePath;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getFilePath() {
        return filePath;
    }
    
}
