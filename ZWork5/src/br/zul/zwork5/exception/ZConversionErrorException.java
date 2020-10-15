package br.zul.zwork5.exception;

import br.zul.zwork5.conversion.ZConversionObj;

/**
 *
 * @author Luiz Henrique
 */
public class ZConversionErrorException extends ZException {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZConversionObj conversionObject;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZConversionErrorException(ZConversionObj conversionObject) {
        super("Não foi possível converter o seguinte valor da classe ''{0}'' para ''{1}'': {2}", conversionObject.getValue().getClass().getName(), conversionObject.getTargetClass().getName(), conversionObject.getValue().toString());
        this.conversionObject = conversionObject;
    }
    
    public ZConversionErrorException(Throwable cause, ZConversionObj conversionObject) {
        super(cause, "Não foi possível converter o seguinte valor da classe ''{0}'' para ''{1}'': {2}", conversionObject.getValue().getClass().getName(), conversionObject.getTargetClass().getName(), conversionObject.getValue().toString());
        this.conversionObject = conversionObject;
    }

    public ZConversionErrorException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
        this.conversionObject = null;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZConversionObj getConversionObject() {
        return conversionObject;
    }
    
}
