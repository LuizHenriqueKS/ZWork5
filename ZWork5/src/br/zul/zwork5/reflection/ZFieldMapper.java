package br.zul.zwork5.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author luizh
 */
class ZFieldMapper {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Class<?> _class;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZFieldMapper(Class<?> _class) {
        this._class = _class;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Map<String, ZField> map(){
        List<Class<?>> parentList = listParents(_class);
        Map<String, ZField> result = new LinkedHashMap<>();
        
        parentList.forEach(cls->{
            fillWithFields(cls, result);
            fillWithDeclaredFields(cls, result);
        });
        
        return result;
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void fillWithFields(Class<?> cls, Map<String, ZField> result) {
        convertFields(cls.getFields()).forEach(f->result.put(f.getName(), f));
    }

    private void fillWithDeclaredFields(Class<?> cls, Map<String, ZField> result) {
        convertFields(cls.getDeclaredFields()).forEach(f->result.put(f.getName(), f));
    }

    private List<ZField> convertFields(Field[] fields) {
        return Arrays.asList(fields).stream().map(ZField::new).collect(Collectors.toList());
    }
    
    private List<Class<?>> listParents(Class<?> cls){
        List<Class<?>> parentList = new ArrayList<>();
        do {
            parentList.add(cls);
            cls = cls.getSuperclass();
        } while (!cls.equals(Object.class));
        return parentList;
    }
    
}

