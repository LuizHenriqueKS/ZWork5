package br.zul.zwork5.conversion;

import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZConverterNotFoundException;
import br.zul.zwork5.exception.ZInvalidValueException;
import br.zul.zwork5.exception.ZUnexpectedConversionException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.value.ZValue;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 *
 * @author Luiz Henrique
 */
public class ZConversionManager {
    
    //==========================================================================
    //VARIÁVEIS 
    //==========================================================================
    private static ZConversionManager instance;
    
    private final ZConverterList converterList;
    private int layers;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZConversionManager() {
        this.converterList = new ZConverterList();
        this.layers = 3;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public <T> T convert(Object value, Class<T> targetClass) throws ZUnexpectedConversionException, ZConversionErrorException{
        return (T)convert(new ZConversionObj(value, targetClass)).getValue();
    }
    
    public <T> T convert(Object value, Class<T> targetClass, T _default){
        try {
            ZConversionObj conversionObject = new ZConversionObj(value, targetClass);
            return (T)convert(conversionObject).getValue();
        }catch(Exception e){
            return _default;
        }
    }
    
    public ZConversionObj convert(ZConversionObj conversionObject) throws ZUnexpectedConversionException, ZConversionErrorException {
        Objects.requireNonNull(conversionObject, "O objeto de conversão (conversionObject) não pode ser null.");
        conversionObject = treatConversionObject(conversionObject);
        if (conversionObject.getValue()==null){
            return conversionObject;
        }
        Class<?> valueClass = conversionObject.getValue().getClass();
        try {
            if (conversionObject.getTargetClass().isAssignableFrom(valueClass)){
                return conversionObject;
            }
            ZConverter<?, ?> converter = getConverter(conversionObject);
            return converter.convertToB(conversionObject);
        } catch (ZConversionErrorException e){
            throw e;
        } catch (ZConverterNotFoundException e){
            throw new ZUnexpectedConversionException(conversionObject, e, e.getMessage());
        } catch (Exception e){
            throw new ZConversionErrorException(e, conversionObject);
        }
    } 
    
    public <A, B> ZConverter<A, B> getConverter(Object value, Class<?> targetClass) throws ZConverterNotFoundException{
        return getConverter(new ZConversionObj(value, targetClass));
    }
   
    public <A, B> ZConverter<A, B> getConverter(ZConversionObj conversionObj) throws ZConverterNotFoundException{
        if (conversionObj.getValue()==null) throw new NullPointerException();
        
        conversionObj = treatConversionObject(conversionObj);
        Class<?> sourceClass = conversionObj.getValue().getClass();
        Class<?> targetClass = conversionObj.getTargetClass();
        
        if (!hasConverterTo(sourceClass)||!hasConverterTo(targetClass)) throw new ZConverterNotFoundException(sourceClass, targetClass);
        
        ZPossibleConversionTree tree = new ZPossibleConversionTree(converterList);
        tree.addOf(conversionObj);
        
        while (tree.hasNext()){
            ZPossibleConversion pc = tree.next();
            if (pc.getConverter().validateOutputB(conversionObj)){
                return new ZDynamicConverter(pc);
            }
            if (pc.getLayer()<layers){
                tree.addOf(pc, conversionObj, pc.getTargetClass());
            }
        }
        throw new ZConverterNotFoundException(sourceClass, targetClass);
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS DE CONVERTER
    //==========================================================================
    public void addConverter(ZConverter<?, ?> converter){
        converterList.addConverter(converter);
    }
    
    public void removeConverter(ZConverter<?, ?> converter){
        converterList.removeConverter(converter);
    }
    
    public ZList<ZConverter<?, ?>> listConverters(){
        return converterList.listConverters();
    }

    public boolean hasConverterTo(Class<?> oneOfClass) {
        return converterList.hasConverterTo(oneOfClass);
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZConversionObj treatConversionObject(ZConversionObj conversionObject) {
        if (conversionObject.getValue() instanceof ZConversionObj){
            ZConversionObj subConvObj = (ZConversionObj)conversionObject.getValue();
            ZConversionObj newConvObj = subConvObj.copy();
            newConvObj.setTargetClass(conversionObject.getTargetClass());
            return treatConversionObject(newConvObj);
        } else if (conversionObject.getValue() instanceof ZValue){
            ZConversionObj newConvObj = conversionObject.copy();
            newConvObj.setValue(((ZValue)conversionObject.getValue()).asObject());
            return treatConversionObject(newConvObj);
        } else {
            treatClasses(conversionObject);
            return conversionObject;
        }
    }
    
    private void treatClasses(ZConversionObj conversionObject){
        try {
            conversionObject.setSourceClass(ZPrimitiveClass.getNonPrimitiveClass(conversionObject.getSourceClass()));
        }catch(NullPointerException|NoSuchElementException e){}
        try {
            conversionObject.setTargetClass(ZPrimitiveClass.getNonPrimitiveClass(conversionObject.getTargetClass()));
        }catch(NullPointerException|NoSuchElementException e){}
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public static ZConversionManager getInstance() {
        if (instance==null){
            instance = new ZDefaultConversionManager();
        }
        return instance;
    }
    public static void setInstance(ZConversionManager instance) {
        ZConversionManager.instance = instance;
    }

    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZConverterList getConverterList() {
        return converterList;
    }
    
    public int getLayers() {
        return layers;
    }
    public void setLayers(int layers) {
        if (layers<=0){
            throw new ZInvalidValueException("O valor não pode ser <= 0. (valor={0})", layers);
        }
        this.layers = layers;
    }
    
}
