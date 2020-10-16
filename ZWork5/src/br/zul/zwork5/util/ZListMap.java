package br.zul.zwork5.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 *
 * @author Luiz Henrique
 * @param <K>
 * @param <V>
 */
public class ZListMap<K, V> extends LinkedHashMap<K, ZList<V>> {
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZListMap(){
    
    }

    public ZListMap(Map<K, ZList<V>> base) {
        super();
        base.entrySet().forEach(e->{
            getOrNew(e.getKey()).addAll(e.getValue());
        });
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Optional<V> first(K key) throws IndexOutOfBoundsException, NoSuchElementException{
        return get(key).first();
    }
    
    public Optional<V> last(K key) throws IndexOutOfBoundsException, NoSuchElementException{
        return get(key).last();
    }
    
    @Override
    public ZList<V> get(Object key){
        ZList<V> valueList = super.get(key);
        if (valueList!=null){
            if (valueList.isEmpty()){
                remove((K)key);
                return null;
            } 
            return valueList;
        }
        return null;
    }
    
    public void addValue(K key, V value){
       getOrNew(key).add(value);
    }
    
    public V getValue(K key, int index){
        return get(key).get(index);
    }
    
    public void removeValue(K key, int index){
        ZList<V> valueList = get(key);
        if (valueList!=null){
            valueList.remove(index);
            if (valueList.isEmpty()){
                remove(key);
            }
        }
    }
    
    public void removeValue(K key, V value){
        ZList<V> valueList = get(key);
        if (valueList!=null){
            valueList.remove(value);
            if (valueList.isEmpty()){
                remove(key);
            }
        }
    }
    
    public ZList<V> listValues(){
        ZList<V> result = new ZList<>();
        for (ZList<V> valueList:values()){
            result.addAll(valueList);
        }
        return result;
    }
    
    public ZList<V> getOrNew(K key){
        ZList<V> valueList;
        try {
            valueList = get(key);
            if (valueList==null) throw new NoSuchElementException();
        }catch(NoSuchElementException e){
            valueList = new ZList<>();
            put(key, valueList);
        }
        return valueList;
    }
    
    public ZListMap<K, V> copy(){
        return new ZListMap<>(this);
    }
    
}
