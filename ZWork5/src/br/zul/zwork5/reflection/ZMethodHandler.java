package br.zul.zwork5.reflection;

import br.zul.zwork5.conversion.ZConversionOut;
import br.zul.zwork5.exception.ZMethodException;
import br.zul.zwork5.exception.ZMethodIsStaticException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.value.ZValue;
import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 *
 * @author luizh
 */
public class ZMethodHandler {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZObjHandler objHandler;
    private final ZMethod method;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    ZMethodHandler(ZObjHandler objHandler, ZMethod method) {
        this.objHandler = objHandler;
        this.method = method;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZValue invoke() throws ZMethodException, ZMethodIsStaticException {
        return method.invoke(getObj());
    }
    
    public <T> ZConversionOut<T> invoke(Class<T> returnType) throws ZMethodException, ZMethodIsStaticException{
        return method.invoke(getObj(), returnType);
    }
    
    public ZValue invoke(Collection<Object> paramList) throws ZMethodException, ZMethodIsStaticException{
        return method.invoke(getObj(), paramList);
    }
    
    public <T> ZConversionOut<T> invoke(Class<T> returnType, Collection<Object> paramList) throws ZMethodException, ZMethodIsStaticException{
        return method.invoke(getObj(), returnType, paramList);
    }

    public Class<?> getReturnType() {
        return method.getReturnType();
    }
    
    public ZList<Class<?>> listParamTypes(){
        return method.listParamTypes();
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return method.getAnnotation(annotationClass);
    }
    
    public boolean hasAnnotation(Class<? extends Annotation> annotationClass){
        return method.hasAnnotation(annotationClass);
    }
    
    public ZList<Annotation> listAnnotations(){
        return method.listAnnotations();
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZObjHandler getObjHandler() {
        return objHandler;
    }
    
    public Object getObj(){
        return objHandler.getObj();
    }

    public ZMethod getMethod() {
        return method;
    }
    
}
