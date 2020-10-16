package br.zul.zwork5.exception;

/**
 *
 * @author Luiz Henrique
 */
public class ZEvenClassNotFoundException extends ZException {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Class<?> cls;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZEvenClassNotFoundException(Class<?> cls) {
        super("Não foi encontrado a classe par de: {0}", cls.getName());
        this.cls = cls;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Class<?> getCls() {
        return cls;
    }
    
}
