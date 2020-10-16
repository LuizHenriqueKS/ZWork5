package br.zul.zwork5.util;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Luiz Henrique
 * @param <K>
 * @param <V>
 */
public class ZListTreeMap<K, V> extends TreeMap<K, ZList<V>> {
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZListTreeMap(){
        
    }

    public ZListTreeMap(Map<K, ZList<V>> base) {
        super(base);
        base.entrySet().forEach(e->{
            getOrNew(e.getKey()).addAll(e.getValue());
        });
    }

    public ZListTreeMap(Comparator<? super K> comparator) {
        super(comparator);
    }

    public ZListTreeMap(SortedMap<K, ? extends ZList<V>> sortedMap) {
        super(sortedMap);
    }
    
    public ZListTreeMap(ZListTreeMap<K, V> treeMap) {
        this((SortedMap<K, ? extends ZList<V>>)treeMap);
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Optional<V> first(K key) {
        try {
            return get(key).first();
        } catch (NullPointerException ex){
            return Optional.empty();
        }
    }
    
    public Optional<V> last(K key){
        try {
            return get(key).last();
        } catch (NullPointerException ex){
            return Optional.empty();
        }
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
        ZList<V> valueList = get(key);
        if (valueList==null){
            valueList = new ZList<>();
            put(key, valueList);
        }
        return valueList;
    }
    
    public ZListTreeMap<K, V> copy(){
        return new ZListTreeMap<>(this);
    }
    
}
