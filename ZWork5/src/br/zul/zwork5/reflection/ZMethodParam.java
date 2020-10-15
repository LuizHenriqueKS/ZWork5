package br.zul.zwork5.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 *
 * @author luizh
 */
public class ZMethodParam {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Parameter parameter;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    ZMethodParam(Parameter parameter){
        this.parameter = parameter;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.parameter);
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
        final ZMethodParam other = (ZMethodParam) obj;
        return Objects.equals(this.parameter, other.parameter);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Class<?> getType(){
        return parameter.getType();
    }
    
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass){
        return parameter.getAnnotation(annotationClass);
    }
    
    public boolean hasAnnotation(Class<? extends Annotation> annotationClass){
        return parameter.isAnnotationPresent(annotationClass);
    }
    
}
