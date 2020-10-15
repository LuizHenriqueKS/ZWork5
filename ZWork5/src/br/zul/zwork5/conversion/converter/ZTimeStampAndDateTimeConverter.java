package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZConverter;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.timestamp.ZDateTime;
import java.sql.Timestamp;

/**
 *
 * @author Luiz Henrique
 */
public class ZTimeStampAndDateTimeConverter implements ZConverter<Timestamp, ZDateTime> {

    @Override
    public Class<Timestamp> getAClass() {
        return Timestamp.class;
    }

    @Override
    public Class<ZDateTime> getBClass() {
        return ZDateTime.class;
    }

    @Override
    public ZConversionObj convertToA(ZConversionObj obj, ZDateTime value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(new Timestamp(value.getTimeInMillis()));
        return result;
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, Timestamp value) throws ZConversionErrorException {
        ZConversionObj result = obj.copy();
        result.setValue(new ZDateTime(value.getTime()));
        return result;
    }

}
