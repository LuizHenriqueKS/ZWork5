package br.zul.zwork5.util;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @author luizh
 */
public class ZStreamUtils {
 
    public static <T> Stream<T> fromIterator(Iterator<T> iterator){
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    }
    
}
