package br.zul.zwork5.util;

import br.zul.zwork5.random.ZRandom;
import br.zul.zwork5.stream.ZSupplierWithException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZUtil {

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
    
    public static <T> boolean hasContent(Collection<T> collection){
        return collection!=null&&!collection.isEmpty();
    }

    public static <T> boolean hasContent(T[] vector) {
        return vector!=null&&vector.length>0;
    }

    public static boolean hasContent(String string) {
        return string!=null&&!string.isEmpty();
    }
    
    public static boolean isEmptyOrNull(String str){
        return !hasContent(str);
    }

    public static <T> T random(Collection<T> collection) {
        return new ZRandom().nextObj(collection);
    }
    
    public static <T> T firstNotNull(T... args){
        if (hasContent(args)){
            for (T t:args){
                if (t!=null) return t;
            }
        }
        return null;
    }

    public static <T> T last(Collection<T> collection) throws IndexOutOfBoundsException{
        if (collection instanceof List) {
            List<T> tList = (List<T>) collection;
            return tList.get(tList.size()-1);
        } else {
            return last(new ArrayList<>(collection));
        }
    }
    
    public static <T> T tryGet(ZSupplierWithException<T> supplier, T defaultValue){
        try {
            T result = supplier.get();
            if (result==null){
                return defaultValue;
            }
            return result;
        } catch (Exception ex){
            return defaultValue;
        }
    }
    
    public static <T> T tryGet(ZSupplierWithException<T> supplier){
        return tryGet(supplier, null);
    }
    
}
