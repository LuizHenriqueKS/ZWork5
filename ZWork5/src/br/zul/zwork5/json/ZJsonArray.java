package br.zul.zwork5.json;

import br.zul.zwork5.conversion.ZConversionManager;
import br.zul.zwork5.entity.ZEntity;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZJsonException;
import br.zul.zwork5.exception.ZNewInstanceException;
import br.zul.zwork5.exception.ZUnexpectedException;
import br.zul.zwork5.exception.ZVarHandlerException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.value.ZValue;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author luizh
 */
public class ZJsonArray {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    JSONArray jsonArray;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZJsonArray(String source) throws ZJsonException {
        try {
            this.jsonArray = new JSONArray(source);
        }catch(JSONException e){
            throw new ZJsonException(e);
        }
    }
    
    public ZJsonArray() {
        this.jsonArray = new JSONArray();
    }

    public ZJsonArray(JSONArray value) {
        this.jsonArray = value;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.jsonArray.toString());
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
        final ZJsonArray other = (ZJsonArray) obj;
        return Objects.equals(this.jsonArray.toString(), other.jsonArray.toString());
    }

    @Override
    public String toString() {
        return jsonArray.toString();
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public int size() {
        return this.jsonArray.length();
    }
    
    public boolean isEmpty(){
        return size()==0;
    }

    public ZValue get(int index) throws ZJsonException {
        try {
            Object value = this.jsonArray.get(index);
            Objects.requireNonNull(value, "Não foi encontrado valor para o índice: "+ index);
            return ()->value;
        } catch (JSONException|NullPointerException e){
            throw new ZJsonException(e);
        }
    }

    public String toBeautifulString() {
        return toString(2);
    }

    public String toString(int indentFactor) {
        return jsonArray.toString(indentFactor);
    }

    public ZList<ZValue> listValues() {
        ZList<ZValue> result = new ZList<>();
        for (Object obj: jsonArray){
            result.add(()->obj);
        }
        return result;
    }

    public void add(Object value) throws ZJsonException {
        try {
            jsonArray.put(new ZJsonValuer(value).value());
        } catch (ZConversionErrorException ex) {
            throw new ZJsonException(ex);
        }
    }
    
    public void add(int index, Object value){
        JSONArray newJsonArray = new JSONArray();
        for (int i=0;i<index;i++){
            newJsonArray.put(jsonArray.get(i));
        }
        newJsonArray.put(value);
        for (int i=index;i<jsonArray.length();i++){
            newJsonArray.put(jsonArray.get(i));
        }
        jsonArray = newJsonArray;
    }
    
    public void put(int index, Object value) throws ZJsonException{
        try {
            jsonArray.put(index, new ZJsonValuer(value).value());
        } catch (ZConversionErrorException ex) {
            throw new ZJsonException(ex);
        }
    }
    
    public void remove(int index){
        jsonArray.remove(index);
    }
    
    public ZJsonArray copy() {
        try {
            return new ZJsonArray(toString());
        } catch (ZJsonException ex){
            throw new ZUnexpectedException(ex);
        }
    }
    
    public <T> ZList<T> asObjList(Class<T> objClass) throws ZConversionErrorException, ZJsonException, ZNewInstanceException, ZVarHandlerException{
        ZList<T> result = new ZList<>();
        for (ZValue value: listValues()){
            if (value==null){
                result.add(null);
            } else if (ZEntity.class.isAssignableFrom(objClass)){
                ZJsonObject jsonObject = new ZJsonObject(value.asString());
                result.add((T)jsonObject.asEntity((Class<? extends ZEntity>)objClass));
            } else {
                try {
                    T convertedValue = ZConversionManager.getInstance().convert(value, objClass);
                    result.add(convertedValue);
                } catch (ZConversionErrorException e){
                    ZJsonObject jsonObject = new ZJsonObject(value.asString());
                    result.add(jsonObject.asObject(objClass));
                }
            }
        }
        return result;
    }
    
    public <T extends ZEntity> ZList<T> asEntityList(Class<T> entityClass) throws ZConversionErrorException{
        ZList<T> result = new ZList<>();
        for (ZValue value:listValues()){
            T entity;
            if (value==null){
                entity = null;
            } else {
                entity = value.convertTo(ZJsonObject.class).asEntity(entityClass);
            }
            result.add(entity);
        }
        return result;
    }
    
}
