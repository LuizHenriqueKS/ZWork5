package br.zul.zwork5.util;

/**
 *
 * @author Luiz Henrique
 * @param <T>
 */
public interface ZBuilder<T> {
    
    public void validate();
    public T implement();
    
    default T build(){
        validate();
        return implement();
    }
    
}
