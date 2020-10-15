package br.zul.zwork5.reflection;

import br.zul.zwork5.exception.ZMethodException;
import br.zul.zwork5.exception.ZMethodNotFoundException;
import br.zul.zwork5.exception.ZNewInstanceException;
import br.zul.zwork5.util.ZList;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Luiz Henrique
 * @param <T>
 */
public class ZClass<T> {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    final Class<T> _class;
    
    private Map<String, ZField> fieldMap;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZClass(Class<T> _class) {
        this._class = _class;
        Objects.requireNonNull(this._class);
    }
    
    public ZClass(String className) throws ClassNotFoundException{
        this._class = (Class<T>)Class.forName(className);
        Objects.requireNonNull(this._class);
    }
    
    //==========================================================================
    //MÉTODOS PUBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this._class);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZClass<?> other = (ZClass<?>) obj;
        if (!Objects.equals(this._class, other._class)) {
            return false;
        }
        return true;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    /**
     * LISTA TODAS AS CLASSES QUE PROVEM ALGUMA HERANÇA PARA A CLASSE
     * @return 
     */
    public ZList<Class<?>> listAllParentClasses(){
        return new ZParentClassLister(_class).list();
    }
    
    /**
     * LISTA APENAS A CLASSE PAI E AS INTERFACES
     * @return 
     */
    public ZList<Class<?>> listParentClasses(){
        Set<Class<?>> set = new LinkedHashSet<>();
        set.add(_class.getSuperclass());
        set.addAll(Arrays.asList(_class.getInterfaces()));
        set.remove(_class);
        return new ZList<>(set);
    }
    
    public T newInstance() throws ZNewInstanceException{
        Constructor<T> constructor;
        try {
            constructor = _class.getConstructor();
        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException ex) {}
        try {
            constructor = _class.getDeclaredConstructor();
        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException ex) {
            throw new ZNewInstanceException(ex);
        }
        try {
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new ZNewInstanceException(ex);
        }
    }
    
    public ZObjHandler newInstanceHandler() throws ZNewInstanceException{
        return new ZObjHandler(newInstance());
    }

    public ZList<ZField> listFields() {
        return new ZList<>(getFieldMap().values());
    }

    public ZList<ZMethod> listMethods() {
        return new ZMethodLister(this).list();
    }
    
    public boolean isAssignableFrom(Class<?> cls){
        return _class.isAssignableFrom(cls);
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private Map<String, ZField> getFieldMap() {
        if (fieldMap==null) fieldMap = new ZFieldMapper(_class).map();
        return fieldMap;
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public String getName(){
        return _class.getName();
    }
    
    public String getSimpleName(){
        return _class.getSimpleName();
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        T annotation = _class.getDeclaredAnnotation(annotationClass);
        if (annotation!=null) return annotation;
        return _class.getAnnotation(annotationClass);
    }
    
    public boolean hasAnnotation(Class<? extends Annotation> annotationClass){
        return getAnnotation(annotationClass)!=null;
    }

    public ZField getField(String name) {
        return getFieldMap().get(name);
    }
    
    public Class<T> getType(){
        return _class;
    }

    public ZMethod getMethod(String name) throws ZMethodNotFoundException {
        List<ZMethod> methodList = listMethods().stream()
                                                .filter(m->m.getName().equals(name))
                                                .collect(Collectors.toList());
        if (methodList.size()>1) throw new ZMethodNotFoundException("Existe mais de um método com o mesmo nome.");
        if (methodList.isEmpty()) throw new ZMethodNotFoundException("[Class: {0}] [Method: {1}]", getName(), name);
        return methodList.get(0);
    }
    
}
