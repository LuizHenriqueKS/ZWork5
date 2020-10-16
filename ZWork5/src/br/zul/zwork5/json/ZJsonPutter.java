package br.zul.zwork5.json;

import br.zul.zwork5.conversion.ZConversionManager;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZJsonException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZStrUtils;
import br.zul.zwork5.util.ZUtil;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author luizh
 */
class ZJsonPutter {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZJson json;
    private final ZList<Object> keyList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZJsonPutter(ZJson json, Collection<Object> keyList) {
        this.json = json;
        this.keyList = newKeyList(keyList);
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void put(Object value) throws ZJsonException, ZConversionErrorException {
        ZList<ZJson> jsonList = new ZJsonChildLister(json, keyList).list();
        ZJson beforeLastJson = getBeforeLastJson(jsonList);
        Object lastKey = getLastKey();
        put(beforeLastJson, lastKey, value);
        refreshParentsJsons(jsonList);
    }
    
    public void add(Object value) throws ZConversionErrorException, ZJsonException{
        ZList<ZJson> jsonList = new ZJsonChildLister(json, keyList).list();
        ZJson beforeLastJson = getBeforeLastJson(jsonList);
        Object lastKey = getLastKey();
        if (lastKey==null){
            addIntoArray(beforeLastJson, value);
        } else {
            Integer index = ZConversionManager.getInstance().convert(lastKey, Integer.class);
            addIntoArray(beforeLastJson, index, value);
        }
        refreshParentsJsons(jsonList);
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void put(ZJson json, Object key, Object value) throws ZJsonException, ZConversionErrorException{
        if (Objects.equals(key, null)){
            addIntoArray(json, value);
        } else {
            putIntoAny(json, key, value);
        }
    }

    private void refreshParentsJsons(ZList<ZJson> jsonList) throws ZJsonException, ZConversionErrorException {
        for (int i=jsonList.size()-2;i>=0;i--){
            Object key = keyList.get(i);
            ZJson current = jsonList.get(i);
            
            ZJson parent;
            if (i==0){
                parent = json;
            } else {
                parent = jsonList.get(i-1);
            }
            
            put(parent, key, current);
        }
    }
    
    private void putIntoAny(ZJson json, Object key, Object value) throws ZJsonException, ZConversionErrorException{
        boolean isInteger = isInteger(key);
        if (isInteger&&canBeArray(json)) {
            convertJsonObjToArray(json);
            json.array.put(objToInt(key), value);
        } else if (isInteger&&json.isArray()){
            json.array.put(objToInt(key), value);
        } else if (json.isObject()){
            json.object.put(objToStr(key), value);
        } else {
            convertJsonArrayToObj(json);
            putIntoAny(json, key, value);
        }
    }
    
    private void addIntoArray(ZJson json, int index, Object value) {
        if (!json.isArray()){
            convertJsonObjToArray(json);
        }
        json.array.add(index, value);
    }

    private void addIntoArray(ZJson json, Object value) throws ZJsonException {
        if (json.isArray()){
            json.array.add(value);
        } else {
            convertJsonObjToArray(json);
            json.array.add(value);
        }
    }

    private void convertJsonArrayToObj(ZJson json) throws ZJsonException {
        json.object = json.asJsonObject();
        json.array = null;
    }

    private void convertJsonObjToArray(ZJson json) {
        json.array = json.asJsonArray();
        json.object = null;
    }

    private Object getLastKey() {
        try {
            return ZUtil.last(keyList);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    private String objToStr(Object key) throws ZConversionErrorException {
        return ZConversionManager.getInstance().convert(key, String.class);
    }

    private boolean isInteger(Object key) {
        try {
            ZConversionManager.getInstance().convert(key, Integer.class);
            return true;
        } catch (ZConversionErrorException e){
            return false;
        }
    }

    private Integer objToInt(Object key) throws ZConversionErrorException {
        return ZConversionManager.getInstance().convert(key, Integer.class);
    }

    private ZJson getBeforeLastJson(ZList<ZJson> jsonList) {
        if (jsonList.size()<2){
            return json;
        } else {
            return jsonList.last(1);
        }
    }

    private ZList<Object> newKeyList(Collection<Object> keyList) {
        if (keyList instanceof ZList){
            return (ZList<Object>) keyList;
        } else {
            return new ZList<>(keyList);
        }
    }

    private boolean canBeArray(ZJson json) {
        if (json.isObject()){
            for (Object key:json.listKeys()){
                if (!ZStrUtils.isInteger(String.valueOf(key))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
}
