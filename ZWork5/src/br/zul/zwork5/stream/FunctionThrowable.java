package br.zul.zwork5.stream;

/**
 *
 * @author luiz.silva
 * @param <T>
 * @param <R>
 * @param <E>
 */
public interface FunctionThrowable<T, R, E extends Throwable> {
    
    R apply(T t) throws E;
    
}
