package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZConverter;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.timestamp.ZDateTime;

/**
 *
 * @author Luiz Henrique
 */
public class ZDateTimeAndLongConverter implements ZConverter<ZDateTime, Long>{

    @Override
    public Class<ZDateTime> getAClass() {
        return ZDateTime.class;
    }

    @Override
    public Class<Long> getBClass() {
        return Long.class;
    }

    @Override
    public ZConversionObj convertToA(ZConversionObj obj, Long value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(new ZDateTime(value));
        return result;
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, ZDateTime value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(value.getTimeInMillis());
        return result;
    }

}
