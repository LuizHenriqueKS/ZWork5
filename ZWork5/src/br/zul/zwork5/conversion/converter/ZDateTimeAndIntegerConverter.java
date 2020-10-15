package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZConverter;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.timestamp.ZDateTime;

/**
 *
 * @author Luiz Henrique
 */
public class ZDateTimeAndIntegerConverter implements ZConverter<ZDateTime, Integer>{

    @Override
    public Class<ZDateTime> getAClass() {
        return ZDateTime.class;
    }

    @Override
    public Class<Integer> getBClass() {
        return Integer.class;
    }

    @Override
    public ZConversionObj convertToA(ZConversionObj obj, Integer value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();                     
        if (obj.matchAttribute("json", true)||value.longValue()<=9999999999L){
            result.setValue(new ZDateTime(value.longValue()*1000L));
        } else {
            result.setValue(new ZDateTime(value));
        }
        return result;
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, ZDateTime value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(value.getTimeInMillis());
        return result;
    }

}
