package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.util.ZBigDecimalFormat;
import java.math.BigDecimal;

/**
 *
 * @author Luiz Henrique
 */
public class ZDoubleStringConverter extends ZStringConverter<Double>{

    @Override
    public Class<Double> getObjClass() {
        return Double.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) {
        ZConversionObj result = obj.copy();
        result.setValue(new ZBigDecimalFormat(obj).parse(value).doubleValue());
        return result;
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, Double value) {
        ZConversionObj result = obj.copy();
        result.setValue(new ZBigDecimalFormat(obj).format(new BigDecimal(value)));
        return result;
    }
    
}
