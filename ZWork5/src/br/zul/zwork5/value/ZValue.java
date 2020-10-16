package br.zul.zwork5.value;

import br.zul.zwork5.conversion.ZConversionManager;
import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZConversionOut;
import br.zul.zwork5.entity.ZEntity;
import br.zul.zwork5.str.ZStr;
import br.zul.zwork5.timestamp.ZDate;
import br.zul.zwork5.timestamp.ZDateTime;
import br.zul.zwork5.timestamp.ZTime;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @author luiz.silva
 */
public interface ZValue {

    public Object asObject();
    
    default public ZConversionOut<String> asString(){
        return convertTo(String.class);
    }
    
    default public ZConversionOut<Boolean> asBoolean() {
        return convertTo(Boolean.class);
    }
    
    default public ZConversionOut<Integer> asInteger(){
        return convertTo(Integer.class);
    }
    
    default public ZConversionOut<Double> asDouble(){
        return convertTo(Double.class);
    }
    
    default public ZConversionOut<ZStr> asStr(boolean caseSensitive) {
        return ()->new ZStr(asString().get(), caseSensitive);
    }
    
    default public ZConversionOut<ZDate> asDate() {
        return convertTo(ZDate.class);
    }
    
    default public ZConversionOut<ZDateTime> asDateTime() {
        return convertTo(ZDateTime.class);
    }
    
    default public ZConversionOut<ZTime> asTime() {
        return convertTo(ZTime.class);
    }
    
    default public ZConversionOut<Long> asLong() {
        return convertTo(Long.class);
    }

    default public <T extends ZEntity> ZConversionOut<T> asEntity(Class<T> entityClass) {
        return convertTo(entityClass);
    }
    
    default public <T> ZConversionOut<T> convertTo(Class<T> toClass) {
        Objects.requireNonNull(toClass);
        ZConversionObj in = new ZConversionObj(this, toClass);
        ZConversionOut out = ()->(T)ZConversionManager.getInstance().convert(in);
        return out;
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
