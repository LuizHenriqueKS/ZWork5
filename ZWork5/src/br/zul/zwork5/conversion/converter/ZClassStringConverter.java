package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.exception.ZConversionErrorException;

/**
 *
 * @author Luiz Henrique
 */
public class ZClassStringConverter extends ZStringConverter<Class>{

    @Override
    public Class<Class> getObjClass() {
        return Class.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) throws ZConversionErrorException {
        try {
            ZConversionObj result = obj.copy();
            result.setValue(Class.forName(value));
            return result;
        } catch (ClassNotFoundException ex) {
            throw new ZConversionErrorException(ex, obj);
        }
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, Class value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(value.getName());
        return result;
    }
    
}
