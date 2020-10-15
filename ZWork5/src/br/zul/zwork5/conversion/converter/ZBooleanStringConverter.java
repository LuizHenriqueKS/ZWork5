package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.exception.ZConversionErrorException;

/**
 *
 * @author Luiz Henrique
 */
public class ZBooleanStringConverter extends ZStringConverter<Boolean>{

    @Override
    public Class<Boolean> getObjClass() {
        return Boolean.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        switch (value.trim().toLowerCase()){
            case "0":
            case "false":
            case "f":
                result.setValue(false);
                break;
            case "1":
            case "true":
            case "t":
                result.setValue(true);
                break;
            default:
                throw new ZConversionErrorException(obj);
        }
        return result;
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, Boolean value) {
        ZConversionObj result = obj.copy();
        result.setValue(value.toString());
        return result;
    }
    
}
