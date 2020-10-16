package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZStringConverter;
import br.zul.zwork5.entity.ZEntity;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZInstantiationException;
import br.zul.zwork5.exception.ZJsonException;
import br.zul.zwork5.exception.ZVarHandlerException;
import br.zul.zwork5.json.ZJsonObject;
import java.util.Objects;

/**
 *
 * @author Luiz Henrique
 */
public class ZEntityStringConverter extends ZStringConverter<ZEntity> {

    @Override
    public Class<ZEntity> getObjClass() {
        return ZEntity.class;
    }

    @Override
    public ZConversionObj convertToObj(ZConversionObj obj, String value) throws ZConversionErrorException {
        try {
            ZConversionObj result = obj.copy();
            ZJsonObject json = new ZJsonObject(value);
            if (Objects.equals(result.getTargetClass(), ZEntity.class)) {
                Class<? extends ZEntity> targetClass = (Class<? extends ZEntity>) Class.forName(json.get("class").asString().get());
                result.setValue(json.asEntity(targetClass));

            } else {
                result.setValue(json.asEntity((Class<? extends ZEntity>) obj.getTargetClass()));
            }
            return result;
        } catch (ClassNotFoundException | ZJsonException | ZVarHandlerException | ZInstantiationException ex) {
            throw new ZConversionErrorException(ex, obj);
        }
    }

    @Override
    public ZConversionObj convertToString(ZConversionObj obj, ZEntity value) throws ZConversionErrorException {
        try {
            ZConversionObj result = obj.copy();
            result.setValue(ZJsonObject.fromEntity(value).toString());
            return result;
        } catch (ZJsonException ex) {
            throw new ZConversionErrorException(ex, obj);
        }
    }

}
