package br.zul.zwork5.json;

import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZJsonException;
import java.util.Collection;

/**
 *
 * @author luizh
 */
class ZJsonAdder {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZJson json;
    private final Collection<Object> keyList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZJsonAdder(ZJson json, Collection<Object> keyList) {
        this.json = json;
        this.keyList = keyList;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void add(Object value) throws ZConversionErrorException, ZJsonException{
        new ZJsonPutter(json, keyList).add(value);
    }
    
}
