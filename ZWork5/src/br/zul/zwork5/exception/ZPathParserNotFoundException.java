package br.zul.zwork5.exception;

/**
 *
 * @author Luiz Henrique
 */
public class ZPathParserNotFoundException extends ZException {
    
    public ZPathParserNotFoundException(String path){
        super("Nâo foi encontrado um parser para o path: {0}", path);
    }
    
}
