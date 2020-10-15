package br.zul.zwork5.conversion;

import br.zul.zwork5.util.ZUtil;
import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Luiz Henrique
 */
public class ZConversionObj{
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private Object value;
    private Class<?> sourceClass;
    private Class<?> targetClass;
    private List<? extends Annotation> annotationList;
    private Map<String, Object> attributeMap;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZConversionObj(Object value, Class<?> targetClass) {
        this.value = value;
        this.targetClass = targetClass;
    }

    public ZConversionObj(Object value, Class<?> targetClass, List<? extends Annotation> annotationList) {
        this.value = value;
        this.targetClass = targetClass;
        this.annotationList = annotationList;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZConversionObj copy(){
        ZConversionObj copy = new ZConversionObj(value, targetClass, annotationList);
        copy.sourceClass = sourceClass;
        copy.attributeMap = attributeMap;
        return copy;
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public Class<?> getSourceClass(){
        if (sourceClass!=null){
            return sourceClass;
        }
        return getValue().getClass();
    }
    public void setSourceClass(Class<?> sourceClass) {
        this.sourceClass = sourceClass;
    }
    
    public boolean hasAnnotation(Class<? extends Annotation> annotationType){
        return getAnnotation(annotationType)!=null;
    }
    
    public <T extends Annotation> T getAnnotation(Class<T> annotationType){
        if (ZUtil.hasContent(annotationList)){
            return annotationList.stream()
                                 .filter(a->annotationType.isAssignableFrom(a.getClass()))
                                 .findFirst()
                                 .map(a->(T)a)
                                 .orElse(null);
        } else {
            return null;
        }
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    private Map<String, Object> getAttributeMap(){
        if (attributeMap==null){
            attributeMap = new LinkedHashMap<>();
        }
        return attributeMap;
    }
    
    public Object getAttribute(String name){
        return getAttributeMap().get(name);
    }
    
    public void setAttribute(String name, Object value){
        getAttributeMap().put(name, value);
    }
    
    public boolean matchAttribute(String name, Object value){
        return Objects.equals(value, getAttribute(name));
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }
    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public List<? extends Annotation> getAnnotationList() {
        return annotationList;
    }
    public void setAnnotationList(List<? extends Annotation> annotationList) {
        this.annotationList = annotationList;
    }
    
}
