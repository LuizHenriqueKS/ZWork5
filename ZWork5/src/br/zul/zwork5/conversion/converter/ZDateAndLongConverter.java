package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZConverter;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.timestamp.ZDate;

/**
 *
 * @author Luiz Henrique
 */
public class ZDateAndLongConverter implements ZConverter<ZDate, Long>{

    @Override
    public Class<ZDate> getAClass() {
        return ZDate.class;
    }

    @Override
    public Class<Long> getBClass() {
        return Long.class;
    }

    @Override
    public ZConversionObj convertToA(ZConversionObj obj, Long value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(new ZDate(value));
        return result;
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, ZDate value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(value.getTimeInMillis());
        return result;
    }

}
