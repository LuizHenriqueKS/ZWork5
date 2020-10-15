package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZConverter;
import br.zul.zwork4.exception.ZConversionErrorException;
import br.zul.zwork5.json.ZJsonObject;
import org.json.JSONObject;

/**
 *
 * @author luizh
 */
public class ZJsonObjectAndJSONObjectConverter implements ZConverter<ZJsonObject, JSONObject> {

    @Override
    public Class<ZJsonObject> getAClass() {
        return ZJsonObject.class;
    }

    @Override
    public Class<JSONObject> getBClass() {
        return JSONObject.class;
    }

    @Override
    public ZConversionObj convertToA(ZConversionObj obj, JSONObject value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(new ZJsonObject(value));
        return result;
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, ZJsonObject value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(new JSONObject(value.toString()));
        return result;
    }
    
}
