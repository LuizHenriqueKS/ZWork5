package br.zul.zwork5.json;

import br.zul.zwork5.conversion.ZConversionManager;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZJsonException;
import java.util.Collection;

/**
 *
 * @author luizh
 */
class ZJsonRemover {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZJson json;
    private final Collection<Object> keyList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZJsonRemover(ZJson json, Collection<Object> keyList) {
        this.json = json;
        this.keyList = keyList;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void remove() throws ZConversionErrorException, ZJsonException {
        if (json.has(keyList)){
            ZJsonChildLister lister = new ZJsonChildLister(json, keyList);
            ZJson jsonWithKeyToRemove = lister.beforeLast();
            if (jsonWithKeyToRemove.array!=null){
                Integer key = ZConversionManager.getInstance().convert(lister.getLastKey(), Integer.class);
                jsonWithKeyToRemove.array.remove(key);
            } else {
                jsonWithKeyToRemove.object.remove(lister.getLastKey()+"");
            }
            new ZJsonRefresher(json, keyList, lister.list()).removeLast().refresh();
        }
    }

    
}
