package br.zul.zwork5.value;

import br.zul.zwork5.conversion.ZConversionManager;
import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.entity.ZEntity;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.str.ZStr;
import br.zul.zwork5.timestamp.ZDate;
import br.zul.zwork5.timestamp.ZDateTime;
import br.zul.zwork5.timestamp.ZTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import static javafx.scene.input.KeyCode.T;

/**
 *
 * @author luiz.silva
 */
public interface ZValue {

    public Object asObject();
    
    default public String asString() throws ZConversionErrorException{
        return convertTo(String.class);
    }
    
    default public String optString(){
        return convertTo(String.class, null);
    }
    
    default public boolean asBool() throws ZConversionErrorException{
        return convertTo(Boolean.class);
    }
    
    default public Boolean asBoolean() throws ZConversionErrorException{
        return convertTo(Boolean.class);
    }
    
    default public boolean optBool(boolean _default){
        return convertTo(Boolean.class, _default);
    }
    
    default public Boolean optBoolean(){
        return convertTo(Boolean.class, null);
    }
    
    default public int asInt() throws ZConversionErrorException{
        return convertTo(Integer.class);
    }
    
    default public Integer asInteger() throws ZConversionErrorException{
        return convertTo(Integer.class);
    }
    
    default public int optInt(int _default){
        return convertTo(Integer.class, _default);
    }
    
    default public Integer optInteger(){
        return convertTo(Integer.class, null);
    }
    
    default public Double asDouble() throws ZConversionErrorException{
        return convertTo(Double.class);
    }
    
    default public Double optDouble(){
        return convertTo(Double.class, null);
    }
    
    default public ZStr asStr(boolean caseSensitive) throws ZConversionErrorException{
        return new ZStr(asString(), caseSensitive);
    }
    
    default public ZStr optStr(boolean caseSensitive){
        try {
            if (rawObj()==null) return null;
            return asStr(caseSensitive);
        } catch (Exception ex){
            return null;
        }
    }
    
    default public ZDate asDate() throws ZConversionErrorException{
        return convertTo(ZDate.class);
    }
    
    default public ZDate optDate(){
        return convertTo(ZDate.class, null);
    }
    
    default public ZDateTime asDateTime() throws ZConversionErrorException{
        return convertTo(ZDateTime.class);
    }
    
    default public ZDateTime optDateTime(){
        return convertTo(ZDateTime.class, null);
    }
    
    default public ZTime asTime() throws ZConversionErrorException{
        return convertTo(ZTime.class);
    }
    
    default public ZTime optTime(){
        return convertTo(ZTime.class, null);
    }
    
    default public Long asLong() throws ZConversionErrorException{
        return convertTo(Long.class);
    }
    
    default public Long optLong(){
        return convertTo(Long.class, null);
    }

    default public <T extends ZEntity> T asEntity(Class<T> entityClass) throws ZConversionErrorException{
        return convertTo(entityClass);
    }
    
    default public <T> T convertTo(Class<T> toClass) throws ZConversionErrorException{
        Objects.requireNonNull(toClass);
        return ZConversionManager.getInstance().convert(asObject(), toClass);
    }
    
    default public <T> T convertTo(Class<T> toClass, T _default){
        Objects.requireNonNull(toClass);
        return ZConversionManager.getInstance().convert(asObject(), toClass, _default);
    }
    
    default public Object rawObj(){
        Object child = asObject();
        while (true){
            if (child instanceof ZValue){
                return ((ZValue)child).rawObj();
            } else if (child instanceof ZConversionObj){
                child = ((ZConversionObj)child).getValue();
            } else {
                break;
            }
        }
        return child;
    }

    default public boolean isNull(){
        return rawObj()==null;
    }
    
    default public <T extends Exception> ZValue throwIfNull(Supplier<T> exceptionSupplier) throws T {
        if (isNull()){
            throw exceptionSupplier.get();
        }
        return this;
    }
    
    default public ZValue ifIsNull(Consumer<ZValue> consumer) {
        if (isNull()){
            consumer.accept(this);
        } 
        return this;
    }
    
    default public ZValue ifIsNotNull(Consumer<ZValue> consumer) {
        if (!isNull()){
            consumer.accept(this);
        } 
        return this;
    }
    
}
