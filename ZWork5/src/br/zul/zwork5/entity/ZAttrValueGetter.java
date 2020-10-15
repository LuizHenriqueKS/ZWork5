
package br.zul.zwork5.entity;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork4.value.ZValue;

/**
 *
 * @author luizh
 */
class ZAttrValueGetter {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZAttrHandler attrHandler;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZAttrValueGetter(ZAttrHandler attrHandler) {
        this.attrHandler = attrHandler;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZValue get() {
        ZValue value = attrHandler.varHandler.getValue();
        if (value==null){
            return null;
        } 
        return getValue(value);
    }

    //==========================================================================
    //MÉTODOS PRIVADOS DE APOIO
    //==========================================================================
    private ZValue getValue(ZValue value) {
        ZConversionObj obj = new ZConversionObj(value.asObject(), null);
        obj.setAnnotationList(attrHandler.listAnnotations());
        return ()->obj;
    }
    
}
