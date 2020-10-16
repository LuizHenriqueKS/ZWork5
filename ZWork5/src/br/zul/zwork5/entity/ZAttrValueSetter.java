package br.zul.zwork5.entity;

import br.zul.zwork5.conversion.ZConversionManager;
import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZVarHandlerException;

/**
 *
 * @author luizh
 */
class ZAttrValueSetter {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZAttrHandler attrHandler;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZAttrValueSetter(ZAttrHandler attrHandler) {
        this.attrHandler = attrHandler;
    }

    //==========================================================================
    //MÉTODOS PUBLICOS
    //==========================================================================
    public void set(Object value) throws ZConversionErrorException, ZVarHandlerException {
        Object convertedValue;
        if (value==null){
            convertedValue = value;
        } else {
            ZConversionObj conversionObj = new ZConversionObj(value, attrHandler.getType());
            conversionObj.setAnnotationList(attrHandler.listAnnotations());
            convertedValue = ZConversionManager.getInstance().convert(conversionObj).getValue();
        }
        attrHandler.varHandler.setValue(convertedValue);
    }
    
}
