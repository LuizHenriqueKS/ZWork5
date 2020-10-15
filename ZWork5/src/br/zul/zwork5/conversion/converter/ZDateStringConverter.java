package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.conversion.annotation.ZFormatAnnotation;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZParseException;
import br.zul.zwork5.timestamp.ZDate;

/**
 *
 * @author Luiz Henrique
 */
public class ZDateStringConverter extends ZStringConverter<ZDate> {

    @Override
    public Class<ZDate> getObjClass() {
        return ZDate.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) throws ZConversionErrorException {
        try {
            ZConversionObj result = obj.copy();
            if (obj.hasAnnotation(ZFormatAnnotation.class)) {
                result.setValue(new ZDate(obj.getAnnotation(ZFormatAnnotation.class).value(), value));
            } else {
                result.setValue(new ZDate(value));
            }
            return result;
        } catch (ZParseException ex) {
            throw new ZConversionErrorException(ex, obj);
        }
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, ZDate value) {
        ZConversionObj result = obj.copy();
        if (obj.hasAnnotation(ZFormatAnnotation.class)) {
            result.setValue(value.format(obj.getAnnotation(ZFormatAnnotation.class).value()));
        } else {
            result.setValue(value.toString());
        }
        return result;
    }

}
