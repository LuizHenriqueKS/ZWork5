package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZConverter;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.timestamp.ZTime;
import java.sql.Time;

/**
 *
 * @author Luiz Henrique
 */
public class ZTimeAndTimeConverter implements ZConverter<Time, ZTime> {

    @Override
    public Class<Time> getAClass() {
        return Time.class;
    }

    @Override
    public Class<ZTime> getBClass() {
        return ZTime.class;
    }

    @Override
    public ZConversionObj convertToA(ZConversionObj obj, ZTime value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(new Time(value.getTimeInMillis()));
        return result;
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, Time value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(new ZTime(value.getTime()));
        return result;
    }

}
