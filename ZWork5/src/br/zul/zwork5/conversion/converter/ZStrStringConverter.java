package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.str.ZStr;

/**
 *
 * @author Luiz Henrique
 */
public class ZStrStringConverter extends ZStringConverter<ZStr>{

    @Override
    public Class<ZStr> getObjClass() {
        return ZStr.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) {
        ZConversionObj result = obj.copy();
        result.setValue(new ZStr(value));
        return result;
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, ZStr value) {
        ZConversionObj result = obj.copy();
        result.setValue(value.toString());
        return result;
    }
    
}
