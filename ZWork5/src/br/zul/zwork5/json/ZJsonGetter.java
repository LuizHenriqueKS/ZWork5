package br.zul.zwork5.json;

import br.zul.zwork5.conversion.ZConversionManager;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZJsonException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.value.ZValue;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author luizh
 */
class ZJsonGetter {
    
    //==========================================================================
    //VARIÁEVIS
    //==========================================================================
    private final ZJson json;
    private final Collection<Object> keyList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZJsonGetter(ZJson json, Collection<Object> keyList) {
        this.json = json;
        this.keyList = keyList;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZValue get() throws ZJsonException {
        try {
            ZJsonChildLister lister = new ZJsonChildLister(json, keyList);
            return getValue(lister.beforeLast(), getLastKey());
        }catch(Exception e){
            throw new ZJsonException(e);
        }
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZValue getValue(ZJson last, Object key) throws ZJsonException, ZConversionErrorException {
        if (last.array!=null){
            return last.array.get(getKeyInt(key));
        } else {
            return last.object.get(String.valueOf(key));
        }
    }

    private Object getLastKey() {
        if (keyList instanceof List){
            List<?> list = ((List<?>)keyList);
            return list.get(list.size()-1);
        } else {
            return new ZList<>(keyList).last();
        }
    }

    private int getKeyInt(Object key) throws ZConversionErrorException {
        if (key instanceof Integer){
            return (int)key;
        } else {
            return ZConversionManager.getInstance().convert(key, Integer.class);
        }
    }
    
}
