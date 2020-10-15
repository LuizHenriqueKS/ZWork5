package br.zul.zwork5.reflection;

import br.zul.zwork5.util.ZList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author luizh
 */
class ZParentClassLister {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Class<?> _class;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZParentClassLister(Class<?> _class){
        this._class = _class;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZList<Class<?>> list(){
        Set<Class<?>> set = new LinkedHashSet<>();
        addParentClasses(set, _class);
        set.remove(_class);
        return new ZList<>(set);
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void addParentClasses(Set<Class<?>> set, Class<?> cls){
        if (cls==null) return;
        Set<Class<?>> tmpSet = new LinkedHashSet<>();
        tmpSet.add(cls);
        tmpSet.addAll(Arrays.asList(cls.getInterfaces()));
        tmpSet.add(cls.getSuperclass());
        for (Class<?> c:tmpSet){
            if (c!=null&&!set.contains(c)){
                set.add(c);
                addParentClasses(set, c);
            }
        }
        addParentClasses(set, cls.getSuperclass());
    }
    
}
