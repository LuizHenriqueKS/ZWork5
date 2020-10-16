package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.util.ZStrUtils;
import java.util.Arrays;

/**
 *
 * @author Luiz Henrique
 */
public class ZEnumStringConverter extends ZStringConverter<Enum>{

    @Override
    public Class<Enum> getObjClass() {
        return Enum.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) {
        ZConversionObj result = obj.copy();
        if (ZStrUtils.isLong(value)){
            int index = Integer.valueOf(value);
            result.setValue(((Class<Enum>)obj.getTargetClass()).getEnumConstants()[index]);
        } else {
            result.setValue(Arrays.asList(obj.getTargetClass().getEnumConstants())
                            .stream()
                            .map(e->(Enum)e)
                            .filter(e->treatName(e.name()).equals(treatName(value)))
                            .findFirst().get());
        }
        return result;
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, Enum value) {
        ZConversionObj result = obj.copy();
        result.setValue(value.name());
        return result;
    }

    private String treatName(String name) {
        return name.toLowerCase().replace("_", "");
    }
    
}
