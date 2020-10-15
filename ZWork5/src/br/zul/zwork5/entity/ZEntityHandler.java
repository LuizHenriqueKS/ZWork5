package br.zul.zwork5.entity;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork4.util.ZList;
import br.zul.zwork4.value.ZValue;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author luizh
 */
public class ZEntityHandler {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    final ZEntity entity;
    
    private Map<String, ZAttrHandler> attrMap;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZEntityHandler(ZEntity entity) {
        this.entity = entity;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Map<String, String> getStringVarMap() {
        Map<String, String> result = new LinkedHashMap<>();
        for (ZAttrHandler attr:listAttrs()){
            ZValue value = attr.getValue();
            result.put(attr.getName(), value==null?null:value.asString());
        }
        return result;
    }
    
    public ZList<ZAttrHandler> listAttrs(){
        return new ZList<>(getVarMap().values());
    }

    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public Map<String, ZAttrHandler> getVarMap() {
        if (attrMap==null) attrMap = new ZAttrHandlerMapper(this).map();
        return attrMap;
    }
    
    public Map<String, Object> getObjVarMap(){
        Map<String, Object> result = new LinkedHashMap<>();
        getVarMap().forEach((key, val)->{
            Object value = val.getValue()==null?null:val.getValue().asObject();
            if (value instanceof ZConversionObj){
                result.put(key, ((ZConversionObj)value).getValue());
            } else {
                result.put(key, value);
            }
        });
        return result;
    }
    
    public ZAttrHandler getPrimaryKeyAttr(){
        return getVarMap().values().stream().filter(v->v.getAttrAnnotation().primaryKey()).findFirst().get();
    }
    
    public ZList<ZAttrHandler> listPrimaryKeyAttrs(){
        return new ZList<>(getVarMap().values().stream().filter(v->v.getAttrAnnotation().primaryKey()));
    }

    public ZAttrHandler getAttr(String key) {
        return getVarMap().get(key);
    }
    
    public ZAttrHandler getAttrByFieldName(String fieldName){
        return getVarMap().values().stream().filter(a->a.getFieldName().equals(fieldName)).findFirst().get();
    }
    
    public String getEntityName(){
        return new ZEntityManager(entity.getClass()).getEntityName();
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZEntity getEntity() {
        return entity;
    }
    
}
