package br.zul.zwork5.json;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.entity.ZAttrHandler;
import br.zul.zwork5.entity.ZEntity;
import br.zul.zwork5.entity.ZEntityHandler;
import br.zul.zwork5.entity.ZEntityManager;
import br.zul.zwork5.exception.ZAttrHandlerException;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.reflection.ZClass;
import br.zul.zwork5.reflection.ZObjHandler;
import br.zul.zwork5.reflection.ZVarHandler;
import br.zul.zwork5.exception.ZJsonException;
import br.zul.zwork5.exception.ZNewInstanceException;
import br.zul.zwork5.exception.ZUnexpectedException;
import br.zul.zwork5.exception.ZVarHandlerException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.value.ZValue;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author luizh
 */
public class ZJsonObject {
    
    //==========================================================================
    //VARIAVEIS
    //==========================================================================
    final JSONObject jsonObject;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZJsonObject(){
        this.jsonObject = new JSONObject();
    }
    
    public ZJsonObject(String source) throws ZJsonException{
        try {
            this.jsonObject = new JSONObject(source);
        } catch (JSONException e){
            throw new ZJsonException(e);
        }
    }
    
    public static ZJsonObject fromMap(Map<String, Object> map) throws ZJsonException{
        ZJsonObject result = new ZJsonObject();
        for (Entry<String, Object> e:map.entrySet()){
            result.put(e.getKey(), e.getValue());
        }
        return result;
    }
    
    public static ZJsonObject fromEntity(ZEntity entity) throws ZJsonException{
        try {
            ZJsonObject result = new ZJsonObject();
            Set<Entry<String, ZAttrHandler>> varSet = new ZEntityHandler((ZEntity)entity).getVarMap().entrySet();
            for (Entry<String, ZAttrHandler> var:varSet){
                String key = var.getKey();
                ZAttrHandler attr = var.getValue();
                result.put(key, attr.getValue());
            }
            return result;
        }catch(ZAttrHandlerException ex){
            throw new ZJsonException(ex);
        }
    }
    
    public static ZJsonObject fromObj(Object obj) throws ZJsonException {
        try {
            ZJsonObject result = new ZJsonObject();
            Set<Entry<String, ZVarHandler>> varSet = new ZObjHandler(obj).getVarMap().entrySet();
            for (Entry<String, ZVarHandler> var:varSet){
                String key = var.getKey();
                ZVarHandler attr = var.getValue();
                result.put(key, attr.getValue());
            }
            return result;
        } catch (ZVarHandlerException ex){
            throw new ZJsonException(ex);
        }
    }
    
    public ZJsonObject(JSONObject value){
        this.jsonObject = value;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.jsonObject.toString());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZJsonObject other = (ZJsonObject) obj;
        return Objects.equals(this.jsonObject.toString(), other.jsonObject.toString());
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String toBeautifulString(){
        return jsonObject.toString(2);
    }
    
    public ZValue get(String key) throws ZJsonException{
        try {
            Object value = jsonObject.get(key);
            if (value==null) {
                Objects.requireNonNull(value, "Não foi encontrado um valor para a chave: "+ key);
            }
            return ()->value;
        }catch(JSONException|NullPointerException e){
            throw new ZJsonException(e); 
        }
    }
    
    public ZValue opt(String key){
        Object value = jsonObject.opt(key);
        if (value==null) return ()->null;
        return ()->value;
    }
    
    public ZValue opt(String key, Object _default){
        Object value = jsonObject.opt(key);
        if (value==null) return ()->_default;
        return ()->value;
    }
    
    public void put(String key, Object value) throws ZJsonException{
        try {
            Object convertedValue = new ZJsonValuer(value).value();
            jsonObject.put(key, convertedValue);
        }catch(JSONException|ZConversionErrorException e){
            throw new ZJsonException(e);
        }catch(ZJsonException e){
            if (key.startsWith("this")){
                throw new ZJsonException(e, "Tente transformar a classe em estática.");
            }
            throw e;
        }
    }
    
    public void remove(String key){
        jsonObject.remove(key);
    }

    public int size() {
        return jsonObject.length();
    }
    
    public boolean isEmpty(){
        return size()==0;
    }

    public ZList<String> listKeys() {
        return new ZList<>(jsonObject.keySet());
    }

    public Map<String, ZValue> asMap() {
        Map<String, ZValue> result = new LinkedHashMap<>();
        jsonObject.toMap().forEach((key,value)->{
            if (value==null){
                result.put(key, null);
            } else {
                result.put(key, ()->value);
            }
        });
        return result;
    }
    
    public void forEach(BiConsumer<String, ZValue> consumer){
        jsonObject.toMap().forEach((key,value)->{
            consumer.accept(key, ()->value);
        });
    }

    public <T extends ZEntity> T asEntity(Class<T> entityClass) {
        ZEntityManager entityManager = new ZEntityManager(entityClass);
        ZEntityHandler entityHandler = entityManager.createNewEntityHandler();
        forEach((key, value)->{
            ZAttrHandler attr = entityHandler.getAttr(key);
            if (attr!=null){
                ZConversionObj convObj = new ZConversionObj(value, attr.getType());
                convObj.setAttribute("json", true);
                attr.setValue(convObj);
            }
        });
        return (T)entityHandler.getEntity();
    }

    public <T> T asObject(Class<T> objClass) throws ZNewInstanceException, ZVarHandlerException {
        ZClass clazz = new ZClass(objClass);
        T obj = (T)clazz.newInstance();
        ZObjHandler objHandler = new ZObjHandler(obj);
        for (Entry<String, Object> e:jsonObject.toMap().entrySet()){
            String key = e.getKey();
            Object value = e.getValue();
            String localKey = key.replace("_", "");
            ZVarHandler var = objHandler.listVars().optFirst(v->v.getName().equalsIgnoreCase(localKey));
            if (var!=null&&(var.hasSetters()||var.hasField())){
                ZConversionObj convObj = new ZConversionObj(value, var.getType());
                convObj.setAttribute("json", true);
                var.setValue(convObj);
            }
        };
        return obj;
    }
    
    public ZJsonObject copy() {
        try {
            return new ZJsonObject(toString());
        } catch (ZJsonException ex){
            throw new ZUnexpectedException(ex);
        }
    }
    
}
