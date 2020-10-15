package br.zul.zwork5.json;

import br.zul.zwork5.conversion.ZConversionManager;
import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.annotation.ZListAnnotation;
import br.zul.zwork5.currency.ZCurrency;
import br.zul.zwork5.entity.ZAttribute;
import br.zul.zwork5.entity.ZEntity;
import br.zul.zwork4.exception.ZJsonException;
import br.zul.zwork5.io.ZFile;
import br.zul.zwork4.timestamp.ZDate;
import br.zul.zwork4.timestamp.ZDateTime;
import br.zul.zwork4.timestamp.ZTime;
import br.zul.zwork4.timestamp.ZTimeStamp;
import br.zul.zwork4.util.ZUtil;
import br.zul.zwork4.value.ZValue;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author luizh
 */
public class ZJsonValuer {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Object value;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZJsonValuer(Object value) {
        this.value = value;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Object value() {
        if (value==null){
            return null;
        } else if (isSupported(value)){
            return value;
        } else if (value instanceof ZCurrency){
            return currencyToValue((ZCurrency)value);
        } else if (value instanceof ZEntity){
            return entityToValue((ZEntity)value);
        } else if (value instanceof ZJson){
            return jsonToValue((ZJson)value);
        } else if (value instanceof ZJsonObject){
            return jsonObjectToValue((ZJsonObject)value);
        } else if (value instanceof ZJsonArray){
            return jsonArrayToValue((ZJsonArray)value);
        } else if (value instanceof ZValue){
            return valueToValue((ZValue)value);
        } else if (value instanceof ZConversionObj){
            return conversionObjToValue((ZConversionObj)value);
        } else if (value instanceof ZDate){
            return dateToValue((ZDate)value);
        } else if (value instanceof ZDateTime){
            return dateTimeToValue((ZDateTime)value);
        } else if (value instanceof ZTime){
            return timeToValue((ZTime)value);
        } else if (value instanceof ZTimeStamp){
            return timeStampToValue((ZTimeStamp)value);
        } else if (value instanceof Collection){
            return collectionToValue((Collection)value);
        } else if (value instanceof Class){
            return classToValue((Class<?>)value);
        } else if (value instanceof ZFile){
            return fileToValue((ZFile)value);
        } else if (value instanceof Enum){
            return ((Enum)value).name();
        } else if (value.getClass().isArray()){
            return arrayToValue(value);
        } else if (value instanceof Object){
            return new ZJsonObject(value).jsonObject;
        } else {
            throw new ZJsonException("Valor para json não suportado ({0}): {1}", value.getClass().getName(), value);
        }
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private boolean isSupported(Object value) {
        if (value instanceof Integer) return true;
        if (value instanceof Long) return true;
        if (value instanceof String) return true;
        if (value instanceof Double) return true;
        if (value instanceof Float) return true;
        if (value instanceof Date) return true;
        if (value instanceof Boolean) return true;
        if (value instanceof JSONObject) return true;
        if (value instanceof JSONArray) return true;
        if (value instanceof BigDecimal) return true;
        return false;
    }

    private Object jsonToValue(ZJson json) {
        if (json.isArray()){
            return json.array.jsonArray;
        } else {
            return json.object.jsonObject;
        }
    }

    private Object jsonObjectToValue(ZJsonObject jsonObject) {
        return jsonObject.jsonObject;
    }

    private Object jsonArrayToValue(ZJsonArray jsonArray) {
        return jsonArray.jsonArray;
    }

    private Object valueToValue(ZValue value) {
        if (isSupported(value.asObject())){
            return value.asObject();
        } else {
            return new ZJsonValuer(value.asObject()).value();
        }
    }

    private Object conversionObjToValue(ZConversionObj obj) {
        if (isNeedToConvert(obj)){
            return ZConversionManager.getInstance().convert(obj, String.class);
        } else {
            return new ZJsonValuer(obj.getValue()).value();
        }
    }

    private Object dateToValue(ZDate value) {
        return value.toString();
    }

    private Object dateTimeToValue(ZDateTime value) {
        return value.toString();
    }

    private Object timeToValue(ZTime value) {
        return value.toString();
    }

    private Object timeStampToValue(ZTimeStamp value) {
        return value.toString();
    }

    private Object classToValue(Class<?> aClass) {
        return aClass.getName();
    }

    private Object entityToValue(ZEntity entity) {
        return new ZJsonObject(entity).jsonObject;
    }

    private Object collectionToValue(Collection<?> collection) {
        JSONArray array = new JSONArray();
        for (Object item:collection){
            array.put(new ZJsonValuer(item).value());
        }
        return array;
    }

    private Object fileToValue(ZFile file) {
        return file.getPath();
    }

    private boolean isNeedToConvert(ZConversionObj obj) {
        if (obj.getValue() instanceof String){
            return true;
        } else if (ZUtil.hasContent(obj.getAnnotationList())){
            List<? extends Annotation> annotationList = obj.getAnnotationList();
            annotationList.removeIf(an->an instanceof ZAttribute);
            annotationList.removeIf(an->an instanceof ZListAnnotation);
            return !annotationList.isEmpty();
        } else {
            return false;
        }
    }

    private Object currencyToValue(ZCurrency cur) {
        return cur.toString();
    }

    private Object arrayToValue(Object value) {
        List<Object> objectList = new ArrayList<>();
        for (int i=0;i<Integer.MAX_VALUE;i++){
            try {
                Object item = Array.get(value, i);
                objectList.add(item);
            } catch (ArrayIndexOutOfBoundsException ex){
                break;
            }
        }
        return collectionToValue(objectList);
    }
    
}