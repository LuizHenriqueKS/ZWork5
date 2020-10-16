package br.zul.zwork5.entity;

import br.zul.zwork5.exception.ZAttrHandlerException;
import br.zul.zwork5.exception.ZVarHandlerException;
import br.zul.zwork5.reflection.ZVarHandler;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZUtil;
import br.zul.zwork5.value.ZValue;
import java.lang.annotation.Annotation;

/**
 *
 * @author luizh
 */
public class ZAttrHandler {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    final ZEntityHandler entityHandler;
    final ZVarHandler varHandler;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    ZAttrHandler(ZEntityHandler entityHandler, ZVarHandler varHandler) {
        this.entityHandler = entityHandler;
        this.varHandler = varHandler;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZList<? extends Annotation> listAnnotations(){
        return varHandler.listAnnotations();
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public ZAttribute getAttrAnnotation(){
        return varHandler.getAnnotation(ZAttribute.class);
    }
    
    public boolean hasDefaultValue(){
        return !getAttrAnnotation().defaultValue().equals("-.-");
    }
    
    public Class<?> getType(){
        return varHandler.getType();
    }
    
    public ZVarHandler getVarHandler(){
        return varHandler;
    }
    
    public ZEntity getEntity(){
        return entityHandler.entity;
    }
    
    public String getName(){
        ZAttribute attr = getAttrAnnotation();
        if (ZUtil.hasContent(attr.name())){
            return attr.name();
        }
        return varHandler.getName();
    }
    
    public String getFieldName(){
        return varHandler.getName();
    }
    
    public ZValue getValue() throws ZAttrHandlerException{
        try {
            return new ZAttrValueGetter(this).get();
        } catch (ZVarHandlerException ex) {
            throw new ZAttrHandlerException(ex);
        }
    }
    public void setValue(Object value){
        new ZAttrValueSetter(this).set(value);
    }
    
}


