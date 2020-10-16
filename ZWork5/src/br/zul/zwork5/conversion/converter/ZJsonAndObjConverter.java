package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZConversionObjChecker;
import br.zul.zwork5.conversion.ZConverter;
import br.zul.zwork5.entity.ZEntity;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZJsonException;
import br.zul.zwork5.exception.ZNewInstanceException;
import br.zul.zwork5.exception.ZVarHandlerException;
import br.zul.zwork5.json.ZJson;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Henrique
 */
public class ZJsonAndObjConverter implements ZConverter<ZJson, Object>{

    @Override
    public Class<ZJson> getAClass() {
        return ZJson.class;
    }

    @Override
    public Class<Object> getBClass() {
        return Object.class;
    }

    @Override
    public boolean validateInputA(ZConversionObj obj, ZJson value) {
        if (value==null) return false;
        return value.isObject();
    }

    @Override
    public boolean validateInputB(ZConversionObj obj, Object value) {
        if (value==null) return false;
        return !ZConversionObjChecker.isBaseClass(value.getClass());
    }

    @Override
    public ZConversionObj convertToA(ZConversionObj obj, Object value) throws ZConversionErrorException {
        try {
            ZConversionObj result = obj.copy();
            if (value instanceof ZEntity){
                result.setValue(ZJson.fromEntity((ZEntity)value));
            } else {
                result.setValue(ZJson.fromObj((Object)value));
            }
            return result;
        } catch (ZJsonException ex){
            throw new ZConversionErrorException(ex, obj);
        }
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, ZJson value) throws ZConversionErrorException {
        try {
            ZConversionObj result = obj.copy();
            if (ZEntity.class.isAssignableFrom(obj.getTargetClass())){
                result.setValue(value.asJsonObject().asEntity((Class<? extends ZEntity>)obj.getTargetClass()));
            } else {
                result.setValue(value.asJsonObject().asObject(obj.getTargetClass()));
            }
            return result;
        } catch (ZJsonException|ZNewInstanceException|ZVarHandlerException ex){
            throw  new ZConversionErrorException(ex, obj);
        }
    }
}
