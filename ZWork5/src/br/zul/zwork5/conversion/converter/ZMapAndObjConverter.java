package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZConversionObjChecker;
import br.zul.zwork5.conversion.ZConverter;
import br.zul.zwork5.entity.ZAttrHandler;
import br.zul.zwork5.entity.ZEntity;
import br.zul.zwork5.entity.ZEntityHandler;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZNewInstanceException;
import br.zul.zwork5.exception.ZVarHandlerException;
import br.zul.zwork5.reflection.ZClass;
import br.zul.zwork5.reflection.ZObjHandler;
import br.zul.zwork5.reflection.ZVarHandler;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Luiz Henrique
 */
public class ZMapAndObjConverter implements ZConverter<Map, Object>{

    @Override
    public Class<Map> getAClass() {
        return Map.class;
    }

    @Override
    public Class<Object> getBClass() {
        return Object.class;
    }
    
    @Override
    public boolean validateInputA(ZConversionObj obj, Map value) {
        if (value==null) return false;
        return value.keySet().stream()
                             .allMatch(key->key instanceof String);
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
            result.setValue(new ZObjHandler(value).getObjVarMap());
            return result;
        } catch (ZVarHandlerException ex) {
            throw new ZConversionErrorException(ex, obj);
        }
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, Map value) throws ZConversionErrorException {
        try {
            ZConversionObj result = obj.copy();
            
            ZClass<?> clazz = new ZClass<>(obj.getTargetClass());
            result.setValue(clazz.newInstance());
            
            if (ZEntity.class.isAssignableFrom(obj.getTargetClass())){
                
                ZEntityHandler entityHandler = new ZEntityHandler((ZEntity)result.getValue());
                for (Object entryObj:value.entrySet()){
                    Entry<String, Object> e = (Entry<String, Object>)entryObj;
                    ZAttrHandler attrHandler = entityHandler.getAttr(e.getKey());
                    if (attrHandler!=null){
                        attrHandler.setValue(e.getValue());
                    }
                }
                
                
            } else {
                
                ZObjHandler objHandler = new ZObjHandler(result.getValue());
                for (Object entryObj:value.entrySet()){
                    Entry<String, Object> e = (Entry<String, Object>)entryObj;
                    ZVarHandler varHandler = objHandler.getVar(e.getKey());
                    if (varHandler!=null){
                        varHandler.setValue(e.getValue());
                    }
                }
                
            }
            
            return result;
        } catch (ZNewInstanceException|ZVarHandlerException ex) {
            throw new ZConversionErrorException(ex, obj);
        }
    }

}

