package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZConversionObjChecker;
import br.zul.zwork5.conversion.ZConverter;
import br.zul.zwork5.entity.ZEntity;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZInstantiationException;
import br.zul.zwork5.exception.ZJsonException;
import br.zul.zwork5.exception.ZNewInstanceException;
import br.zul.zwork5.exception.ZVarHandlerException;
import br.zul.zwork5.json.ZJsonObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Henrique
 */
public class ZJsonObjectAndObjConverter implements ZConverter<ZJsonObject, Object>{

    @Override
    public Class<ZJsonObject> getAClass() {
        return ZJsonObject.class;
    }

    @Override
    public Class<Object> getBClass() {
        return Object.class;
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
                result.setValue(ZJsonObject.fromEntity((ZEntity)value));
            } else {
                result.setValue(ZJsonObject.fromObj((Object)value));
            }
            return result;
        } catch (ZJsonException ex) {
            throw new ZConversionErrorException(ex, obj);
        }
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, ZJsonObject value) throws ZConversionErrorException {
        try {
            ZConversionObj result = obj.copy();
            if (ZEntity.class.isAssignableFrom(obj.getTargetClass())){
                result.setValue(value.asEntity((Class<? extends ZEntity>)obj.getTargetClass()));
            } else {
                result.setValue(value.asObject(obj.getTargetClass()));
            }
            return result;
        } catch (ZNewInstanceException|ZVarHandlerException|ZInstantiationException ex) {
            throw new ZConversionErrorException(ex, obj);    
        }
    }
}
