package br.zul.zwork5.conversion;

import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.util.ZUtil;

/**
 *
 * @author luiz.silva
 * @param <T>
 */
public interface ZConversionOut<T> {
    
    public T get() throws ZConversionErrorException;
    
    public default T orElse(T alternative){
        return ZUtil.tryGet(()->alternative, alternative);
    }
    
}
