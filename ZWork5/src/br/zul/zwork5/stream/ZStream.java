package br.zul.zwork5.stream;

import br.zul.zwork5.util.ZList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author luiz.silva
 * @param <T>
 */
public class ZStream<T> implements Stream<T> {
    
    //==========================================================================
    //VARIAVEIS
    //==========================================================================
    private final Stream<T> stream;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZStream(Stream<T> stream) {
        this.stream = stream;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    /*public <R,E> ZStream<R> mapWithException(FunctionWithException<? super T, R, E> mapper){
        
    }*/
    
    public ZList<T> toList() {
        return collect(ZList.getCollector());
    }
    
    public Optional<T> findLast(){
        try {
            Iterator<T> iterator = stream.iterator();
            if (iterator.hasNext()){
                T item;
                T lastItem = null;
                while ((item = iterator.next())!=null){
                    lastItem = item;
                }
                return Optional.ofNullable(lastItem);
            } else {
                return Optional.empty();
            }
        } finally {
            close();
        }
    }
    
    public Iterable<T> iterable(){
        return ()->iterator();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZStream<T> filter(Predicate<? super T> predicate) {
        return new ZStream(stream.filter(predicate));
    }

    @Override
    public <R> ZStream<R> map(Function<? super T, ? extends R> mapper) {
        return new ZStream(stream.map(mapper));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return stream.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return stream.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return stream.mapToDouble(mapper);
    }

    @Override
    public <R> ZStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return new ZStream(stream.flatMap(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return stream.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return stream.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return stream.flatMapToDouble(mapper);
    }

    @Override
    public ZStream<T> distinct() {
        return new ZStream<>(stream.distinct());
    }

    @Override
    public ZStream<T> sorted() {
        return new ZStream<>(stream.sorted());
    }

    @Override
    public ZStream<T> sorted(Comparator<? super T> comparator) {
        return new ZStream<>(stream.sorted(comparator));
    }

    @Override
    public ZStream<T> peek(Consumer<? super T> action) {
        return new ZStream<>(stream.peek(action));
    }

    @Override
    public ZStream<T> limit(long maxSize) {
        return new ZStream<>(stream.limit(maxSize));
    }

    @Override
    public ZStream<T> skip(long n) {
        return new ZStream<>(stream.skip(n));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        stream.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        stream.forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return stream.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return stream.toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return stream.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return stream.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return stream.collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return stream.min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return stream.max(comparator);
    }

    @Override
    public long count() {
        return stream.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return stream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return stream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return stream.noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return stream.findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return stream.findAny();
    }

    @Override
    public Iterator<T> iterator() {
        return stream.iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return stream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public ZStream<T> sequential() {
        return new ZStream<>(stream.sequential());
    }

    @Override
    public ZStream<T> parallel() {
        return new ZStream<>(stream.parallel());
    }

    @Override
    public ZStream<T> unordered() {
        return new ZStream<>(stream.unordered());
    }

    @Override
    public ZStream<T> onClose(Runnable closeHandler) {
        return new ZStream<>(stream.onClose(closeHandler));
    }

    @Override
    public void close() {
        stream.close();
    }

}
