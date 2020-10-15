package br.zul.zwork5.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 *
 * @author Luiz Henrique
 * @param <V>
 */
public class ZStringMap<V> extends LinkedHashMap<String, V>  {

    //==========================================================================
    //VARIAVEIS
    //==========================================================================
    private boolean caseSensitive = true;
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public V get(Object key) {
        if (caseSensitive){
            return super.get(key);
        } else {
            for (Entry<String, V> e:entrySet()){
                if (e.getKey().equalsIgnoreCase((String)key)){
                    return e.getValue();
                }
            }
        }
        return null;
    }
    
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        V value = get((String)key);
        if (value==null){
            return defaultValue;
        }
        return value;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public boolean isCaseSensitive() {
        return caseSensitive;
    }
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }
    
}
