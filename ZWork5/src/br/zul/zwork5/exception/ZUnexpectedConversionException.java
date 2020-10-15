package br.zul.zwork5.exception;

import br.zul.zwork5.conversion.ZConversionObj;

/**
 *
 * @author Luiz Henrique
 */
public class ZUnexpectedConversionException extends ZConversionErrorException {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZConversionObj conversionObject;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZUnexpectedConversionException(ZConversionObj conversionObject, Throwable throwable, String message, Object... args) {
        super(throwable, message, args);
        this.conversionObject = conversionObject;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZConversionObj getConversionObject() {
        return conversionObject;
    }
    
}
