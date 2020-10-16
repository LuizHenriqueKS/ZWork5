package br.zul.zwork5.json;

import br.zul.zwork5.exception.ZJsonException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.value.ZValue;
import java.util.Collection;

/**
 *
 * @author luizh
 */
public class ZJsonEntry {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZJson json;
    private final Collection<Object> keyList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    ZJsonEntry(ZJson json, Collection<Object> keyList) {
        this.json = json;
        this.keyList = new ZList<>(keyList);
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZValue getValue() throws ZJsonException{
        return json.get(keyList);
    }

    public void setValue(Object value) throws ZJsonException {
        json.put(keyList, value);
    }
    
    public void addValue(Object value){
        ZList<Object> newKeyList = new ZList<>(keyList);
        newKeyList.add(null);
        json.add(newKeyList, value);
    }
    
    public void addValue(int index, Object value){
        ZList<Object> newKeyList = new ZList<>(keyList);
        newKeyList.add(index);
        json.add(newKeyList, value);
    }

    public boolean isValid() {
        return json.has(keyList);
    }

    public ZValue optValue() {
        try {
            return getValue();
        } catch(Exception e){
            return null;
        }
    }
    
}
