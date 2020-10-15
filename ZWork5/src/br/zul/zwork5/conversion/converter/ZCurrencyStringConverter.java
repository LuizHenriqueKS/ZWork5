package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.conversion.annotation.ZFormatAnnotation;
import br.zul.zwork5.currency.ZCurrency;
import br.zul.zwork5.entity.ZAttribute;
import br.zul.zwork5.util.ZBigDecimalFormat;

/**
 *
 * @author Luiz Henrique
 */
public class ZCurrencyStringConverter extends ZStringConverter<ZCurrency>{

    @Override
    public Class<ZCurrency> getObjClass() {
        return ZCurrency.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) {
        ZConversionObj result = obj.copy();
        result.setValue(new ZCurrency(new ZBigDecimalFormat(obj).parse(value)));
        return result;
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, ZCurrency value) {
        ZConversionObj result = obj.copy();
        if (result.hasAnnotation(ZFormatAnnotation.class)||result.hasAnnotation(ZAttribute.class)){
            result.setValue(new ZBigDecimalFormat(obj).format(value.asBigDecimal()));
        } else {
            result.setValue(value.toString());
        }
        return result;
    }
    
}
