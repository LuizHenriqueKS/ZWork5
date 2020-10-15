package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZConverter;
import br.zul.zwork4.exception.ZConversionErrorException;
import br.zul.zwork5.json.ZJsonArray;
import org.json.JSONArray;

/**
 *
 * @author luizh
 */
public class ZJsonArrayAndJSONArrayConverter implements ZConverter<ZJsonArray, JSONArray> {

    @Override
    public Class<ZJsonArray> getAClass() {
        return ZJsonArray.class;
    }

    @Override
    public Class<JSONArray> getBClass() {
        return JSONArray.class;
    }

    @Override
    public ZConversionObj convertToA(ZConversionObj obj, JSONArray value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(new ZJsonArray(value));
        return result;
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, ZJsonArray value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(new JSONArray(value.toString()));
        return result;
    }
    
}
