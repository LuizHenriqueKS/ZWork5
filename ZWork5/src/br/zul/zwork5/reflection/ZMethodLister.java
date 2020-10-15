package br.zul.zwork5.reflection;

import br.zul.zwork5.util.ZList;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author luizh
 */
public class ZMethodLister {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZClass<?> objClass;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZMethodLister(ZClass<?> objClass) {
        this.objClass = objClass;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZList<ZMethod> list(){
        Set<ZMethod> methodSet = new LinkedHashSet<>();
        convertMethods(objClass._class.getMethods()).forEach(methodSet::add);
        convertMethods(objClass._class.getDeclaredMethods()).forEach(methodSet::add);
        return new ZList<>(methodSet);
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private List<ZMethod> convertMethods(Method[] methods) {
        return Arrays.asList(methods).stream().map(this::convertMethod).collect(Collectors.toList());
    }
    
    private ZMethod convertMethod(Method method){
        return new ZMethod(objClass, method);
    }
    
}
