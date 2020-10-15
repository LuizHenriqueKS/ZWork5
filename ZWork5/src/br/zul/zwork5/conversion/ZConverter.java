package br.zul.zwork5.conversion;

import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZEvenClassNotFoundException;

/**
 *
 * @author Luiz Henrique
 * @param <A>
 * @param <B>
 */
public interface ZConverter<A, B> {

    public Class<A> getAClass();

    public Class<B> getBClass();

    public ZConversionObj convertToA(ZConversionObj obj, B value) throws ZConversionErrorException;

    public ZConversionObj convertToB(ZConversionObj obj, A value) throws ZConversionErrorException;
    
    default public boolean validateInputA(ZConversionObj obj, A value){
        return true;
    }
    
    default public boolean validateInputB(ZConversionObj obj, B value){
        return true;
    }
    
    default public boolean validateOutputA(ZConversionObj obj, Class<? extends A> targetClass){
        return true;
    }
    
    default public boolean validateOutputB(ZConversionObj obj, Class<? extends B> targetClass){
        return true;
    }
    
    default public boolean validateInputA(ZConversionObj obj){
        if (obj==null){
            return false;
        } else if (getAClass().isAssignableFrom(obj.getSourceClass())){
            A a = null;
            try {
                if (getAClass().isAssignableFrom(obj.getValue().getClass())) a = (A)obj.getValue();
            }catch(ClassCastException|NullPointerException e){}
            return validateInputA(obj, a);
        }
        return false;
    }
    
    default public boolean validateInputB(ZConversionObj obj){
        if (obj==null){
            return false;
        } else if (getBClass().isAssignableFrom(obj.getSourceClass())){
            B b = null;
            try {
                if (getBClass().isAssignableFrom(obj.getValue().getClass()))  b = (B)obj.getValue();
            }catch(ClassCastException e){}
            return validateInputB(obj, b);
        }
        return false;
    }
    
    default public boolean validateOutputA(ZConversionObj obj){
        if (obj==null){
            return false;
        } else if (getAClass().isAssignableFrom(obj.getTargetClass())){
            return validateOutputA(obj, (Class<? extends A>) obj.getTargetClass());
        }
        return false;
    }
    
    default public boolean validateOutputB(ZConversionObj obj){
        if (obj==null){
            return false;
        } else if (getBClass().isAssignableFrom(obj.getTargetClass())){
            return validateOutputB(obj, (Class<? extends B>) obj.getTargetClass());
        }
        return false;
    }
    
    default public boolean validateAB(ZConversionObj obj) {
        if (obj==null){
            return ZConverter.this.validateInputA(obj, null);
        } 
        return validateInputA(obj)&&validateOutputB(obj);
    }

    default public boolean validateBA(ZConversionObj obj) {
        if (obj==null){
            return ZConverter.this.validateInputB(obj, null);
        } 
        return validateInputB(obj)&&validateOutputA(obj);
    }
    
    default public ZConversionObj convertToA(ZConversionObj obj) throws ZConversionErrorException {
        return convertToA(obj, (B) obj.getValue());
    }

    default public ZConversionObj convertToB(ZConversionObj obj) throws ZConversionErrorException {
        return convertToB(obj, (A) obj.getValue());
    }

    default public boolean isConverterOf(ZConversionObj conversionObj) {
        try {
            return validateAB(conversionObj)||validateBA(conversionObj);
        } catch (NoClassDefFoundError e) {}
        return false;
    }

    default public Class<?> getEvenClass(Class<?> oneOfClasses) throws ZEvenClassNotFoundException {
        if (getAClass().isAssignableFrom(oneOfClasses)) {
            return getBClass();
        } else if (getBClass().isAssignableFrom(oneOfClasses)) {
            return getAClass();
        } else {
            throw new ZEvenClassNotFoundException(oneOfClasses);
        }
    }

}
