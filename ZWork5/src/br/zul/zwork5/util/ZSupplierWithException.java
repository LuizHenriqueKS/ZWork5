package br.zul.zwork5.util;

/**
 *
 * @author luizh
 */
public interface ZSupplierWithException<T> {
    
    public T get() throws Exception;
    
}
