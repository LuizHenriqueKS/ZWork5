package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import org.json.JSONArray;

/**
 *
 * @author luizh
 */
public class ZJSONArrayRawStringConverter extends ZStringConverter<JSONArray>{

    @Override
    public Class<JSONArray> getObjClass() {
        return JSONArray.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) {
        ZConversionObj result = obj.copy();
        result.setValue(new JSONArray(value));
        return result;
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, JSONArray value) {
        ZConversionObj result = obj.copy();
        result.setValue(value.toString());
        return result;
    }
    
}