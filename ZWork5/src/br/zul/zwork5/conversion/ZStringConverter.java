package br.zul.zwork5.conversion;

import br.zul.zwork5.exception.ZConversionErrorException;

/**
 *
 * @author Luiz Henrique
 * @param <T>
 */
public abstract class ZStringConverter<T> implements ZConverter<String, T> {
    
    //==========================================================================
    //MÉTODOS ABSTRATOS
    //==========================================================================
    public abstract Class<T> getObjClass();
    public abstract ZConversionObj convertToObj(ZConversionObj obj, String value) throws ZConversionErrorException;
    public abstract ZConversionObj convertToString(ZConversionObj obj, T value) throws ZConversionErrorException;
    
    //==========================================================================
    //MÉTODOS SOBRESCRITOS
    //==========================================================================
    @Override
    public Class<String> getAClass() {
        return String.class;
    }

    @Override
    public Class<T> getBClass() {
        return getObjClass();
    }

    @Override
    public ZConversionObj convertToA(ZConversionObj obj, T value) throws ZConversionErrorException {
        return convertToString(obj, value);
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, String value) throws ZConversionErrorException {
        return convertToObj(obj, value);
    }
    
}
