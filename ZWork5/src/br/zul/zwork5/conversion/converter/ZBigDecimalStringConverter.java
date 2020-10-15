package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.util.ZBigDecimalFormat;
import java.math.BigDecimal;

/**
 *
 * @author Luiz Henrique
 */
public class ZBigDecimalStringConverter extends ZStringConverter<BigDecimal>{

    @Override
    public Class<BigDecimal> getObjClass() {
        return BigDecimal.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) {
        ZConversionObj result = obj.copy();
        result.setValue(new ZBigDecimalFormat(obj).parse(value));
        return result;
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, BigDecimal value) {
        ZConversionObj result = obj.copy();
        result.setValue(new ZBigDecimalFormat(obj).format(value));
        return result;
    }
    
}
