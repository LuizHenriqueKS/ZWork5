package br.zul.zwork5.entity;

import br.zul.zwork5.reflection.ZField;
import br.zul.zwork4.util.ZStrUtils;
import br.zul.zwork4.value.ZValue;
import java.lang.annotation.Annotation;

/**
 *
 * @author luizh
 */
public class ZAttrManager {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZField field;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    ZAttrManager(ZField field) {
        this.field = field;
    }

    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS 
    //==========================================================================
    public String getFieldName(){
        return field.getName();
    }
    
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass){
        return field.getAnnotation(annotationClass);
    }
    
    public ZAttribute getAttrAnnotation(){
        return field.getAnnotation(ZAttribute.class);
    }
    
    public String getName(){
        return ZStrUtils.firstNotEmpty(getAttrAnnotation().name(), getFieldName());
    }
    
    public ZValue getValue(ZEntity entity){
        return field.getValue(entity);
    }
    
    public Class<?> getType(){
        return field.getType();
    }
    
    public String getDefaultValue(){
        String defaultValue = getAttrAnnotation().defaultValue();
        if ("-.-".equals(defaultValue)){
            return null;
        }
        return defaultValue;
    }
    
    public Integer getLength(){
        if (getAttrAnnotation().length()==-1){
            return null;
        }
        return getAttrAnnotation().length();
    }
    
    public Integer getScale(){
        if (getAttrAnnotation().scale()==-1){
            return null;
        }
        return getAttrAnnotation().scale();
    }
    
    public boolean isNullable(){
        return getAttrAnnotation().nullable();
    }
    
    public boolean isLazy(){
        return getAttrAnnotation().lazy();
    }
    
    public boolean isAutoIncrement(){
        return getAttrAnnotation().autoIncrement();
    }
    
    public boolean isUnique(){
        return getAttrAnnotation().unique();
    }
    
    public boolean isPrimaryKey(){
        return getAttrAnnotation().primaryKey();
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZField getField() {
        return field;
    }
    
}
