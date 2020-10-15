package br.zul.zwork5.util;

import br.zul.zwork5.exception.ZRequiredContentException;
import static br.zul.zwork5.util.ZUtil.hasContent;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author luizh
 */
public class ZValidations {
    
    public static <T> void requireContent(Collection<T> colletion) throws ZRequiredContentException{
        if (!hasContent(colletion)){
            throw new ZRequiredContentException();
        }
    }
    
    public static <T> void requireContent(T[] array) throws ZRequiredContentException{
        if (!hasContent(array)){
            throw new ZRequiredContentException();
        }
    }
    
    public static void requireContent(String string) throws ZRequiredContentException{
        if (!hasContent(string)){
            throw new ZRequiredContentException();
        }
    }
    
    public static <T> void requireNonNull(T obj) throws NullPointerException{
        Objects.requireNonNull(obj);
    }
    
}
