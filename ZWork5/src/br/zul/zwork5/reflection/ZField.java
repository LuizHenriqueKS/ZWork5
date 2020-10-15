package br.zul.zwork5.reflection;

import br.zul.zwork5.conversion.ZConversionManager;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZFieldException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.value.ZValue;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 *
 * @author luizh
 */
public class ZField {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Field field;
    private final boolean accessible;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    ZField(Field field){
        this.field = field;
        this.accessible = field.isAccessible();
    }
    
    //==========================================================================
    //MÉTODOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.field);
        hash = 59 * hash + (this.accessible ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZField other = (ZField) obj;
        if (this.accessible != other.accessible) {
            return false;
        }
        if (!Objects.equals(this.field, other.field)) {
            return false;
        }
        return true;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZList<Annotation> listAnnotations(){
        ZList<Annotation> result = new ZList<>();
        result.addAll(field.getAnnotations());
        result.addAll(field.getDeclaredAnnotations());
        return result;
    }
    
    public ZValue getValue(Object obj) throws ZFieldException {
        try {
            field.setAccessible(true);
            Object value = field.get(obj);
            field.setAccessible(accessible);
            return ()->value;
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new ZFieldException(ex);
        }
    }

    public void setValue(Object obj, Object value) throws ZFieldException, ZConversionErrorException {
        try {
            field.setAccessible(true);
            Object convertedValue = ZConversionManager.getInstance().convert(value, getType());
            field.set(obj, convertedValue);
            field.setAccessible(accessible);
        } catch (IllegalAccessException|IllegalArgumentException ex) {
            throw new ZFieldException(ex);
        }
    }
    
    public boolean hasAnnotation(Class<? extends Annotation> annotationClass){
        return field.isAnnotationPresent(annotationClass);
    }
    
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass){
        T result = field.getAnnotation(annotationClass);
        if (result!=null) return result;
        result = field.getDeclaredAnnotation(annotationClass);
        return result;
    }
    
    public boolean isEnumConstant(){
        return field.isEnumConstant();
    }
    
    public Class<?> getType(){
        return field.getType();
    }

    public boolean isStatic() {
        return java.lang.reflect.Modifier.isStatic(field.getModifiers());
    }
    
    public boolean isFinal(){
        return java.lang.reflect.Modifier.isFinal(field.getModifiers());
    }
    
    public boolean isPrivate(){
        return java.lang.reflect.Modifier.isPrivate(field.getModifiers());
    }
    
    public boolean isProtected(){
        return java.lang.reflect.Modifier.isProtected(field.getModifiers());
    }
    
    public boolean isPublic(){
        return java.lang.reflect.Modifier.isPublic(field.getModifiers());
    }
    
    public boolean isSynchronized(){
        return java.lang.reflect.Modifier.isSynchronized(field.getModifiers());
    }
    
    public boolean isTransient(){
        return java.lang.reflect.Modifier.isTransient(field.getModifiers());
    }
    
    public boolean isVolatile(){
        return java.lang.reflect.Modifier.isVolatile(field.getModifiers());
    }
    
    public boolean isSynthetic(){
        return field.isSynthetic();
    }
    
    public boolean isAcessible(){
        return accessible;
    }
    
    public String getName(){
        return field.getName();
    }
    
}
