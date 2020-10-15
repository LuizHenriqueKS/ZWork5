package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork4.util.ZBigDecimalFormat;
import java.math.BigDecimal;

/**
 *
 * @author Luiz Henrique
 */
public class ZFloatStringConverter extends ZStringConverter<Float>{

    @Override
    public Class<Float> getObjClass() {
        return Float.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) {
        ZConversionObj result = obj.copy();
        result.setValue(new ZBigDecimalFormat(obj).parse(value).floatValue());
        return result;
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, Float value) {
        ZConversionObj result = obj.copy();
        result.setValue(new ZBigDecimalFormat(obj).format(new BigDecimal(value)));
        return result;
    }
    
}
