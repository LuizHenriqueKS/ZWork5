package br.zul.zwork5.conversion;

/**
 *
 * @author Luiz Henrique
 */
public class ZPossibleConversion {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZPossibleConversion parent;
    private final Class<?> sourceClass;
    private final Class<?> targetClass;
    private final ZConverter<?, ?> converter;
    private final int layer;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZPossibleConversion(ZPossibleConversion parent, ZConverter<?, ?> converter){
        this.parent = parent;
        this.sourceClass = converter.getAClass();
        this.targetClass = converter.getBClass();
        this.converter = converter;
        this.layer = parent.layer + 1;
    }
    
    public ZPossibleConversion(Class<?> sourceClass, ZConverter<?, ?> converter){
        this.parent = null;
        this.sourceClass = converter.getAClass();
        this.targetClass = converter.getBClass();
        this.layer = 1;
        this.converter = converter;
    }
    
    //==========================================================================
    //
    //==========================================================================
 
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZPossibleConversion getParent() {
        return parent;
    }

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public ZConverter<?, ?> getConverter() {
        return converter;
    }

    public int getLayer() {
        return layer;
    }
    
}
