package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZJsonException;
import br.zul.zwork5.json.ZJsonArray;

/**
 *
 * @author luizh
 */
public class ZJsonArrayStringConverter extends ZStringConverter<ZJsonArray> {

    @Override
    public Class<ZJsonArray> getObjClass() {
        return ZJsonArray.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) throws ZConversionErrorException {
        try {
            ZConversionObj result = obj.copy();
            result.setValue(new ZJsonArray(value));
            return result;
        } catch (ZJsonException ex) {
            throw new ZConversionErrorException(ex, obj);
        }
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, ZJsonArray value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(value.toString());
        return result;
    }
    
}
