package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.json.ZJson;

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
    public ZConversionObj convertToObj(ZConversionObj obj, String value) {
        ZConversionObj result = obj.copy();
        result.setValue(new ZJson(value));
        return result;
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, ZJson value) {
        ZConversionObj result = obj.copy();
        result.setValue(value.toString());
        return result;
    }
    
}
