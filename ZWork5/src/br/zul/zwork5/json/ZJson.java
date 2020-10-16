package br.zul.zwork5.json;

import br.zul.zwork5.entity.ZEntity;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZJsonException;
import br.zul.zwork5.exception.ZUnexpectedException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.value.ZValue;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 *
 * @author luizh
 */
public class ZJson {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    ZJsonArray array;
    ZJsonObject object;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZJson() {
        this.object = new ZJsonObject();
    }
    
    public static ZJson fromMap(Map<Object, Object> map) throws ZJsonException{
        ZJson json = new ZJson();
        for (Entry<Object, Object> e:map.entrySet()){
            json.put(Arrays.asList(e.getKey()), e.getValue());
        }
        return json;
    }
    
    public static ZJson fromEntity(ZEntity entity) throws ZJsonException{
        ZJsonObject arg = ZJsonObject.fromEntity(entity);
        ZJson json = new ZJson(arg);
        json.requireValid();;
        return json;
    }
    
    public static ZJson fromObj(Object obj) throws ZJsonException{
        ZJsonObject arg = ZJsonObject.fromObj(obj);
        ZJson json = new ZJson(arg);
        json.requireValid();
        return json;
    }

    public ZJson(String source) throws ZJsonException {
        tryParseArray(source);
        tryParseObject(source);
        requireValid();
    }

    public ZJson(ZJsonArray array) throws ZJsonException {
        this.array = array;
        requireValid();
    }

    public ZJson(ZJsonObject object) throws ZJsonException {
        this.object = object;
        requireValid();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.array);
        hash = 11 * hash + Objects.hashCode(this.object);
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
        final ZJson other = (ZJson) obj;
        if (!Objects.equals(this.array, other.array)) {
            return false;
        }
        return Objects.equals(this.object, other.object);
    }
    
    @Override
    public String toString(){
        if (object!=null){
            return object.toString();
        } else {
            return array.toString();
        }
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void add(Object value) throws ZJsonException{
        if (isObject()&&object.isEmpty()){
            convertToArray();
        }
        array.add(value);
    }
    
    public ZValue get(Object... keys) throws ZJsonException{
        return get(Arrays.asList(keys));
    }
    
    public ZValue opt(Object... keys){
        try {
            return get(keys);
        }catch(ZJsonException e){
            return null;
        }
    }
    
    public ZValue get(Collection<Object> keyList) throws ZJsonException{
        return new ZJsonGetter(this, keyList).get();
    }
    
    public ZValue opt(Collection<Object> keyList){
        try {
            return get(keyList);
        }catch(ZJsonException e){
            return null;
        }
    }
    
    public void add(Collection<Object> keyList, Object value) throws ZJsonException{
        try {
            new ZJsonAdder(this, keyList).add(value);
        } catch (ZConversionErrorException ex) {
            throw new ZJsonException(ex);
        }
    }
    
    public void put(Collection<Object> keyList, Object value) throws ZJsonException{
        try {
            new ZJsonPutter(this, keyList).put(value);
        } catch (ZConversionErrorException ex) {
            throw new ZJsonException(ex);
        }
    }
    
    public void put(Object key, Object value) throws ZJsonException{
        put(Arrays.asList(key), value);
    }
    
    public void remove(Object... keys){
        remove(Arrays.asList(keys));
    }
    
    public void remove(Collection<Object> keyList){
        try {
            new ZJsonRemover(this, keyList).remove();
        } catch (ZConversionErrorException | ZJsonException ex) {
            throw new ZUnexpectedException(ex);
        }
    }
    
    public ZJsonEntry getEntry(Object... keys){
        return getEntry(Arrays.asList(keys));
    }
    
    public ZJsonEntry getEntry(Collection<Object> keyList){
        return new ZJsonEntry(this, keyList);
    }
    
    public int size(){
        if (object!=null){
            return object.size();
        } else {
           return array.size(); 
        }
    }
    
    public ZList<Object> listKeys(){
        if (array!=null){
            ZList<Object> keyList = new ZList<>();
            for (int i=0;i<array.size();i++){
                keyList.add(i);
            }
            return keyList;
        } else {
            return object.listKeys().map(key->(Object)key);
        }
    }
    
    public ZList<ZValue> listValues(){
        if (array!=null){
            return array.listValues();
        } else {
            return new ZJsonObjectSortedValueLister(object).list();
        }
    }
    
    public ZJsonObject asJsonObject() throws ZJsonException{
        if (object==null){
            ZJsonObject jsonObject = new ZJsonObject();
            for (int key=0;key<array.size();key++){
                jsonObject.put(String.valueOf(key), array.get(key));
            }
            return jsonObject;
        } else {
            return object.copy();
        }
    }
    
    public ZJsonArray asJsonArray() throws ZJsonException{
        if (array==null){
            return new ZJsonObjectToJsonArray(object).convert();
        } else {
            return array.copy();
        }
    }
    
    public Map<Object, ZValue> asMap(){
        Map<Object, ZValue> result = new LinkedHashMap<>();
        if (array!=null){
            int index = 0;
            for (ZValue value:array.listValues()){
                result.put(index++, value);
            }
        } else {
            for (Entry<String, ZValue> e:object.asMap().entrySet()){
                Object key;
                try {
                    key = Integer.valueOf(e.getKey());
                } catch (NumberFormatException ex){
                    key = e.getKey();
                }
                result.put(key, e.getValue());
            }
        }
        return result;
    }
    
    public Map<Object, Object> asObjMap(){
        Map<Object, Object> result = new LinkedHashMap<>();
        asMap().forEach((key,val)->result.put(key, val.asObject()));
        return result;
    }
    
    public boolean has(Object... keys){
        return has(Arrays.asList(keys));
    }
    
    public boolean has(Collection<Object> keyList){
        try {
            return get(keyList)!=null;
        }catch(ZJsonException e){
            return false;
        }
    }
    
    public String toBeautifulString(){
        if (object!=null){
            return object.toBeautifulString();
        } else {
            return array.toBeautifulString();
        }
    }
    
    public void forEach(BiConsumer<Object, ZValue> consumer){
        asMap().forEach(consumer);
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void requireValid() throws ZJsonException {
        if (!isValid()) {
            throw new ZJsonException("Json inválido.");
        }
    }

    private void tryParseArray(String source) {
        try {
            array = new ZJsonArray(source);
        } catch (ZJsonException e) {}
    }

    private void tryParseObject(String source) {
        try {
            object = new ZJsonObject(source);
        } catch (ZJsonException e) {}
    }
    
    private void convertToArray() throws ZJsonException {
        array = asJsonArray();
        object = null;
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public boolean isValid(){
        return array!=null||object!=null;
    }
    
    public boolean isArray(){
        return array!=null;
    }
    
    public boolean isObject(){
        return object!=null;
    }
    
}
