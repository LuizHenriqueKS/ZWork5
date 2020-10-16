package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZJsonException;
import br.zul.zwork5.json.ZJson;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Henrique
 */
public class ZJsonStringConverter extends ZStringConverter<ZJson>{

    @Override
    public Class<ZJson> getObjClass() {
        return ZJson.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) throws ZConversionErrorException {
        try {
            ZConversionObj result = obj.copy();
            result.setValue(new ZJson(value));
            return result;
        } catch (ZJsonException ex) {
            throw new ZConversionErrorException(ex, obj);
        }
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, ZJson value) {
        ZConversionObj result = obj.copy();
        result.setValue(value.toString());
        return result;
    }
    
}
