package br.zul.zwork5.util;

import br.zul.zwork5.stream.ZStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import br.zul.zwork5.stream.FunctionThrowable;

/**
 *
 * @author Luiz Henrique
 * @param <T>
 */
public class ZList<T> extends ArrayList<T> {
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZList() {
    }

    public ZList(Collection<? extends T> collection) {
        super(collection);
    }
    
    public ZList(Stream<T> stream){
        super();
        stream.forEach(this::add);
    }
    
    public static <T> ZList<T> asList(T... array) {
        return new ZList<>(Arrays.asList(array));
    }
    
    public static <T> ZList<T> fromArray(T[] array) {
        return new ZList<>(Arrays.asList(array));
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    @Override
    public ZStream<T> stream(){
        return new ZStream<>(super.stream());
    }
    
    public T[] toArray(Class<T> tClass){
        T[] array = (T[]) Array.newInstance(tClass, size());
        int index = 0;
        for (T obj: this){
            array[index++] = obj;
        }
        return array;
    }
    
    public ZList<T> reverse(){
       Collections.reverse(this);
       return this;
    }
    
    public ZList<T> shuffle(){
        Collections.shuffle(this);
        return this;
    }
    
    /**
     * @return Retorna o primeiro índice da lista.
     *          Retorna -1 se a lista estiver vazia.
     */
    public int firstIndex(){
        if (isEmpty()){
            return -1;
        }
        return 0;
    }
    
    public int lastIndex(){
        return size() - 1;
    }
    
    public T first() throws IndexOutOfBoundsException{
        return get(0);
    }
    
    public T optFirst(){
        try {
            return first();
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }
    
    public T last() throws IndexOutOfBoundsException{
        return get(lastIndex());
    }
    
    public T optLast(){
        try {
            return last();
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }
    
    public T last(int skip) throws IndexOutOfBoundsException{
        return get(lastIndex()-skip);
    }
    
    public T removeLast() throws IndexOutOfBoundsException{
        return remove(lastIndex());
    }
    
    public T removeFirst() throws IndexOutOfBoundsException{
        return remove(firstIndex());
    }
    
    public ZList<T> copy(){
        return new ZList<>(this);
    }
    
    public ZList<T> fromIndex(int index){
        removeRange(0, index);
        return this;
    }
    
    public ZList<T> tillIndex(int index){
        removeRange(index+1, lastIndex()+1);
        return this;
    }
    
    public ZList<T> addAll(T... items){
        addAll(Arrays.asList(items));
        return this;
    }
    
    public <R> ZList<R> map(Function<T, R> mapper){
        ZList<R> result = new ZList<>();
        for (T item:this){
            result.add(mapper.apply(item));
        }
        return result;
    }
    
    public <R, E extends Throwable> ZList<R> mapThrowable(FunctionThrowable<T, R, E> mapper) throws E{
        ZList<R> result = new ZList<>();
        for (T item:this){
            result.add(mapper.apply(item));
        }
        return result;
    }
    
    public T first(Predicate<T> predicate) throws NoSuchElementException{
        return stream().filter(predicate).findFirst().get();
    }
    
    public ZList<T> filter(Predicate<T> predicate){
        List<T> list = stream().filter(predicate).collect(Collectors.toList());
        return new ZList<>(list);
    }
    
    public boolean anyMatch(Predicate<T> predicate){
        return stream().anyMatch(predicate);
    }
    
    public int count(Predicate<T> predicate){
        return (int)stream().filter(predicate).count();
    }
    
    public T opt(int index){
        try {
            return get(index);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }
    
    public T optFirst(Predicate<T> predicate){
        try {
            return first(predicate);
        }catch (NoSuchElementException e){
            return null;
        }
    }
    
    public static <T> Collector<T, ?, ZList<T>>  getCollector(){
        return Collector.of(()->new ZList<>(), 
                            (list, item)->list.add(item), 
                            (list1, list2)->{
                                ZList<T> result = new ZList<>();
                                result.addAll(list1);
                                result.addAll(list2);
                                return result;
                            });
    }
    
}
