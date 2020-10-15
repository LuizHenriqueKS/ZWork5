package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import org.json.JSONObject;

/**
 *
 * @author luizh
 */
public class ZJSONObjectRawStringConverter extends ZStringConverter<JSONObject>{

    @Override
    public Class<JSONObject> getObjClass() {
        return JSONObject.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) {
        ZConversionObj result = obj.copy();
        result.setValue(new JSONObject(value));
        return result;
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, JSONObject value) {
        ZConversionObj result = obj.copy();
        result.setValue(value.toString());
        return result;
    }
    
}