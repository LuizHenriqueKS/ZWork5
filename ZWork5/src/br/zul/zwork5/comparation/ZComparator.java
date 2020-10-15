package br.zul.zwork5.comparation;

import br.zul.zwork5.exception.ZComparationException;

/**
 *
 * @author luizh
 * @param <T>
 */
public interface ZComparator<T> {
    
    public Class<T> getType();
    public int compare(T obj1, T obj2) throws ZComparationException;
    
}
