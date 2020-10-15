package br.zul.zwork5.conversion.complex;

import java.util.stream.Stream;

/**
 *
 * @author luizh
 */
public interface ZComplexConverter<T> {
    
    public Class<T> getType();
    public boolean isValidInput(ZComplexConvertedValueGetter<?> getter);
    public boolean isValidOutput(ZComplexConvertedValueGetter<?> getter);
    public Stream<Object> stream(T obj);
    public T newInstance();
    public void add(T obj, Object item, ZComplexConvertedValueGetter<?> getter);
    
}
