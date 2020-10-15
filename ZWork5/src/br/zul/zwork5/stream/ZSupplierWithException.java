package br.zul.zwork5.stream;

/**
 *
 * @author luizh
 */
public interface ZSupplierWithException<T> {
    
    public T get() throws Exception;
    
}
