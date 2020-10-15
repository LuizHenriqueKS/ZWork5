package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.conversion.annotation.ZFormatAnnotation;
import br.zul.zwork5.util.ZBigDecimalFormat;
import java.math.BigDecimal;

/**
 *
 * @author Luiz Henrique
 */
public class ZLongStringConverter extends ZStringConverter<Long>{

    @Override
    public Class<Long> getObjClass() {
        return Long.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) {
        ZConversionObj result = obj.copy();
        if (obj.hasAnnotation(ZFormatAnnotation.class)){
            result.setValue(new ZBigDecimalFormat(obj).parse(value));
        } else {
            result.setValue(Long.valueOf(value));
        }
        return result;
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, Long value) {
        ZConversionObj result = obj.copy();
        if (obj.hasAnnotation(ZFormatAnnotation.class)){
            result.setValue(new ZBigDecimalFormat(obj).format(new BigDecimal(value)));
        } else {
            result.setValue(value.toString());
        }
        return result;
    }
    
}
