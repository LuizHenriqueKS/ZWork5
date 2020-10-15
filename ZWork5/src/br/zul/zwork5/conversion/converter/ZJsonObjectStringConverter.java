package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork4.exception.ZConversionErrorException;
import br.zul.zwork5.json.ZJsonObject;

/**
 *
 * @author luizh
 */
public class ZJsonObjectStringConverter extends ZStringConverter<ZJsonObject> {

    @Override
    public Class<ZJsonObject> getObjClass() {
        return ZJsonObject.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(new ZJsonObject(value));
        return result;
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, ZJsonObject value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(value.toString());
        return result;
    }
    
}
