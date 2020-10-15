package br.zul.zwork5.exception;

/**
 *
 * @author Luiz Henrique
 */
public class ZConverterNotFoundException extends ZException {

    //==========================================================================
    //VARIÁVEIS 
    //==========================================================================    
    private final Class<?> sourceClass;
    private final Class<?> targetClass;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================    
    public ZConverterNotFoundException(Class<?> sourceClass, Class<?> targetClass) {
        super("Não foi encontrado um conversor para converter de ''{0}'' para ''{1}''.", sourceClass.getName(), targetClass.getName());
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================    
    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }
    
}
