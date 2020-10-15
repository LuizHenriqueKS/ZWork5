package br.zul.zwork5.conversion;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 *
 * @author luizh
 */
public class ZPrimitiveClass {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private static final Map<Class<?>, Class<?>> NATIVE_CLASS_MAP = new LinkedHashMap<>();
    
    //==========================================================================
    //INICIALIZADOR
    //==========================================================================
    static {
        NATIVE_CLASS_MAP.put(int.class, Integer.class);
        NATIVE_CLASS_MAP.put(long.class, Long.class);
        NATIVE_CLASS_MAP.put(float.class, Float.class);
        NATIVE_CLASS_MAP.put(double.class, Double.class);
        NATIVE_CLASS_MAP.put(boolean.class, Boolean.class);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public static Class<?> getPrimitiveClass(Class<?> cls) throws NoSuchElementException{
        return NATIVE_CLASS_MAP.entrySet()
                               .stream()
                               .filter(e->e.getValue().equals(cls))
                               .findFirst()
                               .map(e->e.getKey())
                               .get();
    }
    
    
    public static Class<?> getNonPrimitiveClass(Class<?> cls) throws NoSuchElementException{
        Class<?> result = NATIVE_CLASS_MAP.get(cls);
        if (result==null){
            throw new NoSuchElementException();
        }
        return result;
    }
    
}