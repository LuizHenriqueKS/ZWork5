package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.conversion.annotation.ZFormatAnnotation;
import br.zul.zwork5.exception.ZParseException;
import br.zul.zwork5.exception.ZUnexpectedException;
import br.zul.zwork5.timestamp.ZDateTime;

/**
 *
 * @author Luiz Henrique
 */
public class ZDateTimeStringConverter extends ZStringConverter<ZDateTime>{

    @Override
    public Class<ZDateTime> getObjClass() {
        return ZDateTime.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) {
        try {
            ZConversionObj result = obj.copy();
            if (obj.hasAnnotation(ZFormatAnnotation.class)){
                result.setValue(new ZDateTime(obj.getAnnotation(ZFormatAnnotation.class).value(), value));
            } else {
                result.setValue(new ZDateTime(value));
            }
            return result;
        } catch (ZParseException ex){
            throw new ZUnexpectedException(ex);
        }
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, ZDateTime value) {
        ZConversionObj result = obj.copy();
        if (obj.hasAnnotation(ZFormatAnnotation.class)){
            result.setValue(value.format(obj.getAnnotation(ZFormatAnnotation.class).value()));
        } else {
            result.setValue(value.toString());
        }
        return result;
    }
    
}
