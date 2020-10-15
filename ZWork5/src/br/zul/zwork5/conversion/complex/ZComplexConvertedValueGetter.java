package br.zul.zwork5.conversion.complex;

import br.zul.zwork5.conversion.ZConversionManager;
import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.complex.impl.ZComplexConverterArrayList;
import br.zul.zwork5.conversion.complex.impl.ZComplexConverterList;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.util.ZUtil;
import br.zul.zwork5.value.ZValue;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luizh
 * @param <T>
 */
public class ZComplexConvertedValueGetter<T> {
    
    //==========================================================================
    //CONSTANTES
    //==========================================================================
    private final static List<ZComplexConverter> CONVERTER_LIST = new ArrayList<>();
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Object value;
    private Class<?> sourceClass;
    private final Class<T> targetClass;
    private final List<Annotation> annotationList;
    
    //==========================================================================
    //INICIALIZADORES
    //==========================================================================
    static {
        CONVERTER_LIST.add(new ZComplexConverterList());
        CONVERTER_LIST.add(new ZComplexConverterArrayList());
    }
    
    //==========================================================================
    //CONSTRUTOES
    //==========================================================================
    public ZComplexConvertedValueGetter(Object value, Class<T> targetClass, List<Annotation> annotationList) {
        this.value = value;
        this.targetClass = targetClass;
        this.annotationList = annotationList;
        try {
            this.sourceClass = getRaw(value).getClass();
        }catch(NullPointerException e){}
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZConversionObj get() throws ZConversionErrorException{
        if (value==null) return null;
        ZComplexConverter<Object> sourceConverter = getSourceConverter();
        if (sourceConverter!=null){
            ZComplexConverter<T> targetConverter = getTargetConverter();
            T obj = (T)targetConverter.newInstance();
            sourceConverter.stream(getRaw(value))
                            .forEach(item->targetConverter.add(obj, item, ZComplexConvertedValueGetter.this));
            return new ZConversionObj(obj, targetClass);
        }
        ZConversionObj co = new ZConversionObj(value, targetClass);
        co.setAnnotationList(annotationList);
        return ZConversionManager.getInstance().convert(co);
    }
    
    public T getConvertedValue() throws ZConversionErrorException{
        ZConversionObj obj = get();
        return (T)obj.getValue();
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationClass){
        return getAnnotation(annotationClass)!=null;
    }
    
    public <A extends Annotation> A getAnnotation(Class<A> annotationClass){
        if (ZUtil.hasContent(annotationList)){
            return annotationList.stream()
                                 .filter(a->annotationClass.isAssignableFrom(a.getClass()))
                                 .findFirst()
                                 .map(a->(A)a)
                                 .orElse(null);
        }
        return null;
    }

    public ZComplexConverter<T> getTargetConverter() {
        return CONVERTER_LIST.stream()
                             .filter(c->c.isValidOutput(ZComplexConvertedValueGetter.this))
                             .findFirst()
                             .orElse(null);
    }

    public ZComplexConverter<Object> getSourceConverter() {
        return CONVERTER_LIST.stream()
                             .filter(c->c.isValidInput(ZComplexConvertedValueGetter.this))
                             .findFirst()
                             .orElse(null);
    }

    private Object getRaw(Object value) {
        if (value instanceof ZConversionObj){
            return getRaw(((ZConversionObj)value).getValue());
        } else if (value instanceof ZValue){
            return ((ZValue)value).rawObj();
        } else {
            return value;
        }
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Object getValue() {
        return value;
    }
    
    public Class<?> getSourceClass(){
        return sourceClass;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public List<Annotation> getAnnotationList() {
        return annotationList;
    }
    
}
