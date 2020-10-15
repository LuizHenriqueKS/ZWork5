package br.zul.zwork5.reflection;

import br.zul.zwork5.conversion.complex.ZComplexConvertedValueGetter;
import br.zul.zwork5.exception.ZVarHandlerException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZUtil;
import br.zul.zwork5.value.ZValue;
import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 *
 * @author luizh
 */
public class ZVarHandler {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZObjHandler objHandler;
    private final String name;
    
    protected ZField field;
    protected ZMethodHandler getter;
    protected ZList<ZMethodHandler> setterList;
    
    private boolean useGettersAndSetters;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    ZVarHandler(ZObjHandler objHandler, String name) {
        this.objHandler = objHandler;
        this.name = name;
        this.useGettersAndSetters = true;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZList<Annotation> listAnnotations(){
        ZList<Annotation> result = new ZList<>();
        result.addAll(field.listAnnotations());
        if (getter!=null) result.addAll(getter.listAnnotations());
        if (ZUtil.hasContent(setterList)&&setterList.size()==1){
            result.addAll(setterList.first().listAnnotations());
        }
        return result;
    }
    
    public ZValue getValue() throws ZVarHandlerException{
        try {
            if (useGettersAndSetters&&getter!=null){
                return getter.invoke();
            } else {
                return field.getValue(getObj());
            }
        }catch(Exception e){
            throw new ZVarHandlerException(e);
        }
    }
    
    public void setValue(Object value) throws ZVarHandlerException{
        try {
            if (useGettersAndSetters && ZUtil.hasContent(setterList)) {
                ZMethodHandler setter = getBestSetter(value);
                Class<?> type = setter.listParamTypes().get(0);
                ZComplexConvertedValueGetter vg = new ZComplexConvertedValueGetter(value, type, listAnnotations());
                setter.invoke(Arrays.asList(vg.getConvertedValue()));
            } else {
                ZComplexConvertedValueGetter vg = new ZComplexConvertedValueGetter(value, getType(), listAnnotations());
                field.setValue(getObj(), vg.get());
            }
        } catch (Exception e) {
            throw new ZVarHandlerException(e);
        }
    }

    public boolean hasGetter(){
        return getter!=null;
    }
    
    public boolean hasSetters(){
        return ZUtil.hasContent(setterList);
    }
    
    public boolean hasField(){
        return field!=null;
    }
    
    public boolean hasAnnotation(Class<? extends Annotation> annotationClass){
        return getAnnotation(annotationClass)!=null;
    }
    
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass){
        T result = null;
        if (field!=null) result = field.getAnnotation(annotationClass);
        if (result!=null) return result;
        if (getter!=null) result = getter.getAnnotation(annotationClass);
        if (result!=null) return result;
        if (ZUtil.hasContent(setterList)){
            for (ZMethodHandler setter:setterList){
                result = setter.getAnnotation(annotationClass);
                if (result!=null) return result;
            }
        }
        return null;
    }
    
    public Class<?> getType(){
        return field.getType();
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZMethodHandler getBestSetter(Object value) {
        if (setterList.size()==1||value==null){
            return setterList.first();
        } else {
            return setterList.stream().filter(setter->{
                return setter.getReturnType().isAssignableFrom(value.getClass());
            }).findFirst().orElse(setterList.first());
        } 
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Object getObj() {
        return getObjHandler().getObj();
    }

    public ZObjHandler getObjHandler() {
        return objHandler;
    }

    public String getName() {
        return name;
    }

    public boolean isUseGettersAndSetters() {
        return useGettersAndSetters;
    }
    public ZVarHandler setUseGettersAndSetters(boolean useGettersAndSetters) {
        this.useGettersAndSetters = useGettersAndSetters;
        return this;
    }
    
}
