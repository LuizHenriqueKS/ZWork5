package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZConverter;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.timestamp.ZDate;
import java.sql.Date;

/**
 *
 * @author Luiz Henrique
 */
public class ZDateAndDateConverter implements ZConverter<ZDate, Date>{

    @Override
    public Class<ZDate> getAClass() {
        return ZDate.class;
    }

    @Override
    public Class<Date> getBClass() {
        return Date.class;
    }

    @Override
    public ZConversionObj convertToA(ZConversionObj obj, Date value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(new ZDate(value.getTime()));
        return result;
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, ZDate value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(value.getTimeInMillis());
        return result;
    }

}
