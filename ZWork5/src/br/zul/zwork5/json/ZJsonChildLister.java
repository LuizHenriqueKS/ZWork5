package br.zul.zwork5.json;

import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZJsonException;
import br.zul.zwork5.util.ZList;
import java.util.Collection;

/**
 *
 * @author luizh
 */
class ZJsonChildLister {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZJson json;
    private final ZList<Object> keyList;
    private ZList<ZJson> result;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    ZJsonChildLister(ZJson json, Collection<Object> keyList) {
        this.json = json;
        this.keyList = newKeyList(keyList);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZJson beforeLast(){
        try {
            if (keyList.size()==1){
                return json;
            }
            return list().last(1).get();
        } catch (IndexOutOfBoundsException e){
            return json;
        }
    }

    public ZJson last() {
        return list().last().get();
    }
    
    public ZList<ZJson> list(){
        if (result==null){
            ZJson current = json;
            result = new ZList<>();
            for (Object key:keyList){
                try {
                    current = current.get(key).convertTo(ZJson.class).get();
                } catch (ZConversionErrorException|ZJsonException e){
                    current = new ZJson();
                }
                result.add(current);
            }
        }
        return result;
    }
    
    public Object getLastKey(){
        return keyList.last();
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZList<Object> newKeyList(Collection<Object> keyList) {
        if (keyList instanceof ZList){
            return (ZList<Object>) keyList;
        } else {
            return new ZList<>(keyList);
        }
    }
    
    
}
